package oracle.adf.utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadXml {
	
	public Map<String, String> getDataFromEnvXML(String filePath) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(filePath);
		
		doc.getDocumentElement().normalize();
		Map<String, String> envData = new HashMap<String, String>();
		NodeList nList=doc.getElementsByTagName("Variable");
		int noOfVariables = nList.getLength();
		for(int i=0;i<noOfVariables;i++)
		{
			Element element = (Element) nList.item(i);
			envData.put(element.getAttribute("name"), element.getAttribute("value"));
		}
		
		return envData;
	}

}
