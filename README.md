# Personal Project - OrangeHRM

![OrangeHRM](https://img.shields.io/badge/OrangeHRM-Test%20Automation-orange)
![Status](https://img.shields.io/badge/Status-Active-green)
![License](https://img.shields.io/badge/License-MIT-blue)

## 📋 Overview

This project focuses on automating and testing the OrangeHRM platform, which is a comprehensive Human Resource Management (HRM) System. The project demonstrates various testing methodologies and automation frameworks applied to the OrangeHRM demo environment.

## 🎯 About OrangeHRM

OrangeHRM is an open-source Human Resource Management System that captures essential HR functionalities required for enterprises. It includes modules for:

- 👥 Employee Management
- 🏖️ Leave Management
- 📊 Performance Management
- 🔍 Recruitment
- ⏰ Time Tracking
- 📈 Reports & Analytics

**🌐 Demo Platform:** [OrangeHRM Live Demo](https://opensource-demo.orangehrmlive.com/)

## 🚀 Project Objectives

- ✅ Implement comprehensive test automation for OrangeHRM modules
- ✅ Practice modern testing frameworks and methodologies
- ✅ Demonstrate proficiency in test automation tools
- ✅ Create maintainable and scalable test suites
## 🔧 Technology Stack

### Core Technologies
- **Language:** Java 11+
- **Web Automation:** Selenium WebDriver
- **Testing Framework:** TestNG
- **Build Tool:** Maven
- **Design Pattern:** Page Object Model (POM)
- **Reporting:** Allure Reports

### Supporting Tools
- **Browser Management:** WebDriverManager
- **Configuration:** XML configuration files
- **Logging:** Log4j2
- **IDE:** IntelliJ IDEA

```
Personal-Project-OrangeHRM/
├── 📂 .idea/                          # IntelliJ IDEA configuration
├── 📂 actions/
│   ├── 📂 commons/
│   │   ├── BasePage.java              # Base page class
│   │   ├── BaseTest.java              # Base test configuration
│   │   ├── BrowserList.java           # Browser enumeration
│   │   ├── EnvironmentConfigManager.java
│   │   ├── EnvironmentList.java
│   │   ├── GlobalConstants.java       # Application constants
│   │   └── TestGuard.java
│   ├── 📂 dataAccessObject/           # Database query data access
│   ├── 📂 database/
│   │   └── BaseDBHelper.java
│   ├── 📂 dataObject/                 # Testing data  
│   │   └── PersonalDetailsData.java
│   ├── 📂 pageObject/                # Page Object
│   ├── 📂 reportConfigs/             # Config for Allure Report and Database
│   ├── 📂 retryConfigs/
│   └── 📂 utilities/
├── 📂 allure-results/                # Allure test results
├── 📂 exception/
├── 📂 interfaces/                    # UI locator
├── 📂 resources/                     # XML    
├── 📂 target/                        # Maven build output
├── 📂 testcases/                     # Test suit
│   ├── 📂 admin/
│   ├── 📂 common/
│   ├── 📂 employee/
│   └── 📂 testdata/
├── 📂 uploadFiles/
├── .gitignore                         # Git ignore file
├── h.origin.main                      # Git reference file
├── HRMOrange.iml                      # IntelliJ module file
├── pom.xml                           # Maven dependencies
└── README.md                         # Project documentation
```
## 📊 Test Coverage

Current test coverage by module:

| Module | Test Cases | Classes | Description                          |
|--------|------------|---------|--------------------------------------|
| **Admin Module** | 9+ | 3 | Login, Forgot Password, Add Employee |
| **Employee Module** | 6+ | 2 | Login, Personal Details Edit         |
| **Common Functions** | 3+ | 1 | Shared Login Procedures              |
| **Database Integration** | 2+ | 1 | Data Access Objects                  |
| **Reporting & Listeners** | 4+ | 4 | Allure Report                        |
| ****Total** | **29+** | **15** | **Comprehensive Coverage**           |

## 📚 Key Learning Outcomes

✅ **Technical Skills:**
- Java-based test automation framework design
- Page Object Model implementation with PageFactory
- Data-driven testing with TestNG DataProvider
- Cross-browser testing strategies
- Maven build automation
- CI/CD pipeline integration with GitHub Actions

✅ **Best Practices:**
- Clean code principles in Java
- Test maintenance strategies with TestNG
- Error handling and reporting with Log4j2
- Version control workflows with Git