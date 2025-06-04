package admin;

import commons.BaseTest;
import commons.EnvironmentConfigManager;
import commons.GlobalConstants;
import dataAccessObject.EmployeeDataAccess;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.testng.AllureTestNg;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObject.*;
import reportConfigs.AllureTestListener;
import ultilities.DataUltilities;

import java.util.Arrays;
import java.util.List;

import static commons.TestGuard.skipIfDBDisabled;

@Listeners({AllureTestNg.class, AllureTestListener.class})
public class User_03_Admin_Add_New_Employee extends BaseTest {
    @Parameters({"browser","environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environmentName){
        log.info("Pre-condition - Step_01 - Open Browser "+ browserName + " and navigate to the URL in " + environmentName + " environment");
        driver = getBrowserDriver(browserName, environmentName);
        config = EnvironmentConfigManager.getInstance();
        loginPage = PageGeneratorManager.getLoginPage(driver);

        log.info("Pre-condition - Step_02 - Input Admin Username with value "+config.getAdminUserName());
        loginPage.inputToTextBoxByText(driver, "Username",config.getAdminUserName() );

        log.info("Pre-condition - Step_02 - Input Admin Password with value "+ config.getAdminPassword());
        loginPage.inputToTextBoxByText(driver, "Password",config.getAdminPassword() );

        log.info("Pre-condition - Step_03 - Click Login button");
        loginPage.clickToLoginButton();
        dashboardPage = PageGeneratorManager.getDashboardPage(driver);

        log.info("Pre-condition - Step_04 - Verify the page header 'Dashboard'");
        Assertions.assertEquals("Dashboard", dashboardPage.getPageHeaderByText(driver,"Dashboard"));

        log.info("Pre-condition - Step_05 - Click to PIM");
        pimPage = dashboardPage.clickToMenuByText(driver, "PIM");

        log.info("Pre-condition - Step_06 - Click Add button");
        pimPage.clickToButtonByText(driver, "Add");
        getAddEmployeePage= PageGeneratorManager.getAddEmployeePage(driver);

        fakeData = DataUltilities.getData();
        firstName = fakeData.getFirstName();
        middleName = fakeData.getMiddleName();
        lastName = fakeData.getLastName();
        username = fakeData.getUsername();
        password = fakeData.getValidPassword();
        invalidPassword = fakeData.getInvalidPassword();
    }
    @Description("Verify error message when leave required fields empty")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Admin_AddNewEmployee_01_EmptyFields() {
        log.info("AddNewEmployee_01_EmptyFields - Step_01 - Click on the 'Create Login Details' toggle");
        getAddEmployeePage.checkToCreateLoginDetailsToggle();

        log.info("AddNewEmployee_01_EmptyFields - Step_02 - Click on Save button without inputting any text in First Name and Last Name fields");
        getAddEmployeePage.clickToButtonByText(driver, "Save");

        log.info("AddNewEmployee_01_EmptyFields - Step_03 - Verify the error message in the First Name field");
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, getAddEmployeePage.getErrorMessageOfTextBoxByName("firstName"));

        log.info("AddNewEmployee_01_EmptyFields - Step_04 - Verify the error message in the Last Name field");
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, getAddEmployeePage.getErrorMessageOfTextBoxByName("lastName"));

