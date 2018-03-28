package oracle.adf.widgetactions;

import oracle.adf.core.ADFManager;
import oracle.adf.core.ADFManagerHelper;
import oracle.adf.utilities.ADFLogger;

public interface IGUIWidget{
	
	public static final ADFManager manager = ADFManager.getInstance();
	public static final ADFManagerHelper managerHelper = ADFManagerHelper.getInstance();
	public static final ADFLogger logger = ADFLogger.getInstance();
	
	public void setDisplayValue(String value);
	
	public String getDisplayValue();
}
