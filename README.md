# OrangeHRM Automation Framework

## Project Overview

This project is a Selenium WebDriver based test automation framework developed for testing the OrangeHRM application.

The framework covers UI automation, API validation, role-based validation, employee lifecycle testing, and CI/CD execution using GitHub Actions.

## Tech Stack

- Java 17
- Selenium WebDriver
- TestNG
- Maven
- REST Assured
- Git
- GitHub
- GitHub Actions
- Google Chrome
- Page Object Model (POM)

## Framework Structure

    OrangeHRM_Anup
    │
    ├── src
    │   ├── main
    │   │   ├── java
    │   │   └── resources
    │   │
    │   └── test
    │       ├── java
    │       │   ├── base
    │       │   ├── listeners
    │       │   ├── pages
    │       │   │   ├── DashboardPage.java
    │       │   │   ├── EmployeeListpage.java
    │       │   │   ├── EmployeePage.java
    │       │   │   └── LoginPage.java
    │       │   │
    │       │   ├── tests
    │       │   │   ├── EmployeeApiTest.java
    │       │   │   ├── EmployeeLifeCycleTest.java
    │       │   │   ├── LoginTest.java
    │       │   │   └── RoleBasedValidationTest.java
    │       │   │
    │       │   └── utils
    │       │
    │       └── resources
    │
    ├── .github
    │   └── workflows
    │       └── ci.yml
    │
    ├── pom.xml
    ├── testng.xml
    └── README.md

## Design Pattern

The framework follows the Page Object Model (POM) design pattern.

Each application page has a dedicated Page Object class containing:

- Web element locators
- Page actions
- Reusable methods

This improves:

- Maintainability
- Reusability
- Readability
- Scalability

## Test Scenarios

### Login Test

Validates successful login to the OrangeHRM application.

Test class:

`LoginTest.java`

### Employee Lifecycle Test

Validates the complete employee lifecycle:

1. Login
2. Create Employee
3. Search Employee
4. Update Employee
5. Verify Updated Employee
6. Delete Employee
7. Verify Employee Deleted

Test class:

`EmployeeLifeCycleTest.java`

### Employee API Test

Validates employee information through the OrangeHRM API using REST Assured.

The test performs:

1. UI Login
2. Create Employee
3. Get OrangeHRM Session Cookie
4. Call Employee API
5. Validate API Status
6. Validate Employee Data
7. Delete Employee

Test class:

`EmployeeApiTest.java`

### Role Based Validation

Validates that an authenticated admin user can access the Admin functionality.

Test class:

`RoleBasedValidationTest.java`

## Reusable Components

### BaseTest

Provides common WebDriver setup and teardown functionality for test classes.

The framework also supports CI execution using headless Chrome.

### ConfigReader

Used for reading configuration properties such as:

- Application URL
- Username
- Password

### Page Objects

The framework contains the following Page Object classes:

- LoginPage
- DashboardPage
- EmployeePage
- EmployeeListpage

## Synchronization Strategy

The framework uses Selenium explicit waits to handle dynamic elements.

Examples include:

- `visibilityOfElementLocated`
- `elementToBeClickable`
- `presenceOfElementLocated`
- `invisibilityOfElementLocated`

Explicit waits help reduce synchronization issues between the automation script and the application.

The employee creation flow also waits for the successful save operation before continuing with employee search.

## Retry and Flaky Test Handling

The framework includes retry and search retry logic for operations where the OrangeHRM application may require additional time to display newly created employee data.

Smart waits are used instead of relying only on fixed delays.

Flaky test mitigation includes:

- Explicit waits
- Retry logic
- Stable element locators
- Waiting for application state before performing actions
- Waiting for employee save operation to complete

## API Validation

REST Assured is used to validate employee information at API level.

The API test validates:

- HTTP status code
- Employee availability
- Employee first name
- Employee ID
- API response data

The API test also uses the OrangeHRM session cookie obtained after UI login.

This provides an additional backend verification layer beyond UI testing.

## TestNG Groups

TestNG groups are used to categorize tests.

### Smoke Tests

Smoke tests cover the critical application flows:

- Login Test
- Employee Lifecycle Test
- Role Based Validation Test

### Regression Tests

Regression execution includes the complete automation suite:

- Login Test
- Employee Lifecycle Test
- Employee API Test
- Role Based Validation Test

Tests are categorized using TestNG groups.

Example:

    @Test(groups = {"smoke", "regression"})

Regression-only example:

    @Test(groups = {"regression"})

## Maven

Maven is used for:

- Dependency management
- Test execution
- Build execution
- Surefire test reporting

