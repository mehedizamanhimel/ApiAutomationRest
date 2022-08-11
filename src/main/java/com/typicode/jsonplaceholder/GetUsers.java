package com.typicode.jsonplaceholder;



import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utils.TestData;

import java.io.IOException;
import java.util.*;

public class GetUsers {


    public GetUsers() {

    }


    JsonPath jsonResponse;

    static TestData testData;

    public ArrayList<String> website;
    public ArrayList<String> address;
    public ArrayList<String> address_zipcode;
    public ArrayList<String> address_geo;
    public ArrayList<String> address_geolng;
    public ArrayList<String> address_geolat;
    public ArrayList<String> address_suite;
    public ArrayList<String> address_city;
    public ArrayList<String> address_street;
    public ArrayList<String> phone;
    public ArrayList<String> name;
    public ArrayList<String> company;
    public ArrayList<String> company_bs;
    public ArrayList<String> company_catchPhrase;
    public ArrayList<String> company_name;
    public ArrayList<Integer> id;
    public ArrayList<String> email;
    public ArrayList<String> username;
    HashMap<String, String> nameMap ;



    //Http client for base api and storing the fields & data as an arrayList

    public void getBaseData() throws IOException {

        testData = new TestData();

        RestAssured.baseURI = testData.properties.getProperty("baseUrl");
        RestAssured.basePath = "/users";


        jsonResponse = new JsonPath(RestAssured.given()

                .contentType("application/json")
                .log()
                .all()
                .when()
                .get()
                .asString());

        jsonResponse.prettyPrint();

        website = new ArrayList<>(jsonResponse.getList("website"));
        address = new ArrayList<>(jsonResponse.getList("address"));
        address_zipcode = new ArrayList<>(jsonResponse.getList("address.zipcode"));
        address_geo = new ArrayList<>(jsonResponse.getList("address.geo"));
        address_geolng = new ArrayList<>(jsonResponse.getList("address.geo.lng"));
        address_geolat = new ArrayList<>(jsonResponse.getList("address.geo.lat"));
        address_suite = new ArrayList<>(jsonResponse.getList("address.suite"));
        address_city = new ArrayList<>(jsonResponse.getList("address.city"));
        address_street = new ArrayList<>(jsonResponse.getList("address.street"));
        phone = new ArrayList<>(jsonResponse.getList("phone"));
        name = new ArrayList<>(jsonResponse.getList("name"));
        company = new ArrayList<>(jsonResponse.getList("name"));
        company_bs = new ArrayList<>(jsonResponse.getList("company.bs"));
        company_catchPhrase = new ArrayList<>(jsonResponse.getList("company.catchPhrase"));
        company_name = new ArrayList<>(jsonResponse.getList("company.name"));
        id = new ArrayList<>(jsonResponse.getList("id"));
        email = new ArrayList<>(jsonResponse.getList("email"));
        username = new ArrayList<>(jsonResponse.getList("username"));


    }



    public void getData_WithStatusCode(int StatusCode) throws IOException {

        testData = new TestData();

        RestAssured.baseURI = testData.properties.getProperty("baseUrl");
        RestAssured.basePath = "/users";
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

    public String returnName(String fName){
        String finalName="";
        for(int i=0; i<name.size();i++){
            if (name.get(i).contentEquals(fName)){
                finalName=name.get(i);
            }
        }
        return finalName;
    }

    public List<String> retunNameList(){
        return name;
    }


    public int retunID(String userID){
        int finalID = 0;
        for(int i=0; i< username.size(); i++){
            if(username.get(i).contentEquals(userID)){
                finalID = id.get(i);

            }
        }
        return finalID;
    }


}






