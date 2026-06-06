package com.typicode.jsonplaceholder.tests;

import com.aventstack.extentreports.ExtentTest;
import com.typicode.jsonplaceholder.PostUser;
import com.typicode.jsonplaceholder.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PostUserApiTest extends BaseTestClass {

    private PostUser postUser;
    private String title;
    private String body;
    private int userId;

    @BeforeClass
    public void init() {
        postUser = new PostUser();
        title    = ConfigReader.get("test.postTitle");
        body     = ConfigReader.get("test.postBody");
        userId   = Integer.parseInt(ConfigReader.get("test.postUserId"));
    }

    @Test(priority = 0, description = "POST /posts returns HTTP 201")
    public void verifyPostReturns201() {
        ExtentTest test = extent.createTest("verifyPostReturns201", "POST /posts should return 201 Created");
        extentTest.set(test);
        postUser.postFromUser_WithStatusCode(title, body, userId, 201);
        test.pass("POST /posts returned 201 Created");
    }

    @Test(priority = 1, description = "POST /posts response body reflects submitted data")
    public void verifyPostResponseBody() {
        ExtentTest test = extent.createTest("verifyPostResponseBody", "Response should echo submitted fields");
        extentTest.set(test);
        postUser.postFromUser(title, body, userId);
        Assert.assertEquals(postUser.getPostTitle(), title,    "Title mismatch in POST response");
        Assert.assertEquals(postUser.getPostBody(),  body,     "Body mismatch in POST response");
        Assert.assertEquals(postUser.getPostUserID(), userId,  "UserId mismatch in POST response");
        Assert.assertTrue(postUser.getPostID() > 0,            "Post ID should be a positive integer");
        test.pass("POST response body validated. New postId=" + postUser.getPostID());
        logger.info("Created post with ID: {}", postUser.getPostID());
    }
}
