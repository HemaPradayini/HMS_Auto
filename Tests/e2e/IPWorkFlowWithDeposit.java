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

import genericFunctions.DbFunctions;
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
public class IPWorkFlowWithDeposit {
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
		AutomationID = "Login - IPWorkFlowWithDeposit";
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
		AutomationID = "IPWorkFlowWithDeposit";
		DataSet = "TS_050";	
		
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

	public void IPWorkFlowWithDeposit(){
		openBrowser();
		login();
		
		DataSet = "TS_050";
		AutomationID = "IPWorkFlowWithDeposit";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test IPWorkFlow_1 - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet); 
		

		//Navigate To
			
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);	

		navigation.navigateTo(driver, "IPRegistrationLink", "IPRegistrationPage");
		
		//In IP registration, Enter all required information, and also select ward name , bed name.
		//From here its for Entering details on the IP Registration Screen 

		Registration IPReg = new Registration(executeStep, verifications);
		IPReg.RegisterPatientCashIP();
		
		// Go to bill and from bill navigate to order screen and order all billable items and save the screen..
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
			
		PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.addBillOrder("OrderItem");
		
		//Go to deposit collection screen under billing module.
		//Collect deposit say 2000 against General. Collect 5000 against IP deposit.	
		navigation.navigateTo(driver, "DepositsCollectionLink", "CollectRefundPage");
		
		Deposits Collection = new Deposits(executeStep, verifications);
		Collection.collectDeposit("General","");
		Collection.collectDeposit("IP","False");
		
		// Go to bill and observe the available Deposit.
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		//SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
			
	   //Add/Order all types of items in bill say billed amount is 8000.
		
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
		order.addOrderItem("OrderScreenItem", "OrdersPage");
		order.saveOrder(false);
		
		//6. Do deposit set off as 7000,by selecting IP Deposit Radio button in bill and observe the pateint due.
		//7. Try to do deposit set off more than available Deposit and observe the alert.
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
	//	SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		
	//	PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.depositOff("IP");
				
		//Now collect 4000 more deposit against IP.
		navigation.navigateTo(driver, "DepositsCollectionLink", "CollectRefundPage");
//		Deposits Collection = new Deposits(executeStep, verifications);
		Collection.collectDeposit("IP","True");
		
       //Do sales for items having package size=1,>1, claimable and non claimable items where split should 
		// happen for non claimable items also
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		//sales.doSales("IssueItem", "SalesPageBillType");
		//sales.closePrescriptionTab();
		
		//10. Select bill type as Bill Now and do Deposit set off say 2000(Item Total in Sales screen) in 
		//sales screen.
		sales.salesDepositOff("IssueItem","SalesPageBillType","IP");
		sales.closePrescriptionTab();

		//Go to deposit collect/refund screen and do refund
		navigation.navigateTo(driver, "DepositsCollectionLink", "CollectRefundPage");
	//	Deposits Collection = new Deposits(executeStep, verifications);
		Collection.refundDeposit("General");
		Collection.refundDeposit("IP");
	
		// Verify Patient Deposit in deposit dashboard by entering MR No. of patient.
		navigation.navigateTo(driver, "DepositsLink", "PatientDepositsPage");
		
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);	
		mrnoSearch.searchMRNo("PatientDepositsMRNoField","PatientDepositsMRNoList","PatientDepositsSearchButton","PatientDepositsPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientDepositsPage",false);		
		
		DbFunctions dbFunctions = new DbFunctions();
		dbFunctions.storeDate(this.executeStep.getDataSet(), "ShiftEndDate","C",0);
		
		// Now do bed finalization 
		navigation.navigateTo(driver, "ADTLink", "ADTPage");
		BedFinalisation FinaliseBed = new BedFinalisation(executeStep, verifications);
		FinaliseBed.finaliseBed();
	
		//  Do patient amount settlement in bill and close the bill.
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		
	//	PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.settleBills("NO", "UNPAID", "IP");
		
		testCaseStatus="PASS";
		System.out.println("End Test IPWF With Deposit");
		
	}
	
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_050", "IPWorkFlowWithDeposit", null, "", "", testCaseStatus);
		driver.close();
	}
		
}
