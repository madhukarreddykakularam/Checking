package com.nbcu.sphere.Common;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.seleniumhq.jetty7.util.log.Log;






import com.nbcu.sphere.Administration;
import com.nbcu.sphere.TestDriver;
import com.nbcu.sphere.ConfigManager.FileLocSetter;
import com.nbcu.sphere.ObjectRepository.SphereCommon;
import com.nbcu.sphere.ObjectRepository.SphereModules;
import com.nbcu.sphere.ReportManager.Reporter;
import com.nbcu.sphere.UtilManager.AppMessages;
import com.nbcu.sphere.UtilManager.DBUtil;
import com.sun.glass.ui.Window.Level;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class GenericFunctionLibrary
{
	//public static AppiumDriver<IOSElement> iosdriver;
	public static WebDriver driver = null;
	public static Properties prop=null; // properties file
	public static Properties objRepProp=null; // object repository property
	public static Reporter obj=new Reporter();
//	public static PrepareExcelReport objEx=new PrepareExcelReport();
	
	public static boolean step_fail; // keep track of total step fail for each testcase
	public static int mainStep; // keep track of main step for each testcases
	public static int subStep; // keep track of subStep for each mainStep
	public static boolean testCaseStatus; // keep track of the test flow
	public String strTimeDiff = null;
	
	DBUtil objDBUtility = new DBUtil();
	SphereCommon objSphereCommon = new SphereCommon();
	SphereModules objSphereModules = new SphereModules();
	
	public Boolean fnSphereLogIn(String url, String user, String password) throws Exception
	{	
		Boolean bFlag = true;
		try {
			//fnSetBrowserCapabilities();
			WebDriverWait wait = new WebDriverWait(driver,35);
			obj.repAddData( "Sign In to the application - Pre Condition","", "", "");
			driver.navigate().to(url);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SphereCommon.Main_HomePageLogo_xp)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(SphereCommon.Main_BtnSignIn_nm)));
			
			SendKeyById(SphereCommon.Main_InputUserId_id,user,"Username");
			SendKeyById(SphereCommon.Main_InputPassword_id, password,"Password");
			ClickByName(SphereCommon.Main_BtnSignIn_nm, "Sign In Button",true);
			fnCheckAlert();
		/*	
			//wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SphereHome.Main_BtnSignIn_xp)));//Now button would be disabled for login until username and password are entered
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SphereCommon.Main_BtnSignIn_xp)));
			
			
			Thread.sleep(2000);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SphereHome.Main_BtnSignIn_xp)));
			fnCheckTitle(SphereCommon.Main_TitleSpherePage_nm);
			SendKeyById(SphereCommon.Main_InputUserId_id,user,"Username");
			SendKeyById(SphereCommon.Main_InputPassword_id, password,"Password");
			
			
			//ClickByName(prop.getProperty("ButtonSignIn"), "Sign In Button"); // static ID	
			//ClickById(SphereHome.Main_BtnSignIn_id, "Login Button", true); // static ID
			ClickByXpath(SphereCommon.Main_BtnSignIn_xp, "Login Button", true); 

			
			//if(CheckTextOnPage("Username or password are incorrect"))
			 * */
			if(CheckTextOnPage(AppMessages.Common_LoginPage_ErrorMsgInvalidSSO_msg) || CheckTextOnPage(AppMessages.Common_LoginPage_ErrorMsgInvalidPassword_msg))
			{
				System.out.println("Username or password is incorrect");
				obj.repAddData( "Login to the application", "Login should be successful", "Login Failed", "Fail");
				TestDriver.log.info("Sphere Login Failed - Username or password are incorrect");
				bFlag = false;
				//throw new Exception();
			}
			
			try{
				// wait for page to load here
				if(bFlag == true)
				{
					fnLoadingPageWait();
					//wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SphereCommon.Main_labelHomePageWelcome_xp)));
					//WebElement ele = driver.findElement(By.xpath(SphereCommon.Main_labelHomePageWelcome_xp));
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SphereCommon.Main_labelHomePageComingSoon_xp)));
					WebElement ele = driver.findElement(By.xpath(SphereCommon.Main_labelHomePageComingSoon_xp));
					HighlightElement(ele);
					String stextMessage = AppMessages.Common_LoginPage_Text_msg;
					//sWelcomeMessage = sWelcomeMessage.replaceAll("Name", prop.getProperty("name").toString().trim());
					
					if(CheckTextOnPage(stextMessage))
					{
						obj.repAddData( "Verify Sphere home page", "Home page should be loaded", "Home page loaded successfully with Welcome messgae : "+stextMessage, "Pass");
						TestDriver.log.info( "Login Successful..Home page loaded successfully!");
						TestDriver.bLoginFlag=true;
					}
					Thread.sleep(2000);
				}
				
			}catch(Exception e){
				bFlag = false;
				TestDriver.log.error( "Home page not loaded!", e );
				System.out.println("Home Page Header not found");
			}
			
		/*	
			if(bFlag == true)
			{
				obj.repAddData( "Login to the application", "Login should be successful", "Logged in successfully", "Pass");
			}
			else
			{
				obj.repAddData( "Login to the application", "Login should be successful", "Login Failed", "Fail");
			}
		*/			
		} catch (Exception e) {
			System.out.println("fnLogIn------------------Failed");
			bFlag = false;
			obj.repAddData( "Login to the application", "Login should be successful", "Login Failed", "Fail");
			TestDriver.log.error( "Sphere Login Failed!", e );
			////throw(e);
		}
		
		return bFlag;
	}
	
	
	public GenericFunctionLibrary() 
	{
	
	}
	
	
	public void fnSetBrowserCapabilities()
	{ 
		String sBrowser = TestDriver.mEnvSheetData.get(TestDriver.iMasterRowId).get("Browser").toString().trim();
		if(sBrowser.equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver", FileLocSetter.sProjPath+"chromedriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
			options.addArguments("--disable-extensions"); 
			options.addArguments("disable-infobars");
			//
			Map<String, Object> prefs = new HashMap<String, Object>();
		    prefs.put("credentials_enable_service", false);
		    prefs.put("profile.password_manager_enabled", false);
		    options.setExperimentalOption("prefs", prefs);
		    //
			capabilities.setCapability("chrome.binary",FileLocSetter.sProjPath+"chromedriver.exe");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);  
			/////////////////////////////////Console Logs///////////////////////////////////////
			LoggingPreferences logPrefs = new LoggingPreferences();
	        logPrefs.enable(LogType.BROWSER,java.util.logging.Level.ALL);
	        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			/////////////////////////////////////////////////////////////////////////
			driver = new ChromeDriver(capabilities);
		}
		else if(sBrowser.equalsIgnoreCase("Firefox"))
		{
			
			 //FirefoxProfile fp = new FirefoxProfile();
			 //fp.setPreference("webdriver.load.strategy", "unstable"); // As of 2.19. from 2.9 - 2.18 use 'fast'
			 
			 //Alternate option if default Firefox profile is not working
			 ProfilesIni profile = new ProfilesIni();
			 FirefoxProfile fp = profile.getProfile("FFAutomationProfile"); //create an automation profile by using firefox.exe -p in Run command
			 
			 driver = new FirefoxDriver(fp);
			 
			// driver = new FirefoxDriver();
			 
		}
		else if(sBrowser.equalsIgnoreCase("IE"))
		{
			System.setProperty("webdriver.ie.driver", FileLocSetter.sProjPath+"IEDriverServer.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			//capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true); 
			driver = new InternetExplorerDriver(capabilities);
		}
		else if(sBrowser.equalsIgnoreCase("Safari"))
		{
			System.setProperty("webdriver.safari.noinstall", "true");
			DesiredCapabilities capabilities = DesiredCapabilities.safari();
			//capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true); 
			driver = new SafariDriver(capabilities);
		}
		
		driver.manage().window().maximize();
		//driver.manage().window().maximize();
		
	}
	public int fnRandomNum(int min, int max) 
	{
	   // NOTE: Usually this should be a field rather than a method
	   // variable so that it is not re-seeded every call.
	   Random rand = new Random();
	   // nextInt is normally exclusive of the top value,
	   // so add 1 to make it inclusive
	   int randomNum = rand.nextInt((max - min) + 1) + min;

	   return randomNum;
	}
	
	public String fnGenerateStr(int length)
	{
		int i = 0;
		StringBuilder sBuilder = new StringBuilder();
		String sReturn;
		while (i < length)
		{
			char c = 't';
			sBuilder.append(c);
			i++;
		}
		sReturn = sBuilder.toString();
		return sReturn;
	}
	
	
	
		
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public void fnExecuteFunction(String ClassName, String sFunctionName)
	{
		try {
			Class[] cArg = new Class[1];
			cArg[0] = obj.getClass();
			
			// load the ClassName at runtime
			Class clsObj = Class.forName(ClassName);
			Object obj1 = clsObj.newInstance();

			//String RowNum = Integer.toString(DriverScript.i);
			Method method = clsObj.getDeclaredMethod(sFunctionName,cArg);
			Reporter.iTotalExecuted++;
			method.invoke(obj1, obj);

		} catch (Exception e) {
			TestDriver.log.info("Test case failed for "+sFunctionName+". Check if the function is missing...");
		}
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public void fnExecuteLoginFunction()
	{
		try {
			  Method mLogin = Administration.class.getMethod("TC18", int.class);
			  mLogin.invoke(new Administration(), 100);

		} catch (Exception e) {
			TestDriver.log.info("Test case failed for fnExecuteLoginFunction. Check if the function is missing...");
		}
	}
	
	
	//WScript C:\\...\\client.vbs
	public void ExecuteCommand(String command) throws Exception
	{
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
		builder.redirectErrorStream(true);
		Process p = builder.start();

		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

		String line;
		while (true) {
			line = r.readLine();
			if (line == null) {
				break;
			}
			System.out.println(line);
		}
	}
	
	
	public void HighlightElementByName(String value) throws InterruptedException 
	{
		WebElement ele =  driver.findElement(By.name(value));
		
		String originalStyle = ele.getAttribute("style");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for(int i=0;i<2;i++)
		{
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red; width: 35%');", ele);
			Thread.sleep(500);
			js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", ele);
			Thread.sleep(500);
		}
	}
	
		
	public void HighlightElementById(String IDvalue) throws InterruptedException 
	{
		WebElement ele =  driver.findElement(By.id(IDvalue));
		
		String originalStyle = ele.getAttribute("style");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		
		for(int i=0;i<2;i++)
		{
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red; width: 35%');", ele);
			Thread.sleep(500);
			js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", ele);
			Thread.sleep(500);
		}
		
	}
	
	
	public void HighlightElementByXpath(String xPath) throws InterruptedException 
	{
		WebElement ele =  driver.findElement(By.xpath(xPath));
		
		String originalStyle = ele.getAttribute("style");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for(int i=0;i<2;i++)
		{
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red; width: 35%');", ele);
			Thread.sleep(150);
			js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", ele);
			Thread.sleep(150);
		}
	}
	
		
	public void HighlightElement(WebElement ele) throws InterruptedException 
	{	
		String originalStyle = ele.getAttribute("style");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		for(int i=0;i<2;i++)
		{
			js.executeScript("arguments[0].setAttribute('style', 'background: gold; border: 1px solid blue; width: 35%');", ele);
			Thread.sleep(150);
			js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", ele);
			Thread.sleep(150);
		}
	}
	
	
	public boolean ElementFoundById(String sId) 
	{
		boolean result = false;
		List<WebElement> eleList = driver.findElements(By.id(sId));
		if (eleList.size() > 0)
			result = true;
		return result;
	}
	
	public boolean ElementFound(String xPath) 
	{
		boolean result = false;
		List<WebElement> eleList = driver.findElements(By.xpath(xPath));
		if (eleList.size() == 1)
			result = true;
		return result;
	}
	
	
	//
	public int CheckXpath(WebDriver thisDriver, String value)  
	{
		int result = 0;
		try {
			List<WebElement> eleList = thisDriver.findElements(By.xpath(value));
			result = eleList.size();
		} catch (Exception e) {
		}
		return result;
	}
	
	/*public int CheckXpath(String value) {
		int result = 0;
		try {
			List<WebElement> eleList = driver.findElements(By.xpath(value));
			result = eleList.size();
		} catch (Exception e) {
			System.out.println("Xpath>>"+value+" not found.");
		}
		return result;
	}*/
	
	public int CheckXpath(String sLocator, String sReportText, boolean bReportFlag, boolean bScenarioFlag) {
		//Added bScenarioFlag to have reporting for both +ve and -ve scenarios.. bScenarioFlag will be false for -ve and vice-versa
		int result = 0;
		try {
			List<WebElement> eleList = driver.findElements(By.xpath(sLocator));
			result = eleList.size();
			String sText="";
			if(bReportFlag==true)
			{	
				if(result==1)
				{
					if(eleList.get(0).getText().trim().contains("Properties") && eleList.get(0).getText().trim().contains("Select Status"))
					{
						sText="Properties";
					}
					else
					{
						sText = eleList.get(0).getText().trim();
					}
					HighlightElement(eleList.get(0));
					obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" shown successfully with value '"+sText+"'", "Pass");
				}
				else
				{
					if(bScenarioFlag==true)
					{
						obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" not shown", "Fail");
					}
					else
					{
						obj.repAddData( "Verify "+sReportText, sReportText+" should not be shown", sReportText+" not shown", "Pass");
					}
					
				}
			}
			
		} catch (Exception e) {
			System.out.println("Id>>"+sLocator+" not found.");
			TestDriver.log.info("Id>>"+sLocator+" not found.");
		}
		return result;
	}
	
	public int CheckId(String sLocator, String sReportText, boolean bReportFlag) {
		int result = 0;
		String sText ="";
		try {
			List<WebElement> eleList = driver.findElements(By.id(sLocator));
			result = eleList.size();
			if(bReportFlag==true)
			{
				if(result==1)
				{
					sText = eleList.get(0).getText().trim();
					HighlightElement(eleList.get(0));
					obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" shown successfully with value '"+sText+"'", "Pass");
				}
				else
				{
					obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" not shown", "Fail");
				}
			}
			
		} catch (Exception e) {
			System.out.println("Id>>"+sLocator+" not found.");
			TestDriver.log.info("Id>>"+sLocator+" not found.");
		}
		return result;
	}
	
	public boolean CheckTextOnPage(String text)
	{
	boolean result = false;
	text=text.toLowerCase();
	result = driver.getPageSource().toLowerCase().contains(text);
	//System.out.println(driver.getPageSource().toString());
	return result;
	}
	
	// perform one action from Keyboard
	public void KeyboardAction(Keys action, String actionDescription) throws Exception
	{
		try {
			Actions act = new Actions(driver);
			act.sendKeys(action).build().perform();
			Thread.sleep(1000);
			obj.repAddData( "Perform action from Keyboard", "'"+actionDescription+"' should be performed", "'"+actionDescription+"' performed", "Pass");
		} catch (Exception e) {
			System.out.println("--"+actionDescription+"--Not Performed");
			obj.repAddData( "Perform action from Keyboard", "'"+actionDescription+"' should be performed", "'"+actionDescription+"' not performed", "Fail");
			throw(e);
		}
	}
	
	
	public void fnCheckfieldDisbleById(String IDvalue, String key, String sFieldName) throws Exception
	{
		try {
			WebElement ele = driver.findElement(By.id(IDvalue));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
			HighlightElement(ele);
			ele.clear();
			ele.sendKeys(key);
			Thread.sleep(500);
			if(sFieldName.equalsIgnoreCase("Password") || sFieldName.contains("Password"))
			{
				obj.repAddData( "Enter data to "+sFieldName+" field", "<b>'"+"*******"+"'</b> should be entered", "'"+"*******"+"'</b> entered", "Pass");
			}
			else
			{	
				obj.repAddData( "Enter data to "+sFieldName+" field", "<b>'"+key+"'</b> should be entered", "'"+key+"'</b> entered", "Pass");
			}
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData( "Enter data to "+sFieldName+" field", "<b>'"+key+"'<b/> should be entered", "<b>'"+key+"'</b> not entered", "Fail");
			//throw(e);
		}
	}
	
	
	public void SendKeyByXpath(String Xpath, String key, String sFieldName) throws Exception
	{
		try {
			
			WebElement ele = driver.findElement(By.xpath(Xpath));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
			HighlightElement(ele);
			ele.clear();
			ele.sendKeys(key);
			Thread.sleep(500);
			obj.repAddData( "Enter data to "+sFieldName+" field", "'"+key+"' should be entered", "'"+key+"' entered", "Pass");
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData("Enter data to "+sFieldName+" field", "'"+key+"' should be entered", "'"+key+"' not entered", "Fail");
			//throw(e);
		}
	}

	public void SendKeyById(String Id, String key, String sFieldName) throws Exception
	{
		try {
			WebElement ele = driver.findElement(By.id(Id));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
			HighlightElement(ele);
			ele.clear();
			ele.sendKeys(key);
			Thread.sleep(500);
			if(sFieldName.toLowerCase().equalsIgnoreCase("password")){
				StringBuilder pwd=new StringBuilder(); 
				for(int i=1; i<=key.length();i++){
					
					pwd=pwd.append("*");
					
				}
				obj.repAddData( "Enter data to "+sFieldName+" field", "'"+pwd+"' should be entered", "'"+pwd+"' entered", "Pass");
				
			}else
			{
				obj.repAddData( "Enter data to "+sFieldName+" field", "'"+key+"' should be entered", "'"+key+"' entered", "Pass");	
			}
			
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData("Enter data to "+sFieldName+" field", "'"+key+"' should be entered", "'"+key+"' not entered", "Fail");
			//throw(e);
		}
	}
	public void SendKeyByName(String value, String key, String sFieldName) throws Exception
	{
		try {
			WebElement ele = driver.findElement(By.name(value));
			HighlightElement(ele);
			ele.clear();
			ele.sendKeys(key);
			Thread.sleep(500);
			obj.repAddData( "Enter data to "+sFieldName+" field", "<b>'"+key+"'</b> should be entered", "<b>'"+key+"'</b> entered", "Pass");
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData( "Enter data to "+sFieldName+" field", "<b>'"+key+"'</b> should be entered", "<b>'"+key+"'</b> not entered", "Fail");
			//throw(e);
		}
	}
	
	
	public void SendKeyByElement(WebElement ele, String key, String sFieldName) throws Exception
	{
		try {
			HighlightElement(ele);
			ele.clear();
			ele.sendKeys(key);
			Thread.sleep(500);
			obj.repAddData("Enter data to "+sFieldName+" field",
					"'" + key + "' should be entered", "'" + key + "' entered",
					"Pass");
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData( "Enter data to "+sFieldName+" field",
					"'" + key + "' should be entered", "'" + key
							+ "' not entered", "Fail");
			throw (e);
		}
	}
	
	public void ClickById(String IDvalue, String faceValue, boolean bReportFlag) throws Exception
	{
		try {
			WebElement ele;
			ele = driver.findElement(By.id(IDvalue));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
			HighlightElement(ele);
			ele.click();
			Thread.sleep(4000);
			if(bReportFlag==true)
			{
				obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' clicked", "Pass");
			}
			fnLoadingPageWait();
			fnVerifyClickSuccessMessage();
			//fnVerifyClickErrorMessage();
		   } catch (Exception e) {
			System.out.println("--No Such Element Found--");
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
			}
			throw(e);
		}
	}
	
	public void ClickByName(String value, String faceValue, boolean bReportFlag) throws Exception
	{
		try {
			WebElement ele;
			ele = driver.findElement(By.name(value));
			HighlightElement(ele);
			ele.click();
			Thread.sleep(1000);
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' clicked", "Pass");
			}
			fnLoadingPageWait();
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
			}
			throw(e);
		}
	}
	
	public void ClickByLinkText(String value, String faceValue, boolean bReportFlag) throws Exception
	{
		try {
			WebElement ele;
			ele = driver.findElement(By.linkText(value));
			HighlightElement(ele);
			ele.click();
			Thread.sleep(1000);
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' clicked", "Pass");
			}
			fnLoadingPageWait();
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
			}
			throw(e);
		}
	}
	
	public void ClickByPartialLinkText(String value, String faceValue, boolean bReportFlag) throws Exception
	{
		if(testCaseStatus)
		{
			try {
				WebElement ele;
				ele = driver.findElement(By.partialLinkText(value));
				HighlightElement(ele);
				ele.click();
				Thread.sleep(1000);
				if(bReportFlag==true)
				{
				obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' clicked", "Pass");
				}
				fnLoadingPageWait();
			} catch (Exception e) {
				System.out.println("--No Such Element Found--");
				if(bReportFlag==true)
				{
				obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
				}
			}
		} else return;
	}
	
	public void ClickByTagName(String value, String faceValue, boolean bReportFlag) throws Exception
	{
		try {
			WebElement ele;
			ele = driver.findElement(By.tagName(value));
			HighlightElement(ele);
			ele.click();
			Thread.sleep(1000);
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' clicked", "Pass");
			}
			fnLoadingPageWait();
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
			}
			throw(e);
		}
	}
	
	
	public void ClickByXpath(String value, String faceValue, boolean bReportFlag) throws Exception
	{
		try {
			WebElement ele;
			ele = driver.findElement(By.xpath(value));
			System.out.println("Displayed>>>"+ele.isDisplayed());
			System.out.println("Enabled>>>"+ele.isEnabled());
			if(ele.isDisplayed()|| ele.isEnabled())
			{
			    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
				HighlightElement(ele);
				ele.click();
				Thread.sleep(1000);
				if(bReportFlag==true)
				{
				obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' is clicked", "Pass");
				}
			}
			else
			{
				TestDriver.log.info("Element found but not clicked (Reason : Not displayed or Not Enabled)");
			}
			fnLoadingPageWait();
			
			fnVerifyClickSuccessMessage();
			//fnVerifyClickErrorMessage();
		    } catch (Exception e) {
			System.out.println("--No Such Element Found--");
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
			}
			throw(e);
		}
	}
	
	
	
	public void ClickByElement(WebElement ele, String faceValue, boolean bReportFlag) throws Exception
	{
		try {
			//Actions action = new Actions(driver);
			//action.click(ele).perform();
			HighlightElement(ele);
			
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
			ele.click();
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"	+ faceValue + "'", "'" + faceValue+ "' should be clicked", "'" + faceValue + "' clicked","Pass");
			}
			fnLoadingPageWait();
		} catch (Exception e) {
			System.out.println("Click by Element on line 373 failed");
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"	+ faceValue + "'", "'" + faceValue+ "' should be clicked", "'" + faceValue+ "' not clicked", "Fail");
			}
			throw(e);
		}
	}
	
	public void fnClickActiveElement(String sActiveEleMsg) throws Exception
	{
		try {
			obj.repAddData( "Click on '"	+ sActiveEleMsg + "'", "'" + sActiveEleMsg+ "' should be clicked", "'" + sActiveEleMsg + "' clicked","Pass");
			driver.switchTo().activeElement().click();
		} catch (Exception e) {
			System.out.println("fnClickActiveElement failed");
			obj.repAddData( "Click on '"	+ sActiveEleMsg + "'", "'" + sActiveEleMsg+ "' should be clicked", "'" + sActiveEleMsg+ "' not clicked", "Fail");
			throw(e);
		}
	}
	
		
	public void ClickByAction(String xpath, String faceValue) throws Exception
	{
		try {
			WebElement buttonClick =driver.findElement(By.xpath(xpath));
			HighlightElement(buttonClick);
			Thread.sleep(1000);
			
			if(buttonClick.isEnabled())
			{
				Actions action =new Actions(driver);
				action.click(buttonClick).build().perform();
				obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' clicked", "Pass");
			}
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
			throw(e);
		}
	}
	
	
	public void ClickNavigationNode(String value) throws Exception 
	{
		try {
			WebElement ele;
			ele = driver.findElement(By.xpath("//a[contains(@class,'navbar-topmenu-item')]//span[text()='"+value+"']"));
			HighlightElement(ele);
			if(ele.isDisplayed())
			{
				Actions action = new Actions(driver);
				action.click(ele).build().perform();
			}
			Thread.sleep(1000);
			obj.repAddData( "Click on tree node '"+value+"'", "'"+value+"' should be clicked", "'"+value+"' clicked", "Pass");
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData( "Click on tree node '"+value+"'", "'"+value+"' should be clicked", "'"+value+"' not clicked", "Fail");
			throw(e);
		}
	}
	
	
	public void fnClickNode(String Category, String Activity) throws Exception 
	{
		try {
			obj.repAddData(	"Advisor navigates to '"+Activity+"' under '"+Category+"' tab.", "", "", "");
			subStep = 1;

			if (driver.findElements(By.xpath("//tr[td/div/span[text()= '"+ Activity + "']]")).size() > 0)				
			{	
				ClickByXpath("//tr[td/div/span[contains(text(), '"+ Activity + "')]]", Activity, true);
			} 
			else 
			{
				DoubleClickByXpath("//tr[td/div/span[contains(text(), '"+ Category + "')]]", Category);
				Thread.sleep(500);
				ClickByXpath("//tr[td/div/span[text()= '"+ Activity + "']]", Activity, true);
			}
			
			obj.repAddData( "Advisor navigates to '"+Activity+"' under '"+Category+"' tab.", "'"+Activity+"' should be displayed", "'"+Activity+"' displayed", "Pass");
		}
		 catch (Exception e) {
			System.out.println("fnClicNode----------------failed");
			//throw(e);
		}
	}
	
	/**
	 * @param mLocator
	 * @param tLocator
	 * @param mByType
	 * @param tByType
	 * @throws Exception
	 * 
	 * Use mLocator (String) to pass the locator for the element you want Moved.
	 * Use tLocator (String) to pass the locator for the element want to move To.
	 * Use mByType and tByType (String) to tell the method which identified you want to use
	 * This method reflects this to the By. parameter in findElement.
	 * 
	 * THIS IS EXPERIMENTAL!!
	 */
	public void ClickElementDragTo(String mLocator, String tLocator, String mByType, String tByType) throws Exception
	{
		try {
			Actions action = new Actions(driver);
			
			Class<?> cBy = Class.forName("org.openqa.selenium.By");
			
			WebElement movingElement = null;
			WebElement toElement = null;
			
			Method[] allByMethods = cBy.getDeclaredMethods();
			for (Method m : allByMethods) {
				String methodName = m.getName();
				if (methodName.equals(mByType)) {
					m.setAccessible(true);
					By actionArg = (By) m.invoke(cBy, mLocator);
					movingElement = driver.findElement(actionArg);
				}
				if (methodName.equals(tByType)) {
					m.setAccessible(true);
					By actionArg = (By) m.invoke(cBy, tLocator);
					toElement = driver.findElement(actionArg);
				}
			}
			if (movingElement != null && toElement != null)
			{
				Action dragAndDrop = action.clickAndHold(movingElement)
						.release(toElement)
						.build();
				
				dragAndDrop.perform();
			}
			else { obj.repAddData("Drag element to element", "One or both elements located.", "One or both elements were not located.", "Fail"); }
			
			/* if(bReportFlag==true)
			{
			obj.repAddData( "Click and hold on '"	+ faceValue + "' then drag to " + tEle.getAttribute("name"), "'" + faceValue+ "' should be clicked and moved", "'" + faceValue + "' moved successfully","Pass");
			} */
		} catch (Exception e) {
			/* if(bReportFlag==true)
			{
			obj.repAddData( "Click and hold on '"	+ faceValue + "' then drag to " + tEle.getAttribute("name"), "'" + faceValue+ "' was not dragged", "Fail", "");
			} */
			throw(e);
		}
	}
	
	public void ClickTreeNode(String value) throws Exception {
		try {
			WebElement ele;
			ele = driver.findElement(By.xpath("//*[contains(@class,'x-tree-node-text') and contains(text(),'"+ value + "')]"));
			HighlightElement(ele);
			if (ele.isDisplayed()) {
				Actions action = new Actions(driver);
				action.doubleClick(ele).build().perform();
			}
			Thread.sleep(1000);
			obj.repAddData( "Click on tree node '"+ value + "'", "'" + value + "' should be clicked", "'"+ value + "' clicked", "Pass");
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData( "Click on tree node '"+ value + "'", "'" + value + "' should be clicked", "'"+ value + "' not clicked", "Fail");
			throw (e);
		}
	}
	
	
	
	public void DoubleClickByXpath(String value, String faceValue) throws Exception
	{
		try {
			WebElement ele;
			ele = driver.findElement(By.xpath(value));
			HighlightElement(ele);
			if (ele.isDisplayed())
			{
				Actions action = new Actions(driver);
				action.doubleClick(ele).build().perform();
			}
			Thread.sleep(1000);
			obj.repAddData( "Double Click on '"+faceValue+"'", "'"+faceValue+"' should be Double Clicked", "'"+faceValue+"' Double Clicked", "Pass");
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData( "Double Click on '"+faceValue+"'", "'"+faceValue+"' should be Double Clicked", "'"+faceValue+"' not Double Clicked", "Fail");
			throw(e);
		}
	}
	
	
	public void RightClickByXpath(String value, String faceValue) throws Exception
	{
		try {
			WebElement ele;
			ele = driver.findElement(By.xpath(value));
			HighlightElement(ele);
			if (ele.isDisplayed())
			{
				Actions action = new Actions(driver);
				action.contextClick(ele).build().perform();
			}
			Thread.sleep(1000);
			obj.repAddData( "RightClick on '"+faceValue+"'", "'"+faceValue+"' should be RightClicked", "'"+faceValue+"' RightClicked", "Pass");
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData( "RightClick on '"+faceValue+"'", "'"+faceValue+"' should be RightClicked", "'"+faceValue+"' not RightClicked", "Fail");
			throw(e);
		}
	}
	
	
	
	public void DoubleClickByAction(String xpath, String faceValue) throws Exception
	{
		try {
			WebElement buttonClick =driver.findElement(By.xpath(xpath));
			HighlightElement(buttonClick);
			Thread.sleep(1000);
			obj.repAddData( "Double Click on '"+faceValue+"'", "'"+faceValue+"' should be Double Clicked", "'"+faceValue+"' Double Clicked", "Pass");
			if(buttonClick.isEnabled())
			{
				Actions action =new Actions(driver);
				action.doubleClick(buttonClick).perform();
			}
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData( "Double Click on '"+faceValue+"'", "'"+faceValue+"' should be Double Clicked", "'"+faceValue+"' not Double Clicked", "Fail");
			throw(e);
		}
	}
	
	public void fnCheckTitle(String text) throws Exception 
	{
		if(driver.getTitle().equalsIgnoreCase(text))
		{
			obj.repAddData( "Navigate to Login Window","'"+text+"' window should be displayed","'"+text+"' window displayed", "Pass");
		} else
		{
			obj.repAddData( "Navigate to Login Window","'"+text+"' window should be displayed","'"+text+"' window not displayed", "Fail");
			throw new Exception();
		}	
	}
	
	
	//Sign out of the Application 
		 public void fnSignOut() throws Exception
		 {
			try {
					if(driver!=null)
					{
						if(driver.getPageSource().contains("Logout"))
						{
							obj.repAddData( "Sign out from the application","", "", "");
							WebElement ele;
							ele = driver.findElement(By.xpath("//*[contains(text(),'Logout')]"));
							HighlightElement(ele);				
													
							if (ele.isDisplayed()) 
							{
								ele.click();
							}
							obj.repAddData( "Logout of the application","Logout should be successful","Logged out successfully", "Pass");
							TestDriver.bLoginFlag=false;
						}
						driver.manage().deleteAllCookies();
						driver.quit();
						driver=null;
						
					}

			} catch (Exception e) {
				System.out.println("fnSignOut-----failed");
				obj.repAddData( "Logout of the application", "Logout should be successful","Logout failed", "Fail");
				driver.manage().deleteAllCookies();
				driver.quit();
				driver=null;
				throw(e);
			}	
		}
		 
	 public void fnDriverSignOut(String Name) throws Exception
	 {
		 try {
			 obj.repAddData( "Sign out from the application","", "", "");
			 ClickByAction("//span[starts-with(@id,'button') and @class='x-btn-inner x-btn-inner-center']/div[contains(text(),'"+Name+"')]", ""+Name+"");
			 ClickByAction("//span[contains(text(),'Sign Out') and @class='x-menu-item-text']", "Sign out option");
		 }catch (Exception e) {
			 System.out.println("DriverSignOut----------------Failed");
			 throw(e);
		 }
	 }
		

	public void fnSelectVehicle(String VIN) throws Exception 
	 {
		try {
			subStep = 1;
			obj.repAddData( "VIN search vehicles", "", "", "");
			ClickNavigationNode("Vehicles");
			SendKeyByXpath(prop.getProperty("InputVIN"),VIN, "VIN"); // prop
			KeyboardAction(Keys.ENTER, "Enter Key");
			DoubleClickByXpath("//span[text()='" + VIN + "']",
					"Vehicle with VIN: " + VIN);
		} catch (Exception e) {
			System.out.println("fnSelectVehicle--------------Failed");
			throw (e);
		}
	}
	
	
	public void fnChangeStatus(String TextValue) throws Exception
	{
		try {
			ClickByXpath(prop.getProperty("DropdownStatus"),"Status dropdown",true);

			List<WebElement> StatusDropDown = driver.findElements(By.xpath("//*[@id='idle_reason_pchelem']"));
			for (WebElement ele : StatusDropDown) {
				if (ele.getText().equalsIgnoreCase(TextValue)) {
					Thread.sleep(500);
					HighlightElement(ele);
					ClickByElement(ele, TextValue,true);
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("fnChangeStatus--------------Failed");
			//throw(e);
		}	
	}
	

		
	public void fnSelectFromComboBoxId(String SelectValue, String TextValue) throws Exception
	{
		try {
			
			WebElement select = driver.findElement(By.id(SelectValue));
			if(select.isDisplayed())
			{
				//HighlightElementById(SelectValue);
				List<WebElement> options = select.findElements(By.tagName("option"));
				for (WebElement option : options)
				{
					if(TextValue.equalsIgnoreCase(option.getText().trim()))
					{
						Thread.sleep(1000);
						option.click();
					    obj.repAddData( "Select '"+TextValue +"' from combo box", "'"+TextValue+"' should be selected", "'"+TextValue+"' is selected in the combo box", "Pass");
						Thread.sleep(1000);
						System.out.println("fnSelectFromComboBoxId--------------passed for " + TextValue);
					    break;
					}
				}
			}
	

		} catch (Exception e) {

			obj.repAddData( "Select '"+TextValue +"' from combo box", "'"+TextValue+"' should be selected", "'"+TextValue+"' is not selected in the combo box", "Fail");
			System.out.println("fnSelectFromComboBoxId--------------Failed");
			//throw(e);
		}	
	}
	
	
	public void fnSelectFromComboBoxXpath(String SelectValue, String TextValue) throws Exception
	{
		try {
			
			WebElement select = driver.findElement(By.xpath(SelectValue));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", select);
			if(select.isDisplayed())//||select.isEnabled())
			{			
				HighlightElementByXpath(SelectValue);
				
				List<WebElement> options = select.findElements(By.tagName("option"));
				for (WebElement option : options)
				{
					if(TextValue.equalsIgnoreCase(option.getText().trim()))
					{
						Thread.sleep(1000);
						option.click();
						if(TextValue.equalsIgnoreCase("Select"))
						{
							TextValue = TextValue+ " (Pre-condition)";
						}
					    obj.repAddData( "Select '"+TextValue +"' from combo box", "'"+TextValue+"' should be selected", "'"+TextValue+"' is selected in the combo box", "Pass");
						Thread.sleep(1000);
						System.out.println("fnSelectFromComboBoxXpath--------------passed for " + TextValue);
					    break;
					}
				}
			}
	

		} catch (Exception e) {

			obj.repAddData( "Select '"+TextValue +"' from combo box", "'"+TextValue+"' should be selected", "'"+TextValue+"' is not selected in the combo box", "Fail");
			System.out.println("fnSelectFromComboBoxXpath--------------Failed");
			//throw(e);
		}	
	}
	
	public void fnSelectFromJSComboBox(String SelectValue, String TextValue) throws Exception
	{
		try {
			
			WebElement select = driver.findElement(By.id(SelectValue));
			HighlightElementById(SelectValue);
			List<WebElement> options = select.findElements(By.tagName("option"));
			for (WebElement option : options)
			{
				if(TextValue.equalsIgnoreCase(option.getText().trim()))
				{
					Thread.sleep(1000);
					
					JavascriptExecutor js = (JavascriptExecutor)driver;
					
					String sGetDurationElement = "document.getElementById('"+SelectValue + "').style.display='block';";
					js.executeScript(sGetDurationElement);
					Select selectComboOption = new Select(driver.findElement(By.id(SelectValue)));
					Thread.sleep(4000);
					selectComboOption.selectByVisibleText(TextValue);
			
					obj.repAddData( "Select '"+TextValue +"' from combo box", "'"+TextValue+"' should be selected", "'"+TextValue+"' is selected in the combo box", "Pass");
					Thread.sleep(1000);
					System.out.println("fnSelectFromJSComboBox--------------passed for " + TextValue);
					break;
				}
			}

		} catch (Exception e) {
			obj.repAddData( "Select '"+TextValue +"' from combo box", "'"+TextValue+"' should be selected", "'"+TextValue+"' is not selected in the combo box", "Fail");
			System.out.println("fnSelectFromJSComboBox--------------Failed");
			//throw(e);
		}	
	}
	
		
	public void OpenNewTab() throws Exception
	{

		try {
			if (driver.findElements(By.id("button-1172-btnIconEl")).size() == 1) {
				HighlightElementById("button-1172-btnIconEl");

				Actions builder = new Actions(driver);
				// WebElement elementVisible
				// =driver.findElement(By.id("button-1172-btnIconEl"));
				builder.sendKeys(Keys.CONTROL).sendKeys(Keys.SHIFT + "n").build().perform();

				// org.openqa.selenium.interactions.Action
				// submitTheTransperentButton = builder.build();

				// submitTheTransperentButton.perform();

				/*
				 * driver.findElement(By.id("button-1172-btnIconEl")).sendKeys(Keys
				 * .CONTROL +"t"); ArrayList<String> tabs = new
				 * ArrayList<String> (driver.getWindowHandles());
				 * driver.switchTo().window(tabs.get(1));
				 */
			}
		} catch (Exception e) {
			System.out.println("OpenNewTab()--------------Failed");
			throw(e);
		}
	}
	
	public void fnEnterInfo(String label, String value) throws Exception
	{	
		try
		{
			if(CheckXpath(driver,"//table")>1)
			{
				List<WebElement>eleList = driver.findElements(By.xpath("//table"));
				for(WebElement ele : eleList)
				{
					System.out.println(ele.getText());
					if (ele.getText().equalsIgnoreCase(label))
					{
						ele.findElement(By.tagName("input")).sendKeys(value);
						obj.repAddData( "Enter data",value+" should be entered", value+" is entered successfully", "Pass");
						break;
					}
				}
			}
			
		}
		catch(Exception e) {
			System.out.println("Failed to fetch "+label+ " And "+value);
			obj.repAddData( "Enter data",value+" should be entered", value+" is not entered successfully", "Fail");
			//throw(e);
		}
	}
	

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	 public void fnSwitchTab(int num) throws Exception
	 {	
		 try {
			 List<WebElement> Tab = driver.findElements(By.xpath(prop.getProperty("ListTab")));
			 Tab.get(num).click();
		 } catch (Exception e) {
			 System.out.println("fnSwitchTab----------------Failed");
			 //throw(e);
		 }
	 }
	
	 
	 public void fnFilesToArchive(String sSrcPath, String sDestPath) throws Exception
		{
			try {
				
				System.out.println("In function fnFilesToArchive...............");
					File fSourceFolder = new File(sSrcPath);
					String sDestinationFolder = sDestPath;
					
					File[] listFiles = fSourceFolder.listFiles();
					
					for(int i=0; i<listFiles.length; i++)
					{
						File fSourceFilePath = new File(sSrcPath+"\\"+listFiles[i].getName());
						fSourceFilePath.renameTo(new File(sDestinationFolder+"\\"+listFiles[i].getName()));
						fSourceFilePath.delete();
					}
				
			} catch (Exception e) {
				System.out.println("fnFilesToArchive--------------Failed");
				//throw(e);
			}	
		}
	 

		 
	 public String fnTimeDifference(Date BeginTransac, Date EndTransac) throws Exception
	 {
		 
		try {
			
			double  diff = (double) (EndTransac.getTime()  - BeginTransac.getTime());
			//if ((diff / 60) != 0) {
			//	strTimeDiff = ((diff / 60) + "Min" + " " + (diff % 60) + " " + "Secs");
			//	} else {
		
			
			double fActualTime = diff / 1000;
			
			strTimeDiff = String.valueOf(new DecimalFormat("##.###").format(fActualTime));
			    

				
		} catch (Exception e) {
			System.out.println("fnTimeDifference--------------Failed");
			//throw(e);
		}	
		return strTimeDiff;
	 }
	 /*
	 
		System.out.println("Text>>>>"+driver.switchTo().activeElement().getText());
		wait.until(ExpectedConditions.textToBePresentInElement(driver.switchTo().activeElement(),"OK"));	
		//System.out.println("After Wait>>>>"+driver.switchTo().activeElement().getText());
		driver.switchTo().activeElement().click();
	 */
	 
	 public void fnSwitchToActiveElement() throws Exception   //Switch to main advisor frame
		{
			try {
				
				driver.switchTo().activeElement();
				
			} catch (Exception e) {
				System.out.println("fnSwitchToActiveElement--------------Failed");
				//throw(e);
			}	
		}
	 
	 public void fnSwitchToFrame() throws Exception   //Switch to main advisor frame
		{
			try {
				
				driver.switchTo().defaultContent();
								
			} catch (Exception e) {
				System.out.println("fnSwitchToframe--------------Failed");
				throw(e);
			}	
		}
	 
	 public void fnSwitchToFrame(String sFrameName) throws Exception   //Switch to a frame passed in function
		{
			try {
				
				driver.switchTo().frame(sFrameName);
				
			} catch (Exception e) {
				System.out.println("fnSwitchToFrame--------------Failed");
				throw(e);
			}	
		}
	 
	 //public void fnGetIframe(String prefix) throws Exception   //Earlier name
	 public void fnGetSwitchProfileActivityFrame(String prefix) throws Exception   //New name
	 {
		try {
			    List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
			    for (WebElement iframe : iframes) {
			        if (iframe.getAttribute("id").startsWith(prefix)) {
			        	//driver.switchTo().frame(iframe.getAttribute("id"));
			        	fnSwitchToFrame(iframe.getAttribute("id"));
			        	break;
			        // TODO your stuff.	
			        }
				}
				
		} catch (Exception e) {
			System.out.println("fnGetIframe--------------Failed");
			throw(e);
		}	
	 }
	 
	 
	 public void fnSwitchToGISFrame() throws Exception
		{
			try {
				
				driver.switchTo().frame("testframe");
				driver.switchTo().frame("ggisframe");
				
			} catch (Exception e) {
				System.out.println("fnSwitchToGISframe--------------Failed");
				//throw(e);
			}	
		}
	 
	 	 
	 public ArrayList<String> fnGetWebElementList(List<WebElement> webOfferingName) throws Exception
		{
		 	ArrayList<String> arrWebElementList = new ArrayList<String>();
		 	
			try {
				
				for(WebElement ele : webOfferingName)
				{
					
					//if (!ele.getText().isEmpty())
					//{
						arrWebElementList.add(ele.getText().replace("Select Price Plan", "").trim());
						System.out.println(ele.getText());
						
					//}
				}
				
			} catch (Exception e) {
				System.out.println("fnGetWebElementList--------------Failed");
				//throw(e);
			}
			
			return arrWebElementList;
		}
	 
	public int fnGetOfferingIndex(ArrayList<String> arrOfferingName, String sOfferingName) throws Exception
	 {
		 	int index = 0;
		 	
			try {
				
				for(int i=0;i<arrOfferingName.size();i++)
				{
					if(arrOfferingName.get(i).toString().equalsIgnoreCase(sOfferingName))
					{
						index=i;
						break;
					}
				}
				
			} catch (Exception e) {
				System.out.println("fnGetWebElementList--------------Failed");
				//throw(e);
			}
			
			return index;		
	 		
	 }
	
	public int fnGetPackageIndex(ArrayList<String> arrPackageName,ArrayList<String> arrOfferingName, String PackageName, String sOfferingName) throws Exception
	 {
		 	int index = -1;
		 	
			try {
				
				for(int i=0;i<arrOfferingName.size();i++)
				{
					if(arrOfferingName.get(i).toString().equalsIgnoreCase(sOfferingName) && arrPackageName.get(i).toString().equalsIgnoreCase(PackageName))
					{
						index=i;
						break;
					}
				}
				
			} catch (Exception e) {
				System.out.println("fnGetWebElementList--------------Failed");
				//throw(e);
			}
			
			return index;		
	 		
	 }
	 
	 public void fnArchiveResults(File fResultFile,File fDest) throws Exception
		{
			try {
				
				if (!fDest.exists())
				{
					fDest.mkdirs();
				}
				
				FileUtils.copyFileToDirectory(fResultFile, fDest);
				
			} catch (Exception e) {
				System.out.println("fnArchiveResults--------------Failed");
				//throw(e);
			}	
		}
 
 
	 public String fnGetGUITextXpath(String value) throws Exception
	 {
		 String guiValue = null;
		 try {
			 WebElement ele;
			 ele = driver.findElement(By.xpath(value));
			 //HighlightElement(ele);
			 guiValue  = ele.getText();
			 Thread.sleep(1000); 
			 if (guiValue.equalsIgnoreCase(""))
			 { 
				// guiValue=ele.getAttribute("value");
				 String attrName="placeholder";
				 guiValue=ele.getAttribute(attrName);
				
			 }
	
			// obj.repAddData( "Return GUI Text '", " Return GUI Text", "Return GUI Value Success - " +guiValue, "Pass");
			 
		 } catch (Exception e) {
			 System.out.println("--No Such Element Found--");
			 //obj.repAddData( "Return GUI Text '", " Return GUI Text", "Return GUI Value NOT Success.", "Fail");
			 //throw(e);
		 }
	
		 return guiValue;

	 }

	public String fnGetGUITextID(String value) throws Exception
	 {
		 String guiValue = null;
		 try {
			 WebElement ele;
			 ele = driver.findElement(By.id(value));
			 //HighlightElement(ele);
			 guiValue  = ele.getText();
			 Thread.sleep(1000);
			 if (guiValue.equalsIgnoreCase(""))
			 { 
				 guiValue=ele.getAttribute("value");
			 }
			 //obj.repAddData( "Return GUI Text '", " Return GUI Text", "Return GUI Value Success - " +guiValue, "Pass");
		
		
		 } catch (Exception e) {
			 System.out.println("--No Such Element Found--");
			// obj.repAddData( "Return GUI Text '", " Return GUI Text", "Return GUI Value NOT Success.", "Fail");
			 //throw(e);
		 }
	
		 return guiValue;

	 }
		
	
		//public void fnWaitForObjectExit(int intSec, String strXpath, String sMessage) throws Exception
		public void fnWaitForObjectExit(int intSec, String strXpath, String sEventName) throws Exception
	    {
	    
	                    
			try {
	                                    
				WebDriverWait wait = new WebDriverWait(driver,intSec);  //306
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(strXpath)));
	                                    
                                   
				if (driver.findElement(By.xpath(strXpath)).isDisplayed()){
					HighlightElement(driver.findElement(By.xpath(strXpath)));
					obj.repAddData( "Wait for '"+sEventName+"'", "'"+sEventName+"' should be displayed", "'"+sEventName+"' displayed", "Pass");
				}   
				
			} catch (Exception e) {
				//obj.repAddData( "Wait for '"+sEventName+"'", "'"+sEventName+"' should be displayed", "'"+sEventName+"' not displayed", "Fail");
	            TestDriver.log.info(sEventName + " is not visible on the screen.. Test execution waited for "+ intSec+ " seconds.");                        
				throw(e);
	                    }
	    }

		/* Shameem - Review all three asc order function and delete if needed
		public ArrayList<String> fnGetIframeAscOrder(String prefix) throws Exception
		 {
			 ArrayList<String> arrFrameNumber = new ArrayList<String>();
			 int temp;
			try {
					JavascriptExecutor js = (JavascriptExecutor) driver;
				    List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
				    for (WebElement iframe : iframes) {
				       // if (iframe.getAttribute("id").startsWith(prefix)) {
				        if (iframe.getAttribute("id").startsWith(prefix)) {
				        	//You may get cd_frame_id_ attribute in any of the profile activity frame
				    	//	boolean bAttributeFlag = (Boolean) js.executeScript("return arguments[0].hasAttribute('cd_frame_id_');",iframe);
				    	//	System.out.println(bAttributeFlag);
				       // 	if(bAttributeFlag==false)
				       // 	{
				        		String[] arrNum = iframe.getAttribute("id").toString().split("_");
				        		arrFrameNumber.add(arrNum[2]);
				       // 	}
				        }
					}
			
				    for(int i = 0; i < arrFrameNumber.size(); i++)
				    {
				        for(int j = 1; j < (arrFrameNumber.size() -i); j++)
				        {
				            //if numbers[j-1] > numbers[j], swap the elements
				            if(Integer.parseInt(arrFrameNumber.get(j-1)) > Integer.parseInt(arrFrameNumber.get(j)))
				            {
				            	temp = Integer.parseInt(arrFrameNumber.get(j-1));
				            	arrFrameNumber.set(j-1, arrFrameNumber.get(j));
				            	arrFrameNumber.set(j, String.valueOf(temp));
				            }
				        }
				    }
				    
					
			} catch (Exception e) {
				System.out.println("fnGetIframeAscOrder--------------Failed");
				DriverScript.log.error("fnGetIframeAscOrder--------------Failed",e);
				////throw(e);
			}
			
			return arrFrameNumber;
		 }
	*/
		
		public ArrayList<String> fnGetIframeAscOrder(String prefix) throws Exception
        {
                        ArrayList<String> arrFrameNumber = new ArrayList<String>();
                        int temp;
                        try {
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
                        for (WebElement iframe : iframes) {
                                        // if (iframe.getAttribute("id").startsWith(prefix)) {
                        				System.out.println(iframe.getAttribute("id"));
                                        if (iframe.getAttribute("id").startsWith(prefix)) {
                                                        //arrFrameNumber.add(iframe.getAttribute("id"));
                                                        boolean bAttributeFlag = (Boolean) js.executeScript("return arguments[0].hasAttribute('cd_frame_id_');",iframe);
                                                        System.out.println(bAttributeFlag);
                                                        if(bAttributeFlag==false)
                                                        {
                                                                        String[] arrNum = iframe.getAttribute("id").toString().split("_");
                                                                        arrFrameNumber.add(arrNum[2]);
                                                        }
                                        }
                        }

                        for(int i = 0; i < arrFrameNumber.size(); i++)
                        {
                                        for(int j = 1; j < (arrFrameNumber.size() -i); j++)
                                        {
                                                        //if numbers[j-1] > numbers[j], swap the elements
                                                        if(Integer.parseInt(arrFrameNumber.get(j-1)) > Integer.parseInt(arrFrameNumber.get(j)))
                                                        {
                                                                        temp = Integer.parseInt(arrFrameNumber.get(j-1));
                                                                        arrFrameNumber.set(j-1, arrFrameNumber.get(j));
                                                                        arrFrameNumber.set(j, String.valueOf(temp));
                                                        }
                                        }
                        }


        } catch (Exception e) {
        System.out.println("fnGetIframeAscOrder--------------Failed");
        TestDriver.log.error("fnGetIframeAscOrder--------------Failed",e);
        ////throw(e);
        }

        return arrFrameNumber;
        }

		public ArrayList<String> fnGetAllIframeAscOrder(String prefix) throws Exception
        {
                        ArrayList<String> arrFrameNumber = new ArrayList<String>();
                        int temp;
                        try {
                        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
                        for (WebElement iframe : iframes) {
                                        // if (iframe.getAttribute("id").startsWith(prefix)) {
                        				System.out.println(iframe.getAttribute("id"));
                                        if (iframe.getAttribute("id").startsWith(prefix)) {
                                                        //arrFrameNumber.add(iframe.getAttribute("id"));
                                                       
                                        String[] arrNum = iframe.getAttribute("id").toString().split("_");
                                        arrFrameNumber.add(arrNum[2]);
                                        }
                        }

                        for(int i = 0; i < arrFrameNumber.size(); i++)
                        {
                                        for(int j = 1; j < (arrFrameNumber.size() -i); j++)
                                        {
                                                        //if numbers[j-1] > numbers[j], swap the elements
                                                        if(Integer.parseInt(arrFrameNumber.get(j-1)) > Integer.parseInt(arrFrameNumber.get(j)))
                                                        {
                                                                        temp = Integer.parseInt(arrFrameNumber.get(j-1));
                                                                        arrFrameNumber.set(j-1, arrFrameNumber.get(j));
                                                                        arrFrameNumber.set(j, String.valueOf(temp));
                                                        }
                                        }
                        }


        } catch (Exception e) {
        System.out.println("fnGetAllIframeAscOrder--------------Failed");
        TestDriver.log.error("fnGetAllIframeAscOrder--------------Failed",e);
        ////throw(e);
        }

        return arrFrameNumber;
        }
		
		public ArrayList<String> fnGetIframeAscOrder_old(String prefix) throws Exception
		 {
			 ArrayList<String> arrFrameNumber = new ArrayList<String>();
			 int temp;
			try {

				    List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
				    for (WebElement iframe : iframes) {
				        if (iframe.getAttribute("id").startsWith(prefix)) {
				        	//arrFrameNumber.add(iframe.getAttribute("id"));
				        	String[] arrNum = iframe.getAttribute("id").toString().split("_");
		    				arrFrameNumber.add(arrNum[2]);
				        }
					}
			
				    for(int i = 0; i < arrFrameNumber.size(); i++)
				    {
				        for(int j = 1; j < (arrFrameNumber.size() -i); j++)
				        {
				            //if numbers[j-1] > numbers[j], swap the elements
				            if(Integer.parseInt(arrFrameNumber.get(j-1)) > Integer.parseInt(arrFrameNumber.get(j)))
				            {
				            	temp = Integer.parseInt(arrFrameNumber.get(j-1));
				            	arrFrameNumber.set(j-1, arrFrameNumber.get(j));
				            	arrFrameNumber.set(j, String.valueOf(temp));
				            }
				        }
				    }
				    
					
			} catch (Exception e) {
				System.out.println("fnGetIframeAscOrder--------------Failed");
				TestDriver.log.error("fnGetIframeAscOrder--------------Failed",e);
				////throw(e);
			}
			
			return arrFrameNumber;
		 }
		
		
		public void fnInitializeGlobalVariables() throws Exception
		{
			try {
				
				mainStep=0;
				subStep=0;
				testCaseStatus=true;
				
			} catch (Exception e) {
				System.out.println("fnInitializeGlobalVariables--------------Failed");
				TestDriver.log.error("fnInitializeGlobalVariables--------------Failed",e);
			}	
		}
		
		
	     
	     public static String fnGetData(String ColumnName) throws Exception
	     {
	    	 String ColumnData=null;
	    	try{  
	    	ColumnData=TestDriver.mTestPhaseData.get(TestDriver.iTC_ID).get(ColumnName);
	    	}
	    	
	    	catch(Exception e){}
	    	
	    	return ColumnData;
	     }
	     
	 
	
	 public String fnGetGVSIframe(String sFrameName) throws Exception
	 {
		String sFlag="false";
		try {
			    List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
			    for (WebElement iframe : iframes) {
			        if (iframe.getAttribute("id").equalsIgnoreCase(sFrameName)) {
			        	System.out.println(iframe.getAttribute("id").toString());
			        	sFlag="true";
			        	driver.switchTo().frame(iframe.getAttribute("id"));
			        	break;
			        // TODO your stuff.
			        }
				}
				
		} catch (Exception e) {
			System.out.println("fnGVSGetIframe--------------Failed");
			throw(e);
		}	
		
		return sFlag;
	 }
	 
	 
	 public void fnOpenDashboard() throws Exception
	 {
		 try {
	            String line;
	            boolean bFlag = true; 
	            Process p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
	            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            while ((line = input.readLine()) != null) {
	              //  System.out.println("Task>>>"+line); //<-- Parse data here.
	                if(line.contains("Dashboardw.exe"))
	                {
	                	bFlag = false;
	                	break;
	                }
	            }
	            input.close();
	            
	            if(bFlag == true)
	            {
	            	System.out.println("Dashboard is not opened. Run OpenDashboardQTP.vbs script");
	            	//ExecuteCommand(FileLoc.sDashboardPath+"OpenDashboardQTP.vbs");	
	            }
	            else
	            {
	            	System.out.println("Dashboard is already opened.");
	            }

		} catch (Exception e) {
			System.out.println("fnOpenDashboard--------------Failed");
			System.out.println("Error in fnOpenDashboard....\n" +e);
		    TestDriver.log.error("Error in fnOpenDashboard....\n" +e);
			throw(e);
		}	
		
	 }
	 

	 // Shameem - review this code and merge with landing page vin validation
	 public void fnLandingPage() throws Exception
	 {	String value;
		 if (driver.findElements(By.xpath("//*[@id='testformname']")).size()==1)
		 {
			 value=driver.findElement(By.xpath("//*[@id='testformname']")).getText();
			 if (value.contains("Account / Subscriber / Vehicle Overview"))
			 {
				 obj.repAddData("Landing Page", "Landing page should display", "Landing page displayed", "Pass");
			 }
			 else
			 {
				 obj.repAddData("Landing Page", "Landing page should display", "Landing page not displayed", "Fail"); 
			 }
		 }
	 }
	 
	 public void fnVerifyActivityEntitlement(String description, String Flag) throws Exception
	 {
		 
		try {Thread.sleep(1000);
			String xEntitleinfoPath="//*[@id='entitlement_table']/table[2]/tbody/tr";
			List <WebElement> Entitleinfo= driver.findElements(By.xpath(xEntitleinfoPath));

			for(int k=3;k<=Entitleinfo.size();k++)
			{
				String sText=driver.findElement(By.xpath(xEntitleinfoPath+"["+k+"]/td[2]")).getText();
				System.out.print(sText);
				if(driver.findElement(By.xpath(xEntitleinfoPath+"["+k+"]/td[1]")).getText().equalsIgnoreCase(description) && sText.equalsIgnoreCase(Flag))
				    {
						obj.repAddData("Service Entitlement", "Service Entitlement should Display", description +" is Displayed with <b>"+ Flag.toUpperCase() +"</b> in Application", "Pass");
						break;
					}
					
			}
		} catch (Exception e) {
			System.out.print("Failed");
		}

	 }
	

	 public void fnMatchText(String sxpath, String object, String text) throws Exception

	 {
		 try
		 {


			 if (driver.findElement(By.xpath(sxpath)).getText().contentEquals(text))
			 {	
				 obj.repAddData(""+object+" text verification", ""+object+" text should be found be verified", ""+object+" text is verified as '" + text + "'", "Pass");	
			 }
			 else
			 {
				 obj.repAddData(""+object+" text verification", ""+object+" text should be found be verified", ""+object+" text is not verified as '" + text + "'", "Fail");
			 }					
		 } catch (Exception e) {
			 System.out.println("History Transaction Verifiation --------------Failed");    
			 TestDriver.log.error("fnHistory failed",e);
		 }
	 }
	 
	
	
	
	 public void fnVerifyHeaders(List<WebElement> arrColumns, int iLoc, String sExpectedText) throws Exception

	 {
		try
		{
			System.out.println(arrColumns.get(iLoc).getText());
			//String Text = arrColumns.get(iLoc).getText();
			if(arrColumns.get(iLoc).getText().equalsIgnoreCase(sExpectedText))
			
			{
				
				HighlightElement(arrColumns.get(iLoc));
				obj.repAddData( "Verify '"+sExpectedText+"' in the Header", "'"+sExpectedText+"' should be shown in the header", "'"+sExpectedText+"' shown successfully in the header", "Pass");
			}
			else
			{
				obj.repAddData( "Verify '"+sExpectedText+"' in the Header", "'"+sExpectedText+"' should be shown in the header", "'"+sExpectedText+"' not shown in the header", "Fail");
			}
			
			if(sExpectedText.equalsIgnoreCase("County") || sExpectedText.equalsIgnoreCase("Last Activity"))
			{
				WebElement ele = arrColumns.get(iLoc).findElement(By.xpath("./img[@src='images/sort-arrows.png']"));
				if(ele.isDisplayed())
				{
					HighlightElement(ele);
					obj.repAddData( "Verify 'Sort (Up and Down)' in the Header for '"+sExpectedText+"'", "'Sort (Up and Down)' should be shown in the header for '"+sExpectedText+"'", "'Sort (Up and Down)' shown successfully in the header for '"+sExpectedText+"'", "Pass");
				}
				else
				{
					obj.repAddData( "Verify 'Sort (Up and Down)' in the Header for '"+sExpectedText+"'", "'Sort (Up and Down)' should be shown in the header for '"+sExpectedText+"'", "'Sort (Up and Down)' not shown in the header for '"+sExpectedText+"'", "Fail");
				}
				
			}
		} catch (Exception e) {
	    System.out.println("fnVerifyHeaders --------------Failed");    
	    TestDriver.log.error("fnVerifyHeaders failed",e);
	    }
	 }
	 
	 
	 public void fnLoadingPageWait() throws Exception

	 {
		try
		{
			WebDriverWait wait = new WebDriverWait(driver,250);
			//WebDriverWait wait = new WebDriverWait(driver,30);
			//CheckId(SphereModules.Common_ViewModules_IconPageLoad_xp,"'Loading...' icon",true);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(SphereModules.Common_ViewModules_IconPageLoad_xp)));	 //Wait for loading message to disappear

		} catch (Exception e) {
	    System.out.println("fnLoadingPageWait --------------Failed");    
	    TestDriver.log.error("fnLoadingPageWait failed",e);
	    }
	 }
	 
	 public void fnVerifyDialogBox(String sActionType, int iVerifyType) throws Exception

	 {  ///sActionType = Add, Update ,Delete, OfficeCode
		 // iVerifyType = 0 for only text verification
		// iVerifyType = 1 for text verification and button click, applicable for OfficeCode only
		try
		{
			boolean bFlag = false;
			WebDriverWait wait = new WebDriverWait(driver,20);
						
			if(ElementFound(SphereModules.Common_AddModule_TextDialogBox) )
			{
					WebElement ele = driver.findElement(By.xpath(SphereModules.Common_AddModule_TextDialogBox));
					HighlightElement(ele);
					String sText = ele.getText().toString().trim();
					
					if(sActionType.equalsIgnoreCase("Add"))
					{
						if(sText.equalsIgnoreCase(AppMessages.Common_AddModule_DialogBox_msg))
						{
							bFlag = true;
						}
		
					}
					else if(sActionType.equalsIgnoreCase("Update"))
					{
						if(sText.equalsIgnoreCase(AppMessages.Common_UpdateModule_DialogBox_msg))
						{
							bFlag = true;
						}
		
					}
					else if(sActionType.equalsIgnoreCase("Delete"))
					{
						if(sText.equalsIgnoreCase(AppMessages.Common_DeleteModule_DialogBox_msg))
						{
							bFlag = true;
						}
		
					}
					else if(sActionType.equalsIgnoreCase("OfficeCode"))
					{
						if(sText.equalsIgnoreCase(AppMessages.Common_ViewModule_DialogBoxOfficeCode_msg))
						{
							bFlag = true;
						}
		
					}
					
					
					if(iVerifyType==1)
					{
						try {
							ClickByXpath(SphereModules.Common_AddModule_BtnDialogBox, "OK button", true);
						} catch (Exception e) {
							bFlag = false;
							System.out.println("OK button not displayed");
							obj.repAddData( "Click OK button",  "OK button should be clicked", "OK button not clicked", "Fail");
							e.printStackTrace();
							TestDriver.log.info("Dialog box OK click failed", e);
						}
					}
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(SphereModules.Common_AddModule_TextDialogBox)));	 //Wait for dialog message to disappear
					
					if(bFlag==true)
					{
						obj.repAddData( "Check '"+sActionType+"' Message", "'"+sActionType+"' message should be displayed properly","Correct message displayed as '"+sText+"'", "Pass");
					}
					else
					{
						obj.repAddData( "Check '"+sActionType+"' Message",  "'"+sActionType+"' message should be displayed properly", "Wrong message displayed as '"+sText+"'", "Fail");
					}
			}
			else
			{
				obj.repAddData( "Check '"+sActionType+"' Message", "'"+sActionType+"' message should be displayed properly","Dialog box not available", "Fail");
			}

		} catch (Exception e) {
	    System.out.println("fnVerifyDialogBox --------------Failed");    
	    TestDriver.log.error("fnVerifyDialogBox failed",e);
	    }
	 }
	 
	 
	 public void fnVerifyCancelForm(int iButton) throws Exception

	 {  
		 //iButton = 1 for Yes and 0 for No
		 try
		 {

			 WebDriverWait wait = new WebDriverWait(driver,20);
			 if(iButton == 1)
			 {
				 try {
					 ClickByXpath(SphereModules.Common_CancelModule_BtnYes, "YES button", true);
				 } catch (Exception e) {

					 obj.repAddData( "Click YES button",  "YES button should be clicked", "YES button not clicked", "Fail");
					 e.printStackTrace();
					 TestDriver.log.info("Dialog box Yes Button click failed", e);
				 }
			 }
			 else
			 {
				 try {
					 ClickByXpath(SphereModules.Common_CancelModule_BtnNo, "NO button", true);
				 } catch (Exception e) {

					 obj.repAddData( "Click NO button",  "NO button should be clicked", "NO button not clicked", "Fail");
					 e.printStackTrace();
					 TestDriver.log.info("Dialog box NO Button click failed", e);
				 }

			 }

			 wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(SphereModules.Common_CancelModule_TextDialogBox)));	 //Wait for dialog message to disappear



		 } catch (Exception e) {
			 TestDriver.log.error("fnVerifyDialogBox failed",e);
		 }
	 }
	 
	 
	 
	/* public void fnCheckSortedList(HashMap<Integer, HashMap<Integer, String>> mTableData,List<WebElement> arrColumns, String sColumnName, int iColNum) throws Exception

	 {
		try
		{
			//List<WebElement> arrTBodies = driver.findElements(By.xpath(sTableXPath));
			//List<WebElement> arrColumns= eleTable.findElements(By.xpath("./tr"));;
			////////////////////Sorting Logic////////////////////////////
			List<String> arrInitialList = new ArrayList<String>();
			List<String> arrExpSortedList = new ArrayList<String>();
			List<String> arrActSortedList = new ArrayList<String>();
			arrInitialList.clear();
			arrExpSortedList.clear();
			arrActSortedList.clear();
			
			for(int i=0; i<mTableData.size();i++)
			{
				arrInitialList.add(mTableData.get(i+1).get(iColNum).toString().toUpperCase());
			}
				
		    Collections.sort(arrInitialList);
			
			arrExpSortedList = arrInitialList; //Expected value after sorting
			//WebElement ele = arrColumns.get(iColNum).findElement(By.xpath("./img[@src='images/sort-arrows.png']"));
			
			//WebElement eleSortAscBtn = driver.findElement(By.xpath(SphereModules.Common_Btn_Sorting_xp));
			//eleSortAscBtn.click();
			
			WebElement eleCountySortBtn = arrColumns.get(iColNum-1).findElement(By.xpath(SphereModules.Common_BtnSorting_xp));
			eleCountySortBtn.click();
			
			for(int i=0; i<mTableData.size();i++)
			{
					arrActSortedList.add(mTableData.get(i+1).get(iColNum).toString().toUpperCase());  //Actual value without sorting
			}
			
			System.out.println("Expected Sorting Done");
		
			if(arrActSortedList.equals(arrExpSortedList))
			{
				obj.repAddData( "Verify Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in ascending order", "'"+sColumnName+"' values are sorted in ascending order", "Pass");
			}
			else
			{
				obj.repAddData( "Verify Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in ascending order", "'"+sColumnName+"' values are not sorted in ascending order", "Fail");
			}
		
			////////////////////Sorting Logic End////////////////////////////
		} catch (Exception e) {
	    System.out.println("fnCheckSorting --------------Failed");    
	    TestDriver.log.error("fnCheckSorting failed",e);
	    }
	 }*/
	 
	 public void fnCheckSorting(String sTableXPath, String sColumnName, int iColNum) throws Exception

	 {
		try
		{
			////////////////////Sorting Logic////////////////////////////
			List<WebElement> arrTBodies = driver.findElements(By.xpath(sTableXPath));
			List<WebElement> arrColumns = arrTBodies.get(0).findElements(By.xpath("./tr/th"));  //Get the header
			
			List<String> arrInitialList = new ArrayList<String>();
			List<String> arrExpSortedList = new ArrayList<String>();
			List<String> arrActSortedList = new ArrayList<String>();
			
			arrInitialList.clear();
			arrExpSortedList.clear();
			arrActSortedList.clear();
			
			for(int iRec=1;iRec<arrTBodies.size();iRec++)
			{
				arrInitialList.add(arrTBodies.get(iRec).findElement(By.xpath("./tr/td["+iColNum+"]")).getText().trim().toUpperCase());
				
			}
			
			Collections.sort(arrInitialList);
/*			if(sColumnName.equalsIgnoreCase("Last Activity"))  // On clicking last activity sort, it first get in descending order 
			{
				Collections.reverse(arrInitialList); 
			}*/

			arrExpSortedList = arrInitialList;
			System.out.println("Initial Expected Sorting Done");
			
			WebElement eleCountySortBtn = arrColumns.get(iColNum-1).findElement(By.xpath(SphereModules.Common_BtnSorting_xp));
			eleCountySortBtn.click();
			//fnLoadingPageWait();
			
			arrTBodies = driver.findElements(By.xpath(sTableXPath));
			for(int iRec=1;iRec<arrTBodies.size();iRec++)
			{
				arrActSortedList.add(arrTBodies.get(iRec).findElement(By.xpath("./tr/td["+iColNum+"]")).getText().trim().toUpperCase());
				
			}
			System.out.println("Sorted List Done");
			if(arrActSortedList.equals(arrExpSortedList))
			{
				obj.repAddData( "Verify Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in ascending order", "'"+sColumnName+"' values are sorted in ascending order", "Pass");
			}
			else
			{
				obj.repAddData( "Verify Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in ascending order", "'"+sColumnName+"' values are not sorted in ascending order", "Fail");
			}
			
			Collections.reverse(arrExpSortedList);
			arrColumns = arrTBodies.get(0).findElements(By.xpath("./tr/th"));
			eleCountySortBtn = arrColumns.get(iColNum-1).findElement(By.xpath(SphereModules.Common_BtnSorting_xp));
			eleCountySortBtn.click();
			//fnLoadingPageWait();
			
			arrTBodies = driver.findElements(By.xpath(sTableXPath));
			arrActSortedList.clear();
			for(int iRec=1;iRec<arrTBodies.size();iRec++)
			{
				arrActSortedList.add(arrTBodies.get(iRec).findElement(By.xpath("./tr/td["+iColNum+"]")).getText().trim().toUpperCase());
				
			}
			System.out.println("Sorted List Done");
			if(arrActSortedList.equals(arrExpSortedList))
			{
				obj.repAddData( "Verify Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in descending order", "'"+sColumnName+"' values are sorted in descending order", "Pass");
			}
			else
			{
				obj.repAddData( "Verify Sor	ting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in descending order", "'"+sColumnName+"' values are not sorted in descending order", "Fail");
			}
			////////////////////Sorting Logic End////////////////////////////
		} catch (Exception e) {
	    System.out.println("fnCheckSorting --------------Failed");    
	    TestDriver.log.error("fnCheckSorting failed",e);
	    }
	 }
	 public void fnCheckDefaultSorting(String sTableXPath, String sColumnName, int iColNum, String sColXpath) throws Exception

	 {
		try
		{
			////////////////////Sorting Logic////////////////////////////
			WebElement arrTBodies = driver.findElement(By.xpath(sTableXPath));
			List<WebElement> arrRows = arrTBodies.findElements(By.xpath("tr"));  //Get the header
			List<String> arrDFInitialList = new ArrayList<String>();
			for(int i=1;i<=arrRows.size();i++)
			{
				String Data=driver.findElement(By.xpath(sTableXPath+"/tr["+i+"]/td["+iColNum+"]")).getText().trim();
				arrDFInitialList.add(Data);
				
			}
			
			/*String sQuery = objSQLConfig.sDeals_ViewAllDeals_Query;
			TestDriver.conn = objDBUtility.fnOpenDBConnection();
			TestDriver.rset = objDBUtility.fnGetDBData(TestDriver.conn,sQuery);
			HashMap<Integer, HashMap<Integer, String>> mTableDataDB = objDBUtility.fnWriteResultSet(rset);
			
			objDBUtility.fnCloseDBConnection(TestDriver.stmt, TestDriver.rset, TestDriver.conn);
			
			System.out.println(arrDFInitialList.size());
			Boolean bResultFlag = true;
			
			//mTableDataDB.equals(mTableDataDB);
			for(int i=1;i<=arrDFInitialList.size(); i++)
			{
				String sDealNumber = arrDFInitialList.get(i).toString();
				System.out.println(arrDFInitialList.get(i).equals(mTableDataDB.get(i)));
				if(!arrDFInitialList.get(i).equals(mTableDataDB.get(i)))
				{
					bResultFlag = false;
					obj.repAddData( "Compare record for deal number : "+ sDealNumber, "Record on UI and DB should match for deal number : "+ sDealNumber, "Validation failed for deal number : "+ sDealNumber, "Fail");
				}
				
				
				//System.out.println(mTableDataUI.get(i).get(2));
				//System.out.println(mTableDataUI.get(i).get(3));
			}*/
			System.out.println(arrDFInitialList);
			List<String> arrInitialList = new ArrayList<String>();
			List<String> arrExpSortedList = new ArrayList<String>();
			List<String> arrActSortedList = new ArrayList<String>();
			
			/*arrInitialList.clear();
			arrExpSortedList.clear();
			arrActSortedList.clear();
			
			for(int iRec=1;iRec<arrTBodies.size();iRec++)
			{
				arrInitialList.add(arrTBodies.get(iRec).findElement(By.xpath("./tr/td["+iColNum+"]")).getText().trim().toUpperCase());
				
			}*/
			
			Collections.sort(arrDFInitialList);
			System.out.println(arrDFInitialList);
/*			if(sColumnName.equalsIgnoreCase("Last Activity"))  // On clicking last activity sort, it first get in descending order 
			{
				Collections.reverse(arrInitialList); 
			}*/

			arrExpSortedList = arrDFInitialList;
			System.out.println("Initial Expected Sorting Done");
			
			WebElement eleSortBtn = arrRows.get(iColNum).findElement(By.xpath(sColXpath));
			eleSortBtn.click();
			//fnLoadingPageWait();
			WebElement arrAscTBody = driver.findElement(By.xpath(sTableXPath));
			
			List<WebElement> arrAscTBodies=arrAscTBody.findElements(By.tagName("tr")); 
			for(int iRec=1;iRec<arrAscTBodies.size();iRec++)
			{
				arrActSortedList.add(arrAscTBodies.get(iRec).findElement(By.xpath("./tr/td["+iColNum+"]")).getText().trim().toUpperCase());
				
			}
			System.out.println("Sorted List Done");
			if(arrActSortedList.equals(arrExpSortedList))
			{
				obj.repAddData( "Verify Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in ascending order", "'"+sColumnName+"' values are sorted in ascending order", "Pass");
			}
			else
			{
				obj.repAddData( "Verify Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in ascending order", "'"+sColumnName+"' values are not sorted in ascending order", "Fail");
			}
			
			Collections.reverse(arrExpSortedList);
		
			WebElement eleDesSortBtn = arrRows.get(iColNum).findElement(By.xpath(sColXpath));
			eleDesSortBtn.click();
			//fnLoadingPageWait();
			
            WebElement arrDesTBody = driver.findElement(By.xpath(sTableXPath));
			
			List<WebElement> arrDesTBodies=arrDesTBody.findElements(By.tagName("tr")); 
			arrActSortedList.clear();
			for(int iRec=1;iRec<arrDesTBodies.size();iRec++)
			{
				arrActSortedList.add(arrDesTBodies.get(iRec).findElement(By.xpath("./tr/td["+iColNum+"]")).getText().trim().toUpperCase());
				
			}
			System.out.println("Sorted List Done");
			if(arrActSortedList.equals(arrExpSortedList))
			{
				obj.repAddData( "Verify Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in descending order", "'"+sColumnName+"' values are sorted in descending order", "Pass");
			}
			else
			{
				obj.repAddData( "Verify Sor	ting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in descending order", "'"+sColumnName+"' values are not sorted in descending order", "Fail");
			}
			////////////////////Sorting Logic End////////////////////////////
		} catch (Exception e) {
	    System.out.println("fnCheckSorting --------------Failed");    
	    TestDriver.log.error("fnCheckSorting failed",e);
	    }
	 }
	 
	 //Checks for buttons under specified headers
	 public void fnCheckButtonsUnderHeader(String sHeaderXPath, List<String> arrButtons, String sHeaderName, boolean bReportFlag ) throws Exception
	 //add option to check if verified...
	 {
		try
		{

			//Find the header
			WebElement eleHeader = driver.findElement(By.xpath(sHeaderXPath));
			
			WebElement eleParent = eleHeader.findElement(By.xpath("..")).findElement(By.xpath(".."));
			
			System.out.println(eleParent.getText());

			System.out.println("ASDFASDFASDFAsd");
			
			List<WebElement> arrButtonActual  = eleParent.findElements(By.xpath("./button"));
			
			
			List<String> arrButtonInputs = new ArrayList<String>();
			List<String> arrButtonLabels = new ArrayList<String>();
			arrButtonLabels.clear();
			arrButtonInputs.clear();
			
			//Create array of button labels from the app
			for(int iRec=0;iRec<arrButtons.size();iRec++)
			{
				arrButtonInputs.add(driver.findElement(By.xpath(arrButtons.get(iRec))).getText());
				System.out.println(driver.findElement(By.xpath(arrButtons.get(iRec))).getText());
				System.out.println(iRec);
				//System.out.println(arrButtonActual.size());
				arrButtonLabels.add(arrButtonActual.get(iRec).getText());
				System.out.println(arrButtonLabels.get(iRec));
			}
				
		
			if(arrButtonInputs.equals(arrButtonLabels))
			{
				obj.repAddData( "Verify Buttons under '"+sHeaderName+"' Header", "'"+sHeaderName+"'", "'"+sHeaderName+"' Buttons are correct", "Pass");
			}
			else
			{
				obj.repAddData( "Verify Buttons under '"+sHeaderName+"' Header", "'"+sHeaderName+"'", "'"+sHeaderName+"' Buttons are incorrect or missing", "Fail");
			}
			

		} catch (Exception e) {
	    System.out.println("fnCheckButtonsUnderHeader --------------Failed");    
	    TestDriver.log.error("fnCheckButtonsUnderHeader failed",e);
	    }
	 }
	 
	 
	 
	 
	 
	 
	 public void fnCheckDateSorting(HashMap<Integer, HashMap<Integer, String>> mTableData, String sColumnName, int iColNum, String sSortType) throws Exception

	 {
		try
		{  //sSortType can be DEFAULT, ASC , DESC
			////////////////////Sorting Logic////////////////////////////
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

			List<Date> arrInitialList = new ArrayList<Date>();
			List<Date> arrExpSortedList = new ArrayList<Date>();
			List<Date> arrActSortedList = new ArrayList<Date>();
			//List<Date> arrTempList = new ArrayList<Date>();
						
			arrInitialList.clear();
			arrExpSortedList.clear();
			arrActSortedList.clear();
			arrActSortedList.clear();
			
			for(int i=0; i<mTableData.size();i++)
			{
				String sExpLastActivity = mTableData.get(i+1).get(iColNum).toString().trim();
				Date dtExpLastActivity = formatter.parse(sExpLastActivity);
				System.out.println(dtExpLastActivity);
				arrInitialList.add(dtExpLastActivity);
			}
			
			arrActSortedList = arrInitialList;
			
			Collections.sort(arrInitialList);
			if(sSortType.equalsIgnoreCase("DEFAULT") || sSortType.equalsIgnoreCase("ASC"))
			{
				if(sColumnName.equalsIgnoreCase("Last Activity"))  // On clicking last activity sort, it first get in descending order 
				{
					Collections.reverse(arrInitialList); 
				}
				
				arrExpSortedList = arrInitialList;  //In sorted descending order
				
				if(arrExpSortedList.equals(arrActSortedList))
				{
					obj.repAddData( "Verify Default Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in descending order", "'"+sColumnName+"' values are sorted in descending order", "Pass");
				}
				else
				{
					obj.repAddData( "Verify Default Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in descending order", "'"+sColumnName+"' values are not sorted in descending order", "Fail");
				}
			}
			else if(sSortType.equalsIgnoreCase("ASC"))
			{
				arrExpSortedList = arrInitialList;
				
				if(arrExpSortedList.equals(arrActSortedList))
				{
					obj.repAddData( "Verify Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in ascending order", "'"+sColumnName+"' values are sorted in ascending order", "Pass");
				}
				else
				{
					obj.repAddData( "Verify Sor	ting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in ascending order", "'"+sColumnName+"' values are not sorted in ascending order", "Fail");
				}
			}
			////////////////////Sorting Logic End////////////////////////////
		} catch (Exception e) {
	    System.out.println("fnCheckDateSorting --------------Failed");    
	    TestDriver.log.error("fnCheckDateSorting failed",e);
	    }
	 }
	 
	 
	/* public void fnCheckDateSorting_old(String sTableXPath, String sColumnName, int iColNum, String sSortType) throws Exception

	 {
		try
		{  //sSortType can be DEFAULT, ASC , DESC
			////////////////////Sorting Logic////////////////////////////
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
			
			WebElement eleTable = driver.findElement(By.xpath(sTableXPath));
			List<WebElement> arrColumns = eleTable.findElements(By.xpath("./thead/tr/th"));  //Get the header
			List<WebElement> arrTBodies = eleTable.findElements(By.xpath("./tbody/tr"));
			WebElement eleCountySortBtn;
			
			List<Date> arrInitialList = new ArrayList<Date>();
			List<Date> arrExpSortedList = new ArrayList<Date>();
			List<Date> arrActSortedList = new ArrayList<Date>();
			List<Date> arrTempList = new ArrayList<Date>();
			
			arrInitialList.clear();
			arrExpSortedList.clear();
			arrActSortedList.clear();
			
			for(int iRec=1;iRec<arrTBodies.size();iRec++)
			{
				String sExpLastActivity = arrTBodies.get(iRec).findElement(By.xpath("./td["+iColNum+"]")).getText().trim();
				System.out.println(sExpLastActivity);
				Date dtExpLastActivity = formatter.parse(sExpLastActivity);
				System.out.println(dtExpLastActivity);
				arrInitialList.add(dtExpLastActivity);
			}
			
			arrTempList = arrInitialList;
			
			Collections.sort(arrInitialList);
			if(sSortType.equalsIgnoreCase("DEFAULT"))
			{
				if(sColumnName.equalsIgnoreCase("Last Activity"))  // On clicking last activity sort, it first get in descending order 
				{
					Collections.reverse(arrInitialList); 
				}
				
				arrExpSortedList = arrInitialList;  //In sorted descending order
				
				if(arrExpSortedList.equals(arrTempList))
				{
					obj.repAddData( "Verify Default Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in descending order", "'"+sColumnName+"' values are sorted in descending order", "Pass");
				}
				else
				{
					obj.repAddData( "Verify Default Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in descending order", "'"+sColumnName+"' values are not sorted in descending order", "Fail");
				}
			}
			//System.out.println("Initial Expected Sorting Done");
			
			else if(sSortType.equalsIgnoreCase("DESC"))
			{
				eleCountySortBtn = arrColumns.get(iColNum-1).findElement(By.xpath(SphereModules.Common_BtnSorting_xp));
				//eleCountySortBtn.click();
				ClickByElement(eleCountySortBtn,"'Sort Icon'",true);
				fnLoadingPageWait();
				
				arrTBodies = driver.findElements(By.xpath(sTableXPath));
				for(int iRec=1;iRec<arrTBodies.size();iRec++)
				{
					String sActLastActivity = arrTBodies.get(iRec).findElement(By.xpath("./tr/td["+iColNum+"]")).getText().trim();
					System.out.println(sActLastActivity);
					Date dtActLastActivity = formatter.parse(sActLastActivity);
					System.out.println(dtActLastActivity);
					arrActSortedList.add(dtActLastActivity);
					
				}
				System.out.println("Sorted List Done");
				if(arrActSortedList.equals(arrExpSortedList))
				{
					obj.repAddData( "Verify Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in descending order", "'"+sColumnName+"' values are sorted in descending order", "Pass");
				}
				else
				{
					obj.repAddData( "Verify Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in descending order", "'"+sColumnName+"' values are not sorted in descending order", "Fail");
				}
			}
			else if(sSortType.equalsIgnoreCase("ASC"))
			{
				Collections.reverse(arrExpSortedList);
				arrColumns = arrTBodies.get(0).findElements(By.xpath("./tr/th"));
				eleCountySortBtn = arrColumns.get(iColNum-1).findElement(By.xpath(SphereModules.Common_BtnSorting_xp));
				//eleCountySortBtn.click();
				ClickByElement(eleCountySortBtn,"'Sort Icon'",true);
				fnLoadingPageWait();
				
				arrTBodies = driver.findElements(By.xpath(sTableXPath));
				arrActSortedList.clear();
				for(int iRec=1;iRec<arrTBodies.size();iRec++)
				{
					String sActLastActivity = arrTBodies.get(iRec).findElement(By.xpath("./tr/td["+iColNum+"]")).getText().trim();
					System.out.println(sActLastActivity);
					Date dtActLastActivity = formatter.parse(sActLastActivity);
					System.out.println(dtActLastActivity);
					arrActSortedList.add(dtActLastActivity);
					
				}
				System.out.println("Sorted List Done");
				if(arrActSortedList.equals(arrExpSortedList))
				{
					obj.repAddData( "Verify Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in ascending order", "'"+sColumnName+"' values are sorted in ascending order", "Pass");
				}
				else
				{
					obj.repAddData( "Verify Sor	ting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in ascending order", "'"+sColumnName+"' values are not sorted in ascending order", "Fail");
				}
			}
			////////////////////Sorting Logic End////////////////////////////
		} catch (Exception e) {
	    System.out.println("fnCheckDateSorting --------------Failed");    
	    TestDriver.log.error("fnCheckDateSorting failed",e);
	    }
	 }*/
	 
	 public void fnVerifyComboBoxValues(List<WebElement> arrPageSize, int iLoc, String sValue) throws Exception

	 {
		try
		{
			if(arrPageSize.get(iLoc).getText().toString().trim().equalsIgnoreCase(sValue))
			{
				obj.repAddData( "Verify Page Size Combo box for value '"+sValue+"'", "'"+sValue+"' value should be shown in the Page Size Combo box", "'"+sValue+"' is shown in the Page Size Combo box", "Pass");
			}
			else
			{
				obj.repAddData( "Verify Page Size Combo box for value '"+sValue+"'", "'"+sValue+"' value should be shown in the Page Size Combo box", "'"+sValue+"' is not shown in the Page Size Combo box", "Fail");
			}
			
		} catch (Exception e) {
	    System.out.println("fnVerifyComboBoxValues --------------Failed");    
	    TestDriver.log.error("fnVerifyComboBoxValues failed",e);
	    }
	 }
	 
	 
	 //public HashMap<Integer, HashMap<Integer, String>> fnGetTableData(String sTablePath, String sModuleName) throws Exception
	 public HashMap<Integer, HashMap<Integer, String>> fnGetTableData (String sTablePath) throws Exception
	 {
		HashMap<Integer, HashMap<Integer, String>> mTableData = new HashMap<Integer,HashMap<Integer, String>>();
		try
		{
			WebElement eleTable = driver.findElement(By.xpath(sTablePath));
			List<WebElement> arrTableRows = eleTable.findElements(By.xpath("./tr"));  //Get the table data rows
			System.out.println("Data Rows Size>>>>"+arrTableRows.size());
			//int i=0;//Header column
			int iColStartCount =1; 
			for(int iRow=0;iRow<arrTableRows.size();iRow++)  //Exclude first header row - not applicable now
			{	
			
				String sColValue=null;
				mTableData.put(iRow+1, new HashMap<Integer, String>());  //Map row starts with 1, so add 1 to iRow
				
				List<WebElement> arrTableColumns = arrTableRows.get(iRow).findElements(By.xpath("./td"));  //Get the table data rows
				for(int iCol=iColStartCount;iCol<arrTableColumns.size();iCol++)
				{
					//if(sModuleName.equalsIgnoreCase("Users"))
					if(iCol==0 )
						        //For now, checkbox would be ignored, initialize if you want to include the checkbox
					{       							
							sColValue = arrTableColumns.get(iCol).findElement(By.xpath("./input")).getAttribute("type");
							
							}
					
						   else
						 {
							sColValue = arrTableColumns.get(iCol).getText();//.toString();
							if(sColValue.contains("/") && sColValue.contains(":")) //Format Dates
							{
								sColValue = sColValue.replace("@ ", "");
								sColValue = sColValue.replace("PM", " PM");
								sColValue = sColValue.replace("AM", " AM");
								sColValue = fnDateFormatter(sColValue,"MM/dd/yyyy hh:mm a");
								//sColValue = fnDateFormatter(sColValue,"MM/dd/yyyy '@'hh:mm a");
								//sColValue = fnDateFormatter(sColValue,"MM/dd/yyyy hh:mm:ss  a");
							}
						
						}
					/*if(sColValue.contains("208")){
						sColValue="Set Lighting - CA";
						
					}*/
					
					//if(sColValue.contains("(") && sColValue.contains(")") && sColValue.contains("-")) //Format Dates
					//{
					//	sColValue = sColValue.
						
					//}
				
					//mTableData.get(iRow).put(iCol+1,sColValue);
						if(sColValue.contains("null") || sColValue.contains("undefined"))
						{
							sColValue="";
						}
						mTableData.get(iRow+1).put(iCol,sColValue);
				}
				

			}
			
			
		}catch (Exception e) {
	    System.out.println("fnGetTableData --------------Failed");    
	    TestDriver.log.error("fnGetTableData failed",e);
	    }
		
		return mTableData;
	 }
	 
	 
	 public boolean fnCheckFieldDisplayById(String sLocator, String sReportText, boolean bReportFlag, boolean bScenarioFlag) throws Exception 
		{
		 	boolean bElementFound = false;
			try {
				WebElement ele;
				ele = driver.findElement(By.id(sLocator));
				
				String sText = "";
								
				if(bReportFlag==true)
				{	
					if(bScenarioFlag==true)
					{
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
						if(ele.isDisplayed())
						{
							HighlightElementById(sLocator);
							bElementFound = true;
							sText = ele.getText().trim();
							obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" shown successfully with value '"+sText+"'", "Pass");
							
						}
						else
						{
							obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" not shown on the screen", "Fail");
						}
					}
					else if(bScenarioFlag==false)
					{
						if(!ele.isDisplayed())
						{   bElementFound = true;
							obj.repAddData( "Verify "+sReportText, sReportText+" should not be shown", sReportText+" not shown on the screen", "Pass");
							
						}
						else
						{
							obj.repAddData( "Verify "+sReportText, sReportText+" should not be shown", sReportText+" shown on the screen", "Fail");
						}
						
					}
				}
				else
				{
					if(ele.isDisplayed())
					{
						bElementFound = true;
					}
					else
					{
						bElementFound = false;
					}
				}
					
			} catch (Exception e) {
				System.out.println("fnCheckFieldDisplayById --------------Failed");  
				 TestDriver.log.error("fnCheckFieldDisplayById failed",e);
				throw(e);
			}
			return bElementFound;
		}
	 
	 public boolean fnCheckFieldDisplayByName(String sLocator, String sReportText, boolean bReportFlag, boolean bScenarioFlag) throws Exception 
		{
		 	boolean bElementFound = false;
			try {
				WebElement ele;
				ele = driver.findElement(By.name(sLocator));
				String sText = "";
								
				if(bReportFlag==true)
				{	
					if(bScenarioFlag==true)
					{
						if(ele.isDisplayed())
						{
							HighlightElementByName(sLocator);
							bElementFound = true;
							sText = ele.getText().trim();
							obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" shown successfully with value '"+sText+"'", "Pass");
							
						}
						else
						{
							obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" not shown on the screen", "Fail");
						}
					}
					else if(bScenarioFlag==false)
					{
						if(!ele.isDisplayed())
						{   bElementFound = true;
							obj.repAddData( "Verify "+sReportText, sReportText+" should not be shown", sReportText+" not shown on the screen", "Pass");
							
						}
						else
						{
							obj.repAddData( "Verify "+sReportText, sReportText+" should not be shown", sReportText+" shown on the screen", "Fail");
						}
						
					}
				}
				else
				{
					if(ele.isDisplayed())
					{
						bElementFound = true;
					}
					else
					{
						bElementFound = false;
					}
				}
					
			} catch (Exception e) {
				System.out.println("fnCheckFieldDisplayByName --------------Failed");  
				 TestDriver.log.error("fnCheckFieldDisplayByName failed",e);
				throw(e);
			}
			return bElementFound;
		}
	 
	 public boolean fnCheckFieldDisplayByXpath(String sLocator, String sReportText, boolean bReportFlag, boolean bScenarioFlag) throws Exception 
		{
		 	boolean bElementFound = false;
			try {
				WebElement ele;
				ele = driver.findElement(By.xpath(sLocator));
				String sText = "";
				
				if(bReportFlag==true)
				{	
					if(bScenarioFlag==true)
					{
						if(ele.isDisplayed())
						{ 
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
							Thread.sleep(1000);
							HighlightElementByXpath(sLocator);			//test and see nothing breaks	
							bElementFound = true;
							if(sLocator.contains("select"))
							{
								sText = driver.findElement(By.xpath(sLocator)).getAttribute("name");
							}
							else if(sLocator.contains("logo"))
							{
								sText = driver.findElement(By.xpath(sLocator)).getAttribute("title");
								sText = "Logo Title : "+ sText;
							}
							else
							{
								sText = ele.getText().trim();
							}
							obj.repAddData( "Verify "+sReportText, sReportText+" should be displayed", sReportText+" displayed successfully with value '"+sText+"'", "Pass");
							
						}
						else
						{
							obj.repAddData( "Verify "+sReportText, sReportText+" should be displayed", sReportText+" not displayed on the screen", "Fail");
						}
					}
					else if(bScenarioFlag==false)
					{
						if(!ele.isDisplayed())
						{   bElementFound = true;
							obj.repAddData( "Verify "+sReportText, sReportText+" should not be displayed", sReportText+" not displayed on the screen", "Pass");
							
						}
						else
						{
							obj.repAddData( "Verify "+sReportText, sReportText+" should not be displayed", sReportText+" displayed on the screen", "Fail");
						}
						
					}
				}
				else
				{
					if(ele.isDisplayed())
					{
						bElementFound = true;
					}
					else
					{
						bElementFound = false;
					}
				}
					
			} catch (Exception e) {
				System.out.println("fnCheckFieldDisplayByXpath --------------Failed");  
				 TestDriver.log.error("fnCheckFieldDisplayByXpath failed",e);
				throw(e);
			}
			return bElementFound;
		}
	 
	 
	 
	 public boolean fnCheckFieldDoesNotExistById(String sLocator, String sReportText, boolean bReportFlag) throws Exception 
		{
		 	boolean bElementFound = false;
			try {
				
				if(bReportFlag==true)
				{	
						if(driver.findElements(By.id(sLocator)).size() < 1)
						{   bElementFound = true;
							obj.repAddData( "Verify "+sReportText, sReportText+" should not be shown", sReportText+" not shown on the screen", "Pass");
							
						}
						else
						{
							obj.repAddData( "Verify "+sReportText, sReportText+" should not be shown", sReportText+" shown on the screen", "Fail");
						}
				}
				else
				{
					if(driver.findElements(By.id(sLocator)).size() < 1)
					{
						bElementFound = true;
					}
					else
					{
						bElementFound = false;
					}
				}
					
			} catch (Exception e) {
				System.out.println("fnCheckFieldDoesNotExistById --------------Failed");  
				 TestDriver.log.error("fnCheckFieldDoesNotExistById failed",e);
				throw(e);
			}
			return bElementFound;
		}
	 
	 
	 public boolean fnCheckFieldDoesNotExistByXpath(String sLocator, String sReportText, boolean bReportFlag) throws Exception 
		{
		 	boolean bElementFound = false;
			try {
				WebElement ele;
				//ele = driver.findElement(By.xpath(sLocator));
				
				if(bReportFlag==true)
				{	
						//if(driver.findElements(By.xpath(sLocator)).size() < 1)
							if(driver.findElements(By.xpath(sLocator)).size() == 0)
						{   
							bElementFound = true;
							obj.repAddData( "Verify "+sReportText, sReportText+" should not be shown", sReportText+" not shown on the screen", "Pass");
							
						}
						else
						{
							obj.repAddData( "Verify "+sReportText, sReportText+" should not be shown", sReportText+" shown on the screen", "Fail");
						}
				}
				else
				{
					if(driver.findElements(By.xpath(sLocator)).size() < 1)
					{
						bElementFound = true;
					}
					else
					{
						bElementFound = false;
					}
				}
					
			} catch (Exception e) {
				System.out.println("fnCheckFieldDoesNotExistByXpath --------------Failed");  
				 TestDriver.log.error("fnCheckFieldDoesNotExistByXpath failed",e);
				throw(e);
			}
			return bElementFound;
		}
	 
	 
	 
	 public void fnVerifyNumofRecords(String sPageOption) throws Exception 
		{
		 	boolean bElementFound = false;
			try {
				WebElement eleTable = driver.findElement(By.xpath(SphereModules.Common_ViewUserModule_Table_xp));
				List<WebElement> arrTableRows = eleTable.findElements(By.xpath("./tbody/tr"));  //Get the table data rows
				System.out.println("Data Rows Size>>>>"+arrTableRows.size());
				String sTotalPages = "";
				if(driver.findElement(By.xpath(SphereModules.Common_ViewModules_InputPageNum_xp)).isDisplayed())
				{
					sTotalPages = fnGetGUITextXpath(SphereModules.Common_ViewModules_LabelTotalPages_xp);
				}
				
				if(arrTableRows.size()==Integer.parseInt(sPageOption))
				{
					obj.repAddData( "Verify No of Records for Option '"+sPageOption+" PER PAGE'", sPageOption+" records should be shown", arrTableRows.size()+" records shown successfully", "Pass");

				}
				else if(arrTableRows.size()<Integer.parseInt(sPageOption))
				{
					
					bElementFound = fnCheckFieldDisplayByXpath(SphereModules.Common_ViewModules_InputPageNum_xp,"'Page Number' textbox",false,true);
					if(bElementFound==true && !sTotalPages.equalsIgnoreCase(""))  //for last page having less records
					{
						obj.repAddData( "Verify No of Records for Option '"+sPageOption+" PER PAGE'", sPageOption+" or less records should be shown", arrTableRows.size()+" records shown successfully", "Pass");
					}
					else if(bElementFound==false && sTotalPages.equalsIgnoreCase(""))  //for first page having less records
					{
						obj.repAddData( "Verify No of Records for Option '"+sPageOption+" PER PAGE'", sPageOption+" or less records should be shown", arrTableRows.size()+" records shown successfully", "Pass");
					}
					else
					{
						obj.repAddData( "Verify No of Records for Option '"+sPageOption+" PER PAGE'", sPageOption+" or less records should be shown", arrTableRows.size()+" records shown but pagination is still present", "Fail");
					}
				}
				else
				{
					obj.repAddData( "Verify No of Records for Option '"+sPageOption+" PER PAGE'", sPageOption+" or less  records should be shown", sPageOption+" or less  records not shown", "Fail");
				}
					
			} catch (Exception e) {
				System.out.println("fnVerifyNumofRecords --------------Failed");  
				 TestDriver.log.error("fnVerifyNumofRecords failed",e);
				throw(e);
			}
		}
	 
	 public void fnCheckEnableByXPath(String sLocator, String sControlName) throws Exception 
		{
			try {
				WebElement eleTable = driver.findElement(By.xpath(sLocator));
				
				if(eleTable.isEnabled())
				{
					obj.repAddData( "Verify "+sControlName, sControlName+" should be enabled", sControlName+" is enabled", "Pass");
				}
				else
				{
					obj.repAddData( "Verify "+sControlName, sControlName+" should be enabled", sControlName+" is not enabled", "Fail");
				}
					
			} catch (Exception e) {
				System.out.println("fnCheckEnableByXPath --------------Failed");  
				 TestDriver.log.error("fnCheckEnableByXPath failed",e);
				throw(e);
			}
		}
	 
	 public void fnCheckDisbleByXPath(String sLocator, String sControlName) throws Exception 
		{
			try {
				WebElement eleTable = driver.findElement(By.xpath(sLocator));
				
				if(!eleTable.isEnabled())
				{
					obj.repAddData( "Verify "+sControlName, sControlName+" should be disabled", sControlName+" is disabled", "Pass");
				}
				else
				{
					obj.repAddData( "Verify "+sControlName, sControlName+" should be disabled", sControlName+" is not disabled", "Fail");
				}
					
			} catch (Exception e) {
				System.out.println("fnCheckDisbleByXPath --------------Failed");  
				 TestDriver.log.error("fnCheckDisbleByXPath failed",e);
				throw(e);
			}
		}
	 
	 public int fnGetTableRowsCount(String sLocator) throws Exception 
		{
		 	int iRows = 0;
			try {
				WebElement eleTable = driver.findElement(By.xpath(sLocator));
				List<WebElement> arrTableRows = eleTable.findElements(By.xpath("./tbody/tr"));  //Get the table data rows
				iRows = arrTableRows.size();
				System.out.println("Data Rows Size>>>>"+arrTableRows.size());
					
			} catch (Exception e) {
				System.out.println("fnGetTableRowsCount --------------Failed");  
				 TestDriver.log.error("fnGetTableRowsCount failed",e);
				throw(e);
			}
			return iRows;
		}
	 
		public void fnVerifyComboBoxValue(String SelectValue, String TextValue) throws Exception
		{
			try {
				
				boolean bFlag = false;
				WebElement select = driver.findElement(By.xpath(SelectValue));
				if(select.isDisplayed())
				{
					HighlightElementByXpath(SelectValue);
					List<WebElement> options = select.findElements(By.tagName("option"));
					for (WebElement option : options)
					{
						if(TextValue.equalsIgnoreCase(option.getText().trim()))
						{
							Thread.sleep(1000);
							option.click();
							bFlag = true;
							Thread.sleep(1000);
						    break;
						}
					}
					
					if(bFlag == true)
					{
						obj.repAddData( "Verify '"+TextValue+"' in the combo box", "'"+TextValue+"' should be displayed in the combo box", "'"+TextValue+"' displayed in the combo box", "Pass");
					}
					else
					{
						obj.repAddData(  "Verify '"+TextValue+"' in the combo box", "'"+TextValue+"' should be displayed in the combo box","'"+TextValue+"' not displayed in the combo box", "Fail");
					}
				}
				

			} catch (Exception e) {
				System.out.println("fnVerifyComboBoxValue--------------Failed");
				TestDriver.log.error("fnVerifyComboBoxValue--------------Failed", e);
				//throw(e);
			}	
		}
		
		
		 public void fnCheckComboBoxSorting(String sComboBoxLocator, String sFieldName, String sDefaultValue) throws Exception

		 {
			try
			{
				////////////////////Sorting Logic////////////////////////////
				List<String> arrInitialList = new ArrayList<String>();
				List<String> arrExpSortedList = new ArrayList<String>();
				List<String> arrActSortedList = new ArrayList<String>();
				
				arrInitialList.clear();
				arrExpSortedList.clear();
				arrActSortedList.clear();
				
				WebElement select = driver.findElement(By.xpath(sComboBoxLocator));
				
				if(select.isDisplayed())
				{
					HighlightElementByXpath(sComboBoxLocator);
					List<WebElement> options = select.findElements(By.tagName("option"));
					for (WebElement option : options)
					{
						
						System.out.println(option.getText().toString().trim());
						
						if(!option.getText().toString().trim().equalsIgnoreCase(sDefaultValue))
						{
							arrInitialList.add(option.getText().toString().toUpperCase());
							arrActSortedList.add(option.getText().toString().toUpperCase());
						}
					}
				
				}
				
				Collections.sort(arrInitialList);

				arrExpSortedList = arrInitialList;
				System.out.println("Initial Expected Sorting Done");
			

				if(arrActSortedList.equals(arrExpSortedList))
				{
					obj.repAddData( "Verify Sorting on '"+sFieldName+"' combo box", "'"+sFieldName+"' values should be sorted in ascending order", "'"+sFieldName+"' values are sorted in ascending order", "Pass");
				}
				else
				{
					obj.repAddData( "Verify Sorting on '"+sFieldName+"' combo box", "'"+sFieldName+"' values should be sorted in ascending order", "'"+sFieldName+"' values are not sorted in ascending order", "Fail");
				}
				
				////////////////////Sorting Logic End////////////////////////////
			} catch (Exception e) {
		    System.out.println("fnCheckComboBoxSorting --------------Failed");    
		    TestDriver.log.error("fnCheckComboBoxSorting failed",e);
		    }
		 }
		 
		 
		 public void fnCheckDropDownSorting(String sDropDownLocator, String sFieldName, String sDefaultValue) throws Exception

		 {
			try
			{
				////////////////////Sorting Logic////////////////////////////
				List<String> arrInitialList = new ArrayList<String>();
				List<String> arrExpSortedList = new ArrayList<String>();
				List<String> arrActSortedList = new ArrayList<String>();
				
				arrInitialList.clear();
				arrExpSortedList.clear();
				arrActSortedList.clear();
				
				//WebElement select = driver.findElement(By.xpath(sDropDownLocator));
				
				
				
				WebElement eleCorpList = driver.findElement(By.xpath(sDropDownLocator));

				List<WebElement> arrCorpList = eleCorpList.findElements(By.xpath("./li"));  //Get the table data rows
				System.out.println("Data Rows Size>>>>"+arrCorpList.size());

				for(WebElement eleCorp : arrCorpList)
				{
					String sCorpName = eleCorp.findElement(By.xpath("./div")).getText();
					if(!sCorpName.equalsIgnoreCase(sDefaultValue))
					{
						System.out.println(sCorpName.trim().toUpperCase());
						arrInitialList.add(sCorpName.trim().toUpperCase());
						arrActSortedList.add(sCorpName.trim().toUpperCase());
					}

				}
				
				Collections.sort(arrInitialList);
				
				
				
				
				

				arrExpSortedList = arrInitialList;
				
				System.out.println(arrExpSortedList);
				System.out.println(arrActSortedList);
				
				
				System.out.println("Initial Expected Sorting Done");
			

				if(arrActSortedList.equals(arrExpSortedList))
				{
					obj.repAddData( "Verify Sorting on '"+sFieldName+"' combo box", "'"+sFieldName+"' values should be sorted in ascending order", "'"+sFieldName+"' values are sorted in ascending order", "Pass");
				}
				else
				{
					obj.repAddData( "Verify Sorting on '"+sFieldName+"' combo box", "'"+sFieldName+"' values should be sorted in ascending order", "'"+sFieldName+"' values are not sorted in ascending order", "Fail");
				}
				
				////////////////////Sorting Logic End////////////////////////////
			} catch (Exception e) {
		    System.out.println("fnCheckComboBoxSorting --------------Failed");    
		    TestDriver.log.error("fnCheckComboBoxSorting failed",e);
		    }
		 }
		 
		 //bxk8854
		 /*
		  * Method to verify if the check box is selected
		  */
		 public void fnCheckSelectedCheckBoxByXPath(String sLocator, String checkBoxName) throws Exception 
		 {
			 try {
				 WebElement checkBox = driver.findElement(By.xpath(sLocator));
				 boolean isChecked = false;				 
				 isChecked = checkBox.findElement(By.xpath(sLocator)).isSelected();
				 HighlightElementByXpath(sLocator);
				 if(isChecked == true)
				 {
					 obj.repAddData( "Verify "+checkBoxName+" should be selected", checkBoxName+" is selected", checkBoxName+" is selected", "Pass");
				 }
				 else
				 {
					 obj.repAddData( "Verify "+checkBoxName+" should be selected", checkBoxName+" is not selected", checkBoxName+" is not selected", "Fail");
				 }

			 } catch (Exception e) {
				 System.out.println("fnCheckEnableByXPath --------------Failed");  
				 TestDriver.log.error("fnCheckEnableByXPath failed",e);
				 throw(e);
			 }
		 }
		 
		 public void fnCheckdisplayCheckBoxByXPath(String sLocator, String checkBoxName) throws Exception 
		 {
			 try {
				 WebElement checkBox = driver.findElement(By.xpath(sLocator));
				 boolean isChecked = false;				 
				 isChecked = checkBox.findElement(By.xpath(sLocator)).isDisplayed();
				 HighlightElementByXpath(sLocator);
				 if(isChecked == true)
				 {
					 obj.repAddData( "Verify "+checkBoxName+" check box should be displayed", checkBoxName+"  check box is displayed", checkBoxName+" check box is displayed", "Pass");
				 }
				 else
				 {
					 obj.repAddData( "Verify "+checkBoxName+" check box should be displayed", checkBoxName+" check box is not displayed", checkBoxName+" check box is not displayed", "Fail");
				 }

			  } catch (Exception e) {
				 System.out.println("fnCheckdisplayCheckBoxByXPath --------------Failed");  
				 TestDriver.log.error("fnCheckdisplayCheckBoxByXPath failed",e);
				 throw(e);
			  }
		 }
		 
		 public void fnSelectCorpOffice_old(String sCorp, String sOffice) throws Exception
      
		 {
			try
			{
				fnSelectFromComboBoxXpath(SphereModules.Common_AllModules_ComboCorporateName_xp,sCorp);

			    if(ElementFound(SphereModules.Common_AllModules_ComboOfficeCode_xp)) 
			    {
				    WebDriverWait waitForOfficeCode = new WebDriverWait(driver, 5);
				    waitForOfficeCode.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SphereModules.Common_AllModules_ComboOfficeCode_xp)));

				    fnSelectFromComboBoxXpath(SphereModules.Common_AllModules_ComboOfficeCode_xp, sOffice);
			    }
			} catch (Exception e) {
		    System.out.println("fnSelectCorpOffice --------------Failed");    
		    TestDriver.log.error("fnSelectCorpOffice failed",e);
		    }
		 }
		 
		 public void fnSelectCorpOffice(String sCorp, String sOffice) throws Exception

		 {
			try
			{
			
				String sDeafaultValue = driver.findElement(By.xpath("//*[@id='corporationSelect']/div")).getText();
				System.out.println("ddfdsf>>>>>>>>>"+sDeafaultValue+"<<<<<<<<<<<<ghfdghgf");
				if(sDeafaultValue.trim().equalsIgnoreCase("Select") || sCorp.equalsIgnoreCase("Select"))
				{
					ClickById("corporationSelect", "Corporation Drop-down", false);
					//WebElement eleCorpList = driver.findElement(By.xpath("//ul[@class='dropdown-menu contextSelect corporationSelect ng-isolate-scope' and @aria-labelledby='dropdownMenu1']"));
					WebElement eleCorpList = driver.findElement(By.xpath(SphereModules.Common_AllModules_ComboCorporateName_xp));

					List<WebElement> arrCorpList = eleCorpList.findElements(By.xpath("./li"));  //Get the table data rows
					System.out.println("Data Rows Size>>>>"+arrCorpList.size());

					for(WebElement eleCorp : arrCorpList)
					{
						String sCorpName = eleCorp.findElement(By.xpath("./div")).getText();
						System.out.println(sCorpName);
						if(sCorpName.equalsIgnoreCase(sCorp))
						{
							eleCorp.click();
							break;
						}

					}
					//make sure it should select office code
					///fnVerifyDialogBox("OfficeCode", 1);  //Remove after testing
					Thread.sleep(2000);

					if(ElementFound(SphereModules.Common_AllModules_ComboOfficeSelect_xp)) 
					{	
						ClickById("officeSelect", "Office Drop-down", false);
						WebElement eleOfficeList = driver.findElement(By.xpath(SphereModules.Common_AllModules_ComboOfficeCode_xp));
						List<WebElement> arrOfficeList = eleOfficeList.findElements(By.xpath("./li"));  //Get the table data rows
						System.out.println("Data Rows Size>>>>"+arrOfficeList.size());

						for(WebElement eleOffice : arrOfficeList)
						{
							String sOfficeName = eleOffice.findElement(By.xpath("./div")).getText();
							System.out.println(sOfficeName);
							if(sOfficeName.equalsIgnoreCase(sOffice))
							{
								eleOffice.click();
								break;
							}

						}
					}

				}
				else
				{
					System.out.println("Corporation is already selected");
				}

			 
			} catch (Exception e) {
		    System.out.println("fnSelectCorpOffice --------------Failed");    
		    TestDriver.log.error("fnSelectCorpOffice failed",e);
		    }
		 }
		 
		 public void fnCheckAlert() {
			    try {
			        WebDriverWait wait = new WebDriverWait(driver, 5);
			        wait.until(ExpectedConditions.alertIsPresent());
			        Alert alert = driver.switchTo().alert();
			        String alertMessage = alert.getText();
		             System.out.println("Alert Message " + alertMessage);
			        alert.accept();
			    } catch (Exception e) {
			    	 System.out.println("fnCheckAlert Warning  - Alert not popped-up");    
					 TestDriver.log.warn("fnCheckAlert Warning  - Alert not popped-up");
			    }
			}
		 
		 public static String fnDateFormatter(String sDate, String sFormat) {
			 String sFormattedDate = "";
			    try {
				    	//String someDate = "4/7/16 1:24 PM";
						//SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy hh:mm a");
			    		//TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
				    	SimpleDateFormat format = new SimpleDateFormat(sFormat);
						Date date = format.parse(sDate);
				    	
						sFormattedDate = format.format(date);
				        System.out.println("Formatted Date--->" + sFormattedDate);

				        
			    } catch (Exception e) {
			    	 System.out.println("fnDateFormatter --------------Failed");    
					    TestDriver.log.error("fnDateFormatter failed",e);
			    }
			    
			    return sFormattedDate;
			}
		 
		 public void fnVerifyLabelMsgText(String sExpectedValue, String sActualValue) throws Exception
		 {
			 
			try {
				
				if(sExpectedValue.equalsIgnoreCase(sActualValue))
				{
					obj.repAddData( "Verify Text", "'"+sExpectedValue +"' should be displayed on the screen", "'"+sExpectedValue + "' successfully displayed on the screen", "Pass");
					
				}
				else
				{
					obj.repAddData( "Verify Text", "'"+sExpectedValue +"' should be displayed on the screen", "'"+sExpectedValue + "' not displayed on the screen", "Fail");
				}
				
			} catch (Exception e) {
				System.out.println("fnVerifyLabelMsgText --------------Failed");    
			    TestDriver.log.error("fnVerifyLabelMsgText failed",e);
			}

		 }
		 
		 public void fnVerifyLabelMsgTextByXpath(String sExpectedValue, String sActXpath) throws Exception
		 {
			 
			try {
				
				String sActualValue = driver.findElement(By.xpath(sActXpath)).getText().trim();
				HighlightElementByXpath(sActXpath);
				if(sExpectedValue.equalsIgnoreCase(sActualValue) || sActualValue.contains(sExpectedValue.toUpperCase()))
				{
					obj.repAddData( "Verify Text", "'"+sExpectedValue +"' should be displayed on the screen", "'"+sExpectedValue + "' successfully displayed on the screen", "Pass");
					
				}
				else
				{
					obj.repAddData( "Verify Text", "'"+sExpectedValue +"' should be displayed on the screen", "'"+sExpectedValue + "' not displayed on the screen", "Fail");
				}
				
			} catch (Exception e) {
				obj.repAddData( "Verify Text", "'"+sExpectedValue +"' should be displayed on the screen", "'"+sExpectedValue + "' not displayed on the screen", "Fail");
				System.out.println("fnVerifyLabelMsgText --------------Failed");    
			    TestDriver.log.error("fnVerifyLabelMsgText failed",e);
			}

		 }
		 
		 public void fnSignOutSignIn() throws Exception  //For Roles and permission test cases
		 {
			 
			try {
				
				if(driver !=null || TestDriver.bLoginFlag==true)
				{
					TestDriver.bLoginFlag=false;
					driver.manage().deleteAllCookies();
					driver.quit();	
					driver=null;
				}
					
				if(TestDriver.bLoginFlag==false || driver ==null)
				{   
					fnSetBrowserCapabilities();
					fnExecuteFunction("com.nbcu.sphere.Administration", "TC18"); //Login function for Sphere program
					//fnExecuteLoginFunction();

				}
				
			} catch (Exception e) {
				System.out.println("fnSignOutSignIn --------------Failed");    
			    TestDriver.log.error("fnSignOutSignIn failed",e);
			}

		 }
		 
		 public String fnComboBoxDataList(String sLocator, String sReportText, boolean bReportFlag, boolean bScenarioFlag) throws Exception 
			{
			 	String sDropDownList = "";
			 	@SuppressWarnings("unused")
				boolean bElementFound = false;
			 	List<WebElement> lsComboOptions = new ArrayList<WebElement>();
				try {
					WebElement ele;
					ele = driver.findElement(By.id(sLocator));
					String sText = "";
									
					if(bReportFlag==true)
					{	
						if(bScenarioFlag==true)
						{
							if(ele.isDisplayed())
							{
								HighlightElementById(sLocator);
								bElementFound = true;
								lsComboOptions = ele.findElements(By.xpath("./option"));
								if(lsComboOptions.size()>=1)
								{
									for(int i=0;i<lsComboOptions.size();i++)
									{
										sText = lsComboOptions.get(i).getText().toString().trim();
										if(!sText.equalsIgnoreCase(""))
										{
											sDropDownList = sDropDownList+";"+sText;
										}
									}
									sDropDownList = sDropDownList.substring(1);
									//sText = ele.getText().trim();
									
									obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" shown successfully with value '"+sDropDownList+"'", "Pass");
								}
								else
								{
									obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" shown but with value '"+sDropDownList+"'", "Fail");
								}
							}
							else
							{
								obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" not shown on the screen", "Fail");
							}
						}
						else if(bScenarioFlag==false)
						{
							if(!ele.isDisplayed())
							{   bElementFound = true;
								obj.repAddData( "Verify "+sReportText, sReportText+" should not be shown", sReportText+" not shown on the screen", "Pass");
								
							}
							else
							{
								obj.repAddData( "Verify "+sReportText, sReportText+" should not be shown", sReportText+" shown on the screen", "Fail");
							}
							
						}
					}
					else
					{
						if(ele.isDisplayed())
						{
							bElementFound = true;
						}
						else
						{
							bElementFound = false;
						}
					}
						
				} catch (Exception e) {
					System.out.println("fnComboBoxDataList --------------Failed");  
					TestDriver.log.error("fnComboBoxDataList failed",e);
					throw(e);
				}
				return sDropDownList;
			}
		 
		 public String fnComboBoxDataListByXpath(String sLocator, String sReportText, boolean bReportFlag, boolean bScenarioFlag) throws Exception 
			{
			 	String sDropDownList = "";
			 	@SuppressWarnings("unused")
				boolean bElementFound = false;
			 	List<WebElement> lsComboOptions = new ArrayList<WebElement>();
				try {
					WebElement ele;
					ele = driver.findElement(By.xpath(sLocator));
					String sText = "";
									
					if(bReportFlag==true)
					{	
						if(bScenarioFlag==true)
						{
							if(ele.isDisplayed())
							{
								HighlightElementByXpath(sLocator);
								bElementFound = true;
								lsComboOptions = ele.findElements(By.xpath("./option"));
								if(lsComboOptions.size()>=1)
								{
									for(int i=0;i<lsComboOptions.size();i++)
									{
										sText = lsComboOptions.get(i).getText().toString().trim();
										if(!sText.equalsIgnoreCase(""))
										{
											sDropDownList = sDropDownList+";"+sText;
										}
									}
									sDropDownList = sDropDownList.substring(1);
									//sText = ele.getText().trim();
									
									obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" shown successfully with value '"+sDropDownList+"'", "Pass");
								}
								else
								{
									obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" shown but with value '"+sDropDownList+"'", "Fail");
								}
							}
							else
							{
								obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" not shown on the screen", "Fail");
							}
						}
						else if(bScenarioFlag==false)
						{
							if(!ele.isDisplayed())
							{   bElementFound = true;
								obj.repAddData( "Verify "+sReportText, sReportText+" should not be shown", sReportText+" not shown on the screen", "Pass");
								
							}
							else
							{
								obj.repAddData( "Verify "+sReportText, sReportText+" should not be shown", sReportText+" shown on the screen", "Fail");
							}
							
						}
					}
					else
					{
						if(ele.isDisplayed())
						{
							bElementFound = true;
						}
						else
						{
							bElementFound = false;
						}
					}
						
				} catch (Exception e) {
					System.out.println("fnComboBoxDataList --------------Failed");  
					TestDriver.log.error("fnComboBoxDataList failed",e);
					throw(e);
				}
				return sDropDownList;
			}
		 
		 
		 /*public void fnCompareMenuOptions(String sExpMenuList) throws Exception {
			 
			 String sMenuOrder = "";
		    	String sMenuType = "";
			    try {
			    	
			    	List<String> arrExpMenuList = new ArrayList<String>();
			    	List<String> arrActMenuList = new ArrayList<String>();
			    	String[] arrMenu = sExpMenuList.split(":");
			    	
			    	List<WebElement> eleNavList = driver.findElements(By.xpath(objSphereCommon.Main_HomeNavigationLinks_xp));
					Integer iNavListSize = eleNavList.size();
					
								    	
			    	if(sExpMenuList.contains("MainMenu"))
			    	{
			    		sMenuType = "Main Menu";  //For reporting purpose
			    		for (String sExpMenuOption: arrMenu[1].toString().split(","))
			    		{
			    			sMenuOrder = sMenuOrder + ", " + sExpMenuOption;
			    			System.out.println(sExpMenuOption);
			    			arrExpMenuList.add(sExpMenuOption);
			    		}
			    		sMenuOrder = sMenuOrder.substring(2);
			    		
			    		for(int i=0;i<iNavListSize; i++)
						{
			    			HighlightElement(eleNavList.get(i).findElement(By.xpath("./div")));
			    			String sMainMenuOption = eleNavList.get(i).findElement(By.xpath("./div")).getText();
							System.out.println(sMainMenuOption);
							arrActMenuList.add(i, sMainMenuOption);
						}
			    		
			    	}
			    	else if(sExpMenuList.contains("SubMenu"))
			    	{
			    		sMenuType = "Sub Menu";    //For reporting purpose
			    		for (String sExpMenuOption: arrMenu[2].toString().split(","))
			    		{
			    			sMenuOrder = sMenuOrder + ", " + sExpMenuOption;
			    			System.out.println(sExpMenuOption);
			    			arrExpMenuList.add(sExpMenuOption);
			    		}
			    		sMenuOrder = arrMenu[1].toString() + "->" + sMenuOrder.substring(2);
			    		
			    		for(int i=0;i<iNavListSize; i++)
						{
			    			String sMainMenuOption = eleNavList.get(i).findElement(By.xpath("./div")).getText();
							System.out.println(sMainMenuOption);
							if(sMainMenuOption.equalsIgnoreCase(arrMenu[1].toString()))
							{	
								ClickByElement(eleNavList.get(i).findElement(By.xpath("./div")), arrMenu[1].toString(), true);
								//eleNavList.get(i).click();
								List<WebElement> eleSubNavList = eleNavList.get(i).findElements(By.xpath("./ul/li"));
								Integer iSubNavListSize = eleSubNavList.size();
								for(int j=0;j<iSubNavListSize; j++)
								{
									HighlightElement(eleSubNavList.get(j).findElement(By.xpath("./div/span[2]")));
									String sSubMenuOption = eleSubNavList.get(j).findElement(By.xpath("./div/span[2]")).getText();
									System.out.println(sSubMenuOption);
									arrActMenuList.add(j, sSubMenuOption);
								}
								break;
							}
							
						}
			    		
			    	}
			    	
			    	if(arrActMenuList.equals(arrExpMenuList))
					{
						obj.repAddData( "Verify "+sMenuType+" Options on Home Page", "All "+sMenuType+" options should be available in correct order", "All "+sMenuType+" options are verified with correct values and order : "+sMenuOrder, "Pass");
					}
					else
					{
						obj.repAddData( "Verify "+sMenuType+" Options  on Home Page", "All "+sMenuType+" options should be available in correct order", sMenuType+" options check failed with incorrect values or order : "+sMenuOrder, "Fail");
					}
			    	
			    } catch (Exception e) {
			    	obj.repAddData( "Verify "+sMenuType+" Options  on Home Page", "All "+sMenuType+" options should be available in correct order", sMenuType+" options check failed with incorrect values or order : "+sMenuOrder, "Fail");
			    	 System.out.println("fnCompareMenuOptions --------------Failed");    
					    TestDriver.log.error("fnCompareMenuOptions failed",e);
			    }
			}*/
		 /* public Actions fnCreateCustomActionsObj(WebDriver driver)
		 {
			 Actions builder = new Actions(driver);
			 return builder;
		 } */
		 
		 public void fnVerifyTextAndReport(String sExpVal, String sActValue, String sFieldName) throws Exception  //For Roles and permission test cases
		 {
			 
			try {
				
				if(sExpVal.equalsIgnoreCase(sActValue))
				{
					obj.repAddData( "Verify data for "+sFieldName, "'"+sExpVal +"' should be displayed on the screen for "+sFieldName, "'"+sExpVal + "' successfully displayed on the screen", "Pass");
					
				}
				else
				{
					obj.repAddData( "Verify data	 for "+sFieldName, "'"+sExpVal +"' should be displayed on the screen for "+sFieldName, "'"+sExpVal + "' not displayed on the screen", "Fail");
				}
				
			} catch (Exception e) {
				System.out.println("fnSignOutSignIn --------------Failed");    
			    TestDriver.log.error("fnSignOutSignIn failed",e);
			}

		 }
		 
		 
		 public String fnGetDestinationLookupCode(HashMap<Integer, HashMap<Integer, String>> mTableDataDB, String sSourceCode) throws Exception  //For Roles and permission test cases
		 {
			 String sDestination = "";
			 
			try {
				
				for(int j=1; j<=mTableDataDB.size();j++)
				{
					String sSourceDB = mTableDataDB.get(j).get(1).toString().trim();
					
					if(sSourceCode.equalsIgnoreCase(sSourceDB))
					{
						sDestination = mTableDataDB.get(j).get(2).toString().trim();
						break;
					}
					
				}
				
			} catch (Exception e) {
				System.out.println("fnGetDestinationLookupCode --------------Failed");    
			    TestDriver.log.error("fnGetDestinationLookupCode failed",e);
			}
			return sDestination;

		 }

		 
		
		 public boolean fnCloseOpenedForm() throws Exception  //For Roles and permission test cases
         {
             
             boolean bOpenFormAvailable = false;
             WebDriverWait waitForTableLoad = new WebDriverWait(driver, 20);
            try {
                
               /* List<WebElement> eleList = driver.findElements(By.xpath(SphereModules.Customers_AddNationalAccount_BtnCancel_xp));
                if(eleList.size()>=1)
                {
                	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", eleList);
                	eleList.get(0).click();
                    Thread.sleep(3000);
                    ClickByXpath(SphereModules.Common_AddEditModules_BtnFormExitConfirmation_xp, "Confirmation Button", false);
                    bOpenFormAvailable = true;
                    System.out.println("Add/Edit Parent Company form available : "+bOpenFormAvailable);
                }*/
                
                List<WebElement> eleListUser = driver.findElements(By.xpath(SphereModules.Users_AddUser_BtnClose_xp));
                if(eleListUser.size()>=1)
                {
                    eleListUser.get(0).click();
                    Thread.sleep(2000);
                    ClickByXpath(SphereModules.Common_AddEditModules_BtnFormExitConfirmation_xp, "Confirmation Button", false);
                    bOpenFormAvailable = true;
                    System.out.println("Add/Edit User form available : "+bOpenFormAvailable);
                }
                
                List<WebElement> eleListNationalAccount = driver.findElements(By.xpath(SphereModules.Customers_AddNationalAccount_BtnClose_xp));
                if(eleListNationalAccount.size()>=1)
                {
                    eleListNationalAccount.get(0).click();
                    Thread.sleep(2000);
                    ClickByXpath(SphereModules.Common_AddEditModules_BtnFormExitConfirmation_xp, "Confirmation Button", false);
                    bOpenFormAvailable = true;
                    System.out.println("Add/Edit Parent Company form available : "+bOpenFormAvailable);
                }
                
                List<WebElement> eleListCustomer = driver.findElements(By.xpath(SphereModules.CustomersMaster_AddCustomer_BtnClose_xp));
                if(eleListCustomer.size()>=1)
                {
                    eleListCustomer.get(0).click();
                    Thread.sleep(2000);
                    ClickByXpath(SphereModules.Common_AddEditModules_BtnFormExitConfirmation_xp, "Confirmation Button", false);
                    bOpenFormAvailable = true;
                    System.out.println("Add/Edit Customer form available : "+bOpenFormAvailable);
                }
                
                List<WebElement> eleListDeal = driver.findElements(By.xpath(SphereModules.Deals_AddDeal_BtnClose_xp));
                if(eleListDeal.size()>=1)
                {
                    eleListDeal.get(0).click();
                    Thread.sleep(2000);
                    ClickByXpath(SphereModules.Common_AddEditModules_BtnFormExitConfirmation_xp, "Confirmation Button", false);
                    bOpenFormAvailable = true;
                    System.out.println("Add/Edit Deal form available : "+bOpenFormAvailable);
                }
                List<WebElement> eleListBilling = driver.findElements(By.xpath(SphereModules.BillingRequest_Cancel_xp));
                if(eleListBilling.size()>=1)
                {
                	eleListBilling.get(0).click();
                    Thread.sleep(2000);
                    ClickByXpath(SphereModules.Common_AddEditModules_BtnFormExitConfirmation_xp, "Confirmation Button", false);
                    bOpenFormAvailable = true;
                    System.out.println("Add/Edit Deal form available : "+bOpenFormAvailable);
                }
                
                
                
            } catch (Exception e) {
                System.out.println("fnCloseOpenedForm --------------Failed");    
                TestDriver.log.error("fnCloseOpenedForm failed",e);
            }
            finally
            {
                try {
                    if(ElementFound(SphereModules.Common_AddEditModules_BtnFormExitConfirmation_xp))
                    {
                        waitForTableLoad.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SphereModules.Common_AddEditModules_BtnFormExitConfirmation_xp)));
                        ClickByXpath(SphereModules.Common_AddEditModules_BtnFormExitConfirmation_xp, "Confirmation Button", false);
                    }
                } catch (Exception e) {

                }
            }
            return bOpenFormAvailable;

         }
		 public boolean fnCloseOpenedFormStage() throws Exception  //For Roles and permission test cases
		 {
			 
			 boolean bOpenFormAvailable = false;
			try {
				
				List<WebElement> eleList = driver.findElements(By.xpath(SphereModules.Customers_AddNationalAccount_BtnCancel_xp));
				if(eleList.size()>=1)
				{
					obj.repAddData( "Check any opened form", "Add/Edit form should not be opened","Add/Edit form opened", "Fail");
					eleList.get(0).click();
					bOpenFormAvailable = true;
					System.out.println("Add Parent Company form available : "+bOpenFormAvailable);
				}
				
				List<WebElement> eleListUser = driver.findElements(By.xpath(SphereModules.Users_AddUser_BtnClose_xp));
				if(eleListUser.size()>=1)
				{
					obj.repAddData( "Check any opened form", "Add/Edit form should not be opened","Add/Edit form opened", "Fail");
					eleListUser.get(0).click();
					bOpenFormAvailable = true;
					System.out.println("Add User form available : "+bOpenFormAvailable);
				}
				
				List<WebElement> eleListNationalAccount = driver.findElements(By.xpath(SphereModules.Customers_AddNationalAccount_BtnClose_xp));
				if(eleListNationalAccount.size()>=1)
				{
					obj.repAddData( "Check any opened form", "Add/Edit form should not be opened","Add/Edit form opened", "Fail");
					eleListNationalAccount.get(0).click();
					bOpenFormAvailable = true;
					System.out.println("Add Parent Company form available : "+bOpenFormAvailable);
				}
				
				List<WebElement> eleListCustomer = driver.findElements(By.xpath(SphereModules.CustomersMaster_AddCustomer_BtnClose_xp));
				if(eleListCustomer.size()>=1)
				{
					obj.repAddData( "Check any opened form", "Add/Edit form should not be opened","Add/Edit form opened", "Fail");
					eleListCustomer.get(0).click();
					bOpenFormAvailable = true;
					System.out.println("Add Customer form available : "+bOpenFormAvailable);
				}
				
				List<WebElement> eleListDeal = driver.findElements(By.xpath(SphereModules.Deals_AddDeal_BtnClose_xp));
				if(eleListDeal.size()>=1)
				{
					obj.repAddData( "Check any opened form", "Add/Edit form should not be opened","Add/Edit form opened", "Fail");
					eleListDeal.get(0).click();
					bOpenFormAvailable = true;
					System.out.println("Add Deal form available : "+bOpenFormAvailable);
				}
				

				
			} catch (Exception e) {
				System.out.println("fnCloseOpenedFormStage --------------Failed");    
			    TestDriver.log.error("fnCloseOpenedFormStage failed",e);
			}
			finally
			{
				/*if(bOpenFormAvailable == true)
				{
					obj.repAddData( "Check any opened form", "Add/Edit form should not be opened","Add/Edit form opened", "Fail");
				}*/
			}
			return bOpenFormAvailable;

		 }
		 
		 public void analyzeLog() {
		        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
		        for (LogEntry entry : logEntries) {
		            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
		            TestDriver.log.info(entry.getLevel() + " " + entry.getMessage());
		            //do something useful with the data
		        }
		    }

		
		 public HashMap<Integer, HashMap<Integer, String>> fnGetQueueTableData (String sTablePath) throws Exception
		 {
			 HashMap<Integer, HashMap<Integer, String>> mTableData = new HashMap<Integer,HashMap<Integer, String>>();
		 		 
			//String expectedColumn=null;
			try
			{
				WebElement eleTable = driver.findElement(By.xpath(sTablePath));
				List<WebElement> arrTableRows = eleTable.findElements(By.xpath("./tr"));  //Get the table data rows
				System.out.println("Data Rows Size>>>>"+arrTableRows.size());
				int i=0;//Header column
				int iColStartCount=0; 
				//List<WebElement> arrHeaderColumns = eleTable.findElements(By.xpath("../thead/tr/th"));			
												
				for(int iRow=0;iRow<arrTableRows.size();iRow++)  //Exclude first header row - not applicable now
				{	
					String sColValue=null;
					mTableData.put(iRow+1, new HashMap<Integer, String>());  //Map row starts with 1, so add 1 to iRow
					
					List<WebElement> arrTableColumns = arrTableRows.get(iRow).findElements(By.xpath("./td"));  //Get the table data rows
					
					for(int iCol=iColStartCount;iCol<arrTableColumns.size()-1;iCol++)
					{
														
						{
								sColValue = arrTableColumns.get(iCol).getText();//.toString();
								if(sColValue.contains("/") && sColValue.contains(":")) //Format Dates
								{
									sColValue = sColValue.replace("@ ", "");
									sColValue = sColValue.replace("PM", "PM");
									sColValue = sColValue.replace("AM", "AM");
									//sColValue = fnDateFormatter(sColValue,"MM/dd/yy hh:mm a")
									//sColValue = fnDateFormatter(sColValue,"MM/dd/yyyy hh:mm a");
									  sColValue = fnDateFormatter(sColValue,"MM/dd/yyyy h:mm:ss a");
								}
							
							}
						
					
						//mTableData.get(iRow).put(iCol+1,sColValue);
							if(sColValue.contains("null") || sColValue.contains("undefined"))
							{
								sColValue="";
							}
							mTableData.get(iRow+1).put(iCol+1,sColValue);
					}
					

				}
				
				
			}catch (Exception e) {
		    System.out.println("fnGetQueueTableData --------------Failed");    
		    TestDriver.log.error("fnGetQueueTableData failed",e);
		    }
			
			return mTableData;
		 }
		
		 public void fnCheckfieldDisbleByXPath(String sLocator, String sFieldName) throws Exception 
			{
				try {
					WebElement ele = driver.findElement(By.xpath(sLocator));
					
					if(!ele.isEnabled())
					{
						obj.repAddData( "Verify "+sFieldName, sFieldName+" should be disabled",sFieldName+" is disabled", "Pass");
					}
					else
					{
						obj.repAddData( "Verify "+sFieldName, sFieldName+" should be disabled", sFieldName+" is not disabled", "Fail");
					}
						
				} catch (Exception e) {
					System.out.println("fnCheckDisbleByXPath --------------Failed");  
					 TestDriver.log.error("fnCheckDisbleByXPath failed",e);
					throw(e);
				}
			}	 
		 
		 public void fnCheckfieldDisbleById(String sLocator, String sFieldName) throws Exception 
			{
				try {
					WebElement ele = driver.findElement(By.id(sLocator));
					
					if(!ele.isEnabled())
					{
						obj.repAddData( "Verify "+sFieldName, sFieldName+" should be disabled",sFieldName+" is disabled", "Pass");
					}
					else
					{
						obj.repAddData( "Verify "+sFieldName, sFieldName+" should be disabled", sFieldName+" is not disabled", "Fail");
					}
						
				} catch (Exception e) {
					System.out.println("fnCheckDisbleByXPath --------------Failed");  
					 TestDriver.log.error("fnCheckDisbleByXPath failed",e);
					throw(e);
				}
			}	  
		 
		 public void fnCheckfieldEnableById(String sLocator, String sFieldName) throws Exception 
			{
				try {
					WebElement ele = driver.findElement(By.id(sLocator));
					
					if(ele.isEnabled())
					{
						obj.repAddData( "Verify "+sFieldName, sFieldName+" should be enabled",sFieldName+" is enabled", "Pass");
					}
					else
					{
						obj.repAddData( "Verify "+sFieldName, sFieldName+" should be enabled", sFieldName+" is not enabled", "Fail");
					}
						
				} catch (Exception e) {
					System.out.println("fnCheckfieldEnableById --------------Failed");  
					 TestDriver.log.error("fnCheckfieldEnableById failed",e);
					throw(e);
				}
			}	  

		 public void fnCheckSortbycolumn(HashMap<Integer, HashMap<Integer, String>> mTableData, String sColumnName, int iColNum, String sSortType) throws Exception

		 {
			try
			{  //sSortType can be DEFAULT, ASC , DESC
				////////////////////Sorting Logic////////////////////////////
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

				List<Date> arrInitialList = new ArrayList<Date>();
				List<Date> arrExpSortedList = new ArrayList<Date>();
				List<Date> arrActSortedList = new ArrayList<Date>();
				//List<Date> arrTempList = new ArrayList<Date>();
							
				arrInitialList.clear();
				arrExpSortedList.clear();
				arrActSortedList.clear();
				arrActSortedList.clear();
				
				for(int i=0; i<mTableData.size();i++)
				{
					String sExpLastActivity = mTableData.get(i+1).get(iColNum).toString().trim();
					Date dtExpLastActivity = formatter.parse(sExpLastActivity);
					System.out.println(dtExpLastActivity);
					arrInitialList.add(dtExpLastActivity);
				}
				
				arrActSortedList = arrInitialList;
				
				Collections.sort(arrInitialList);
				if(sSortType.equalsIgnoreCase("DEFAULT") || sSortType.equalsIgnoreCase("ASC"))
				{
					if(sColumnName.equalsIgnoreCase("Last Activity"))  // On clicking last activity sort, it first get in descending order 
					{
						Collections.reverse(arrInitialList); 
					}
					
					arrExpSortedList = arrInitialList;  //In sorted descending order
					
					if(arrExpSortedList.equals(arrActSortedList))
					{
						obj.repAddData( "Verify Default Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in descending order", "'"+sColumnName+"' values are sorted in descending order", "Pass");
					}
					else
					{
						obj.repAddData( "Verify Default Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in descending order", "'"+sColumnName+"' values are not sorted in descending order", "Fail");
					}
				}
				else if(sSortType.equalsIgnoreCase("ASC"))
				{
					arrExpSortedList = arrInitialList;
					
					if(arrExpSortedList.equals(arrActSortedList))
					{
						obj.repAddData( "Verify Sorting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in ascending order", "'"+sColumnName+"' values are sorted in ascending order", "Pass");
					}
					else
					{
						obj.repAddData( "Verify Sor	ting on '"+sColumnName+"' column", "'"+sColumnName+"' values should be sorted in ascending order", "'"+sColumnName+"' values are not sorted in ascending order", "Fail");
					}
				}
				////////////////////Sorting Logic End////////////////////////////
			} catch (Exception e) {
		    System.out.println("fnCheckDateSorting --------------Failed");    
		    TestDriver.log.error("fnCheckDateSorting failed",e);
		    }
		 }
		 
		  public void fnVerifyClickSuccessMessageOld() throws Exception
           {
               try {
            	   String sExpAlertMsg = AppMessages.Deals_Export_Alert_Message_msg;
                   List<WebElement> sElementList = driver.findElements(By.xpath(SphereModules.Common_AddEditModules_MsgSuccess_xp));
                   
                   if(sElementList.size()>0)
                   {
                       for (WebElement ele : sElementList) 
                       {
                           
                           String sSuccessMsg = ele.getText().toString();
                          
                           if(sSuccessMsg.contains("2000 records"))
                           {
           				   
                        	   fnVerifyLabelMsgText(sExpAlertMsg, sSuccessMsg.trim());
           				      
           				      
                           }
                           Thread.sleep(2000);
                           if(!sSuccessMsg.contains("were created"))
                           {
                        	   if(sSuccessMsg.contains("unexpected error")||(sSuccessMsg.contains("Orbit experienced a system error")))
                        	   {
                        		   ele.click();
                        		   obj.repAddData( "click failure message", "message should be clicked","'"+sSuccessMsg+"' clicked successfully", "fail");  
                        	   }
                        	   else
                        	   {
                        	       ele.click();
                        	       obj.repAddData( "click  message", "message should be clicked","'"+sSuccessMsg+"' clicked successfully", "Pass");
                        	   }
                        	  
                           }
                           Thread.sleep(1000);
                          
                   }
                   
                   }

               } catch (Exception e) {
                   System.out.println("fnVerifyClickSuccessMessage--------------Failed");
                   //throw(e);
               }    
           }	
		   
		   public void fnVerifyAlertMessage() throws Exception
           {
               try {
                   
                   List<WebElement> sElementList = driver.findElements(By.xpath(SphereModules.Deals_Export_Alert_Message_xp));
                   if(sElementList.size()>0)
                   {
                       for (WebElement ele : sElementList) 
                       {
                           
                           String sAlertMsg = ele.getText().toString();
                           String sExpAlertMsg = AppMessages.Deals_Export_Alert_Message_msg;
           				   fnVerifyLabelMsgText(sExpAlertMsg, sAlertMsg.trim());
                           Thread.sleep(2000);
                           ele.click();
                           Thread.sleep(1000);
                           obj.repAddData( "Click Alert message", "Alert message should be clicked","'"+sAlertMsg+"' clicked successfully", "Pass");
                       }
                   }


               } catch (Exception e) {
                   System.out.println("fnVerifyAlertMessage--------------Failed");
                   //throw(e);
               }    
           }	 
		   public void fnVerifyDropdownValues(String sLocator, String sGetColName, String sLegalEntity, String sFieldName ) throws Exception
           {
			   boolean sbFlag = false;
			   try{
				   String sColValue = TestDriver.mTestPhaseData.get(TestDriver.iTC_ID).get(sGetColName).trim();
					String [] arrExpList = sColValue.split(";");
					    WebElement dropdown =driver.findElement(By.id(sLocator));
			            Select select = new Select(dropdown);
			            List<WebElement> options = select.getOptions();
			            for(WebElement we:options)
			            {
			             for (int i=0; i<arrExpList.length; i++){
			            	 if (we.getText().equals(arrExpList[i])){
			            		 sbFlag=true;
			                	 System.out.println("Matched");
			                	 break;
			                 }
			               }
			             } 
                  if(sbFlag==true){
            	  
            	  obj.repAddData( "Verify '"+sLegalEntity+"' legal entity '"+sFieldName+"' values in the combo box", "'"+sLegalEntity+"' legal entity '"+sFieldName+"' values should be displayed in the combo box", "'"+sLegalEntity+"' legal entity '"+sFieldName+"' values displayed in the combo box", "Pass");  
              
                 }else
                 {
            	 
            	  obj.repAddData( "Verify '"+sLegalEntity+"' legal entity '"+sFieldName+"' values in the combo box", "'"+sLegalEntity+"' legal entity '"+sFieldName+"' values should be displayed in the combo box", "'"+sLegalEntity+"' legal entity '"+sFieldName+"' values not displayed  in the combo box", "Fail");
              
                 }
                
			   }
                catch (Exception e) {
                    System.out.println("fnVerifyDropdownValues--------------Failed");
                    //throw(e);
                }    

           }
		   
		   

		   public void fnVerifyClickErrorMessage() throws Exception
           {
               try {
                   
                   List<WebElement> sElementList = driver.findElements(By.xpath(SphereModules.Common_AddEditModules_MsgError_xp));
                   if(sElementList.size()>0)
                   {
                       for (WebElement ele : sElementList) 
                       {
                           
                           String sErrorMsg = ele.getText().toString();
                           Thread.sleep(2000);
                           ele.click();
                           Thread.sleep(1000);
                           obj.repAddData( "Click Error message", "Error message should be clicked","'"+sErrorMsg+"' clicked successfully", "Pass");
                       }
                   }


               } catch (Exception e) {
                   System.out.println("fnVerifyClickErrorMessage--------------Failed");
                   //throw(e);
               }    
           }	 

		   public void fnverifyEnterInfo(String label, String value) throws Exception
			{	
				try
				{
					if(CheckXpath(driver,"//table")>1)
					{
						List<WebElement>eleList = driver.findElements(By.xpath("//table"));
						for(WebElement ele : eleList)
						{
							System.out.println(ele.getText());
							if (ele.getText().equalsIgnoreCase(label))
							{
								ele.findElement(By.tagName("input")).sendKeys(value);
								obj.repAddData( "Enter data",value+" should be entered", value+" is entered successfully", "Pass");
								break;
							}
						}
					}
					
				}
				catch(Exception e) {
					System.out.println("Failed to fetch "+label+ " And "+value);
					obj.repAddData( "Enter data",value+" should be entered", value+" is not entered successfully", "Fail");
					//throw(e);
				}
			}
		
		  
		   public void fnVerifyCombolist( String sGetColName, String sFieldName,String sColXpath ) throws Exception
           {
			   boolean sbFlag = false;
			   boolean isListEqual=false;
			   try{
				   String sExplist = TestDriver.mTestPhaseData.get(TestDriver.iTC_ID).get(sGetColName).trim();
					String [] arrExpList = sExplist.split(";");
					
					String actualList=fnComboBoxDataList(sColXpath,sFieldName,true,true);//get actual data from combo box)
					String [] sActlist=actualList.split(";");
					for (int i=0; i<arrExpList.length; i++){
						if (sActlist[i].equals(arrExpList[i])){
			            		 sbFlag=true;
			                	 System.out.println(i+ " value Matched");
			                	 
			            	 }else
			            		if(sbFlag==false)
							     {
			            		   System.out.println( i+ " value not Matched"); 
			            		   obj.repAddData( "Verify " +sFieldName+ " values in the combo box", arrExpList[i]+  " should be displayed in the combo box", arrExpList[i]+ " not displayed in the combo box", "Fail");
							     }
					
					}
			       if(sbFlag==true)
			       {
			    	   List<String> list1 = Arrays.asList(sActlist);
			    	   List<String> list2 = Arrays.asList(arrExpList);
			    	  if
			    	  (list1.equals(list2))
			    	  {
			    	   isListEqual=true;
			    	  }
			       }
                  if(isListEqual==true)
                  {
            	  
            	  obj.repAddData( "Verify " +sFieldName+ " values in the combo box", sFieldName+  " values should be displayed in the combo box", sFieldName+ " values displayed in the combo box", "Pass");  
              
                 }
                  else{
                	  obj.repAddData( "Verify " +sFieldName+ " values in the combo box", sFieldName+  " Expected list and Actual list should match ", sFieldName+ " Expected list and Actual list in not matched", "Fail");  
                  }
                
			   }
                catch (Exception e) {
                    System.out.println("fnVerifyCombolist--------------Failed");
                    //throw(e);
                }    
           } 
		   public void fnVerifyCombolistByXpath( String sGetColName, String sFieldName,String sColXpath ) throws Exception
           {
			   boolean sbFlag = false;
			   boolean isListEqual=false;
			   try{
				   String sExplist = TestDriver.mTestPhaseData.get(TestDriver.iTC_ID).get(sGetColName).trim();
					String [] arrExpList = sExplist.split(";");
					
					String actualList=fnComboBoxDataListByXpath(sColXpath,sFieldName,true,true);//get actual data from combo box)
					String [] sActlist=actualList.split(";");
					for (int i=0; i<arrExpList.length; i++){
			            	 if (sActlist[i].equals(arrExpList[i])){
			            		 sbFlag=true;
			                	 System.out.println("Matched");
			                 }
					     if(sbFlag==false)
					     {
					    	 System.out.println( i+ "not Matched"); 
		            		 obj.repAddData( "Verify " +sFieldName+ " values in the combo box", sFieldName+  " values should be displayed in the combo box", sFieldName+ " values not displayed in the combo box", "Fail");
					     
					     }
					}
			       
					if(sbFlag==true)
				       {
				    	   List<String> list1 = Arrays.asList(sActlist);
				    	   List<String> list2 = Arrays.asList(arrExpList);
				    	  if
				    	  (list1.equals(list2))
				    	  {
				    	   isListEqual=true;
				    	  }
				       }
	                  if(isListEqual==true)
	                  {
	            	  
	            	  obj.repAddData( "Verify " +sFieldName+ " values in the combo box", sFieldName+  " values should be displayed in the combo box", sFieldName+ " values displayed in the combo box", "Pass");  
	              
	                 }
	                  else{
	                	  obj.repAddData( "Verify " +sFieldName+ " values in the combo box", sFieldName+  " Expected list and Actual list should match ", sFieldName+ " Expected list and Actual list in not matched", "Fail");  
	                  }
	                
                
			   }
                catch (Exception e) {
                    System.out.println("fnVerifyDropdownValues--------------Failed");
                    //throw(e);
                }    

           }
		   
		   public HashMap<Integer, HashMap<Integer, String>> fnGetSapTableData (String sTablePath) throws Exception
			 {
				 HashMap<Integer, HashMap<Integer, String>> mTableData = new HashMap<Integer,HashMap<Integer, String>>();
			 		 
				//String expectedColumn=null;
				try
				{
					WebElement eleTable = driver.findElement(By.xpath(sTablePath));
					List<WebElement> arrTableRows = eleTable.findElements(By.xpath("./tr"));  //Get the table data rows
					System.out.println("Data Rows Size>>>>"+arrTableRows.size());
					int i=0;//Header column
					int iColStartCount =0; 
					//List<WebElement> arrHeaderColumns = eleTable.findElements(By.xpath("../thead/tr/th"));			
													
					for(int iRow=0;iRow<arrTableRows.size();iRow++)  //Exclude first header row - not applicable now
					{	
						//arrTableData.clear();
						String sColValue=null;
						//mTableData.put(iRow+1, new HashMap<Integer, String>());  //Map row starts with 1
						mTableData.put(iRow+1, new HashMap<Integer, String>());  //Map row starts with 1, so add 1 to iRow
						
						List<WebElement> arrTableColumns = arrTableRows.get(iRow).findElements(By.xpath("./td"));  //Get the table data rows
						
						for(int iCol=iColStartCount;iCol<arrTableColumns.size();iCol++)
						{
															
							{
									sColValue = arrTableColumns.get(iCol).getText();//.toString();
									
									if(sColValue.contains("/") && sColValue.contains(":")) //Format Dates
									{
										sColValue = sColValue.replace("@ ", "");
										sColValue = sColValue.replace("PM", "PM");
										sColValue = sColValue.replace("AM", "AM");
										//sColValue = fnDateFormatter(sColValue,"MM/dd/yy hh:mm a")
										//sColValue = fnDateFormatter(sColValue,"MM/dd/yyyy hh:mm a");
										  sColValue = fnDateFormatter(sColValue,"MM/dd/yyyy h:mm:ss a");
									}
								
								}
							
						
							//mTableData.get(iRow).put(iCol+1,sColValue);
								if(sColValue.contains("null") || sColValue.contains("undefined"))
								{
									sColValue="";
								}
								mTableData.get(iRow+1).put(iCol+1,sColValue);
						}
						

					}
					
					
				}catch (Exception e) {
			    System.out.println("fnGetSapTableData --------------Failed");    
			    TestDriver.log.error("fnGetSapTableData failed",e);
			    }
				
				return mTableData;
			 }
			 
		   
		   public void fnArchiveFile(File fFilePath, String sFileName) throws Exception {
	            
	            
		        try {
		            
		                 Date date = new Date();
		                  SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyhhmmss");
		                  String formattedDate = sdf.format(date);
		                  System.out.println(formattedDate);
		                  String[] arrFileName = sFileName.toString().split("\\.");
		                  try {    
		                          File theDir = new File(FileLocSetter.sDownloadPath+"Arch");
		                        if(fFilePath.exists())
		                        {
		                              if (!theDir.exists()) 
		                              {
		                                    System.out.println("creating directory: " + theDir);
		                                    theDir.mkdirs();
		                              }
		                              else
		                              {
		                                  System.out.println("Arch folder already created");
		                              }
		                              
		                              File fDest = new File(FileLocSetter.sDownloadPath+"Arch\\"+arrFileName[0].toString().trim()+"_"+formattedDate+"."+arrFileName[1].toString().trim());
		                              
		                               Files.move(fFilePath.toPath(), fDest.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		    
		                        }
		                        else
		                        {
		                              System.out.println("File not found");
		                        }
		                    
		                } catch (Exception e) {
		                    // TODO Auto-generated catch block
		                    e.printStackTrace();
		                } 
		    
		            } catch (Exception e) {
		                System.out.println("fnArchiveFile failed");
		                e.printStackTrace();
		            }    

		         }
		   
		   
		   public void fnSwitchToMainApp() throws Exception   //Switch to main advisor frame
			{
				try {
					
					       driver.navigate().to(TestDriver.sAppLink);

					        WebDriverWait wait = new WebDriverWait(driver,35);
							fnLoadingPageWait();
						
							
							//wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SphereCommon.Main_labelHomePageWelcome_xp)));
							wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SphereCommon.Main_HomeDashBoard_xp)));
							wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SphereCommon.Main_HomeDashBoard_xp)));
							
							//WebElement ele = driver.findElement(By.xpath(SphereCommon.Main_labelHomePageWelcome_xp));
							WebElement ele = driver.findElement(By.xpath(SphereCommon.Main_HomeDashBoard_xp));
							HighlightElement(ele);
							Thread.sleep(4000);
							
							if(ElementFound(SphereCommon.Main_BtnMenuExpand_xp))
							{
								ClickByXpath(SphereCommon.Main_BtnMenuExpand_xp, "Expand Button", true);
							}
									
				}catch (Exception e) {
					System.out.println("fnSwitchToMainApp--------------Failed");
					throw(e);
				}	

      } 

		   public void fnVerifyClickSuccessMessage() throws Exception
			{
				try {
					
					List<WebElement> sElementList = driver.findElements(By.xpath(SphereModules.Common_AddEditModules_MsgSuccess_xp));
					if(sElementList.size()>0)
					{
						for (WebElement ele : sElementList) 
						{
							
							String sSuccessMsg = ele.getText().toString();
							System.out.println(sSuccessMsg); 
							
							if (sSuccessMsg.equals("") || sSuccessMsg == null || sSuccessMsg.equals(" ")) {
								//Thread.sleep(60000);
								Thread.sleep(30000);
								sSuccessMsg = ele.getText().toString();
							}
							
							ele.click();
							Thread.sleep(3000);
							//if(resolvedcolor.equals(Whitecolor)||resolvedcolor.equals(lightcolor))
							if(sSuccessMsg.contains("Success")||sSuccessMsg.contains("successfully")||
									sSuccessMsg.contains("Order successfully updated!")||
									sSuccessMsg.contains("Order successfully added!")||
									sSuccessMsg.contains("You cannot export more than 2000 records.")||
									sSuccessMsg.contains("You cannot export more than 2000 records.")||
								    sSuccessMsg.contains("Processing!"))
							{
								obj.repAddData( "Click Success message", "Success message should be clicked","'"+sSuccessMsg+"' clicked successfully", "Pass");
							}
							else
							{
								obj.repAddData( "Click Failure/Warning message", "Failure/Warning message should be clicked","'"+sSuccessMsg+"' clicked successfully", "Fail");
							}
						
						
						}
					}


				} catch (Exception e) {
					System.out.println("fnVerifyClickSuccessMessage--------------Failed");
					//throw(e);
				}	
			}
		   
		   
		   
		   
		   
