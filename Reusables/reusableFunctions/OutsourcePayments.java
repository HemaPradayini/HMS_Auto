package reusableFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class OutsourcePayments {
	
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	
	public OutsourcePayments(){

	   }

	   /**
	    * Use this
	    * @param AutomationID
	    */
	public OutsourcePayments(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		   this.executeStep = execStep;
		   this.verifications = verifications;
	   }
	
	public void searchOutsourceRecords(){
	    executeStep.performAction(SeleniumActions.Enter, "OutsourceName","OutsourceField");
		verifications.verify(SeleniumVerifications.Appears, "","OutsourceList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","OutsourceList");
		verifications.verify(SeleniumVerifications.Entered, "OutsourceName","OutsourceField",false);
		
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("OutsourcePaymentsMRNoField", "OutsourcePaymentsMRNoList", "OutsourcePaymentsSearchButton", "OutsourcePaymentsResultList");		

	}
	
	public void outsourcepayments(){
		executeStep.performAction(SeleniumActions.Click, "","OutsourceResultCheckBox");
		verifications.verify(SeleniumVerifications.Appears, "","OutsourceResultCheckBox",false);
		executeStep.performAction(SeleniumActions.Click, "","OutsourceSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","OutsourcePostpaymentResult",false);
	}

}
