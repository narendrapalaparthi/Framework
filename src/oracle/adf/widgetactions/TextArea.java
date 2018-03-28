package oracle.adf.widgetactions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import oracle.adf.widgets.WidgetInfo;

public class TextArea extends GUIWidget implements IGUIWidget {

	public TextArea(WidgetInfo widgetInfo) {
		super(widgetInfo);
	}
	
	@Override
	public void setDisplayValue(String value) {
		WebElement element = managerHelper.getWebElement(widgetInfo);
		List<WebElement> elements = element.findElements(By.tagName("textarea"));
		for (WebElement ele : elements)
			element = ele;

		element.clear();
		if (!("".equals(value) || "null".equals(value)))
			element.sendKeys(value+Keys.TAB);
	}

	@Override
	public String getDisplayValue() {
		WebElement element = managerHelper.getWebElement(widgetInfo);
		return element.getAttribute("value").trim();
	}
	
	public void setText(String text)
	{
		setDisplayValue(text);
		logger.widgetAction("Widget : "+widgetInfo.getName()+" is set with value : "+text);
	}

}
