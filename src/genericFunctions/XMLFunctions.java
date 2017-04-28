package genericFunctions;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLFunctions {
	
	public static Map<String, String> dataMap;
	static int lineItemCount = 0;
	static DocumentBuilderFactory docFactory;
	static DocumentBuilder docBuilder;
	static String allColNames = "";
	static String[] colnames;
	static Document doc;
	static Element rootElement;
	static Attr attribute;
	
	static Element parentElement;
	static Element childElem;
	
	public static void createInputDataXML(ResultSet requiredDataResultSet, String parentNodeName){

		String columnName = "";
		String colData = "";
		
		dataMap = new HashMap<>();
		colnames = null;
		allColNames = "";
		initializexml();
		
		try{
			fetchAllColNames(requiredDataResultSet);
			colnames = allColNames.split(",");
			
			requiredDataResultSet.beforeFirst();
			while(requiredDataResultSet.next()){
				lineItemCount = lineItemCount +1;
				for(int i=0;i<colnames.length;i++){
					columnName = colnames[i];
					try{
						colData = requiredDataResultSet.getString(columnName);
						if(colData==null){
							colData = "";
						}
						dataMap.put(columnName, colData);
					}
					
					catch(Exception ex){
						System.out.println(ex.toString());
					}
				}
				
				buildInputDataXML(lineItemCount,parentNodeName);
				dataMap.clear();
				
			}
			
			lineItemCount = 0;
			publishXML(parentNodeName);
			
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
	}
	
	
	private static void buildInputDataXML(int NodeID, String NodeName){
		colnames = allColNames.split(",");
		if(!dataMap.isEmpty()){
			parentElement = doc.createElement(NodeName);
			rootElement.appendChild(parentElement);
			attribute = doc.createAttribute("id");
			attribute.setValue(Integer.toString(NodeID));
			parentElement.setAttributeNode(attribute);
			String colName = "";
			String colData= "";
			
			for(int i=0;i<colnames.length;i++){
				colName = colnames[i];
				colData = "";
				if(colName!=null && !colName.trim().isEmpty()){
					try{
						if(!colName.contains("#") && !colName.contains("/")){
							childElem = doc.createElement(colName);
							colData = dataMap.get(colName);
							if(colData==null){
								colData = "";
							}
							childElem.appendChild(doc.createTextNode(colData));
							parentElement.appendChild(childElem);
						}
					}
					catch(Exception ex){
						System.out.println(ex.toString());
					}
				}
			}
		}
	}
	
	private static void initializexml(){
		try{
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			rootElement = doc.createElement("TestData");
			doc.appendChild(rootElement);
		}
		catch(Exception ex){
			 System.out.println(ex.toString());
		}
	}
	
	private static void publishXML(String parentNode){
		try{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			EnvironmentSetup.xmlDataRootPath = EnvironmentSetup.strRootFolder + "\\Test Data\\"; 
			EnvironmentSetup.xmlDataPath = EnvironmentSetup.xmlDataRootPath + parentNode + ".xml";
			//File destinationFile = new File(EnvironmentSetup.xmlDataPath);
			//StreamResult result = new StreamResult(destinationFile);
			StreamResult result = new StreamResult(new FileOutputStream(EnvironmentSetup.xmlDataPath));
			
			transformer.transform(source, result);
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
	}

	
	private static void fetchAllColNames(ResultSet resultSet){
		String columnName = "";
		try{
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			System.out.println(rsmd.getColumnCount());
			
			for (int i=1; i<= rsmd.getColumnCount(); i++){
				columnName = rsmd.getColumnName(i);
				if(columnName!=null){
				
					if(i==1){
						allColNames = allColNames + columnName;
					}
					else{
						allColNames = allColNames + "," + columnName;
					}
				}
			}
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
	}
	
	public static String fetchDataFromXML(String columnName, int nodeCount){
		String requiredData = "";
		try{
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			EnvironmentSetup.xmlDataPath = EnvironmentSetup.xmlDataRootPath + EnvironmentSetup.XMLTagName + ".xml";
			Document doc = docBuilder.parse(EnvironmentSetup.xmlDataPath);
			
			try{
				requiredData = doc.getElementsByTagName(columnName).item(nodeCount).getTextContent();
				return requiredData;
			}
			catch(Exception ex){
				System.out.println(ex.toString());
				return "";
			}
		}
		catch(Exception ex){
			return "";
		}
	}
	
	public static void setDataInXML(String requiredData, String columnName, int nodeCount){
		
		Document docToEdit = null;
		
		try{
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			System.out.println(EnvironmentSetup.XMLTagName);
			EnvironmentSetup.xmlDataPath = EnvironmentSetup.xmlDataRootPath + EnvironmentSetup.XMLTagName + ".xml";
			docToEdit = docBuilder.parse(EnvironmentSetup.xmlDataPath);
			
			try{
				docToEdit.getElementsByTagName(columnName).item(nodeCount).setTextContent("");
				docToEdit.getElementsByTagName(columnName).item(nodeCount).setTextContent(requiredData);
				System.out.println(docToEdit.getElementsByTagName(columnName).item(nodeCount).getTextContent());
			}
			catch(Exception ex){
				System.out.println(ex.toString());
			}
		
		}
		catch(Exception ex){
			
		}
		
		try{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source = new DOMSource(docToEdit);
			StreamResult result = new StreamResult(new FileOutputStream(EnvironmentSetup.xmlDataPath));
			
			transformer.transform(source, result);
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
		
	}
	
	public static void getChildNodeCount(String nodeName){
		try{
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			EnvironmentSetup.xmlDataPath = EnvironmentSetup.xmlDataRootPath + EnvironmentSetup.XMLTagName + ".xml";
			Document doc = docBuilder.parse(new File(EnvironmentSetup.xmlDataPath));
			
			NodeList requiredNodes = doc.getElementsByTagName(nodeName);
			
			EnvironmentSetup.childLineItemCount = requiredNodes.getLength();
			System.out.println("Number of Child Nodes: " + EnvironmentSetup.childLineItemCount);
			
		}
		catch(Exception ex){
			
		}
		
	}
}
