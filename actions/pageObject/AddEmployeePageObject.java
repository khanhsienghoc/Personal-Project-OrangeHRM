package pageObject;

import commons.BasePage;
import interfaces.AddEmployeeUI;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

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
        clickToElementByJavascript(driver, AddEmployeeUI.CREATE_LOGIN_DETAILS_TOGGLE);
    }
}
