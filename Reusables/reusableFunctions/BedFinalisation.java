package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class BedFinalisation {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public BedFinalisation() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public BedFinalisation(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void finaliseBed(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
		
		
		//MR No search in ADT page
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
	//	search.searchMRNo("InPatientListMrNoField","InPatientListSelectionMRNoLi","InPatientListSearchButton","ADTPage");
	//	verifications.verify(SeleniumVerifications.Appears, "","ADTPage",false);
				
		//Navigate to Bed Details Screen
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("ADTBedDetailsLink", "BedDetailsScreen");
		
		executeStep.performAction(SeleniumActions.Enter, "ShiftEndDate", "BedDetailsActionDateField");
		verifications.verify(SeleniumVerifications.Entered, "ShiftEndDate","BedDetailsActionDateField",false);
		
		
		executeStep.performAction(SeleniumActions.Click, "","BedDetailsFinalizeRadioButton");
		verifications.verify(SeleniumVerifications.Appears, "","BedDetailsScreen",false);
		
		executeStep.performAction(SeleniumActions.Click, "","BedDetailsSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","BedDetailsScreen",false);
			
	}
	public void finaliseBedFromBill(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
		
		executeStep.performAction(SeleniumActions.Enter, "ShiftEndDate", "BedDetailsActionDateField");
		verifications.verify(SeleniumVerifications.Entered, "ShiftEndDate","BedDetailsActionDateField",false);
		
		
		executeStep.performAction(SeleniumActions.Click, "","BedDetailsFinalizeRadioButton");
		verifications.verify(SeleniumVerifications.Appears, "","BedDetailsScreen",false);
		
		executeStep.performAction(SeleniumActions.Click, "","BedDetailsSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","BedDetailsScreen",false);
			
	}
	
	}
