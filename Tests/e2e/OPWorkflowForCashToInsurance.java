/**
 * 
 */
package e2e;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.ChangeRatePlanBedType;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientEMR;
import reusableFunctions.PatientInsurance;
import reusableFunctions.PatientVisit;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Registration;
import reusableFunctions.Sales;
import reusableFunctions.SalesReturn;
import reusableFunctions.SearchBillList;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Triage;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

/**
 * @author Tejaswini - Changed 26/02/2017
 *
 */
public class OPWorkflowForCashToInsurance {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus = "Fail";
	
//	@BeforeSuite
//	public void BeforeSuite(){
	private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - OPWorkflowForCashToInsurance";
        long id = Thread.currentThread().getId();
        System.out.println("Before suite-method in OPWorkflowForCashToInsurance. Thread id is: " + id);
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
	
//	@BeforeMethod
//	public void BeforeTest(){
	private void login(){
		AutomationID = "Login - OPWorkflowForCashToInsurance";
		DataSet = "TS_011";
		//EnvironmentSetup.testScenarioId = "TS_001";
        long id = Thread.currentThread().getId();
        System.out.println("Before test-method in OPWorkflowForCashToInsurance. Thread id is: " + id);
		
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		//executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void OPWorkflowForCashToInsurance(){
		openBrowser();
		login();
		DataSet = "TS_011";
		AutomationID = "OPWorkflowForCashToInsurance";
		
        long id = Thread.currentThread().getId();
        System.out.println("@Test -method in OPWorkflowForCashToInsurance. Thread id is: " + id);

		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForBillNow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		//Step 1
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
	
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientOP();
		
		//Step 3 to 5
		
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
		
		ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
		consultMgmt.saveConsultationAndMgmt("Diagnosis");
		
		executeStep.performAction(SeleniumActions.Click, "","ConsulationMgtScreenTriageLink");
		verifications.verify(SeleniumVerifications.Appears, "","TriagePage",true);

		Triage triage = new Triage(executeStep, verifications);
		triage.saveTriage();

		OpenGenericForm opGenericForm = new OpenGenericForm(executeStep, verifications);
		opGenericForm.openGenericForm();
		
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPSearch = new OPListSearch(executeStep, verifications);		
		OPSearch.searchOPList("OPListScreenIntialAssessmentLink", "InitialAssessmentPage");
		
		executeStep.performAction(SeleniumActions.Click, "","InitialAssessmentSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","InitialAssessmentPage",true);

		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
	
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
		
		order.saveOrder(false);
		executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenBillLink");
		
		//Step 10
		
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.searchMRNo();
		sales.doSales("", "AddToBillPaymentType");
		sales.closePrescriptionTab();
		
		//Step 12
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		salesReturns.doSalesReturn("PharmacyReturns","OP");		

		//Step 13
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		MRNoSearch mrNoSearch = new MRNoSearch(executeStep, verifications);
		mrNoSearch.searchMRNo("RaisePatientIndentMRNoField", "RaisePatientIndentMRNoList", "RaisePatientIndentMRNoFindButton", "RaisePatientIndentPage");		
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("FirstIndent",true);		
		
		//Step 14
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.searchPatientIndent();		
		raisePatientIndent.savePatientIndentIssue(true);
		
		//Step 15
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");		
		RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("FirstIndent", false);

		//Step 16
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.searchPatientIndent();
		raisePatientIndent.savePatientIndentIssue(false);
		
		navigation.navigateTo(driver, "OpenBills", "BillListPage");
//		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
//		searchBillList.searchBills();
		PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.viewEditBills("BL");
		
		executeStep.performAction(SeleniumActions.Click, "", "PatientBillAddEditPatientInsuranceLink");
		verifications.verify(SeleniumVerifications.Appears, "", "AddEditPatientInsurancePage", false);
		
		PatientInsurance patientIns = new PatientInsurance(executeStep, verifications);
		patientIns.savePatientInsuranceDetails();

		executeStep.performAction(SeleniumActions.Click, "", "ConnectToInsLink");
		verifications.verify(SeleniumVerifications.Appears, "", "ConnectToInsPage", false);

		executeStep.performAction(SeleniumActions.Click, "", "ConnectToInsPageOkBtn");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
		verifications.verify(SeleniumVerifications.Appears, "", "PatientBillScreen", false);
		
		ChangeRatePlanBedType changeRatePlan = new ChangeRatePlanBedType(executeStep, verifications);

		changeRatePlan.changeAndSaveRatePlanAndBedType();

		executeStep.performAction(SeleniumActions.Click, "","ChangeRatePlanBillLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
		
		performOrderedTestsAndSignOffReports(navigation);
		
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);

		navigation.navigateTo(driver, "VisitEMRLink", "VisitEMRPage");
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
		mrnoSearch.searchMRNo("VisitEMRMRNoField","VisitEMRMRNoLi","VisitEMRGetDetailsBtn","VisitEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","VisitEMRPage",false);
		
		navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
		//PatientBill patientBill = new PatientBill(executeStep, verifications);			
		//MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");			
		patientBill.settleBills("YES", "UNPAID", "OP");

		//
		navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
		//PatientBill patientBill = new PatientBill(executeStep, verifications);			
		//MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");	
		//PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.sponsorAmountSettlement("");
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");			
		//PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.closeBill("FINALIZED_INS");
		
	   	/*navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
		//MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
	   	//PatientBill patientBill = new PatientBill(executeStep, verifications);
	   	patientBill.advancedSearch(mrnoSearch,"REFUND");
	   	patientBill.viewEditMenuClick();
		patientBill.settleBillDetails("NO", "BillRefundPaymentType");*/
		
		testCaseStatus = "Pass";
		
		System.out.println("TS_011 completed Successfully");

	}
	