//***********************************************Code added by Surja***********************************************************
		   
		   
		   
		   
		   
		   
		   
		   public boolean fnIsFieldEditable(String xpath) throws Exception
			{
			   Boolean IsFieldEditable = false;
				try {
		   WebElement fieldName = driver.findElement(By.xpath(xpath));
		    fieldName.clear();
		    fieldName.sendKeys("123");
		    String fieldNameVal = fieldName.getAttribute("value");
		    IsFieldEditable=fieldNameVal.contentEquals("123");
		    if(IsFieldEditable==true){
		    System.out.println("Field is editable");
		   
		    
		    }
		    else{
		    System.out.println("Field is non editable" + fieldNameVal);
		    }
				}
				catch (Exception e) {
					System.out.println("fnIsFieldEditable--------------Failed");
				
				}
				return IsFieldEditable;	
			}
		   
		   
		   public void ClickByXpath1(String value, String faceValue, boolean bReportFlag) throws Exception
			{
				try {
					WebElement ele;
					ele = driver.findElement(By.xpath(value));
					System.out.println("Displayed>>>"+ele.isDisplayed());
					System.out.println("Enabled>>>"+ele.isEnabled());
					if(ele.isDisplayed()|| ele.isEnabled())
					{
					    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
						HighlightElement(ele);
						ele.click();
						Thread.sleep(1000);
						if(bReportFlag==true)
						{
						obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' is clicked", "Pass");
						}
					}
					else
					{
						TestDriver.log.info("Element found but not clicked (Reason : Not displayed or Not Enabled)");
					}
					fnLoadingPageWait();
					
					fnVerifyClickSuccessMessage1();
					//fnVerifyClickErrorMessage();
				    } catch (Exception e) {
					System.out.println("--No Such Element Found--");
					if(bReportFlag==true)
					{
					obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
					}
					throw(e);
				}
			}
		   
		   public void fnVerifyClickSuccessMessage1() throws Exception
			{
				try {
					
					List<WebElement> sElementList = driver.findElements(By.xpath(SphereModules.Common_AddEditModules_MsgSuccess_xp));
					if(sElementList.size()>0)
					{
						for (WebElement ele : sElementList) 
						{
							
							String sSuccessMsg = ele.getText().toString();
							ele.click();
							Thread.sleep(1000);
							//if(resolvedcolor.equals(Whitecolor)||resolvedcolor.equals(lightcolor))
							if(sSuccessMsg.equalsIgnoreCase("Success! The Invoice was saved."))
							{
								obj.repAddData( "Click Success message", "Success message should be clicked","'"+sSuccessMsg+"' clicked successfully", "Pass");
							}
							else
							{
								obj.repAddData( "Click Failure/Warning message", "Failure/Warning message should be clicked","'"+sSuccessMsg+"' clicked successfully", "Fail");
							}
						}
					}


				} catch (Exception e) {
					System.out.println("fnVerifyClickSuccessMessage--------------Failed");
					//throw(e);
				}	
				
			}
				
				public String ClickByXpath2(String value, String faceValue, boolean bReportFlag) throws Exception
				{
				   String msg = "";
					try {
						WebElement ele;
						ele = driver.findElement(By.xpath(value));
						System.out.println("Displayed>>>"+ele.isDisplayed());
						System.out.println("Enabled>>>"+ele.isEnabled());
						if(ele.isDisplayed()|| ele.isEnabled())
						{
						    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
							HighlightElement(ele);
							ele.click();
							Thread.sleep(1000);
							if(bReportFlag==true)
							{
							obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' is clicked", "Pass");
							}
						}
						else
						{
							TestDriver.log.info("Element found but not clicked (Reason : Not displayed or Not Enabled)");
						}
						fnLoadingPageWait();
						
						msg = fnVerifyClickSuccessMessage2();
						//fnVerifyClickErrorMessage();
					    } catch (Exception e) {
						System.out.println("--No Such Element Found--");
						if(bReportFlag==true)
						{
						obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
						}
						throw(e);
						
					}
					return msg;
				}   
				public String ClickByXpath3(String value, String faceValue, boolean bReportFlag) throws Exception
				{
				   String msg = "";
					try {
						WebElement ele;
						ele = driver.findElement(By.xpath(value));
						System.out.println("Displayed>>>"+ele.isDisplayed());
						System.out.println("Enabled>>>"+ele.isEnabled());
						if(ele.isDisplayed()|| ele.isEnabled())
						{
						    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
							HighlightElement(ele);
							ele.click();
							Thread.sleep(1000);
							if(bReportFlag==true)
							{
							obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' is clicked", "Pass");
							}
						}
						else
						{
							TestDriver.log.info("Element found but not clicked (Reason : Not displayed or Not Enabled)");
						}
						fnLoadingPageWait();
						
						//msg = fnVerifyClickSuccessMessage2();
						//fnVerifyClickErrorMessage();
					    } catch (Exception e) {
						System.out.println("--No Such Element Found--");
						if(bReportFlag==true)
						{
						obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
						}
						throw(e);
						
					}
					return msg;
				}   
				
				public String fnVerifyClickSuccessMessage2() throws Exception
				{   
				   String sSuccessMsg = "";
					try {
						
						List<WebElement> sElementList = driver.findElements(By.xpath(SphereModules.Common_AddEditModules_MsgSuccess_xp));
						if(sElementList.size()>0)
						{
							for (WebElement ele : sElementList) 
							{
								
								sSuccessMsg = ele.getText().toString();
								ele.click();
								Thread.sleep(1000);
								//if(resolvedcolor.equals(Whitecolor)||resolvedcolor.equals(lightcolor))
								if(sSuccessMsg.contains("Success")||sSuccessMsg.contains("successfully"))
								{
									obj.repAddData( "Click Success message", "Success message should be clicked","'"+sSuccessMsg+"' clicked successfully", "Pass");
								}
								else
								{
									obj.repAddData( "Click Failure/Warning message", "Failure/Warning message should be clicked","'"+sSuccessMsg+"' clicked successfully", "Fail");
								}
							}
						}


					} catch (Exception e) {
						System.out.println("fnVerifyClickSuccessMessage--------------Failed");
						//throw(e);
					}	
					
					return sSuccessMsg;
				}
			   		
				
				
				
				
				
				 public void fnVerifySearchedFilterOnMutualFundDocumentItem(String smutualfundscSearchValue) throws Exception

				 {  
					try
					{
						
						WebDriverWait wait = new WebDriverWait(driver,20);
									
						
								WebElement ele = driver.findElement(By.xpath(SphereModules.Sap_Validation_Screen_Manual_Funds_Commitment_DocumentNumberItemFirstRow_xp));
								String sText = ele.getText().toString().trim();
								
								if(smutualfundscSearchValue.equalsIgnoreCase(sText))
								{
									obj.repAddData( "Verify searched document item ","searched document item should be disdplayed on the screen",
											"searched document item is displayed on the screen", "Pass");
								}else
									
								{
									String Flag = "Y";
									String FlagData = TestDriver.mTestPhaseData.get(TestDriver.iTC_ID).get("VerifyInvalidSearchedData").trim();
									if(Flag.equalsIgnoreCase(FlagData))
									{
										obj.repAddData( "Verify searched text","Invalid searched text should not be disdplayed on the screen",
												"Invalid searched text is not displayed on the screen", "Pass");
										return;
									}
									obj.repAddData( "Verify searched text","searched text should be disdplayed on the screen",
											"searches text is not displayed on the screen", "Fail");
								}
						
					} catch (Exception e) {
					    System.out.println("fnVerifyDialogBox --------------Failed");    
					    TestDriver.log.error("fnVerifyDialogBox failed",e);
					    }
				 }
					 
				 
				 
				 public void fnVerifySearchedFilterOnMutualFundCompany(String sapValidationValue) throws Exception

				 {  
					try
					{
								
								WebElement ele1 = driver.findElement(By.xpath(SphereModules.Sap_Validation_Screen_Manual_Funds_Commitment_CompanyFirstRow_xp));
								String sapText = ele1.getText().toString().trim();
								fnLoadingPageWait();
								if(sapValidationValue.equalsIgnoreCase(sapText))
								{
									obj.repAddData( "Verify searched text","searched text should be disdplayed on the screen",
											"searches text is displayed on the screen", "Pass");
								}else
									
								{
									String Flag = "Y";
									String FlagData = TestDriver.mTestPhaseData.get(TestDriver.iTC_ID).get("VerifyInvalidSearchedData").trim();
									if(Flag.equalsIgnoreCase(FlagData))
									{
										obj.repAddData( "Verify searched text","Invalid searched text should not be disdplayed on the screen",
												"invalid searched text is not displayed on the screen", "Pass");
										return;
									}
									obj.repAddData( "Verify searched text","searched text should be disdplayed on the screen",
											"searched text is not displayed on the screen", "Fail");
								}
						
								
					} catch (Exception e) {
					    System.out.println("fnVerifyDialogBox --------------Failed");    
					    TestDriver.log.error("fnVerifyDialogBox failed",e);
					    }
					 }				
								
								
				 public boolean switchToWindow(String title) throws Exception {
					 Set<String> availableWindows = driver.getWindowHandles();
					 if (availableWindows.size() > 1) {
					 try {
					 for (String windowId : availableWindows) {
						 String name =driver.switchTo().window(windowId).getTitle();
					
						 obj.repAddData( "Verify Invoice pdf","Invoice pdf should display on the screen",
									"Invoice pdf is displayed on the screen", "Pass");
					 
					 return true;
					 //return true;
					 }
					 } catch (NoSuchWindowException e) {

					 TestDriver.log.error( "No child window is available to switch", e );
					 }
					 }

					 return false;
					 }
					
				 
					   
}// end of BaseClass