package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    // Locators
    private By username = By.name("username");
    private By password = By.name("password");
    private By loginButton = By.xpath("//button[@type='submit']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterUsername(String usernameValue) {
        driver.findElement(username).sendKeys(usernameValue);
    }

    public void enterPassword(String passwordValue) {
        driver.findElement(password).sendKeys(passwordValue);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void login(String usernameValue, String passwordValue) {
        enterUsername(usernameValue);
        enterPassword(passwordValue);
        clickLogin();
    }
}