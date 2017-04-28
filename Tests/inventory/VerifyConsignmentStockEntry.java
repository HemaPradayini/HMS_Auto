package inventory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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
import reusableFunctions.inventory.PackageUOM;
import reusableFunctions.inventory.StockEntryModule;
import reusableFunctions.inventory.SupplierDetails;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class VerifyConsignmentStockEntry {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus = "Fail";
	//@BeforeSuite
	//public void BeforeSuite(){
	  private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - VerifyConsignmentStockEntry";
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
		AutomationID = "Login - VerifyConsignmentStockEntry";
		DataSet = "PreReq_019";
				
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
	  
	  @BeforeClass
			public void VerifyConsignmentStockEntryPreRequisite(){
				openBrowser();
				login();
				DataSet = "PreReq_019";
				AutomationID = "VerifyConsignmentStockEntry";
				List<String> lineItemIDs = null;
				System.out.println("Inside Test VerifyConsignmentStockEntry - Before Navigation");
				
				executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
				verifications = new VerificationFunctions(driver, AutomationID, DataSet);

			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);

			 //1. Set stock Entry with PO

     /*       navigation.navigateToSettings(driver, "GenericPreferencesLink", "GenericPreferencesPage");     
      GenericPreferences genericPref= new GenericPreferences(executeStep, verifications);
        genericPref.SetStockEntryWithPO();
       genericPref.save();
       
		/*	1. -> Create a batch Item Category in Item Category Master under stores master 
			Click on Add New Category
			  * Enter Category Name        
			  * Select Identification = Batch No
			  * Issue Type = Consumable 
			2. -> Create a serial Category in Item Category Master under stores master
			  Click on Add New Category
			  * Enter Category Name        
			  * Select Identification = Serial NO
			  * Issue Type = Consumable 
			
			
			navigation.navigateToSettings(driver, "ItemCategoriesLink", "AddNewcategory");
			
			ItemCategories itemCat = new ItemCategories(executeStep, verifications);
			itemCat.AddItemcategory("Itemcategory", false);
			
			//3.Settings --> store masters-->Package UOM --> Click on Add Package UOM --> Enter Package UOM -10,Unit  
			//UOM - 1 and Pkg Size = 10
			navigation.navigateToSettings(driver, "PackageUOM", "PackageUOMHeader");
	        PackageUOM uom = new PackageUOM(executeStep, verifications);
			uom.AddPackageUOMItem("pakageUOMItem");
			
			/*3. Create items in Item Master -->store master-> Click on add new item
			4. Enter Item Name say item1, Shorter Name, Manufacturer.
			5.Enter an Item Name say item1(unit size 1), Shorter Name, Manufacturer, select Category say Batch No, Service Sub Group = general, Tax Basis = MRP Based(with bonus) and Tax(%) = 5.5 and click on save.
			-->  Enter an Item Name say item2(package size 10), Shorter Name, Manufacturer, select Category say Batch No, Unit UOM = 10,Service Sub Group = general, Tax Basis = MRP Based(with bonus) and Tax(%) = 5.5 and click on save.
			--> Enter an Item Name say item3(serial item), Shorter Name, Manufacturer, select Category say Serial NO , Service Sub Group = general, Tax Basis = CP Based(with bonus) and Tax(%) = 5.5 and click on save.
		
			navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");	
			ItemMaster itemMaster = new ItemMaster(executeStep, verifications);
			itemMaster.AddItem("ItemMaster");
			
			//6. In Supplier Masters, add 'Supplier_1' with Credit Period and Address
			// In Supplier Masters.
			navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");	
			 executeStep.performAction(SeleniumActions.Click, "","AddNewSupplierLink");
			    
			SupplierDetails	supplier = new SupplierDetails(executeStep, verifications);
			supplier.createSupplier();
			*/
	  }
	  @Test
	  public void verifyConsignmentStockEntryTest(){
		  
		  
			DataSet = "TS_019";
			AutomationID = "verifyConsignmentStockEntryTest";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test verifyConsignmentStockEntryTest - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
				
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
			
			
		    StockEntryModule stockentry = new StockEntryModule(executeStep,verifications);
		    stockentry.AddSupplierName();
		    stockentry.Addinvoicenumber();
		    stockentry.selectStockType();
		    stockentry.addItemsToStocks("stockentryItem");
		    stockentry.Save();
		    
		    testCaseStatus = "Pass";
			
			  System.out.println("Module Test TS_019 Completed");
			  
	  }
		@AfterClass
		public void closeBrowser(){
			String delimiter = " :: ";
			//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
			//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
			ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "VerifyConsignmentStockEntry", null, "", "", testCaseStatus);
		//	driver.close();
		}  
}
