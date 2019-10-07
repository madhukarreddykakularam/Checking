package testNGClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TestNGClass1 {
WebDriver driver;
@Test (priority=1)	//runs on Priority based on the number 
public void VerifyLogin() throws InterruptedException{
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
	driver = new ChromeDriver();
	driver.manage().window().maximize(); 
	driver.get("http://www.gcrit.com/build3/admin/login.php?osCAdminID=kqlkbj8ekat1u10dk6u50p1627");
	Thread.sleep(3000);
	String PageTitle = driver.getTitle();
	Assert.assertEquals(PageTitle, "GCR Shop");
	Thread.sleep(3000);
} 
@AfterMethod
public void CloseBrowser() {
	driver.close();
}
}
