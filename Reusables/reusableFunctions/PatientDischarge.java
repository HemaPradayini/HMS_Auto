package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PatientDischarge {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public PatientDischarge(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PatientDischarge(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void patientDischarge(){
		
		executeStep.performAction(SeleniumActions.Select, "DischargeType","PatientDischargeDischargeType");
		verifications.verify(SeleniumVerifications.Selected, "DischargeType","PatientDischargeDischargeType",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PatientDischargeDischargeButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientDischargePage",false);
		

	}
	
}
