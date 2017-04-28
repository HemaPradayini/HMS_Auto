package reusableFunctions.inventory;

import org.openqa.selenium.WebDriver;

import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PaymentTerms {

	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	String AutomationID;
	String DataSet;

	public PaymentTerms() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PaymentTerms(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
		public void createPaymentTerms(String lineItemId){
			
			executeStep.performAction(SeleniumActions.Click, "","PaymentsAddNewTemplate");
		//	verifications.verify(SeleniumVerifications.Appears, "","PaymentsTermsHeader",true);
			
			EnvironmentSetup.LineItemIdForExec = lineItemId;
			EnvironmentSetup.lineItemCount =0;
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

			EnvironmentSetup.UseLineItem = true;
			DbFunctions dbFunction = new DbFunctions();
			int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
			System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
			for(int i=1; i<=rowCount; i++){	
				EnvironmentSetup.UseLineItem = true;
			
			executeStep.performAction(SeleniumActions.Enter, "PaymentsTermsTemplateName", "PaymentsTermsTemplateNameField");
		    verifications.verify(SeleniumVerifications.Entered, "PaymentsTermsTemplateName","PaymentsTermsTemplateNameField",false);
		    
		    executeStep.performAction(SeleniumActions.Check, "PaymentsTermsPoDeliveryInstruction","PaymentsTermsPoDeliveryInstructionField");
		    verifications.verify(SeleniumVerifications.Checked, "PaymentsTermsPoDeliveryInstruction","PaymentsTermsPoDeliveryInstructionField",false);
		    
		    executeStep.performAction(SeleniumActions.Enter, "PaymentsTermsAndCondition", "PaymentsTermsAndConditionField");
		    verifications.verify(SeleniumVerifications.Entered, "PaymentsTermsAndCondition","PaymentsTermsAndConditionField",false);
		    
		    executeStep.performAction(SeleniumActions.Click, "","SupplierSaveButton");
		    verifications.verify(SeleniumVerifications.Appears, "","PaymentTermsAdd",false);
		    executeStep.performAction(SeleniumActions.Click, "", "PaymentTermsAdd");
		    
		    
		    //verifications.verify(SeleniumVerifications.Appears, "","PaymentsTermsHeader",false);
		    EnvironmentSetup.lineItemCount++;

			}
			EnvironmentSetup.lineItemCount =0;
			EnvironmentSetup.UseLineItem = false;
			
		   
			
		
			
		}
}
