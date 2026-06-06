package com.typicode.jsonplaceholder;

import com.typicode.jsonplaceholder.utils.RequestSpecProvider;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GetUserPosts {

    private static final Logger logger = LogManager.getLogger(GetUserPosts.class);

    private JsonPath jsonResponse;

    public List<Integer> list_PostId;
    public List<Integer> list_userId;
    public List<String> list_title;
    public List<String> list_body;

    public void getUserPostData(int userID) {
        String path = "/posts";
        logger.info("Fetching posts for userId={}", userID);
        jsonResponse = new JsonPath(
                RestAssured.given()
                        .spec(RequestSpecProvider.get())
                        .basePath(path)
                        .queryParam("userId", userID)
                        .log().all()
                        .when()
                        .get()
                        .asString()
        );

        list_PostId = new ArrayList<>(jsonResponse.getList("id"));
        list_userId = new ArrayList<>(jsonResponse.getList("userId"));
        list_title  = new ArrayList<>(jsonResponse.getList("title"));
        list_body   = new ArrayList<>(jsonResponse.getList("body"));

        logger.info("Retrieved {} posts for userId={}", list_PostId.size(), userID);
    }

    public void getUserPostData_WithStatusCode(int userID, int statusCode) {
        logger.info("Asserting status code {} for userId={}", statusCode, userID);
        RestAssured.given()
                .spec(RequestSpecProvider.get())
                .basePath("/posts/" + userID)
                .log().all()
                .when()
                .get()
                .then()
                .log().all()
                .assertThat()
                .statusCode(statusCode);
    }

    public List<Integer> returnPostIDList() {
        logger.info("Post ID list: {}", list_PostId);
        return list_PostId;
    }

    public List<String> getPostTitle() { return list_title; }
    public List<String> getPostBody()  { return list_body; }
    public List<Integer> getPostUserID() { return list_userId; }
}
