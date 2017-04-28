package reusableFunctions.insurance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.MRNoSearch;
import reusableFunctions.SiteNavigation;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class CodeMaster {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public CodeMaster(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public CodeMaster(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void storecodesCodeMaster(String lineItemId){
		
		executeStep.performAction(SeleniumActions.Select, "CodeMasterPageCodeType","CodeMasterPageCodeType");
		verifications.verify(SeleniumVerifications.Selected, "CodeMasterPageCodeType","CodeMasterPageCodeType",false);
		
		executeStep.performAction(SeleniumActions.Click, "","CodeMasterPageSearch");
		verifications.verify(SeleniumVerifications.Appears, "", "CodeMasterPage", false);
		
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.lineItemCount = 0;           
		EnvironmentSetup.LineItemIdForExec = "CodeField";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		int rowCount=0;
		DbFunctions dbFunction = new DbFunctions();

		rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){			
			EnvironmentSetup.UseLineItem = true;
			EnvironmentSetup.intGlobalLineItemCount=i+1;
			EnvironmentSetup.LineItemIdForExec = "CodeField-"+i;
			executeStep.performAction(SeleniumActions.Store,"CodeMasterPageCodeField","CodeMasterPageCodes");
			EnvironmentSetup.intGlobalLineItemCount++;
			EnvironmentSetup.lineItemCount ++;
	
		}
		EnvironmentSetup.intGlobalLineItemCount=0;
       	EnvironmentSetup.UseLineItem = false; 
		
	}
		
	
public void storecodesRevisit(String lineItemId){
		
		executeStep.performAction(SeleniumActions.Select, "CodeMasterPageCodeType","CodeMasterPageCodeType");
		verifications.verify(SeleniumVerifications.Selected, "CodeMasterPageCodeType","CodeMasterPageCodeType",false);
		
		executeStep.performAction(SeleniumActions.Click, "","CodeMasterPageSearch");
		verifications.verify(SeleniumVerifications.Appears, "", "CodeMasterPage", false);
		
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.lineItemCount = 0;           
		EnvironmentSetup.LineItemIdForExec = "RevisitCode";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		int rowCount=0;
		DbFunctions dbFunction = new DbFunctions();

		rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){			
			EnvironmentSetup.UseLineItem = true;
			EnvironmentSetup.intGlobalLineItemCount=i+5;
			EnvironmentSetup.LineItemIdForExec = "RevisitCode-"+i;
			executeStep.performAction(SeleniumActions.Store,"CodeMasterPageCodeField","CodeMasterPageCodes");
			EnvironmentSetup.intGlobalLineItemCount++;
			EnvironmentSetup.lineItemCount ++;
	
		}
		EnvironmentSetup.intGlobalLineItemCount=0;
       	EnvironmentSetup.UseLineItem = false; 
		
	}
	
	
public void storecodesLaboratory(String lineItemId){
	
	executeStep.performAction(SeleniumActions.Select, "CodeMasterPageCodeType","CodeMasterPageCodeType");
	verifications.verify(SeleniumVerifications.Selected, "CodeMasterPageCodeType","CodeMasterPageCodeType",false);
	
	executeStep.performAction(SeleniumActions.Click, "","CodeMasterPageSearch");
	verifications.verify(SeleniumVerifications.Appears, "", "CodeMasterPage", false);
	
	EnvironmentSetup.UseLineItem = true;
	EnvironmentSetup.lineItemCount = 0;           
	EnvironmentSetup.LineItemIdForExec = "CodesForLab";
	System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
	int rowCount=0;
	DbFunctions dbFunction = new DbFunctions();

	rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
	System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
	for(int i=1; i<=rowCount; i++){			
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.intGlobalLineItemCount=i+1;
		EnvironmentSetup.LineItemIdForExec = "CodesForLab-"+i;
		executeStep.performAction(SeleniumActions.Store,"CodeMasterPageCodeField","CodeMasterPageCPTCodes");
		EnvironmentSetup.intGlobalLineItemCount++;
		EnvironmentSetup.lineItemCount ++;

	}
	EnvironmentSetup.intGlobalLineItemCount=0;
   	EnvironmentSetup.UseLineItem = false; 
	
}

public void storecodesRadiology(String lineItemId){
	
	executeStep.performAction(SeleniumActions.Select, "CodeMasterPageCodeType","CodeMasterPageCodeType");
	verifications.verify(SeleniumVerifications.Selected, "CodeMasterPageCodeType","CodeMasterPageCodeType",false);
	
	executeStep.performAction(SeleniumActions.Click, "","CodeMasterPageSearch");
	verifications.verify(SeleniumVerifications.Appears, "", "CodeMasterPage", false);
	
	EnvironmentSetup.UseLineItem = true;
	EnvironmentSetup.lineItemCount = 0;           
	EnvironmentSetup.LineItemIdForExec = "CodesForRadio";
	System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
	int rowCount=0;
	DbFunctions dbFunction = new DbFunctions();

	rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
	System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
	for(int i=1; i<=rowCount; i++){			
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.intGlobalLineItemCount=i+5;
		EnvironmentSetup.LineItemIdForExec = "CodesForRadio-"+i;
		executeStep.performAction(SeleniumActions.Store,"CodeMasterPageCodeField","CodeMasterPageCPTCodes");
		EnvironmentSetup.intGlobalLineItemCount++;
		EnvironmentSetup.lineItemCount ++;

	}
	EnvironmentSetup.intGlobalLineItemCount=0;
   	EnvironmentSetup.UseLineItem = false; 
	
}

public void storecodesPackages(String lineItemId){
	
	executeStep.performAction(SeleniumActions.Select, "CodeMasterPageCodeType","CodeMasterPageCodeType");
	verifications.verify(SeleniumVerifications.Selected, "CodeMasterPageCodeType","CodeMasterPageCodeType",false);
	
	executeStep.performAction(SeleniumActions.Click, "","CodeMasterPageSearch");
	verifications.verify(SeleniumVerifications.Appears, "", "CodeMasterPage", false);
	
	EnvironmentSetup.UseLineItem = true;
	EnvironmentSetup.lineItemCount = 0;           
	EnvironmentSetup.LineItemIdForExec = "CodesForPackages";
	System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
	int rowCount=0;
	DbFunctions dbFunction = new DbFunctions();

	rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
	System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
	for(int i=1; i<=rowCount; i++){			
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.intGlobalLineItemCount=i+9;
		EnvironmentSetup.LineItemIdForExec = "CodesForPackages-"+i;
		executeStep.performAction(SeleniumActions.Store,"CodeMasterPageCodeField","CodeMasterPageCPTCodes");
		EnvironmentSetup.intGlobalLineItemCount++;
		EnvironmentSetup.lineItemCount ++;

	}
	EnvironmentSetup.intGlobalLineItemCount=0;
   	EnvironmentSetup.UseLineItem = false; 
	
}
public void storecodesService(String lineItemId){
	
	executeStep.performAction(SeleniumActions.Select, "CodeMasterPageCodeType","CodeMasterPageCodeType");
	verifications.verify(SeleniumVerifications.Selected, "CodeMasterPageCodeType","CodeMasterPageCodeType",false);
	
	executeStep.performAction(SeleniumActions.Click, "","CodeMasterPageSearch");
	verifications.verify(SeleniumVerifications.Appears, "", "CodeMasterPage", false);
	
	EnvironmentSetup.UseLineItem = true;
	EnvironmentSetup.lineItemCount = 0;           
	EnvironmentSetup.LineItemIdForExec = "CodesForService";
	System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
	int rowCount=0;
	DbFunctions dbFunction = new DbFunctions();

	rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
	System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
	for(int i=1; i<=rowCount; i++){			
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.intGlobalLineItemCount=i+1;
		EnvironmentSetup.LineItemIdForExec = "CodesForService-"+i;
		executeStep.performAction(SeleniumActions.Store,"CodeMasterPageCodeField","CodeMasterPageServiceCode");
		EnvironmentSetup.intGlobalLineItemCount++;
		EnvironmentSetup.lineItemCount ++;

	}
	EnvironmentSetup.intGlobalLineItemCount=0;
   	EnvironmentSetup.UseLineItem = false; 
	
}
		
	}
		
	
		
		