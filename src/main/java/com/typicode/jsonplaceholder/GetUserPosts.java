package com.typicode.jsonplaceholder;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utils.TestData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetUserPosts {


    public GetUserPosts() {

    }


    JsonPath jsonResponse;

    public ArrayList<Integer> list_PostId;
    public ArrayList<Integer> list_userId;
    public ArrayList<String> list_title;
    public ArrayList<String> list_body;

    static TestData testData;





    //Http client for base api and storing the fields & data as an arrayList

    public void getUserPostData(int userID) throws IOException {

        testData = new TestData();

        RestAssured.baseURI = testData.properties.getProperty("baseUrl");
        RestAssured.basePath = "/posts?userId="+userID;


        jsonResponse = new JsonPath(RestAssured.given()

                .contentType("application/json")
                .queryParam("userId",userID)
                .log()
                .all()
                .when()
                .get()
                .asString());

        jsonResponse.prettyPrint();

        list_PostId = new ArrayList<>(jsonResponse.getList("id"));
        list_userId = new ArrayList<>(jsonResponse.getList("userId"));
        list_title = new ArrayList<>(jsonResponse.getList("title"));
        list_body = new ArrayList<>(jsonResponse.getList("body"));


    }



    public void getUserPostData_WithStatusCode(int userID, int StatusCode) throws IOException {

        testData = new TestData();

        RestAssured.baseURI = testData.properties.getProperty("baseUrl");
        RestAssured.basePath = "/posts/"+userID;
        RestAssured.given()
                .then()
                .log()
                .all()
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(StatusCode);

    }


    public ArrayList returnPostIDList(){
        System.out.println("The list of postID is:"+list_PostId);
        return list_PostId;
    }


    public ArrayList getPostTitle() {
        return list_title;
    }

    public ArrayList getPostBody() {
        return list_body;
    }

    public ArrayList getPostUserID() {
        return list_userId;
    }

}
