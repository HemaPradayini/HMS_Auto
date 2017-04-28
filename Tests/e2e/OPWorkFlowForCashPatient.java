/**
 * 
 */
package e2e;

import java.util.List;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
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
import reusableFunctions.PatientBillOSP;
import reusableFunctions.PatientEMR;
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

/**
 * @author Sai
 *
 */
public class OPWorkFlowForCashPatient {
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
        long id = Thread.currentThread().getId();
        System.out.println("Before suite-method in OPWorkFlowForCashPatient. Thread id is: " + id);
		AutomationID = "Login - OPWorkFlowForCashPatient";
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
		AutomationID = "Login - AppointmentToBilling";
		DataSet = "TS_003";
		//EnvironmentSetup.testScenarioId = "TS_001";
        long id = Thread.currentThread().getId();
        System.out.println("Before test-method in OPWorkFlowForCashPatient. Thread id is: " + id);
		
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
	public void OPWorkFlowForCashPatient(){
		openBrowser();
		login();
		
		DataSet = "TS_003";
		AutomationID = "OPWorkFlowForCashPatient";
		long id = Thread.currentThread().getId();
        System.out.println("@Test -method in OPWorkFlowForCashPatient. Thread id is: " + id);
		
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForCashPatient_1 - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "DoctorSchedulerLink", "DoctorSchedulerPage");
		//Step 1 - 3
		DoctorScheduler OPWorkFlowDocScheduler = new DoctorScheduler(executeStep,verifications);
		OPWorkFlowDocScheduler.scheduleDoctorAppointment();
		
		//From here its for Marking as Patient arrived. move it to another reusable
		PatientArrival OPPatientArrival = new PatientArrival(executeStep,verifications);
		OPPatientArrival.markPatientAsArrived();
		
		//Step 4-6
		//From here its for Entering details on the OP Registration Screen - Move to another reusable
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientGenericDetails();
		OPReg.unCheckPrimarySponsor();
		OPReg.setBillType();
		OPReg.storeDetails();

		//Step 7		
		//This is for navigation to the OP List and searching with the MR No on the OPList and navigating to the consultation and management screen
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
		
		//Step 8
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

		//Step 10-12
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
	
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
		order.saveOrder(false);

//		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
//		verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
//		
//		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
//		collectSamples.collectSamplesAndSave();
//		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
//		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
		
		//Step 13		
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.payAndPrint(false);
		
		//Step 15
		navigation.navigateTo(driver, "SalesReturnLink", "SalesPage");
		SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		salesReturns.doSalesReturn("SalesReturns","OP");
		
		//Step 16
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		MRNoSearch mrNoSearch = new MRNoSearch(executeStep, verifications);
		mrNoSearch.searchMRNo("RaisePatientIndentMRNoField", "RaisePatientIndentMRNoList", "RaisePatientIndentMRNoFindButton", "RaisePatientIndentPage");
		
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentSales",true);
		
		//Step 17
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");

		raisePatientIndent.doSalesOrSalesReturnsForIndent(true, "");
		
/*		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		//Sales sales = new Sales(executeStep, verifications);
		sales = new Sales(executeStep, verifications);
		sales.payAndPrint(false);*/ 
		
		//Step 18
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");
		
		RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("ReturnedSalesIndent", false);
		
		//Step 19
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		raisePatientIndent.doSalesOrSalesReturnsForIndent(false, "");
		
		//Step 20
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		/*SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();*/
		PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.viewEditBills("BL");
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		
		//Step 21
		ChangeRatePlanBedType chngRatePlanBedType = new ChangeRatePlanBedType(executeStep, verifications);
		chngRatePlanBedType.changeAndSaveRatePlanAndBedType();

		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		/*SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();*/

		//Step 22
		patientBill = new PatientBill(executeStep, verifications);
		patientBill.viewEditBills("BL");
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");

		//Step 23
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
		
		//Step 24
		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();
		
		//Step 25
		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);

		//Step 26
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();

		//Step 27
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		
		testCaseStatus = "Pass";
		System.out.println("TS_003 completed Successfully");
		

	}
	@AfterTest
	public void closeBrowser(){
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OPWorkFlowForCashPatient", null, "", "", testCaseStatus);
		driver.close();
	}
}
