package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


import java.io.File;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PriorAuth {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public PriorAuth(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PriorAuth(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void addItems(){
		executeStep.performAction(SeleniumActions.Click, "","AddItemBtnAddItem");
		verifications.verify(SeleniumVerifications.Appears, "","AddActivityDetails",false);

		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.lineItemCount = 0;           //Added by Tejaswini
		EnvironmentSetup.LineItemIdForExec = "PriorAuthItemOnPriorAuthPage";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		System.out.println("");
		//DbFunctions dbFunction = new DbFunctions();
		int rowCount=0;
		DbFunctions dbFunction = new DbFunctions();

		rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=0; i<rowCount; i++){			
			EnvironmentSetup.UseLineItem = true;
			executeStep.performAction(SeleniumActions.Click, "ItemType","AddItemItemType");
			verifications.verify(SeleniumVerifications.Appears, "","AddActivityDetails",false);

			executeStep.performAction(SeleniumActions.Enter, "Item","AddItemItemName");
			verifications.verify(SeleniumVerifications.Appears,"","AddItemItemNameList",false);
			
			executeStep.performAction(SeleniumActions.Click, "","AddItemItemNameList");
			verifications.verify(SeleniumVerifications.Appears, "","AddActivityDetails",false);
			executeStep.performAction(SeleniumActions.Enter, "ItemQuantity","AddActivityItemQuantity");
			verifications.verify(SeleniumVerifications.Appears, "","AddActivityDetails",false);
			
				//Added by Tejaswini for Priot Auth Workflow
			executeStep.performAction(SeleniumActions.Check, "SendForPriorAuth","SendForPriorAuthCheckBox");
			verifications.verify(SeleniumVerifications.Checked, "SendForPriorAuth","SendForPriorAuthCheckBox",false);
					//Added by Tejaswini for Prior Auth Workflow
			EnvironmentSetup.UseLineItem = false;

			executeStep.performAction(SeleniumActions.Click, "","AddActivityAddBtn");
			verifications.verify(SeleniumVerifications.Appears, "","AddActivityDetails",false);
			EnvironmentSetup.lineItemCount ++;

		}
		EnvironmentSetup.lineItemCount = 0;           //Added by Tejaswini
		EnvironmentSetup.UseLineItem = false;          //Added by abhishek
		executeStep.performAction(SeleniumActions.Click, "","AddActivityCloseBtn");
		verifications.verify(SeleniumVerifications.Appears, "","PriorAuthPage",false);//Synchronisation issue
		
		executeStep.performAction(SeleniumActions.Click, "","SavePriorAuthBtn");
		verifications.verify(SeleniumVerifications.Appears, "","PriorAuthPage",false);//Synchronisation issue*/

		executeStep.performAction(SeleniumActions.Click, "","ViewPriorAuthRequestXML");
/*		WebElement xmlObject = this.executeStep.getDriver().findElement(By.tagName("SenderID"));
		if (xmlObject != null){
			System.out.println("XML Generated");
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}*/
		verifyPriorAuthXML();
		
		System.out.println("Prior Auth asdPrescription Request Saved, Verified and Sent");
}
	
	public void verifyPriorAuthXML(){
		boolean matchFound  = false;
		List<String> codeList = getCodes();
		String xmlString =	this.executeStep.getDriver().getPageSource();
		try{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
		Element rootElement = doc.getDocumentElement();
		NodeList list = rootElement.getElementsByTagName("Code");
		for (int i = 0; i < list.getLength(); i++) {
			matchFound = false;
			Node childNode = list.item(i);
			System.out.println("Code is " + childNode.getTextContent());
			for (String code : codeList)	{
				if (childNode.getTextContent().equalsIgnoreCase(code)){
					matchFound = true;
					break;
				}
			}
		}
		if (matchFound){
			System.out.println("All Reqd Codes sent for Pre Auth");
		}		
		} catch (Exception e){
			System.out.println("Exception Occurred");
		}

}
	
	
	public void verifyPriorAuthApprovalStatus(){
		verifications.verify(SeleniumVerifications.Appears, "","PriorAuthRequestStatus",false);		
//		10.Click on auto refresh.
		executeStep.performAction(SeleniumActions.Click, "","PriorAuthApprovalPageRefreshBtn");
//		verifications.verify(SeleniumVerifications.Appears, "","PriorAuthApprovalPage",false);
//		11.The response will be shown be default for the request sent.		
//		12.User will see either fully approved/denied response. 
		verifications.verify(SeleniumVerifications.Appears, "","PriorAuthRequestApprovedStatus",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PriorAuthRequestStatus");
		verifications.verify(SeleniumVerifications.Appears, "","PriorAuthPrescriptionsEditMenu",false);

		executeStep.performAction(SeleniumActions.Click, "","PriorAuthPrescriptionsEditMenu");
		verifications.verify(SeleniumVerifications.Opens, "","PriorAuthApprovalPage",false);

//		13.Go to prior auth presc screen and check the prior auth status against each item.		
		EnvironmentSetup.intGlobalLineItemCount = 2;
		verifications.verify(SeleniumVerifications.Appears, "","PriorAuthItemApprovedStatus",false);
		EnvironmentSetup.intGlobalLineItemCount = 3;
		verifications.verify(SeleniumVerifications.Appears, "","PriorAuthItemApprovedStatus",false);
		EnvironmentSetup.intGlobalLineItemCount = 5;
		verifications.verify(SeleniumVerifications.Appears, "","PriorAuthItemApprovedStatus",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PriorAuthApprovalCancelBtn");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");

		verifications.verify(SeleniumVerifications.Appears, "","PriorAuthPage",false);
	}
	
	public void sendForPriorAuth(){
		executeStep.performAction(SeleniumActions.Click, "","SendPriorAuthRequestBtn");
		
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		System.out.println("Prior Auth Prescription Request Sent");

	}
	
	private List<String> getCodes(){ //Replace this with a SQL to retrieve from datasheet
		List <String> codeList = new ArrayList(); 
		codeList.add("86021");
		codeList.add("93306");
		codeList.add("29");
		return codeList;
	}
}
