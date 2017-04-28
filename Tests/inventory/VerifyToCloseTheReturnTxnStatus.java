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

public class VerifyToCloseTheReturnTxnStatus {
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
		AutomationID = "Login - VerifyToCloseTheReturnTxnStatus";
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
		AutomationID = "Login - VerifyToCloseTheReturnTxnStatus";
		DataSet = "PreReq_Inv_TS_059";
				
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
		public void VerifyToCloseTheReturnTxnStatusPreRequisite(){
			openBrowser();
			login();
			DataSet = "PreReq_Inv_TS_059";
			AutomationID = "VerifyToCloseTheReturnTxnStatus";
			List<String> lineItemIDs = null;
			System.out.println("VerifyToCloseTheReturnTxnStatus - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
		/*	1. -> Create a batch Item Category in Item Category Master under stores master 
			Click on Add New Category
			  * Enter Category Name        
			  * Select Identification = Batch No
			  * Issue Type = Consumable 
			2. -> Create a serial Category in Item Category Master under stores master
			  Click on Add New Category
			  * Enter Category Name        
			  * Select Identification = Serial NO
			  * Issue Type = Consumable */
			
			navigation.navigateToSettings(driver, "ItemCategoriesLink", "AddNewcategory");
			
			ItemCategories itemCat = new ItemCategories(executeStep, verifications);
			itemCat.AddItemcategory("Itemcategory", false);
			
			//3. Create items in Item Master -->store master-> Click on add new item
			//4. Enter Item Name say item1, Shorter Name, Manufacturer.
			//5. Select Category say Batch No/Serial NO, Service Sub Group = general, 
			//Tax Basis = CP Based(without bonus) and Tax(%) = 5.5
			//Add n number of items(3 to 4 items)
			
			navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");
			
			ItemMaster itemMaster = new ItemMaster(executeStep, verifications);
			itemMaster.AddItem("ItemMaster");
			
			//6. In Supplier Masters, add 'Supplier_1' with Credit Period and Address
			// In Supplier Masters.
			
			navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");	
			
			SupplierDetails	supplier = new SupplierDetails(executeStep, verifications);
			supplier.addSupplier();
			supplier.createSupplier();
			
			//7. Open stock entry screen and enter Supplier_1 
			//8. Enter the Invoice Number"1"
			//9. Click on “add new item” and ensure it popups “Item details”. 
			//10. Enter Item name = item1, Batch/Serial No,Expiry (MM-YY), MRP = 100, Rate = 80, 
			//Quantity =1, Bonus = 1,Discount (%) = 10 and click on “Add” button. 
			//11. Ensure that item is added in the grid. 
			//12. Click on Save button GRN number will be generated
			//13. Note the GRN number (GR00001).
			
			navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
			
			StockEntryModule stockentry = new StockEntryModule(executeStep,verifications);
		    stockentry.AddSupplierName();
		    stockentry.Addinvoicenumber();
		    stockentry.addItemsToStocks("stockentryItem");
		    stockentry.Save();
		    stockentry.store();														
		    
		    //14. Click on Supplier Returns/Replacements -> Procurement 
		    //15. Set Allow decimals for qty(Supplier) to Yes and Return Against .
		    
		    navigation.navigateToSettings(driver, "GenericPreferencesLink", "GenericPreferencesPage");
			GenericPreferences genPref = new GenericPreferences(executeStep,verifications); 
			genPref.allowDecimalForQtySupplier();
			genPref.returnAgainstSpecificSupplier();
			genPref.save();
			
			//16. Click on ""Raise New Supplier Return "" link . 
			//17. Ensure that supplier returns screen is opened 
			
			navigation.navigateTo(driver,"SupplierReturnsReplacementLink" ,"SupplierReturnsReplacementPage" );  
			executeStep.performAction(SeleniumActions.Click, "", "RaiseNewSupplierReturnLink");
			verifications.verify(SeleniumVerifications.Appears, "", "SupplierReturnPage", false);
			
			//18. Select the qty in ""Package UOM/ Unit UOM"". 
			
			SupplierReturn suppReturn = new SupplierReturn(executeStep,verifications);
			suppReturn.selectPackageUOM();
			
			//19. Enter the return details such as ""supplier, store, return type, remarks, gate pass(check box)"". 
			suppReturn.ReturnDetails();
			suppReturn.gatePassCheckBox();
			
			//20. Click on ""add item"" button and ensure that it displays item details popup. 
			//21. Enter the item name and select the available batch/ serial no
			
			suppReturn.addReturnItem("SupplyReturn",false);
			
			//22. Enter the ""return qty"" and click on ""save"" button. 
			
			suppReturn.clickReturn();
			
			//23. Add n number of items to return by repeating step 5,6,7. 9. Click on "return" button txn.No should be 
			//generated with open status.
			//24. Supplier returns/ replacement list screen should be opened."
			
			verifications.verify(SeleniumVerifications.Appears, "", "Supplier ReturnsReplacementsPage", false);
			

			
	  }	

			 @Test
				public void VerifyToCloseTheReturnTxnStatus(){
				 
				 DataSet = "Inv_TS_059";
					AutomationID = "VerifyToCloseTheReturnTxnStatus";
					List<String> lineItemIDs = null;
					System.out.println("VerifyToCloseTheReturnTxnStatus - Before Navigation");
					
					executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
					verifications = new VerificationFunctions(driver, AutomationID, DataSet);
					
					
			//1. Select the "supplier return" txn.No and click on "close" option. 		
					SupplierReturn suppReturn = new SupplierReturn(executeStep,verifications);	
					suppReturn.StoreTxnNo();
					suppReturn.SearchTxnNo();
					suppReturn.CloseTxnNo();
					executeStep.performAction(SeleniumActions.Click, "", "PharmacyStatusConfirmationScreenCloseButton");
					verifications.verify(SeleniumVerifications.Appears, "", "Supplier ReturnsReplacementsPage", false);
					
			
			
			
}
}

