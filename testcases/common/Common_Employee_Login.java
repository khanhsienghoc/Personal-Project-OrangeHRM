package common;

import commons.BaseTest;
import commons.GlobalConstants;
import io.qameta.allure.testng.AllureTestNg;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObject.*;
import reportConfigs.AllureTestListener;
import ultilities.DataUltilities;

@Listeners({AllureTestNg.class, AllureTestListener.class})
public class Common_Employee_Login extends BaseTest {
    @Parameters({"browser","environment"})
    @BeforeTest
    public void beforeClass(String browserName, String environmentName) {
        log.info("Pre-condition: Open Browser "+ browserName + " and navigate to the URL in " + environmentName + " environment");

        driver = getBrowserDriver(browserName, environmentName);
        loginPage = PageGeneratorManager.getLoginPage(driver);
        fakeData = DataUltilities.getData();

        username = fakeData.getUsername();
        password = fakeData.getValidPassword();
        firstName = fakeData.getFirstName();
        middleName = fakeData.getMiddleName();
        lastName = fakeData.getLastName();

        log.info("Pre-condition - Step 02 - Input username of the admin with value: " + GlobalConstants.TESTING_ADMIN_USERNAME);
        loginPage.inputToTextBoxByText(driver, "Username", GlobalConstants.TESTING_ADMIN_USERNAME);

        log.info("Pre-condition - Step 03 - Input password of the admin with value: " + GlobalConstants.TESTING_ADMIN_PASSWORD);
        loginPage.inputToTextBoxByText(driver, "Password", GlobalConstants.TESTING_ADMIN_PASSWORD);

        log.info("Pre-condition - Step 04 - Click Login button");
        loginPage.clickToLoginButton();
        homePage = PageGeneratorManager.getHomePage(driver);

        log.info("Pre-condition - Step 05 - Verify the page title");
        Assertions.assertEquals("OrangeHRM", loginPage.getPageTitle(driver));

        log.info("Pre-condition - Step 06 - Click the PIM tab");
        pimPage = homePage.clickToMenuByText(driver, "PIM");

        log.info("Pre-condition - Step 07 - Click Add button to add new employee");
        pimPage.clickToButtonByText(driver, "Add");
        getAddEmployeePage= PageGeneratorManager.getAddEmployeePage(driver);

        log.info("Pre-condition - Step 08 - Input Employee First Name with value: '"+ firstName +"'" );
        getAddEmployeePage.InputEmployeeInformationByName("firstName", firstName);

        log.info("Pre-condition - Step 09 - Get the value of 'Employee ID'");
        employeeID = getAddEmployeePage.getPropertyOfTextBoxByText(driver, "value","Employee Id");

        log.info("Pre-condition - Step 10 - Input Employee Middle Name with value: '" + middleName + "'");
        getAddEmployeePage.InputEmployeeInformationByName("middleName", middleName);

        log.info("Pre-condition - Step 11 - Input Employee Last Name with value: '" + lastName + "'");
        getAddEmployeePage.InputEmployeeInformationByName("lastName", lastName);

        log.info("Pre-condition - Step 12 - Click on the toggle 'Create Login Details'");
        getAddEmployeePage.checkToCreateLoginDetailsToggle();

        log.info("Pre-condition - Step 13 - Input Username with value: " + username);
        getAddEmployeePage.inputToTextBoxByText(driver, "Username", username);

        log.info("Pre-condition - Step 14 - Input Password and Confirm Password with value: " + password);
        getAddEmployeePage.inputToTextBoxByText(driver, "Password", password);
        getAddEmployeePage.inputToTextBoxByText(driver, "Confirm Password", password);

        log.info("Pre-condition - Step 15 - Click Save Button");
        getAddEmployeePage.clickToButtonByText(driver, "Save");
        pimPage = PageGeneratorManager.getPIMPage(driver);

        log.info("Pre-condition - Step 16 - Verify a success pop up show");
        Assertions.assertTrue(pimPage.isSuccessPopUpShow(driver));

        log.info("Pre-condition - Step 17 - Click on Profile Options and click Logout");
        pimPage.clickOnProfileDropdown(driver);
        pimPage.clickOnProfileOptionByText(driver, "Logout");
        loginPage = PageGeneratorManager.getLoginPage(driver);

        log.info("Pre-condition - Step 18 - Input username of the employee with value: " +username);
        loginPage.inputToTextBoxByText(driver, "Username", username);

        log.info("Pre-condition - Step 19 - Input password of the employee with value: "+ password);
        loginPage.inputToTextBoxByText(driver, "Password", password);

        log.info("Pre-condition - Step 20 - Click Login");
        loginPage.clickToLoginButton();
        homePage = PageGeneratorManager.getHomePage(driver);

        log.info("Pre-condition - Step 21 - Verify Page Title");
        Assertions.assertEquals("OrangeHRM", homePage.getPageTitle(driver));

        log.info("Pre-condition - Step 22 - Click Profile Option");
        pimPage.clickOnProfileDropdown(driver);

        log.info("Pre-condition - Step 23 - Click Logout");
        pimPage.clickOnProfileOptionByText(driver, "Logout");
        closeBrowserAndDriver();
    }
    @AfterTest(alwaysRun = true)
    public void afterTest(){
        closeBrowserAndDriver();
    }
    private WebDriver driver;
    public static String username, password;
    private LoginPageObject loginPage;
    private HomePageObject homePage;
    private PIMPageObject pimPage;
    private AddEmployeePageObject getAddEmployeePage;
    public static String firstName, middleName, lastName, employeeID;
    private DataUltilities fakeData;

}
