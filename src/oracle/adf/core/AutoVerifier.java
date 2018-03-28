package oracle.adf.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import oracle.adf.utilities.ADFLogger;
import oracle.adf.widgetactions.IGUIWidget;
import oracle.adf.widgets.WidgetInfo;

public class AutoVerifier {

	public static ADFManagerHelper managerHelper = ADFManagerHelper
			.getInstance();
	public static ADFLogger logger = ADFLogger.getInstance();

	public static void verify(Object screenObject, Map<String, String> dataMap) {
		Map<String, WidgetInfo> widgetInfoMap = managerHelper
				.getWidgetInfos(screenObject);
		logger.info("Auto verification started... in screen : "
				+ screenObject.getClass().getSimpleName());
		for (String fieldName : dataMap.keySet()) {
			if (widgetInfoMap.containsKey(fieldName)) {
				WidgetInfo widgetInfo = widgetInfoMap.get(fieldName);
				verifyWidget(widgetInfo, fieldName, dataMap.get(fieldName));
			} else {
				logger.warning("Widget: " + fieldName + " not found in screenbject "
						+ screenObject.getClass().getSimpleName());
			}
		}
	}

	public static void verifyWidget(WidgetInfo widgetInfo, String fieldName,
			String actualValue) {
		try {
			logger.debug("Widget : "+fieldName);
			IGUIWidget guiObject = (IGUIWidget) widgetInfo.getLocatorType().getDeclaredConstructor(WidgetInfo.class).newInstance(widgetInfo);
			String expectedValue = guiObject.getDisplayValue();
			Verify.verifyEquals(expectedValue, actualValue);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			logger.fatal(e.getMessage());
		}
	}

}
