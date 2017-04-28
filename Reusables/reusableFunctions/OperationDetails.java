package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class OperationDetails {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public OperationDetails() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public OperationDetails(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void addAnaesthsiaTypes(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
				
		executeStep.performAction(SeleniumActions.Click, "", "OperationDetailsPageAddAnaesthsiaTypePlusButton");
		verifications.verify(SeleniumVerifications.Appears, "","AddAnaesthesiaTypePage",false);
		
		//EnvironmentSetup.replaceData= true;
		EnvironmentSetup.lineItemCount = 0;
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.LineItemIdForExec = "AnesthesiaType";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		
		DbFunctions dbFunction = new DbFunctions();
		int rowCount=0;
		rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		EnvironmentSetup.selectByPartMatchInDropDown = true;
		for(int i=0; i<rowCount; i++){			
			EnvironmentSetup.UseLineItem = true;
			
			executeStep.performAction(SeleniumActions.Select, "AnaesthesiaType","AddAnaesthesiaTypePageTypeField");
			verifications.verify(SeleniumVerifications.Selected, "AnaesthesiaType","AddAnaesthesiaTypePageTypeField",true);
						
			EnvironmentSetup.UseLineItem = false;

			executeStep.performAction(SeleniumActions.Click, "","AddAnaesthesiaTypePageOkButton");
			verifications.verify(SeleniumVerifications.Appears, "","AddAnaesthesiaTypePage",true);
	
			EnvironmentSetup.lineItemCount ++;
			
		}
		
		EnvironmentSetup.selectByPartMatchInDropDown = false;
		EnvironmentSetup.lineItemCount = 0;
		
		executeStep.performAction(SeleniumActions.Click, "","AddAnaesthesiaTypePageCancelButton");
		verifications.verify(SeleniumVerifications.Appears, "","OperationDetailsPage",true);
	}
	public void addAnaesthetists(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
				
		executeStep.performAction(SeleniumActions.Click, "", "OperationDetailsPageAddAnaesthetistsPlusButton");
		verifications.verify(SeleniumVerifications.Appears, "","AddAnaesthetistsPage",false);
		
		//EnvironmentSetup.replaceData= true;
		EnvironmentSetup.lineItemCount = 0;
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.LineItemIdForExec = "Anaesthetists";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		
		DbFunctions dbFunction = new DbFunctions();
		int rowCount=0;
		rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		EnvironmentSetup.selectByPartMatchInDropDown = true;
		for(int i=0; i<rowCount; i++){			
			EnvironmentSetup.UseLineItem = true;
			
			executeStep.performAction(SeleniumActions.Select, "AnaesthetistsRole","AddAnaesthetistsPageRoleDropdown");
			verifications.verify(SeleniumVerifications.Selected, "AnaesthetistsRole","AddAnaesthetistsPageRoleDropdown",true);
			
			executeStep.performAction(SeleniumActions.Enter, "AnaesthetistsName","AddAnaesthetistsPageAnaesthetistsName");
			verifications.verify(SeleniumVerifications.Appears, "","AddAnaesthetistsPageAnaesthetistsNameList",false);
			
			executeStep.performAction(SeleniumActions.Click, "AnaesthetistsName","AddAnaesthetistsPageAnaesthetistsNameList");
			verifications.verify(SeleniumVerifications.Entered, "AnaesthetistsName","AddAnaesthetistsPageAnaesthetistsName",true);	
					
			EnvironmentSetup.UseLineItem = false;

			executeStep.performAction(SeleniumActions.Click, "","AddAnaesthetistsPageOkButton");
			verifications.verify(SeleniumVerifications.Appears, "","AddAnaesthetistsPageOkButton",true);
	
			EnvironmentSetup.lineItemCount ++;
			
		}
		
		EnvironmentSetup.selectByPartMatchInDropDown = false;
		EnvironmentSetup.lineItemCount = 0;
		
		executeStep.performAction(SeleniumActions.Click, "","AddAnaesthetistsPageCancelButton");
		verifications.verify(SeleniumVerifications.Appears, "","OperationDetailsPage",true);
	}
	
	public void saveOperationDetails(){
		
		executeStep.performAction(SeleniumActions.Select, "ChargeType","OperationDetailsPageChargeTypeDropdown");
		verifications.verify(SeleniumVerifications.Selected, "ChargeType","OperationDetailsPageChargeTypeDropdown",true); 
		saveOperation();
		
		
	}
    public void saveOperation()//added by Reena as chargetype is disabled 
    {

		executeStep.performAction(SeleniumActions.Select, "Status","OperationDetailsPageStatusDropdown");
		verifications.verify(SeleniumVerifications.Selected, "Status","OperationDetailsPageStatusDropdown",true);
		
		executeStep.performAction(SeleniumActions.Click, "","OperationDetailsPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","OperationDetailsPage",true);
    }
	
	}
