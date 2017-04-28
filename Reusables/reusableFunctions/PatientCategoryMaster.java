package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PatientCategoryMaster {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public PatientCategoryMaster() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PatientCategoryMaster(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void categorySearch(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
		
		executeStep.performAction(SeleniumActions.Enter, "PatientCategoryMasterCategory","PatientCategoryMasterCategoryName");
		verifications.verify(SeleniumVerifications.Entered, "PatientCategoryMasterCategory","PatientCategoryMasterCategoryName",false);
		executeStep.performAction(SeleniumActions.Click, "","PatientCategoryMasterSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientCategoryMasterPage",false);
		executeStep.performAction(SeleniumActions.Click, "","PatientCategoryMasterGeneralLabel");
		verifications.verify(SeleniumVerifications.Appears, "","PatientCategoryMasterEditMenu",false);
		executeStep.performAction(SeleniumActions.Click, "","PatientCategoryMasterEditMenu");
		verifications.verify(SeleniumVerifications.Appears, "","EditPatientCategoryDetailsPage",false);
		executeStep.performAction(SeleniumActions.Select, "PatientCategoryCasefileRequired","EditPatientCategoryFileReqDropdown");
		verifications.verify(SeleniumVerifications.Appears, "","EditPatientCategoryDetailsPage",false);
		executeStep.performAction(SeleniumActions.Click, "","EditPatientCategorySaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditPatientCategoryDetailsPage",false);
	}
	
	
	}
