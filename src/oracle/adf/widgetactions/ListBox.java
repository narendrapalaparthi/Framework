package oracle.adf.widgetactions;

import java.util.List;

import oracle.adf.widgets.WidgetInfo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ListBox extends GUIWidget implements IGUIWidget {

	public ListBox(WidgetInfo widgetInfo) {
		super(widgetInfo);
	}

	@Override
	public void setDisplayValue(String value) {
		WebElement element = managerHelper.getWebElement(widgetInfo);
		List<WebElement> elements = element.findElements(By.tagName("select"));
		for (WebElement ele : elements)
			element = ele;
		Select select = new Select(element);
		select.selectByVisibleText(value);
	}

	@Override
	public String getDisplayValue() {
		Select select = new Select(managerHelper.getWebElement(widgetInfo));
		return select.getFirstSelectedOption().getText();
	}

	public void selectByValue(String value) {
		Select select = new Select(managerHelper.getWebElement(widgetInfo));
		select.selectByValue(value);
		logger.widgetAction("Widget : " + widgetInfo.getName()
				+ " is selected by value " + value);
	}

	public void selectByVisibleText(String text) {
		setDisplayValue(text);
		logger.widgetAction("Widget : " + widgetInfo.getName()
				+ " is selected by visible text " + text);
	}

	public void selectByIndex(int index) {
		Select select = new Select(managerHelper.getWebElement(widgetInfo));
		select.selectByIndex(index);
		logger.widgetAction("Widget : " + widgetInfo.getName()
				+ " is selected by index " + index);
	}

}
