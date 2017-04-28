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
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.Registration;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Sales;
import reusableFunctions.SalesReturn;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Triage;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;
/**
 * @author Abhishek
 *
 */
public class OPWorkFlowForFollowUpVisit {

	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus = "Fail";
//	@BeforeSuite
	//public void BeforeSuite(){
	private void openBrowser(){
	AutomationID = "Login - OPWorkFlowForFollowUpVisit";
	DataSet = "TS_004";
	//EnvironmentSetup.testScenarioId = "TS_001";
	
	System.out.println("BeforeSuite");
	AutomationID = "Login - OPWorkFlowForFollowUpVisit";
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
		
		//executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void OPWorkFlowForFollowUpVisit() throws InterruptedException{
		
		openBrowser();
		login();
		DataSet = "TS_004";
		AutomationID = "OPWorkFlowForFollowUpVisit";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForFollowUpVisit_1 - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

	
		//. Go to scheduler module and click on Doctor Scheduler.
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	navigation.navigateTo(driver, "DoctorSchedulerLink", "DoctorSchedulerPage");
		
		// Click on any of the available slot under Doc1 And then Add/Edit menu option.
		// Enter the Name, mobile number, Doctor, time, duration and then click on OK
		DoctorScheduler OPWorkFlowDocScheduler = new DoctorScheduler(executeStep,verifications);
		OPWorkFlowDocScheduler.scheduleDoctorAppointment();
		
		//. Click on the slot and then on Arrived menu option
		PatientArrival OPPatientArrival = new PatientArrival(executeStep,verifications);
		OPPatientArrival.markPatientAsArrived();		

		//5. Enter all patient level and visit level information (all mandatory fields) and select bill type as Bill Later
		//6. Click on  Register.
		
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.comingFromDrScheduler();
		OPReg.GovtIDDetailsCollapsedPanel();
		//OPReg.RegisterPatientGenericDetails();
		OPReg.setBillType();
		OPReg.unCheckPrimarySponsor();
		executeStep.performAction(SeleniumActions.Select, "RatePlan","OPRScreenRatePlanField");
		verifications.verify(SeleniumVerifications.Selected, "RatePlan","OPRScreenRatePlanField",false);
		//OPReg.visitInformationDetails();
		
		OPReg.storeDetails();
        
		
		//7. Go to Out Patient List and try to access doctor consultaion screen before paying the bill.
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
		
		ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
		
		//Prescribe all types of items like medicines(serial and batch items having packet size 1 and 10) with quantity as 5,Lab,rad,services,diag packages and save the consultation screen
		consultMgmt.saveConsultationAndMgmt("Diagnosis");
		
		
		// Enter all required sections / fields in consultation/ IA / Triage/ generic forms
		executeStep.performAction(SeleniumActions.Click, "","ConsulationMgtScreenTriageLink");
		verifications.verify(SeleniumVerifications.Appears, "","TriagePage",true);

		Triage triage = new Triage(executeStep, verifications);
		triage.saveTriage();

		OpenGenericForm opGenericForm = new OpenGenericForm(executeStep, verifications);
		opGenericForm.openGenericForm();
		
		
		
		// Copy the MR_no and navigate to OP Registration screen, paste the MR_No in mr_no field in registration screen for follow up.
	   //User should be alerted in OP registration screen that Pending Prescription exists do you want to order or not. 
		// Clicking on ok button the pending prescriptions will be auto filled in the registration order section. Select consultation type
		
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		OPReg.patientFollowUpVisit(true);
		
		//executeStep.performAction(SeleniumActions.Click, "", "OPRSuccessProceedToBillingBtn");
		
		
        verifications.verify(SeleniumVerifications.Opens, "","PatientBillScreen",false);
        executeStep.performAction(SeleniumActions.Click, "", "PatientBillTour");
        verifications.verify(SeleniumVerifications.Appears, "","PatientBillBilledAmount",false);
        
    //******************************************************************************************************    
        // Here implement the code to verify the items in the followup visit function
        //
        //In order section observe that all items other than medicines will be populated.
        //(Done:Verification need to be added)
        //Do Registration and then observe all item level parameters and totals in bill
  //*************ALL THE ABOVE COMMENTS ARE DONE  OR NEEDS TO BE DONE IN patientFollowUpVisit();****************     
        
		executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillScreen");
		verifications.verify(SeleniumVerifications.Closes, "","PatientBillScreen",false);
		
		
		
		//14. Go to consultation/IA/Triage/Generic forms and observe the data pre-populated.
		
		//Needs to be done
		
		//. Go to sales screen and observe that medicines prescribed in previous visit don't auto-fill.
		//****Bill evel verification to be added
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.searchMRNo();
		executeStep.performAction(SeleniumActions.Click, "", "SalesAddItemCloseImg");  
		// check this step 
		//verifications.verify(SeleniumVerifications.Appears, "","PatientBillBilledAmount",false);
		
		
		//Prescribe few medicines(serial and batch items having packet size 1 and 10)
		//with quantity as 5 in follow up visit and go to sales screen. Enter MR number.
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
		
		verifications.verify(SeleniumVerifications.Appears, "","ConsultAndManagementFieldSet",false);
		verifications.verify(SeleniumVerifications.Appears, "","ConsultAndManagementFieldSetPreviousVisitTable",false);
		
		executeStep.performAction(SeleniumActions.Click, "", "ConsultAndManagementFieldSetCloseBtn");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultationAndManagementPage",false);
		
		// for follow up visit filling the details
		consultMgmt.saveConsultationAndMgmt("FollowUpDiag");
	
		
		/*
		Registration OPReg = new Registration(executeStep, verifications);	
		Sales sales = new Sales(executeStep, verifications);
		*/	
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		sales.payAndPrint(false);
		
		
	// #18
		// Go to Sales Returns screen, enter the patient's MR number. Add items to the grid by giving 3 quantiity each. Click on Add to Bill.
		
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		salesReturns.doSalesReturn("SalesReturns","OP");
		
	// #19
//Raise a patient indent by adding serial and batch items (having packet size 1 and 10).
	    navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentSales", true);
				

// Do sales/issues for this patient indent.
	/*	navigation.navigateTo(driver, "SalesLink", "SalesPage");
		
		sales = new Sales(executeStep, verifications);
				sales.payAndPrint(false);
	*/
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");    //changed 13/4
		raisePatientIndent.doSalesOrSalesReturnsForIndent(true, "IndentSales");
				
	//Raise a return indent by giving partial/full quantity for the above sold/issued items.
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");
		
		raisePatientIndent.savePatientIndent("ReturnedSalesInden", false);			
	
	// Do sales return for this patient indent	
	/*	navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		
		salesReturns.doSalesReturn("SalesReturns","OP");
	*/
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");//changed 13/4
		raisePatientIndent.doSalesOrSalesReturnsForIndent(false, "");
		
		
		// Step 24
	/*
		Order order = new Order(executeStep, verifications);            // added by me now
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		order = new Order(executeStep, verifications);
		order.searchOrder(true,false);                                           // added by Abhishek
		
		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
		verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
		*/                        //commented on 14/4
		
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");

		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);

		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);

		//collect samples and save
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
		
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		
		// step 25
	/*	
		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
		*/                                        //commented on 14/4
		
		navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
		//MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LaboratoryReportsPageMRNo","LaboratoryReportsPageMRList","LaboratoryReportsPageSearchButton","LaboratoryReportsPage");
		executeStep.performAction(SeleniumActions.Click, "","LabReportsReportCheckBox");
		executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsPageResultTable");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);

		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();
		
	
		//26 Complete,validate and sign off the pending radiology test
	    navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
		
		//27 Complete the pending service test
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
		
		
		// step 28
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		testCaseStatus = "Pass";
		System.out.println("TS_004 - completed");
		System.out.println("Test Completed");
	} 
	
	@AfterClass
	public void closeBrowser(){
		String delimiter = " :: ";
		//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
		//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OPWorkFlowForFollowUpVisit", null, "", "", testCaseStatus);
		driver.close();
	}
		
	
}
