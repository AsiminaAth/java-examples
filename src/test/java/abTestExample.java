import com.applitools.eyes.*;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;

import static org.junit.Assert.assertEquals;


public class abTestExample {

    private EyesRunner runner = new ClassicRunner();
    private Eyes eyes = new Eyes(runner);
    private WebDriver driver;
    private static BatchInfo batch;

    @BeforeClass
    public static void batchInitialization(){
        batch = new BatchInfo("A/B Search Engines");
    }

    @Before
    public void setUp() throws Exception {

        eyes.setServerUrl("https://eyesapi.applitools.com");
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        eyes.setHideScrollbars(true);
        eyes.setHideCaret(true);
        eyes.setForceFullPageScreenshot(false);
        eyes.setStitchMode(StitchMode.CSS);
        //eyes.setMatchLevel(MatchLevel.LAYOUT2);
        eyes.setLogHandler(new StdoutLogHandler(true));
        eyes.setBatch(batch);

        driver = new ChromeDriver();
    }

    @Test
    public void SearchEngines() throws Exception {
        String searchUrls[] = { "google", "duckduckgo", "bing"};

        Random generator = new Random();
        int randomIndex = generator.nextInt(searchUrls.length);
        String url = searchUrls[randomIndex];

        driver.get("https://www." + url + ".com");
        eyes.open(driver, "My Search Engine", "Search Page", new RectangleSize(1200, 800));
        eyes.check(url, Target.window());
        eyes.closeAsync();
    }

    @After
    public void tearDown() throws Exception {
        TestResultsSummary allTestResults = runner.getAllTestResults(false);

        TestResultContainer[] results = allTestResults.getAllResults();
        for(TestResultContainer result: results){
            TestResults test = result.getTestResults();

            assertEquals(test.getName() + " has mismatches", 0, test.getMismatches());
        }

        driver.quit();
        eyes.abortIfNotClosed();
    }
}