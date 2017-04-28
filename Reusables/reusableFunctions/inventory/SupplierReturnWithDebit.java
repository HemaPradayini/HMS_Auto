package reusableFunctions.inventory;

import org.openqa.selenium.WebDriver;

import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class SupplierReturnWithDebit {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	String AutomationID;
	String DataSet;

	public SupplierReturnWithDebit() {

	}

	/**
	 * Use this
	 * @param AutomationID
	 */
	public SupplierReturnWithDebit(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	
public void gatePassCheckBox(){
		
		executeStep.performAction(SeleniumActions.Click, "","GatePassCheckBox");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnsDebitPage",true);
		
	}
	
	public void debitNotesDetails(){
		
		executeStep.performAction(SeleniumActions.Enter, "SupplierName","SupplierReturnsDebitSupplierName");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnsDebitSupplierLi",false);

		executeStep.performAction(SeleniumActions.Click, "","SupplierReturnsDebitSupplierLi");
		verifications.verify(SeleniumVerifications.Entered, "SupplierName","SupplierReturnsDebitSupplierName",false);

		
		
		executeStep.performAction(SeleniumActions.Select, "SupplierReturnType","SupplierReturnsDebitReturnType");
		verifications.verify(SeleniumVerifications.Selected, "SupplierReturnType","SupplierReturnsDebitReturnType",false);
		
		executeStep.performAction(SeleniumActions.Enter, "SupplierReturnRemarks","SupplierReturnsDebitRemarks");
		verifications.verify(SeleniumVerifications.Entered, "SupplierReturnRemarks","SupplierReturnsDebitRemarks",false);

	
	}
	
	public void addItem(String lineItemId){
		
		executeStep.performAction(SeleniumActions.Click, "","SupplierReturnsDebitItemAddButton");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnsDebitAddItemDialog",true);
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			
			executeStep.performAction(SeleniumActions.Enter, "StockEntryItem","SupplierReturnsDebitItemName");
			verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnsDebitItemLi",false);

			executeStep.performAction(SeleniumActions.Click, "","SupplierReturnsDebitItemLi");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryItem","SupplierReturnsDebitItemName",false);
/*	if (batchNo)
	{
		executeStep.performAction(SeleniumActions.Click, "","SupplierReturnsDebitBatchDropDown");
		   
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnsDebitBatchli",false);
		executeStep.performAction(SeleniumActions.Click, "","SupplierReturnsDebitBatchli");
	}
		
	*/		executeStep.performAction(SeleniumActions.Enter, "StockEntryQuantity","SupplierReturnsDebitItemQty");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryQuantity","SupplierReturnsDebitItemQty",true);
			
		
		   executeStep.performAction(SeleniumActions.Click, "","SupplierReturnsDebitItemSaveButton");
		   
			verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnsDebitAddItemDialog",false);
			
			EnvironmentSetup.lineItemCount++;

		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
		executeStep.performAction(SeleniumActions.Click, "","SupplierReturnsDebitItemCancelButton");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnsDebitPage",false);
	  
	
	}
	
	public void updateDebitNote(){
		
		executeStep.performAction(SeleniumActions.Click, "","SupplierReturnsDebitItemUpdateButton");
		//verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnsDebitPage",false);
	}
}
