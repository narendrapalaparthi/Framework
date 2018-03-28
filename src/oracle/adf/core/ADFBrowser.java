package oracle.adf.core;

import java.net.MalformedURLException;
import java.net.URL;

import oracle.adf.scripts.ADFBaseScript;
import oracle.adf.utilities.ADFLogger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ADFBrowser{
	ADFManager adfManager = ADFManager.getInstance();
	ADFLogger logger = ADFLogger.getInstance();
	public void startBrowser(String startUrl)
	{
		WebDriver driver = initializeBrowser();
		logger.info("Browser launched Successfully");
		adfManager.setActiveDriver(driver);
		driver.manage().deleteAllCookies();
		driver.get(startUrl);
		logger.info("Browser loaded with url : "+startUrl);
		if(!("FF".equalsIgnoreCase(ADFConfigure.browser) || "mobile".equalsIgnoreCase(ADFConfigure.browser)))
		getActiveWindow().maximize();
	}
	
	public void loadUrl(String loadUrl)
	{
		adfManager.getCurrentWebDriver().navigate().to(loadUrl);
	}
	
	public Window getActiveWindow()
	{
		return adfManager.getCurrentWebDriver().manage().window();
	}
	
	private WebDriver initializeBrowser()
	{
		String browser = ADFConfigure.getADFConfiguration().get("browser");
		switch (browser) {
		case "IE":
			return getIEDriver();
			
		case "chrome":
			return getChromeDriver();
			
		case "edge":
			return getEdgeDriver();
			
		case "mobile":
			return getAndroidDriver();

		default:
			return getFFDriver();
		}
	}

	public WebDriver getFFDriver()
	{
		WebDriver driver = null;
		if(null == System.getProperty("webdriver.gecko.driver"))
			System.setProperty("webdriver.gecko.driver", ADFBaseScript.globalConfig.get("FFDriver"));
		if(Boolean.parseBoolean(ADFConfigure.getADFConfiguration().get("isRCServer")))
		{
			DesiredCapabilities DC_FF = DesiredCapabilities.firefox();
			DC_FF.setJavascriptEnabled(true);
			String url = "http://localhost:"+ADFConfigure.getADFConfiguration().get("port")+"/wd/hub";
			
			try {
				driver = new RemoteWebDriver(new URL(url), DC_FF);
			} catch (MalformedURLException e) {
				logger.error(e);
			}
		}
		else
			driver = new FirefoxDriver();
			
		return driver;
	}

	public WebDriver getIEDriver()
	{
		WebDriver driver = null;
		if(null == System.getProperty("webdriver.ie.driver"))
			System.setProperty("webdriver.ie.driver", ADFBaseScript.globalConfig.get("IEDriver"));
		if(Boolean.parseBoolean(ADFConfigure.getADFConfiguration().get("isRCServer")))
		{
			DesiredCapabilities DC_IE = DesiredCapabilities.internetExplorer();
			DC_IE.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			DC_IE.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
			DC_IE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			DC_IE.setCapability("ignoreProtectedModeSettings",true);
			String url = "http://localhost:"+ADFConfigure.getADFConfiguration().get("port")+"/wd/hub";
			
			try {
				driver = new RemoteWebDriver(new URL(url), DC_IE);
			} catch (MalformedURLException e) {
				logger.error(e);
			}
		}
		else
			driver = new InternetExplorerDriver();
		ADFManagerHelper.getInstance().sleep(2);
		return driver;
	}
	
	public WebDriver getEdgeDriver()
	{
		WebDriver driver = null;
		if(null == System.getProperty("webdriver.edge.driver"))
			System.setProperty("webdriver.edge.driver", ADFBaseScript.globalConfig.get("EdgeDriver"));
		if(Boolean.parseBoolean(ADFConfigure.getADFConfiguration().get("isRCServer")))
		{
			DesiredCapabilities DC_Edge = DesiredCapabilities.edge();
			DC_Edge.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			DC_Edge.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
			DC_Edge.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			DC_Edge.setCapability("ignoreProtectedModeSettings",true);
			String url = "http://localhost:"+ADFConfigure.getADFConfiguration().get("port")+"/wd/hub";
			
			try {
				driver = new RemoteWebDriver(new URL(url), DC_Edge);
			} catch (MalformedURLException e) {
				logger.error(e);
			}
		}
		else
			driver = new EdgeDriver();
		return driver;
	}
	
	public WebDriver getChromeDriver()
	{
		WebDriver driver = null;
		if(null == System.getProperty("webdriver.chrome.driver"))
			System.setProperty("webdriver.chrome.driver", ADFBaseScript.globalConfig.get("chromeDriver"));
		if(Boolean.parseBoolean(ADFConfigure.getADFConfiguration().get("isRCServer")))
		{
			ChromeOptions options = new ChromeOptions();
	        options.addArguments("--no-sandbox");
			DesiredCapabilities DC_Chrome = DesiredCapabilities.chrome();
			DC_Chrome.setCapability("enableNativeEvents", true);
			DC_Chrome.setCapability(ChromeOptions.CAPABILITY, options);
			String url = "http://localhost:"+ADFConfigure.getADFConfiguration().get("port")+"/wd/hub";
			
			try {
				driver = new RemoteWebDriver(new URL(url), DC_Chrome);
			} catch (MalformedURLException e) {
				logger.error(e);
			}
		}
		else
			driver = new ChromeDriver();
			
		return driver;
	}
	
	public WebDriver getAndroidDriver()
	{
		WebDriver driver = null;
//		DesiredCapabilities DC_Android = DesiredCapabilities.android();
//		DC_Android.setJavascriptEnabled(true);
////		DC_Android.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
//		DC_Android.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
//		DC_Android.setCapability(MobileCapabilityType.DEVICE_NAME, "My Phone");
		
		if(null == System.getProperty("webdriver.chrome.driver"))
			System.setProperty("webdriver.chrome.driver", ADFBaseScript.globalConfig.get("chromeDriver"));
		
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("androidPackage","com.android.chrome");
		
		try {
			driver = new ChromeDriver(chromeOptions);
		} catch (Exception e) {
			logger.error(e);
		}
		return driver;
	}

}
