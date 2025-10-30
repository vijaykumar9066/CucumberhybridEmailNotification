package utiles;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementsUtils {
	
	WebDriver driver;
	
	public ElementsUtils(WebDriver driver)
	{
		this.driver=driver;
	}
	
	public void clickOnElement(WebElement element,long durationInSeconds)
	{
		WebElement webelement = waitforelement(element,durationInSeconds);
		webelement.click();
	}
	
	public void typeTextintoEelement(WebElement element,String texttobetyped,long durationInSeconds)
	{
		
		WebElement webelement = waitforelement(element,durationInSeconds);
		webelement.click();
		webelement.clear();
		webelement.sendKeys(texttobetyped);
	}
	
	public WebElement waitforelement(WebElement element,long durationInSeconds)
	{
		WebElement webelement = null;
		try
		{
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(durationInSeconds));
			webelement= wait.until(ExpectedConditions.elementToBeClickable(element));
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		return webelement;
	}
	
	public void selectOptionInDropdown(WebElement element,String dropdownOption,long durationInSeconds)
	{
		WebElement webelement = waitforelement(element,durationInSeconds);
		Select select = new Select(element);
		select.selectByVisibleText(dropdownOption);
	}
	
	public void acceptAlert(WebElement element,long durationInSeconds)
	{
		Alert alert=waitForAlert(durationInSeconds);
		alert.accept();
		
	}
	
	public void dissmissAlert(WebElement element,long durationInSeconds)
	{
		Alert alert=waitForAlert(durationInSeconds);
		alert.dismiss();
	}

	public Alert waitForAlert(long durationInSeconds)
	{
		Alert alert=null;
		try 
		{
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(durationInSeconds));
			alert = wait.until(ExpectedConditions.alertIsPresent());
			

		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		return alert;
				
	}
	public void mouseHoverAndClick(WebElement element,long durationInSeconds)
	{
		WebElement webelement=waitForVisibilityOfElement(element,durationInSeconds);
		
		Actions actions = new Actions(driver);
		actions.moveToElement(webelement).click().build().perform();
	}
	
	public WebElement waitForVisibilityOfElement(WebElement element,long durationInSeconds)
	{
		WebElement webelement=null;
		try
		{
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(durationInSeconds));
			webelement=wait.until(ExpectedConditions.visibilityOf(element));
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		return webelement;
		
	}
	public void javaScriptClick(WebElement element,long durationInSeconds)
	{
		WebElement webelement = waitForVisibilityOfElement(element,durationInSeconds);
		JavascriptExecutor jse = ((JavascriptExecutor)driver);
		jse.executeScript("arguments[0].click();", webelement);
	}
	public void javaScriptType(WebElement element,long durationInSeconds,String texttobeTyped) 
	{
		WebElement webelement = waitForVisibilityOfElement(element,durationInSeconds);
		JavascriptExecutor jse = ((JavascriptExecutor)driver);
		jse.executeScript("arguments[0].value='"+texttobeTyped+"'", webelement);
	}
	
	public String GetTextFromElement(WebElement element,long durationInSeconds)
	{
		WebElement webelement = waitForVisibilityOfElement(element,durationInSeconds);
		return webelement.getText();
	}
	
	public boolean displayStatusOfElement(WebElement element,long durationInSeconds)
	{
		try 
		{
			WebElement webelement = waitForVisibilityOfElement(element,durationInSeconds);
			return webelement.isDisplayed();
		}
		catch(Throwable e)
		{
			return false;
		}
		
	}
}
