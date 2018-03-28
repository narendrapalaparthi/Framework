package oracle.adf.widgets;

import java.util.HashMap;
import java.util.Map;


public class WidgetInfo {

	private String locatorString;
	private Class<?> locatorType;
	private Map<String, String> locatorMap = new HashMap<String, String>();

	public WidgetInfo(String locatorString, Class<?> locatorType) {
		this.locatorString = locatorString;
		this.locatorType = locatorType;
	}
	public String getName()
	{
		return WidgetInfo.class.getName();
	}

	public Class<?> getLocatorType() {
		return locatorType;
	}

	public Map<String, String> getLocatorMap() {
		setLocatorMap();
		return locatorMap;
	}

	private void setLocatorMap() {
		String[] locators = locatorString.split("\\|");
		for (String locator : locators) {
			locatorMap.put(locator.split("=")[0], locator.split("=",2)[1]);
		}
	}

	public void setLocatorString(String locatorString) {
		this.locatorString = locatorString;
	}

	public void setLocatorType(Class<?> locatorType) {
		this.locatorType = locatorType;
	}
	public String getLocatorString() {
		return this.locatorString;
	}
	public String getAutomatorName() {
		return this.locatorType.getSimpleName();
	}

}
