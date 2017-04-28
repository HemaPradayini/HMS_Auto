package inventory;

import java.util.List;

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
import reusableFunctions.MRNoSearch;
import reusableFunctions.SiteNavigation;
import reusableFunctions.inventory.GenericPreferences;
import reusableFunctions.inventory.ItemCategories;
import reusableFunctions.inventory.ItemVerification;
import reusableFunctions.inventory.POApproval;
import reusableFunctions.inventory.PurchaseOrder;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class ApprovePurchaseOrderFromApprovePOScreen {

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
		AutomationID = "Login - ApprovePurchaseOrderFromApprovePOScreen";
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
		AutomationID = "Login - ApprovePurchaseOrderFromApprovePOScreen";
		DataSet = "PreReq_002";
				
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
		public void ApprovePurchaseOrderFromApprovePOScreenPreRequisite(){
			openBrowser();
			login();
			DataSet = "PreReq_002";
			AutomationID = "ApprovePurchaseOrderFromApprovePOScreenPreRequisite";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test ApprovePurchaseOrderFromApprovePOScreen - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		
	  
		//1. In Generic Prefrence (Sttings -> HOSPITAL PREFERENCES-> Generic preferences) set PO validation to yes.
		navigation.navigateToSettings(driver, "GenericPreferencesLink", "GenericPreferencesPage");
		
		GenericPreferences genPref = new GenericPreferences(executeStep, verifications);
		genPref.setPOValidation();
		genPref.save();
		
		//2. Go to Purchase Order screen under Procurement enter the valid information in mandatory fields such as �store�, �supplier�, �PO date�.
		navigation.navigateTo(driver, "PurchaseOrderLink", "PurchaseOrderHeader");
		
		PurchaseOrder purchaseOrder = new PurchaseOrder(executeStep, verifications);
		purchaseOrder.raisePurchaseOrder();
		purchaseOrder.addItemsToPurchaseOrder("PurchaseItem");
		ItemVerification itemVerify = new ItemVerification(executeStep, verifications);
		itemVerify.ItemsVerification("PurchaseItem");
		purchaseOrder.SaveAndValidate();
		
	  }  
	  
	  @Test(priority = 1)
		public void ApprovePurchaseOrderFromApprovePOScreen(){
		  System.out.println(" Entered ApprovePurchaseOrderFromApprovePOScreen");
		  
		  //1. Modules -> PROCUREMENT -> Approve PO, click on More options and give PO number ( i.e, PO no. you got after raising PO ) say PO00001  and click search it.
		  navigation.navigateTo(driver, "ApprovePOLink", "POApprovalPage");
		  
		  executeStep.performAction(SeleniumActions.Click, "","POApprovalPageMoreOptions");
			verifications.verify(SeleniumVerifications.Appears, "","POApprovalPage",true);
		  
		  POApproval pOApproval = new POApproval(executeStep, verifications);
		  pOApproval.searchPO();
		  //2. Click on PO and click on edit, should navigate to Approve/ Reject PO scrren.
		  pOApproval.clickEditMenuOption();
		  //3. Select Approve radio button and hit on save button.
		  pOApproval.approvePO();
		  testCaseStatus = "Pass";
			
		  System.out.println("Module Test TS_002 Completed");
	  }
		@AfterClass
		public void closeBrowser(){
			String delimiter = " :: ";
			//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
			//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
			ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "ApprovePurchaseOrderFromApprovePOScreen", null, "", "", testCaseStatus);
			//driver.close();
		}
	  
}
