package abcd;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserLogIn {
	static WebDriver driver;
	
	public static WebDriver launch(String browserName,String url) {
		
		if(browserName.equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver",
		            "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
			driver= new ChromeDriver();
		}
		if(browserName.equalsIgnoreCase("IE"))
		{
			driver= new InternetExplorerDriver();
		}
		driver.manage().window().maximize();
		driver.get(url);
		return driver;
	}
}
