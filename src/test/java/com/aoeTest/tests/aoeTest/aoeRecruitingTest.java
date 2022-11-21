package com.aoeTest.tests.aoeTest;

import com.aoeTest.pages.AOETestPage;
import com.aoeTest.tests.TestBase.TestBase;
import com.aoeTest.utilities.BrowserUtils;
import com.aoeTest.utilities.ConfigurationReader;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Driver;

public class aoeRecruitingTest extends TestBase {

    AOETestPage aoeTestPage;

    @Test
    public void recruiterTest() throws InterruptedException {
        aoeTestPage = new AOETestPage(driver);
        extendlogger = report.createTest("AOE Recruiter Test");

        extendlogger.info("Accept button clicked");
        aoeTestPage.cookieBtn.click();

        WebDriverWait wait = new WebDriverWait(driver,15);
        wait.until(ExpectedConditions.elementToBeClickable(aoeTestPage.careerBtn));

        BrowserUtils.hover (aoeTestPage.careerBtn);

        extendlogger.info("Navigated to the ‘Vacancies’ page ");
        aoeTestPage.vacanciesBtn.click();

        extendlogger.info("‘frontend’ as keyword filtered ");
        aoeTestPage.keywordBtn.sendKeys(ConfigurationReader.get("keyword"));

        // System.out.println("aoeTestPage.offeringList().get(0) = " + aoeTestPage.offeringList().get(0));

        extendlogger.info("Filtered Frontend search verified");
        Assert.assertTrue(aoeTestPage.listOfVacanciesFiltered().get(0).getText().contains(ConfigurationReader.get("keyword")));

        extendlogger.info("Clicked the first entry which is displayed.");
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        //executor.executeScript("arguments[0].click();",aoeTestPage.listOfVacanciesFiltered().get(0).getText());
        executor.executeScript("arguments[0].click();",aoeTestPage.offering(aoeTestPage.listOfVacanciesFiltered().get(0).getText()));

        extendlogger.info("Content of this job offering is being displayed");

        String expectedText="Frontend / React Developer (m/f/d), remote possible (headquarters in Wiesbaden)";

        String actualText=aoeTestPage.verifyOption1(ConfigurationReader.get("keyword")).getText().trim();
        Assert.assertTrue(actualText.contains(expectedText));

    }
}