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
import reusableFunctions.inventory.Department;
import reusableFunctions.inventory.ItemCategories;
import reusableFunctions.inventory.ItemMaster;
import reusableFunctions.inventory.StockEntryModule;
import reusableFunctions.inventory.Stores;
import reusableFunctions.inventory.SupplierDetails;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PurchaseDetails {
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
		AutomationID = "Login - PurchaseDetails";
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
		AutomationID = "Login - PurchaseDetails";
		DataSet = "PreReq_051";
				
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
		public void PurchaseDetailsPreRequisite(){
			openBrowser();
			login();
			DataSet = "PreReq_051";
			AutomationID = "PurchaseDetails";
			List<String> lineItemIDs = null;
			System.out.println("PurchaseDetails - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			//1. Navigate to settings Page -->go to stores and Create a new Store
			
			
			navigation.navigateToSettings(driver, "StoresLink", "StoreMasterPage");
			
			Stores store= new Stores(executeStep, verifications);
			store.createstore();

			//2. Create a batch wise Item Category in Item Category Master (Settings ->STORES MASTERS -> Item Categories) and select following and keep remaining fields as it is.
		    //  * Select Identification = Batch No
			//	* Issue Type = Consumable 
			
			navigation.navigateToSettings(driver, "ItemCategoriesLink", "AddNewcategory");
			
			ItemCategories itemCat = new ItemCategories(executeStep, verifications);
			itemCat.AddItemcategory("Itemcategory", false);
			
			//2.In Supplier Masters, add 'Supplier_1' with Credit Period and Address --In Supplier Masters.
			
			navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");	
			
			executeStep.performAction(SeleniumActions.Click, "","AddNewSupplierLink");
			verifications.verify(SeleniumVerifications.Appears, "","AddSupplierDetailsPage",true);
			
			SupplierDetails	supplier = new SupplierDetails(executeStep, verifications);
			supplier.createSupplier();
			
			//Create items in Item Master -->store master-> Click on add new item
			// Enter Item Name say item1, Shorter Name, Manufacturer.
						
				navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");
				
				ItemMaster itemMaster = new ItemMaster(executeStep, verifications);
				itemMaster.AddItem("ItemMaster");
				
				//Navigate to stockEntry module
				
				SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
				navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
				
				StockEntryModule stockentry=new StockEntryModule(executeStep, verifications);
				stockentry.AddSupplierName();
				stockentry.Addinvoicenumber();
				stockentry.addItemsToStocks("stockentryItem");
				stockentry.Save();
				
	  }
	  

	  @Test()
		public void PurchaseDetails(){
		  
			DataSet = "TS_051";
			AutomationID = "PurchaseDetails";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test ItemDiscountType - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
			
			StockEntryModule stockentry=new StockEntryModule(executeStep, verifications);
			stockentry.AddSupplierName();
			stockentry.Addinvoicenumber();
			stockentry.addItemsToClickonPurchaseDetails("stockentryItem");
	    	
			
}
}
