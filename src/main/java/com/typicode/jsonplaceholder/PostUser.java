package com.typicode.jsonplaceholder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utils.TestData;

import java.io.IOException;

public class PostUser {


    public PostUser() {

    }

    static TestData testData;

    JsonObject json = new JsonObject();
    JsonArray jArray = new JsonArray();
    JsonPath jsonResponse;

    public void Post_From_User(String title, String body, int userId) throws IOException {
        testData = new TestData();

        RestAssured.baseURI = testData.properties.getProperty("baseUrl");
        RestAssured.basePath = "/posts" ;

        json.addProperty("title", title);
        json.addProperty("body", body);
        json.addProperty("userId", userId);

        jsonResponse = new JsonPath(RestAssured
                .given()
                .contentType("application/json; charset=UTF-8")
                .header("Accept-Language", "en")
                .body(json.toString())
                .log()
                .all()
                .when()
                .post()
                .asString());

        jsonResponse.prettyPrint();

    }

    public void Post_From_User_statusCode(String title, String body, int userId, int statusCode) throws IOException {
        testData = new TestData();

        RestAssured.baseURI = testData.properties.getProperty("baseUrl");
        RestAssured.basePath = "/posts";

        json.addProperty("title", title);
        json.addProperty("body", body);
        json.addProperty("userId", userId);

        RestAssured.given()
                .contentType("application/json; charset=UTF-8")
                .header("Accept-Language", "en")
                .body(json.toString())
                .log()
                .all()
                .when()
                .post()
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(statusCode);

    }






    public int getPostID() {
        return Integer.parseInt(jsonResponse.getString("id"));
    }

    public String getPostTitle() {
        return jsonResponse.getString("title");
    }

    public String getPostBody() {
        return jsonResponse.getString("body");
    }

    public int getPostUserID() {
        return Integer.parseInt(jsonResponse.getString("userId"));
    }


}
