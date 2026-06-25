# InMotion Hosting Automation Project

## Overview

This project automates the main e-commerce flow on InMotion Hosting using Selenium WebDriver and Java.

The framework follows the Page Object Model (POM) design pattern to improve readability, reusability, and maintenance.

Website:
https://www.inmotionhosting.com/

---

## Tools & Technologies

* Java
* Selenium WebDriver
* TestNG
* Maven
* Page Object Model (POM)
* Apache Commons IO

---

## Project Structure

```plaintext
InMotionHosting
│
├── src
│   ├── main
│   │   └── java
│   │       ├── Base
│   │       │   ├── BasePage.java
│   │       │   └── DriverFactory.java
│   │       │
│   │       ├── Pages
│   │       │   ├── HomePage.java
│   │       │   ├── DomainSearchPage.java
│   │       │   ├── CartPage.java
│   │       │   ├── WebHostingPage.java
│   │       │   └── EndToEnd.java
│   │       │
│   │       └── Utils
│   │           ├── ConfigReader.java
│   │           └── ScreenshotUtil.java
│   │
│   └── test
│       └── java
│           ├── Base
│           │   └── BaseTest.java
│           │
│           ├── Listeners
│           │   └── TestListener.java
│           │
│           └── Tests
│               ├── HomePageTest.java
│               ├── DomainSearchTest.java
│               ├── CartTest.java
│               ├── HostingPlanTest.java
│               └── EndToEndTest.java
│
├── .gitignore
├── pom.xml
└── README.md
```

---

## Automated Test Coverage

### Home Page

* Verify page title
* Verify top navigation menu
* Navigate to Domain Search

### Domain Search

* Search available domain
* Verify displayed price
* Validate unavailable domain
* Validate alternative suggestions

### Cart

* Add domain to cart
* Verify domain information
* Validate cart persistence after refresh
* Remove item from cart

### Hosting Plans

* Add Power Plan
* Verify hosting details
* Replace Power Plan with Launch Plan
* Validate updated price

### End-to-End Flow

* Complete customer journey
* Domain search
* Cart validation
* Hosting selection
* Refresh validation
* Remove items
* Update hosting
* Invalid domain validation

---

## Screenshots

Screenshots are captured automatically during execution.

Examples:

* Domain search results
* Final cart view
* Failed test cases

Saved inside:

```plaintext
reports/
```

---

## Execution Steps

### Clone Repository

```bash
git clone <repository-url>
```

---

### Install Dependencies

```bash
mvn clean install
```

---

### Run Tests

Run all tests:

```bash
mvn test
```

Run a specific class:

```bash
mvn test -Dtest=EndToEndTest
```

---

## Test Data

Available Domain:

```plaintext
myautomationtest123.com
```

Unavailable Domain:

```plaintext
google.com
```

---

## Notes

* Explicit waits are implemented using WebDriverWait.
* Screenshots are generated automatically.
* Assertions are used for all validations.
* The framework supports extending additional scenarios easily.
