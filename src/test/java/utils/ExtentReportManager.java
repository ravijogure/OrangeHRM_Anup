package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getReportInstance() {

        if (extent == null) {

            String reportPath =
                    System.getProperty("user.dir")
                    + "/test-output/ExtentReport.html";

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(reportPath);

            sparkReporter.config().setDocumentTitle(
                    "OrangeHRM Automation Report"
            );

            sparkReporter.config().setReportName(
                    "OrangeHRM Test Execution Report"
            );

            extent = new ExtentReports();

            extent.attachReporter(sparkReporter);

            extent.setSystemInfo(
                    "Project",
                    "OrangeHRM Automation"
            );

            extent.setSystemInfo(
                    "Framework",
                    "Selenium + TestNG"
            );

            extent.setSystemInfo(
                    "API Testing",
                    "REST Assured"
            );

            extent.setSystemInfo(
                    "Build Tool",
                    "Maven"
            );
        }

        return extent;
    }
}