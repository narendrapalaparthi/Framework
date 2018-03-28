package oracle.adf.widgetactions;

import oracle.adf.widgets.WidgetInfo;

import org.openqa.selenium.WebElement;

public class Label extends GUIWidget implements IGUIWidget {

	public Label(WidgetInfo widgetInfo) {
		super(widgetInfo);
	}
	
	@Override
	public void setDisplayValue(String value) {
		value = value.toUpperCase();
		WebElement element = managerHelper.getWebElement(widgetInfo);
		element.click();
	}

	@Override
	public String getDisplayValue() {
		WebElement element = managerHelper.getWebElement(widgetInfo);
		return element.getText().trim();
	}
	

}
