package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.WebDriver;

import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class IPListSearch {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public IPListSearch(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public IPListSearch(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;

	}
	
	public void searchIPList(){
		System.out.println("Inside OPListSearch searchOPList ");
		
		
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
	//	search.searchMRNo("InPatientListMrNoField","InPatientListSelectionMRNoLi","InPatientListSearchButton","InPatientListPage");
	//	verifications.verify(SeleniumVerifications.Appears, "","InPatientListPage",false);
						
		//Navigate to IP Case Sheet Screen
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("IPListScreenIPCaseSheetLink", "IPCaseSheetScreen");
		
	}
	
}
