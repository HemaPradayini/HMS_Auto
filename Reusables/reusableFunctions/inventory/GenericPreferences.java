package reusableFunctions.inventory;

import org.openqa.selenium.WebDriver;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class GenericPreferences {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	String AutomationID;
	String DataSet;

	public GenericPreferences() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public GenericPreferences(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void setPOValidation(){
		
		 executeStep.performAction(SeleniumActions.Select, "POValidation", "POValidationDropDown");
			verifications.verify(SeleniumVerifications.Selected, "POValidation","POValidationDropDown",false);
			
	}
	
	public void save(){
		
		executeStep.performAction(SeleniumActions.Click, "", "GenericPreferencesSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","GenericPreferencesPage",false);
	
	}
	
	public void POItemReject(){
		
		executeStep.performAction(SeleniumActions.Select, "POItemReject", "POItemRejectDropDown");
		verifications.verify(SeleniumVerifications.Selected, "POItemReject","POItemRejectDropDown",false);
		
	}
	
	public void SetStockEntryWithPO(){
        
        executeStep.performAction(SeleniumActions.Select, "StockEntryPo", "StockEntryPoDialogbox");
           verifications.verify(SeleniumVerifications.Selected, "StockEntryPo","StockEntryPoDialogbox",false);
	
	}
	
           
     public void UseMaxCostPriceDropdown(){
               
               executeStep.performAction(SeleniumActions.Select, "MaxPricePreference", "MaxCostPriceDropdown");
                  verifications.verify(SeleniumVerifications.Selected, "MaxPricePreference","MaxCostPriceDropdown",false);
	
		
		
	}
     
     public void allowDecimalForQtySupplier(){
 		executeStep.performAction(SeleniumActions.Select, "AllowDecimalForQtySupplier", "AllowDecimalForQtySupplierDropDown");
         verifications.verify(SeleniumVerifications.Selected, "AllowDecimalForQtySupplier","AllowDecimalForQtySupplierDropDown",false);
 	
 		
 	}
 	
 	public void returnAgainstSpecificSupplier(){
 		executeStep.performAction(SeleniumActions.Select, "ReturnAgainstSpecificSupplier", "ReturnAgainstSpecificSupplierDropDown");
         verifications.verify(SeleniumVerifications.Selected, "ReturnAgainstSpecificSupplier","ReturnAgainstSpecificSupplierDropDown",false);
 	
 		
 		
 	}
}
