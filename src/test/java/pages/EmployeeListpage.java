package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeeListpage {

    WebDriver driver;

    // ==========================================
    // Locators
    // ==========================================

    private By employeeId =
            By.xpath("//label[normalize-space()='Employee Id']/following::input[1]");

    private By searchButton =
            By.xpath("//button[normalize-space()='Search']");

    private By employeeTable =
            By.xpath("//div[contains(@class,'oxd-table-body')]");

    // ==========================================
    // Constructor
    // ==========================================

    public EmployeeListpage(WebDriver driver) {
        this.driver = driver;
    }

    // ==========================================
    // Search Employee by ID
    // ==========================================

    public void searchEmployeeById(String empId) {

        By employeeResult = By.xpath(
                "//div[contains(@class,'oxd-table-row')]"
                + "[.//div[contains(@class,'oxd-table-cell') "
                + "and normalize-space()='" + empId + "']]"
        );

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(30));

        int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            try {

                System.out.println(
                        "Searching employee ID: " + empId
                        + " | Attempt: " + attempt
                );

                // Wait for Employee ID input
                WebElement idField = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                employeeId
                        )
                );

                // Clear and enter Employee ID
                idField.click();
                idField.clear();
                idField.sendKeys(empId);

                // Click Search
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                searchButton
                        )
                ).click();

                // Wait for table body
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                employeeTable
                        )
                );

                // Wait for employee result
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                employeeResult
                        )
                );

                System.out.println(
                        "Employee found successfully with ID: "
                        + empId
                );

                return;

            } catch (Exception e) {

                System.out.println(
                        "Search attempt " + attempt
                        + " failed for employee ID: "
                        + empId
                );

                if (attempt < maxAttempts) {

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }

        throw new RuntimeException(
                "Employee " + empId
                + " not found after "
                + maxAttempts
                + " search attempts"
        );
    }
    // ==========================================
    // Verify Employee ID is displayed
    // ==========================================

    public boolean isEmployeeDisplayed(String empId) {

        By employeeResult = By.xpath(
                "//div[contains(@class,'oxd-table-body')]"
                + "//div[contains(@class,'oxd-table-cell')]"
                + "[normalize-space()='" + empId + "']"
        );

        try {

            WebDriverWait wait =
                    new WebDriverWait(driver, Duration.ofSeconds(10));

            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            employeeResult
                    )
            ).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    // ==========================================
    // Open Employee
    // ==========================================

    public void openEmployee(String empId) {

        By employeeResult = By.xpath(
                "//div[contains(@class,'oxd-table-body')]"
                + "//div[contains(@class,'oxd-table-cell')]"
                + "[normalize-space()='" + empId + "']"
        );

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        employeeResult
                )
        ).click();
    }

    // ==========================================
    // Verify Employee Name is displayed
    // ==========================================

    public boolean isEmployeeNameDisplayed(String firstName) {

        By employeeResult = By.xpath(
                "//div[contains(@class,'oxd-table-body')]"
                + "//div[contains(@class,'oxd-table-cell')]"
                + "[contains(normalize-space(),'" + firstName + "')]"
        );

        try {

            WebDriverWait wait =
                    new WebDriverWait(driver, Duration.ofSeconds(10));

            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            employeeResult
                    )
            ).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    // ==========================================
    // Delete Employee
    // ==========================================

    public void deleteEmployee(String empId) {

        By deleteButton = By.xpath(
                "//div[contains(@class,'oxd-table-row')]"
                + "[.//div[contains(@class,'oxd-table-cell') "
                + "and normalize-space()='" + empId + "']]"
                + "//button[contains(@class,'oxd-table-cell-action-space')][2]"
        );

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click Delete icon
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        deleteButton
                )
        ).click();

        // Confirmation Delete button
        By confirmDeleteButton = By.xpath(
                "//button[contains(normalize-space(),'Delete')]"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        confirmDeleteButton
                )
        ).click();
    }
}