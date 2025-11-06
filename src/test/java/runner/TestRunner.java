package runner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utiles.EmailUtils;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @AfterClass
    public void sendReportEmail() {
        String reportPath = System.getProperty("user.dir") + "/target/cucumber-report.html";
        EmailUtils.sendEmailWithAttachment(reportPath);
    }
}

