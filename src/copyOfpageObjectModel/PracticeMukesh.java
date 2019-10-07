package copyOfpageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import pageObjectModel.PracticePageFactory;

public class PracticeMukesh {

	public void Checklogin() {
	WebDriver driver =new ChromeDriver();
	PracticePageFactory login = PageFactory.initElements(driver, PracticePageFactory.class);
	login.login("admin", "demo123");
	}

}
