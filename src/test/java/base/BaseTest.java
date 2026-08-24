package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.ConfigReader;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {

        // ==========================================
        // Chrome Options
        // ==========================================

        ChromeOptions options = new ChromeOptions();

        // GitHub Actions / CI environment
        if (System.getenv("CI") != null) {

            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }

        // ==========================================
        // Launch Chrome
        // ==========================================

        driver = new ChromeDriver(options);

        // ==========================================
        // Browser Settings
        // ==========================================

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(30));

        // Maximize only when running locally
        if (System.getenv("CI") == null) {
            driver.manage().window().maximize();
        }

        // ==========================================
        // Open OrangeHRM
        // ==========================================

        driver.get(
                ConfigReader.getProperty("url")
        );
    }

    // ==========================================
    // Close Browser
    // ==========================================

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}