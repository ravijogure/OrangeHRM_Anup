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

    // ==========================================
    // Setup Browser
    // ==========================================

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        System.out.println("===== BaseTest Setup Started =====");

        ChromeOptions options = new ChromeOptions();

        // GitHub Actions / CI environment
        if (System.getenv("CI") != null) {

            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }

        // Launch Chrome
        driver = new ChromeDriver(options);

        System.out.println("ChromeDriver initialized successfully");

        // Browser settings
        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

        driver.manage()
                .timeouts()
                .pageLoadTimeout(Duration.ofSeconds(30));

        // Maximize only locally
        if (System.getenv("CI") == null) {
            driver.manage().window().maximize();
        }

        // Open application
        String url = ConfigReader.getProperty("url");

        System.out.println("Opening URL: " + url);

        driver.get(url);

        System.out.println("===== BaseTest Setup Completed =====");
    }

    // ==========================================
    // Close Browser
    // ==========================================

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        System.out.println("===== BaseTest TearDown Started =====");

        if (driver != null) {

            driver.quit();

            driver = null;

            System.out.println("Browser closed successfully");
        }

        System.out.println("===== BaseTest TearDown Completed =====");
    }
}