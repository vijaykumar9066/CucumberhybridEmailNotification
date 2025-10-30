package hooks;

import java.util.Properties;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utiles.ConfigReader;

public class MyHooks {

    WebDriver driver;

    @Before
    public void setup() {

        Properties prop = ConfigReader.loadConfigProperties();
        driver = DriverFactory.initializeBrowser(prop.getProperty("browser"));
        driver.get(prop.getProperty("url"));
    }

    @After
    public void teardown(Scenario scenario) {

        if (scenario.isFailed()) {
            String scenarioName = scenario.getName().replace(" ", "_");

            byte[] src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(src, "image/png", scenarioName);
        }

        if (driver != null) {
            driver.quit();
        }
    }
}




