package copyOfpageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pageObjectModel.Class1;

public class POMClassMukesh {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		
Class1 obj =new Class1(driver);
	}

}
