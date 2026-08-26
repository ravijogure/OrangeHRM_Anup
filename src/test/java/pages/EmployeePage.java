package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;


public class EmployeePage {

    WebDriver driver;

    // Locators
    private By firstName = By.xpath("//input[@placeholder='First Name']");
    private By middleName = By.xpath("//input[@placeholder='Middle Name']");
    private By lastName = By.xpath("//input[@placeholder='Last Name']");
    private By employeeId =
            By.xpath("//label[normalize-space()='Employee Id']/parent::div/following-sibling::div//input");
    private By addEmployeeButton = By.xpath("//a[normalize-space()='Add Employee']");
    private By saveButton =
            By.xpath("//button[normalize-space()='Save']");
    private By employeeIdField =
            By.xpath("//label[normalize-space()='Employee Id']/following::input[1]");

    // Constructor
    public EmployeePage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterFirstName(String value) {
        driver.findElement(firstName).sendKeys(value);
    }

    public void enterMiddleName(String value) {
        driver.findElement(middleName).sendKeys(value);
    }

    public void enterLastName(String value) {
        driver.findElement(lastName).sendKeys(value);
    }

    public void enterEmployeeId(String value) {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.cssSelector(".oxd-form-loader")
                )
        );

        WebElement element =
                wait.until(
                        ExpectedConditions.elementToBeClickable(employeeId)
                );

        element.click();

        element.sendKeys(Keys.CONTROL, "a");
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(value);
    }
    public void clickAddEmployee() {
        driver.findElement(addEmployeeButton).click();
    }

    public void clickSave() {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement save =
                wait.until(
                        ExpectedConditions.presenceOfElementLocated(saveButton)
                );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        save
                );

        wait.until(
                ExpectedConditions.elementToBeClickable(save)
        );

        save.click();
    }
    public String getEmployeeId() {
        return driver.findElement(employeeIdField).getAttribute("value");
    }

    public String createEmployee(String first, String middle, String last, String empId) {

        enterFirstName(first);
        enterMiddleName(middle);
        enterLastName(last);
        enterEmployeeId(empId);

        System.out.println("Employee ID before Save: " + getEmployeeId());

        clickSave();

      
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeIdField));

        String actualEmpId = getEmployeeId();
        System.out.println("Employee ID after Save: " + actualEmpId);

        return actualEmpId;
    }
    public void updateEmployee(String first, String middle, String last) {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(30));

        // ==========================================
        // Wait for form fields to become available
        // ==========================================

        WebElement firstNameElement =
                wait.until(
                        ExpectedConditions.elementToBeClickable(firstName)
                );

        // ==========================================
        // First Name
        // ==========================================

        firstNameElement.click();

        firstNameElement.sendKeys(Keys.CONTROL, "a");
        firstNameElement.sendKeys(Keys.BACK_SPACE);
        firstNameElement.sendKeys(first);

        // ==========================================
        // Middle Name
        // ==========================================

        WebElement middleNameElement =
                wait.until(
                        ExpectedConditions.elementToBeClickable(middleName)
                );

        middleNameElement.click();

        middleNameElement.sendKeys(Keys.CONTROL, "a");
        middleNameElement.sendKeys(Keys.BACK_SPACE);
        middleNameElement.sendKeys(middle);

        // ==========================================
        // Last Name
        // ==========================================

        WebElement lastNameElement =
                wait.until(
                        ExpectedConditions.elementToBeClickable(lastName)
                );

        lastNameElement.click();

        lastNameElement.sendKeys(Keys.CONTROL, "a");
        lastNameElement.sendKeys(Keys.BACK_SPACE);
        lastNameElement.sendKeys(last);

        // ==========================================
        // Save
        // ==========================================

        WebElement save =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                saveButton
                        )
                );

        save.click();

        // ==========================================
        // Wait for Save operation to complete
        // ==========================================

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.cssSelector(".oxd-form-loader")
                )
        );

        System.out.println(
                "Employee updated successfully"
        );
    }
}