package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PatientReturnIndent {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public PatientReturnIndent(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PatientReturnIndent(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void patientIndentForReturn(){
		
		System.out.println("Inside PatientReturnIndent patientIndentForReturn ");
		MRNoSearch mrNoSearch = new MRNoSearch(executeStep, verifications);
		mrNoSearch.searchMRNo("RaisePatientIndentMRNoField", "RaisePatientIndentMRNoList", "RaisePatientIndentMRNoFindButton", "RaisePatientIndentPage");
		
		executeStep.performAction(SeleniumActions.Select, "IndentStatus","RaisePatientIndentStatusDropDown");
		verifications.verify(SeleniumVerifications.Selected, "IndentStatus","RaisePatientIndentStatusDropDown",false);
		executeStep.performAction(SeleniumActions.Click, "","RaisePatientIndentAddItemButton");
		verifications.verify(SeleniumVerifications.Appears, "","RaisePatientIndentAddItemDiv",false);

		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.lineItemCount=0;
		EnvironmentSetup.LineItemIdForExec = "ReturnedSalesIndent";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		//DbFunctions dbFunction = new DbFunctions();
		int rowCount=0;
		DbFunctions dbFunction = new DbFunctions();
		rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=0; i<rowCount; i++){			
			EnvironmentSetup.UseLineItem = true;
			executeStep.performAction(SeleniumActions.Enter, "IndentItem","AddItemPageItemField");
			verifications.verify(SeleniumVerifications.Appears,"","RaisePatientIndentAddItemDropDown",false);
			
			executeStep.performAction(SeleniumActions.Click, "","RaisePatientIndentAddItemDropDown");
			verifications.verify(SeleniumVerifications.Appears, "","RaisePatientIndentAddItemDiv",false);
			
/*			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			executeStep.performAction(SeleniumActions.Enter, "IndentItemReturnedQty","AddItemPageReqQtyField");
			verifications.verify(SeleniumVerifications.Entered, "IndentItemReturnedQty","AddItemPageReqQtyField",false);

			EnvironmentSetup.UseLineItem = false;

			executeStep.performAction(SeleniumActions.Click, "","AddItemPageAddButton");
			verifications.verify(SeleniumVerifications.Appears, "","RaisePatientIndentAddItemDiv",false);
			EnvironmentSetup.lineItemCount ++;
		}
		EnvironmentSetup.lineItemCount = 0;
		executeStep.performAction(SeleniumActions.Click, "","AddItemPageCancelButton");
		verifications.verify(SeleniumVerifications.Appears, "","RaisePatientIndentPage",false);//Synchronisation issue
				
		executeStep.performAction(SeleniumActions.Click, "","RaisePatientIndentSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditPatientIndentScreen",false);//Synchronisation issue

		System.out.println("Patient Indent Saved");

	}

}
