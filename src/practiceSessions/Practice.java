package practiceSessions;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Practice {
	@Test
	public void keybrdActions() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver =new ChromeDriver();
		driver.get("https://www.goibibo.com/");
		driver.manage().window().maximize();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[contains(text(),'Sign up')]")).click();
		Thread.sleep(3000);
		String Name = "authiframe";
		driver.switchTo().frame(Name);
		driver.findElement(By.xpath("//input[@placeholder='Enter Mobile Number']")).sendKeys("1234567890");
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		
	}
}
