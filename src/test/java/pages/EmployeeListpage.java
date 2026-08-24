package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeeListpage {

    WebDriver driver;

    // Locators
    private By employeeId =
            By.xpath("//label[normalize-space()='Employee Id']/following::input[1]");

    private By searchButton =
            By.xpath("//button[normalize-space()='Search']");

    private By employeeTable =
            By.xpath("//div[contains(@class,'oxd-table-body')]");

    private By loadingSpinner =
            By.xpath("//div[contains(@class,'oxd-loading-spinner')]");

    // Constructor
    public EmployeeListpage(WebDriver driver) {
        this.driver = driver;
    }

    // ==========================================
    // SEARCH EMPLOYEE BY ID
    // ==========================================

    public void searchEmployeeById(String empId) {

        By employeeResult = By.xpath(
                "//div[contains(@class,'oxd-table-body')]"
                + "//div[contains(@class,'oxd-table-cell')]"
                + "[normalize-space()='" + empId + "']"
        );

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            try {

                // Wait for Employee ID field
                WebElement idField = wait.until(
                        ExpectedConditions.elementToBeClickable(employeeId)
                );

                idField.clear();
                idField.sendKeys(empId);

                // Click Search
                WebElement search =
                        wait.until(
                                ExpectedConditions.elementToBeClickable(searchButton)
                        );

                search.click();

                // Wait for spinner to disappear
                try {
                    wait.until(
                            ExpectedConditions.invisibilityOfElementLocated(
                                    loadingSpinner
                            )
                    );
                } catch (Exception ignored) {
                }

                // Wait for table
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                employeeTable
                        )
                );

                // Give UI a chance to update the result
                Thread.sleep(1000);

                // Check employee
                if (!driver.findElements(employeeResult).isEmpty()) {

                    WebElement employee =
                            driver.findElement(employeeResult);

                    if (employee.isDisplayed()) {

                        System.out.println(
                                "Employee found successfully with ID: "
                                        + empId
                        );

                        return;
                    }
                }

            } catch (Exception e) {

                System.out.println(
                        "Search attempt "
                                + attempt
                                + " failed for ID: "
                                + empId
                );
            }

            // Retry delay
            if (attempt < maxAttempts) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println(
                        "Retrying employee search..."
                );
            }
        }

        throw new RuntimeException(
                "Employee "
                        + empId
                        + " not found after "
                        + maxAttempts
                        + " search attempts"
        );
    }

    // ==========================================
    // VERIFY EMPLOYEE ID
    // ==========================================

    public boolean isEmployeeDisplayed(String empId) {

        By employeeResult = By.xpath(
                "//div[contains(@class,'oxd-table-body')]"
                + "//div[contains(@class,'oxd-table-cell')]"
                + "[normalize-space()='" + empId + "']"
        );

        try {

            WebDriverWait wait =
                    new WebDriverWait(driver, Duration.ofSeconds(15));

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
    // OPEN EMPLOYEE
    // ==========================================

    public void openEmployee(String empId) {

        By employeeResult = By.xpath(
                "//div[contains(@class,'oxd-table-body')]"
                + "//div[contains(@class,'oxd-table-cell')]"
                + "[normalize-space()='" + empId + "']"
        );

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement employee =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                employeeResult
                        )
                );

        employee.click();
    }

    // ==========================================
    // VERIFY EMPLOYEE NAME
    // ==========================================

    public boolean isEmployeeNameDisplayed(String firstName) {

        By employeeResult = By.xpath(
                "//div[contains(@class,'oxd-table-body')]"
                + "//div[contains(@class,'oxd-table-cell')]"
                + "[contains(normalize-space(),'"
                + firstName
                + "')]"
        );

        try {

            WebDriverWait wait =
                    new WebDriverWait(driver, Duration.ofSeconds(15));

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
    // DELETE EMPLOYEE
    // ==========================================

    public void deleteEmployee(String empId) {

        By deleteButton = By.xpath(
                "//div[contains(@class,'oxd-table-row')]"
                + "[.//div[contains(@class,'oxd-table-cell') "
                + "and normalize-space()='"
                + empId
                + "']]"
                + "//button[contains(@class,"
                + "'oxd-table-cell-action-space')][2]"
        );

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        // Click Delete icon
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        deleteButton
                )
        ).click();

        // Confirmation popup
        By confirmDeleteButton = By.xpath(
                "//button[contains(normalize-space(),'Delete')]"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        confirmDeleteButton
                )
        ).click();

        // Wait until delete popup disappears
        try {

            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            confirmDeleteButton
                    )
            );

        } catch (Exception ignored) {
        }
    }
}