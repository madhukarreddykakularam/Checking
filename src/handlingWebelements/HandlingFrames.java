package handlingWebelements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class HandlingFrames {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
	WebDriver driver= new ChromeDriver();
	driver.get("https://seleniumhq.github.io/selenium/docs/api/java/index.html");
	Thread.sleep(3000);
	driver.manage().window().maximize();
	//driver.switchTo().frame(2);
	driver.switchTo().frame("classFrame");
	String expLink = "org.openqa.selenium";
	
	List <WebElement> abc = driver.findElements(By.xpath("//div/table/tbody[2]/tr"));
	System.out.println(abc.size());
	for(int i=0;i<abc.size();i++)
	{
		String linkName = abc.get(i).getText();
		System.out.println(linkName);
		if (linkName.equalsIgnoreCase(expLink))
		{
			System.out.println("Displayed correctly");
			break;
		}
	}
	
	
	driver.findElement(By.linkText("org.openqa.selenium")).click();
	//driver.findElement(By.xpath("//html/body/div[3]/table/tbody[2]/tr[5]/td[1]/a")).click();
	driver.switchTo().defaultContent();
	driver.navigate().back();
	driver.switchTo().frame(0);
	driver.findElement(By.linkText("com.thoughtworks.selenium")).click();
	Thread.sleep(3000);
	
	

	}

}
