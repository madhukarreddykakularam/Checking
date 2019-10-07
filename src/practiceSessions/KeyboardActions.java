package practiceSessions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class KeyboardActions {
@Test
public void keybrdActions() {
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
	WebDriver driver =new ChromeDriver();
	driver.get("https://www.facebook.com/");
	driver.manage().window().maximize();
	WebElement username =driver.findElement(By.id("email"));
	Actions act =new Actions(driver);
	act.moveToElement(username).perform();
	act.contextClick(username).perform();
	act.sendKeys(Keys.TAB).perform();
	act.sendKeys(Keys.chord(Keys.CONTROL,"c")).build().perform();
	act.clickAndHold(username).moveToElement(username).release(username).build().perform();
	}
}
