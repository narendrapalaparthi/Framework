package oracle.adf.widgetactions;

import oracle.adf.widgets.WidgetInfo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ComboBox extends GUIWidget implements IGUIWidget {

	public ComboBox(WidgetInfo widgetInfo) {
		super(widgetInfo);
	}
	
	@Override
	public void setDisplayValue(String value) {
		WebElement element = managerHelper.getWebElement(widgetInfo);
		if(!value.equalsIgnoreCase(element.getAttribute("value")))
		{
			String ul_ID = element.getAttribute("aria-owns");
			WebDriver driver = manager.getCurrentWebDriver();
			
			manager.waitForWidget(widgetInfo, 5, 1);
			element.click();
			String xpath = "//ul[@id='"+ul_ID+"']/li[text()='"+value+"']";
			manager.waitForWidget(By.xpath(xpath), 10, 1);
			driver.findElement(By.xpath(xpath)).click();
		}
		
	}

	@Override
	public String getDisplayValue() {
		WebElement element = managerHelper.getWebElement(widgetInfo);
		return element.getAttribute("value").trim();
	}

}
