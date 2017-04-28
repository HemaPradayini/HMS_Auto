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

import genericFunctions.CommonUtilities;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.BedAllocation;
import reusableFunctions.BedFinalisation;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DiagnosisDetails;
import reusableFunctions.DischargeSummary;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.DrOrder;
import reusableFunctions.IPListSearch;
import reusableFunctions.Notes;
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.OTRecord;
import reusableFunctions.Registration;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.OperationBill;
import reusableFunctions.OperationDetails;
import reusableFunctions.OperationScheduler;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
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
public class IPWorkFlowWithAdvanceOT {
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
		AutomationID = "Login - IPWorkFlowWithAdvanceOT";
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
		AutomationID = "IPWorkFlowWithAdvanceOT";
		DataSet = "TS_026";	
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();
		System.out.println("After Login");
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		// Delay is introduced to handle the sync problem happening because of two 
		//consecutive close tours and no verification possible
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
	//	verifications.verify(SeleniumVerifications.Appears, "", "NavigationMenu", false); 
		
	}
	
	@Test(groups={"E2E","Regression"})

	public void IPWorkFlowWithAdvanceOT(){
		openBrowser();
		login();
		
		DataSet = "TS_026";
		AutomationID = "IPWorkFlowWithAdvanceOT";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test IPWorkFlow_1 - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet); 

		//Navigate To
		
		// System.out.println("Inside Test IPWorkFlow_1 - Before Navigation");
			
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);		

		navigation.navigateTo(driver, "IPRegistrationLink", "IPRegistrationPage");
		
		//In IP registration, Enter all required information, and also select ward name , bed name.
		//From here its for Entering details on the IP Registration Screen 

		Registration IPReg = new Registration(executeStep, verifications);
		IPReg.RegisterPatientCashIP();

		// Go to In Patient List--> Select the registered IP patient and click on IP case sheet	
		navigation.navigateTo(driver, "InPatientListLink", "InPatientListPage");
		
		IPListSearch IPSearch = new IPListSearch(executeStep, verifications);
		IPSearch.searchIPList();
		
 
		//In IP case sheet , click on Doctor Notes and enter doctor notes, 
		// select 'BILL' check box , and consultation types as well as the doctor name and save
			
		Notes Notes = new Notes(executeStep, verifications);
		Notes.RecordDrNotes();
		
		//Go back to IP case sheet and click on Nurse Notes and enter nurse notes and save the screen
		Notes.RecordNurseNotes();
			
		// Go back to IP Case sheet and click on Doctor order and prescribe all types of items such as 
		// Medicine,Service,Investigation, Consultation and Others
		
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
		//conduct test
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
		//executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		//verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);

		
		//Go to laboratory reports screen > select the test and click on View/Edit, 
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
		
		
		// Go to radiology reports > Select the test and click on View/Edit, choose a 
		// template then edit details and save. Mark complete,validate and signoff.

		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
		
		//conduct service
		//On Service screen saved is clicked.
		
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
			
			
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}		
		
		//Go to Raise Patient Indent screen under Sales and Issues, enter MR No and click on OK 
		//for the alert displayed.Save the indent in finalized status.
		//Go to Patient Indents List under Sales and Issues, select the above indent and click on Issue.
		// Complete Issues by clicking on Save button in Patient Issue screen.
		
		
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
		
		
		
		//Schedule an operation in Surgery scheduler
		navigation.navigateTo(driver, "SurgerySchedulerLink", "SurgerySchedulerPage");
		OperationScheduler OprnScheduler = new OperationScheduler(executeStep, verifications);
		OprnScheduler.scheduleOperationAppointment();
	
	    //Go to IP case sheet and click on New Operation, select the scheduled operation and save
		navigation.navigateTo(driver, "InPatientListLink", "InPatientListPage");
		
		//MR No search in Inpatient List page
	//	IPListSearch IPSearch = new IPListSearch(executeStep, verifications);
		IPSearch.searchIPList();
				
		executeStep.performAction(SeleniumActions.Click, "","IPCaseSheetNewOperationLink");
		verifications.verify(SeleniumVerifications.Appears, "","OperationDetailsPage",false);
		
		EnvironmentSetup.selectByPartMatchInDropDown = true;
		
		executeStep.performAction(SeleniumActions.Select, "SurgeryName","OperationDetailsPageSchApointmentDropdown");
		verifications.verify(SeleniumVerifications.Selected, "SurgeryName","OperationDetailsPageSchApointmentDropdown",true);
	
		EnvironmentSetup.selectByPartMatchInDropDown = false;
		
		executeStep.performAction(SeleniumActions.Click, "","OperationDetailsPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","OperationDetailsPage",false);
	
		// In OT management click on Operation	
	    // Below can be used when the end to end flow is correct
		
		executeStep.performAction(SeleniumActions.Click, "","OperationDetailsPageOTManagementLink");
		verifications.verify(SeleniumVerifications.Appears, "","OTManagementPage",false);
	
		// -----
