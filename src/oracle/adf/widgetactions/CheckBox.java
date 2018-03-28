package oracle.adf.widgetactions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import oracle.adf.widgets.WidgetInfo;
import static oracle.adf.constants.ADFConstants.*;

public class CheckBox extends GUIWidget implements IGUIWidget{

	public CheckBox(WidgetInfo widgetInfo) {
		super(widgetInfo);
	}

	@Override
	public void setDisplayValue(String value) {
		value = value.toUpperCase();
		WebElement element = managerHelper.getWebElement(widgetInfo);
		List<WebElement> elements = element.findElements(By.tagName("input"));
		for (WebElement ele : elements)
			element = ele;
		boolean selected = element.isSelected();
		if((!selected) && key_Check.equals(value))
			element.click();
		if(selected && (!key_Uncheck.equals(value)))
			element.click();
	}

	@Override
	public String getDisplayValue() {
		WebElement element = managerHelper.getWebElement(widgetInfo);
		return element.getText().trim();
	}
	
	public void check()
	{
		setDisplayValue("check");
		logger.widgetAction("Widget : "+widgetInfo.getName()+" is checked");
	}
	
	public void unCheck()
	{
		setDisplayValue("uncheck");
		logger.widgetAction("Widget : "+widgetInfo.getName()+" is unchecked");
	}

}
