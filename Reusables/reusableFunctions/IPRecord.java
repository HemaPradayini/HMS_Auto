package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class IPRecord {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public IPRecord() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public IPRecord(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void ipRecordCreation(){
		executeStep.performAction(SeleniumActions.Click, "","IPCaseSheetIPRecordLink");
		verifications.verify(SeleniumVerifications.Appears, "","IPRecordPage",false);
		
		EnvironmentSetup.intGlobalLineItemCount = 1;
		executeStep.performAction(SeleniumActions.Enter, "GenericFormTestField1","IPRecordPageTestField");
		verifications.verify(SeleniumVerifications.Entered, "GenericFormTestField1","IPRecordPageTestField",false);
		
		EnvironmentSetup.intGlobalLineItemCount = 2;
		executeStep.performAction(SeleniumActions.Enter, "GenericFormTestField1","IPRecordPageTestField");
		verifications.verify(SeleniumVerifications.Entered, "GenericFormTestField1","IPRecordPageTestField",false);
		
		EnvironmentSetup.intGlobalLineItemCount = 3;
		executeStep.performAction(SeleniumActions.Enter, "GenericFormTestField1","IPRecordPageTestField");
		verifications.verify(SeleniumVerifications.Entered, "GenericFormTestField1","IPRecordPageTestField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","IPRecordPageSaveBtn");
		verifications.verify(SeleniumVerifications.Appears, "","IPRecordPage",false);

				
	}
	
	}
