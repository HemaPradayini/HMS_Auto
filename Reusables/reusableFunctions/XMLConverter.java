package reusableFunctions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class XMLConverter {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	 Element rootElement;
	 Document writeDoc,readDoc;
	 String pA;
	
	public XMLConverter(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public  void xml2XML(String type, String readXML, String hA, String submissionBatchID) {
		try {
			DocumentBuilderFactory dbFactoryWrite = DocumentBuilderFactory.newInstance();;
			DocumentBuilder dBuilderWrite = dbFactoryWrite.newDocumentBuilder();
			writeDoc = dBuilderWrite.newDocument();
			writeDoc.setXmlStandalone(true);
			rootElement = writeDoc.createElementNS("http://www.haad.ae/DataDictionary/CommonTypes", "Remittance.Advice");
			rootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance","xsi:noNamespaceSchemaLocation", "http://www.haad.ae/DataDictionary/CommonTypes/DataDictionary.xsd");
			writeDoc.appendChild(rootElement);
			readXML(type,readXML,hA);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(writeDoc);
			StreamResult console = new StreamResult(System.out);
			StreamResult file = new StreamResult(new File(submissionBatchID+".XML"));             // change file name here
			//write data
			transformer.transform(source, console);
			transformer.transform(source, file);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private  Node getHeader(Document doc, String sId, String rId, String tD,String rC,String df) {
		Element header = doc.createElement("Header");
		header.appendChild(getHeaderElements(doc, header, "SenderID", rId));
		header.appendChild(getHeaderElements(doc, header, "ReceiverID", sId));
		header.appendChild(getHeaderElements(doc, header, "TransactionDate", tD));
		header.appendChild(getHeaderElements(doc, header, "RecordCount", rC));
		header.appendChild(getHeaderElements(doc, header, "DispositionFlag", df));
		return header;
	}
	
	private  Node getClaim(Document doc, String id, String mI, String pID, String pI, String eIN, String g, String pS, String n,String type, String ha) {
		Element claim = doc.createElement("Claim");
		claim.appendChild(getClaimElements(doc, claim, "ID", id));
		claim.appendChild(getClaimElements(doc, claim, "IDPayer", pID));
		claim.appendChild(getClaimElements(doc, claim, "MemberID", mI));
		claim.appendChild(getClaimElements(doc, claim, "PayerID", pID));
		claim.appendChild(getClaimElements(doc, claim, "ProviderID", pI));
		claim.appendChild(getClaimElements(doc, claim, "EmiratesIDNumber", eIN));
		claim.appendChild(getClaimElements(doc, claim, "PaymentReference", Integer.toString(new Random().nextInt(100000) + 1)));
		claim.appendChild(getClaimElements(doc, claim, "DateSettlement", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())));
		NodeList nListActivity = readDoc.getElementsByTagName("Activity");
		for (int tempA = 0; tempA < nListActivity.getLength(); tempA++) {
			Node activityNode = nListActivity.item(tempA);
			if (activityNode.getNodeType() == Node.ELEMENT_NODE) {
				Element actElement = (Element) activityNode;
				if(ha=="HAAD"){
					claim.appendChild(getActivity(doc,
												  actElement.getElementsByTagName("ID").item(0).getTextContent(),
												  actElement.getElementsByTagName("Start").item(0).getTextContent(),
												  actElement.getElementsByTagName("Type").item(0).getTextContent(),
												  actElement.getElementsByTagName("Code").item(0).getTextContent(),
												  actElement.getElementsByTagName("Quantity").item(0).getTextContent(),
												  actElement.getElementsByTagName("Net").item(0).getTextContent(),
												  actElement.getElementsByTagName("OrderingClinician").item(0).getTextContent(),
												  type,ha
												)
									   );
				}
				else if(ha=="DHA"){
					claim.appendChild(getActivity(doc,
												  actElement.getElementsByTagName("ID").item(0).getTextContent(),
												  actElement.getElementsByTagName("Start").item(0).getTextContent(),
												  actElement.getElementsByTagName("Type").item(0).getTextContent(),
												  actElement.getElementsByTagName("Code").item(0).getTextContent(),
												  actElement.getElementsByTagName("Quantity").item(0).getTextContent(),
												  actElement.getElementsByTagName("Net").item(0).getTextContent(),
												  actElement.getElementsByTagName("Clinician").item(0).getTextContent(),
												  type,ha
												)
									   );
				}
			}
		}
		return claim;
	}
	
	private  Node getActivity(Document doc, String id,String s,String t, String code, String q, String n, String oC, String type, String ha) {
		if(type=="Full")
			pA = Float.toString(Float.parseFloat(n) * 1f);
		else if (type=="Partial")
			pA = Float.toString(Float.parseFloat(n) * 0.2f);
		Element activity = doc.createElement("Activity");
		activity.appendChild(getActivityElements(doc, activity, "ID", id));
		activity.appendChild(getActivityElements(doc, activity, "Start", s));
		activity.appendChild(getActivityElements(doc, activity, "Type", t));
		activity.appendChild(getActivityElements(doc, activity, "Code", code));
		activity.appendChild(getActivityElements(doc, activity, "Quantity", q));
		activity.appendChild(getActivityElements(doc, activity, "Net", n));
		if(ha=="HAAD")
			activity.appendChild(getActivityElements(doc, activity, "OrderingClinician", oC));
		activity.appendChild(getActivityElements(doc, activity, "Clinician", oC));
		activity.appendChild(getActivityElements(doc, activity, "PaymentAmount", pA));
		if(Float.parseFloat(n) != Float.parseFloat(pA))
			activity.appendChild(getActivityElements(doc, activity, "DenialCode","AUTH-005"));
		return activity;
	}
	
	private  Node getHeaderElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
	
	private  Node getClaimElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
	
	private  Node getActivityElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
	
	public  void readXML(String type, String readXML, String hA) {
		File fXmlFile;
		try {
			String workingDir = System.getProperty("user.dir");
            String filepath = workingDir +"\\"+readXML ;
            
            String test = filepath.replace("\\", "\\\\");
            System.out.println("FILEPATH :"+test);
			//File fXmlFile = new File("ReadXML.xml");
            for(int i=0;i<3;i++){
            	fXmlFile = new File(test);
            	if(!fXmlFile.exists() && i!=2) {
            		System.out.println("Trying to find file :"+(i+1));
            		Thread.sleep(30000);
            	}
            	else if(fXmlFile.exists()){
            		DocumentBuilderFactory dbFactoryRead = DocumentBuilderFactory.newInstance();
        			DocumentBuilder dBuilderRead = dbFactoryRead.newDocumentBuilder();
        			readDoc = dBuilderRead.parse(fXmlFile);
        			readDoc.getDocumentElement().normalize();
        			NodeList nListHeader = readDoc.getElementsByTagName("Header");
        			for (int tempH = 0; tempH < nListHeader.getLength(); tempH++) {
        				Node nNode = nListHeader.item(tempH);
        				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        					Element eElement = (Element) nNode;
        					rootElement.appendChild(getHeader(writeDoc, 
        													  eElement.getElementsByTagName("SenderID").item(0).getTextContent(),
        													  eElement.getElementsByTagName("ReceiverID").item(0).getTextContent(),
        													  eElement.getElementsByTagName("TransactionDate").item(0).getTextContent(),
        													  eElement.getElementsByTagName("RecordCount").item(0).getTextContent(),
        													  eElement.getElementsByTagName("DispositionFlag").item(0).getTextContent()
        													 )
        											);
        				}
        			}
        			NodeList nListClaim = readDoc.getElementsByTagName("Claim");
        			for (int tempC = 0; tempC < nListClaim.getLength(); tempC++) {
        				Node nNode = nListClaim.item(tempC);
        				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        					Element eElement = (Element) nNode;
        					rootElement.appendChild(getClaim(writeDoc,
        													 eElement.getElementsByTagName("ID").item(0).getTextContent(),
        													 eElement.getElementsByTagName("MemberID").item(0).getTextContent(),
        													 eElement.getElementsByTagName("PayerID").item(0).getTextContent(),
        													 eElement.getElementsByTagName("ProviderID").item(0).getTextContent(),
        													 eElement.getElementsByTagName("EmiratesIDNumber").item(0).getTextContent(),
        													 eElement.getElementsByTagName("Gross").item(0).getTextContent(),
        													 eElement.getElementsByTagName("PatientShare").item(0).getTextContent(),
        													 eElement.getElementsByTagName("Net").item(0).getTextContent(),
        													 type,hA
        													)
        											);
        				}
        			}
        			break;
            	}
            	else {
            		System.out.println("File Not Found");
            		System.exit(0);
            	}
            }
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
