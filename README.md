# OrangeHRM Automation Framework

## Project Overview

This project is a Selenium based automation framework for OrangeHRM.## Project Overview

This project is a Selenium WebDriver based test automation framework developed for testing the OrangeHRM application.

The framework covers UI automation, API validation, role-based validation, employee lifecycle testing and CI/CD execution using GitHub Actions.

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

src/main/java
src/main/resources

src/test/java
  base
  listeners
  pages
    DashboardPage.java
    EmployeeListpage.java
    EmployeePage.java
    LoginPage.java
  tests
    EmployeeApiTest.java
    EmployeeLifeCycleTest.java
    LoginTest.java
    RoleBasedValidationTest.java
  utils

src/test/resources

.github/workflows
  ci.yml

pom.xml
target
test-output
README.md

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

LoginTest.java

### Employee Lifecycle Test

Validates the complete employee lifecycle:

Login
Create Employee
Search Employee
Update Employee
Delete Employee

Test class:

EmployeeLifeCycleTest.java

### Employee API Test

Validates employee information through the OrangeHRM API using REST Assured.

The test performs:

UI Login
Create Employee
Get OrangeHRM Session Cookie
Call Employee API
Validate API Status
Validate Employee Data
Delete Employee

Test class:

EmployeeApiTest.java

### Role Based Validation

Validates that an authenticated admin user can access the Admin functionality.

Test class:

RoleBasedValidationTest.java

## Reusable Components

### BaseTest

Provides common WebDriver setup and teardown functionality for test classes.

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

- visibilityOfElementLocated
- elementToBeClickable
- presenceOfElementLocated
- invisibilityOfElementLocated

Explicit waits help reduce synchronization issues between the automation script and the application.

## Retry and Flaky Test Handling

The framework includes retry and search retry logic for operations where the OrangeHRM application may require additional time to display newly created employee data.

Smart waits are used instead of relying only on fixed delays.

Flaky test mitigation includes:

- Explicit waits
- Retry logic
- Stable element locators
- Waiting for application state before performing actions

## API Validation

REST Assured is used to validate employee information at API level.

The API test validates:

- HTTP status code
- Employee availability
- Employee first name
- Employee ID
- API response data

The API test also uses the OrangeHRM session cookie obtained after UI login.

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

## Run Tests

Open a terminal in the project root directory and execute:

mvn clean test

TestNG is used as the test execution framework.

## CI/CD - GitHub Actions

The project uses GitHub Actions for Continuous Integration.

Workflow file:

.github/workflows/ci.yml

The CI pipeline performs:

1. Checkout source code
2. Setup Java
3. Install Maven dependencies
4. Execute automated tests
5. Generate test results
6. Upload test reports
7. Upload failure artifacts

The automation suite has been successfully executed through GitHub Actions.

## CI Test Environment

The tests are executed on a Linux GitHub Actions runner using Chrome.

Java 17 and Maven are configured in the CI workflow before test execution.

## Test Reports

Maven Surefire reports are generated during test execution.

Reports are available under:

target/surefire-reports

GitHub Actions uploads generated test reports as workflow artifacts.

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
- Role based validation
- API validation
- End-to-end employee lifecycle

## Key Design Decisions

### Page Object Model

Application pages are separated from test classes to improve maintainability and reusability.

### Explicit Waits

Explicit waits are used to synchronize automation with dynamic application elements.

### API Validation

API validation provides an additional backend verification layer beyond UI testing.

### Reusable Components

Common functionality is maintained in BaseTest, Page Objects and utility classes.

### CI/CD

GitHub Actions automatically executes the automation suite when changes are pushed to the repository.

## Project Benefits

This framework provides:

- Reusable Page Objects
- UI automation
- API validation
- Employee end-to-end lifecycle testing
- Role based validation
- Explicit wait handling
- Retry handling
- Maven integration
- TestNG execution
- Git version control
- GitHub Actions CI/CD
- Test reports
- Failure screenshots

## Author

Ravi Jogure

OrangeHRM Automation Testing Project