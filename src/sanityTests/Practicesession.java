package sanityTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Practicesession {

	public static void main(String[] args) throws Exception {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://www.gcrit.com/build3/admin/");//to open that url 
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("admin");
		
		WebElement userid = driver.findElement(By.xpath("//input[@name='password']"));
		userid.sendKeys("admin@123");
		Thread.sleep(3000);
		
		String Val = driver.findElement(By.xpath("//input[@name='username']")).getAttribute("Value");
 		System.out.println(Val);
		userid.clear();
		String pageTitle = driver.getTitle();//to get the page title
		System.out.println(pageTitle);
		String currentUrl = driver.getCurrentUrl(); //gets the currentpage url
		System.out.println(currentUrl);
		driver.navigate().to("https://www.google.com");//it will open the new url in the same browser in new tab
		String googleTitle = driver.getTitle();
		System.out.println(googleTitle);
		driver.navigate().back();//navigate back
		
		//driver.quit();//closes the browser only
		//Thread.sleep(8000);
		driver.close();//closer current tab in the browser
	}
}
