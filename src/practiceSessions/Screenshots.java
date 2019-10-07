package practiceSessions;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;



public class Screenshots {
@Test
	public void ScreenshotsDemo() throws Exception{
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
	WebDriver driver =new ChromeDriver();
	driver.get("https://www.facebook.com/");
	driver.manage().window().maximize();
	Thread.sleep(3000);
	driver.findElement(By.id("email")).sendKeys("UserName");
	TakesScreenshot ts = (TakesScreenshot)driver;
	
	File source =ts.getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(source,new File("path.png"));

	}
}
