package inventory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
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
import reusableFunctions.inventory.SupplierReplacement;
import reusableFunctions.inventory.SupplierReturn;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class ReplacePartialQtyForSupplierReturn {
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
		AutomationID = "Login - ReplacePartialQtyForSupplierReturn";
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
		AutomationID = "Login - ReplacePartialQtyForSupplierReturn";
		DataSet = "PreReq_035";
				
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
		public void ReplacePartialQtyForSupplierReturnPreRequisite(){
			openBrowser();
			login();
			DataSet = "PreReq_035";
			AutomationID = "ReplacePartialQtyForSupplierReturnPreRequisite";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test ReplacePartialQtyForSupplierReturn - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);

			
//1. Create a batch wise Item Category in Item Category Master (Settings ->STORES MASTERS -> Item Categories) and select following and keep remaining fields as it is.
//  * Select Identification = Batch No
//	* Issue Type = Consumable 
//2. Create a serial wise Item Category in Item Category Master (Settings ->STORES MASTERS -> Item Categories) and select following and keep remaining fields as it is.
//  * Select Identification = Serial NO
//  * Issue Type = Consumable 
			
			navigation.navigateToSettings(driver, "ItemCategoriesLink", "AddNewcategory");
			
			ItemCategories itemCat = new ItemCategories(executeStep, verifications);
			itemCat.AddItemcategory("Itemcategory", false);
			
	//3. Settings --> store masters-->Package UOM --> Click on Add Package UOM --> Enter Package UOM -10,Unit  UOM - 1 and Pkg Size = 10
			
			navigation.navigateToSettings(driver, "PackageUOM", "PackageUOMHeader");
	        PackageUOM uom = new PackageUOM(executeStep, verifications);
			uom.AddPackageUOMItem("pakageUOMItem");
			
		//	4. Create items in Item Master -->store master-> Click on add new item
		//	5. Enter an Item Name say item1(unit size 1), Shorter Name, Manufacturer, select Category say Batch No, Service Sub Group = general, Tax Basis = MRP Based(with bonus) and Tax(%) = 5.5 and click on save.
		//	-->  Enter an Item Name say item2(package size 10), Shorter Name, Manufacturer, select Category say Batch No, Unit UOM = 10,Service Sub Group = general, Tax Basis = MRP Based(with bonus) and Tax(%) = 5.5 and click on save.
		//	--> Enter an Item Name say item3(serial item), Shorter Name, Manufacturer, select Category say Serial NO , Service Sub Group = general, Tax Basis = MRP Based(with bonus) and Tax(%) = 5.5 and click on save.			
		
			navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");	
			ItemMaster itemMaster = new ItemMaster(executeStep, verifications);
			itemMaster.AddItem("ItemMaster");
			
//6. Settings -> STORES MASTERS-> Suppliers, Create a supllier by giving supplier name and hit on save.
			navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");	
			 executeStep.performAction(SeleniumActions.Click, "","AddNewSupplierLink");
			    
			SupplierDetails	supplier = new SupplierDetails(executeStep, verifications);
			supplier.createSupplier();
			
//7. Open stock entry screen and enter Supplier_1 
			navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
			 StockEntryModule stockentry = new StockEntryModule(executeStep,verifications);
			    stockentry.AddSupplierName();
			    
			   
//			8. Enter the Invoice Number ""1""
			    stockentry.Addinvoicenumber();
		
			
	//9. Click on “add new item” and ensure it popups “Item details”. 
	//10. -->Enter Item name = item1, Batch no,Expiry (MM-YY), MRP = 100, Rate = 80, Quantity =1, Bonus = 1,Discount (%) = 10 and click on “Add” button. 
	//    -->Enter Item name = item2, Batch No,Expiry (MM-YY), MRP = 100, Rate = 80, Quantity =10, Bonus = 10,Discount (%) = 10 and click on “Add” button.
//		-->Enter Item name = item3, Expiry (MM-YY), MRP = 100, Rate = 80,Quantity =5, Bonus = 5,Discount (%) = 10 and click on “Add” button. 
	//		 11. Ensure that item is added in the grid.	    
			  //  stockentry.selectStockType();
			    stockentry.addItemsToStocks("stockentryItem");
 //12. Click on Save button GRN number will be generated
			    //13. Note the GRN number (GR00001).
			    stockentry.Save();
			    stockentry.store();
			  
			    
		//14. Set Allow decimals for qty(Supplier) to Yes and Return Against 
		//	    Specific Supplier to Yes in generic preferences.
			    navigation.navigateToSettings(driver, "GenericPreferencesLink", "GenericPreferencesPage");
			    GenericPreferences genPref = new GenericPreferences(executeStep,verifications); 
			    genPref.allowDecimalForQtySupplier();
			    genPref.returnAgainstSpecificSupplier();
			    genPref.save();
			    
			    
	//15. Click on Supplier Returns/Replacements -> Procurement 
			//   16. Supplier return txn.No(Txn.No-1) should be generated.
			    navigation.navigateTo(driver,"SupplierReturnsReplacementLink" ,"SupplierReturnsReplacementPage" );    
			    executeStep.performAction(SeleniumActions.Click, "", "RaiseNewSupplierReturnLink");
				verifications.verify(SeleniumVerifications.Appears, "", "SupplierReturnPage", false);
				
				//2. Ensure that supplier returns screen is opened . 
				//3. Select the qty in "Unit UOM". 
				SupplierReturn suppReturn = new SupplierReturn(executeStep,verifications);
				suppReturn.selectPackageUOM();
								
				
				//4. Enter the return details such as "supplier, store, return type, remarks, gate pass(check box)". 
				suppReturn.ReturnDetails();
				suppReturn.gatePassCheckBox();
				
				//5. Click on "add item" button and ensure that it displays item details popup. 
				suppReturn.addReturnItem("SupplyReturn",false);
				//6. Enter the item name item1,item2 and item3 select the available batch/ serial no. 
				//7. Enter the "return qty" and click on "save" button. 
				//8. Add n number of items to return by repeating step 5,6,7. 
				//9. Click on "return" button.
				suppReturn.clickReturn();
	 	  
	 	  }

		 	 @Test(priority = 1)
				public void ReplacePartialQtyForSupplierReturn(){
				  
				  
					DataSet = "TS_035";
					AutomationID = "ReplacePartialQtyForSupplierReturn";
					List<String> lineItemIDs = null;
					System.out.println("Inside Test ReplacePartialQtyForSupplierReturn - Before Navigation");
					
					executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
					verifications = new VerificationFunctions(driver, AutomationID, DataSet);
					
						
					
					SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
					
					
					//1. Select txn.No(Txn.No -1) and click on "replace" option.
					SupplierReplacement suppReplace = new SupplierReplacement(executeStep,verifications);
					suppReplace.clickReplaceMenuOption();
					//2. Ensure that "supplier replacement" screen is opened. 
					//3. Select Damage/ expiry/ non- moving/ others options from return type dropdown and enter the remarks in remarks field. 
					suppReplace.supplierOriginalDetails();
					//4. In item list Edit "MRP/ exp date/ replacing qty/ replacing batch no. 
					//5. Click on "return" button.
					suppReplace.replaceSupplier1("ReplaceSupplier1",true);
					suppReplace.replaceSupplier2("ReplaceSupplier2",true);
					suppReplace.replaceSupplier3("ReplaceSupplier3",true);
					suppReplace.clickReplace();
		 	 }
}
