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
import reusableFunctions.inventory.ItemCategories;
import reusableFunctions.inventory.ItemMaster;
import reusableFunctions.inventory.StockEntryModule;
import reusableFunctions.inventory.SupplierDetails;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class DuplicateItemsInStockEntry {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	
	//@BeforeSuite
	//public void BeforeSuite(){
	  private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - DuplicateItemsInStockEntry";
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
		AutomationID = "Login - DuplicateItemsInStockEntry";
		DataSet = "PreReq_016";
				
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
		public void DuplicateItemsInStockEntryPreRequisite(){
			openBrowser();
			login();
			DataSet = "PreReq_016";
			AutomationID = "DuplicateItemsInStockEntry";
			List<String> lineItemIDs = null;
			System.out.println("DuplicateItemsInStockEntry - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			//1. Navigate to settings Page
			//1.In Supplier Masters, add 'Supplier_1' with Credit Period and Address --In Supplier Masters.
			
			navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");	
			executeStep.performAction(SeleniumActions.Click, "","AddNewSupplierLink");
			
			SupplierDetails	supplier = new SupplierDetails(executeStep, verifications);
			supplier.createSupplier();
			
			//2. Create a batch wise Item Category in Item Category Master (Settings ->STORES MASTERS -> Item Categories) and select following and keep remaining fields as it is.
		    //  * Select Identification = Batch No
			//	* Issue Type = Consumable 
			
			navigation.navigateToSettings(driver, "ItemCategoriesLink", "AddNewcategory");
			
			ItemCategories itemCat = new ItemCategories(executeStep, verifications);
			itemCat.AddItemcategory("Itemcategory", false);	  
			
			
		//3. Create a serial wise Item Category in Item Category Master (Settings ->STORES MASTERS -> Item Categories) and select following and keep remaining fields as it is.
		//  * Select Identification = Serial NO
		//  * Issue Type = Consumable 
					
			navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");
			
			ItemMaster itemMaster = new ItemMaster(executeStep, verifications);
			itemMaster.AddItem("ItemMaster");
	  }
		
	  //Create batch and serial items in item master mapping with 
	 // respective item category.
	  
			
	  
	  @Test
		public void DuplicateItemsInStockEntry(){
		  
			DataSet = "TS_016";
			AutomationID = "StockEntryWithPoStrictPOEntry";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test RosterWorkFlow - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			//1. Open stock entry screen and enter Supplier_1 
			 
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
		    
			//2. Enter the Invoice Number "1"
			//3. Click on “add new item” and ensure it popups “Item details”. 
			//4. Enter Item name, Batch/Serial No, MRP, Rate, Qty and click on “Add” button. 
			
			StockEntryModule stockentrydata=new StockEntryModule(executeStep, verifications);
			stockentrydata.AddSupplierName();
			stockentrydata.Addinvoicenumber();
			stockentrydata.addItemsToStocks("stockentryItem");
			stockentrydata.Save();
			
			//Add same item with same batch number again
			// Click on save button.
			
			stockentrydata.addItemsToStocks("stockentryItem");
			stockentrydata.Save();
			
	  }
}