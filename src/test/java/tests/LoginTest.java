package tests;

import org.testng.Assert;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.ConfigReader;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import listners.ExtentTestListener;
import org.testng.annotations.Test;

@Listeners(ExtentTestListener.class)
public class LoginTest extends BaseTest {

	@Test(groups = {"smoke", "regression"})
    public void verifyLogin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // Dashboard validation
        Assert.assertTrue(
                driver.findElement(By.xpath("//h6[normalize-space()='Dashboard']")).isDisplayed(),
                "Dashboard was not displayed after login"
        );
    }
}