package oracle.adf.scripts;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import oracle.adf.core.ADFConfigure;
import oracle.adf.core.Verify;
import oracle.adf.utilities.ADFLogger;

public class ADFScriptRunner {
	public static ADFLogger logger;
	public static Date startTime;
	public static void main(String[] args) {
		try {
			startTime = Calendar.getInstance().getTime();
			logger = ADFLogger.getInstance();
			ADFBaseScript.ADFSetUp();
			logetupInformation();
			Class<?> cl= Class.forName(ADFConfigure.getADFConfiguration().get("script"));
			ADFBaseScript obj = (ADFBaseScript) cl.newInstance();
			obj.scriptMain();
		} catch ( InstantiationException | IllegalAccessException
				| IllegalArgumentException
				| SecurityException | ClassNotFoundException e) {
			logger.error(e);
		}
		finally
		{
			logger.verificationPoint("result=true Total number of verification points: "+(Verify.success+Verify.fail)+" Passed verification points: "+Verify.success+" Failed verification points: "+Verify.fail, true);
			logger.info("Total time elapsed : "+getTotalTimeLapsed());
			try {
				writeXML();
				generateHtml();
			} catch (TransformerException | FileNotFoundException e) {
				logger.error(e);
			}
			finally{
				try {
					Desktop.getDesktop().browse(new File(ADFLogger.logFile.replace(".xml", ".html")).toURI());
				} catch (IOException e) {
					logger.error(e);
				}
			}
			
		}
	}
	
	private static String getTotalTimeLapsed() {
		long diff = System.currentTimeMillis() - startTime.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000);
		String time = "";
		for(String s : (diffHours+":"+diffMinutes+":"+diffSeconds).split(":"))
		{
			if(s.length()<=1)
				s="0"+s;
			time += s+":";
		}
		return time.substring(0, time.length()-1);
	}

	private static void logetupInformation() {
		logger.info("Script Used : "+ADFLogger.script);
		logger.info("Execution Time Stamp : "+startTime.toString());
		logger.info("Config File Used : "+new File(ADFLogger.configFile).getAbsolutePath());
		logger.info("Data File Used : "+new File(ADFLogger.dataFile).getAbsolutePath());
		logger.info("Log File : "+ADFLogger.logFile.replaceAll(".xml", ".html"));
		logger.info("Launching in Browser  : "+ADFLogger.browser);
	}

	public static void writeXML () throws TransformerException
	{
		TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(ADFLogger.doc);
			StreamResult target = new StreamResult(ADFLogger.logFile);
			
			transformer.transform(source, target);
	}
	
	public static void generateHtml() throws FileNotFoundException, TransformerException
	{
		TransformerFactory tFactory=TransformerFactory.newInstance();

        Source xslDoc=new StreamSource(ADFConfigure.templateFile);
        Source xmlDoc=new StreamSource(ADFLogger.logFile);

        OutputStream htmlFile=new FileOutputStream(ADFLogger.logFile.replace(".xml", ".html"));
        Transformer trasform=tFactory.newTransformer(xslDoc);
        trasform.transform(xmlDoc, new StreamResult(htmlFile));
	}
	
}
