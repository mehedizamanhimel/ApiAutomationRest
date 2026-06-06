package com.typicode.jsonplaceholder.tests;

import com.aventstack.extentreports.ExtentTest;
import com.typicode.jsonplaceholder.GetUsers;
import com.typicode.jsonplaceholder.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UsersApiTest extends BaseTestClass {

    private GetUsers getUsers;
    private int resolvedUserId;

    @BeforeClass
    public void init() {
        getUsers = new GetUsers();
    }

    @Test(priority = 0, description = "GET /users returns HTTP 200")
    public void verifyGetUsersStatusCode() {
        ExtentTest test = extent.createTest("verifyGetUsersStatusCode", "GET /users returns HTTP 200");
        extentTest.set(test);
        getUsers.getData_WithStatusCode(200);
        test.pass("GET /users returned 200 OK");
        logger.info("GET /users status code verified.");
    }

    @Test(priority = 1, description = "GET /users returns a non-empty user list")
    public void verifyUserListIsNotEmpty() {
        ExtentTest test = extent.createTest("verifyUserListIsNotEmpty", "User list should not be empty");
        extentTest.set(test);
        getUsers.getBaseData();
        Assert.assertFalse(getUsers.name.isEmpty(), "User list should not be empty");
        Assert.assertFalse(getUsers.email.isEmpty(), "Email list should not be empty");
        test.pass("User list contains " + getUsers.name.size() + " users");
    }

    @Test(priority = 2, description = "Verify user ID lookup by username")
    public void verifyUserIdByUsername() {
        ExtentTest test = extent.createTest("verifyUserIdByUsername", "Lookup user ID by username");
        extentTest.set(test);
        getUsers.getBaseData();
        String userName = ConfigReader.get("test.userName");
        int expectedId  = Integer.parseInt(ConfigReader.get("test.expectedUserId"));
        resolvedUserId  = getUsers.returnID(userName);
        logger.info("Resolved userId for '{}': {}", userName, resolvedUserId);
        Assert.assertEquals(resolvedUserId, expectedId,
                "Expected userId " + expectedId + " for user " + userName);
        test.pass("User '" + userName + "' resolved to ID " + resolvedUserId);
    }
}
