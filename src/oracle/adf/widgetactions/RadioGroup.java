package oracle.adf.widgetactions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import oracle.adf.widgets.WidgetInfo;

public class RadioGroup extends GUIWidget implements IGUIWidget{

	public RadioGroup(WidgetInfo widgetInfo) {
		super(widgetInfo);
	}

	@Override
	public void setDisplayValue(String value) {
		WebElement element = managerHelper.getWebElement(widgetInfo);
		List<WebElement> labels = element.findElements(By.tagName("label"));
		for(WebElement ele : labels)
		{
			if(value.equals(ele.getText()))
			{
				String id = ele.getAttribute("for");
				managerHelper.getWebDriver().findElement(By.id(id)).click();
			}
		}
	}

	@Override
	public String getDisplayValue() {
		WebElement element = managerHelper.getWebElement(widgetInfo);
		return element.getText().trim();
	}

}
