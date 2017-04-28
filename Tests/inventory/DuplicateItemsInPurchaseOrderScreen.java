package inventory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import GenericFunctions.CommonUtilities;
import GenericFunctions.EnvironmentSetup;
import GenericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.Login;
import reusableFunctions.SiteNavigation;
import reusableFunctions.inventory.ItemVerification;
import reusableFunctions.inventory.PurchaseOrder;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class DuplicateItemsInPurchaseOrderScreen {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	String testCaseStatus = "Fail";
	//@BeforeSuite
	//public void BeforeSuite(){
	  private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - DuplicateItemsInPurchaseOrderScreen";
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
		AutomationID = "Login - DuplicateItemsInPurchaseOrderScreen";
		DataSet = "TS_014";
				
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
		public void DuplicateItemsInPurchaseOrderScreenPreRequisite(){
			openBrowser();
			login();
			DataSet = "TS_014";
			AutomationID = "DuplicateItemsInPurchaseOrderScreen";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test DuplicateItemsInPurchaseOrderScreen - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			//1. Go to Purchase Order screen under Procurement enter the valid information in mandatory fields such as “store”, “supplier”, “PO date”. 
			//2. Click on “add new item” and ensure it popups “Item details”. 
			//3. Enter Item name, MRP, Rate, Qty and click on “Add” button. 
			//4. Ensure that item is added in the grid.
			//5. Click on “save” button, PO number will be generated say PO00001
			//6. PO number(PO00001) should be in open status.
			//7. Edit purchase order screen should be opened with same PO number(PO00001).
			
			
			navigation.navigateTo(driver, "PurchaseOrderLink", "PurchaseOrderHeader");
			
			PurchaseOrder purchaseOrder = new PurchaseOrder(executeStep, verifications);
			purchaseOrder.raisePurchaseOrder();
			purchaseOrder.addItemsToPurchaseOrder("PurchaseItem");
			ItemVerification itemVerify = new ItemVerification(executeStep, verifications);
			itemVerify.ItemsVerification("PurchaseItem");
			purchaseOrder.Save();
			
			
	  }
	  
	  @Test(priority = 1)
			public void duplicateItemsInPurchaseOrderScreen(){
			  
			  
				DataSet = "TS_014";
				AutomationID = "DuplicateItemsInPurchaseOrderScreen";
				List<String> lineItemIDs = null;
				System.out.println("Inside Test DuplicateItemsInPurchaseOrderScreen - Before Navigation");
				
				executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
				verifications = new VerificationFunctions(driver, AutomationID, DataSet);
				
				//1. Ensure that PO is opened. 
				//2. Click on "add new item" button. 
				//3. Add the same item and enter qty in item details. 
				//4. Click on "add" button.
				PurchaseOrder purchaseOrder = new PurchaseOrder(executeStep, verifications);
				
				purchaseOrder.addItemsToPurchaseOrder("PurchaseItem");
				testCaseStatus = "Pass";
				System.out.println("TS_014 - completed");
				
	  }
			
		@AfterClass
		public void closeBrowser(){
			String delimiter = " :: ";
			//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
			//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
			ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "DuplicateItemsInPurchaseOrderScreen", null, "", "", testCaseStatus);
			//driver.close();
		}
}
