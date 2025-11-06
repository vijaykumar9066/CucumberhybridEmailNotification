package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utiles.ElementsUtils;
import utiles.commonutils;

public class LoginPage {
	
	WebDriver driver;
	ElementsUtils elementsUtils;

	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		elementsUtils = new ElementsUtils(driver);
	}
	//img[@src='https://soapboxtesting.riversilica.com/genesis/medialocal/avatars/user-avatar.png']
	@FindBy(xpath="//img[@alt='user']")
	private WebElement clickingavatarimage;
	
	@FindBy(xpath="//h6[normalize-space()='SIGN IN']")
	private WebElement clickonSignIn;
	
	@FindBy(xpath="//input[@placeholder='User Name']")
	private WebElement enterusername;
	
	@FindBy(xpath="//input[@placeholder='Password']")
	private WebElement enterpassword;
	
	@FindBy(xpath="//button[normalize-space()='Sign In']")
	private WebElement clickonloginbtn;
	
	@FindBy(xpath="//img[@alt='Create']")
	private WebElement aftersuccessfullyloginverfiyCreateprofileIcon;
	
	@FindBy(xpath="//div[@id='swal2-html-container']")
	private WebElement invalidpassworderrormessage;
	
	@FindBy(xpath="//div[@id='swal2-html-container']")
	private WebElement invalidusernameerrormessage;
	
	@FindBy(xpath="//div[@id='swal2-html-container']")
	private WebElement emptyfielderrormsg;
	
	public void ClickOnAvatarimage()
	{
		elementsUtils.clickOnElement(clickingavatarimage,commonutils.EXPLICIT_WAIT_BASIC_TIME);
		
	}
	
	public void ClickOnSignIn()
	{
		elementsUtils.clickOnElement(clickonSignIn,commonutils.EXPLICIT_WAIT_BASIC_TIME);
		
	}
	
	public void enterUserName(String username)
	{
		elementsUtils.typeTextintoEelement(enterusername, username,commonutils.EXPLICIT_WAIT_BASIC_TIME);
		
	}
	
	public void enterpassword(String password)
	{
		elementsUtils.typeTextintoEelement(enterpassword, password,commonutils.EXPLICIT_WAIT_BASIC_TIME);
		
	}
	
	public void clickonloginbtn()
	{
		elementsUtils.clickOnElement(clickonloginbtn,commonutils.EXPLICIT_WAIT_BASIC_TIME);
		
	}
	
	public boolean verifycreateprofilebuttonafterloginsuccess()
	{
		return elementsUtils.displayStatusOfElement(aftersuccessfullyloginverfiyCreateprofileIcon, commonutils.EXPLICIT_WAIT_BASIC_TIME);
		
	}
	
	public boolean verifyinvalidpassworderrormsg()
	{
		return elementsUtils.displayStatusOfElement(invalidpassworderrormessage, commonutils.EXPLICIT_WAIT_BASIC_TIME);
		
	}
	
	public boolean verifyinvalidusernameerrormsg()
	{
		return elementsUtils.displayStatusOfElement(invalidusernameerrormessage, commonutils.EXPLICIT_WAIT_BASIC_TIME);
	}
	
	public boolean verifyemptyfielderrormsg()
	{
		return elementsUtils.displayStatusOfElement(emptyfielderrormsg, commonutils.EXPLICIT_WAIT_BASIC_TIME);

	}
	
}
