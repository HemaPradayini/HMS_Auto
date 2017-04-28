package reusableFunctions;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

public class PatientIssue {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	
	public PatientIssue(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PatientIssue(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}

	
	public void saveIssue(){

		executeStep.performAction(SeleniumActions.Click, "","PatientIssuePageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);	
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Opens, "","StoresStockPatientIssuePage",true);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","StoresStockPatientIssuePage");
		verifications.verify(SeleniumVerifications.Closes, "","StoresStockPatientIssuePage",false);
		
		System.out.println("Issue Screen Saved");		
	}
	public void saveIssueReturns(){

		executeStep.performAction(SeleniumActions.Click, "","PatientIssueReturnsSaveButton");
		verifications.verify(SeleniumVerifications.Opens, "","StoresStockPatientIssueReturnsPage",true);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","StoresStockPatientIssueReturnsPage");
		verifications.verify(SeleniumVerifications.Closes, "","StoresStockPatientIssueReturnsPage",false);
		
		System.out.println("Issue Screen Saved");		
	}
	
	
	public void addPatientIssueItem(String lineItemId){
		executeStep.performAction(SeleniumActions.Click, "", "PatientIssuePageAddButton");
		verifications.verify(SeleniumVerifications.Appears, "", "PatientIssueAddItemsPage", false);
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		
		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=0; i<rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			executeStep.performAction(SeleniumActions.Enter, "Item","PatientIssueAddItemsItemsField");
			verifications.verify(SeleniumVerifications.Appears, "","PatientIssueAddItemsItemsList",false);
			
			executeStep.performAction(SeleniumActions.Click, "","PatientIssueAddItemsItemsList");
			verifications.verify(SeleniumVerifications.Entered, "Item","PatientIssueAddItemsItemsField",false);
			CommonUtilities.delay(200);
			
			executeStep.performAction(SeleniumActions.Enter, "ItemQuantity","PatientIssueAddItemsQtyField");
			verifications.verify(SeleniumVerifications.Appears, "","PatientIssueAddItemsPage",false);	
			
			executeStep.performAction(SeleniumActions.Click, "","PatientIssueAddItemsOKButton");
			verifications.verify(SeleniumVerifications.Appears, "","PatientIssueAddItemsPage",false);
			
			EnvironmentSetup.lineItemCount ++;
			EnvironmentSetup.UseLineItem = false;

	
		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
		
		executeStep.performAction(SeleniumActions.Click, "","PatientIssueAddItemsCancelButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientIssuePage",true);
		
		System.out.println("Added Order Items");
	}
	public void returnPatientIssueItem(String lineItemId){
		
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("PatientIssueReturnsMRNoField", "PatientIssueReturnsMRContainerField", "PatientIssueReturnsFindButton", "PatientIssueReturnPage");
	
		executeStep.performAction(SeleniumActions.Click, "", "PatientIssueReturnsPageAddButton");
		verifications.verify(SeleniumVerifications.Appears, "", "PatientIssueReturnsAddItemPage", false);
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		
		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=0; i<rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			executeStep.performAction(SeleniumActions.Enter, "Item","PatientIssueReturnsAddItemItemField");
			verifications.verify(SeleniumVerifications.Appears, "","PatientIssueReturnsAddItemItemList",false);
			
			executeStep.performAction(SeleniumActions.Click, "","PatientIssueReturnsAddItemItemList");
			// changed by Alamelu to handle sync problem
			verifications.verify(SeleniumVerifications.Entered, "Item","PatientIssueReturnsAddItemItemField",false);
		//	verifications.verify(SeleniumVerifications.Appears, "","PatientIssueReturnsAddItemPage",false);
			CommonUtilities.delay(200);
			
			executeStep.performAction(SeleniumActions.Enter, "IssueReturnedQuantity","PatientIssueReturnsAddItemQtyField");
			//verifications.verify(SeleniumVerifications.Appears, "","PatientIssueReturnsAddItemPage",false);	
			verifications.verify(SeleniumVerifications.Entered, "IssueReturnedQuantity","PatientIssueReturnsAddItemQtyField",false);
			
			executeStep.performAction(SeleniumActions.Click, "","PatientIssueAddItemsOKButton");
			verifications.verify(SeleniumVerifications.Appears, "","PatientIssueReturnsAddItemPage",false);
			
			EnvironmentSetup.lineItemCount ++;
			EnvironmentSetup.UseLineItem = false;

	
		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
		
		executeStep.performAction(SeleniumActions.Click, "","PatientIssueAddItemsCancelButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientIssueReturnPage",false);
		
		System.out.println("Added Order Items");
	}
	
	public void searchPatientIssue(){

		executeStep.performAction(SeleniumActions.Enter, "MRID","BillListMRNoField");
		verifications.verify(SeleniumVerifications.Appears, "MRID","BillListMRNoList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","BillListMRNoList");
		verifications.verify(SeleniumVerifications.Appears, "","PatientIssuePage",false);
		
	}
	
}
