package factory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import utiles.ConfigReader;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver initializeBrowser(String browserName) {
        Properties prop = ConfigReader.loadConfigProperties();
        boolean runGrid = Boolean.parseBoolean(prop.getProperty("seleniumgrid"));
        String gridUrl = prop.getProperty("gridurl");
        boolean headless = Boolean.parseBoolean(prop.getProperty("headless"));

        WebDriver localDriver;

        if (runGrid) {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu","--no-sandbox",
                            "--disable-dev-shm-usage","--remote-allow-origins=*");
                    localDriver = new RemoteWebDriver(getGridUrl(gridUrl), chromeOptions);
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) firefoxOptions.addArguments("--headless");
                    localDriver = new RemoteWebDriver(getGridUrl(gridUrl), firefoxOptions);
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headless) edgeOptions.addArguments("--headless=new");
                    localDriver = new RemoteWebDriver(getGridUrl(gridUrl), edgeOptions);
                    break;

                default:
                    throw new IllegalArgumentException("Invalid Browser: " + browserName);
            }
        } else {
            browserName = prop.getProperty("localbrowser");
            switch (browserName.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    localDriver = new org.openqa.selenium.chrome.ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    localDriver = new org.openqa.selenium.firefox.FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    localDriver = new org.openqa.selenium.edge.EdgeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Browser: " + browserName);
            }
        }

        // Common setup
        localDriver.manage().deleteAllCookies();
        localDriver.manage().window().maximize();
        localDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
        localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.set(localDriver);
        return getDriver();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    private static URL getGridUrl(String urlString) {
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            throw new RuntimeException("❌ Invalid Grid URL: " + urlString);
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
