package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.WebDriver;

import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class OPListSearch {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public OPListSearch(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public OPListSearch(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;

	}
	
	public void searchOPList(String navigateToScreenLink, String resultScreen){
		System.out.println("Inside OPListSearch searchOPList ");
		executeStep.performAction(SeleniumActions.Enter, "MRID","OPListScreenSearchField");
		verifications.verify(SeleniumVerifications.Entered, "MRID","OPListScreenSearchField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","OPListScreenSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "MRID","OPListScreenSearchResults",false);	

		executeStep.performAction(SeleniumActions.Click, "MRID","OPListScreenSearchResults");
		verifications.verify(SeleniumVerifications.Appears, "",navigateToScreenLink,false);	

		executeStep.performAction(SeleniumActions.Click, "",navigateToScreenLink);
		verifications.verify(SeleniumVerifications.Appears, "",resultScreen,false);
		
	}
	//Added by Abhishek to check if consult doctor is enabled
	public void consultationOPListDisabledCheck(String navigateToScreenLink){
		System.out.println("Inside OPListSearch searchOPList ");
		executeStep.performAction(SeleniumActions.Enter, "MRID","OPListScreenSearchField");
		verifications.verify(SeleniumVerifications.Entered, "MRID","OPListScreenSearchField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","OPListScreenSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "MRID","OPListScreenSearchResults",false);	

		executeStep.performAction(SeleniumActions.Click, "MRID","OPListScreenSearchResults");
		verifications.verify(SeleniumVerifications.Appears, "",navigateToScreenLink,false);	
		verifications.verify(SeleniumVerifications.Disabled, "",navigateToScreenLink,false);
		
	}
}
