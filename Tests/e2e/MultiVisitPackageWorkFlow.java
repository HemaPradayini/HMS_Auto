package e2e;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.CommonUtilities;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.CollectSamples;
import reusableFunctions.DoctorReferral;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.Order;
import reusableFunctions.PatientBill;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.Registration;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class MultiVisitPackageWorkFlow {
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
		AutomationID = "Login - MultiVisitPackage";
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
		AutomationID = "Login - "
				+ " ";
		DataSet = "TS_072";
				
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void MultiVisitPackageWorkFlow(){
		openBrowser();
		login();
		DataSet = "TS_072";
		AutomationID = "MultiVisitPackageWorkFlow ";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test MultiVisitPackage - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		//1.Go to OP registration screen,enter all mandatory details click on register and edit bill option.
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.unCheckPrimarySponsor();
		OPReg.setBillType();
		OPReg.RegisterPatientGenericDetails();
		OPReg.visitInformationDetails();
		OPReg.storeAndEditBill();
		
		
        executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillScreen");
		verifications.verify(SeleniumVerifications.Closes, "","PatientBillScreen",false);
	    
		
		//2.Now from bill go to bill order screen and Try to Order MVP1 in the same bill.
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");		
		PatientBill patientBill = new PatientBill(executeStep, verifications);	
		executeStep.performAction(SeleniumActions.Click, "","BillListItemToClick");
	    verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
	    CommonUtilities.delay(20);
        executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
        verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
        executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		
		executeStep.performAction(SeleniumActions.Click, "","BillPageOrderLink");
	    verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",false);
		
		Order order = new Order(executeStep, verifications);
		order.addMultiVisitPakageWithOldBill("OrderItem1");
		
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		executeStep.performAction(SeleniumActions.Select, "OrderPageBillNoChoice","OrdersScreenBillNumberDropDown");
		
		order.addMultiVisitPakageWithnewBill("OrderItem1");
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "","OrderPageBillNumber");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		
		executeStep.performAction(SeleniumActions.Click, "","BillPageOrderLink");
	    verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",false);
		
		order.addItemsWithMVP1("OrderItem2");
		
		//Pay unpaid bills
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		patientBill = new PatientBill(executeStep, verifications);		
		patientBill.settleBills("NO", "UNPAID", "OP");
		
		//perform test and signoff on reports
		performOrderedTestsAndSignOffReports(navigation);
		
		//Now again register the patient by using MRNum
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		OPReg.patientFollowUpVisit(true);
		
		verifications.verify(SeleniumVerifications.Opens, "","PatientBillScreen",false);
        executeStep.performAction(SeleniumActions.Click, "", "PatientBillTour");
        verifications.verify(SeleniumVerifications.Appears, "","PatientBillBilledAmount",false);
        executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillScreen");
		verifications.verify(SeleniumVerifications.Closes, "","PatientBillScreen",false);
		
		//Now order 'MVP1' from registration order with qty as 1 for all the items
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");			
		executeStep.performAction(SeleniumActions.Click, "","BillListItemToClick");
	    verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
	    CommonUtilities.delay(20);
        executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
        verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
        executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		
		executeStep.performAction(SeleniumActions.Click, "","BillPageOrderLink");
	    verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",false);
		
		executeStep.performAction(SeleniumActions.Select, "OrderPageBillNoChoice","OrdersScreenBillNumberDropDown");
		
		order.addMultiVisitPakageWithnewBill("OrderItem3");
		
		
		//Pay unpaid bills
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		patientBill = new PatientBill(executeStep, verifications);		
		patientBill.settleBills("NO", "UNPAID", "OP");
				
		performOrderedTestsAndSignOffReports(navigation);
		
			
	}
	
	public void performOrderedTestsAndSignOffReports(SiteNavigation navigation){
		//Click on collect sample link
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
		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
						
		//Go to laboratory reports screen select the test and click on View/Edit, enter values (1,2,3,4,etc) complete,validate and Signoff.
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
						
		//validate and sign off the pending radiology test
		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
						
		//Complete the pending service test
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
		
		//Go to Patient/Visit EMR and observe.
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		Search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		
		testCaseStatus="PASS";
		System.out.println("Multivisit pakage work flow");
						
				
	}
	
	@AfterTest
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, DataSet, "Multivisit pakage work flow", null, "", "", testCaseStatus);
		 driver.close();
	}
	
}



