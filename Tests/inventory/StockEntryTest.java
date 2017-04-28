package inventory;

import org.openqa.selenium.WebDriver;

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
import reusableFunctions.Order;
import reusableFunctions.SiteNavigation;
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


public class StockEntryTest {

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
			AutomationID = "Login - StockEntryTest";
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
		AutomationID = "Login - StockEntryTest";
		DataSet = "PreReq_015";
				
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
private void loginFromDiffUser(){
		  
		  Login OPWorkFlowLogin = new Login(executeStep,verifications);
			OPWorkFlowLogin.logOut();
			DataSet = "TS_015";
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
	 public void StockEntryTestPreRequisite(){
			openBrowser();
			login();
			DataSet = "PreReq_015";
			AutomationID = "StockEntryTest";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test StockEntryTest - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			
			
			//1. Create a user by selecting the default store under 'Add User' and should have access to Stock Entry screen under 'User Roles'. 
						
			navigation.navigateToSettings(driver, "RolesAndUsersLink", "RolesAndUsersPage");
			executeStep.performAction(SeleniumActions.Click, "", "CreateNewUserLink");

			RolesAndUsers user = new RolesAndUsers(executeStep, verifications);
			user.createUser();
		/*	loginFromDiffUser();
			
			
			navigation.navigateToSettings(driver, "RolesAndUsersLink", "RolesAndUsersPage");
		
			
			user.SearchUser();
			user.EditUser();
			*/
			
			//2. In Supplier Masters, add 'Supplier_1' with Credit Period and Address In Supplier Masters
			
			navigation.navigateToSettings(driver, "SuppliersLink", "SuppliersListPage");	
			 executeStep.performAction(SeleniumActions.Click, "","AddNewSupplierLink");
			    
			SupplierDetails	supplier = new SupplierDetails(executeStep, verifications);
			supplier.createSupplier();
		}

         @Test
         public void DirectStockEntry(){
		  
		  DataSet = "TS_015";
		  AutomationID = "DirectStockEntry";
		  List<String> lineItemIDs = null;
		  System.out.println("Inside Test RosterWorkFlow - Before Navigation");
		  
		  executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
			

		    StockEntryModule stockentry = new StockEntryModule(executeStep,verifications);
		    stockentry.AddSupplierName();
		    CommonUtilities.delay(5000);
		    executeStep.performAction(SeleniumActions.Click, "", "StockEntryInvoiceNo");
		 //   EnvironmentSetup.useExcelScripts = false;
		    verifications.verify(SeleniumVerifications.Appears, "SupplierAddress","SupplierAddressValue2",false);
			 
		    testCaseStatus = "Pass";
		    System.out.println("Module Test TS_015 Completed");
	    
	    }
         @AfterClass
 		public void closeBrowser(){
 			String delimiter = " :: ";
 			//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
 			//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
 			ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "StockEntryTest", null, "", "", testCaseStatus);
 		//	driver.close();
 		}
}
