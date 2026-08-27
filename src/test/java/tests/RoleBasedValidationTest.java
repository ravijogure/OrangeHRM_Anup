package tests;

import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.ConfigReader;
import org.testng.annotations.Listeners;
import listners.ExtentTestListener;

@Listeners(ExtentTestListener.class)

public class RoleBasedValidationTest extends BaseTest {

	@Test(groups = {"smoke", "regression"})
    public void verifyAdminRoleAccess() {

        System.out.println("===== Role Based Validation Test Started =====");

        // ==========================================
        // 1. LOGIN
        // ==========================================

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ==========================================
        // 2. WAIT FOR DASHBOARD
        // ==========================================

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(30));

        boolean dashboardLoaded = wait.until(
                ExpectedConditions.urlContains("/dashboard")
        );

        String currentUrl = driver.getCurrentUrl();

        System.out.println(
                "Current URL after login: " + currentUrl
        );

        Assert.assertTrue(
                dashboardLoaded,
                "User was not redirected to Dashboard after login. Current URL: "
                        + currentUrl
        );

        // ==========================================
        // 3. VERIFY ADMIN MENU ACCESS
        // ==========================================

        By adminMenu =
                By.xpath("//span[normalize-space()='Admin']");

        boolean adminMenuDisplayed = false;

        try {

            adminMenuDisplayed = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            adminMenu
                    )
            ).isDisplayed();

        } catch (Exception e) {

            System.out.println(
                    "Admin menu was not displayed: "
                            + e.getMessage()
            );
        }

        Assert.assertTrue(
                adminMenuDisplayed,
                "Admin menu is not displayed for the logged-in user"
        );

        // ==========================================
        // 4. SUCCESS MESSAGE
        // ==========================================

        System.out.println(
                "Admin role validation successful"
        );
    }
}