package testCasesPracticeGC;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Class3VerifyCreatedUser {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("http://www.gcrit.com/build3/login.php");
		driver.manage().window().maximize();
		driver.findElement(By.name("email_address")).sendKeys("testingmails@gmail.com");
		driver.findElement(By.name("password")).sendKeys("Test.123");
		driver.findElement(By.xpath("//span[contains(text(),'Sign In')]")).click();
		
		
		
		
				
		
		
	}

}
