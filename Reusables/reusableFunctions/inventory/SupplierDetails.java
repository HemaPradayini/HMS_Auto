package reusableFunctions.inventory;

import org.openqa.selenium.WebDriver;

import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class SupplierDetails {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	String AutomationID;
	String DataSet;

	public SupplierDetails() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public SupplierDetails(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	 public void addSupplier(){
		
		executeStep.performAction(SeleniumActions.Click, "","AddNewSupplierLink");
	    verifications.verify(SeleniumVerifications.Appears, "","AddSupplierDetailsPage",false);
	    
		
	}
	 
	 public void createSupplier(){
			
			executeStep.performAction(SeleniumActions.Enter, "SupplierName", "SupplierName");
		    verifications.verify(SeleniumVerifications.Entered, "SupplierName","SupplierName",false);
		    
		    executeStep.performAction(SeleniumActions.Enter, "SupplierCreditPeriod", "SupplierCreditPeriodField");
		    verifications.verify(SeleniumVerifications.Entered, "SupplierCreditPeriod","SupplierCreditPeriodField",false);
		    
		    executeStep.performAction(SeleniumActions.Enter, "SupplierAddress", "SupplierAddressField");
		    verifications.verify(SeleniumVerifications.Entered, "SupplierAddress","SupplierAddressField",false);
		
		    executeStep.performAction(SeleniumActions.Click, "","SupplierSaveButton");
		    verifications.verify(SeleniumVerifications.Appears, "","EditSupplierDetailsPage",false);
		    
		    
		} 
	
	public void createMultipleSupplier(String lineItemId){
		
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

	EnvironmentSetup.UseLineItem = true;
	DbFunctions dbFunction = new DbFunctions();
	int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
	System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
	for(int i=1; i<=rowCount; i++){	
		EnvironmentSetup.UseLineItem = true;
		
		    
	    executeStep.performAction(SeleniumActions.Enter, "SupplierName", "SupplierName");
	    verifications.verify(SeleniumVerifications.Entered, "SupplierName","SupplierName",false);
	    
	    executeStep.performAction(SeleniumActions.Enter, "SupplierCreditPeriod", "SupplierCreditPeriodField");
	    verifications.verify(SeleniumVerifications.Entered, "SupplierCreditPeriod","SupplierCreditPeriodField",false);
	    
	    executeStep.performAction(SeleniumActions.Enter, "SupplierAddress", "SupplierAddressField");
	    verifications.verify(SeleniumVerifications.Entered, "SupplierAddress","SupplierAddressField",false);
	
	    executeStep.performAction(SeleniumActions.Click, "","SupplierSaveButton");
	    verifications.verify(SeleniumVerifications.Appears, "","EditSupplierAddButton",false);
	
	  executeStep.performAction(SeleniumActions.Click, "","EditSupplierAddButton");
	  verifications.verify(SeleniumVerifications.Appears, "","AddSupplierDetailsPage",false);
	
	
	    EnvironmentSetup.lineItemCount++;

	}
	
	EnvironmentSetup.lineItemCount =0;
	EnvironmentSetup.UseLineItem = false;
	
	
	}} 
	
	    
	    
	
	

		 