package testCasesPracticeGC;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Class2RegistrationForm {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("http://www.gcrit.com/build3/");
		driver.manage().window().maximize();
		Thread.sleep(3000);
		driver.findElement(By.linkText("create an account")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@value='m']")).click();
		driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("Tester");
		driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("123");
		driver.findElement(By.name("dob")).sendKeys("07/08/1994");
		driver.findElement(By.name("email_address")).sendKeys("testingmails@gmail.com");
		driver.findElement(By.name("street_address")).sendKeys("Testing");
		driver.findElement(By.name("postcode")).sendKeys("123432");
		driver.findElement(By.name("city")).sendKeys("testing");
		driver.findElement(By.name("state")).sendKeys("testing");
		Select dropdown = new Select(driver.findElement(By.name("country")));
		dropdown.selectByVisibleText("India");
		System.out.println(dropdown.getAllSelectedOptions());
		driver.findElement(By.name("telephone")).sendKeys("1234567890");
		driver.findElement(By.name("password")).sendKeys("Test.123");
		driver.findElement(By.name("confirmation")).sendKeys("Test.123");
		driver.findElement(By.xpath("//span[contains(text(),'Continue')]")).click();
		
				
		
		
	}

}
