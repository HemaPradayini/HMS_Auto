package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PBMPrescriptionsList {
	
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public PBMPrescriptionsList(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PBMPrescriptionsList(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void searchMRNo(){
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("PBMPrescriptionsListMRNoField", "PBMPrescriptionsListMRNoList", "PBMPrescriptionsListSearchButton", "PBMPrescriptionsListResultList");		       //added by abhishek
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);            
		executeStep.performAction(SeleniumActions.Accept, "","Framework");                 
	}

}
