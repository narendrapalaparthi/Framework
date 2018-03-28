package oracle.adf.widgetactions;

import oracle.adf.widgets.WidgetInfo;

import org.openqa.selenium.WebElement;

public class GUIWidget implements IGUIWidget{
	
	public static WidgetInfo widgetInfo = null;
	
	public GUIWidget(WidgetInfo widgetInfo)
	{
		GUIWidget.widgetInfo = widgetInfo;
	}
	
	public void setDisplayValue(String value)
	{
		WebElement element = managerHelper.getWebElement(widgetInfo);
		element.click();
	}
	
	public String getDisplayValue()
	{
		WebElement element = managerHelper.getWebElement(widgetInfo);
		return element.getText().trim();
	}
	
	public void click()
	{
		WebElement element = managerHelper.getWebElement(widgetInfo);
//		JavascriptExecutor js = (JavascriptExecutor)ADFManager.getInstance().getCurrentWebDriver();
//		js.executeScript("arguments[0].click();", element);
		element.click();
		logger.widgetAction("Widget : "+widgetInfo.getName()+" is clicked");
	}
	
	public String getText()
	{
		WebElement element = managerHelper.getWebElement(widgetInfo);
		return element.getText();
	}
	public String getAttribute(String attributeName)
	{
		WebElement element = managerHelper.getWebElement(widgetInfo);
		return element.getAttribute(attributeName);
	}
}
