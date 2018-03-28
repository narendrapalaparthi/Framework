package oracle.adf.scripts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import oracle.adf.core.ADFConfigure;
import oracle.adf.utilities.ADFLogger;
import oracle.adf.utilities.ReadExcel;
import oracle.adf.utilities.ReadXml;

import org.xml.sax.SAXException;


public abstract class ADFBaseScript{
	public static Map<String, String> globalConfig = new HashMap<String, String>();
	public static Map<String, List<Map<String, String>>> dataMap = new LinkedHashMap<String, List<Map<String, String>>>();
	public static ADFLogger logger = ADFLogger.getInstance();
	public static final String timeStamp = new SimpleDateFormat("MMddyyyyHHmmss").format(System.currentTimeMillis());
	public static final String systemDate =  new SimpleDateFormat("M/d/yy").format(System.currentTimeMillis());
	public static String suffix = "";
	public static Map<String, String> getGlobalConfig()
	{
		return globalConfig;
	}
	
	public static Map<String, List<Map<String, String>>> getDataMap()
	{
		return dataMap;
	}
	public static void ADFSetUp()
	{
		ReadExcel readExcel = new ReadExcel();
		ReadXml readXml = new ReadXml();
		try {
			globalConfig = readXml.getDataFromEnvXML(ADFConfigure.getADFConfiguration().get("configFile"));
			suffix = globalConfig.get("suffix");
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error(e);
		}
		dataMap = readExcel.getDataFromExcel(ADFConfigure.getADFConfiguration().get("dataFile"));
	}
	
	public abstract void scriptMain();
}
