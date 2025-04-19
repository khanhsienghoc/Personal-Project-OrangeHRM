package commons;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import pageObject.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageUIsMeganto.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;


public class BasePage {
    public static BasePage BasePageObject() {
       return new BasePage();
    }
    protected void openURL(WebDriver driver, String url){
        driver.get(url);
        waitForPageLoad(driver);
    }
    protected String getPageTitle(WebDriver driver) {
        waitForPageLoad(driver);
        return driver.getTitle();
    }
    protected String getCurrentURL(WebDriver driver){
        return driver.getCurrentUrl();
    }
    protected String getPageSource(WebDriver driver){
        return driver.getPageSource();
    }
    protected void backToPage(WebDriver driver){
        driver.navigate().back();
    }
    protected void forwardToPage(WebDriver driver){
        driver.navigate().forward();
    }
    protected void refreshPage(WebDriver driver){
        driver.navigate().refresh();
    }
    protected Alert waitForAlertPresence(WebDriver driver){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }
    protected void acceptAlert(WebDriver driver){
        waitForAlertPresence(driver).accept();
    }
    protected void dismissAlert(WebDriver driver){
        waitForAlertPresence(driver).dismiss();
    }
    protected String getAlertText(WebDriver driver){
        return waitForAlertPresence(driver).getText();
    }
    protected String sendKeyTextToAlert(WebDriver driver, String text){
        waitForAlertPresence(driver).sendKeys(text);
        return text;
    }
    protected void switchToWindowByID (WebDriver driver, String windowID){
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows){
            if(!Objects.equals(window, windowID)){
                driver.switchTo().window(windowID);
                break;
            }
        }
    }
    protected void switchToWindowByTitle(WebDriver driver, String tabTitle){
        Set<String> allWindowsID = driver.getWindowHandles();
        for (String windowID : allWindowsID ){
            driver.switchTo().window(windowID);
            String actualTitle = driver.getTitle();
            if(Objects.equals(actualTitle, tabTitle)){
                break;
            }
        }
    }
    protected void closeAllTabWithoutParent(WebDriver driver, String parentID){
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String windowID : allWindowIDs){
            if(!Objects.equals(windowID, parentID)){
                driver.switchTo().window(windowID);
                driver.close();
            }
            driver.switchTo().window(parentID);
        }

    }
    private By getBylocatorType(String locatorType){
        By by = null;
        if (locatorType.startsWith("id=") || locatorType.startsWith("ID=") || locatorType.startsWith("Id=")){
            by = By.id(locatorType.substring(3));
        }else if(locatorType.startsWith("class=") || locatorType.startsWith("Class=") || locatorType.startsWith("CLASS=")){
            by = By.className( locatorType.substring(6));
        }else if(locatorType.startsWith("name=") || locatorType.startsWith("Name=") || locatorType.startsWith("NAME=")) {
            by = By.name(locatorType.substring(5));
        }else if(locatorType.startsWith("xpath=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPATH=")) {
            by = By.xpath(locatorType.substring(6));
        }
        else if(locatorType.startsWith("css=") || locatorType.startsWith("Css=") || locatorType.startsWith("CSS=")) {
            by = By.cssSelector(locatorType.substring(4));
        }
        else{
            throw new RuntimeException("locatorType type is not support");
        }
        return by;
    }

    private WebElement getElement(WebDriver driver, String locatorType){
        return driver.findElement(getBylocatorType(locatorType));
    }
    public List<WebElement> getListElement(WebDriver driver, String locatorType){
        return driver.findElements(getBylocatorType(locatorType));
    }
    public List<WebElement> getListElement(WebDriver driver, String locatorType, String...dynamicValue){
        return driver.findElements(getBylocatorType(getDynamicXpath(locatorType,dynamicValue)));
    }


    private String getDynamicXpath(String locatorType, String...values){
        if (locatorType.startsWith("xpath=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPATH=")){
            locatorType = String.format(locatorType,(Object[]) values);
        } return locatorType;
    }
    protected void clickToElement(WebDriver driver, String locatorType){
        getElement(driver, locatorType).click();
    }

    protected void clickToElement(WebDriver driver,String locatorType, String...dynamicValues){
        getElement(driver, getDynamicXpath(locatorType,dynamicValues)).click();
    }

    protected void sendKeyToElement(WebDriver driver, String locatorType, String text){
        WebElement element = getElement(driver, locatorType);
        element.clear();
        element.sendKeys(text);
    }
    protected void sendKeyToElement(WebDriver driver,String locatorType, String text, String...dynamicValues){
        WebElement element = getElement(driver, getDynamicXpath(locatorType,dynamicValues));
        element.clear();
        element.sendKeys(text);

    }
    protected void waitForPageLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    protected String getElementText(WebDriver driver, String locatorType){
        return getElement(driver, locatorType).getText();
    }
    protected String getElementText(WebDriver driver, String locatorType, String...dynamicValues){
        return getElement(driver, getDynamicXpath(locatorType,dynamicValues)).getText();
    }

    protected void selectItemInDefaultDropDown (WebDriver driver, String locatorType, String text){
        Select select = new Select(getElement(driver, locatorType));
        select.selectByVisibleText(text);
    }
    protected void selectItemInDefaultDropDown (WebDriver driver, String locatorType, String text, String...dynamicValue){
        Select select = new Select(getElement(driver,getDynamicXpath(locatorType,dynamicValue) ));
        select.selectByVisibleText(text);
    }

    protected String getFirstSelectedItemDefaultDropDown(WebDriver driver, String locatorType){
        Select select = new Select(getElement(driver, locatorType));
        return select.getFirstSelectedOption().getText();
    }
    protected boolean isDropDownMultiple(WebDriver driver, String locatorType){
        Select select = new Select(getElement(driver, locatorType));
        return select.isMultiple();
    }
    protected void selectItemInCustomDropDown(WebDriver driver,String parentLocator, String childLocator, String expectValue){
        getElement(driver, parentLocator).click();
        sleepInSecond(1);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        List<WebElement> allItems = explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getBylocatorType(childLocator)));
        for (WebElement item : allItems){
            if (item.getText().equals(expectValue)){
                scrollToElement(driver,item);
                sleepInSecond(1);
                item.click();
                break;
            }
        }
    }
    protected void selectItemInCustomDropDown(WebDriver driver,String parentLocator, String childLocator, String expectValue, String...dynamicValue){
        getElement(driver, getDynamicXpath(parentLocator,dynamicValue)).click();
        sleepInSecond(1);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        List<WebElement> allItems = explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getBylocatorType(getDynamicXpath(childLocator,dynamicValue))));
        for (WebElement item : allItems){
            if (item.getText().equals(expectValue)){
                scrollToElement(driver,item);
                sleepInSecond(1);
                item.click();
                break;
            }
        }
    }
    protected String getAttributeValue(WebDriver driver, String locatorType, String attributeName){
        return getElement(driver, locatorType).getDomProperty(attributeName);
    }
    protected String getAttributeValue(WebDriver driver, String locatorType, String attributeName , String...dynamicValues){
        return getElement(driver, getDynamicXpath(locatorType,dynamicValues)).getDomProperty(attributeName);
    }
    protected String getCSSCValue(WebDriver driver, String locatorType, String propertyName){
        return getElement(driver, locatorType).getCssValue(propertyName);
    }
    protected String getCSSCValue(WebDriver driver, String locatorType, String propertyName,String...dynamicValues){
        return getElement(driver, getDynamicXpath(locatorType,dynamicValues)).getCssValue(propertyName);
    }
    protected String getHexaColorFromRGBA(String rgbaValue){
        return Color.fromString(rgbaValue).asHex();
    }
    private List<WebElement> findMulipleElements(WebDriver driver, String locatorTypes){
        return driver.findElements(getBylocatorType(locatorTypes));
    }
    protected int getElementsSize(WebDriver driver, String locatorTypes){
        return findMulipleElements(driver, locatorTypes).size();
    }
    protected int getElementsSize(WebDriver driver, String locatorTypes, String...dynamicValues){
        return findMulipleElements(driver, getDynamicXpath(locatorTypes,dynamicValues)).size();
    }
    private boolean isElementSelected(WebDriver driver, String locatorType){
        return getElement(driver, locatorType).isSelected();
    }
    protected void checkTheCheckBox(WebDriver driver, String locatorType){
        if (!isElementSelected(driver, locatorType)){
            getElement(driver, locatorType).click();
        }
    }
    protected void checkTheCheckBox(WebDriver driver, String locatorType, String...dynamicValue){
        if (!isElementSelected(driver, getDynamicXpath(locatorType,dynamicValue))){
            getElement(driver, getDynamicXpath(locatorType,dynamicValue)).click();
        }
    }

    protected void uncheckTheCheckBox(WebDriver driver, String locatorType){
        if (isElementSelected(driver, locatorType)){
            getElement(driver, locatorType).click();
        }
    }
    protected void uncheckTheCheckBox(WebDriver driver,String locatorType, String...dynamicValue){
        if (isElementSelected(driver, getDynamicXpath(locatorType,dynamicValue))){
            getElement(driver, getDynamicXpath(locatorType,dynamicValue)).click();
        }
    }
    protected boolean isElementDisplayed(WebDriver driver,String locatorType){
        return getElement(driver, locatorType).isDisplayed();
    }
    protected boolean isElementDisplayed(WebDriver driver,String locatorType, String...dynamicValues){
        return getElement(driver, getDynamicXpath(locatorType,dynamicValues)).isDisplayed();
    }
    protected boolean isElementEnabled(WebDriver driver,String locatorType){
        return getElement(driver, locatorType).isEnabled();
    }
    protected void switchToFrame(WebDriver driver, String locatorType){
        driver.switchTo().frame(getElement(driver, locatorType));
    }
    protected void switchtoDefaultContent(WebDriver driver){
        driver.switchTo().defaultContent();
    }
    protected void moveToElement(WebDriver driver, String locatorType){
        Actions actions = new Actions(driver);
        actions.moveToElement(getElement(driver, locatorType)).perform();
    }
    protected void moveToElement(WebDriver driver, String locatorType,String...dynamicValues ){
        Actions actions = new Actions(driver);
        actions.moveToElement(getElement(driver, getDynamicXpath(locatorType,dynamicValues))).perform();
    }
    protected void doubleClickOnElements(WebDriver driver, String locatorType){
        Actions action = new Actions(driver);
        action.doubleClick(getElement(driver, locatorType)).perform();
    }
    protected void rightClick(WebDriver driver, String locatorType){
        Actions action = new Actions(driver);
        action.contextClick(getElement(driver, locatorType)).perform();
    }
    protected void dragAndDropFromAndTo(WebDriver driver, String locatorTypeFrom, String locatorTypeTo){
        Actions action = new Actions(driver);
        action.dragAndDrop(getElement(driver, locatorTypeFrom), getElement(driver,locatorTypeTo)).perform();
    }
    protected void pressKeyToElemnt (WebDriver driver, String locatorType, Keys key){
        Actions action = new Actions(driver);
        action.sendKeys(getElement(driver,locatorType), key).perform();
    }
    protected void pressKeyToElemnt (WebDriver driver, String locatorType, Keys key, String...dynamicValue){
        Actions action = new Actions(driver);
        action.sendKeys(getElement(driver,getDynamicXpath(locatorType,dynamicValue)), key).perform();
    }
    protected void uploadOneFile(WebDriver driver, String locatorType, String filePath){
        getElement(driver, locatorType).sendKeys(GlobalConstants.UPLOAD_FILE);
    }
