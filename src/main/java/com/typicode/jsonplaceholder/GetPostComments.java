package com.typicode.jsonplaceholder;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utils.TestData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetPostComments {


    public GetPostComments() {

    }


    JsonPath jsonResponse;

    static TestData testData;

    //public ArrayList<Integer> postID;
    public ArrayList<Integer> user;
    public ArrayList<String> name;
    public ArrayList<String> email;
    public ArrayList<String> body;

    //Http client for base api and storing the fields & data as an arrayList

    public void getPostCommentDetails(int postID) throws IOException {
        testData = new TestData();

        RestAssured.baseURI = testData.properties.getProperty("baseUrl");
        RestAssured.basePath = "/posts/"+postID+"/comments";

        jsonResponse = new JsonPath(RestAssured.given()

                .contentType("application/json")
                .log()
                .all()
                .when()
                .get()
                .asString());

        jsonResponse.prettyPrint();

        //postID = new ArrayList<>(jsonResponse.getList("postId"));
        user = new ArrayList<>(jsonResponse.getList("id"));
        name = new ArrayList<>(jsonResponse.getList("name"));
        email = new ArrayList<>(jsonResponse.getList("email"));
        body = new ArrayList<>(jsonResponse.getList("body"));


    }



    public void getPostCommentDetails_WithStatusCode(int postID, int StatusCode) throws IOException {

        testData = new TestData();

        RestAssured.baseURI = testData.properties.getProperty("baseUrl");
        RestAssured.basePath = "/posts/"+postID+"/comments";
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

    public Object getData() {

        return jsonResponse.get();
    }

    public int getPostID() {

        return Integer.parseInt(jsonResponse.getString("postId"));
    }

    public int getUserID() {

        return Integer.parseInt(jsonResponse.getString("id"));
    }

    public String getPostTitle() {

        return jsonResponse.getString("name");
    }

    public ArrayList getEmail() {

        return email;
    }

    public boolean verifyEmail(){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        for(String singeEmail : email){
            //Create instance of matcher
            Matcher matcher = pattern.matcher(singeEmail);
            //System.out.println(singeEmail +" : "+ matcher.matches()+"\n");
            return true;
        }

        return false;

    }


    // internet.catalog_id

    // internet.pack_type
    // internet.pack_sub_type internet_details.type internet_details.label internet_details


}
