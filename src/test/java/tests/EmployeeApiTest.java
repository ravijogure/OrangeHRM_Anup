package tests;

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
        // 3. CREATE EMPLOYEE FOR API TEST
        // ==========================================

        EmployeePage employeePage =
                new EmployeePage(driver);

        employeePage.clickAddEmployee();

        String employeeId =
                String.valueOf(System.currentTimeMillis()).substring(7);

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
        // 5. CALL EMPLOYEE API
        // ==========================================

        String apiUrl =
                ConfigReader.getProperty("url")
                        + "web/index.php/api/v2/pim/employees";

        Response response =
                RestAssured
                        .given()
                        .cookie("orangehrm", orangeHrmCookie)
                        .queryParam(
                                "nameOrId",
                                firstName
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
                        .get(apiUrl);

        // ==========================================
        // 6. PRINT API RESPONSE
        // ==========================================

        System.out.println(
                "API Status Code: "
                        + response.getStatusCode()
        );

        System.out.println("API Response:");

        System.out.println(
                response.asPrettyString()
        );

        // ==========================================
        // 7. VERIFY STATUS CODE
        // ==========================================

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Employee API did not return 200"
        );

        // ==========================================
        // 8. VERIFY EMPLOYEE EXISTS IN API
        // ==========================================

        int totalEmployees =
                response.jsonPath()
                        .getInt("meta.total");

        System.out.println(
                "Employees found by API: "
                        + totalEmployees
        );

        Assert.assertTrue(
                totalEmployees > 0,
                firstName + " employee was not found through API"
        );

        // ==========================================
        // 9. VERIFY EMPLOYEE NAME
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
        // 10. DELETE EMPLOYEE AFTER API VERIFICATION
        // ==========================================

        dashboardPage.clickPIM();

        dashboardPage.clickEmployeeList();

        EmployeeListpage employeeListPage =
                new EmployeeListpage(driver);

        employeeListPage.searchEmployeeById(employeeId);

        Assert.assertTrue(
                employeeListPage.isEmployeeDisplayed(employeeId),
                "Created employee was not found in Employee List"
        );

        employeeListPage.deleteEmployee(employeeId);

        System.out.println(
                "Employee deleted successfully: "
                        + employeeId
        );

        // ==========================================
        // FINAL SUCCESS MESSAGE
        // ==========================================

        System.out.println(
                "===== Employee API Test Passed Successfully ====="
        );
    }
}