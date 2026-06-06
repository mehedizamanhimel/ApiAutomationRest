package com.typicode.jsonplaceholder.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTestClass {

    protected static final Logger logger = LogManager.getLogger(BaseTestClass.class);
    protected static ExtentReports extent;
    protected static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @BeforeSuite
    public void beforeSuite() {
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("API Automation Report");
        sparkReporter.config().setReportName("JSONPlaceholder API Tests");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Tester", "Md. Mehedi Zaman Himel");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("API", "https://jsonplaceholder.typicode.com");
        logger.info("ExtentReports initialised at: {}", reportPath);
    }

    @AfterSuite
    public void afterSuite() {
        if (extent != null) {
            extent.flush();
            logger.info("ExtentReports flushed.");
        }
    }
}
