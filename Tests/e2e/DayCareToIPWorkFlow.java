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

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.BedAllocation;
import reusableFunctions.BedFinalisation;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DischargeSummary;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.DrOrder;
import reusableFunctions.IPListSearch;
import reusableFunctions.Notes;
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.Registration;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.OperationScheduler;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientDischarge;
import reusableFunctions.PatientEMR;
import reusableFunctions.PatientWard;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Sales;
import reusableFunctions.SearchBillList;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Triage;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author C N Alamelu
 *
 */
public class DayCareToIPWorkFlow {
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
		AutomationID = "Login - DayCareToIPWorkFlow";
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
		AutomationID = "DayCareToIPWorkFlow";
		DataSet = "TS_024";
				
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();
		System.out.println("After Login");
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
	//	verifications.verify(SeleniumVerifications.Appears, "", "NavigationMenu", false); 
	}
	
	@Test(groups={"E2E","Regression"})

	public void DayCareToIPWorkFlow(){
		openBrowser();
		login();
		
		DataSet = "TS_024";
		AutomationID = "DayCareToIPWorkFlow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test DayCareToIPWorkFlow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet); 

		//Navigate To
			
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");	
		
		navigation.navigateTo(driver, "IPRegistrationLink", "IPRegistrationPage");
			
		// Register a day care patient by selecting 'Day Care' check box in 
		// bed allocation section of IP registration screen.

		Registration IPReg = new Registration(executeStep, verifications);
		IPReg.RegisterDayCarePatient();
		
		//2. Order lab, radiology test and services.
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
	
		order.addOrderItem("OrderScreenItem","OrdersPage");
		order.saveOrder(false);
		
		//2a. Go to Laboratory Pending samples screen, select the test and click on collect 
		//menu option. Select the samples by ticking the check boxes and click on Save.
		//Collect samples
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();

		
		//2b. Go to laboratory reports screen > select the test and click on View/Edit menu 
		//option, enter values (1,2,3,4,etc) complete,validate and Signoff.	
		navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LaboratoryReportsPageMRNo","LaboratoryReportsPageMRList","LaboratoryReportsPageSearchButton","LaboratoryReportsPage");
		executeStep.performAction(SeleniumActions.Click, "","LabReportsReportCheckBox");
		executeStep.performAction(SeleniumActions.Click, "","LabReportsReportRow");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();		
		
		
		//2c. Go to radiology reports > Select the test and click on View/Edit menu option,
		//choose a template then edit details and save. Mark complete,validate and signoff.
		
		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
		
		//2d. Go to pending services list > Select the service and click on edit menu option, 
		//mark completed and save
	
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}
		
		// Proceed To Bill 
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		
		//From bill screen Go to bed details screen and select the radio button 'Convert to IP Billing and save
		
		executeStep.performAction(SeleniumActions.Click, "","BillPageBedDetailsLink");
		verifications.verify(SeleniumVerifications.Appears, "","BedDetailsScreen",false);
		
		executeStep.performAction(SeleniumActions.Click, "","BedDetailsConvertToIPBillingRadioButton");
		verifications.verify(SeleniumVerifications.Appears, "","BedDetailsScreen",false);
		
		executeStep.performAction(SeleniumActions.Click, "","BedDetailsSaveButton");
		//verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		//executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","BedDetailsScreen",false);
		
		// Go to Patient &Visit EMR and observe documents
		
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		
		// Go to In Patient List--> Select the registered IP patient and click on IP case sheet	
		navigation.navigateTo(driver, "InPatientListLink", "InPatientListPage");
	
		
		//MR No search in Inpatient List page
		IPListSearch IPSearch = new IPListSearch(executeStep, verifications);
		IPSearch.searchIPList();

		//In IP case sheet , click on Doctor Notes and enter doctor notes, 
		// select 'BILL' check box , and consultation types as well as the doctor name and save
			
		Notes Notes = new Notes(executeStep, verifications);
		Notes.RecordDrNotes();
		
		//Go back to IP case sheet and click on Nurse Notes and enter nurse notes and save the screen
		Notes.RecordNurseNotes();
			
		//  Go back to IP Case sheet and click on Doctor order and prescribe all types of 
		// items such as Medicine,Service,Investigation, Care plan ,Consultation and Others
		//include care plan
		
		executeStep.performAction(SeleniumActions.Click, "","IPCaseSheetDrOrderLink");
		verifications.verify(SeleniumVerifications.Appears, "","DrOrderScreen",false);
	
		DrOrder DrOrderDetails = new DrOrder(executeStep, verifications);
		DrOrderDetails.AddItems();
		DrOrderDetails.SaveDrOrder();
	
		// Click on Patient ward activities and select the items and mark as Order
		
		executeStep.performAction(SeleniumActions.Click, "","DrOrderPatientWardLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientWardActivitiesScreen",false);
		
	
		PatientWard PatientWardDetails = new PatientWard(executeStep, verifications);
		PatientWardDetails.OrderItems();	
		PatientWardDetails.SavePatientWard();

		
		//Conduct the test & service in the respective departments
		
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
//		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		
//		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();

		
		//Go to laboratory reports screen > select the test and click on View/Edit, 
		// enter values (1,2,3,4,etc) complete,validate and Signoff.
			
		navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LaboratoryReportsPageMRNo","LaboratoryReportsPageMRList","LaboratoryReportsPageSearchButton","LaboratoryReportsPage");
		executeStep.performAction(SeleniumActions.Click, "","LabReportsReportCheckBox");
		executeStep.performAction(SeleniumActions.Click, "","LabReportsReportRow");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
	//	TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();
		
		// Go to radiology reports > Select the test and click on View/Edit, choose a 
		// template then edit details and save. Mark complete,validate and signoff.

		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
