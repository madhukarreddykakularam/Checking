package practiceSessions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DropdownFunctionality 
{
@Test	
public void dropdown() throws Exception
{
System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
WebDriver driver =new ChromeDriver();
driver.get("https://www.facebook.com/");
driver.manage().window().maximize();
		/*
		 * driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 * WebDriverWait wait = new WebDriverWait(driver,10);
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("")));
		 */
Thread.sleep(3000);
Select drop = new Select(driver.findElement(By.id("month")));
List<WebElement> options = drop.getOptions();
ArrayList actual = new ArrayList();
ArrayList expected = new ArrayList();
for(WebElement ee:options)
{
	String data = ee.getText();
	actual.add(data);
}
expected.addAll(actual);
Collections.sort(expected);//this is for ascending Order
Collections.sort(expected,Collections.reverseOrder());//this is for descending Order 
Assert.assertEquals(actual, expected);


}	
}
