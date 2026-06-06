package com.typicode.jsonplaceholder.tests;

import com.aventstack.extentreports.ExtentTest;
import com.typicode.jsonplaceholder.GetUserPosts;
import com.typicode.jsonplaceholder.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class UserPostsApiTest extends BaseTestClass {

    private GetUserPosts getUserPosts;
    private int userId;

    @BeforeClass
    public void init() {
        getUserPosts = new GetUserPosts();
        userId = Integer.parseInt(ConfigReader.get("test.postUserId"));
    }

    @Test(priority = 0, description = "GET /posts?userId returns HTTP 200")
    public void verifyGetUserPostsStatusCode() {
        ExtentTest test = extent.createTest("verifyGetUserPostsStatusCode", "GET /posts/{id} returns HTTP 200");
        extentTest.set(test);
        getUserPosts.getUserPostData_WithStatusCode(userId, 200);
        test.pass("GET /posts/" + userId + " returned 200 OK");
    }

    @Test(priority = 1, description = "User posts list is not empty")
    public void verifyUserPostsNotEmpty() {
        ExtentTest test = extent.createTest("verifyUserPostsNotEmpty", "Posts list should not be empty");
        extentTest.set(test);
        getUserPosts.getUserPostData(userId);
        List<Integer> postIds = getUserPosts.returnPostIDList();
        Assert.assertNotNull(postIds);
        Assert.assertFalse(postIds.isEmpty(), "Post ID list should not be empty for userId=" + userId);
        Assert.assertNotNull(getUserPosts.getPostTitle());
        Assert.assertNotNull(getUserPosts.getPostBody());
        test.pass("Found " + postIds.size() + " posts for userId=" + userId);
        logger.info("Post IDs for userId={}: {}", userId, postIds);
    }

    @Test(priority = 2, description = "All posts belong to the requested userId")
    public void verifyPostsBelongToUser() {
        ExtentTest test = extent.createTest("verifyPostsBelongToUser", "Each post userId should match requested userId");
        extentTest.set(test);
        getUserPosts.getUserPostData(userId);
        for (int uid : getUserPosts.getPostUserID()) {
            Assert.assertEquals(uid, userId, "Post userId mismatch");
        }
        test.pass("All posts correctly belong to userId=" + userId);
    }
}
