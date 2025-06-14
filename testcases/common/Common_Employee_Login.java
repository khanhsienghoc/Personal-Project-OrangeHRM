package common;

import commons.BaseTest;
import commons.EnvironmentConfigManager;
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
        config = EnvironmentConfigManager.getInstance();
        loginPage = PageGeneratorManager.getLoginPage(driver);
        fakeData = DataUltilities.getData();

        username = fakeData.getUsername();
        password = fakeData.getValidPassword();
        firstName = fakeData.getFirstName();
        middleName = fakeData.getMiddleName();
        lastName = fakeData.getLastName();
        adminUsername = config.getAdminUserName();
        adminPassword = config.getAdminPassword();
        employeeID = fakeData.getEmployeeID();

        log.info("Pre-condition - Step_01 - Login with Admin data");
        loginPage.login(adminUsername, adminPassword);
        homePage = PageGeneratorManager.getDashboardPage(driver);

        log.info("Pre-condition - Step_02 - Verify the page title");
        Assertions.assertEquals("OrangeHRM", loginPage.getPageTitle(driver));

        log.info("Pre-condition - Step_03 - Click the PIM tab");
        pimPage = homePage.clickToMenuByText(driver, "PIM");

        log.info("Pre-condition - Step_04 - Click Add button to add new employee");
        pimPage.clickToButtonByText(driver, "Add");
        getAddEmployeePage= PageGeneratorManager.getAddEmployeePage(driver);

        log.info("Pre-condition - Step_05 - Input Employee Information");
        getAddEmployeePage.fillEmployeeInformation(firstName,middleName, lastName, employeeID);

        log.info("Pre-condition - Step_06 - Click on the 'Create Login Details' toggle");
        getAddEmployeePage.checkToCreateLoginDetailsToggle();

        log.info("Pre-condition - Step_07 - Input username, password and confirm password with value '" + username +"' and '" + password +"'");
        getAddEmployeePage.createLoginDetails(username, password, password);

        log.info("Pre-condition - Step_08 - Click Save Button");
        getAddEmployeePage.clickToButtonByText(driver, "Save");
        pimPage = PageGeneratorManager.getPIMPage(driver);

        log.info("Pre-condition - Step_09 - Verify a success pop up show");
        pimPage.verifySuccessMessage(driver);

        log.info("Pre-condition - Step_10 - Click on Profile Options and click Logout");
        pimPage.clickOnProfileDropdown(driver);
        pimPage.clickOnProfileOptionByText(driver, "Logout");
        loginPage = PageGeneratorManager.getLoginPage(driver);

        log.info("Pre-condition - Step_11 - Login with Employee data");
        loginPage.login(username, password);
        homePage = PageGeneratorManager.getDashboardPage(driver);

        log.info("Pre-condition - Step_12 - Verify Page Title");
        Assertions.assertEquals("OrangeHRM", homePage.getPageTitle(driver));

        log.info("Pre-condition - Step_13 - Click Profile Option");
        pimPage.clickOnProfileDropdown(driver);

        log.info("Pre-condition - Step_14 - Click Logout");
        pimPage.clickOnProfileOptionByText(driver, "Logout");
        closeBrowserAndDriver();
    }
    @AfterTest(alwaysRun = true)
    public void afterTest(){
        log.info("Cleaning up: Closing browser and driver");
        closeBrowserAndDriver();
    }
    private WebDriver driver;
    public static String username, password;
    private LoginPageObject loginPage;
    private DashboardPageObject homePage;
    private PIMPageObject pimPage;
    private AddEmployeePageObject getAddEmployeePage;
    public static String firstName, middleName, lastName, employeeID;
    private String adminUsername, adminPassword;
    private DataUltilities fakeData;
    EnvironmentConfigManager config = EnvironmentConfigManager.getInstance();

}
