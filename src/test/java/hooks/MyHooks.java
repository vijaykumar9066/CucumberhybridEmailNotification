package hooks;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utiles.ConfigReader;

public class MyHooks {

    WebDriver driver;

    @Before
    public void setup(Scenario scenario) {
        Properties prop = ConfigReader.loadConfigProperties();

        // Read browser from system property passed from TestNG
        String browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = prop.getProperty("localbrowser"); // fallback
        }

        driver = DriverFactory.initializeBrowser(browser);
        driver.get(prop.getProperty("url"));
    }

    @After
    public void teardown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] src = ((org.openqa.selenium.TakesScreenshot) driver)
                    .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
            scenario.attach(src, "image/png", scenario.getName().replace(" ", "_"));
        }
        DriverFactory.quitDriver();
    }
}
