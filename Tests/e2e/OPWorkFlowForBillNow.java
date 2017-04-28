/**
 * 
 */
package e2e;

import java.util.List;
import java.util.NoSuchElementException;

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
public class OPWorkFlowForBillNow {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus = "Fail";
	
	//@BeforeSuite
	//public void BeforeSuite(){
	private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - OPWorkFlowForCashPatient";
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
		AutomationID = "Login - OPWorkFlowBillLater";
		DataSet = "TS_001";
		//EnvironmentSetup.testScenarioId = "TS_001";
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();
		CommonUtilities.delay(500);
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		//executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void OPWorkFlowForBillNow(){
		
		openBrowser();
		login();
		
		DataSet = "TS_001";
		AutomationID = "OPWorkFlowForBillNow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForBillNow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		//Step 1
		navigation.navigateTo(driver, "DoctorSchedulerLink", "DoctorSchedulerPage");
		
		DoctorScheduler OPWorkFlowDocScheduler = new DoctorScheduler(executeStep,verifications);
		//Step 2 and 3
		OPWorkFlowDocScheduler.scheduleDoctorAppointment();
		
		//From here its for Marking as Patient arrived. move it to another reusable
		PatientArrival OPPatientArrival = new PatientArrival(executeStep,verifications);
		//Step 4
		OPPatientArrival.markPatientAsArrived();		

		//From here its for Entering details on the OP Registration Screen - Move to another reusable
		//Step 5 and 6
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.unCheckPrimarySponsor();
		OPReg.RegisterPatientGenericDetails();
		OPReg.setBillType();
		OPReg.storeDetails();
		
		//This is for navigation to the OP List and searching with the MR No on the OPList and navigating to the consultation and management screen
		//Step 7 - Consult Link Diabled for OP Bill Now
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		try{
		executeStep.performAction(SeleniumActions.Enter, "MRID","OPListScreenSearchField");
		verifications.verify(SeleniumVerifications.Entered, "MRID","OPListScreenSearchField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","OPListScreenSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "MRID","OPListScreenSearchResults",false);	

		executeStep.performAction(SeleniumActions.Click, "MRID","OPListScreenSearchResults");
		verifications.verify(SeleniumVerifications.Disabled, "","OPListScreenConsultLink",false); // Disabled Consult Link
		
		} catch(NoSuchElementException nse){
			nse.printStackTrace();
			System.out.println("Expected Result - Consult Link is disabled for Bill Now Patient.");
		}
		//Step 8
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		String billType = "Settlement";		
		PatientBill patientBillNow = new PatientBill(executeStep, verifications);		
		patientBillNow.settleBillDetails("NO", "SettlementPaymentType");
		
		//Step 9
		
		//OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		//OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
		
		//Step 10 and 11
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

		//Step 12 and 13
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
	
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
		
		//Step 14
		order.saveOrder(true);
		executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenBillLink");
		verifications.verify(SeleniumVerifications.Appears, "", "PatientBillScreen", false);
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.settleBillDetails("NO", "SettlementPaymentType");
		
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		//Order order = new Order(executeStep, verifications);
//		order.searchOrder(false,false);
//		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
//		verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
		
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);

	
		//Step 15 - Verfiy in respective departments like Labaratory,Service and Radiology such that ordered items are lshowing in respective Departments
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
//		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
//		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
		
		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
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
		//MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);

		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);		
		sales.payAndPrint(true);
		sales.closePrescriptionTab();

		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturn=new SalesReturn(executeStep, verifications);
		salesReturn.doSalesReturn("SalesMedicineReturns", "OP");

		//Step 16
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		MRNoSearch mrNoSearch = new MRNoSearch(executeStep, verifications);
		mrNoSearch.searchMRNo("RaisePatientIndentMRNoField", "RaisePatientIndentMRNoList", "RaisePatientIndentMRNoFindButton", "RaisePatientIndentPage");
		
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentSales", true);
		
		//Step 17 - Do sales/issues for this patient indent.
/*		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		//Sales sales = new Sales(executeStep, verifications);
		//sales.doAddToBillSales();
		//sales.doBillNowSales();
		sales.searchMRNo();
		sales.doSales("", "RaiseBillPayment");
*/		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		raisePatientIndent.doSalesOrSalesReturnsForIndent(true, "");
		//sales.closePrescriptionTab();
		

		//Step 18 - Raise a return indent either full or partial
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");
		RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("IndentSales", false);
		
		//Step 19 - Do Sales or returns for the above indent
				
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.doSalesOrSalesReturnsForIndent(false, "");		 

		//Verifications:
		//Step 20 - Observe the item level parameters like Rate,Discount,Net and Amount also check the total amount,total discount and total due in bill
		//Step 21 - Now from bill navigate to change rate plan/bed type screen and change the rate plan to NewRatePlan with bill action as 'All Previous Charges' where rates/rate plan discounts defined are different from General rate sheet 
		//The Step 21 doesn't come in Bill Now scenario
		//navigation.navigateTo(driver, "BillsLink", "BillListPage");
		//SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		//searchBillList.searchBills();
		//ChangeRatePlanBedType chngRatePlanBedType = new ChangeRatePlanBedType(executeStep, verifications);
		//chngRatePlanBedType.changeAndSaveRatePlanAndBedType();
		
		//Step 22 - Observe the item level rates,rate plan discount and patient due in bill also observe total dues

	
		//Step 23 - Cancel Order - Cancel without Refund - can we do a cancel order for bill now scenario
		//navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		
		//Order order = new Order(executeStep, verifications);
		//order.searchOrder(false);
		//order.cancelOrder();
		

		//Step 25
		/*navigation.navigateTo(driver, "BillsLink", "BillListPage");
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");
		
		//Step 26
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		//Select Bill Type as Raise Bill and click on Raise Bill and Print
		//Bill Receipt is opened in new screen. Verify that and Close the Bill receipt
		//Sales sales = new Sales(executeStep, verifications);
		sales.doPharmacySales();
		
		//27. Do sales returns for partial/full qty by clicking on Raise bill/Pay&Print/Add to bill.
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		//SalesReturn salesReturn=new SalesReturn(executeStep, verifications);
		salesReturn.doSalesReturn("ReturnedSalesIndent", "OP");
		
		
		//Step 28
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");
		//executeStep.performAction(SeleniumActions.Click, "","BillListPatientBills");
		//verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",true);
		
		//executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
		//verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
	
		patientBillNow = new PatientBill(executeStep, verifications);		
		billType = "Settlement";
		patientBillNow.savePatientBillCashPatient(billType);		
		PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.settleBills("NO", "UNPAID", "OP");;*/
		
		testCaseStatus = "Pass";
		System.out.println("TS_001 completed Successfully");

	}
	
	@AfterClass
	public void closeBrowser(){
		String delimiter = " :: ";
		//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
		//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OPWorkFlowForBillNow", null, "", "", testCaseStatus);
		driver.close();
	}
		
}
