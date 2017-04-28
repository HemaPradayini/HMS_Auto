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
import reusableFunctions.Order;
import reusableFunctions.SiteNavigation;
import reusableFunctions.inventory.ItemCategories;
import reusableFunctions.inventory.ItemCategories;
import reusableFunctions.inventory.ItemMaster;
import reusableFunctions.inventory.MiscellaneousSettings;
import reusableFunctions.inventory.PaymentTerms;
import reusableFunctions.inventory.PurchaseOrder;
import reusableFunctions.inventory.RolesAndUsers;
import reusableFunctions.inventory.SupplierDetails;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class InventoryPurchaseOrder {
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
		AutomationID = "Login - InventoryPurchaseOrder";
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
		AutomationID = "Login - InventoryPurchaseOrder";
		DataSet = "PreReq_001";
				
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
	  
	  private void loginFromDiffUser(){
		  
		  Login OPWorkFlowLogin = new Login(executeStep,verifications);
			OPWorkFlowLogin.logOut();
			DataSet = "TS_001";
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			Login Login = new Login(executeStep,verifications);
			Login.login();
			executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
			System.out.println("After Close Tour");
			CommonUtilities.delay();
			executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
			System.out.println("After Close Tour");
			
			executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		  
	  }
	  @BeforeClass
		public void InventoryPurchaseOrderPreRequisite(){
			openBrowser();
			login();
			DataSet = "PreReq_001";
			AutomationID = "InventoryPurchaseOrder";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test InventoryPurchaseOrder - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
//1. Create a user by selecting the default store.
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
						navigation.navigateToSettings(driver, "RolesAndUsersLink", "RolesAndUsersPage");
			executeStep.performAction(SeleniumActions.Click, "", "CreateNewUserLink");
			
			RolesAndUsers user = new RolesAndUsers(executeStep, verifications);
					user.createUser();
			
			/*Login OPWorkFlowLogin = new Login(executeStep,verifications);
			OPWorkFlowLogin.logOut();
			DataSet = "TS_001";
			Login Login = new Login(executeStep,verifications);
			Login.login();
			*/
			loginFromDiffUser();
//2. Create a batch wise Item Category in Item Category Master (Settings ->STORES MASTERS -> Item Categories) and select following and keep remaining fields as it is.
//  * Select Identification = Batch No
//	* Issue Type = Consumable 
//3. Create a serial wise Item Category in Item Category Master (Settings ->STORES MASTERS -> Item Categories) and select following and keep remaining fields as it is.
//  * Select Identification = Serial NO
//  * Issue Type = Consumable 
			
			navigation.navigateToSettings(driver, "ItemCategoriesLink", "AddNewcategory");
			
			ItemCategories itemCat = new ItemCategories(executeStep, verifications);
			itemCat.AddItemcategory("Itemcategory", false);
		
//4. Create items in item master (Settings ->STORES MASTERS -> Item master) by giving following details.
// Item Name : ----- (Free text, Name of the item which u r going to create )
//Shorter Name: -----( Free text , can be given short name of item name)
//Manufacturer : ---- (Auto fill, press space or any letter and select any data )
//Category : -------(Select what you have created in category master i.e, batch wise item category).
//Service Sub Group : (Drop down, can select any one)
			             
// Create one more item by selecting Category : Serial wise item category 			
			navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");	
			ItemMaster itemMaster = new ItemMaster(executeStep, verifications);
			itemMaster.AddItem("ItemMaster");
			
//5. Settings -> STORES MASTERS-> Suppliers, Create a supllier by giving supplier name and hit on save.
			navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");	
			 executeStep.performAction(SeleniumActions.Click, "","AddNewSupplierLink");
			    
			SupplierDetails	supplier = new SupplierDetails(executeStep, verifications);
			supplier.createSupplier();
			
//6. Settings -> STORES MASTERS -> Miscellaneous Settings, Enter values in fields and mandatorely for 'PO Terms and Conditions'.			
			navigation.navigateToSettings(driver, "MiscellaneousSettings", "MiscellaneousSettingsHeader");	
			MiscellaneousSettings miscSettings = new MiscellaneousSettings(executeStep, verifications);
			miscSettings.DoMiscellaneousSettings();
			
//7. Settings -> STORES MASTERS -> Payment Terms, Create a payment term by not selecting Delivery Instruction(check box) and giving data in Terms And Conditions field then save.
//8. Settings -> STORES MASTERS -> Payment Terms, Create a payment term by selecting Delivery Instruction(check box) and giving data in Terms And Conditions field then save.			
			navigation.navigateToSettings(driver, "PaymentTermsLink", "PaymentsTermsHeader");
			PaymentTerms payment = new PaymentTerms(executeStep, verifications);
			payment.createPaymentTerms("PaymentTerms");
			
		
			
	  }
	  
	  @Test(priority = 1)
		public void PurchaseOrderInOpenStatus(){
		  
		  
			DataSet = "TS_001";
			AutomationID = "PurchaseOrderInOpenStatus";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test RosterWorkFlow - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
				
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateTo(driver, "PurchaseOrderLink", "PurchaseOrderHeader");
			
			PurchaseOrder purchaseOrder = new PurchaseOrder(executeStep, verifications);
			purchaseOrder.raisePurchaseOrder();
			purchaseOrder.addItemsToPurchaseOrder("PurchaseItem");
			purchaseOrder.termsAndConditions();
			purchaseOrder.Save();
			
			//Should generate PO number and the Purchase order with open status.
			verifications.verify(SeleniumVerifications.Selected, "POStatus","EditPurchaseOrderHeader",true);	
			
			
			testCaseStatus = "Pass";
			
			  System.out.println("Module Test TS_001 Completed");
	  }
	  
		@AfterClass
		public void closeBrowser(){
			String delimiter = " :: ";
			//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
			//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
			ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "InventoryPurchaseOrder", null, "", "", testCaseStatus);
		//	driver.close();
		}
}
