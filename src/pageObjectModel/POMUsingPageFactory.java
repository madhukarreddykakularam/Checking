package pageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class POMUsingPageFactory {
	WebDriver driver;
public POMUsingPageFactory(WebDriver driver) {
	this.driver = driver;
}
	
	@FindBy(id="username")
	WebElement username1;
	
	@FindBy(how=How.ID, using="username") WebElement username;
}
