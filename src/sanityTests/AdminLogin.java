package sanityTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AdminLogin {

	public static void main(String[] args) throws Exception {
		//instantiate chrome browser driver
		System.setProperty("webdriver.chrome.driver",
	            "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
	//create chrome browser driver
		WebDriver driver = new ChromeDriver();
	driver.manage().window().maximize();//to maximize the browser window
	driver.get("http://www.gcrit.com/build3/admin/");
	Thread.sleep(3000);
	driver.findElement(By.name("username")).sendKeys("admin");
	driver.findElement(By.name("password")).sendKeys("admin@123");
	driver.findElement(By.id("tdb1")).click();
	}

}
