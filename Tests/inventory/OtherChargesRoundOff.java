package inventory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import GenericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.Login;
import reusableFunctions.SiteNavigation;
import reusableFunctions.inventory.ItemVerification;
import reusableFunctions.inventory.StockEntryModule;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class OtherChargesRoundOff {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus="FAIL";

	//@BeforeSuite
	//public void BeforeSuite(){
	  private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - OtherChargesRoundOff";
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

	// @BeforeMethod
	//public void BeforeTest(){
	  private void login(){
		AutomationID = "Login - OtherChargesRoundOff";
		DataSet = "PreReq_026";
				
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
	}
	 
	  @Test()
	  public void OtherChargesRoundOffTest(){
		  openBrowser();
			login();
			DataSet = "TS_026";
			AutomationID = "OtherChargesRoundOffTest";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test OtherChargesRoundOffTest - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
			navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
			StockEntryModule stockentry = new StockEntryModule(executeStep, verifications);
			stockentry.AddSupplierName();
			stockentry.Addinvoicenumber();
			stockentry.StockEntryCSTType();
			stockentry.StockEntryCSTRate();
			stockentry.addItemsToStocks("stockentryItem");
			stockentry.StockEntryRoundOff();
			stockentry.Save();
			
			//4. Ensure that item is added in the grid.
			ItemVerification itmVer = new ItemVerification(executeStep, verifications);
			itmVer.ItemsVerification("stockentryItem");

			//Expected Result for Tax amount and Total
			PurchaseOrderTotalAndTax("stockentryItem");
			
			testCaseStatus="PASS";
			System.out.println("OtherChargesRoundOffTest workflow");

	  }
	  
	  
	  public void PurchaseOrderTotalAndTax(String lineItemId){
			
			EnvironmentSetup.LineItemIdForExec = lineItemId;
			EnvironmentSetup.lineItemCount =0;
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

			EnvironmentSetup.UseLineItem = true;
			DbFunctions dbFunction = new DbFunctions();
			int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
			System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
			for(int i=1; i<=rowCount; i++){	
				EnvironmentSetup.UseLineItem = true;
				EnvironmentSetup.intGlobalLineItemCount= 1;
				verifications.verify(SeleniumVerifications.Appears, "Total","PurchaseOrderItemWithoutCBTaxBasisTotal",false);
				verifications.verify(SeleniumVerifications.Appears, "TaxAmt","PurchaseOrderItemWithoutCBTaxBasisTaxAmt",false);
		
				EnvironmentSetup.lineItemCount++;
				EnvironmentSetup.intGlobalLineItemCount++;
			}
			EnvironmentSetup.lineItemCount =0;
			EnvironmentSetup.intGlobalLineItemCount = 0;
			EnvironmentSetup.UseLineItem = false;
			

	  }	
	  
	  @AfterMethod
		public void closeBrowser(){
	  	reporter.UpdateTestCaseReport(AutomationID, DataSet, "workflow", null, "", "", testCaseStatus);
	    driver.close();
		}
	}



