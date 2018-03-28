package oracle.adf.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import oracle.adf.utilities.ADFLogger;
import oracle.adf.widgets.WidgetInfo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ADFManagerHelper {
	
	public static ADFManagerHelper adfManagerHelper= new ADFManagerHelper();
	ADFManager adfManager = ADFManager.getInstance();
	
	public static ADFManagerHelper getInstance()
	{
		return adfManagerHelper;
	}
	
	public WebElement getWebElement(WidgetInfo widgetInfo)
	{
		adfManager.waitForAjaxToLoad();
		By byLocator = getByLocator(widgetInfo);
		WebElement element = getWebDriver().findElement(byLocator);
		adfManager.getJSExecutor().executeScript("arguments[0].setAttribute('style', 'background-color: red')", element);
		return element;
	}
	
	public WebDriver getWebDriver()
	{
		return adfManager.getCurrentWebDriver();
	}
	
	public void switchWindow(String winTitle)
	{
		WebDriver driver = getWebDriver();
		if(winTitle.equalsIgnoreCase(driver.getTitle()))
			return;
		for(String winHandle : driver.getWindowHandles())
		{
			driver.switchTo().window(winHandle);
			if(winTitle.equalsIgnoreCase(driver.getTitle()))
					break;
		}
	}
	
	public void switchFrame(WidgetInfo widgetInfo)
	{
		getWebDriver().switchTo().frame(getWebElement(widgetInfo));
	}

	public By getByLocator(WidgetInfo widgetInfo) {
		Map<String, String> locatorMap = widgetInfo.getLocatorMap();
		if(locatorMap.size()<=1)
			return getSinglelocator(locatorMap);
		else
			return getMultipleLocator(locatorMap);
	}

	private By getMultipleLocator(Map<String, String> locatorMap) {
		By locator = null;
		for(String locatorType : locatorMap.keySet())
		{
			System.out.println(locatorMap.get(locatorType));
		}
		return locator;
	}

	private By getSinglelocator(Map<String, String> locatorMap) {
		By locator = null;
		for(String locatorType : locatorMap.keySet())
		{
			switch(locatorType){
			case "id" :
			{
				locator = By.id(locatorMap.get(locatorType));
				break;
			}
			case "name" :
			{
				locator = By.name(locatorMap.get(locatorType));
				break;
			}
			case "linktext" :
			{
				locator = By.linkText(locatorMap.get(locatorType));
				break;
			}
			case "partiallinktext" :
			{
				locator = By.partialLinkText(locatorMap.get(locatorType));
				break;
			}
			case "class" :
			{
				locator = By.className(locatorMap.get(locatorType));
				break;
			}
			case "tag" :
			{
				locator = By.tagName(locatorMap.get(locatorType));
				break;
			}
			case "css" :
			{
				locator = By.cssSelector(locatorMap.get(locatorType));
				break;
			}
			default :
			{
				locator = By.xpath(locatorMap.get(locatorType));
				break;
			}
			}
		}
		return locator;
	}

	public Map<String, WidgetInfo> getWidgetInfos(Object screenObject) {
		Map<String, WidgetInfo> widgetInfoMap = new HashMap<String, WidgetInfo>();
		Set<Class<?>> screenClasses = new LinkedHashSet<Class<?>>();
		Class<?> screenClass = screenObject.getClass();
		while(true)
		{
			screenClasses.add(screenClass);
			if(null == (screenClass = screenClass.getSuperclass()))
				break;
		}
		for(Class<?> screen : screenClasses)
		{
			Class<?>[] innerClasses = screen.getClasses();
			for(Class<?> innerClass : innerClasses)
			{
				if("Widgets".equalsIgnoreCase(innerClass.getSimpleName()))
				{
					Field[] fields = innerClass.getFields();
					for(Field field : fields)
					{
						try {
							widgetInfoMap.put(field.getName(), (WidgetInfo)field.get(null));
						} catch (IllegalArgumentException | IllegalAccessException e) {
							ADFLogger.getInstance().error(e);
						}
					}
				}
			}
		}
		
		return widgetInfoMap;
	}

	public void sleep(double sec) {
		try {
			Thread.sleep((long) (sec*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
