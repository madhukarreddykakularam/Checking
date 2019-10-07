package testCasesPracticeGC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class VerifyCreatedUserDataNotepad {
public static WebDriver driver;
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
		
		FileReader fr = new FileReader("C:\\Users\\Dell\\Desktop\\Testing.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		int count=0;
		while ((line = br.readLine())!=null) {
			count=count+1;
		if (count>1){
		String [] inputData = line.split(",", 2);
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://www.gcrit.com/build3/login.php");
		driver.findElement(By.name("email_address")).sendKeys(inputData[0]);
		driver.findElement(By.name("password")).sendKeys(inputData[1]);
		driver.findElement(By.xpath("//span[contains(text(),'Sign In')]")).click();
		System.out.println("Success");
		driver.close();
		}
		
		
		}
				
		
		
	}

}
