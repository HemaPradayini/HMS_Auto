package e2e;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.CollectSamples;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.Order;
import reusableFunctions.PatientBill;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Registration;
import reusableFunctions.Sales;
import reusableFunctions.SalesReturn;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author Abhishek
 *
 */
public class OPCashWorkflowWithDiscountPlan {
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
		AutomationID = "Login - OPCashWorkflowWithDiscountPlan";
		DataSet = "TS_052";
		
		System.out.println("BeforeSuite");
		AutomationID = "Login - OPCashWorkflowWithDiscountPlan";
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
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
	
	}
	
	@Test(groups={"E2E","Regression"})
	public void OPCashWorkflowWithDiscountPlan(){
		
		openBrowser();
		login();
		DataSet = "TS_052";
		AutomationID = "OPCashWorkflowWithDiscountPlan";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPCashWorkflowWithDiscountPlan - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		

		 //1. Register an OP cash patient by ordering all billable items from registration order grid,
	      //select bill type as 'Bill Later' and click on Register
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		Registration OPReg = new Registration(executeStep, verifications);
		 
		
		 OPReg.unCheckPrimarySponsor();
	       OPReg.RegisterPatientGenericDetails();
	      
	       OPReg.setBillType();
	       OPReg.visitInformationDetails();
		   
		
	      
	        Order order = new Order(this.executeStep, this.verifications);
			//executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenAddButton");
			//verifications.verify(SeleniumVerifications.Appears, "", "AddItemScreen", false);
			order.addOrderItem("RegOrder","RegistrationScreen");
			//executeStep.performAction(SeleniumActions.Select, "RatePlan","OPRScreenRatePlanField");
			//verifications.verify(SeleniumVerifications.Selected, "RatePlan","OPRScreenRatePlanField",false);
			OPReg.storeDetails();
		
			
			//2. Add discount plan in bill as DiscountPlan1
			//3. Save the bill
			executeStep.performAction(SeleniumActions.Click, "","ProceedToBillingLink");
			verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
			PatientBill patientBill = new PatientBill(executeStep, verifications);
		
			executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
			//4. Observe the rate,amount and discount at item level as well as total
			patientBill.addDiscountPlanAndSave();
			
			
			//5. Add/Order all billable items in bill and observe the rates, discounts and total Dues
			patientBill.addItemsIntoPatientBill();
			
			//6. Go to sales screen. Enter MR number. Select bill type as Add to Bill, add few medicines(having package size 1 and 10 and serial items)
			//by giving quantity as 5 each and complete sales by clicking on Add to Bill & Print.
			navigation.navigateTo(driver, "SalesLink", "SalesPage");
			
			Sales sales = new Sales(executeStep, verifications);
			sales.doSales("SalesItem", "SalesPageBillType");
			//sales.payAndPrint(true);
			sales.closePrescriptionTab();
			
			//7. Go to Sales Returns screen, enter the patient's MR number. Add items to the grid by giving 3 quantity each.
			//Click on Add to Bill & Print to complete sales returns.
			navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
			SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
			salesReturns.doSalesReturn("SalesReturns", "OP");
			
			
			//8. Go to Add Patient Indent screen under Sales and Issues, enter the MR Number. Raise a patient indent by adding serial and batch items 
			//(having packet size 1 and 10 and serial items) by giving quantity as 5. Save this indent in finalized satus.
			navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
			RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
			raisePatientIndent.savePatientIndent("IndentSales", true);
			
			//9. Go to Patient Indents List under Sales and Issues. Select the above genarated indent and click
			//on Sales.(Changed to issues)
			navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
			raisePatientIndent.searchPatientIndent();
			raisePatientIndent.savePatientIndentIssue(true);
			//10. Complete sales by clicking on Add to Bill & Print.
			//11. Go to Add Patient Return Indent screen under Sales and Issues, enter the MR number and add medicines by giving 3 quantity each. Save this indent in finalized status.
			navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");
			
			raisePatientIndent.savePatientIndent("ReturnedSalesInden", false);	
			executeStep.performAction(SeleniumActions.Accept, "","Dialog");
			
			//12. Go to Patient Indents List under Sales and Issues. Select the above genarated return indent and click on Sales Returns.(Changed to issues Return)
			navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
			raisePatientIndent.searchPatientIndent();
			raisePatientIndent.savePatientIndentIssue(false);
			executeStep.performAction(SeleniumActions.Accept, "","Dialog");
			
			//14. Click on apply in the bill and observe the discount for pharmacy and inventory items.
			navigation.navigateTo(driver, "OpenBills", "BillListPage");
			MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
			mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");
			patientBill.viewEditMenuClick();
			executeStep.performAction(SeleniumActions.Click, "","PatientBillApplyButton");
			
			patientBill.setBillStatus("NO");
			patientBill.settleBillDetails("NO", "SettlementPaymentType");
			
			
			
			
			//16.Go to Laboratory Pending samples screen, select the test and click on collect menu option.
			//Select the samples by ticking the check boxes and click on Save.
			navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
			order.searchOrder(false,false);
			
			//order.saveOrder(false);                       //added just now
			
			executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
			verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
			
			CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
			collectSamples.collectSamplesAndSave();
			executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
			verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
			
			//17 Go to laboratory reports screen > 
	//select the test and click on View/Edit, enter values (1,2,3,4,etc) complete,validate and Signoff.
//					
			navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
			MRNoSearch search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
			verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
					
			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
			verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
			TestsConduction testConduction = new TestsConduction(executeStep, verifications);
			testConduction.conductTests();
		
			
			//18. Go to Radiology reports screen, select the test and click on View/Edit menu option,
			//select a template, enter details and complete, validate and signoff.
			
			 navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
				RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
				radiologyPendingTests.radiologyPending(true);
				
				//19.Go to Pending Services List, select the service and click on edit menu option mark completed and save.
				navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
				ServicePending servicePending = new ServicePending(executeStep, verifications);
				servicePending.conductPendingServices();
			
			
								
				
			// 20 Go to Patient &Visit EMR and observe documents.
			navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
			search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
			verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
			
			testCaseStatus = "Pass";
			System.out.println("TS_052 - completed");
			
			
	}
	
	@AfterClass
	public void closeBrowser(){
		String delimiter = " :: ";
		//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
		//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OPCashWorkflowWithDiscountPlan", null, "", "", testCaseStatus);
		driver.close();
	}

}
