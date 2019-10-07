package handlingWebelements;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AlertPopup {

	public static void main(String[] args) throws InterruptedException {
		try {
		// TODO Auto-generated method stub
System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
WebDriver driver = new ChromeDriver();
driver.get("https://mail.rediff.com/cgi-bin/login.cgi");
Thread.sleep(3000);
driver.manage().window().maximize();
driver.findElement(By.xpath("//input[@type='submit']")).click();

Alert popUp = driver.switchTo().alert();
String errormessage = popUp.getText();
System.out.println(errormessage);
popUp.accept();

//popUp.dismiss();



	}
		catch (Exception e) {
		
		}
	
}

}