//		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		//radiologyPendingTests.radiologyReportSignOff();
		radiologyPendingTests.radiologyPending(true);
		
		//conduct service
	
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
//		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}
		
		//Go to Indents screen and create an Indent for the prescribed medicine
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
	
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentSales", true);		
		
		
		//Issue the indent
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
	//	RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.searchPatientIndent();
		raisePatientIndent.savePatientIndentIssue(true);
			
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}
		
		//Come back to Patient ward activity and mark the items as done.
		//Navigate to Inpatient-IP Case Sheet
		navigation.navigateTo(driver, "InPatientListLink", "InPatientListPage");
	
		
		//MR No search in Inpatient List page
	//	IPListSearch IPSearch = new IPListSearch(executeStep, verifications);
		IPSearch.searchIPList();
				
		
		executeStep.performAction(SeleniumActions.Click, "","IPCaseSheetDrOrderLink");
		verifications.verify(SeleniumVerifications.Appears, "","DrOrderScreen",false);
		executeStep.performAction(SeleniumActions.Click, "","DrOrderPatientWardLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientWardActivitiesScreen",false);
//		PatientWard PatientWardDetails = new PatientWard(executeStep, verifications);
		PatientWardDetails.CloseItems();
		PatientWardDetails.SavePatientWard();
				
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}
		//In ADT dashboard, click on Bed details and Finalize the bed such that 
		// the even shifted bed is allocated for a day and finalize the bill .
		
		DbFunctions dbFunctions = new DbFunctions();
		dbFunctions.storeDate(this.executeStep.getDataSet(), "ShiftEndDate","C",0);
		
		
		navigation.navigateTo(driver, "ADTLink", "ADTPage");

		BedFinalisation FinaliseBed = new BedFinalisation(executeStep, verifications);
		FinaliseBed.finaliseBed();
		
		//Click Ok to Discharge in bill and Finalize the bill
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");			
		PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.settleBills("NO", "UNPAID", "IP");
				
	    //Go to Discharge screen and discharge thePatient
		navigation.navigateTo(driver, "DischargeLink", "PatientDischargePage");
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("PatientDischargeMRNoField", "PatientDischargeMRNoLi", "PatientDischargeFindButton", "PatientDischargePage");

		PatientDischarge patientDischarge = new PatientDischarge(executeStep, verifications);		
		patientDischarge.patientDischarge();
				
		//Go to EMR and observe
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		
		testCaseStatus="PASS";
		System.out.println("End Test DayCareToIPWorkFlow");

	}
	
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_024", "OPWorkFlowWithDeposit", null, "", "", testCaseStatus);
		driver.close();
	}
	
}
