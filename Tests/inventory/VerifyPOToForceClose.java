package inventory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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
import reusableFunctions.inventory.POApproval;
import reusableFunctions.inventory.PurchaseOrder;
import reusableFunctions.inventory.SupplierDetails;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class VerifyPOToForceClose {
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
		AutomationID = "Login - VerifyValidatingPOFromValidatePOScreen";
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
		AutomationID = "Login - VerifyPOToForceClose";
		DataSet = "PreReq_056";
				
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
		public void VerifyPOToForceClosePreRequisite(){
			openBrowser();
			login();
			DataSet = "PreReq_056";
			AutomationID = "VerifyPOToForceClose";
			List<String> lineItemIDs = null;
			System.out.println("PurchaseDetails - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			//Create a batch wise Item Category in Item Category Master (Settings ->STORES MASTERS -> Item Categories) and select following and keep remaining fields as it is.
		    //  * Select Identification = Batch No
			//	* Issue Type = Consumable 
		
			navigation.navigateToSettings(driver, "ItemCategoriesLink", "AddNewcategory");
			
			ItemCategories itemCat = new ItemCategories(executeStep, verifications);
			itemCat.AddItemcategory("Itemcategory", false);
			
			//Create items in Item Master -->store master-> Click on add new item 
			// Enter Item Name say item1, Shorter Name, Manufacturer. 
			
			navigation.navigateToSettings(driver, "ItemMasterLink", "StoresItemMasterPage");
			
			ItemMaster itemMaster = new ItemMaster(executeStep, verifications);
			itemMaster.AddItem("ItemMaster");
			
			//In Supplier Masters, add 'Supplier_1' with Credit Period and Address --In Supplier Masters.
			
			navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");	
			
			executeStep.performAction(SeleniumActions.Click, "","AddNewSupplierLink");
			verifications.verify(SeleniumVerifications.Appears, "","AddSupplierDetailsPage",true);
			
			SupplierDetails	supplier = new SupplierDetails(executeStep, verifications);
			supplier.addSupplier();
			supplier.createMultipleSupplier("Supplier");
			
			
			//Open purchase order screen and enter Supplier_1 
		

			navigation.navigateTo(driver, "PurchaseOrderLink", "PurchaseOrderHeader");
			
			
			PurchaseOrder purchaseOrder = new PurchaseOrder(executeStep, verifications);
			purchaseOrder.raisePurchaseOrder();
			purchaseOrder.addItemsToPurchaseOrder("PurchaseItem");
			purchaseOrder.Save();
	  	}
	  	@Test()
		public void VerifyPOToForceClose(){
		  
			DataSet = "PreReq_056";
			AutomationID = "VerifyPOToForceClose";
			List<String> lineItemIDs = null;
			System.out.println("VerifyPOToForceClose - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			//1. Navigate to procurement-->view PO.
			navigation.navigateTo(driver, "ViewPOLink", "POListPage");
			
			
			//Click on search button by entring PO number(PO00001) under PO NO text box
			
			POApproval pOApproval = new POApproval(executeStep, verifications);
			pOApproval.searchPO();
			PurchaseOrder purchaseOrder = new PurchaseOrder(executeStep, verifications);
			purchaseOrder.viewEditPO();
			purchaseOrder.PurchaseOrderStatus();
			purchaseOrder.Save();
			
			System.out.println("Module Test TS_056 Completed");
			
	  }	 
}