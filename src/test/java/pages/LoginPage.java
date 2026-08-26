package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    // ==========================================
    // Locators
    // ==========================================

    private By username =
            By.name("username");

    private By password =
            By.name("password");

    private By loginButton =
            By.xpath("//button[@type='submit']");

    // ==========================================
    // Constructor
    // ==========================================

    public LoginPage(WebDriver driver) {

        this.driver = driver;

        this.wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(30)
                );
    }

    // ==========================================
    // Enter Username
    // ==========================================

    public void enterUsername(String usernameValue) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        username
                )
        ).clear();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        username
                )
        ).sendKeys(usernameValue);
    }

    // ==========================================
    // Enter Password
    // ==========================================

    public void enterPassword(String passwordValue) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        password
                )
        ).clear();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        password
                )
        ).sendKeys(passwordValue);
    }

    // ==========================================
    // Click Login
    // ==========================================

    public void clickLogin() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        loginButton
                )
        ).click();
    }

    // ==========================================
    // Login
    // ==========================================

    public void login(
            String usernameValue,
            String passwordValue) {

        enterUsername(usernameValue);

        enterPassword(passwordValue);

        clickLogin();
    }
}