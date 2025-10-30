package runner;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import utiles.EmailUtils;

@RunWith(Cucumber.class)
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
public class TestRunner {

    @AfterClass
    public static void sendReportEmail() {

        String reportPath = System.getProperty("user.dir")
                + "/target/cucumber-report.html";

        EmailUtils.sendEmailWithAttachment(reportPath);
    }
}

