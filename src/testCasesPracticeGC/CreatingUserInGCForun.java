package testCasesPracticeGC;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CreatingUserInGCForun {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
WebDriver driver = new ChromeDriver();
driver.get("http://www.gcreddy.com/forum/");
driver.manage().window().maximize();
Thread.sleep(3000);
driver.findElement(By.linkText("Register")).click();
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
driver.findElement(By.id("agreed")).click();
driver.findElement(By.id("username")).sendKeys("Test3245");
driver.findElement(By.id("email")).sendKeys("Test3245@gmail.com");
driver.findElement(By.id("new_password")).sendKeys("Test3245");
driver.findElement(By.id("password_confirm")).sendKeys("Test3245");
Scanner scan = new Scanner(System.in);
System.out.println("Enter Confirmation code");
String captcha = scan.nextLine();
driver.findElement(By.id("confirm_code")).sendKeys(captcha);
driver.findElement(By.id("submit")).click();




	}

}
