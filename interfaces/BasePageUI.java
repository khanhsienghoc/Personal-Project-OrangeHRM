package interfaces;

public class BasePageUI {
    public static final String TEXTBOX_BY_NAME = "xpath=//label[text()='%s']//parent::div//following-sibling::div//input";
    public static final String ERROR_MESSAGE_BY_NAME ="xpath=//label[text()='%s']//parent::div//following-sibling::span";
    public static final String MENU_BY_TEXT = "xpath=//span[text()='%s']//parent::a";
    public static final String BUTTON_BY_NAME ="xpath=//button[contains(.,'%s')]";
    public static final String SUCCESS_SAVE_POPUP ="xpath=//div[@id='oxd-toaster_1']";
    public static final String PROFILE_DROPDOWN = "xpath=//p[@class='oxd-userdropdown-name']//parent::span//i";
    public static final String PROFILE_OPTION_BY_TEXT ="xpath=//a[text()='%s']";
}
