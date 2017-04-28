package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Vitals {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public Vitals() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Vitals(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void recordVitals(){
		executeStep.performAction(SeleniumActions.Enter, "Pulse","ConsultMgtScreenVitalsDialogPulseField");
		verifications.verify(SeleniumVerifications.Entered, "Pulse","ConsultMgtScreenVitalsDialogPulseField",true);
		
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenVitalsDialogOKButton");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultMgtScreenVitalsDialog",true);
		
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenVitalsDialogCancelButton");
		verifications.verify(SeleniumVerifications.Appears, "","VitalMeasurementsPage",true);
				
	}
	
	}
