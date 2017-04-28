package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PBMApprovalList {KeywordSelectionLibrary executeStep;
VerificationFunctions verifications;


public PBMApprovalList(){

   }

   /**
    * Use this
    * @param AutomationID
    */
public PBMApprovalList(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
	   this.executeStep = execStep;
	   this.verifications = verifications;
   }

public void searchMRNo(){
	MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
	mrnoSearch.searchMRNo("PBMApprovalListMRNoField", "PBMApprovalListMRNoList", "PBMApprovalListResultList", "PBMApprovalListSearchButton");		       //added by abhishek
	verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);            
	executeStep.performAction(SeleniumActions.Accept, "","Framework");                 
}
}
