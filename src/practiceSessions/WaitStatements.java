package practiceSessions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class WaitStatements {
WebDriver driver;
	@Test
	public void NormalWait() throws Exception
	{
		Thread.sleep(3000);
		System.out.println("NormalWait");
	}
	@Test
	public void ImplicitWait() 
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Implicit Wait");
	}
	
	@Test
	public void ExplicitWait() 
	{
		WebDriverWait wait = new WebDriverWait(driver,5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Username")));
		System.out.println("Explicit Wait");
			
	}
	
}
