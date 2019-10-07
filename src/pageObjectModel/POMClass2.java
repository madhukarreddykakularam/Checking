package pageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import abcd.BrowserLogIn;

public class POMClass2 {

	public static void main(String[] args) {
		String url = "www.google.com";
		BrowserLogIn.launch("Chrome", url);
		//String url = "www.google.com";
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver",
	            "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
	//create chrome browser driver
		WebDriver driver = new ChromeDriver();
		Class1 obj = new Class1(driver);
		obj.typeUserName("admin");
		driver.findElement(obj.uName).click();
	}

}
