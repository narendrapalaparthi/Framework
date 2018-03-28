package oracle.adf.widgetactions;

import static oracle.adf.constants.ADFConstants.key_Click;
import static oracle.adf.constants.ADFConstants.key_True;
import static oracle.adf.constants.ADFConstants.key_Y;
import static oracle.adf.constants.ADFConstants.key_Yes;

import java.util.List;

import oracle.adf.widgets.WidgetInfo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Button extends GUIWidget implements IGUIWidget{

	public Button(WidgetInfo widgetInfo) {
		super(widgetInfo);
	}

	@Override
	public void setDisplayValue(String value) {
		value = value.toUpperCase();
		WebElement element = managerHelper.getWebElement(widgetInfo);
		List<WebElement> elements = element.findElements(By.tagName("button"));
		for (WebElement ele : elements)
			element = ele;
		if(key_Click.equals(value) || key_Y.equals(value) || key_Yes.equals(value) || key_True.equals(value))
			element.click();
	}

	@Override
	public String getDisplayValue() {
		WebElement element = managerHelper.getWebElement(widgetInfo);
		return element.getText().trim();
	}

}
