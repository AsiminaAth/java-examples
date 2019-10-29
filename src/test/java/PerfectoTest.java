import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class PerfectoTest {

    private String USER_NAME = "moshe.milman@applitools.com";
    private String PASSWORD = "Perfecto1";
    private String url = "https://partners.perfectomobile.com/nexperience/perfectomobile/wd/hub";

    private Eyes eyes = new Eyes();
    private static BatchInfo batch;
    private WebDriver driver;

    @BeforeClass
    public static void batchInitialization(){
        batch = new BatchInfo("Perfecto Tests");
    }

    @Before
    public void setUp() throws Exception {
        eyes.setServerUrl("https://eyesapi.applitools.com");
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        eyes.setForceFullPageScreenshot(true);
        eyes.setStitchMode(StitchMode.CSS);
        eyes.setBatch(batch);
        eyes.setLogHandler(new StdoutLogHandler(true));
    }

    @Test
    public void AndroidTest() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities("chrome", "", Platform.ANY);
        capabilities.setCapability("user", USER_NAME);
        capabilities.setCapability("password", PASSWORD);
        capabilities.setCapability("deviceName", "520326A2B4809359");

        driver = new AndroidDriver(new URL(url), capabilities);

        driver.get("https://www.github.com");

        eyes.open(driver, "GitHub Android", "Android Test");
        eyes.check("Android Page", Target.window());
        eyes.close();
    }

    @Test
    public void iOSTest() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities("safari", "", Platform.ANY);
        capabilities.setCapability("user", USER_NAME);
        capabilities.setCapability("password", PASSWORD);
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "12.3");
        capabilities.setCapability("manufacturer", "Apple");
        capabilities.setCapability("model", "iPhone-7 Plus");
        capabilities.setCapability("location", "NA-US-BOS");
        capabilities.setCapability("resolution", "1242 x 2208");
        //capabilities.setCapability("app", "https://store.applitools.com/download/iOS.TestApp.app.zip");

        //does not work
        //driver = new IOSDriver(new URL(url), capabilities);
        //works
        driver = new RemoteWebDriver(new URL(url), capabilities);

        driver.get("https://www.github.com");

        eyes.open(driver, "GitHub iOS", "iOS Test");
        eyes.check("iOS Page", Target.window());
        eyes.close();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        eyes.abortIfNotClosed();
    }
}