package com.typicode.jsonplaceholder.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides a shared RequestSpecification so RestAssured.baseURI / basePath
 * are never set as mutable static globals (which breaks parallel execution).
 */
public class RequestSpecProvider {

    private static final Logger logger = LogManager.getLogger(RequestSpecProvider.class);
    private static final RequestSpecification requestSpec;

    static {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("baseUrl"))
                .setContentType(ContentType.JSON)
                .build();
        logger.info("RequestSpecification initialised with baseUri: {}", ConfigReader.get("baseUrl"));
    }

    private RequestSpecProvider() {}

    public static RequestSpecification get() {
        return requestSpec;
    }
}
