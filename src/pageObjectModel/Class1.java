package pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Class1 {
WebDriver driver;
By uName = By.name("username");
By pwd = By.name("password");
By signIn = By.name("tdbl");

public Class1 (WebDriver driver)
{
	this.driver = driver;
}

public void typeUserName(String userName)
{
	driver.findElement(uName).sendKeys(userName);

}
public void typePassword()
{
	driver.findElement(pwd).sendKeys("Password1");
}
public void login()
{
	driver.findElement(signIn).click();
}
}
