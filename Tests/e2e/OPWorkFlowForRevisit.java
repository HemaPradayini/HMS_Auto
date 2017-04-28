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
import reusableFunctions.OpenGenericForm;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
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

public class OPWorkFlowForRevisit {
	

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
		AutomationID = "Login - OPWorkFlowForRevisit";
		DataSet = "TS_005";
		//EnvironmentSetup.testScenarioId = "TS_001";
		
		System.out.println("BeforeSuite");
		AutomationID = "Login - OPWorkFlowForRevisit";
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
	public void OPWorkFlowForRevisit(){
		openBrowser();
		login();
		DataSet = "TS_005";
		AutomationID = "OPWorkFlowForRevisit";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForRevisit - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		//1.Go to OP Registration screen, enter all mandatory fields at patient level and visit level.
		
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.GovtIDDetailsCollapsedPanel();
		OPReg.RegisterPatientGenericDetails();
		
		//2.Select bill type as Bill Now and doctor as 'Doc1' as do registration with Register&Pay/Register&Edit/Register
		
		OPReg.setBillType();
		OPReg.unCheckPrimarySponsor();
		OPReg.visitInformationDetails();
		executeStep.performAction(SeleniumActions.Select, "RatePlan","OPRScreenRatePlanField");
		verifications.verify(SeleniumVerifications.Selected, "RatePlan","OPRScreenRatePlanField",false);
		OPReg.storeDetails();
		
		// click on Proceed To Bill link in registration success page
		//3.(ProceedToBilling->Click)Go to bill and do full amount settlement and click on 'Pay&close' button
		// in bill also observe the total sponsor and patient due in bill
		
				 		
		executeStep.performAction(SeleniumActions.Click, "","ProceedToBillingLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		
		PatientBill patientBill = new PatientBill(executeStep, verifications);
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");            //Added by Abhishek
		patientBill.settleBillDetails("NO", "SettlementPaymentType");
        
		//4. Go to Out Patient List and try to access doctor consultaion screen before paying the bill.
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
				
		ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
				
		//6. Prescribe all types of items like medicines(serial and batch items having packet size 1 and 10) with quantity as 5,Lab,rad,services,diag packages and save the consultation screen
		consultMgmt.saveConsultationAndMgmt("Diagnosis");
		
		// 5 .Enter all required sections / fields in consultation/ IA / Triage/ generic forms
		executeStep.performAction(SeleniumActions.Click, "","ConsulationMgtScreenTriageLink");
		verifications.verify(SeleniumVerifications.Appears, "","TriagePage",true);

		Triage triage = new Triage(executeStep, verifications);
		triage.saveTriage();

		OpenGenericForm opGenericForm = new OpenGenericForm(executeStep, verifications);
		opGenericForm.openGenericForm();
				
		
		//Now go to op registration screen,check the mr no radio button and put the mr no of patient and 
		//create a follow up visit without ordering the pending prescription , select consultation type,
		//bill type as bill now and click on register
		
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		OPReg.patientFollowUpVisit(false);
		// to close the bill
		
		
		verifications.verify(SeleniumVerifications.Opens, "","PatientBillScreen",false);
        executeStep.performAction(SeleniumActions.Click, "", "PatientBillTour");
        verifications.verify(SeleniumVerifications.Appears, "","PatientBillBilledAmount",false);        
		executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillScreen");
		verifications.verify(SeleniumVerifications.Closes, "","PatientBillScreen",false);
		
		
		
		//8.Now again go to op registration screen,check the mr no radio button and put the mr no of patient 
		//and create a revisit for the patient ,
		//select consultation type,bill type as bill now and click on register
		
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		OPReg.patientFollowUpVisit(true);                         // For patient revisit
		// to close the bill
		verifications.verify(SeleniumVerifications.Opens, "","PatientBillScreen",false);
        executeStep.performAction(SeleniumActions.Click, "", "PatientBillTour");
        verifications.verify(SeleniumVerifications.Appears, "","PatientBillBilledAmount",false); 
        executeStep.performAction(SeleniumActions.Click, "", "BillPageOrderLink");
        verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",false);
        
      
        
        
        Order order = new Order(executeStep, verifications); 
        order.addOSPOrder();
        
		executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillScreen");
		verifications.verify(SeleniumVerifications.Closes, "","PatientBillScreen",false);
		/*		
		  navigation.navigateTo(driver, "OrdersLink", "OrdersPage");            //Added on 13/4
		  Order order = new Order(executeStep, verifications); 
	        
			order = new Order(executeStep, verifications);
			order.searchOrder(false,true); 
			order.addOSPOrder();
			*/
		//After step 9 added 3.(ProceedToBilling->Click)Go to bill and do full amount settlement and click on 'Pay&close' button.
		// because to clear bill this step was not mentioned in test steps
		
//***************************Added Tonight ***************8 		
        executeStep.performAction(SeleniumActions.Click, "","ProceedToBillingLink");
        executeStep.performAction(SeleniumActions.Click, "", "CloseTour");            //Added by Abhishek
        verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
         	
        
        
        //PatientBill patientBill = new PatientBill(executeStep, verifications);
        patientBill.settleBillDetails("NO","SettlementPaymentType");
 //*************************Added Tonight ***************8
		
		
		//. Go to sales screen and observe that medicines prescribed in previous visit don't auto-fill.
		//****Bill level verification to be added
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
		//verifications.verify(SeleniumVerifications.Appears, "","ConsultAndManagementFieldSetPreviousVisitTable",false);
				
		executeStep.performAction(SeleniumActions.Click, "", "ConsultAndManagementFieldSetCloseBtn");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultationAndManagementPage",false);
				
		// for follow up visit filling the details
		consultMgmt.saveConsultationAndMgmt("FollowUpDiag");
		
				
/*		Removed becoz vinay tld its unnecessary
	// #12
		// Do sales returns for partial/full qty by clicking on Raise bill/Pay&Print/Add to bill.
		
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		salesReturns.doSalesReturn("SalesReturns","OP");
		*/
	// #13
//Raise a patient indent by adding serial and batch items (having packet size 1 and 10).
	    navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentSales", true);
				

//14. Do sales/issues for this patient indent.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");    //changed 13/4
		raisePatientIndent.doSalesOrSalesReturnsForIndent(true, "IndentSales");
		
	/*			
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		
		sales = new Sales(executeStep, verifications);
				sales.payAndPrint(false);
				*/	                //commented on 13/4			
/*  ***************************************************************
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");   // added 19/2
		raisePatientIndent.doSalesOrSalesReturnsForIndent(true, "IndentSales");
	*/	
				
// 15. Raise a return indent by giving partial/full quantity for the above sold/issued items.
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");
		
		raisePatientIndent.savePatientIndent("ReturnedSalesInden", false);			
	
//16. Do sales/issue returns for the above return indent.
		
		/*	                             //commented on 13/4
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		salesReturns.doSalesReturn("SalesReturns","OP");
		 ***************************************************************
		 *			
 */		
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");// added 19/2
		raisePatientIndent.doSalesOrSalesReturnsForIndent(false, "");
		
		// 17.Go to Laboratory Pending samples screen, select the test and click on collect menu option. 
		//Select the samples by ticking the check boxes and click on Save.

		           // added by me now
	/*	navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
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
		//18. Go to laboratory reports screen > 
		//select the test and click on View/Edit, enter values (1,2,3,4,etc) complete,validate and Signoff.
		
		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();
		

		//19Complete,validate and sign off the pending radiology test
	    navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
		
		//20Complete the pending service test
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
		
		// step 21 Go to Patient &Visit EMR and observe documents.
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);

		testCaseStatus = "Pass";
		System.out.println("TS_005 - completed");		
		
		
		System.out.println("Till here done");
		
			
		
		

	} 
	
	@AfterClass
	public void closeBrowser(){
		String delimiter = " :: ";
		//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
		//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OPWorkFlowForRevisit", null, "", "", testCaseStatus);
		driver.close();
	}


}
