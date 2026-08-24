package base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.ConfigReader;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

        driver.get(
                ConfigReader.getProperty("url")
        );
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        // ==========================================
        // TAKE SCREENSHOT WHEN TEST FAILS
        // ==========================================

        if (result.getStatus() == ITestResult.FAILURE) {

            takeScreenshot(
                    result.getMethod()
                            .getMethodName()
            );
        }

        // ==========================================
        // CLOSE BROWSER
        // ==========================================

        if (driver != null) {
            driver.quit();
        }
    }

    // ==========================================
    // SCREENSHOT METHOD
    // ==========================================

    private void takeScreenshot(String testName) {

        try {

            File screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(
                                    OutputType.FILE
                            );

            File screenshotFolder =
                    new File("screenshots");

            if (!screenshotFolder.exists()) {
                screenshotFolder.mkdirs();
            }

            File destination =
                    new File(
                            screenshotFolder,
                            testName + "_failure.png"
                    );

            Files.copy(
                    screenshot.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println(
                    "Failure screenshot saved at: "
                            + destination.getAbsolutePath()
            );

        } catch (IOException e) {

            System.out.println(
                    "Unable to capture failure screenshot: "
                            + e.getMessage()
            );
        }
    }
}