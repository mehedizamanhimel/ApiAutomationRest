package com.typicode.jsonplaceholder.tests;

import com.aventstack.extentreports.ExtentTest;
import com.typicode.jsonplaceholder.GetPostComments;
import com.typicode.jsonplaceholder.GetUserPosts;
import com.typicode.jsonplaceholder.utils.ConfigReader;
import com.typicode.jsonplaceholder.utils.EmailValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class PostCommentsApiTest extends BaseTestClass {

    private GetUserPosts getUserPosts;
    private GetPostComments getPostComments;
    private List<Integer> postIds;

    @BeforeClass
    public void init() {
        getUserPosts   = new GetUserPosts();
        getPostComments = new GetPostComments();
        int userId = Integer.parseInt(ConfigReader.get("test.postUserId"));
        getUserPosts.getUserPostData(userId);
        postIds = getUserPosts.returnPostIDList();
    }

    @Test(priority = 0, description = "GET /posts/{postId}/comments returns HTTP 200")
    public void verifyCommentsStatusCode() {
        ExtentTest test = extent.createTest("verifyCommentsStatusCode", "Comments endpoint returns 200");
        extentTest.set(test);
        getPostComments.getPostCommentDetails_WithStatusCode(postIds.get(0), 200);
        test.pass("GET /posts/" + postIds.get(0) + "/comments returned 200 OK");
    }

    @Test(priority = 1, description = "Comment fields are not null or empty")
    public void verifyCommentFieldsNotEmpty() {
        ExtentTest test = extent.createTest("verifyCommentFieldsNotEmpty", "Comment fields should be populated");
        extentTest.set(test);
        getPostComments.getPostCommentDetails(postIds.get(0));
        Assert.assertFalse(getPostComments.getName().isEmpty(),  "Comment names should not be empty");
        Assert.assertFalse(getPostComments.getEmail().isEmpty(), "Comment emails should not be empty");
        Assert.assertFalse(getPostComments.getBody().isEmpty(),  "Comment bodies should not be empty");
        test.pass("Comment fields validated for postId=" + postIds.get(0));
    }

    @Test(priority = 2, description = "All commenter emails are in valid format")
    public void verifyAllCommentEmailsAreValid() {
        ExtentTest test = extent.createTest("verifyAllCommentEmailsAreValid",
                "All comment emails must be RFC-valid");
        extentTest.set(test);
        List<String> allEmails = new ArrayList<>();
        for (int postId : postIds) {
            getPostComments.getPostCommentDetails(postId);
            allEmails.addAll(getPostComments.getEmail());
        }
        logger.info("Total emails collected: {}", allEmails.size());
        boolean allValid = EmailValidator.validateAll(allEmails);
        Assert.assertTrue(allValid, "One or more comment emails failed validation");
        test.pass("All " + allEmails.size() + " emails passed format validation");
    }
}
