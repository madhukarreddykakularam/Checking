package testCasesPracticeGC;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Class1 {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
WebDriver driver=new ChromeDriver();
driver.get("https://en.wikipedia.org/wiki/Selenium_(software)");

driver.findElement(By.xpath("//a[contains(text(),'Create account')]")).click();
Thread.sleep(3000);
String wikiUrl = driver.getCurrentUrl();
if(wikiUrl.contains("wikipedia.org"))
{
	System.out.println("Internal link");
}
driver.navigate().back();
Thread.sleep(3000);
driver.findElement(By.linkText("www.seleniumhq.org")).click();
String URL = driver.getCurrentUrl();
System.out.println(URL);
if(URL.contains("wikipedia.org"))
{
	System.out.println("Internal link");
}
else 
{
	System.out.println("External link");
}

driver.close();
	}

}
