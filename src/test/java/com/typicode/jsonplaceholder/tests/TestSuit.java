package com.typicode.jsonplaceholder.tests;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.typicode.jsonplaceholder.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestSuit extends BaseTestClass{

    public GetUsers getUserDetails = new GetUsers();
    public GetUserPosts getUserPosts = new GetUserPosts();
    public GetPostComments getCommentDetails = new GetPostComments();
    public VerifyEmailFormat verifyEmailFormat = new VerifyEmailFormat();
    public ArrayList<Integer> listOfPostID;


    ExtentTest logger;

    String userName = "Samantha";
    int userID = 0;



    @Test (priority = 0, description = "Verify that the field object is not empty")
    public void VerifyUserDataWorkingProperly() throws IOException {
        logger = extent.startTest("Test started for checking Get User details");
        getUserDetails.getData_WithStatusCode(200);
        logger.log(LogStatus.PASS, "Get user list Api is working properly");
    }

    @Test (priority = 1, description = "Verify that the Data object is not empty")
    public void VerifyUserID() throws IOException {
        logger = extent.startTest("Test started for checking Verifying user ID");

        getUserDetails.getBaseData();

        System.out.println("the ID of user is: "+getUserDetails.retunID(userName));
        userID = getUserDetails.retunID(userName);
        logger.log(LogStatus.PASS, "UserID found");
        assertEquals(userID, 3);
        logger.log(LogStatus.PASS, "Get user Api is working properly");
    }


    @Test (priority = 3, description = "Verify that the Count is not empty")
    public void VerifyUsersPostsAreShowing() throws IOException {
        logger = extent.startTest("Test started for checking User posts with post ID");
        getUserPosts.getUserPostData(userID);
        logger.log(LogStatus.PASS, "Posts of User is showing ");

        listOfPostID = getUserPosts.returnPostIDList();
        Assert.assertNotNull(getUserPosts.returnPostIDList());
        Assert.assertNotNull(getUserPosts.getPostTitle());
        Assert.assertNotNull(getUserPosts.getPostBody());
        Assert.assertNotNull(getUserPosts.getPostTitle());

        logger.log(LogStatus.PASS, "Get user post Api is working properly");
    }

    @Test (priority = 4, description = "Verify that all the filter values are available")
    public void VerifyEmailFormatIsOk() throws IOException {
        logger = extent.startTest("Test started for checking if all the email of comment are accurate");
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i =0 ; i< listOfPostID.size() ; i++){
            getCommentDetails.getPostCommentDetails(listOfPostID.get(i));
            arrayList.addAll(getCommentDetails.getEmail());
        }
        logger.log(LogStatus.PASS, "List of email are collected for all the commenters");

        assertEquals(verifyEmailFormat.verifyEmail(arrayList), true);

        logger.log(LogStatus.PASS, "Email verification for commenters are complete");
    }

}
