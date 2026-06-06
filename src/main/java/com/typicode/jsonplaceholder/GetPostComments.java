package com.typicode.jsonplaceholder;

import com.typicode.jsonplaceholder.utils.RequestSpecProvider;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GetPostComments {

    private static final Logger logger = LogManager.getLogger(GetPostComments.class);

    private JsonPath jsonResponse;

    public List<Integer> user;
    public List<String> name;
    public List<String> email;
    public List<String> body;

    public void getPostCommentDetails(int postID) {
        String path = "/posts/" + postID + "/comments";
        logger.info("Fetching comments for postId={}", postID);
        jsonResponse = new JsonPath(
                RestAssured.given()
                        .spec(RequestSpecProvider.get())
                        .basePath(path)
                        .log().all()
                        .when()
                        .get()
                        .asString()
        );

        user  = new ArrayList<>(jsonResponse.getList("id"));
        name  = new ArrayList<>(jsonResponse.getList("name"));
        email = new ArrayList<>(jsonResponse.getList("email"));
        body  = new ArrayList<>(jsonResponse.getList("body"));
    }

    public void getPostCommentDetails_WithStatusCode(int postID, int statusCode) {
        logger.info("Asserting status code {} for postId={}", statusCode, postID);
        RestAssured.given()
                .spec(RequestSpecProvider.get())
                .basePath("/posts/" + postID + "/comments")
                .log().all()
                .when()
                .get()
                .then()
                .log().all()
                .assertThat()
                .statusCode(statusCode);
    }

    public List<String> getEmail() { return email; }
    public List<String> getName()  { return name; }
    public List<String> getBody()  { return body; }
}
