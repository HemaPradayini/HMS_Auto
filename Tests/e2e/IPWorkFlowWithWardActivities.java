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
import reusableFunctions.OperationBill;
import reusableFunctions.OperationDetails;
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
public class IPWorkFlowWithWardActivities {
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
		AutomationID = "Login - IPWorkFlowWithWardActivities";
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
		AutomationID = "IPWorkFlowWithWardActivities";
		DataSet = "TS_019";	
		
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

	public void IPWorkFlowWithWardActivities(){
		openBrowser();
		login();
		
		DataSet = "TS_019";
		AutomationID = "IPWorkFlowWithWardActivities";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test IPWorkFlow_1 - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet); 

		//Navigate To
		
		// System.out.println("Inside Test IPWorkFlow_1 - Before Navigation");
			
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	
		navigation.navigateTo(driver, "IPRegistrationLink", "IPRegistrationPage");
		
		
		//1. In IP registration, Enter all required information, and also select ward name ,bed name.

		Registration IPReg = new Registration(executeStep, verifications);
		IPReg.RegisterPatientCashIP();
		
		//2. Go to In Patient List--> Select the registered IP pateint and click on IP case sheet
		
		navigation.navigateTo(driver, "InPatientListLink", "InPatientListPage");
		//MR No search in Inpatient List page
		IPListSearch IPSearch = new IPListSearch(executeStep, verifications);
		IPSearch.searchIPList();
 
		//3. In IP case sheet, click on Doctor Notes and enter doctor notes, select 'BILL' 
		// check box , and consultation types as well as the doctor name and save
			
		Notes Notes = new Notes(executeStep, verifications);
		Notes.RecordDrNotes();
		
		//4. Go back to IP case sheet and click on Nurse Notes and enter nurse notes and save the screen
		Notes.RecordNurseNotes();
			
		//5. Go back to IP Casesheet and click on Doctor order and prescribe all types of items such as 
		// Medicines(having package size 1 and 10 and serial items), Service, Investigation, Care plan, 
		// Consultation and Others.
		
		executeStep.performAction(SeleniumActions.Click, "","IPCaseSheetDrOrderLink");
		verifications.verify(SeleniumVerifications.Appears, "","DrOrderScreen",false);
		
		DrOrder DrOrderDetails = new DrOrder(executeStep, verifications);
		DrOrderDetails.AddItems();
		DrOrderDetails.SaveDrOrder();
	
		// 6. Click on Patient ward activities and select the items and mark as Order.
		
		executeStep.performAction(SeleniumActions.Click, "","DrOrderPatientWardLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientWardActivitiesScreen",false);
		
	
		PatientWard PatientWardDetails = new PatientWard(executeStep, verifications);
		PatientWardDetails.OrderItems();
		PatientWardDetails.SavePatientWard();
		
		// 7.a.Go to Laboratory Pending samples screen, select the test and click on collect 
		//menu option. Select the samples by ticking the check boxes and click on Save.
	
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();

		
		//7.b.Go to laboratory reports screen > select the test and click on View/Edit, 
		// enter values (1,2,3,4,etc) complete,validate and Signoff.
		
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
		
