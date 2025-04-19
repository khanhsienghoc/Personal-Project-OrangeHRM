package commons;
import java.io.File;

public class GlobalConstants {
    public static final String PORTAL_TESTING_URL ="https://magento.softwaretestingboard.com/";
    public static final String PORTAL_DEV_URL = "https://magento-demo.mageplaza.com/";
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String UPLOAD_FILE= PROJECT_PATH + File.separator +"uploadFiles" + File.separator;
    public static final String DOWNLOAD_FILE = PROJECT_PATH + File.separator +"downloadFiles";
    public static final String BROWSER_lOG = PROJECT_PATH + File.separator +"browserLogs";
    public static final String DRAG_DROP_HTML5 = PROJECT_PATH + File.separator +"dragDropHTML5";
    public static final String DB_TEST_URL = "";
    public static final String DB_TEST_USERNAME = "";
    public static final String DB_TEST_PASSWORD = "";
    public static final long SHORT_TIMEOUT = 5;
    public static final long LONG_TIMEOUT = 30;
    public static final long RETRY_TEST_FAIL = 3;
    public static final String REPORTNG_SCREENSHOT = PROJECT_PATH +  File.separator + "ReportNGImages";
    public static final String EXTENTREPORT_SCREENSHOT = PROJECT_PATH +  File.separator + "ExtentReportImages";
    public static final String JAVA_VERSION = System.getProperty("java.version");

}
