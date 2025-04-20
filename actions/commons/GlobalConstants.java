package commons;
import java.io.File;

public class GlobalConstants {
    public static final String PORTAL_TESTING_URL ="https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    public static final String PORTAL_DEV_URL ="https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String UPLOAD_FILE= PROJECT_PATH + File.separator +"uploadFiles" + File.separator;
    public static final long SHORT_TIMEOUT = 5;
    public static final long LONG_TIMEOUT = 30;
//    public static final String REPORTNG_SCREENSHOT = PROJECT_PATH +  File.separator + "ReportNGImages";
//    public static final String EXTENTREPORT_SCREENSHOT = PROJECT_PATH +  File.separator + "ExtentReportImages";
    public static final String JAVA_VERSION = System.getProperty("java.version");

}
