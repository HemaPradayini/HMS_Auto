package inventory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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
import reusableFunctions.inventory.ItemVerification;
import reusableFunctions.inventory.POApproval;
import reusableFunctions.inventory.PurchaseOrder;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class ViewPO {
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
		AutomationID = "Login - ViewPO";
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
		AutomationID = "Login - ViewPO";
		DataSet = "TS_012";
				
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
		public void ViewPOPreRequisite(){
			openBrowser();
			login();
			AutomationID = "Login - ViewPO";
			DataSet = "TS_012";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test ViewPO - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			
//1. Genenrate the PO number by raising purchase order. 
			//2. Note the PO number(PO00001).
			
			navigation.navigateTo(driver, "PurchaseOrderLink", "PurchaseOrderHeader");
			
			PurchaseOrder purchaseOrder = new PurchaseOrder(executeStep, verifications);
			purchaseOrder.raisePurchaseOrder();
			purchaseOrder.addItemsToPurchaseOrder("PurchaseItem");
			ItemVerification itemVerify = new ItemVerification(executeStep, verifications);
			itemVerify.ItemsVerification("PurchaseItem");
			purchaseOrder.Save();
	  }
	  @Test(priority = 1)
		public void viewPOTestTwelve(){
	  DataSet = "TS_012";
		AutomationID = "viewPOTestTwelve";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test viewPOTestTwelve - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		//1. Navigate to procurement-->view PO.
		navigation.navigateTo(driver, "ViewPOLink", "POListPage");
		
		//2. Ensure that PO list is listed according to filterable.
		POApproval pOApproval = new POApproval(executeStep, verifications);
		pOApproval.searchPO();
		testCaseStatus = "Pass";
		
		  System.out.println("Module Test TS_012 Completed");
		
	  }
	  
	  @Test(priority = 2)
		public void viewPOTestThirteen(){
	  DataSet = "TS_012";
		AutomationID = "viewPOTestThirteen";
		List<String> lineItemIDs = null;
		
		System.out.println("Inside Test viewPOTestThirteen - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		//1. Navigate to procurement-->view PO.
		navigation.navigateTo(driver, "ViewPOLink", "POListPage");
		executeStep.performAction(SeleniumActions.Click, "","POApprovalPageMoreOptions");
	    verifications.verify(SeleniumVerifications.Appears, "","POListPage",true);
		//2. Select the PO(PO00001) and click on "View/edit PO" option.
		POApproval pOApproval = new POApproval(executeStep, verifications);
		pOApproval.searchPO();
		PurchaseOrder purchaseOrder = new PurchaseOrder(executeStep, verifications);
		purchaseOrder.viewEditPO();
		testCaseStatus = "Pass";
		
		  System.out.println("Module Test TS_013 Completed");
	  }
		@AfterMethod
		public void closeBrowser(){
			String delimiter = " :: ";
			//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
			//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
			ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "ViewPO", null, "", "", testCaseStatus);
			//driver.close();
		}
	  
}
