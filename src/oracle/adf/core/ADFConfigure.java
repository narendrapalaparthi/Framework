package oracle.adf.core;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import oracle.adf.utilities.ADFLogger;

public class ADFConfigure {
	
	public static String script;
	public static String basePath;
	public static String configFile;
	public static String dataFile;
	public static String browser = "firefox";
	public static String logPath;
	public static String logName = "AdfLog";
	public static String timeStamp = "fasle";
	public static String instanceId;
	public static String success_robot = "false";
	public static String success_scroll = "false";
	public static String failure_robot = "false";
	public static String failure_scroll = "true";
	public static String error_robot = "false";
	public static String error_scroll = "true";
	public static String isRCServer = "false";
	public static String port = "4444";
	public static String templateFile;
	public static String openLogFile = "true";
	
	public static Map<String, String> ADFConfigMap = new HashMap<String, String>();
	
	public ADFConfigure()
	{
		assignSystemArguments();
		setADFConfiguration();
	}
	
	private void assignSystemArguments() {
		if(null != System.getProperty("script"))
			script = System.getProperty("script");
		if(null != System.getProperty("basePath"))
			basePath = System.getProperty("basePath");
		if(null != System.getProperty("configFile"))
			configFile = System.getProperty("configFile");
		if(null != System.getProperty("dataFile"))
			dataFile = System.getProperty("dataFile");
		if(null != System.getProperty("browser"))
			browser = System.getProperty("browser");
		if(null != System.getProperty("logPath"))
			logPath = System.getProperty("logPath")+"\\Adflogs\\"+(new SimpleDateFormat("MM-dd-yyyy").format(System.currentTimeMillis()));
		else
			logPath = System.getProperty("user.dir")+"\\Adflogs\\"+(new SimpleDateFormat("MM-dd-yyyy").format(System.currentTimeMillis()));
		if(null != System.getProperty("logName"))
			logName = System.getProperty("logName");
		if(null != System.getProperty("timeStamp"))
			timeStamp = System.getProperty("timeStamp");
		if(null != System.getProperty("instanceId"))
			logName = System.getProperty("instanceId");
		else if(Boolean.parseBoolean(timeStamp))
			logName +="_"+System.currentTimeMillis();
		if(null != System.getProperty("success.robot"))
			success_robot = System.getProperty("success.robot");
		if(null != System.getProperty("success.scroll"))
			success_scroll = System.getProperty("success.scroll");
		if(null != System.getProperty("failure.robot"))
			failure_robot = System.getProperty("failure.robot");
		if(null != System.getProperty("failure.scroll"))
			failure_scroll = System.getProperty("failure.scroll");
		if(null != System.getProperty("error.robot"))
			error_robot = System.getProperty("error.robot");
		if(null != System.getProperty("error.scroll"))
			error_scroll = System.getProperty("error.scroll");
		if(null != System.getProperty("isRCServer"))
			isRCServer = System.getProperty("isRCServer");
		if(null != System.getProperty("port"))
			port = System.getProperty("port");
		if(null != System.getProperty("openLogFile"))
			openLogFile = System.getProperty("openLogFile");
		if(null != System.getProperty("templateFile"))
			templateFile = System.getProperty("templateFile");
		else if(null != basePath)
			templateFile = basePath+"\\NewStylesheet.xsl";
	}

	public static Map<String, String> getADFConfiguration()
	{
		return ADFConfigMap;
	}
	
	private void setADFConfiguration()
	{
		Field[] fields = ADFConfigure.class.getFields();
		String variable;
		for(Field field : fields)
		{
			variable = field.getName();
			if("ADFConfigMap".equals(variable))
				continue;
				try {
					ADFConfigMap.put(variable, (String)field.get(null));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					ADFLogger.getInstance().error(e);
				}
		}
	}
	
	public static void setADFConfigurableVriable(String variableName, String newValue)
	{
		ADFConfigMap.put(variableName, newValue);
		try {
			ADFConfigure.class.getDeclaredField(variableName).set(null, newValue);
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			ADFLogger.getInstance().error(e);
		}
	}
	
}
 