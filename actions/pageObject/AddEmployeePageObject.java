package pageObject;

import commons.BasePage;
import commons.GlobalConstants;
import interfaces.AddEmployeeUI;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class AddEmployeePageObject extends BasePage {
    private final WebDriver driver;

    public AddEmployeePageObject(WebDriver driver){
        this.driver = driver;

    }
    /**
     * Input the text box by Name
     * @param name (name of the locator)
     * @param text (text to sendkey)
     */
    @Step("In the field {0}, input the value '{1}'")
    public void InputEmployeeInformationByName(String name, String text){
        waitElementVisible(driver, AddEmployeeUI.TEXTBOX_BY_NAME, name);
        sendKeyToElement(driver, AddEmployeeUI.TEXTBOX_BY_NAME,text, name);
    }
    /**
     * Check the toggle "Create Login Details"
     */
    @Step("Check the 'Create Login Details' toggle")
    public void checkToCreateLoginDetailsToggle(){
        waitForElementClickable(driver, AddEmployeeUI.CREATE_LOGIN_DETAILS_TOGGLE);
        clickToElementByJS(driver, AddEmployeeUI.CREATE_LOGIN_DETAILS_TOGGLE);
    }
    public void uploadEmployeeAvatar(String filePath){
        waitElementPresence(driver, AddEmployeeUI.UPLOAD_EMPLOYEE_AVATAR);
        uploadOneFile(driver, AddEmployeeUI.UPLOAD_EMPLOYEE_AVATAR, filePath);
    }
    public String getErrorMessageForEmployeeAvatar(){
        waitElementVisible(driver, AddEmployeeUI.UPLOAD_AVATAR_ERROR);
        return getElementText(driver, AddEmployeeUI.UPLOAD_AVATAR_ERROR);
    }
    public boolean isEmployeeImageUploaded(){
        waitElementVisible(driver, AddEmployeeUI.UPLOADED_EMPLOYEE_AVATAR);
        String src = getAttributeValue(driver, AddEmployeeUI.UPLOADED_EMPLOYEE_AVATAR, "src");
        if (src.startsWith("data:image")){
            return true;
        }
        return false;
    }
    public String getErrorMessageOfTextBoxByName(String textboxName){
        waitElementVisible(driver, AddEmployeeUI.ERROR_MESSAGE_BY_NAME,textboxName );
        return getElementText(driver, AddEmployeeUI.ERROR_MESSAGE_BY_NAME, textboxName);
    }
    public int getSizeOfErrorInEmployeeAvatar(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.SHORT_TIMEOUT));
        int size = getListElement(driver, AddEmployeeUI.UPLOAD_AVATAR_ERROR).size();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
        return size;
    }

}
