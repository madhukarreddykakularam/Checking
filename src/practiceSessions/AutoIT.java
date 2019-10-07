package practiceSessions;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class AutoIT {
WebDriver driver;
@Test
public void MouseHover() throws InterruptedException, Exception  {
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
	driver =new ChromeDriver();
	driver.get("https://www.youtube.com/playlist?list=PL6flErFppaj2ArNxLyR4nQ4JV8qFc56-M");
	Robot rb = new Robot();
	 
	// Press control keyboard key
	rb.keyPress(KeyEvent.VK_CONTROL);
	 
	// Press A keyboard key
	rb.keyPress(KeyEvent.VK_P);
		/*
		 * Actions act= new Actions(driver);
		 * act.sendKeys(Keys.chord(Keys.CONTROL,"p")).perform();
		 */
	Thread.sleep(3000);
	Runtime.getRuntime().exec("C:\\Users\\Dell\\Desktop\\AUTOIT\\testfirst.exe");
	
}
}
