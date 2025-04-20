package interfaces;

public class BasePageUI {
    public static final String TEXTBOX_BY_NAME = "xpath=//label[text()='%s']//parent::div//following-sibling::div//input";
    public static final String ERROR_MESSAGE_BY_NAME ="xpath=//label[text()='%s']//parent::div//following-sibling::span";
}
