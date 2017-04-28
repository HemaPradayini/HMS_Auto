package reusableFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class StockEntry {

	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	
	public StockEntry(){

	   }

	   /**
	    * Use this
	    * @param AutomationID
	    */
	public StockEntry(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		   this.executeStep = execStep;
		   this.verifications = verifications;
	   }
	
	public void searchMRNo(){
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("StockEntryMRNoField", "StockEntryMRNoList", "StockEntrySearchButton", "StockEntryResultList");		       //added by abhishek
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);            
		executeStep.performAction(SeleniumActions.Accept, "","Framework");                 
	}
	
	
	
	
	public void addItemsToStocks(String lineItemId)
    {
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
            dbFunction.storeDate(this.executeStep.getDataSet(), "StockEntryBatchNo","C",0);
			
			executeStep.performAction(SeleniumActions.Enter, "StockEntryBatchNo","BatchNoField");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryBatchNo","BatchNoField",true);

			CommonUtilities.delay();
			this.executeStep.getDriver().findElement(By.xpath("//input[@name='exp_dt_mon']")).sendKeys("12");
			this.executeStep.getDriver().findElement(By.xpath("//input[@name='exp_dt_year']")).sendKeys("20");
			
			/*executeStep.performAction(SeleniumActions.Enter, "ExpiryMonth","ExpiryMonthField");
			verifications.verify(SeleniumVerifications.Entered, "ExpiryMonth","ExpiryMonthField",false);
			
			executeStep.performAction(SeleniumActions.Enter, "ExpiryYear","ExpiryYearField");
			verifications.verify(SeleniumVerifications.Entered, "ExpiryYear","ExpiryYearField",false);*/
				
			executeStep.performAction(SeleniumActions.Enter, "StockEntryQuantity","QuantityField");
			verifications.verify(SeleniumVerifications.Entered, "StockEntryQuantity","QuantityField",false);

			verifications.verify(SeleniumVerifications.Appears, "","DialogBlock",true);
			//CommonUtilities.delay();
			executeStep.performAction(SeleniumActions.Click, "","StockEntryScreenAddButton");
			verifications.verify(SeleniumVerifications.Appears, "","DialogBlock",true);
			//CommonUtilities.delay();
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			EnvironmentSetup.lineItemCount++;

		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
		executeStep.performAction(SeleniumActions.Click, "","StockEntryScreenItemClose");

    }
	
	

	  
	  
	

}
