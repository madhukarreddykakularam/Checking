package handlingWebelements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class HandlingDropDown {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
		 * WebDriver driver= new ChromeDriver(); driver.get(
		 * "http://www.gcrit.com/build3/create_account.php?osCsid=sku30iqkim8fc4hmc86rr0rt44"
		 * ); Thread.sleep(3000); driver.manage().window().maximize(); boolean status =
		 * driver.findElement(By.name("country")).isDisplayed();
		 * System.out.println(status); Select selectoptions = new
		 * Select(driver.findElement(By.name("country")));
		 * selectoptions.selectByVisibleText("India");
		 * //selectoptions.deselectByVisibleText("India"); //for deselecting
		 * List<WebElement> itemList = selectoptions.getOptions(); int sizeOfList =
		 * itemList.size(); System.out.println(sizeOfList);
		 */
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("https://www.facebook.com");
		Thread.sleep(4000);
		Select day = new Select(driver.findElement(By.id("day")));
		day.selectByValue("7");
		Select month = new Select(driver.findElement(By.id("month")));
		month.selectByVisibleText("Mar");
		Select year= new Select(driver.findElement(By.id("year")));
		year.selectByValue("2018");
		
		List<WebElement> DropdownValues = day.getOptions();
		System.out.println(DropdownValues.size());
		int size = DropdownValues.size();
		for(WebElement values:DropdownValues)
		{
		System.out.println(values.getText());
		String crctValue = values.getText();
		if (crctValue.equals("3"))
		{
			System.out.println("Values displayed correctly");
		}
		
		}
		
		
		
	}

}
