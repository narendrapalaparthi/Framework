package oracle.adf.widgetactions;

import static oracle.adf.constants.ADFConstants.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import oracle.adf.widgets.WidgetInfo;

public class RadioButton extends GUIWidget implements IGUIWidget{

	public RadioButton(WidgetInfo widgetInfo) {
		super(widgetInfo);
	}

	@Override
	public void setDisplayValue(String value) {
		WebElement element = managerHelper.getWebElement(widgetInfo);
		List<WebElement> elements = element.findElements(By.tagName("input"));
		for (WebElement ele : elements)
			element = ele;
		if(key_Click.equals(value) || key_Y.equals(value) || key_Yes.equals(value) || key_True.equals(value))
			element.click();
	}

	@Override
	public String getDisplayValue() {
		
		WebElement element = managerHelper.getWebElement(widgetInfo);
		return element.getText();
	}
	
}
