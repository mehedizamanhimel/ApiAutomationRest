package com.typicode.jsonplaceholder.tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;

public class BaseTestClass {
    ExtentReports extent;
    ExtentTest logger;

    @BeforeTest
    public void beforeTest(){
        extent = new ExtentReports(System.getProperty("user.dir") +"/test-output/testReport.html", true);
        extent.loadConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));

        extent
                .addSystemInfo("Host Name", "FreeNow Api Automation Test")
                .addSystemInfo("Environment", "Extent Report FreeNow Api")
                .addSystemInfo("User Name", "Md. Mehedi Zaman Himel");

    }

    @AfterTest
    public void afterTest(){
        extent.endTest(logger);
        extent.flush();
    }
}
