package practiceSessions;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MouseHover {
	WebDriver driver;

@Test
public void MouseHover() throws InterruptedException  {
	 System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
	 
	driver =new ChromeDriver();
	driver.get("http://seleniumpractise.blogspot.com/2016/08/how-to-perform-mouse-hover-in-selenium.html");
	driver.manage().window().maximize();
	WebDriverWait wait =new WebDriverWait(driver,10);
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Automation Tools')]")));
	WebElement automation = driver.findElement(By.xpath("//button[contains(text(),'Automation Tools')]"));
	Actions act = new Actions(driver);
	act.moveToElement(automation).perform();//for mouse hover
	act.contextClick(automation).perform();//This is for right click
	Thread.sleep(6000);
	List <WebElement> drpValues=driver.findElements(By.xpath("//div[@class='dropdown-content']/a"));
	int count = drpValues.size();
	ArrayList list = new ArrayList();
	for (WebElement e:drpValues) {
		String Values =e.getAttribute("innerHTML");
		if(Values.equalsIgnoreCase("Selenium")) {
			e.click();
		}
		//list.add(Values);
		//System.out.println(list);
	}
	//Assert.assertEquals(list, list);
}
}
