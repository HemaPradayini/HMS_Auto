package e2e;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DiagnosisDetails;
import reusableFunctions.LabSamplesTransferManual;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.Order;
import reusableFunctions.PatientBill;
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

public class RegistrationFollowupDifferentDoctor {
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
		AutomationID = "Login - RegistrationFollowupDifferentDoctor";
		DataSet = "TS_077a";
		//EnvironmentSetup.testScenarioId = "TS_016";
		
		System.out.println("BeforeSuite");
		AutomationID = "Login - RegistrationFollowupDifferentDoctor";
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
	public void RegistrationFollowupDifferentDoctor(){
		openBrowser();
		login();
		DataSet = "TS_077a";
		AutomationID = "RegistrationFollowupDifferentDoctor";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test RegistrationFollowupDifferentDoctor - Before Navigation");
		
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
		DbFunctions db = new DbFunctions();
		
		
		db.saveMRIDVisitID("TS_077b");
		
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
		
		
		//7.a.Go to Lab pending samples screen , transfer to drop down should have internal lab selected by default.
		//Select the sample by clicking the check box and click on Save button.
	/*	executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
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
		
		//7.b.Go to Lab Pending sample assertion, search the sample using sample no and enter Rcd qty same as Dlvd qty,
		//select the check box and click on assert button.
		navigation.navigateTo(driver, "LaboratoryPendingSampleAssertionsLink", "LaboratoryPendingSampleAssertionsPage");
		collectSamples.assertPendingSamples();
		
		//7.c.Go to Lab Transfer samples manual screen, Search the samples by using sample no. 
		//Select the sample by clicking on the check box and mark transferred & print.
	/*	navigation.navigateTo(driver, "LaboratoryTransferSamplesManualLink", "LaboratoryTransferSamplesManualPage");
		
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PendingSampleAssertionsSearchMRID","PendingSampleAssertionsSearchMRIDContainer","PendingSampleAssertionsSearchButton","LaboratoryTransferSamplesManualPage");
		collectSamples.markTransferredLabSamples();
	*/                        //commented now
		
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		navigation.navigateTo(driver, "LabTransferSamplesManualLink", "LabTransferSamplesManualPage");
		LabSamplesTransferManual labTransfers = new LabSamplesTransferManual(executeStep, verifications);
		labTransfers.transferLabSamples();
		System.out.println("EnvironmentSetup.strGlobalException II is :: " + EnvironmentSetup.strGlobalException.toString());

		//8.Observe the rates and discounts at items level in bill
		navigation.navigateTo(driver, "OpenBills", "BillListPage");
		PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.advancedSearch(search, "OPEN_UNPAID_NONINS");
		patientBill.advanceBillNavigation();
		
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
		
		//11.Now logout and login as center2 user
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.logOut();
		AutomationID = "Login - RegistrationFollowupDifferentDoctor";
		DataSet = "TS_077b";
		login();
		//12. Go to center 2 and register the same patient by selecting consulting doctor other than doctor A
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		Registration Register = new Registration(executeStep, verifications);
		Register.patientFollowUpVisit(true);
		
		
		verifications.verify(SeleniumVerifications.Opens, "","PatientBillScreen",false);
        executeStep.performAction(SeleniumActions.Click, "", "PatientBillTour");
        verifications.verify(SeleniumVerifications.Appears, "","PatientBillBilledAmount",false);
        executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillScreen");
		verifications.verify(SeleniumVerifications.Closes, "","PatientBillScreen",false);
		                                                
		
		
		//13. Go to OP List, select the patient and click on Consult menu option observe the consultation screen.
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearchList = new OPListSearch(executeStep, verifications);
		OPSearchList.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");

		
		
		//14a. Observe complaint,diagnosis field. Enter all the other details such as investigations,
		//services and consultation notes and close the consultation .
		
		
		AutomationID = "Login - RegistrationFollowupDifferentDoctor";
		DataSet = "TS_077a";
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		SiteNavigation navigat = new SiteNavigation(AutomationID, DataSet);
		ConsultationAndMgmt consultMagmt = new ConsultationAndMgmt(executeStep, verifications);
		diagDetails.addDianosisDetails( "Diagnosis","ConsultationAndManagementPage");
		consultMagmt.addManagementItems();
		
		
		//************************************************88                 //added on 17/4
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenCloseConsultationCheckBox");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultationAndManagementPage",true);
		
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenSaveButton");
		System.out.println("Consultation Screen Saved");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultationAndManagementPage",false);
		//********************************************8
		
		
		
