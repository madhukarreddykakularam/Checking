package pageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PracticePageFactory {
	WebDriver driver;
	
	/*
	 * @FindBy(id="user_login") WebElement Username;
	 */
	public PracticePageFactory(WebDriver driver)
	{
		this.driver=driver;
	}
	@FindBy(how=How.ID, using="user_login")
	@CacheLookup
	WebElement username;
	@FindBy(how=How.ID,using="user_pass")
	WebElement password;
	@FindBy(how=How.ID,using="wp-submit")
	WebElement Login;
	public void login(String userid,String Passwrd)
	{
		username.sendKeys("admin");
		password.sendKeys("demo123");
		Login.click();
		
	}
	
	
}