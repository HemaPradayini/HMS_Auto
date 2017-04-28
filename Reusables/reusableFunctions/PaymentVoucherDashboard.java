package reusableFunctions;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PaymentVoucherDashboard {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	
	public PaymentVoucherDashboard(){

	   }

	   /**
	    * Use this
	    * @param AutomationID
	    */
	public PaymentVoucherDashboard(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		   this.executeStep = execStep;
		   this.verifications = verifications;
	   }
	
	   //String currentdate= CommonUtilities.getSystemDate("dd-MM-yyyy");
	  
	
	
	public void searchPayeeWithDoctor(String PaymentType){
		DbFunctions dbFunctions = new DbFunctions();
		
		executeStep.performAction(SeleniumActions.Click, "","MoreOptions");
		verifications.verify(SeleniumVerifications.Appears, "","LessOptionsLink",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PaymentTypeCheckBox");
		verifications.verify(SeleniumVerifications.Appears, "","PaymentTypeCheckBox",false);
		
		
		if(PaymentType=="Doctor"){
			
			executeStep.performAction(SeleniumActions.Enter, "PayeeName","PayeeNameField");
			verifications.verify(SeleniumVerifications.Appears, "","PayeeNameList",false);
			
			executeStep.performAction(SeleniumActions.Click, "","PayeeNameList");
			verifications.verify(SeleniumVerifications.Entered, "PayeeName","PayeeNameField",false);
			
			executeStep.performAction(SeleniumActions.Click, "","DoctorCheckBox");
			verifications.verify(SeleniumVerifications.Appears, "","DoctorCheckBox",false);
			
		}
	    
         if(PaymentType=="Outgoing Tests"){
        	 
        	executeStep.performAction(SeleniumActions.Enter, "item","PayeeNameField");
 			verifications.verify(SeleniumVerifications.Appears, "","PayeeNameList",false);
 			
 			executeStep.performAction(SeleniumActions.Click, "","PayeeNameList");
 			verifications.verify(SeleniumVerifications.Entered, "item","PayeeNameField",false);
 			
			
			executeStep.performAction(SeleniumActions.Click, "","OutgoingTestsCheckBox");
			verifications.verify(SeleniumVerifications.Appears, "","OutgoingTestsCheckBox",false);
			
		}
         
         if(PaymentType=="Supplier"){
        	 
         	executeStep.performAction(SeleniumActions.Enter, "StockEntrySupplierName","PayeeNameField");
  			verifications.verify(SeleniumVerifications.Appears, "","PayeeNameList",false);
  			
  			executeStep.performAction(SeleniumActions.Click, "","PayeeNameList");
  			verifications.verify(SeleniumVerifications.Entered, "StockEntrySupplierName","PayeeNameField",false);
  			
 			
 			executeStep.performAction(SeleniumActions.Click, "","SupplierCheckBox");
 			verifications.verify(SeleniumVerifications.Appears, "","SupplierCheckBox",false);
 			
 		}
        dbFunctions.storeDate(this.executeStep.getDataSet(), "PaymentVoucherStartDate","C",0);
     	
     	executeStep.performAction(SeleniumActions.Enter, "PaymentVoucherStartDate","FromDateField");
     	verifications.verify(SeleniumVerifications.Entered,"PaymentVoucherStartDate","FromDateField",false);
     	
     	executeStep.performAction(SeleniumActions.Enter, "PaymentVoucherStartDate","ToDateField");
     	verifications.verify(SeleniumVerifications.Entered,"PaymentVoucherStartDate","ToDateField",false);
     	
		executeStep.performAction(SeleniumActions.Click, "","SearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","PayeeSearchResult",true);
		
		

	}
	
	
	public void selectPaymentVoucherOP(){
	   
		executeStep.performAction(SeleniumActions.Click, "","PayeeSearchResult");
		verifications.verify(SeleniumVerifications.Appears, "","PaymentVoucherOPOnly",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PaymentVoucherOPOnly");
		verifications.verify(SeleniumVerifications.Appears, "","PaymentVoucherHeader",false);
		
		

	}
	
	public void selectPaymentVoucher(){
		   
		executeStep.performAction(SeleniumActions.Click, "","PayeeSearchResult");
		verifications.verify(SeleniumVerifications.Appears, "","PaymentVoucherOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PaymentVoucherOption");
		verifications.verify(SeleniumVerifications.Appears, "","PaymentVoucherHeader",false);
		
		

	}
	
	 

}