Run the complete test suite using:

    mvn clean test

## Local Test Execution

### Prerequisites

Install the following:

- Java 17
- Maven
- Google Chrome
- Git

Verify Java installation:

    java -version

Verify Maven installation:

    mvn -version

### Run Complete Test Suite

Open a terminal in the project root directory and execute:

    mvn clean test

### Run Smoke Tests

    mvn clean test -Dgroups=smoke

### Run Regression Tests

    mvn clean test -Dgroups=regression

TestNG is used as the test execution framework.

## CI/CD - GitHub Actions

The project uses GitHub Actions for Continuous Integration.

Workflow file:

    .github/workflows/ci.yml

The CI pipeline performs:

1. Checkout source code
2. Setup JDK 17
3. Setup Google Chrome
4. Execute Maven tests
5. Generate Surefire test results
6. Generate Extent HTML report
7. Upload Surefire reports
8. Upload Extent report
9. Upload failure screenshots

### Automatic Regression Execution

Regression tests are automatically executed when code is pushed to the `master` branch or when a pull request is created against `master`.

### Manual Smoke / Regression Execution

GitHub Actions also supports manual workflow execution.

The test type can be selected from:

- Smoke
- Regression

This allows the required test group to be executed directly from the GitHub Actions workflow.

## CI Test Environment

The tests are executed on a Linux GitHub Actions runner using Google Chrome.

The CI environment uses:

- Java 17
- Maven
- Chrome
- Headless Chrome execution

Chrome is configured with CI-compatible options such as:

- `--headless=new`
- `--no-sandbox`
- `--disable-dev-shm-usage`
- `--window-size=1920,1080`

## Test Reports

### Maven Surefire Reports

Maven Surefire reports are generated during test execution.

Reports are available under:

    target/surefire-reports

GitHub Actions uploads the generated Surefire reports as workflow artifacts.

### Extent Report

The framework generates an HTML Extent Report after test execution.

The report provides:

- Test execution status
- Test names
- Execution timestamps
- Pass/Fail status
- Test execution details

GitHub Actions uploads the Extent Report as a workflow artifact.

## Failure Screenshots

Failure screenshots are captured by the framework and uploaded as GitHub Actions artifacts.

These screenshots help in debugging failures that occur during CI execution.

## Current Test Coverage

The automation suite covers:

- Authentication
- Employee creation
- Employee search
- Employee update
- Employee deletion
- Employee lifecycle
- Role based validation
- API validation
- End-to-end employee lifecycle
- Smoke testing
- Regression testing
- CI/CD execution

## Key Design Decisions

### Page Object Model

Application pages are separated from test classes to improve maintainability and reusability.

### Explicit Waits

Explicit waits are used to synchronize automation with dynamic application elements.

### Retry Handling

Retry and synchronization mechanisms are used for operations affected by application response timing.

### API Validation

API validation provides an additional backend verification layer beyond UI testing.

### Reusable Components

Common functionality is maintained in BaseTest, Page Objects, listeners, and utility classes.

### TestNG Groups

TestNG groups provide separate Smoke and Regression execution strategies.

### CI/CD

GitHub Actions automatically executes the automation suite when changes are pushed to the repository and also supports manual Smoke/Regression execution.

## Project Benefits

This framework provides:

- Reusable Page Objects
- UI automation
- API validation
- Employee end-to-end lifecycle testing
- Role based validation
- Explicit wait handling
- Retry handling
- TestNG grouping
- Maven integration
- TestNG execution
- Git version control
- GitHub Actions CI/CD
- Extent HTML reporting
- Surefire test reporting
- Failure screenshots
- CI-friendly headless browser execution

## CI Verification

The framework has been successfully verified through GitHub Actions.

### Regression

- Regression workflow: PASS
- All 4 regression tests executed successfully.

### Smoke

- Smoke workflow: PASS
- All 3 smoke tests executed successfully.

## Author

Ravi Jogure

OrangeHRM Automation Testing Project## Flaky Test Detection

Flaky tests are identified by monitoring test results across repeated executions.

A test is considered potentially flaky when it passes and fails intermittently without a consistent application or code defect.

The framework uses:

- Retry logic for transient failures
- Explicit waits for dynamic application behavior
- Stable locators
- CI execution history
- Failure screenshots for debugging

## Flaky Test Mitigation Strategy

Flaky behavior is mitigated by:

- Replacing unnecessary fixed delays with explicit waits
- Waiting for application state before performing actions
- Adding retry logic for transient search failures
- Waiting for successful employee save confirmation
- Capturing screenshots when failures occur
- Running tests repeatedly through GitHub Actions