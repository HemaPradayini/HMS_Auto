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
import reusableFunctions.inventory.ItemCategories;
import reusableFunctions.inventory.ItemMaster;
import reusableFunctions.inventory.PackageUOM;
import reusableFunctions.inventory.StockEntryModule;
import reusableFunctions.inventory.SupplierDetails;
import reusableFunctions.inventory.SupplierReturnWithDebit;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class VerifyCloseStatusSupplierReturnDebitNote {
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
		AutomationID = "Login - VerifyCloseStatusSupplierReturnDebitNote";
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
		AutomationID = "Login - VerifyCloseStatusSupplierReturnDebitNote";
		DataSet = "PreReq_038";
				
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
		public void verifyCloseStatusSupplierReturnDebitNotePreRequisite(){
			openBrowser();
			login();
			DataSet = "PreReq_038";
			AutomationID = "VerifyCloseStatusSupplierReturnDebitNote";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test VerifyCloseStatusSupplierReturnDebitNote - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);

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
		*/
		
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
	*/
		navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");	
		ItemMaster itemMaster = new ItemMaster(executeStep, verifications);
		itemMaster.AddItem("ItemMaster");
		
		//6. In Supplier Masters, add 'Supplier_1' with Credit Period and Address
		// In Supplier Masters.
		navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");	
		 executeStep.performAction(SeleniumActions.Click, "","AddNewSupplierLink");
		    
		SupplierDetails	supplier = new SupplierDetails(executeStep, verifications);
		supplier.createSupplier();
		
		//7. Open stock entry screen and enter Supplier_1 
		navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
		 StockEntryModule stockentry = new StockEntryModule(executeStep,verifications);
		    stockentry.AddSupplierName();
		    
		//8. Enter the Invoice Number "1"
		    stockentry.Addinvoicenumber();
		    
		//9. Click on “add new item” and ensure it popups “Item details”. 
		//10.-->Enter Item name = item1, Batch no,Expiry (MM-YY), MRP = 100, Rate = 80, Quantity =1, Bonus = 1,Discount (%) = 10 and click on “Add” button. 
		//-->Enter Item name = item2, Batch No,Expiry (MM-YY), MRP = 100, Rate = 80, Quantity =10, Bonus = 10,Discount (%) = 10 and click on “Add” button.
		//->Enter Item name = item3, Expiry (MM-YY), MRP = 100, Rate = 80,Quantity =5, Bonus = 5,Discount (%) = 10 and click on “Add” button. 
		
		    stockentry.addItemsToStocks("stockentryItem");
		    //11. Ensure that item is added in the grid. 
		//12. Click on Save button GRN number will be generated
		//13. Note the GRN number (GR00001).
		    stockentry.Save();
		    stockentry.store();
		//14. Click on Supplier Returns (With Debit Notes) -> Procurement .
		    navigation.navigateTo(driver, "SupplierReturnsWithDebitNotesLink", "SupplierReturnsWithDebitNotesPage");  
		//15. Supplier Return (With Debit Note) screen should be opened.
		    
			
			//	16. Click on "Raise New Debit Note " link.
//				17. Ensure that Supplier return (debit) sccreen is opened.
				executeStep.performAction(SeleniumActions.Click, "","RaiseNewDebitNoteLink");
				verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnsDebitPage",true);
			
				
			//	18. Select Return Against:Without GRN No radio button 
				
			//	19. Enter the return details such as "supplier, store, return type, remarks, 
				SupplierReturnWithDebit supRetDebit = new SupplierReturnWithDebit(executeStep,verifications);
				supRetDebit.debitNotesDetails();
			//20	gate pass(check box). 
				supRetDebit.gatePassCheckBox();
			//	21. "add item" which is to be returned and enter the returned qty.
				supRetDebit.addItem("SupplierReturnDebit");
			//	22. Click on "update" note.   
				supRetDebit.updateDebitNote();
	 	  }    
	 	  
	 	  @Test(priority = 1)
			public void VerifyCloseStatusSupplierReturnDebitNote(){
			  
			  
				DataSet = "TS_038";
				AutomationID = "VerifyCloseStatusSupplierReturnDebitNote";
				List<String> lineItemIDs = null;
				System.out.println("Inside Test VerifyCloseStatusSupplierReturnDebitNote - Before Navigation");
				
				executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
				verifications = new VerificationFunctions(driver, AutomationID, DataSet);
				
					
				
				SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			//	1. Select the debit note numebr(DB0001) and click on view/edit option. 
			//	2. Select the close option from status dropdown. 
			//	3. Click on "update debit note" button.
				
				
	 	  }
}