/*			
		navigation.navigateTo(driver, "PlannedOperationsLink", "PlannedOperationListPage");
		MRNoSearch MRNOSearch = new MRNoSearch(executeStep, verifications);
		MRNOSearch.searchMRNo("PendingServicesListPageMRNoField", "PendingServicesListPageMRNoList", "PendingServicesListPageSearchButton", "PlannedOperationListPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PlannedOperationListPageClick");
		verifications.verify(SeleniumVerifications.Appears, "","PendingServicesListPageOTManagementLink",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingServicesListPageOTManagementLink");
		verifications.verify(SeleniumVerifications.Appears, "","OTManagementPage",false);
*/	
	// ----	
		executeStep.performAction(SeleniumActions.Click, "","OTManagementPageOperationLink");
		verifications.verify(SeleniumVerifications.Appears, "","OperationDetailsPage",false);
			
		//Select the charge type, add an Anaesthsit doctor, Anaesthsia,  and mark status 
		// as complete and save the screen
		
		OperationDetails operationDetails = new OperationDetails(executeStep, verifications);
		operationDetails.addAnaesthetists();
		operationDetails.addAnaesthsiaTypes();
		
		operationDetails.saveOperationDetails();
				
		// Click on Add to bill link , select the items, add comments in text box and click on Add to 
		//bill button
		executeStep.performAction(SeleniumActions.Click, "","OperationDetailsPageAddToBillLink");
		verifications.verify(SeleniumVerifications.Appears, "","OperationAddToBillPage",false);
		
		OperationBill addOperationBill = new OperationBill(executeStep, verifications);
		addOperationBill.markBillable();
		addOperationBill.saveOperationBill();
		
		//click on operation details-->Operation management and click on OT Forms
		executeStep.performAction(SeleniumActions.Click, "","OperationAddToBillPageOprDetailsLink");
		verifications.verify(SeleniumVerifications.Appears, "","OperationDetailsPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","OperationDetailsPageOTManagementLink");
		verifications.verify(SeleniumVerifications.Appears, "","OTManagementPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","OTManagementPageOTFormLink");
		verifications.verify(SeleniumVerifications.Appears, "","OTFormsListPage",false);
		
		//. Select the form , add the details and save the form
		executeStep.performAction(SeleniumActions.Click, "","OTFormsListPageFormName");
		verifications.verify(SeleniumVerifications.Appears, "","OTRecordPage",false);
		
	
		OTRecord otRecord = new OTRecord(executeStep, verifications);
		otRecord.otRecordEntry();
				
		//In Ot management click on Add/Edit OT docs.
		executeStep.performAction(SeleniumActions.Click, "","OTRecordPageOTManagementLink");
		verifications.verify(SeleniumVerifications.Appears, "","OTManagementPage",false);

		executeStep.performAction(SeleniumActions.Click, "","OTManagementAddEditLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientOperationDocumentPage",false);
	
		
		// Select the doc, add the doc and save
		executeStep.performAction(SeleniumActions.Click, "","PatientOperationDocumentPageRichTextRadioButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientOperationDocumentPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PatientOperationDocumentPageAddButton");
		verifications.verify(SeleniumVerifications.Appears, "","AddRichTextDocumentPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AddRichTextDocumentPageSaveButton");
		verifications.verify(SeleniumVerifications.Opens, "","OperationDocumentsPrintPage",true);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","OperationDocumentsPrintPage");
		verifications.verify(SeleniumVerifications.Closes, "","OperationDocumentsPrintPage",false);
		
		//Click on patient document in the same screen and select the attached document and 
		// click on sign off.
		executeStep.performAction(SeleniumActions.Click, "","AddRichTextDocumentPagePatientDocumentLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientOperationDocumentPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PatientOperationDocumentPageSignOffCheckBox");
		verifications.verify(SeleniumVerifications.Appears, "","PatientOperationDocumentPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PatientOperationDocumentPageSignOffButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientOperationDocumentPage",false);
		
		//Observe the documents in Patient/Visit EMR
		
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		
		System.out.println("End Test IPWF With Advance OT");
		
	}
	
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_026", "IPWorkFlowWithAdvanceOT", null, "", "", testCaseStatus);
		
		driver.close();
	}
		
}
