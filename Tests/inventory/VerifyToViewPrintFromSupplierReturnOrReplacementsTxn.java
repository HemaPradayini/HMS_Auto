package inventory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import GenericFunctions.CommonUtilities;
import GenericFunctions.EnvironmentSetup;
import GenericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.Login;
import reusableFunctions.SiteNavigation;
import reusableFunctions.inventory.GenericPreferences;
import reusableFunctions.inventory.ItemCategories;
import reusableFunctions.inventory.ItemMaster;
import reusableFunctions.inventory.StockEntryModule;
import reusableFunctions.inventory.SupplierDetails;
import reusableFunctions.inventory.SupplierReturn;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class VerifyToViewPrintFromSupplierReturnOrReplacementsTxn {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	
	//@BeforeSuite
	//public void BeforeSuite(){
	  private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - VerifyToViewPrintFromSupplierReturnOrReplacementsTxn";
		reporter = new ReportingFunctions();
		System.out.println("AfterReport");
		
		initDriver = new KeywordExecutionLibrary();
		System.out.println("AfterInitDriver");

		try{
			System.out.println("EnvironmentSetUp is ::" + EnvironmentSetup.URLforExec);
			driver = initDriver.LaunchApp("Chrome",  EnvironmentSetup.URLforExec);
			System.out.println("Am here");
		}catch(Exception e){
			e.printStackTrace();
		}
		Assert.assertFalse(driver==null, "Browser Not Initialized - Check Log File for Errors");
		initDriver = null;
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		verifications.verify(SeleniumVerifications.Appears, "", "LoginScreenHospital_Est", false);
		verifications = null;
		
	}
	
	//@BeforeMethod
	//public void BeforeTest(){
	  private void login(){
		AutomationID = "Login - VerifyToViewPrintFromSupplierReturnOrReplacementsTxn";
		DataSet = "PreReq_Inv_TS_057";
				
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);

	  }
	  
	  @BeforeMethod
		public void VerifyValidatingPOFromValidatePOScreenPreRequisite(){
			openBrowser();
			login();
			DataSet = "PreReq_Inv_TS_057";
			AutomationID = "VerifyToViewPrintFromSupplierReturnOrReplacementsTxn";
			List<String> lineItemIDs = null;
			System.out.println("VerifyToViewPrintFromSupplierReturnOrReplacementsTxn - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
		//Create a batch wise Item Category in Item Category Master (Settings ->STORES MASTERS -> Item Categories) and select following and keep remaining fields as it is.
		    //  * Select Identification = Batch No
			//	* Issue Type = Consumable 
			
			navigation.navigateToSettings(driver, "ItemCategoriesLink", "AddNewcategory");
			
			ItemCategories itemCat = new ItemCategories(executeStep, verifications);
			itemCat.AddItemcategory("Itemcategory", false);
			
			//Create items in Item Master -->store master-> Click on add new item 
			// Enter Item Name say item1, Shorter Name, Manufacturer. 
			
			navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");
			
			ItemMaster itemMaster = new ItemMaster(executeStep, verifications);
			itemMaster.AddItem("ItemMaster");
			
			//In Supplier Masters, add 'Supplier_1' with Credit Period and Address --In Supplier Masters.
			
			navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");	
			
			executeStep.performAction(SeleniumActions.Click, "","AddNewSupplierLink");
			verifications.verify(SeleniumVerifications.Appears, "","AddSupplierDetailsPage",true);
			
			SupplierDetails	supplier = new SupplierDetails(executeStep, verifications);
			supplier.addSupplier();
			supplier.createMultipleSupplier("Supplier");
			
			//Open stock entry screen and enter Supplier_1 .Enter the Invoice Number "TS_058"
			// Click on “add new item” and ensure it popups “Item details”
			
			navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
			
			StockEntryModule stockentry = new StockEntryModule(executeStep,verifications);
		    stockentry.AddSupplierName();
		    stockentry.Addinvoicenumber();
		    stockentry.addItemsToStocks("stockentryItem");
		    stockentry.Save();
		    stockentry.store();
		    
		    // Set Allow decimals for qty(Supplier) to Yes and Return Against 
			//	    Specific Supplier to Yes in generic preferences.
				   
		    navigation.navigateToSettings(driver, "GenericPreferencesLink", "GenericPreferencesPage");
			GenericPreferences genPref = new GenericPreferences(executeStep,verifications); 
			genPref.allowDecimalForQtySupplier();
			genPref.returnAgainstSpecificSupplier();
			genPref.save();
			
			//Click on Supplier Returns/Replacements -> Procurement 
		    
			navigation.navigateTo(driver,"SupplierReturnsReplacementLink" ,"SupplierReturnsReplacementPage" );   
			
			//Click on "Raise New Supplier Return " link .
			
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			executeStep.performAction(SeleniumActions.Click, "", "RaiseNewSupplierReturnLink");
			verifications.verify(SeleniumVerifications.Appears, "", "SupplierReturnPage", false);
			
			
			//Ensure that supplier returns screen is opened . 
			// Select the qty in "Unit UOM". 
			SupplierReturn suppReturn = new SupplierReturn(executeStep,verifications);
			suppReturn.selectPackageUOM();
			
			//Enter the return details such as "supplier, store, return type, remarks, gate pass(check box)". 
			suppReturn.ReturnDetails();
			suppReturn.gatePassCheckBox();
			
			//Click on "add item" button and ensure that it displays item details popup. 
			
			suppReturn.addReturnItem("SupplyReturn",false);
			
			//Enter the item name and select the available batch/ serial no. 
			// Enter the "return qty" and click on "save" button.
			// Click on "return" button.
			//Supplier returns/ replacement list screen should be opened.
			
			suppReturn.clickReturn();
	  }
			
			
			 @Test
				public void VerifyToViewPrintFromSupplierReturnOrReplacementsTxn(){
				 
				 DataSet = "Inv_TS_057";
					AutomationID = "VerifyToViewPrintFromSupplierReturnOrReplacementsTxn";
					List<String> lineItemIDs = null;
					System.out.println("VerifyToViewPrintFromSupplierReturnOrReplacementsTxn - Before Navigation");
					
					executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
					verifications = new VerificationFunctions(driver, AutomationID, DataSet);
					

			 //Select the txn.No and click on "view print" option.
					
				SupplierReturn suppReturn = new SupplierReturn(executeStep,verifications);	
				suppReturn.StoreTxnNo();
				suppReturn.SearchTxnNo();
				suppReturn.ViewPrintTxnNo();
}
}
