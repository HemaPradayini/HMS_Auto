package inventory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import genericFunctions.CommonUtilities;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.Login;
import reusableFunctions.SiteNavigation;
import reusableFunctions.inventory.ItemCategories;
import reusableFunctions.inventory.ItemMaster;
import reusableFunctions.inventory.PurchaseOrder;
import reusableFunctions.inventory.StockEntryModule;
import reusableFunctions.inventory.SupplierDetails;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class InventoryPurchaseCpBasedWithBonusAndInvoiceNumber {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus="FAIL";

	//@BeforeSuite
	//public void BeforeSuite(){
	  private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - InventoryPurchaseCpBasedWithBonusAndInvoiceNumber";
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

	// @BeforeMethod
	//public void BeforeTest(){
	  private void login(){
		AutomationID = "Login - InventoryPurchaseCpBasedWithBonusAndInvoiceNumber";
		DataSet = "PreReq_022";
				
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
	}
	  
	  @BeforeClass
		public void InventoryPurchaseCpBasedWithBonusAndInvoiceNumberPreRequisite(){
			openBrowser();
			login();
			DataSet = "PreReq_022";
			AutomationID = "InventoryPurchaseCpBasedWithBonusAndInvoiceNumberPreRequisite";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test InventoryPurchaseCpBasedWithBonusAndInvoiceNumberPreRequisite - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			//1. -> Create a batch Item Category in Item Category Master under stores master 
			 //Click on Add New Category * Enter Category Name  * Select Identification = Serial NO * Issue Type = Consumable -> Click on save button.
			navigation.navigateToSettings(driver, "ItemCategoriesLink", "AddNewcategory");
			ItemCategories itemCat = new ItemCategories(executeStep, verifications);
			itemCat.AddItemcategory("Itemcategory", false);
			
			
			//3. Create items in Item Master -->store master-> Click on add new item//4. Enter Item Name say item1, Shorter Name, Manufacturer.//5. Select Category say Batch No/Serial NO, Service Sub Group = general,
			//Tax Basis = MRP Based(with bonus) and Tax(%) = 5.5
			navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");
			ItemMaster itemMaster =new ItemMaster(executeStep, verifications);
			itemMaster.AddItem("ItemMaster");
			
			//6. In Supplier Masters, add 'Supplier_1' with Credit Period and Address --In Supplier Masters.
			navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");	
			executeStep.performAction(SeleniumActions.Click, "","AddNewSupplierLink");
			    
			SupplierDetails	supplier = new SupplierDetails(executeStep, verifications);
			supplier.createSupplier();
			
			
	  }
	  
	  @Test()
		public void InventoryPurchaseCpBasedWithBonusAndInvoice(){
		  
			DataSet = "TS_022";
			AutomationID = "InventoryPurchaseCpBasedWithBonus";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test InventoryPurchaseCpBasedWithBonus - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
			navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
			StockEntryModule stockentry = new StockEntryModule(executeStep, verifications);
			stockentry.addItemsToStocks("stockentryItem");
			
			testCaseStatus="PASS";
			  System.out.println("InventoryPurchaseCpBasedWithBonusAndInvoice workflow");
		  }
		  
		  @AfterTest
			public void closeBrowser(){
		  	reporter.UpdateTestCaseReport(AutomationID, DataSet, " InventoryPurchaseCpBasedWithBonusAndInvoice workflow", null, "", "", testCaseStatus);
		    driver.close();
			}
		  

	  }
	
