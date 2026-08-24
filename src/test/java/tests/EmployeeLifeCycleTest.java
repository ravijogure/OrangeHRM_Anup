package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DashboardPage;
import pages.EmployeeListpage;
import pages.EmployeePage;
import pages.LoginPage;
import utils.ConfigReader;

public class EmployeeLifeCycleTest extends BaseTest {

    @Test
    public void employeeLifecycle() {

        // ==========================================
        // 1. LOGIN
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
        // 3. ADD EMPLOYEE
        // ==========================================

        EmployeePage employeePage =
                new EmployeePage(driver);

        employeePage.clickAddEmployee();


        // ==========================================
        // 4. CREATE EMPLOYEE
        // ==========================================

        String employeeId =
                String.valueOf(System.currentTimeMillis()).substring(7);

        System.out.println(
                "Generated Employee ID for this run: " + employeeId
        );

        employeeId = employeePage.createEmployee(
                "Ravi",
                "C",
                "Jogure",
                employeeId
        );

        System.out.println(
                "Actual Employee ID after Save: " + employeeId
        );


        // ==========================================
        // 5. GO TO EMPLOYEE LIST
        // ==========================================

        dashboardPage.clickPIM();
        dashboardPage.clickEmployeeList();


        // ==========================================
        // 6. SEARCH CREATED EMPLOYEE
        // ==========================================

        EmployeeListpage employeeListPage =
                new EmployeeListpage(driver);

        employeeListPage.searchEmployeeById(employeeId);


        // ==========================================
        // 7. VERIFY EMPLOYEE
        // ==========================================

        boolean employeeDisplayed =
                employeeListPage.isEmployeeDisplayed(employeeId);

        Assert.assertTrue(
                employeeDisplayed,
                "Employee with ID " + employeeId +
                " was not displayed in Employee List"
        );


        // ==========================================
        // 8. OPEN AND UPDATE EMPLOYEE
        // ==========================================

        employeeListPage.openEmployee(employeeId);

        employeePage.updateEmployee(
                "RaviUpdated",
                "C",
                "Jogure"
        );


        // ==========================================
        // 9. VERIFY UPDATED EMPLOYEE
        // ==========================================

        dashboardPage.clickPIM();
        dashboardPage.clickEmployeeList();

        employeeListPage.searchEmployeeById(employeeId);

        boolean updatedEmployeeDisplayed =
                employeeListPage.isEmployeeNameDisplayed("RaviUpdated");

        Assert.assertTrue(
                updatedEmployeeDisplayed,
                "Employee name was not updated successfully"
        );


        // ==========================================
        // 10. DELETE EMPLOYEE
        // ==========================================

        employeeListPage.deleteEmployee(employeeId);


        // ==========================================
        // 11. VERIFY EMPLOYEE DELETED
        // ==========================================

        boolean employeeDeleted =
                !employeeListPage.isEmployeeDisplayed(employeeId);

        Assert.assertTrue(
                employeeDeleted,
                "Employee with ID " + employeeId +
                " was not deleted successfully"
        );


        // ==========================================
        // FINAL SUCCESS MESSAGE
        // ==========================================

        System.out.println(
                "Employee created, updated and deleted successfully: "
                        + employeeId
        );
    }
}