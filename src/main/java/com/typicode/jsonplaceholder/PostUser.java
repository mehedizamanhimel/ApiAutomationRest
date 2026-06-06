package com.typicode.jsonplaceholder;

import com.google.gson.JsonObject;
import com.typicode.jsonplaceholder.utils.RequestSpecProvider;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PostUser {

    private static final Logger logger = LogManager.getLogger(PostUser.class);
    private static final String PATH = "/posts";

    private JsonPath jsonResponse;

    public void postFromUser(String title, String body, int userId) {
        logger.info("POSTing to {} with title='{}', userId={}", PATH, title, userId);
        JsonObject json = new JsonObject();
        json.addProperty("title", title);
        json.addProperty("body", body);
        json.addProperty("userId", userId);

        jsonResponse = new JsonPath(
                RestAssured.given()
                        .spec(RequestSpecProvider.get())
                        .basePath(PATH)
                        .header("Accept-Language", "en")
                        .body(json.toString())
                        .log().all()
                        .when()
                        .post()
                        .asString()
        );
    }

    public void postFromUser_WithStatusCode(String title, String body, int userId, int statusCode) {
        logger.info("Asserting POST status code {} for userId={}", statusCode, userId);
        JsonObject json = new JsonObject();
        json.addProperty("title", title);
        json.addProperty("body", body);
        json.addProperty("userId", userId);

        RestAssured.given()
                .spec(RequestSpecProvider.get())
                .basePath(PATH)
                .header("Accept-Language", "en")
                .body(json.toString())
                .log().all()
                .when()
                .post()
                .then()
                .log().all()
                .assertThat()
                .statusCode(statusCode);
    }

    public int getPostID()     { return Integer.parseInt(jsonResponse.getString("id")); }
    public String getPostTitle()  { return jsonResponse.getString("title"); }
    public String getPostBody()   { return jsonResponse.getString("body"); }
    public int getPostUserID() { return Integer.parseInt(jsonResponse.getString("userId")); }
}
