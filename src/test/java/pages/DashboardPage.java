package pages;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    WebDriver driver;

    // Locator
    private By pimMenu = By.xpath("//span[normalize-space()='PIM']");
    
    private By employeeList =
            By.xpath("//a[normalize-space()='Employee List']");

    // Constructor
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    // Action
    public void clickPIM() {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(30));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        pimMenu
                )
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        pimMenu
                )
        ).click();
    }
    public void clickEmployeeList() {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.elementToBeClickable(employeeList)
        ).click();
    }
}