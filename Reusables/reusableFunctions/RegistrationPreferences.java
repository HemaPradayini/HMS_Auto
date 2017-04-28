package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class RegistrationPreferences {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public RegistrationPreferences() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public RegistrationPreferences(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void setMRDCaseFilePreferences(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");	
		executeStep.performAction(SeleniumActions.Select, "GenerateCasefile","RegistrationPreferencesGenerateCasefile");
		verifications.verify(SeleniumVerifications.Appears, "","RegistrationPreferencesPage",false);
		
		executeStep.performAction(SeleniumActions.Select, "IssueToDept","RegistrationPreferencesIssueToDept");
		verifications.verify(SeleniumVerifications.Appears, "","RegistrationPreferencesPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","RegistrationPreferencesSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","RegistrationPreferencesPage",false);
	}
	
	
	}