	public void performOrderedTestsAndSignOffReports(SiteNavigation navigation){
		
		//12a Go to Laboratory Pending samples screen, select the test and click on collect menu option. Select the samples by ticking the check boxes and click on Save.
		//12b. Go to laboratory reports screen > select the test and click on View/Edit menu option, enter values (1,2,3,4,etc) complete,validate and Signoff.
		WebElement we = null;
		
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);

		try{
			we = this.executeStep.getDriver().findElement(By.xpath("//td[contains(text(), 'Laboratory')]"));		
		} catch (Exception e){
			System.out.println("Exception is :: " + e.toString());
		}
		if (we !=null){
			System.out.println("Lab Exists");
			executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
			verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
			CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
			collectSamples.collectSamplesAndSave();
			executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
			verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
			
			navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
			MRNoSearch search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");

			PatientBill patientBills = new PatientBill(executeStep, verifications);
			int noOfPendingTests = patientBills.getNoOfBills();
			for (int i=0; i< noOfPendingTests; i++){
				System.out.println("Value of i is :: " + i);
				System.out.println("no of pending bills is :: " + noOfPendingTests);
				executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
				verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);			
				executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
				verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
				TestsConduction testConduction = new TestsConduction(executeStep, verifications);
				testConduction.conductTests();
				navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
				search = new MRNoSearch(executeStep, verifications);
				search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
				noOfPendingTests = patientBills.getNoOfBills();
			}			
		}
		
		//12c. Go to radiology reports > Select the test and click on View/Edit menu option, choose a template then edit details and save. Mark complete,validate and signoff.
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
		try{
			we = null;
			we = this.executeStep.getDriver().findElement(By.xpath("//td[contains(text(), 'Radiology')]"));		
		} catch (Exception e){
			System.out.println("Exception is :: " + e.toString());
		}
		if (we != null){
			System.out.println("Radiology Exists");
			navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
			RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
			radiologyPendingTests.radiologyPending(true);
		}

		try{
			we = null;
			we = this.executeStep.getDriver().findElement(By.xpath("//td[contains(text(), 'Service')]"));		
		} catch (Exception e){
			System.out.println("Exception is :: " + e.toString());
		}

		//12d. Go to pending services list > Select the service and click on edit menu option, mark completed and save.
		if (we!=null){
			System.out.println("Service Exists");
			navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
			ServicePending servicePending = new ServicePending(executeStep, verifications);
			servicePending.conductPendingServices();
		}
	} // Tejaswini Tested

	@AfterClass
	public void closeBroswer(){
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OPWorkflowForCashToInsurance", null, "", "", testCaseStatus);
		driver.close();
	}
	
}
