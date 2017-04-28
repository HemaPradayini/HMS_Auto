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
import reusableFunctions.inventory.GenericPreferences;
import reusableFunctions.inventory.POApproval;
import reusableFunctions.inventory.PurchaseOrder;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class ToRejectPOFromApprovePOScreen {
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
		AutomationID = "Login - ToRejectPOFromApprovePOScreen";
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
		AutomationID = "Login - ToRejectPOFromApprovePOScreen";
		DataSet = "TS_004";
				
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
		public void toRejectPOFromApprovePOScreenPreRequisite(){
			openBrowser();
			login();
			DataSet = "TS_004";
			AutomationID = "ToRejectPOFromApprovePOScreen";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test ToRejectPOFromApprovePOScreen - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);

			
			//1. Force Remarks on PO Item Reject should be set to "Yes" in generic preferences screen(Settings-> Hospital Prefrence).
			navigation.navigateToSettings(driver, "GenericPreferencesLink", "GenericPreferencesPage");
			
			GenericPreferences genPref = new GenericPreferences(executeStep, verifications);
			genPref.POItemReject();
			genPref.save();
			
			//2. Go to Purchase Order screen under Procurement enter the valid information in mandatory fields such as “store”, “supplier”, “PO date”.
			navigation.navigateTo(driver, "PurchaseOrderLink", "PurchaseOrderHeader");
			
			//3. Click on “add new item” and ensure it popups “Item details”. 
			//4. Enter Item name, MRP, Rate, Qty and click on “Add” button. 
			//5. Ensure that item is added in the grid.
			//6. Click on “save & Validate” button, PO number will be generated say PO00001 
			PurchaseOrder purchaseOrder = new PurchaseOrder(executeStep, verifications);
			purchaseOrder.raisePurchaseOrder();
			purchaseOrder.addItemsToPurchaseOrder("PurchaseItem");
			purchaseOrder.SaveAndValidate();
			
			 }
	  
	  @Test(priority = 1)
			public void toRejectPOFromApprovePOScreen(){
			  System.out.println(" Entered ToRejectPOFromApprovePOScreen");
			  
			  
	//1. Modules-> Procurement-> Approve PO, Click on more oprtion and provide PO no. and select stores and click on search button.
			  navigation.navigateTo(driver, "ApprovePOLink", "POApprovalPage");
			  
			  executeStep.performAction(SeleniumActions.Click, "","POApprovalPageMoreOptions");
			  verifications.verify(SeleniumVerifications.Appears, "","POApprovalPage",true);
			  POApproval pOApproval = new POApproval(executeStep, verifications);
			  pOApproval.searchPO();
	//2. Click on PO and click on edit, should navigate to Approve/ Reject PO scrren.
			pOApproval.clickEditMenuOption();
			//4. Select the "reject" radio button, and Enter the remarks and click on ok button.
			//5. Click on save button it will give validation alert message say ok for that.
			pOApproval.rejectPO();
			testCaseStatus = "Pass";
			
			  System.out.println("Module Test TS_004 Completed");
			  
	  }
		@AfterClass
		public void closeBrowser(){
			String delimiter = " :: ";
			//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
			//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
			ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "ToRejectPOFromApprovePOScreen", null, "", "", testCaseStatus);
			driver.close();
		}  
	  
}
