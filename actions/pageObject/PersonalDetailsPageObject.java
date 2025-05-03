package pageObject;

import commons.BasePage;
import interfaces.MyInfoPageUI;
import org.openqa.selenium.WebDriver;

public class PersonalDetailsPageObject extends BasePage {
    private final WebDriver driver;

    public PersonalDetailsPageObject(WebDriver driver){
        this.driver = driver;
    }
    public String getChosenValueFromNationalityDropdownByText(String text){
        waitElementVisible(driver, MyInfoPageUI.NATIONALITY_CHOSEN_VALUE, text);
        return getElementText(driver, MyInfoPageUI.NATIONALITY_CHOSEN_VALUE, text);
    }
}
