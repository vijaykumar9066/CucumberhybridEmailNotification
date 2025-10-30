package stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;

public class Login {

    WebDriver driver = DriverFactory.getDriver();
    LoginPage loginpage = new LoginPage(driver);
    

    @Given("User is navigate to login page")
    public void user_is_navigate_to_login_page() {
        loginpage.ClickOnAvatarimage();
        loginpage.ClickOnSignIn();
    }

    @When("User enters valid username {string}")
    public void user_enters_valid_username(String validusername) {
        loginpage.enterUserName(validusername);
    }

    @When("User enters valid password {string}")
    public void user_enters_valid_password(String validpassword) {
        loginpage.enterpassword(validpassword);
    }

    @When("User clicks on the login button")
    public void user_clicks_on_the_login_button() {
        loginpage.clickonloginbtn();
    }

    @Then("User should see profile selection page once login success")
    public void user_should_see_profile_selection_page_once_login_success() {
        Assert.assertTrue(loginpage.verifycreateprofilebuttonafterloginsuccess());
    }

    @When("User enters Invalid password {string}")
    public void user_enters_invalid_password(String invalidpassword) {
        loginpage.enterpassword(invalidpassword);
    }

    @Then("User should see an invalid password error message")
    public void user_should_see_an_invalid_password_error_message() {
        Assert.assertTrue(loginpage.verifyinvalidpassworderrormsg());
    }

    @When("User enters Invalid username {string}")
    public void user_enters_invalid_username(String invalidusername) {
        loginpage.enterUserName(invalidusername);
    }

    @Then("User should see an invalid username error message")
    public void user_should_see_an_invalid_username_error_message() {
        Assert.assertTrue(loginpage.verifyinvalidusernameerrormsg());
    }

    @When("User without enters username")
    public void user_without_enters_username() {
        loginpage.enterUserName("");
    }

    @When("User without enters password")
    public void user_without_enters_password() {
        loginpage.enterpassword("");
    }

    @Then("User should see an empty field error message")
    public void user_should_see_an_empty_field_error_message() {
        Assert.assertTrue(loginpage.verifyemptyfielderrormsg());
    }
}

