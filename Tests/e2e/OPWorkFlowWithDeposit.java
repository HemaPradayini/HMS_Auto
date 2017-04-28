/**
 * 
 */
package e2e;

import java.util.List;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.BedAllocation;
import reusableFunctions.BedFinalisation;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.Deposits;
import reusableFunctions.DischargeSummary;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.DrOrder;
import reusableFunctions.Notes;
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.Registration;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.OperationScheduler;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientEMR;
import reusableFunctions.PatientWard;
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Sales;
import reusableFunctions.SearchBillList;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Triage;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author C N Alamelu
 *
 */
public class OPWorkFlowWithDeposit {
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
		AutomationID = "Login - OPWorkFlowWithDeposit";
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
		AutomationID = "OPWorkFlowWithDeposit";
		DataSet = "TS_049";	
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();
		System.out.println("After Login");
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
	//	verifications.verify(SeleniumVerifications.Appears, "", "NavigationMenu", false); 
	}
	
	@Test(groups={"E2E","Regression"})

	public void OPWorkFlowWithDeposit(){
		openBrowser();
		login();
		
		DataSet = "TS_049";
		AutomationID = "OPWorkFlowWithDeposit";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test IPWorkFlow_1 - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet); 

		//Navigate To
		
		// System.out.println("Inside Test IPWorkFlow_1 - Before Navigation");
			
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);		
	
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		//Register an OP patient by ordering all billable items from registration order grid

		Registration IPReg = new Registration(executeStep, verifications);
		IPReg.RegisterPatientOP();
	
		
		 //.Go to deposit collection screen under billing module
		 //Collect deposit by entering some amt say 5000 , Collect 1000 against 
		 //IP deposit by checking Applicable to IP check box in Payment section.	
		
		navigation.navigateTo(driver, "DepositsCollectionLink", "CollectRefundPage");
		Deposits Collection = new Deposits(executeStep, verifications);
		Collection.collectDeposit("General","");
		Collection.collectDeposit("IP","False");	
		
		// Go to bill and observe the available Deposit.
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		
	   //Add/Order all types of items in bill say billed amount is 8000.
		
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
		order.addOrderItem("OrderScreenItem", "OrdersPage");
		order.saveOrder(false);
		
		//Do deposit set off in the bill and observe the patient due.
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		//SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		
		PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.depositOff("OP");
	
		
		//Now collect more deposit against IP.
		navigation.navigateTo(driver, "DepositsCollectionLink", "CollectRefundPage");
	//	Deposits Collection = new Deposits(executeStep, verifications);
		Collection.collectDeposit("General","True");
		
			
       //Do sales for items having package size=1,>1, claimable and non claimable items where split should happen for non claimable items also
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.salesDepositOff("IssueItem","SalesPageBillType","OP");
		sales.closePrescriptionTab();
	
		//Go to deposit collect/refund screen and do refund
		navigation.navigateTo(driver, "DepositsCollectionLink", "CollectRefundPage");
	//	Deposits Collection = new Deposits(executeStep, verifications);
		Collection.refundDeposit("General");
		//Collection.refundDeposit("IP");
	
		// Verify Patient Deposit in deposit dashboard by entering MR No. of patient.
		navigation.navigateTo(driver, "DepositsLink", "PatientDepositsPage");
		
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);	
		mrnoSearch.searchMRNo("PatientDepositsMRNoField","PatientDepositsMRNoList","PatientDepositsSearchButton","PatientDepositsPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientDepositsPage",false);
		
		//  Do patient amount settlement in bill and close the bill.
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
	//	PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.settleBills("NO", "UNPAID", "OP");
		
		testCaseStatus="PASS";
		System.out.println("OP Workflow with deposit");
		
	}
	
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_049", "OPWorkFlowWithDeposit", null, "", "", testCaseStatus);
		driver.close();
	}
		
}
