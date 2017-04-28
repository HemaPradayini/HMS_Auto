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

import genericFunctions.CommonUtilities;
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
 * @author Tejaswini
 *
 */
public class OPWorkFlowForCreditPatient {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus="FAIL";
	
//	@BeforeSuite
//	public void BeforeSuite(){
	private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - OPWorkFlowForCreditPatient";
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
		AutomationID = "Login - OPWorkFlowForCreditPatient";
		DataSet = "TS_002";
		//EnvironmentSetup.testScenarioId = "TS_001";
		
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
	public void OPWorkFlowForCreditPatient(){
		openBrowser();
		login();
		DataSet = "TS_002";
		AutomationID = "OPWorkFlowForCreditPatient";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForCreditPatient_1 - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//Pre-Register the Patient and capture the OPID and store
		
		//Navigate To OP Registration Screen
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "PreRegistrationLink", "PreRegistrationPage");
		

		//From here its for Entering details on the OP Registration Screen - Move to another reusable
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.PreRegisterOP("PREREG");
		OPReg.savePreRegister();
		
		//This is for navigation to the OP List and searching with the MR No on the OPList and navigating to the consultation and management screen
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		//Registration OPReg = new Registration(executeStep, verifications);
		OPReg.retrievePatientDetails();		
		//OPReg.RegisterPatientGenericDetails();
		executeStep.performAction(SeleniumActions.Select, "RatePlan","OPRScreenRatePlanField");
		verifications.verify(SeleniumVerifications.Selected, "RatePlan","OPRScreenRatePlanField",false);
		OPReg.setBillType();
		OPReg.unCheckPrimarySponsor();
		OPReg.visitInformationDetails();
		OPReg.storeDetails();
		
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
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

//		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
//		verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
//		
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		CommonUtilities.delay(3000);
		//executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
		
		//executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		//verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
		
//		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
//		MRNoSearch search = new MRNoSearch(executeStep, verifications);
//		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
//		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
//		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
//		
//		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
//		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
//		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
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

		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
		
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
		
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		ChangeRatePlanBedType chngRatePlanBedType = new ChangeRatePlanBedType(executeStep, verifications);
		chngRatePlanBedType.changeAndSaveRatePlanAndBedType();
		
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.payAndPrint(true);
		sales.closePrescriptionTab();
		
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		salesReturns.doSalesReturn("MedicineSalesReturns", "OP");
		
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		MRNoSearch mrNoSearch = new MRNoSearch(executeStep, verifications);
		mrNoSearch.searchMRNo("RaisePatientIndentMRNoField", "RaisePatientIndentMRNoList", "RaisePatientIndentMRNoFindButton", "RaisePatientIndentPage");
		
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("Indent",true);
	
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.doSalesOrSalesReturnsForIndent(true, "");
		
		//sales.searchMRNo();
		//sales.doSales("", "RaiseBillPayment");
		//new prescription tab is opening up on the new server. Part of config settings?? - Tejaswini

		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");		
		RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("Indent", false);		 //Patient Return indent not displaying any item that can be returned... Bug??
		
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//MRNoSearch mrNoSearch = new MRNoSearch(executeStep, verifications);
		//RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.doSalesOrSalesReturnsForIndent(false, "");		 //Patient Return indent not displaying any item that can be returned... Bug??
		//SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		//salesReturns.doSalesReturn("Indent","OP");
 		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		PatientBill patientBill = new PatientBill(executeStep, verifications);		
		doAdvancePayment();
		patientBill.editReceipt();
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		//PatientBill patientBill = new PatientBill(executeStep, verifications);	
	   	patientBill.viewEditBills("BL");
	   	patientBill.setBillStatus("NO");
	   	patientBill.settleBillDetails("NO", "SettlementPaymentType");
		
		/*
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		//PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.settleBills("NO", "UNPAID", "OP");
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		//PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.settleBills("NO", "PAID", "OP");
		
	   	 navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
	   	 //PatientBill patientBill = new PatientBill(executeStep, verifications);
	   	 //MRNoSearch Search = new MRNoSearch(executeStep, verifications);
	   	 patientBill.advancedSearch(Search,"REFUND");
	   	 patientBill.viewEditMenuClick();
	   	 patientBill.settleBillDetails("NO", "BillRefundPaymentType");
		
	   	 navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
	   	 //PatientBill patientBill = new PatientBill(executeStep, verifications);
	   	 //MRNoSearch Search = new MRNoSearch(executeStep, verifications);
	   	 patientBill.advancedSearch(Search,"REFUND");
	   	 patientBill.viewEditMenuClick();
	   	 patientBill.settleBillDetails("NO", "BillRefundPaymentType");

	   	 /*navigation.navigateTo(driver, "BillsLink", "BillListPage");
		PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.payAndCloseMultipleBills();*/
		testCaseStatus = "PASS";
		
		System.out.println("TS_002 completed Successfully");

	}	
	
	private void doAdvancePayment(){
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");		
		
		executeStep.performAction(SeleniumActions.Click, "","BillListHospitalBill");
		verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
		
		executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		
		//executeStep.performAction(SeleniumActions.Select,"PaidPaymentStatus","BillPagePaymentStatus");
	    //verifications.verify(SeleniumVerifications.Selected, "PaidPaymentStatus","BillPagePaymentStatus",false);

    	System.out.println(" inside settleBillDetails");   		                                                                             //added by abhishek
		executeStep.performAction(SeleniumActions.Click,"","BillPagePaymentSpan");
			
	    executeStep.performAction(SeleniumActions.Select, "Advance","PatientBillPaymentType");
		verifications.verify(SeleniumVerifications.Selected, "Advance","PatientBillPaymentType",true);
		
		//executeStep.performAction(SeleniumActions.Store, "PaymentDue","PatientPaymentDue");
		executeStep.performAction(SeleniumActions.Enter, "SponsorDue","PatientBillPayField"); // Reusing the Sponsor Due column of the datasheet here instead of introucing a new column since this is a non-insurance scenario
		verifications.verify(SeleniumVerifications.Entered, "SponsorDue","PatientBillPayField",false);

		executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");	
		try{
			verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
			executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
			verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);
			
		} catch (Exception e){
			System.out.println("Receipts Not Opened");
		}
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);

	//	executeStep.performAction(SeleniumActions.Click, "","PatientPaymentsLink");
	//	verifications.verify(SeleniumVerifications.Appears, "","SearchReceiptsPage",false);
					
	//	editReceipt();
	}

	@AfterTest
	public void closeBrowser(){
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OPWorkFlowForCreditPatient", null, "", "", testCaseStatus);
		driver.close();
	}
	
}
