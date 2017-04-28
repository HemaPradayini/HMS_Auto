package e2e;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DiagnosisDetails;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.Order;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.Registration;
import reusableFunctions.Sales;
import reusableFunctions.SalesReturn;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Triage;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class RegistrationFollowupSameDoctorWorkflow {

	
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
		AutomationID = "Login - RegistrationFollowupSameDoctorWorkflow";
		DataSet = "TS_076a";
		//EnvironmentSetup.testScenarioId = "TS_016";
		
		System.out.println("BeforeSuite");
		AutomationID = "Login - RegistrationFollowupSameDoctorWorkflow";
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
	public void RegistrationFollowupSameDoctorWorkflow(){
		openBrowser();
		login();
		DataSet = "TS_076a";
		AutomationID = "RegistrationFollowupSameDoctorWorkflow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test RegistrationFollowupSameDoctorWorkflow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
	
		//1.Go to center 1 and register a op patient be entering all the mandatory details by selecting consulting doctor
		//as 'Doctor A' & Bill type as 'Bill Later'.
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientGenericDetails();
		OPReg.setBillType();
		OPReg.visitInformationDetails();
		OPReg.storeDetails();
		
	//	2.Go to Out Patient List under Patient Module and access doctor consultaion screen by 
		//clicking on Consult link before paying the bill
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
		
		//3.Enter all required parameters in consultation/ Triage/ IA/ Generic forms Screens
		//4.Prescribe all types of items like medicines(serial and batch items having packet size 1 and 10) by
		//giving quantity as 5, Lab, rad, services, diag packages,activity and save the consultation screen
		DiagnosisDetails diagDetails = new DiagnosisDetails(executeStep, verifications);
		
		diagDetails.addDianosisDetails( "Diagnosis","ConsultationAndManagementPage");
		ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
		consultMgmt.addManagementItems();
		
		executeStep.performAction(SeleniumActions.Click, "","ConsulationMgtScreenTriageLink");
		verifications.verify(SeleniumVerifications.Appears, "","TriagePage",true);

		Triage triage = new Triage(executeStep, verifications);
		triage.saveTriage();

		OpenGenericForm opGenericForm = new OpenGenericForm(executeStep, verifications);
		opGenericForm.openGenericForm();
		
		//5.Go to bill order screen and observe the alert message of pending prescription
		//6.Click on 'OK' so that prescription with all hospital items will be loaded
		//7.Save the order screen such that all prescribed items are ordered
		Order order = new Order(executeStep, verifications);            // added by me now
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		order = new Order(executeStep, verifications);
		order.searchOrder(false,false); 
		order.saveOrder(false);
		
		//9. Go to sales screen under Sales and Issues. Enter MR number. Let patient's details get auto-populated. Select bill type as Add to Bill,
		//wait for the prescribed medicines to auto fill. Complete sales by clicking on Add to Bill & Print.
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.payAndPrint(false);
		
		
	// 10. Go to Sales Returns screen under Sales and Issues, enter the patient's MR number.
		//Add items to the grid by giving 3 quantity each. Click on Add to Bill & Print to complete sales returns.navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		salesReturns.doSalesReturn("SalesReturns","OP");
		 
		//11a. Go to Laboratory Pending samples screen, select the test and click on collect menu option. Select the samples by ticking the check boxes and click on Save.
/*		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		order = new Order(executeStep, verifications);
		order.searchOrder(true,false);                                           // added by Abhishek
		
		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
		verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
	*/
		
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
		//  executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		//  verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
		  
		//11b. Go to Lab Pending Samples Assertion, Search the sample using sample no. Enter Qty Rcd same as Qty Dlvd
		//and click on Assert button.
		navigation.navigateTo(driver, "LaboratoryPendingSampleAssertionsLink", "LaboratoryPendingSampleAssertionsPage");
		collectSamples.assertPendingSamples();
		
		//11c. Go to Laboratory reports screen > select the test and click on View/Edit, enter values (1,2,3,4,etc) complete,validate and Signoff.
	/*	
		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();
		*/                          //commented on 14/4
		
		
		navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LaboratoryReportsPageMRNo","LaboratoryReportsPageMRList","LaboratoryReportsPageSearchButton","LaboratoryReportsPage");
		executeStep.performAction(SeleniumActions.Click, "","LabReportsReportCheckBox");
		executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsPageResultTable");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);

		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();
		//11d. Go to Signed off report list > Select the report and click on Amend report menu option.
		//11e. In the conduction screen, click on Amend button for a result value , and enter new value in the new field.
		//11f. Again mark complete, validate and signoff.
		navigation.navigateTo(driver, "SignedOffReportLink", "SignedOffReportListPage");
		search.searchMRNo("PendingSampleAssertionsSearchMRID","PendingSampleAssertionsSearchMRIDContainer","PendingSampleAssertionsSearchButton","PendingSampleAssertionsTable");
		
		executeStep.performAction(SeleniumActions.Click, "","SignedOffReportListAlert");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSampleAssertionsTable",false);
		testConduction.amendReports();
		
		//12a.Go to radiology reports > Select the test and click on View/Edit, choose a template then edit details and save. Mark complete,validate and signoff.
		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
	
		
		//12b. Go to signed off list, select the report and click on Amend report menu option.
		//12c. In the conduction screen, click on Amend button and choose a new template,edit details and mark complete,validate and signoff.
		//12d.Go to Pending Services List, select the service and click on edit menu option mark completed and save.
		navigation.navigateTo(driver, "SignedOffReportsListLink", "SignedOffReportListPage");
		radiologyPendingTests.amendRadiology();
		
		// added today
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
	
		//13.Now logout and login as center2 user
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.logOut();
		AutomationID = "Login - RegistrationFollowupSameDoctorWorkflow";
		DataSet = "TS_076b";
		login();
		
		
		//14. Go to center 2 and register the same patient,observe the visit type in registration screen.
		AutomationID = "Login - RegistrationFollowupSameDoctorWorkflow";
		DataSet = "TS_076a";
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		SiteNavigation navigat = new SiteNavigation(AutomationID, DataSet);
		navigat.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		Registration Register = new Registration(executeStep, verifications);
		Register.patientFollowUpVisit(true);
		
		verifications.verify(SeleniumVerifications.Opens, "","PatientBillScreen",false);
        executeStep.performAction(SeleniumActions.Click, "", "PatientBillTour");
        verifications.verify(SeleniumVerifications.Appears, "","PatientBillBilledAmount",false);
        executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillScreen");
		verifications.verify(SeleniumVerifications.Closes, "","PatientBillScreen",false);
                          
		
       //15. Go to OP list ,select the patient and go to consultation screen by clicking on 
		//Consult menu option and observe the previous summary. 
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearchList = new OPListSearch(executeStep, verifications);
		OPSearchList.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");

		verifications.verify(SeleniumVerifications.Appears, "","ConsultAndManagementFieldSet",false);
		verifications.verify(SeleniumVerifications.Appears, "","ConsultAndManagementFieldSetPreviousVisitTable",false);
		
		executeStep.performAction(SeleniumActions.Click, "", "ConsultAndManagementFieldSetCloseBtn");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultationAndManagementPage",false);
		
		//16. Observe complaint,diagnosis field. Enter all the other details such as investigations,
		//services and consultation notes and save the consultation screen.
		ConsultationAndMgmt consultMagmt = new ConsultationAndMgmt(executeStep, verifications);
		consultMagmt.addManagementItems();
		
		//17. Observe the report in EMR.
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		testCaseStatus = "Pass";
		System.out.println("TS_076 - completed");
		System.out.println("Test Completed"); 
	}
	@AfterClass
	public void closeBrowser(){
		String delimiter = " :: ";
		//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
		//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "RegistrationFollowupSameDoctorWorkflow", null, "", "", testCaseStatus);
		driver.close();
	}
	
}
