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
import reusableFunctions.inventory.GenericPreferences;
import reusableFunctions.inventory.PurchaseOrder;
import reusableFunctions.inventory.RolesAndUsers;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class StockEntryWithPoStrictPOEntry {
		WebDriver driver = null;
		KeywordSelectionLibrary executeStep;
		KeywordExecutionLibrary initDriver;
		VerificationFunctions verifications;
		ReportingFunctions reporter;
		String AutomationID;
		String DataSet;
		
		//@BeforeSuite
		//public void BeforeSuite(){
		  private void openBrowser(){
			System.out.println("BeforeSuite");
			AutomationID = "Login - StockEntryWithPoStrictPOEntry";
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
			AutomationID = "Login - StockEntryWithPoStrictPOEntry";
			DataSet = "PreReq_018";
					
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
			public void StockEntryWithPoStrictPOEntryPreRequisite(){
				openBrowser();
				login();
				DataSet = "PreReq_018";
				AutomationID = "StockEntryWithPoStrictPOEntry";
				List<String> lineItemIDs = null;
				System.out.println("StockEntryWithPoStrictPOEntry - Before Navigation");
				
				executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
				verifications = new VerificationFunctions(driver, AutomationID, DataSet);
				
				SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
				navigation.navigateToSettings(driver, "RolesAndUsersLink", "RolesAndUsersPage");
				
				RolesAndUsers roles = new RolesAndUsers(executeStep,verifications);
				roles.SearchUser();
				roles.EditUser();
				//roles.setDirectStockEntryNo();
				
				executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
				verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
				SiteNavigation navigationTo = new SiteNavigation(AutomationID, DataSet);
				navigationTo.navigateToSettings(driver, "GenericPreferencesLink", "GenericPreferencesPage");
				
				//1. Set stock Entry with PO
		  GenericPreferences genericPref= new GenericPreferences(executeStep, verifications);
            genericPref.SetStockEntryWithPO();
            genericPref.save();
            
          navigation.navigateTo(driver, "PurchaseOrderLink", "PurchaseOrderHeader");
			
	        PurchaseOrder purchaseOrder = new PurchaseOrder(executeStep, verifications);
			purchaseOrder.raisePurchaseOrder();
			purchaseOrder.addItemsToPurchaseOrder("PurchaseItem");
			purchaseOrder.Save();
		  }
		  
		//  @Test
		 // public void testing(){
			 // System.out.println("Testing Page");}
		  
			  @Test
				public void StockEntryWithPoStrictPOEntry(){
				  
					DataSet = "TS_018";
					AutomationID = "StockEntryWithPoStrictPOEntry";
					List<String> lineItemIDs = null;
					System.out.println("Inside Test RosterWorkFlow - Before Navigation");
					
					executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
					verifications = new VerificationFunctions(driver, AutomationID, DataSet);
					
					
					SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
					navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreenpage");
					

				    executeStep.performAction(SeleniumActions.Select, "StockEntryPoNum", "StockEntryPodropdown");
				    verifications.verify(SeleniumVerifications.Selected, "StockEntryPoNum","StockEntryPodropdown",false);
					
					executeStep.performAction(SeleniumActions.Select, "StockEntryInvoiceNoAdd", "StockEntryInvoiceNo");
					verifications.verify(SeleniumVerifications.Selected, "StockEntryInvoiceNoAdd","StockEntryInvoiceNo",false);
					
					executeStep.performAction(SeleniumActions.Click, "","DialogBlock");
					
					executeStep.performAction(SeleniumActions.Select, "BatchNoAdd", "BatchNoField");
					verifications.verify(SeleniumVerifications.Selected, "BatchNoAdd","BatchNoField",false);
					
					executeStep.performAction(SeleniumActions.Select, "ExpiryMonthAdd", "ExpiryMonthField");
					verifications.verify(SeleniumVerifications.Selected, "ExpiryMonthAdd","ExpiryMonthField",false);
					
					executeStep.performAction(SeleniumActions.Select, "ExpiryYearAdd", "ExpiryYearField");
					verifications.verify(SeleniumVerifications.Selected, "ExpiryYearAdd","ExpiryYearField",false);
					
					executeStep.performAction(SeleniumActions.Click, "","StockEntryScreenAddButton");
					
					executeStep.performAction(SeleniumActions.Select, "ExpiryYearAdd", "ExpiryYearField");
					verifications.verify(SeleniumVerifications.Selected, "ExpiryYearAdd","ExpiryYearField",false);
					
					verifications.verify(SeleniumVerifications.Appears, "","ItemGrid",true);
					
					executeStep.performAction(SeleniumActions.Click, "","SaveButton");	
					
					
}}


