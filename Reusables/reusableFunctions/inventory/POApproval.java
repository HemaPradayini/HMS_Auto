package reusableFunctions.inventory;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class POApproval {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public POApproval(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public POApproval(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}

	public void searchPO(){
		
System.out.println("Search PO");
		
			
		
		executeStep.performAction(SeleniumActions.Enter, "PurchaseOrderNo","PONoField");
		verifications.verify(SeleniumVerifications.Entered, "PurchaseOrderNo","PONoField",true);
		
		executeStep.performAction(SeleniumActions.Click, "","POApprovalPageSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","POResultTable",true);
		
		
		
	}
	
	public void clickEditMenuOption(){
		
		executeStep.performAction(SeleniumActions.Click, "","POResultTable");
		verifications.verify(SeleniumVerifications.Appears, "","POResultEditMenu",true);	
		
		executeStep.performAction(SeleniumActions.Click, "","POResultEditMenu");
		verifications.verify(SeleniumVerifications.Appears, "","ApproveRejectPOPage",true);	
		
	}
	
	public void approvePO(){
		
		executeStep.performAction(SeleniumActions.Click, "","ApproveRadioButton");
		verifications.verify(SeleniumVerifications.Appears, "","ApproveRejectPOPage",true);	
		
		executeStep.performAction(SeleniumActions.Click, "","ApproveRejectSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","POApprovalPage",true);	
		
		
	}
	
public void rejectPO(){
		
		executeStep.performAction(SeleniumActions.Click, "","RejectRadioButton");
		verifications.verify(SeleniumVerifications.Appears, "","AddRemarksDialog",true);
		
		executeStep.performAction(SeleniumActions.Enter, "PORejectRemarks","RejectionRemarks");
		verifications.verify(SeleniumVerifications.Entered, "PORejectRemarks","RejectionRemarks",true);
		
		executeStep.performAction(SeleniumActions.Click, "","AddRemarksDialogOKButton");
		verifications.verify(SeleniumVerifications.Appears, "","ApproveRejectPOPage",true);
		executeStep.performAction(SeleniumActions.Click, "","ApproveRejectSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
		
		
		verifications.verify(SeleniumVerifications.Appears, "","POApprovalPage",true);	
		
		
	}
}
