package inventory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import GenericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.Login;
import reusableFunctions.Order;
import reusableFunctions.SiteNavigation;
import reusableFunctions.inventory.GRNModule;
import reusableFunctions.inventory.GenericPreferences;
import reusableFunctions.inventory.ItemCategories;
import reusableFunctions.inventory.ItemMaster;
import reusableFunctions.inventory.MiscellaneousSettings;
import reusableFunctions.inventory.PaymentTerms;
import reusableFunctions.inventory.PurchaseOrder;
import reusableFunctions.inventory.RolesAndUsers;
import reusableFunctions.inventory.StockEntryModule;
import reusableFunctions.inventory.SupplierDetails;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class ViewGRN {
	
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	DbFunctions grnno = new DbFunctions();
	//@BeforeSuite
		//public void BeforeSuite(){
		  private void openBrowser(){
			System.out.println("BeforeSuite");
			AutomationID = "Login - ViewGRN";
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
		AutomationID = "Login - ViewGRN";
		DataSet = "TS_030";
				
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
		 public void ViewGRNPreRequisite(){
				openBrowser();
				login();
				DataSet = "TS_030";
				AutomationID = "ViewGRNPreRequisite";
				List<String> lineItemIDs = null;
				System.out.println("Inside Test ViewGRNPreRequisite - Before Navigation");
				
				executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
				verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			 
				SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
				
		/*	1. -> Create a batch Item Category in Item Category Master under stores master 
				Click on Add New Category
				  * Enter Category Name        
				  * Select Identification = Batch No
				  * Issue Type = Consumable */
			/*	2. -> Create a serial Category in Item Category Master under stores master
				  Click on Add New Category
				  * Enter Category Name        
				  * Select Identification = Serial NO
				  * Issue Type = Consumable  */
				
			   navigation.navigateToSettings(driver, "ItemCategoriesLink", "AddNewcategory");
				ItemCategories itemCat = new ItemCategories(executeStep, verifications);
		        itemCat.AddItemcategory("Itemcategory", false);
				
			/*	3. Create items in Item Master -->store master-> Click on add new item */
			/*	4. Enter Item Name say item1, Shorter Name, Manufacturer.  */
			/*	5. Select Category say Batch No/Serial NO, Service Sub Group = general, 
				Tax Basis = CP Based(without bonus) and Tax(%) = 5.5 */
		        
		        
		    navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");
			ItemMaster itemMaster =new ItemMaster(executeStep, verifications);
			itemMaster.AddItem("ItemMaster"); 
				
				
			/*	6. In Supplier Masters, add 'Supplier_1' with Credit Period and Address
				 In Supplier Masters. */
				
				
				navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");
				SupplierDetails supplier = new SupplierDetails(executeStep, verifications);
				supplier.addSupplier();
				supplier.createMultipleSupplier("SupplierMaster");   
				
				
				
			/*	7. Open stock entry screen and enter Supplier_1 */
		 /*		8. Enter the Invoice Number "1" */
			/*	9. Click on “add new item” and ensure it popups “Item details”. */
				/* 10. Enter Item name = item1, Batch/Serial No,Expiry (MM-YY), MRP = 100, Rate = 80, 
				Quantity =1, Bonus = 1,Discount (%) = 10 and click on “Add” button. */
			/*	11. Ensure that item is added in the grid. */
			/*	12. Click on Save button GRN number will be generated */
			/*	13. Note the GRN number.  */

		
		 navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
		     
		 StockEntryModule stockentry = new StockEntryModule(executeStep,verifications);
			   stockentry.AddSupplierName();
			    stockentry.Addinvoicenumber();
			    stockentry.addItemsToStocks("stockentryItem");
			    stockentry.Save();
			   String GRNNo = stockentry.store();
			    //stockentry.storeGRN(String GRNNo, String dataSet);
			    
			    grnno.storeGRN(GRNNo,DataSet);
	  }    

	  
	 
	  
	  
	 @Test(priority=1)     // TEST CASE #29 : Ivv_TS_029
      public void Grn(){
		  openBrowser();
			login();
		  DataSet = "TS_029";
		  AutomationID = "Grn";
		  List<String> lineItemIDs = null;
		  System.out.println("Inside Test Grn - Before Navigation");
		  
		  executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateTo(driver, "ViewGRNLink", "GRNPage");
			GRNModule searchgrn = new GRNModule(executeStep,verifications);
			searchgrn.GRNSearch(); 
		 
		 
			
			}  
	  
	   @Test(priority=2)    // TEST CASE #30 : Ivv_TS_030
      public void GrnPrint(){
		  openBrowser();
			login();
		  DataSet = "TS_030";
		  AutomationID = "GrnPrint";
		  List<String> lineItemIDs = null;
		  System.out.println("Inside Test GrnPrint - Before Navigation");
		  
		  executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateTo(driver, "ViewGRNLink", "GRNPage");
			
			GRNModule viewgrn = new GRNModule(executeStep,verifications);
			viewgrn.GRNSearch();
			viewgrn.GRNPrint();
			viewgrn.OpenCloseTab();
			
			
			
			} 	  

		  
	  
	  
	  
	  
	  
	  
	  
	  
	  
}
