package reusableFunctions;

import genericFunctions.DbFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PaymentVoucher {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	
	public PaymentVoucher(){

	   }

	   /**
	    * Use this
	    * @param AutomationID
	    */
	public PaymentVoucher(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		   this.executeStep = execStep;
		   this.verifications = verifications;
	   }
	
	public void SearchpayeeWithdate(){
		DbFunctions dbFunctions = new DbFunctions();
		
		executeStep.performAction(SeleniumActions.Enter, "StockEntrySupplierName","PaymentVoucherPayeeName");
		verifications.verify(SeleniumVerifications.Appears, "","PaymentVoucherPayeeNameSearchList",false);
			
		executeStep.performAction(SeleniumActions.Click, "","PaymentVoucherPayeeNameSearchList");
		verifications.verify(SeleniumVerifications.Entered, "StockEntrySupplierName","PaymentVoucherPayeeName",false);
		
        dbFunctions.storeDate(this.executeStep.getDataSet(), "PaymentVoucherStartDate","C",0);
     	
     	executeStep.performAction(SeleniumActions.Enter, "PaymentVoucherStartDate","PaymentVoucherFromdateField");
     	verifications.verify(SeleniumVerifications.Entered,"PaymentVoucherStartDate","PaymentVoucherFromdateField",false);
     	
     	executeStep.performAction(SeleniumActions.Enter, "PaymentVoucherStartDate","PaymentVoucherTodateField");
     	verifications.verify(SeleniumVerifications.Entered,"PaymentVoucherStartDate","PaymentVoucherTodateField",false);
     	
		executeStep.performAction(SeleniumActions.Click, "","SearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","PayeeSearchResult",true);
	}
	
	
	public void ExportCsv(){
		executeStep.performAction(SeleniumActions.Click, "","ExportCsvButton");
	}
	

}