		// 7.c.Go to radiology reports > Select the test and click on View/Edit, choose 
		//a template then edit details and save. Mark complete,validate and signoff.

		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);

		
		//7.d.Go to Pending Services List, select the service and click on edit menu option mark 
		// completed and save.
	
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
			
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}	
		
		//8. Go to Add Patient Indent screen under Sales and Issues, enter the MR Number. Click on OK for the alert 
		//displayed and wait for the prescribed medicines to auto-fill. Save this indent in finalized status.
		
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentSales", true);
		

		//9. Go to Patient Indents List under Sales and Issues. Select the above generated indent and 
		// click on Issue. Complete issues by clicking on Save.
	
		//Issue the indent
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
	//	RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.searchPatientIndent();
		raisePatientIndent.savePatientIndentIssue(true);

		//10.Come back to Patient ward activity and mark the items as done..
		//Navigate to Inpatient-IP Case Sheet
		navigation.navigateTo(driver, "InPatientListLink", "InPatientListPage");
		//MR No search in Inpatient List page
	//	IPListSearch IPSearch = new IPListSearch(executeStep, verifications);
		IPSearch.searchIPList();
		
		executeStep.performAction(SeleniumActions.Click, "","IPCaseSheetDrOrderLink");
		verifications.verify(SeleniumVerifications.Appears, "","DrOrderScreen",false);
		executeStep.performAction(SeleniumActions.Click, "","DrOrderPatientWardLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientWardActivitiesScreen",false);
	//	PatientWard PatientWardDetails = new PatientWard(executeStep, verifications);
		PatientWardDetails.CloseItems();
		PatientWardDetails.SavePatientWard();
				
		//11. In ADT dashboard, select the same patient and click on shift bed, Select the bed name 
		//and ward name and also select the date such that previous bed is allocate atleast a day and 
		//save the screen.
		
		navigation.navigateTo(driver, "ADTLink", "ADTPage");
		//MR No search in ADT page
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
	//	search.searchMRNo("InPatientListMrNoField","InPatientListSelectionMRNoLi","InPatientListSearchButton","ADTPage");
	//	verifications.verify(SeleniumVerifications.Appears, "","ADTPage",false);
		
		//Navigate to Shift Bed Screen
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("ADTShiftBedLink", "ShiftBedScreen");

		BedAllocation ShiftBed = new BedAllocation(executeStep, verifications);
		ShiftBed.AssignBed();
		ShiftBed.SaveShiftBed();

		
		//12. In ADT dash board, click on Bystander Bed menu link and enter ward name, 
		//bed type and bed name save.
		
		navigation.navigateTo(driver, "ADTLink", "ADTPage");
		//MR No search in ADT page
		//MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("InPatientListMrNoField","InPatientListSelectionMRNoLi","InPatientListSearchButton","ADTPage");
		verifications.verify(SeleniumVerifications.Appears, "","ADTPage",false);
		
		//Navigate to Bystander Bed Screen
		//OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("ADTBystanderBedLink", "AllocateBystanderBedScreen");
		
		//BedAllocation ShiftBed = new BedAllocation(executeStep, verifications);
		ShiftBed.AssignBystanderBed();
		ShiftBed.SaveByStanderBed();
	
	
		//13. Schedule an operation in Surgery scheduler and mark it as arrived.
		navigation.navigateTo(driver, "SurgerySchedulerLink", "SurgerySchedulerPage");
		OperationScheduler OprnScheduler = new OperationScheduler(executeStep, verifications);
		OprnScheduler.scheduleOperationAppointment();
		OprnScheduler.markPatientAsArrived();
		
		//14. Go to Operation module and mark it as completed and add to bill
	
		navigation.navigateTo(driver, "PlannedOperationsLink", "PlannedOperationListPage");
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PendingServicesListPageMRNoField","PendingServicesListPageMRNoList","PendingServicesListPageSearchButton","PlannedOperationListPage");
	
		executeStep.performAction(SeleniumActions.Click, "","PlannedOperationListPageClick");
		verifications.verify(SeleniumVerifications.Appears, "","PendingServicesListPageOTManagementLink",false);	

		executeStep.performAction(SeleniumActions.Click, "","PendingServicesListPageOTManagementLink");
		verifications.verify(SeleniumVerifications.Appears, "","OTManagementPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","OTManagementPageOperationLink");
		verifications.verify(SeleniumVerifications.Appears, "","OperationDetailsPage",false);
		
		OperationDetails operationDetails = new OperationDetails(executeStep, verifications);
		operationDetails.saveOperationDetails();
		
		// Click on Add to bill link , select the items, add comments in text box and click on Add to 
		//bill button
				executeStep.performAction(SeleniumActions.Click, "","OperationDetailsPageAddToBillLink");
				verifications.verify(SeleniumVerifications.Appears, "","OperationAddToBillPage",false);
				
				OperationBill addOperationBill = new OperationBill(executeStep, verifications);
				addOperationBill.markBillable();
				addOperationBill.saveOperationBill();
	
		//15.In ADT dashboard, click on Bed details and Finalize the bed such that 
		// the even shifted bed is allocated for a day and finalize the bill .
		
		navigation.navigateTo(driver, "ADTLink", "ADTPage");

		BedFinalisation FinaliseBed = new BedFinalisation(executeStep, verifications);
		FinaliseBed.finaliseBed();
		
			
		// Finalize the bill
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");			
		PatientBill patientBill = new PatientBill(executeStep, verifications);
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");		
		patientBill.advanceBillNavigation();
		patientBill.setBillStatus("YES");
		executeStep.performAction(SeleniumActions.Click, "", "PatientBillSaveButton");
		//patientBill.settleBills("NO", "UNPAID", "IP");
				
		//16. Go to discharge summary screen and add Rich text/PDF/HVF document and finalize
		 
		 navigation.navigateTo(driver, "DischargeSummaryLink", "DischargeSummaryPage");
		//MR No search in ADT page
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("DischargeSummaryMRNoField","DischargeSummaryMRContainerField","DischargeSummaryFindButton","DischargeSummaryPage");
		verifications.verify(SeleniumVerifications.Appears, "","DischargeSummaryPage",false);
		executeStep.performAction(SeleniumActions.Click, "","DischargeSummaryAddDSLink");
		verifications.verify(SeleniumVerifications.Appears, "","DischargeSummaryPage",false);
		
		DischargeSummary DisSummary = new DischargeSummary(executeStep, verifications);
		DisSummary.PrepareDischargeSummary();
	
		
		// 	17. Close the bill and discharge the patient
	
		navigation.navigateTo(driver, "BillsLink", "BillListPage");			
	//	PatientBill patientBill = new PatientBill(executeStep, verifications);	
		patientBill.closeBill("FINALIZED");
	//	patientBill.settleBills("NO", "UNPAID", "IP");
		
		//and discharge the patient
		
		navigation.navigateTo(driver, "DischargeLink", "PatientDischargePage");
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("PatientDischargeMRNoField", "PatientDischargeMRNoLi", "PatientDischargeFindButton", "PatientDischargePage");

		PatientDischarge patientDischarge = new PatientDischarge(executeStep, verifications);		
		patientDischarge.patientDischarge();
		
		//18. Observe the documents in Patient/Visit EMR.
	
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		
		testCaseStatus="PASS";
		
		System.out.println("End Test IPWF With Ward activities");

	}

	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_019", "IPWorkFlowWithWardActivities", null, "", "", testCaseStatus);
		driver.close();
	}
		
}