        log.info("AddNewEmployee_01_EmptyFields - Step_05 - Verify the error message in the Username field");
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, getAddEmployeePage.getErrorMessageByName(driver, "Username"));

        log.info("AddNewEmployee_01_EmptyFields - Step_06 - Verify the error message in the Password field");
        Assertions.assertEquals(GlobalConstants.REQUIRED_ERROR_MESSAGE, getAddEmployeePage.getErrorMessageByName(driver, "Password"));

        log.info("AddNewEmployee_01_EmptyFields - Step_07 - Verify the error message in the Confirm Password field");
        Assertions.assertEquals("Passwords do not match", getAddEmployeePage.getErrorMessageByName(driver, "Confirm Password"));
    }
    @Description("Verify First Name, Middle Name and Last Name fields are empty")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Admin_AddNewEmployee_02_VerifyPlaceHolders() {
        log.info("AddNewEmployee_02_VerifyPlaceHolders - Step_01 - Verify the placeholder of the First Name textbox is 'First Name'");
        Assertions.assertEquals("First Name", getAddEmployeePage.getPropertyOfTextBoxByName(driver, "placeholder","firstName"));

        log.info("AddNewEmployee_02_VerifyPlaceHolders - Step_01 - Verify the placeholder of the Middle Name textbox is 'Middle Name'");
        Assertions.assertEquals("Middle Name", getAddEmployeePage.getPropertyOfTextBoxByName(driver, "placeholder","middleName"));

        log.info("AddNewEmployee_02_VerifyPlaceHolders - Step_01 - Verify the placeholder of the Last Name textbox is 'Last Name'");
        Assertions.assertEquals("Last Name", getAddEmployeePage.getPropertyOfTextBoxByName(driver, "placeholder","lastName"));
    }
    @Description("verify input the unmatched password in Confirm Password field")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void Admin_AddNewEmployee_03_NotMatchConfirmPassword() {
        log.info("Admin_AddNewEmployee_03_NotMatchConfirmPassword - Step_01 - Input 'First Name' field with value: " + firstName);
        getAddEmployeePage.InputEmployeeInformationByName("firstName", firstName);

        log.info("Admin_AddNewEmployee_03_NotMatchConfirmPassword - Step_02 - Input 'Middle Name' field with value: " + middleName);
        getAddEmployeePage.InputEmployeeInformationByName("middleName", middleName);

        log.info("Admin_AddNewEmployee_03_NotMatchConfirmPassword - Step_03 - Input 'Last Name' field with value: " + lastName);
        getAddEmployeePage.InputEmployeeInformationByName("lastName", lastName);

        log.info("Admin_AddNewEmployee_03_NotMatchConfirmPassword - Step_04 - Input 'Username' field with value: " + username);
        getAddEmployeePage.inputToTextBoxByText(driver, "Username", username);

        log.info("Admin_AddNewEmployee_03_NotMatchConfirmPassword - Step_05 - Input 'Password' field with value: " + password);
        getAddEmployeePage.inputToTextBoxByText(driver, "Password", password);

        log.info("Admin_AddNewEmployee_03_NotMatchConfirmPassword - Step_06 - Input value in 'Confirm Password' field with unmatched value: " + invalidPassword);
        getAddEmployeePage.inputToTextBoxByText(driver, "Confirm Password", invalidPassword);

        log.info("Admin_AddNewEmployee_03_NotMatchConfirmPassword - Step_06 - Click Save button");
        getAddEmployeePage.clickToButtonByText(driver, "Save");

        log.info("Admin_AddNewEmployee_03_NotMatchConfirmPassword - Step_07 - Verify the error message in the Confirm Password field");
        Assertions.assertEquals("Passwords do not match", loginPage.getErrorMessageByName(driver, "Confirm Password"));
    }
    @Description("verify upload invalid files in the Employee avatar")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Admin_AddNewEmployee_04_VerifyUploadInvalidFile() {
        List<String> invalidFiles = Arrays.asList(docFile, txtFile);
        for(String file : invalidFiles){
            int index = invalidFiles.indexOf(file) + 1;
            log.info("Admin_AddNewEmployee_04_VerifyUploadInvalidFile - Step_01."+index+ "- Upload " + file + " file");
            getAddEmployeePage.uploadEmployeeAvatar(GlobalConstants.UPLOAD_FILE + file);

            log.info("Admin_AddNewEmployee_04_VerifyUploadInvalidFile - Step_02."+index+ "- Verify the error message: 'File type not allowed'");
            Assertions.assertEquals("File type not allowed", getAddEmployeePage.getErrorMessageForEmployeeAvatar());
        }
    }
    @Description("verify upload more than 1MB files in the Employee avatar")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Admin_AddNewEmployee_05_VerifyUploadInvalidFileMoreThan1MB() {
        List<String> invalidFiles = Arrays.asList(pngFileMoreThan1MB, gifFileMoreThan1MB, jpgFileMoreThan1MB);
        for(String file : invalidFiles){
            int index = invalidFiles.indexOf(file) + 1;
            log.info("Admin_AddNewEmployee_05_VerifyUploadInvalidFileMoreThan1MB - Step_01."+index+ "- Upload " + file + " file");
            getAddEmployeePage.uploadEmployeeAvatar(GlobalConstants.UPLOAD_FILE + file);

            log.info("Admin_AddNewEmployee_05_VerifyUploadInvalidFileMoreThan1MB - Step_02."+index+ "- Verify the error message: 'File type not allowed'");
            Assertions.assertEquals("Attachment Size Exceeded", getAddEmployeePage.getErrorMessageForEmployeeAvatar());
        }
    }
    @Description("Verify upload difference type Image")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Admin_AddNewEmployee_06_VerifyUploadValidTypeOfImage() {
        List<String> validFiles = Arrays.asList(gifFile, pngFile, jpegFile);
        for(String file : validFiles){
            int index = validFiles.indexOf(file) + 1;
            log.info("Admin_AddNewEmployee_06_VerifyUploadValidTypeOfImage - Step_01."+index+ "- Upload " + file + " file");
            getAddEmployeePage.uploadEmployeeAvatar(GlobalConstants.UPLOAD_FILE + file);

            log.info("Admin_AddNewEmployee_06_VerifyUploadValidTypeOfImage - Step_02."+index+ "- Verify the " + file +" is uploaded");
            Assertions.assertTrue(getAddEmployeePage.isEmployeeImageUploaded());

            log.info("Admin_AddNewEmployee_06_VerifyUploadValidTypeOfImage - Step_03."+index+ "- Verify there is no error show");
            Assertions.assertTrue(getAddEmployeePage.getSizeOfErrorInEmployeeAvatar() < 1);
        }
    }
    @Description("Verify add new employee successfully")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void Admin_AddNewEmployee_07_AddNewEmployeeSuccess() {
        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 01 - Click PIM");
        pimPage = getAddEmployeePage.clickToMenuByText(driver, "PIM");

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 02 - Click Add");
        pimPage.clickToButtonByText(driver, "Add");
        getAddEmployeePage= PageGeneratorManager.getAddEmployeePage(driver);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 03 - Input First Name with value: " + firstName);
        getAddEmployeePage.InputEmployeeInformationByName("firstName", firstName);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 04 - Input Middle Name with value: " + middleName);
        getAddEmployeePage.InputEmployeeInformationByName("middleName", middleName);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 05 - Input Last Name with value: " + lastName);
        getAddEmployeePage.InputEmployeeInformationByName("lastName", lastName);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 06 - Get Employee ID");
        employeeID = getAddEmployeePage.getPropertyOfTextBoxByText(driver, "_value", "Employee Id");

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 07 - Click 'Create Login Details' toggle");
        getAddEmployeePage.checkToCreateLoginDetailsToggle();

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 08 - Input Username with value: " + username);
        getAddEmployeePage.inputToTextBoxByText(driver, "Username", username);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 09 - Input Password with value: " + password);
        getAddEmployeePage.inputToTextBoxByText(driver, "Password", password);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 10 - Input Confirm Password with value: " + password);
        getAddEmployeePage.inputToTextBoxByText(driver, "Confirm Password", password);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 10 - Upload an employee avatar");
        getAddEmployeePage.uploadEmployeeAvatar(GlobalConstants.UPLOAD_FILE + gifFile);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 10 - verify the employee avatar");
        Assertions.assertTrue(getAddEmployeePage.isEmployeeImageUploaded());

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 11 - Click Save button");
        getAddEmployeePage.clickToButtonByText(driver, "Save");
        getPersonalPage = PageGeneratorManager.getPersonalDetails(driver);

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 12 - Verify success message show");
        Assertions.assertTrue(pimPage.isSuccessPopUpShow(driver));

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 12 - Verify First Name value is: " + firstName);
        Assertions.assertEquals(firstName, getPersonalPage.getPropertyOfTextBoxByName(driver, "_value","firstName"));

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 12 - Verify Middle Name value is: " + middleName);
        Assertions.assertEquals(middleName, getPersonalPage.getPropertyOfTextBoxByName(driver, "_value","middleName"));

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 12 - Verify Last Name value is: " + lastName);
        Assertions.assertEquals(lastName, getPersonalPage.getPropertyOfTextBoxByName(driver, "_value","lastName"));

        log.info("Admin_AddNewEmployee_07_AddNewEmployeeSuccess - Step 12 - Verify Employee ID value is: "+ employeeID);
        Assertions.assertEquals(employeeID, getPersonalPage.getPropertyOfTextBoxByText(driver, "_value","Employee Id"));


    }

    @Description("Verify the employee data exist on database")
    @Severity(SeverityLevel.NORMAL)
    @Test()
    public void Admin_AddNewEmployee_08_IsEmployeeExist() {
        skipIfDBDisabled();
        log.info("Admin_AddNewEmployee_08_IsEmployeeExist - Step 01 - Verify the employee record exists in database");
        employeeDAo.isEmployeeExist(employeeID);
    }

    private WebDriver driver;
    private LoginPageObject loginPage;
    private DashboardPageObject dashboardPage;
    private PIMPageObject pimPage;
    private AddEmployeePageObject getAddEmployeePage;
    private PersonalDetailsPageObject getPersonalPage;
    private String firstName, middleName, lastName, username, password, invalidPassword;
    private DataUltilities fakeData;
    private String docFile = "DocFile.doc";
    private String txtFile = "TxtFile.txt";
    private String gifFile = "GifImage.gif";
    private String pngFile = "PngImage.png";
    private String jpegFile = "JpegImage.jpeg";
    private String pngFileMoreThan1MB = "PngImageMoreThan1MB.png";
    private String gifFileMoreThan1MB = "GifImageMoreThan1MB.gif";
    private String jpgFileMoreThan1MB = "JpgImageMoreThan1MB.jpg";
    private String employeeID;
    EnvironmentConfigManager config = EnvironmentConfigManager.getInstance();
    private final EmployeeDataAccess employeeDAo = new EmployeeDataAccess();
}

