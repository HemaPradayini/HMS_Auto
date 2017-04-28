package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class DischargeSummary {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public DischargeSummary() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public DischargeSummary(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void PrepareDischargeSummary(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
		
		executeStep.performAction(SeleniumActions.Enter, "DischargeDrName","DischargeSummaryDischargingDrField");
		verifications.verify(SeleniumVerifications.Appears, "DischargeDrName","DischargeSummaryDischargingDrFieldList",false);
		
		executeStep.performAction(SeleniumActions.Click, "DischargeDrName","DischargeSummaryDischargingDrFieldList");
		verifications.verify(SeleniumVerifications.Entered, "DischargeDrName","DischargeSummaryDischargingDrField",false);	
		
		executeStep.performAction(SeleniumActions.Check, "","DischargeSummaryFinaliseField");
		verifications.verify(SeleniumVerifications.Checked, "","DischargeSummaryFinaliseField",true);
		
		executeStep.performAction(SeleniumActions.Enter, "AdmissionReason","DischargeSummaryReasonForAdmissionField");
		verifications.verify(SeleniumVerifications.Entered, "AdmissionReason","DischargeSummaryReasonForAdmissionField",false);
	
		executeStep.performAction(SeleniumActions.Enter, "PatientCondition","DischargeSummaryPatientConditionField");
		verifications.verify(SeleniumVerifications.Entered, "PatientCondition","DischargeSummaryPatientConditionField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","DischargeSummarySaveAndPrintButton");
		
		verifications.verify(SeleniumVerifications.Opens, "","DischargeSummaryTitle",true);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","DischargeSummaryTitle");
		verifications.verify(SeleniumVerifications.Closes, "","DischargeSummaryTitle",true);
		
		
		verifications.verify(SeleniumVerifications.Appears, "","DischargeSummaryPage",true);
		
			
	}
	public void doDischargeSummary()
	{
		executeStep.performAction(SeleniumActions.Click, "","DischargeSummarySelectTemplate");
		executeStep.performAction(SeleniumActions.Check, "","DischargeSummaryFinaliseField");
		verifications.verify(SeleniumVerifications.Checked, "","DischargeSummaryFinaliseField",true);
	    executeStep.performAction(SeleniumActions.Click, "","DischargeSummarySaveAndPrintButton");
		
		verifications.verify(SeleniumVerifications.Opens, "","DischargeSummaryTitle",true);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","DischargeSummaryTitle");
		verifications.verify(SeleniumVerifications.Closes, "","DischargeSummaryTitle",true);
		
		verifications.verify(SeleniumVerifications.Appears, "","DischargeSummaryPage",true);
	}
    
	
	}
