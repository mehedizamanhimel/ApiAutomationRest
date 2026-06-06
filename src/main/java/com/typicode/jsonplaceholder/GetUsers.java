package com.typicode.jsonplaceholder;

import com.typicode.jsonplaceholder.utils.RequestSpecProvider;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GetUsers {

    private static final Logger logger = LogManager.getLogger(GetUsers.class);
    private static final String PATH = "/users";

    private JsonPath jsonResponse;

    public List<String> website;
    public List<String> address_zipcode;
    public List<String> address_geolng;
    public List<String> address_geolat;
    public List<String> address_suite;
    public List<String> address_city;
    public List<String> address_street;
    public List<String> phone;
    public List<String> name;
    public List<String> company;
    public List<String> company_bs;
    public List<String> company_catchPhrase;
    public List<String> company_name;
    public List<Integer> id;
    public List<String> email;
    public List<String> username;

    public void getBaseData() {
        logger.info("Fetching all users from {}", PATH);
        jsonResponse = new JsonPath(
                RestAssured.given()
                        .spec(RequestSpecProvider.get())
                        .basePath(PATH)
                        .log().all()
                        .when()
                        .get()
                        .asString()
        );

        website          = new ArrayList<>(jsonResponse.getList("website"));
        address_zipcode  = new ArrayList<>(jsonResponse.getList("address.zipcode"));
        address_geolng   = new ArrayList<>(jsonResponse.getList("address.geo.lng"));
        address_geolat   = new ArrayList<>(jsonResponse.getList("address.geo.lat"));
        address_suite    = new ArrayList<>(jsonResponse.getList("address.suite"));
        address_city     = new ArrayList<>(jsonResponse.getList("address.city"));
        address_street   = new ArrayList<>(jsonResponse.getList("address.street"));
        phone            = new ArrayList<>(jsonResponse.getList("phone"));
        name             = new ArrayList<>(jsonResponse.getList("name"));
        // Fixed: was incorrectly mapped to "name" — now correctly maps to "company"
        company          = new ArrayList<>(jsonResponse.getList("company"));
        company_bs       = new ArrayList<>(jsonResponse.getList("company.bs"));
        company_catchPhrase = new ArrayList<>(jsonResponse.getList("company.catchPhrase"));
        company_name     = new ArrayList<>(jsonResponse.getList("company.name"));
        id               = new ArrayList<>(jsonResponse.getList("id"));
        email            = new ArrayList<>(jsonResponse.getList("email"));
        username         = new ArrayList<>(jsonResponse.getList("username"));
    }

    public void getData_WithStatusCode(int statusCode) {
        logger.info("Asserting status code {} for {}", statusCode, PATH);
        RestAssured.given()
                .spec(RequestSpecProvider.get())
                .basePath(PATH)
                .log().all()
                .when()
                .get()
                .then()
                .log().all()
                .assertThat()
                .statusCode(statusCode);
    }

    public String returnName(String fName) {
        return name.stream()
                .filter(n -> n.equals(fName))
                .findFirst()
                .orElse("");
    }

    public List<String> returnNameList() {
        return name;
    }

    public int returnID(String userName) {
        for (int i = 0; i < username.size(); i++) {
            if (username.get(i).equals(userName)) {
                return id.get(i);
            }
        }
        return 0;
    }
}
