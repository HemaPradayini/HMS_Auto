package reusableFunctions.inventory;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PurchaseOrder {
	
	WebDriver driver;
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	String AutomationID;
	String DataSet;

	public PurchaseOrder() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PurchaseOrder(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void raisePurchaseOrder()
    {
		DbFunctions dbFunction = new DbFunctions();
		executeStep.performAction(SeleniumActions.Select, "Store", "RaisepurchaseStoreIdField");
		verifications.verify(SeleniumVerifications.Selected, "Store","RaisepurchaseStoreIdField",true);
		
		executeStep.performAction(SeleniumActions.Enter, "SupplierName", "RaisepurchaseSupplierNameField");
		verifications.verify(SeleniumVerifications.Appears,"","SupplierResultList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","SupplierResultList");
		verifications.verify(SeleniumVerifications.Entered, "SupplierName","RaisepurchaseSupplierNameField",false);
  
	    executeStep.performAction(SeleniumActions.Select, "TaxType", "PurchaseOrderTaxType");
		verifications.verify(SeleniumVerifications.Selected, "TaxType","PurchaseOrderTaxType",true);
		
		this.executeStep.getDriver().findElement(By.xpath("//input[@name='main_cst_rate']")).sendKeys(Keys.CONTROL,"a",Keys.BACK_SPACE);
		executeStep.performAction(SeleniumActions.Enter, "CSTRate", "PurchaseOrderCSTRate");
	    verifications.verify(SeleniumVerifications.Entered, "CSTRate","PurchaseOrderCSTRate",true);
	    
         dbFunction.storeDate(this.executeStep.getDataSet(), "RaisepurchaseStorePoDate","C",0);
	    
	    executeStep.performAction(SeleniumActions.Enter, "RaisepurchaseStorePoDate", "RaisepurchaseStorePoDateField");
	    verifications.verify(SeleniumVerifications.Entered, "RaisepurchaseStorePoDate","RaisepurchaseStorePoDateField",false);
		
		
    }
	
	
	
	public void addItemsToPurchaseOrder(String lineItemId)
    {
		
		
		executeStep.performAction(SeleniumActions.Click, "","RaisePurchaseOrderAddItem");
		//verifications.verify(SeleniumVerifications.Appears, "","DialogBlock",true);           //object needs to b changed
		
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			
			executeStep.performAction(SeleniumActions.Enter, "PurchaseOrderAddItem","PurchaseOrderAddItemField");
			verifications.verify(SeleniumVerifications.Appears, "","PurchaseOrderItemDropDown",true);
			
			executeStep.performAction(SeleniumActions.Click, "","PurchaseOrderItemDropDown");
			verifications.verify(SeleniumVerifications.Entered, "PurchaseOrderAddItem","PurchaseOrderAddItemField",false);
			
			CommonUtilities.delay();
					
		    executeStep.performAction(SeleniumActions.Enter, "PurchaseOrderRate", "PurchaseOrderRateField");
		    verifications.verify(SeleniumVerifications.Entered, "PurchaseOrderRate","PurchaseOrderRateField",false);
		    
		    CommonUtilities.delay();
		    
		    executeStep.performAction(SeleniumActions.Enter, "PurchaseOrderMRP", "PurchaseOrderMRPField");
		    verifications.verify(SeleniumVerifications.Entered, "PurchaseOrderMRP","PurchaseOrderMRPField",true);
		    
		    CommonUtilities.delay();
		    this.executeStep.getDriver().findElement(By.xpath("//div[@id='detaildialog']//input[@name='discount_per']")).sendKeys(Keys.CONTROL,"a",Keys.BACK_SPACE);
            executeStep.performAction(SeleniumActions.Enter, "PurchaseOrderDiscount", "PurchaseOrderDiscountField");
			verifications.verify(SeleniumVerifications.Entered, "PurchaseOrderDiscount","PurchaseOrderDiscountField",true);
			
		    
		    CommonUtilities.delay();
		    executeStep.performAction(SeleniumActions.Enter, "PurchaseOrderQty", "PurchaseOrderQtyField");
		    verifications.verify(SeleniumVerifications.Entered, "PurchaseOrderQty","PurchaseOrderQtyField",false);
		    
		    CommonUtilities.delay();
		    executeStep.performAction(SeleniumActions.Enter, "PurchaseOrderBonus", "PurchaseOrderBonusField");
		    verifications.verify(SeleniumVerifications.Entered, "PurchaseOrderBonus","PurchaseOrderBonusField",true);

		   
		    CommonUtilities.delay();
		    executeStep.performAction(SeleniumActions.Enter, "PurchaseOrderCEDPercentage", "PurchaseOrderCEDPercentageField");
		    verifications.verify(SeleniumVerifications.Entered, "PurchaseOrderCEDPercentage","PurchaseOrderCEDPercentageField",true);
		    
		    executeStep.performAction(SeleniumActions.Click, "","PurchaseOrderAddButton");
		   // verifications.verify(SeleniumVerifications.Appears, "","DialogBlock",false);
			
			
			EnvironmentSetup.lineItemCount++;

		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
		try{
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
		}catch(Exception e){
			System.out.println("This is not a duplicate entry" +e);
		}
		
		executeStep.performAction(SeleniumActions.Click, "","PurchaseOrderDialogClosebutton");

    }
	
	public void Save(){
		
		executeStep.performAction(SeleniumActions.Click, "","PurchaseOrderSaveButton");
	    verifications.verify(SeleniumVerifications.Appears, "","EditPurchaseOrderHeader",false);
	    CommonUtilities.delay(2000);
	    executeStep.performAction(SeleniumActions.Store, "PurchaseOrderNo", "PurchaseOrderNo");
    }
	
   public void SaveAndValidate(){
		
		executeStep.performAction(SeleniumActions.Click, "","PurchaseOrderSaveAndValidate");
	    verifications.verify(SeleniumVerifications.Appears, "","EditPurchaseOrderHeader",false);
    
	    executeStep.performAction(SeleniumActions.Store, "PurchaseOrderNo", "PurchaseOrderNo");
	    
    }
	
	public void viewEditPO(){
		
		executeStep.performAction(SeleniumActions.Click, "","POResultTable");
		verifications.verify(SeleniumVerifications.Appears, "","POListResultViewEditMenu",true);	
		
		CommonUtilities.delay(2000);
		
		executeStep.performAction(SeleniumActions.Click, "","POListResultViewEditMenu");
		verifications.verify(SeleniumVerifications.Appears, "","EditPurchaseOrderHeader",true);	
		
		
	}

	public void saveAndApprove(){
		
		executeStep.performAction(SeleniumActions.Click, "","SaveAndApproveButton");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		CommonUtilities.delay(2000);
	    verifications.verify(SeleniumVerifications.Appears, "","ViewPurchaseOrderPage",false);
	   // executeStep.performAction(SeleniumActions.Store, "PurchaseOrderNo", "PurchaseOrderNo");
    	
		
	}

	public void termsAndConditions(){         // added 11/4/
		
		 executeStep.performAction(SeleniumActions.Enter, "SupplierTermComments", "SupplierTermTextBox");
			verifications.verify(SeleniumVerifications.Entered, "SupplierTermComments","SupplierTermTextBox",true);
			
		    
		    executeStep.performAction(SeleniumActions.Enter, "HospitalTermsComments", "HospitalTermsTextBox");
		    verifications.verify(SeleniumVerifications.Entered, "HospitalTermsComments","HospitalTermsTextBox",false);
		    
		    executeStep.performAction(SeleniumActions.Enter, "DeliveryInstructionsComments", "DeliveryInstructionsTextBox");
		    verifications.verify(SeleniumVerifications.Entered, "DeliveryInstructionsComments","DeliveryInstructionsTextBox",true);
		
	}
	
	
		public void ItemDeletePO(){
		
		executeStep.performAction(SeleniumActions.Click, "","ItemDeletePOButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditPurchaseOrderHeader",true);
		
	}
	public void ValidatePO(){
		
		executeStep.performAction(SeleniumActions.Click, "","POResultTable");
		verifications.verify(SeleniumVerifications.Appears, "","POListResultValidatePO",true);	
		
		CommonUtilities.delay(2000);
		
		executeStep.performAction(SeleniumActions.Click, "","POListResultValidatePO");
		verifications.verify(SeleniumVerifications.Appears, "","ValidatePOHeader",true);	
	}

		public void ValidatePOSave(){
		
			executeStep.performAction(SeleniumActions.Click, "","ValidatePOSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "","POListPage",false);
	
	}
		public void PurchaseOrderStatus(){
		 
			executeStep.performAction(SeleniumActions.Select, "PurchaseOrderStatus","PurchaseOrderStatusdd");
			verifications.verify(SeleniumVerifications.Selected, "PurchaseOrderStatus","PurchaseOrderStatusdd",true);
				 
	}
}
