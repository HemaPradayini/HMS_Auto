package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Deposits {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public Deposits() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Deposits(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void collectDeposit(String DepositType,String AddOn){

		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
		
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("CollectRefundMRNoField", "CollectRefundMRCollection", "CollectRefundMRNoFindButton", "CollectRefundPage");
		
		executeStep.performAction(SeleniumActions.Select, "CollectionPaymentType", "CollectRefundPaymentTypeDropDown");
		verifications.verify(SeleniumVerifications.Selected, "CollectionPaymentType","CollectRefundPaymentTypeDropDown",false);
		
		if (DepositType == "General"){
			executeStep.performAction(SeleniumActions.Enter, "DepositGeneralPay", "CollectRefundPayField");
			verifications.verify(SeleniumVerifications.Entered, "DepositGeneralPay","CollectRefundPayField",false);
		}
		if (DepositType == "IP"){
			if (AddOn == "False"){
				executeStep.performAction(SeleniumActions.Enter, "DepositIPPay", "CollectRefundPayField");
				verifications.verify(SeleniumVerifications.Entered, "DepositIPPay","CollectRefundPayField",false);
			}
			else {
				executeStep.performAction(SeleniumActions.Enter, "DepositIPPayMore", "CollectRefundPayField");
				verifications.verify(SeleniumVerifications.Entered, "DepositIPPayMore","CollectRefundPayField",false);
			}
			executeStep.performAction(SeleniumActions.Check, "","CollectRefundApplicableToIPCheckBox");
			verifications.verify(SeleniumVerifications.Checked, "","CollectRefundApplicableToIPCheckBox",false);
		}
		executeStep.performAction(SeleniumActions.Click, "","CollectRefundSaveButton");
		verifications.verify(SeleniumVerifications.Opens, "","CollectRefundDepositReceipt",true);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","CollectRefundDepositReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","CollectRefundDepositReceipt",false);
			
	}
	public void refundDeposit(String DepositType){

		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
		
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("CollectRefundMRNoField", "CollectRefundMRCollection", "CollectRefundMRNoFindButton", "CollectRefundPage");
		
		executeStep.performAction(SeleniumActions.Select, "RefundPaymentType", "CollectRefundPaymentTypeDropDown");
		verifications.verify(SeleniumVerifications.Selected, "RefundPaymentType","CollectRefundPaymentTypeDropDown",false);
		
		if (DepositType == "General"){
			executeStep.performAction(SeleniumActions.Enter, "RefundGeneralPay", "CollectRefundPayField");
			verifications.verify(SeleniumVerifications.Entered, "RefundGeneralPay","CollectRefundPayField",false);
		}
		if (DepositType == "IP"){
			executeStep.performAction(SeleniumActions.Enter, "RefundIPPay", "CollectRefundPayField");
			verifications.verify(SeleniumVerifications.Entered, "RefundIPPay","CollectRefundPayField",false);
			
			executeStep.performAction(SeleniumActions.Check, "","CollectRefundApplicableToIPCheckBox");
			verifications.verify(SeleniumVerifications.Checked, "","CollectRefundApplicableToIPCheckBox",false);
		}
		executeStep.performAction(SeleniumActions.Click, "","CollectRefundSaveButton");
		verifications.verify(SeleniumVerifications.Opens, "","CollectRefundDepositReceipt",true);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","CollectRefundDepositReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","CollectRefundDepositReceipt",false);
			
	}
		
	}