		//14b.Go to bill order screen and observe the alert message of pending prescription
		//14c.Click on 'OK' so that prescription with all hospital items will be loaded
		//14d.Save the order screen such that all prescribed items are ordered
		navigat.navigateTo(driver, "OrdersLink", "OrdersPage");
		order = new Order(executeStep, verifications);
		order.searchOrder(false,false); 
		order.saveOrder(false);
		
		//15.a.Go to Lab receive samples manual screen> Search the sample using sample no.
		//15.b.Select the samples by selecting the check box and click on Mark received and print.
		navigation.navigateTo(driver, "LaboratoryReceiveSamplesLink", "LaboratoryReceiveSamplesPage");
		//LabSamplesTransferManual labTransfers = new LabSamplesTransferManual(executeStep, verifications);
		labTransfers.receiveSamples();
		
		//15.c.Go to Lab pending assertion screen, search the sample, select the sample, enter Rcd qty same as dlv qty and click on Assert button.
		navigation.navigateTo(driver, "LaboratoryPendingSampleAssertionsLink", "LaboratoryPendingSampleAssertionsPage");
		collectSamples.assertPendingSamples();
		
		
		//15.d.Go to laboratory reports screen > select the test and click on View/Edit, enter values (1,2,3,4,etc) complete,validate and Signoff.
	/*	navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
		*/                                                      //commented on 14/4
		navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LaboratoryReportsPageMRNo","LaboratoryReportsPageMRList","LaboratoryReportsPageSearchButton","LaboratoryReportsPage");
		executeStep.performAction(SeleniumActions.Click, "","LabReportsReportCheckBox");
		executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsPageResultTable");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);

		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		
		
		testConduction.saveConductionResults();
		executeStep.performAction(SeleniumActions.Click, "","TestConductionSignOffArrow");
		testConduction.signOff();
		
		
		
		//15.e. Go to Signed off List, select the report and click on Add Addendum.
		//15.f. From Manage report screen click on Addendum link , enter the notes in the free text editor and save.
		//Click Save button in Manage report screen
		navigation.navigateTo(driver, "SignedOffReportLink", "SignedOffReportListPage");
		search.searchMRNo("PendingSampleAssertionsSearchMRID","PendingSampleAssertionsSearchMRIDContainer","PendingSampleAssertionsSearchButton","PendingSampleAssertionsTable");
		
		executeStep.performAction(SeleniumActions.Click, "","SignedOffReportListAlert");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSampleAssertionsTable",false);
		testConduction.addAddendum();
		
		//15.g.Go to Signed off list ,select the report and click on Sign off Addendum.
		navigation.navigateTo(driver, "SignedOffReportLink", "SignedOffReportListPage");
		search.searchMRNo("PendingSampleAssertionsSearchMRID","PendingSampleAssertionsSearchMRIDContainer","PendingSampleAssertionsSearchButton","PendingSampleAssertionsTable");
		testConduction.signOffAddendum();
	
		//15.h.Go to radiology reports > Select the test and click on View/Edit, choose a template then edit details and save. Mark complete,validate and signoff.
		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
		
		//15.i.Go to Signed off List, select the report and click on Add Addendum.
		//15.j. From Manage report screen click on Addendum link , enter the notes in the free text editor and save.
		//15.k. Go to Signed off list ,select the report and click on Sign off Addendum.
		navigation.navigateTo(driver, "SignedOffReportsListLink", "SignedOffReportListPage");
		search.searchMRNo("PendingSampleAssertionsSearchMRID","PendingSampleAssertionsSearchMRIDContainer","PendingSampleAssertionsSearchButton","PendingSampleAssertionsTable");
		
		testConduction.addAddendum();
	
		navigation.navigateTo(driver, "SignedOffReportsListLink", "SignedOffReportListPage");
		search.searchMRNo("PendingSampleAssertionsSearchMRID","PendingSampleAssertionsSearchMRIDContainer","PendingSampleAssertionsSearchButton","PendingSampleAssertionsTable");
		testConduction.signOffAddendum();
	
		//15.l.Go to Pending Services List, select the service and click on edit menu option mark completed and save.
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
		
		//16. Go to EMR and check reports.
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		testCaseStatus = "Pass";
		System.out.println("TS_077 - completed");
	
	}
	
	@AfterClass
	public void closeBrowser(){
		String delimiter = " :: ";
		//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
		//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "RegistrationFollowupDifferentDoctor", null, "", "", testCaseStatus);
		driver.close();
	}
}
