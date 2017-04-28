package reusableFunctions.inventory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class SupplierReturn {

	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	String AutomationID;
	String DataSet;

	public SupplierReturn() {

	}

	/**
	 * Use this
	 * @param AutomationID
	 */
	public SupplierReturn(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void selectPackageUOM(){
		
		executeStep.performAction(SeleniumActions.Click, "","PackageUOMradiobutton");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnPage",true);
	}
	
	public void ReturnDetails(){
		executeStep.performAction(SeleniumActions.Enter, "SupplierName", "ReturnDetailsSupplier");
		verifications.verify(SeleniumVerifications.Appears, "","ReturnDetailsSupplierListBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ReturnDetailsSupplierListBox");
		verifications.verify(SeleniumVerifications.Entered, "SupplierName","ReturnDetailsSupplier",false);
		
		executeStep.performAction(SeleniumActions.Select, "SupplierReturnType", "ReturnTypeDropDown");
		verifications.verify(SeleniumVerifications.Selected, "SupplierReturnType","ReturnTypeDropDown",false);
		
		executeStep.performAction(SeleniumActions.Enter, "SupplierReturnRemarks", "ReturnRemarksField");
		verifications.verify(SeleniumVerifications.Entered, "SupplierReturnRemarks","ReturnRemarksField",false);

	}
	
	public void gatePassCheckBox(){
		
		executeStep.performAction(SeleniumActions.Click, "","GatePassCheckBox");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnPage",true);
		
	}
	
	public void addReturnItem(String lineItemId, boolean returnLarge){
		executeStep.performAction(SeleniumActions.Click, "","ReturnItemAddButton");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnAddItemLegend",true);
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			
			executeStep.performAction(SeleniumActions.Enter, "StockEntryItem","SupplierReturnItemName");
			verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnItemNameLi",false);

			executeStep.performAction(SeleniumActions.Click, "","SupplierReturnItemNameLi");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryItem","SupplierReturnItemName",false);

			
			
			executeStep.performAction(SeleniumActions.Click, "","SupplierReturnItemBatchNo");
			verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnItemBatchNoSelect",true);

			executeStep.performAction(SeleniumActions.Click, "","SupplierReturnItemBatchNoSelect");
			
		
			executeStep.performAction(SeleniumActions.Enter, "StockEntryQuantity","SupplierReturnItemQty");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryQuantity","SupplierReturnItemQty",true);
			
		
		   executeStep.performAction(SeleniumActions.Click, "","SupplierReturnItemSave");
		   if(returnLarge){
			   verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
			   executeStep.performAction(SeleniumActions.Accept, "","Framework");
		   }
			verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnAddItemLegend",false);
			
			EnvironmentSetup.lineItemCount++;

		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
		executeStep.performAction(SeleniumActions.Click, "","SupplierReturnItemCancel");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnPage",false);
	  
	
		
		
	}
	
	public void clickReturn(){
		
		executeStep.performAction(SeleniumActions.Click, "","SupplierReturnReturnButton");
		verifications.verify(SeleniumVerifications.Appears, "","Supplier ReturnsReplacementsPage",false);
		
	}
	//added by asmita
	public void StoreTxnNo(){
		
	executeStep.performAction(SeleniumActions.Store, "SelectTxnNo", "SelectTxnNo");
	
	}
	//added by asmita
	public void SearchTxnNo(){
		
		executeStep.performAction(SeleniumActions.Enter, "SelectTxnNo","TxnNoRow");
		verifications.verify(SeleniumVerifications.Entered, "SelectTxnNo","TxnNoRow",true);
		
		
		executeStep.performAction(SeleniumActions.Click, "","SearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","SelectTxnNumTable",false);
		
		
	}
	public void ViewPrintTxnNo(){//added by asmita
		
		executeStep.performAction(SeleniumActions.Click, "","SelectTxnNumTable");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnsViewPrint",true);	
		
		CommonUtilities.delay(2000);
		
		executeStep.performAction(SeleniumActions.Click, "","SupplierReturnsViewPrint");
		verifications.verify(SeleniumVerifications.Appears, "","Supplier ReturnsReplacementsPage",true);	
		
	}
public void CloseTxnNo(){//added by asmita
		
		executeStep.performAction(SeleniumActions.Click, "","SelectTxnNumTable");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnsReplacementsClose",true);	
		
		CommonUtilities.delay(2000);
	
		executeStep.performAction(SeleniumActions.Click, "","SupplierReturnsReplacementsClose");
		verifications.verify(SeleniumVerifications.Appears, "","PharmacyStatusConfirmationScreen",true);	
		
	
}
}
