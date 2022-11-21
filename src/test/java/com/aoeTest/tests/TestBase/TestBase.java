package com.aoeTest.tests.TestBase;

import com.aoeTest.utilities.BrowserUtils;
import com.aoeTest.utilities.ConfigurationReader;
import com.aoeTest.utilities.WebDriverFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestBase {

    protected WebDriver driver;
    protected Actions actions;
    protected WebDriverWait wait;
    protected ExtentReports report;
    //this class is used to create HTML report file
    protected ExtentHtmlReporter htmlReporter;
    //this will define a test enables adding logs, authors, test steps
    protected ExtentTest extendlogger;
    //BrowserUtils'e driver'in gitmesi lazim
    BrowserUtils browserUtils;

    @BeforeTest
    public void setUpTest() {
        //initialize the class
        report = new ExtentReports();

        //create a report path
        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "//test-output//report.html";

        //initialize the html reporter with the report path
        htmlReporter = new ExtentHtmlReporter(path);

        //attach the html report to report object
        report.attachReporter(htmlReporter);

        //title in report
        htmlReporter.config().setReportName("Smoke Test");

        //set environment information
        report.setSystemInfo("Environment", "QA");
        report.setSystemInfo("Browser", ConfigurationReader.get("browser"));
        report.setSystemInfo("OS", System.getProperty("os.name"));
        report.setSystemInfo("Test Engineer", "Halil");
    }

    @BeforeMethod
    public void setup() {
        driver = WebDriverFactory.getDriver(ConfigurationReader.get("browser"));
        browserUtils=new BrowserUtils(driver);
        driver.get(ConfigurationReader.get("url"));
        //actions = new Actions(driver);
        //BrowserUtils'e driver'in gitmesi lazim
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


    }

    // ITestResult class describes the result of a test in TestNG
    @AfterMethod
    public void tearDown(ITestResult result) throws InterruptedException, IOException {
        // If test fails
        //BrowserUtils browserUtils=new BrowserUtils(driver);

        if (result.getStatus() == ITestResult.FAILURE) {
            //record the name of failed case
            extendlogger.fail(result.getName());
            //take the screenshot
            String screenShotPath = browserUtils.getScreenshot(result.getName());
            //add your screenshot to your report
            extendlogger.addScreenCaptureFromPath(screenShotPath);
            //capture the exception and put inside the report
            extendlogger.fail(result.getThrowable());
        }
        Thread.sleep(3000);
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @AfterTest
    public void tearDownTest() {
        //this is when the report is actually created
        report.flush();
    }


}
