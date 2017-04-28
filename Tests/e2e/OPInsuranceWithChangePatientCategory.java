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
import reusableFunctions.ClaimsSubmission;
import reusableFunctions.Codification;
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
import reusableFunctions.PatientVisit;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.RaisePatientIndent;
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

/**
 * @author Reena
 *
 */
@Test
public class OPInsuranceWithChangePatientCategory {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus="FAIL";
//	@BeforeSuite
	//public void BeforeSuite(){
	private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - OPInsuranceWithChangePatientCategory";
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
		AutomationID = "Login - OPInsuranceWithChangePatientCategory";
		DataSet = "TS_013";
		//EnvironmentSetup.testScenarioId = "TS_013";
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		

	}
	
	public void OPInsuranceWithChangePatientCategory(){
		
		openBrowser();
		login();
		DataSet = "TS_013";
		AutomationID = "OPInsuranceWithChangePatientCategory";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForInsurance - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	  navigation.navigateTo(driver, "DoctorSchedulerLink", "DoctorSchedulerPage");
		
		DoctorScheduler OPWorkFlowDocScheduler = new DoctorScheduler(executeStep,verifications);
		OPWorkFlowDocScheduler.scheduleDoctorAppointment();
		
		//From here its for Marking as Patient arrived. move it to another reusable
		PatientArrival OPPatientArrival = new PatientArrival(executeStep,verifications);
		OPPatientArrival.markPatientAsArrived();		

		//From here its for Entering details on the OP Registration Screen 
		Registration OPReg = new Registration(executeStep, verifications);
		   
	       OPReg.RegisterPatientGenericDetails();
	       OPReg.GovtIDDetailsCollapsedPanel();
	       OPReg.setBillType();
	       OPReg.RegisterPatientInsurance("OP");
	       OPReg.visitInformationDetails();
	       OPReg.storeDetails();
	       
	    executeStep.performAction(SeleniumActions.Click, "","ProceedToBillingLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
		
	
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
	
		//Make payment and change payment status as 'Paid'   
		
		 navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
		 MRNoSearch Search = new MRNoSearch(executeStep, verifications);		
		 Search.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");
		
		
		PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.viewEditBills("BN");
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		patientBill.saveRegistrationBillDetails("BillPaymentType","YES");
       
	   		
	//This is for searching with the MR No on the OPList and navigating to the consultation and management screen
		
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
			
		ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
		consultMgmt.saveConsultationAndMgmt("Diagnosis");
	       
	       
	//Observe the patient amount in order screen and save the order
		
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
		order.saveOrder(true);


	    	
		  //Make payment and change payment status as 'Paid'    
		navigation.navigateTo(driver, "BillsLink", "BillListPage"); 
	//	PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.settleBills("YES","UNPAID","OP");


     //Collect samples
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
	//	MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
		
		
	 //Complete,validate and sign off the pending lab test
		
		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
//		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();

		//Complete,validate and sign off the pending radiology test
	    navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
		
		//Complete the pending service test
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();

		
		//Go to Patient & Visit EMR and observe documents
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
	//	MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
	
		//Observe the sales screen- prescribed medicines should get auto filled
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.searchMRNo();
		sales.payAndPrint(true);
		
		
	
		
	//Sales Return		
	     navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		salesReturns.doSalesReturn("SalesReturns", "OP");

		
		//create a new bill
		navigation.navigateTo(driver, "NewBillLink", "NewBillPage");
	//	PatientBill patientBill = new PatientBill(executeStep, verifications);	
		patientBill.createBill();

   //Raise Indent	
		
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
    // 	MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("RaisePatientIndentMRNoField", "RaisePatientIndentMRNoList", "RaisePatientIndentMRNoFindButton", "RaisePatientIndentPage");
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentSales",true);
		
		
		
   //Patient Indents List
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
	//	RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.searchPatientIndent();
				
	// Click On save to complete issues.
		  raisePatientIndent.savePatientIndentIssue(true);
	
			
	//Raise a return indent for the above issued items.
				navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");
		//		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
				raisePatientIndent.savePatientIndent("ReturnedSalesIndent", false);	
				executeStep.performAction(SeleniumActions.Accept, "","Dialog");
				
	// click on Issue Returns.
				navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
				raisePatientIndent.searchPatientIndent();
				
	// Click On save to complete issue returns
				raisePatientIndent.savePatientIndentIssue(false);
		
			
    //Change patient category
		        navigation.navigateTo(driver, "EditPatientVisit", "EditPatientVisitPage");
				PatientVisit changecateg=new PatientVisit(executeStep, verifications);
		        changecateg.changePatientCategory();
		        testCaseStatus="PASS";
		        System.out.println("OPInsuranceWithChangePatientCategory-Completed");	
	}
	
	
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_013", "OPInsuranceWithChangePatientCategory", null, "", "", testCaseStatus);
		driver.close();
	}
		
}
