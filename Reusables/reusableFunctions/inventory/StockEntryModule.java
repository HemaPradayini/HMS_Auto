package reusableFunctions.inventory;

import seleniumWebUIFunctions.KeywordSelectionLibrary;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.SiteNavigation;
import reusableFunctions.StockEntry;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class StockEntryModule extends StockEntry{
        
		KeywordSelectionLibrary executeStep;
		VerificationFunctions verifications;
	
		
		public StockEntryModule(){

		   }

		   /**
		    * Use this
		    * @param AutomationID
		    */
		public StockEntryModule(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
			   this.executeStep = execStep;
			   this.verifications = verifications;
		   }
		
		
	  public void AddSupplierName(){
		  
		 executeStep.performAction(SeleniumActions.Enter, "SupplierName","SupplierField");
		 verifications.verify(SeleniumVerifications.Appears, "","SupplierNameList",true);
			
		 executeStep.performAction(SeleniumActions.Click, "","SupplierNameList");
		 verifications.verify(SeleniumVerifications.Entered, "SupplierName","SupplierField",false);
		  
	  }
	  
	  public void Addinvoicenumber(){
		  
		  executeStep.performAction(SeleniumActions.Enter, "InvoiceNumber","StockEntryInvoiceNo");
		  verifications.verify(SeleniumVerifications.Entered, "InvoiceNumber","StockEntryInvoiceNo",true);
		  
	  }
	  
	  
	  public void selectStockType(){
		  executeStep.performAction(SeleniumActions.Select, "StockType","StockTypeDropDown");
		 verifications.verify(SeleniumVerifications.Selected, "StockType","StockTypeDropDown",true);
				 
		  }
	  
    public void StockEntryCSTType(){
		  
		  executeStep.performAction(SeleniumActions.Select, "TaxType","StockEntryTaxTypeField");
		  verifications.verify(SeleniumVerifications.Selected, "TaxType","StockEntryTaxTypeField",true);
		  
	  }

    public void StockEntryCSTRate(){
      
	  executeStep.performAction(SeleniumActions.Enter, "CSTRate","StockEntryCSTRateField");
	  verifications.verify(SeleniumVerifications.Entered, "CSTRate","StockEntryCSTRateField",true);
	  
    }
    
    public void StockEntryItemDiscount(){
        
  	  executeStep.performAction(SeleniumActions.Select, "InvDiscountType","InvDiscountTypeField");
  	  verifications.verify(SeleniumVerifications.Selected, "InvDiscountType","InvDiscountTypeField",true);
  	  
  	 executeStep.performAction(SeleniumActions.Enter, "InvDiscountValue","InvDiscountValueField");
 	  verifications.verify(SeleniumVerifications.Entered, "InvDiscountValue","InvDiscountValueField",true);
  	  
      }
    
    public void StockEntryRoundOff(){
        
    	  executeStep.performAction(SeleniumActions.Enter, "StockEntryOtherCharges","StockEntryOtherChargesField");
    	  verifications.verify(SeleniumVerifications.Entered, "StockEntryOtherCharges","StockEntryOtherChargesField",true);
    	  
    	 executeStep.performAction(SeleniumActions.Enter, "StockEntryRoundOff","StockEntryRoundOffField");
   	  verifications.verify(SeleniumVerifications.Entered, "StockEntryRoundOff","StockEntryRoundOffField",true);
    	  
        }
	  
    
    public void selectStatus(){
		  executeStep.performAction(SeleniumActions.Select, "StockEntryStatus","StockEntryStatusDropdown");
		 verifications.verify(SeleniumVerifications.Selected, "StockEntryStatus","StockEntryStatusDropdown",true);
				 
		  }
    
	  public void addItemsToStocks(String lineItemId){
		  executeStep.performAction(SeleniumActions.Click, "","AddItemButton");
			verifications.verify(SeleniumVerifications.Appears, "","DialogBlock",true);
			EnvironmentSetup.LineItemIdForExec = lineItemId;
			EnvironmentSetup.lineItemCount =0;
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

			EnvironmentSetup.UseLineItem = true;
			DbFunctions dbFunction = new DbFunctions();
			int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
			System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
			for(int i=1; i<=rowCount; i++){	
				EnvironmentSetup.UseLineItem = true;
				
				executeStep.performAction(SeleniumActions.Enter, "StockEntryItem","StockEntryPageAddItem");
				verifications.verify(SeleniumVerifications.Appears, "","SalesEntryItemDropDown",false);

				executeStep.performAction(SeleniumActions.Click, "","SalesEntryItemDropDown");
				verifications.verify(SeleniumVerifications.Entered, "StockEntryItem","StockEntryPageAddItem",false);

				CommonUtilities.delay();
				
				executeStep.performAction(SeleniumActions.Enter, "StockEntryBatchNo","BatchNoField");
				verifications.verify(SeleniumVerifications.Entered, "StockEntryBatchNo","BatchNoField",true);

				
				this.executeStep.getDriver().findElement(By.xpath("//input[@name='exp_dt_mon']")).sendKeys("12");
				this.executeStep.getDriver().findElement(By.xpath("//input[@name='exp_dt_year']")).sendKeys("20");

				executeStep.performAction(SeleniumActions.Enter, "StockEntryMRP","StockEntryMRPField");
				verifications.verify(SeleniumVerifications.Entered, "StockEntryMRP","StockEntryMRPField",false);
				
				executeStep.performAction(SeleniumActions.Enter, "StockEntryRate","StockEntryRateField");
				verifications.verify(SeleniumVerifications.Entered, "StockEntryRate","StockEntryRateField",false);
				
				executeStep.performAction(SeleniumActions.Enter, "StockEntryQuantity","QuantityField");
				verifications.verify(SeleniumVerifications.Entered, "StockEntryQuantity","QuantityField",false);
				
				executeStep.performAction(SeleniumActions.Enter, "StockEntryBonus","StockEntryBonusField");
				verifications.verify(SeleniumVerifications.Entered, "StockEntryBonus","StockEntryBonusField",true);
				
				executeStep.performAction(SeleniumActions.Enter, "StockEntryItemtype","ItemDiscountTypeDropdown");
				verifications.verify(SeleniumVerifications.Entered, "StockEntryItemtype","ItemDiscountTypeDropdown",true);
				
				executeStep.performAction(SeleniumActions.Enter, "StockEntryCEDPercentage","StockEntryCEDPercentageField");
				verifications.verify(SeleniumVerifications.Entered, "StockEntryCEDPercentage","StockEntryCEDPercentageField",true);
				
				executeStep.performAction(SeleniumActions.Enter, "StockEntryDiscount","StockEntryDiscountField");
				verifications.verify(SeleniumVerifications.Entered, "StockEntryDiscount","StockEntryDiscountField",true);

			   executeStep.performAction(SeleniumActions.Click, "","StockEntryScreenAddButton");
				verifications.verify(SeleniumVerifications.Appears, "","DialogBlock",true);
				
				EnvironmentSetup.lineItemCount++;

			}
			EnvironmentSetup.lineItemCount =0;
			EnvironmentSetup.UseLineItem = false;
			executeStep.performAction(SeleniumActions.Click, "","StockEntryScreenItemClose");
			
		  
		  
	  }
	  
    public void Save(){
		  
    	executeStep.performAction(SeleniumActions.Click, "","SaveButton");
    	verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","GNRNumberVerification",true);
		  
	  }
	  
    public void ItemDiscountType(){
		  
		  executeStep.performAction(SeleniumActions.Enter, "StockEntryDiscountItemtype","ItemDiscountTypeDropdown");
		  verifications.verify(SeleniumVerifications.Entered, "StockEntryDiscountItemtype","ItemDiscountTypeDropdown",true);
		  
    }	
		
		
public void ItemDiscountValue(){
	
	executeStep.performAction(SeleniumActions.Enter, "StockEntryItemDiscountvalue","ItemDiscountValueTextbox");
	  verifications.verify(SeleniumVerifications.Entered, "StockEntryItemDiscountvalue","ItemDiscountValueTextbox",true);
	
}

public void addItemsToClickonPurchaseDetails(String lineItemId){
	  executeStep.performAction(SeleniumActions.Click, "","AddItemButton");
		verifications.verify(SeleniumVerifications.Appears, "","DialogBlock",true);
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			
			executeStep.performAction(SeleniumActions.Enter, "StockEntryItem","StockEntryPageAddItem");
			verifications.verify(SeleniumVerifications.Appears, "","SalesEntryItemDropDown",false);

			executeStep.performAction(SeleniumActions.Click, "","SalesEntryItemDropDown");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryItem","StockEntryPageAddItem",false);

			CommonUtilities.delay();
			
			executeStep.performAction(SeleniumActions.Click, "","PurchaseDetailsLink");
	    	verifications.verify(SeleniumVerifications.Appears, "","VerifyPurchasedialogbox",true);
		}
		
}
		public void addItemsToStocksWithDefaultDiscountValue(String lineItemId){
			executeStep.performAction(SeleniumActions.Click, "","AddItemButton");
		verifications.verify(SeleniumVerifications.Appears, "","DialogBlock",true);
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			
			executeStep.performAction(SeleniumActions.Enter, "StockEntryItem","StockEntryPageAddItem");
			verifications.verify(SeleniumVerifications.Appears, "","SalesEntryItemDropDown",false);

			executeStep.performAction(SeleniumActions.Click, "","SalesEntryItemDropDown");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryItem","StockEntryPageAddItem",false);

			CommonUtilities.delay();
			
			executeStep.performAction(SeleniumActions.Enter, "StockEntryBatchNo","BatchNoField");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryBatchNo","BatchNoField",true);

			
			this.executeStep.getDriver().findElement(By.xpath("//input[@name='exp_dt_mon']")).sendKeys("12");
			this.executeStep.getDriver().findElement(By.xpath("//input[@name='exp_dt_year']")).sendKeys("20");

			executeStep.performAction(SeleniumActions.Enter, "StockEntryMRP","StockEntryMRPField");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryMRP","StockEntryMRPField",false);
			
			executeStep.performAction(SeleniumActions.Enter, "StockEntryRate","StockEntryRateField");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryRate","StockEntryRateField",false);
			
			executeStep.performAction(SeleniumActions.Enter, "StockEntryQuantity","QuantityField");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryQuantity","QuantityField",false);
			
			executeStep.performAction(SeleniumActions.Enter, "StockEntryBonus","StockEntryBonusField");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryBonus","StockEntryBonusField",true);
			
			executeStep.performAction(SeleniumActions.Enter, "StockEntryItemtype","ItemDiscountTypeDropdown");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryItemtype","ItemDiscountTypeDropdown",true);
			
			
			//executeStep.performAction(SeleniumActions.Enter, "StockEntryDiscount","StockEntryDiscountField");
			//verifications.verify(SeleniumVerifications.Entered, "StockEntryDiscount","StockEntryDiscountField",true);

		   executeStep.performAction(SeleniumActions.Click, "","StockEntryScreenAddButton");
			verifications.verify(SeleniumVerifications.Appears, "","DialogBlock",true);
			
			EnvironmentSetup.lineItemCount++;

		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
		executeStep.performAction(SeleniumActions.Click, "","StockEntryScreenItemClose");
}
		public String store(){
	    	
	    	
	    	WebElement we = this.executeStep.getDriver().findElement(By.xpath("//div[@class='fltL'][2]"));
				
			String expression = we.getText();
			String[] list = expression.split("\\s");
	    	int listcount = list.length; 
	    	int number = listcount-1;
	    	String gRN = list[1];
	         System.out.println(gRN);
	         return gRN;
	    }
		
		
}
		
		
	
		
		
		
		
		

		  
		  
		




