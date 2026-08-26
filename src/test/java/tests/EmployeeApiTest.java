package tests;

import java.net.URI;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DashboardPage;
import pages.EmployeeListpage;
import pages.EmployeePage;
import pages.LoginPage;
import utils.ConfigReader;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EmployeeApiTest extends BaseTest {

    @Test
    public void verifyEmployeeThroughAPI() {

        System.out.println("===== Employee API Test Started =====");

        // ==========================================
        // 1. LOGIN THROUGH UI
        // ==========================================

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ==========================================
        // 2. GO TO PIM
        // ==========================================

        DashboardPage dashboardPage =
                new DashboardPage(driver);

        dashboardPage.clickPIM();

        // ==========================================
        // 3. CREATE EMPLOYEE
        // ==========================================

        EmployeePage employeePage =
                new EmployeePage(driver);

        employeePage.clickAddEmployee();

        String employeeId =
                String.valueOf(System.currentTimeMillis())
                        .substring(7);

        String firstName = "ApiRavi";
        String middleName = "C";
        String lastName = "Jogure";

        System.out.println(
                "Generated Employee ID: " + employeeId
        );

        employeeId = employeePage.createEmployee(
                firstName,
                middleName,
                lastName,
                employeeId
        );

        System.out.println(
                "Employee created with ID: " + employeeId
        );

        // ==========================================
        // 4. GET ORANGEHRM SESSION COOKIE
        // ==========================================

        if (driver.manage().getCookieNamed("orangehrm") == null) {

            Assert.fail(
                    "OrangeHRM session cookie was not found after login"
            );
        }

        String orangeHrmCookie =
                driver.manage()
                        .getCookieNamed("orangehrm")
                        .getValue();

        System.out.println(
                "OrangeHRM session cookie obtained successfully"
        );

        // ==========================================
        // 5. SET API BASE URI
        // ==========================================

        String baseUrl =
                ConfigReader.getProperty("url").trim();

        if (baseUrl.endsWith("/")) {

            baseUrl =
                    baseUrl.substring(
                            0,
                            baseUrl.length() - 1
                    );
        }

        try {

            URI baseUri =
                    URI.create(baseUrl);

            RestAssured.baseURI =
                    baseUri.toString();

        } catch (Exception e) {

            Assert.fail(
                    "Invalid OrangeHRM URL: " + baseUrl,
                    e
            );
        }

        System.out.println(
                "API Base URI: "
                        + RestAssured.baseURI
        );

        // ==========================================
        // 6. CALL EMPLOYEE API WITH RETRY
        // ==========================================

        Response response = null;

        int maxAttempts = 3;

        for (int attempt = 1;
             attempt <= maxAttempts;
             attempt++) {

            System.out.println(
                    "API search attempt: "
                            + attempt
                            + " for Employee ID: "
                            + employeeId
            );

            response =
                    RestAssured
                            .given()
                            .cookie(
                                    "orangehrm",
                                    orangeHrmCookie
                            )
                            .queryParam(
                                    "nameOrId",
                                    employeeId
                            )
                            .queryParam(
                                    "includeEmployees",
                                    "onlyCurrent"
                            )
                            .queryParam(
                                    "limit",
                                    "50"
                            )
                            .queryParam(
                                    "offset",
                                    "0"
                            )
                            .when()
                            .get(
                                    "/web/index.php/api/v2/pim/employees"
                            );

            System.out.println(
                    "API Status Code: "
                            + response.getStatusCode()
            );

            int totalEmployees =
                    response.jsonPath()
                            .getInt("meta.total");

            System.out.println(
                    "Employees found by API: "
                            + totalEmployees
            );

            // Employee found
            if (response.getStatusCode() == 200
                    && totalEmployees > 0) {

                break;
            }

            // Wait before retry
            if (attempt < maxAttempts) {

                System.out.println(
                        "Employee not available yet. "
                                + "Waiting before retry..."
                );

                try {

                    Thread.sleep(3000);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                    Assert.fail(
                            "Thread interrupted while "
                                    + "waiting for API retry"
                    );
                }
            }
        }

        // ==========================================
        // 7. PRINT FINAL API RESPONSE
        // ==========================================

        System.out.println("API Response:");

        System.out.println(
                response.asPrettyString()
        );

        // ==========================================
        // 8. VERIFY STATUS CODE
        // ==========================================

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Employee API did not return 200"
        );

        // ==========================================
        // 9. VERIFY EMPLOYEE EXISTS
        // ==========================================

        int totalEmployees =
                response.jsonPath()
                        .getInt("meta.total");

        Assert.assertTrue(
                totalEmployees > 0,
                "Employee ID "
                        + employeeId
                        + " was not found through API"
        );

        // ==========================================
        // 10. VERIFY EMPLOYEE DATA
        // ==========================================

        String actualFirstName =
                response.jsonPath()
                        .getString("data[0].firstName");

        String actualEmployeeId =
                response.jsonPath()
                        .getString("data[0].employeeId");

        System.out.println(
                "API Employee First Name: "
                        + actualFirstName
        );

        System.out.println(
                "API Employee ID: "
                        + actualEmployeeId
        );

        Assert.assertEquals(
                actualFirstName,
                firstName,
                "Employee first name does not match"
        );

        Assert.assertEquals(
                actualEmployeeId,
                employeeId,
                "Employee ID does not match"
        );

        System.out.println(
                "Employee verified successfully through API"
        );

        // ==========================================
        // 11. DELETE EMPLOYEE
        // ==========================================

        dashboardPage.clickPIM();

        dashboardPage.clickEmployeeList();

        EmployeeListpage employeeListPage =
                new EmployeeListpage(driver);

        employeeListPage.searchEmployeeById(
                employeeId
        );

        Assert.assertTrue(
                employeeListPage.isEmployeeDisplayed(
                        employeeId
                ),
                "Created employee was not found "
                        + "in Employee List"
        );

        employeeListPage.deleteEmployee(
                employeeId
        );

        System.out.println(
                "Employee deleted successfully: "
                        + employeeId
        );

        // ==========================================
        // FINAL SUCCESS
        // ==========================================

        System.out.println(
                "===== Employee API Test Passed Successfully ====="
        );
    }
}