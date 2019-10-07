package handlingWebelements;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HandlingInlineElements {

	public static void main(String[] args) throws InterruptedException {
		try {
		// TODO Auto-generated method stub
System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
WebDriver driver = new ChromeDriver();
driver.get("https://www.google.com");
Thread.sleep(3000);
driver.manage().window().maximize();
driver.findElement(By.xpath("//a[@title='Google apps']")).click();
Thread.sleep(2000);
driver.findElement(By.xpath("//a[contains(text(),'More')]")).click();
driver.findElement(By.xpath("//span[contains(text(),'Blogger')]")).click();



	}
		catch (Exception e) {
		
		}
	
}

}