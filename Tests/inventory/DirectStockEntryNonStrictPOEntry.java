package inventory;

import java.util.List;

import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import GenericFunctions.CommonUtilities;
import GenericFunctions.EnvironmentSetup;
import GenericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.Login;
import reusableFunctions.Order;
import reusableFunctions.SiteNavigation;
import reusableFunctions.inventory.GenericPreferences;
import reusableFunctions.inventory.ItemCategories;
import reusableFunctions.inventory.ItemMaster;
import reusableFunctions.inventory.MiscellaneousSettings;
import reusableFunctions.inventory.PackageUOM;
import reusableFunctions.inventory.PaymentTerms;
import reusableFunctions.inventory.PurchaseOrder;
import reusableFunctions.inventory.RolesAndUsers;
import reusableFunctions.inventory.StockEntryModule;
import reusableFunctions.inventory.SupplierDetails;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class DirectStockEntryNonStrictPOEntry {
	
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
			AutomationID = "Login - DirectStockEntryNonStrictPOEntry";
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
		AutomationID = "Login - DirectStockEntryNonStrictPOEntry";
		DataSet = "PreReq_017";
				
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		//executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	  
	@BeforeClass
	 public void DirectStockEntryNonStrictPOEntryPreRequisite(){
			openBrowser();
			login();
			DataSet = "PreReq_017";
			AutomationID = "DirectStockEntryNonStrictPOEntryPreRequisite";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test DirectStockEntryNonStrictPOEntryPreRequisite - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateToSettings(driver, "RolesAndUsersLink", "RolesAndUsersPage");
			
			RolesAndUsers roles = new RolesAndUsers(executeStep,verifications);
			roles.SearchUser();
			roles.EditUser();
			roles.allowDirectStockEntry(); 
			
			
			

          
            //1. Set stock Entry with PO
            navigation.navigateToSettings(driver, "GenericPreferencesLink", "GenericPreferencesPage");     
      GenericPreferences genericPref= new GenericPreferences(executeStep, verifications);
        genericPref.SetStockEntryWithPO();
       genericPref.save();  
	   
        
		//3. -> Create a batch Item Category in Item Category Master under stores master 
		//Click on Add New Categor * Enter Category Name * Select Identification = Batch No * Issue Type = Consumable -> Click on save button.
		
		//4. -> Create a serial Category in Item Category Master under stores master
		 //Click on Add New Category * Enter Category Name  * Select Identification = Serial NO * Issue Type = Consumable -> Click on save button.
        
        navigation.navigateToSettings(driver, "ItemCategoriesLink", "AddNewcategory");
		ItemCategories itemCat = new ItemCategories(executeStep, verifications);
        itemCat.AddItemcategory("Itemcategory", false);     
		
		
		//5. Settings --> store masters-->Package UOM --> Click on Add Package UOM --> Enter Package UOM -10,Unit  
		//UOM - 1 and Pkg Size = 10
	    
		navigation.navigateToSettings(driver, "PackageUOM", "PackageUOMHeader");
        PackageUOM uom = new PackageUOM(executeStep, verifications);
		uom.AddPackageUOMItem("pakageUOMItem");        
		
		
		//6. Create items in Item Master -->store master-> Click on add new item
		//7. Enter Item Name say item1, Shorter Name, Manufacturer.
		// Select Category say Batch No/Serial NO, Service Sub Group = general,
		//Tax Basis = MRP Based(with bonus) and Tax(%) = 5.5  
			
			
		navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");
		ItemMaster itemMaster =new ItemMaster(executeStep, verifications);
		itemMaster.AddItem("ItemMaster"); 
			
	 	
		
		//8. In Supplier Masters, add 'Supplier_1',Supplier_2 with Credit Period and Address In Supplier Masters.
		 
		navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");
		SupplierDetails supplier = new SupplierDetails(executeStep, verifications);
		supplier.addSupplier();
		supplier.createMultipleSupplier("SupplierMaster");
		
	 
	 }   
		
		
	 		
	 @Test
      public void DirectStockEntry(){
		 openBrowser();
			login();
		  
		  DataSet = "TS_017";
		  AutomationID = "DirectStockEntry";
		  List<String> lineItemIDs = null;
		  System.out.println("Inside Test DirectStockEntry - Before Navigation");
		  
		  executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
	        SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
			
			
	   StockEntryModule stockentry = new StockEntryModule(executeStep,verifications);
	    stockentry.AddSupplierName();
	    stockentry.Addinvoicenumber();
	    stockentry.addItemsToStocks("stockentryItem");
	    stockentry.Save(); 
			
			
	    
	    }  
	  
   @AfterClass
	  public void DirectStockEntryNonStrictPOEntryPostCondition()
	  {
		  DataSet = "PostCondition_017";
			AutomationID = "DirectStockEntryNonStrictPOEntryPostCondition";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test InventoryPurchaseOrder - Before Navigation");
		  executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
          verifications = new VerificationFunctions(driver, AutomationID, DataSet);
      
          SiteNavigation navigationTo = new SiteNavigation(AutomationID, DataSet);
          navigationTo.navigateToSettings(driver, "GenericPreferencesLink", "GenericPreferencesPage");
          
          //1. Set stock Entry with PO
    GenericPreferences genericPref= new GenericPreferences(executeStep, verifications);
      genericPref.SetStockEntryWithPO();
      genericPref.save();
		  
}   
	  } 