//    protected void uploadMultipleFiles(WebDriver driver, String locatorType, String...fileNames){
//        String filePath = GlobalConstants.UPLOAD_FILE;
//        String fullFileName="";
//        for (String file : fileNames){
//            fullFileName = fullFileName + filePath + file + "\n";
//        }
//        fullFileName = fullFileName.trim();
//        getElement(driver, locatorType).sendKeys(fullFileName);
//    }
    protected void scrollToElement(WebDriver driver, WebElement locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", locatorType);
    }
    protected void clickToElementByJavascript(WebDriver driver, String locatorType){
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getElement(driver, locatorType);
        if (element != null) {
            jsExecutor.executeScript("arguments[0].click();", element);
        } else {
            throw new NoSuchElementException("Element not found: " + locatorType);
        }
    }
    protected void clickToElementByJavascript(WebDriver driver, String locatorType, String...dynamicValues){
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getElement(driver, getDynamicXpath(locatorType,dynamicValues));
        if (element != null) {
            jsExecutor.executeScript("arguments[0].click();", element);
        } else {
            throw new NoSuchElementException("Element not found: " + locatorType);
        }
    }
    protected void hightlightElement(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getElement(driver, locatorType);
        String originalStyle = element.getDomProperty("style");
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
        sleepInSecond(2);
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
    }
    protected void scrollToElementOnTopByJS(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(driver, locatorType));
    }
    protected void scrollToElementOnDownByJS(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(driver, locatorType));
    }
    protected void scrollToBottomPageByJS(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }
    protected void setAttributeInDOM(WebDriver driver, String locatorType, String attributeName, String attributeValue) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute('" + attributeName + "', '" + attributeValue + "');", getElement(driver, locatorType));
    }
    protected void removeAttributeInDOM(WebDriver driver, String locatorType, String attributeRemove) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(driver, locatorType));
    }
    protected void removeAttributeInDOM(WebDriver driver, String locatorType, String attributeRemove, String...dynamicValues) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(driver, getDynamicXpath(locatorType,dynamicValues)));
    }
    protected String getAttributeInDOMByJS(WebDriver driver, String locatorType, String attributeName) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return arguments[0].getAttribute('" + attributeName + "');", getElement(driver, locatorType));
    }
    protected String getElementValidationMessage(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;",getElement(driver, locatorType));
    }
    protected boolean isImageLoaded(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (boolean) jsExecutor.executeScript("return arguments[0].complete " + "&& typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
                getElement(driver, locatorType));
    }
    protected void waitElementVisible(WebDriver driver, String locatorType){
        waitForPageLoad(driver);
        WebDriverWait explicitWait  = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getBylocatorType(locatorType)));
    }
    protected void waitElementVisible(WebDriver driver, String locatorType, String...dynamicValues){
        waitForPageLoad(driver);
        WebDriverWait explicitWait  = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getBylocatorType(getDynamicXpath(locatorType,dynamicValues))));
    }
    protected void waitListElementVisible(WebDriver driver, String locatorType){
        waitForPageLoad(driver);
        WebDriverWait explicitWait  = new WebDriverWait(driver, Duration.ofSeconds(60));
        explicitWait.until(ExpectedConditions.visibilityOfAllElements(findMulipleElements(driver, locatorType)));
    }
    protected void waitListElementVisible(WebDriver driver, String locatorType,String...dynamicValues){
        waitForPageLoad(driver);
        WebDriverWait explicitWait  = new WebDriverWait(driver, Duration.ofSeconds(60));
        explicitWait.until(ExpectedConditions.visibilityOfAllElements(findMulipleElements(driver, getDynamicXpath(locatorType,dynamicValues))));
    }
    protected void waitElementInvisible(WebDriver driver, String locatorType){
        waitForPageLoad(driver);
        WebDriverWait explicitWait  = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getBylocatorType(locatorType)));
    }
    protected void waitListElementInvisible (WebDriver driver, String locatorType){
        waitForPageLoad(driver);
        WebDriverWait expilicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        expilicitWait.until(ExpectedConditions.invisibilityOfAllElements(findMulipleElements(driver, locatorType)));
    }
    protected void waitForElementSelected (WebDriver driver, String locatorType){
        waitForPageLoad(driver);
        WebDriverWait expilicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        expilicitWait.until(ExpectedConditions.elementToBeClickable(getBylocatorType(locatorType)));
    }
    protected void waitForElementClickable(WebDriver driver, String locatorType){
        waitForPageLoad(driver);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        explicitWait.until(ExpectedConditions.elementToBeClickable(getBylocatorType(locatorType)));
    }
    protected void waitForElementClickable(WebDriver driver, String locatorType, String...dynamicValues){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
        explicitWait.until(ExpectedConditions.elementToBeClickable(getBylocatorType(getDynamicXpath(locatorType,dynamicValues))));
    }
    protected boolean isElementClickable(WebDriver driver, String locatorType) {
        try {
            WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeOut));
            explicitWait.until(ExpectedConditions.elementToBeClickable(getBylocatorType(locatorType)));
            return true;
        }catch(Exception e){
            return false;
        }

    }
    public void sleepInSecond(long time){
        try {
            Thread.sleep(time *1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    public void openPageAtMyAccountByName(WebDriver driver,String className, String pageName){
        waitForElementClickable(driver, BasePageUI.DYNAMIC_LINK_SIDEBAR,className,pageName);
        clickToElement(driver,BasePageUI.DYNAMIC_LINK_SIDEBAR,className,pageName);
    }
    public void refreshCurrentPage(WebDriver driver){
        driver.navigate().refresh();
    }

    public Set<Cookie>  getAllCookies(WebDriver driver){
        return driver.manage().getCookies();
    }
    public void setCookies(WebDriver driver, Set<Cookie> cookies){
        for(Cookie cookie : cookies){
            driver.manage().addCookie(cookie);
        }
        sleepInSecond(3);
    }
    
    @Step("Click the arrow next to the name")
    public void clickOnProfileDropdownArrow(WebDriver driver){
        clickToElement(driver, BasePageUI.PROFILE_DROPDOWN_ARROW);
    }
    @Step("Click Logout")
    public HomePageObject clickLogout(WebDriver driver){
        waitElementVisible(driver, BasePageUI.LOGOUT_BUTTON);
        clickToElement(driver, BasePageUI.LOGOUT_BUTTON);
        return PageGeneratorManager.getHomePage(driver);
    }
    @Step("Click on the Profile Arrow and click My Account")
    public MyAccountObject clickMyAccountHeader(WebDriver driver){
        clickOnProfileDropdownArrow(driver);
        waitElementVisible(driver, BasePageUI.MY_ACCOUNT_HEADER);
        clickToElement(driver, BasePageUI.MY_ACCOUNT_HEADER);
        return PageGeneratorManager.getMyAccountPage(driver);
    }
    public HomePageObject clickLogoToHomePage(WebDriver driver){
        waitElementVisible(driver,BasePageUI.LOGO_TO_HOMEPAGE_HYPERLINK);
        clickToElement(driver,BasePageUI.LOGO_TO_HOMEPAGE_HYPERLINK);
        return PageGeneratorManager.getHomePage(driver);
    }
    public void openURLFooter(WebDriver driver, String dynamicValues){
        waitListElementVisible(driver, getDynamicXpath(BasePageUI.DYNAMIC_FOOTER_HYPERLINK,dynamicValues));
        clickToElement(driver,getDynamicXpath(BasePageUI.DYNAMIC_FOOTER_HYPERLINK,dynamicValues));
    }
    public void inputSearchTextbox(WebDriver driver, String text){
        waitElementVisible(driver, BasePageUI.SEARCH_TEXT_BOX);
        sendKeyToElement(driver, BasePageUI.SEARCH_TEXT_BOX, text);

    }
    public SearchResultPageObject searchAProduct(WebDriver driver, String text){
        inputSearchTextbox(driver, text);
        clickSearchButton(driver);
        return PageGeneratorManager.getSearchResult(driver);
    }
    public SearchResultPageObject clickSearchButton(WebDriver driver){
        waitForElementClickable(driver,BasePageUI.SEARCH_SUBMIT_BUTTON);
        clickToElement(driver,BasePageUI.SEARCH_SUBMIT_BUTTON);
        return PageGeneratorManager.getSearchResult(driver);
    }
    public void clickToMenuHeaderLevel1 (WebDriver driver, String dynamicValues){
        waitElementVisible(driver,BasePageUI.DYNAMIC_MENU_ONE_LEVEL_HEADER, dynamicValues);
        moveToElement(driver, BasePageUI.DYNAMIC_MENU_ONE_LEVEL_HEADER,dynamicValues);
        clickToElement(driver, BasePageUI.DYNAMIC_MENU_ONE_LEVEL_HEADER,dynamicValues);
    }
    public void clickToMenuHeaderLevel2 (WebDriver driver, String...dynamicValues){
        waitElementVisible(driver,BasePageUI.DYNAMIC_MENU_ONE_LEVEL_HEADER, dynamicValues);
        moveToElement(driver, BasePageUI.DYNAMIC_MENU_ONE_LEVEL_HEADER,dynamicValues);
        moveToElement(driver, BasePageUI.DYNAMIC_MENU_TWO_LEVEL_HEADER,dynamicValues);
        clickToElement(driver, BasePageUI.DYNAMIC_MENU_TWO_LEVEL_HEADER,dynamicValues);
    }
    public void clickToMenuHeaderLevel3 (WebDriver driver, String...dynamicValues){
        waitElementVisible(driver,BasePageUI.DYNAMIC_MENU_ONE_LEVEL_HEADER, dynamicValues);
        moveToElement(driver, BasePageUI.DYNAMIC_MENU_ONE_LEVEL_HEADER,dynamicValues);
        moveToElement(driver, BasePageUI.DYNAMIC_MENU_TWO_LEVEL_HEADER,dynamicValues);
        moveToElement(driver, BasePageUI.DYNAMIC_MENU_THREE_LEVEL_HEADER,dynamicValues);
        clickToElement(driver, BasePageUI.DYNAMIC_MENU_THREE_LEVEL_HEADER,dynamicValues);
    }
    public int getNumberofProductInCart(WebDriver driver){
        waitElementVisible(driver,BasePageUI.NUMBER_OF_PRODUCTS_IN_CART);
        String text = getElementText(driver, BasePageUI.NUMBER_OF_PRODUCTS_IN_CART).trim();
        int num = Integer.parseInt(text);
        return num;
    }
    public ProductDetailPageObject clickOnAProduct(WebDriver driver, String productName){
        waitElementVisible(driver,BasePageUI.DYNAMIC_PRODUCT_HYPERLINK, productName);
        clickToElement(driver,BasePageUI.DYNAMIC_PRODUCT_HYPERLINK, productName);
        return PageGeneratorManager.getProductDetail(driver);
    }
    public void clickAddToWishListOrAddToCompare(WebDriver driver,String productname, String addValue){
        waitElementVisible(driver,BasePageUI.DYNAMIC_PRODUCT_HYPERLINK,productname);
        moveToElement(driver,BasePageUI.DYNAMIC_PRODUCT_HYPERLINK,productname);
        waitElementVisible(driver,BasePageUI.DYNAMIC_ADD_TO_WISHLIST_COMPARE,productname,addValue);
        clickToElement(driver,BasePageUI.DYNAMIC_ADD_TO_WISHLIST_COMPARE,productname,addValue);

    }
    public String getAddProductCompareSuccessMessage(WebDriver driver){
        waitElementVisible(driver, BasePageUI.ADD_PRODUCT_COMPARE_SUCCESS_MESSAGE);
        return getElementText(driver, BasePageUI.ADD_PRODUCT_COMPARE_SUCCESS_MESSAGE);
    }
    public CompareListPageObject clickCompareListHyperlink(WebDriver driver){
        waitElementVisible(driver, BasePageUI.ADD_PRODUCT_COMPARE_SUCCESS_MESSAGE);
        clickToElement(driver, BasePageUI.COMPARE_LIST_HYPERLINK);
        return PageGeneratorManager.getCompareListPage(driver);
    }
    public String getProductName(WebDriver driver, String productName){
        waitElementVisible(driver,BasePageUI.DYNAMIC_PRODUCT_HYPERLINK, productName);
        return getElementText(driver,BasePageUI.DYNAMIC_PRODUCT_HYPERLINK, productName);
    }
    public String getProductPrice(WebDriver driver, String productName){
        waitElementVisible(driver,BasePageUI.DYNAMIC_PRODUCT_PRICE, productName);
        return getElementText(driver,BasePageUI.DYNAMIC_PRODUCT_PRICE, productName);
    }
    public WebElement getLatestRemoveButton(WebDriver driver) {
        WebElement removeButton = null;

        for (int i = 0; i < 3; i++) { // Thử tối đa 3 lần nếu gặp lỗi stale
            try {
                removeButton = getElement(driver, CompareListPageUI.REMOVE_ALL_COMPARE_PRODUCT_BUTTON);
                return removeButton; // Nếu lấy thành công, trả về ngay
            } catch (StaleElementReferenceException e) {
                System.out.println("Lỗi stale, thử lại lần " + (i + 1));
            }
        }

        throw new NoSuchElementException("Không tìm thấy phần tử Remove Button sau 3 lần thử!");
    }

    private long longTimeOut = GlobalConstants.LONG_TIMEOUT;

    /**
     * Click to a Hyperlink by text of the locator (for example: "Create an Account", "Sign In" hyperlink)
     * @param driver
     * @param field
     */
    @Step("Click to {0} link")
    @SuppressWarnings("unchecked")
    public <T extends BasePage> T clickToHyperlinkByText(WebDriver driver, String field){
        waitForElementClickable(driver, BasePageUI.DYNAMIC_LINK_BY_TEXT,field);
        clickToElement(driver, BasePageUI.DYNAMIC_LINK_BY_TEXT, field);
        if(field == "Sign In"){
            return (T) PageGeneratorManager.getLoginPage(driver);
        } else if (field == "Create an Account"){
            return (T) PageGeneratorManager.getRegisterPage(driver);
        }
        return (T) this;
    }

    /**
     * Input text in a field by id of the locator (for example: field "firstname" with value "admin"
     * @param driver
     * @param id (input id of the locator)
     * @param text (input the text to sendkey)
     */
    @Step("Input the '{1}' field with value: '{2}'")
    public void inputToTextboxByID(WebDriver driver, String id, String text){
        waitElementVisible(driver, BasePageUI.DYNAMIC_TEXTBOX_BY_ID, id);
        sendKeyToElement(driver, BasePageUI.DYNAMIC_TEXTBOX_BY_ID,text, id);
    }

    /**
     * Get the error message when the text box is empty
     * @param driver
     * @param id
     * @return text
     */
    public String getErrorMessageForTextbox(WebDriver driver, String id){
        waitElementVisible(driver, BasePageUI.DYNAMIC_TEXTBOX_ERROR_BY_ID,id);
        return getElementText(driver, BasePageUI.DYNAMIC_TEXTBOX_ERROR_BY_ID, id);
    }
    /**
     *Asseert Equal for the expected and actual text when leave the field empty
     * @param expectedText
     * @param field
     */
    @Step("Verify the error message for the invalid input with expected text '{1}' for the field '{2}'")
    public void assertEqualErrorMessageForTextBox(WebDriver driver, String expectedText, String field){
        Assertions.assertEquals(expectedText,getErrorMessageForTextbox(driver, field));
    }

    /**
     * Assert Equals for the Page title by inputting the expected Page title
     * @param driver
     * @param expectedTitle
     */
    @Step("Assert Equals the page title to be : '{1}'")
    public void assertPageTitle(WebDriver driver, String expectedTitle){
        waitForPageLoad(driver);
        sleepInSecond(5);
        Assertions.assertEquals(expectedTitle, getPageTitle(driver));
    }

}


