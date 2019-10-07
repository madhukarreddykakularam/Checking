package practiceSessions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class JavaScriptExecutor {
	WebDriver driver;

	public void javaexecutorDemo() {
		WebElement username= driver.findElement(By.id("username"));
		JavascriptExecutor je = (JavascriptExecutor)driver;
		je.executeScript("arguments[0].scrollIntoView(true);", username);
	}

public void Scroll() {
	 System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
	 driver = new ChromeDriver();
	 driver.get("http://jqueryui.com");
	 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	 
	 JavascriptExecutor jse = (JavascriptExecutor)driver;
	 jse.executeScript("scroll(0,400)");
 }
 
 
 @Test
 public void Scrollintoview() throws Exception {
	 System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
	 driver = new ChromeDriver();
	 driver.get("http://manos.malihu.gr/repository/custom-scrollbar/demo/examples/complete_examples.html");
	 driver.manage().window().maximize();
	 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	 WebDriverWait wait = new WebDriverWait(driver,10);
	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='jQuery custom scrollbar']")));
	 WebElement paragraph =driver.findElement(By.xpath("//h2[contains(text(),'theme: \"rounded-dots\" with less momentum')]/../p[4]"));
	 JavascriptExecutor je=(JavascriptExecutor)driver;
	 je.executeScript("arguments[0].scrollIntoView(true)", paragraph);
	 Thread.sleep(4000);
	
	 
 }
 
}
