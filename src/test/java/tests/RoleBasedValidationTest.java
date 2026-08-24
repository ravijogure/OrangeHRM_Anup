package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.ConfigReader;

public class RoleBasedValidationTest extends BaseTest {

    @Test
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
        // 2. VERIFY ADMIN ROLE ACCESS
        // ==========================================

        String currentUrl = driver.getCurrentUrl();

        System.out.println("Current URL after login: " + currentUrl);

        Assert.assertTrue(
                currentUrl.contains("/dashboard"),
                "User was not redirected to Dashboard after login"
        );

        // ==========================================
        // 3. VERIFY ADMIN MENU ACCESS
        // ==========================================

        boolean adminMenuDisplayed = driver.findElements(
                org.openqa.selenium.By.xpath(
                        "//span[normalize-space()='Admin']"
                )
        ).size() > 0;

        Assert.assertTrue(
                adminMenuDisplayed,
                "Admin menu is not displayed for the logged-in user"
        );

        System.out.println(
                "Admin role validation successful"
        );
    }
}