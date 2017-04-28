package e2e;

import java.util.List;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
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
import reusableFunctions.ChangeRatePlanBedType;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.IPListSearch;
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
import reusableFunctions.PatientIssue;
import reusableFunctions.PatientVisit;
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

/*
 * @author Tejaswini
 *
 */
public class OPToIPWorkflowWithCopyChargesBillAction {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String patientBillNo2="";
	String testCaseStatus = "Fail";


//	@BeforeSuite
//	public void BeforeSuite(){
	private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - OPToIPWorkflowWithCopyChargesBillAction";
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
		AutomationID = "Login - OPToIPWorkflowWithCopyChargesBillAction";
		DataSet = "TS_037";
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
	public void OPToIPWorkflowWithCopyChargesBillAction(){
		openBrowser();
		login();
		DataSet = "TS_034A";
		AutomationID = "OPToIPWorkflowWithCopyChargesBillAction";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPToIPWorkflowWithCopyChargesBillAction - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//1.Register an OP patient with Bill Later bill by enetring all mandatory fields		
		//Navigate To OP Registration Screen
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		//This is for navigation to the OP List and searching with the MR No on the OPList and navigating to the consultation and management screen
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientGenericDetails();
		OPReg.setBillType();
		OPReg.unCheckPrimarySponsor();
		OPReg.visitInformationDetails();
		OPReg.storeAndEditBill();
		System.out.println("Step 1 complete");
		
		//2.Go to bill and click on Order link at bottom and order all billable items(such as lab,radiology,services,etc) in Bill1 and save the screen
		
		EnvironmentSetup.UseLineItem= true;
	
		PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.addBillOrder("OrderOrdItem");
		//executeStep.performAction(SeleniumActions.CloseTab, "","OrdersPage");
		//verifications.verify(SeleniumVerifications.Closes, "","OrdersPage",false);		
		System.out.println("Step 2 complete");
			
		//3a. Go to Sales screen, enter the MR No, add items(having packet size 1 and 10 and serial items) by giving quantity as 5.
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);	
		//EnvironmentSetup.selectByPartMatchInDropDown = true;
		sales.doSales("FirstSales", "AddToBillPaymentType");
		sales.closePrescriptionTab();
		saveBillNo(navigation, "BillNumber-1");
		System.out.println("Step 3a complete");
		
		//3c. Go to Sales Returns screen, enter sale bill number, add items which were sold previously by giving quantity as 3 and click on Add to Bill to complete sales returns.
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		salesReturns.doSalesReturnByBillNumber("FirstSales","OP", "BillNumber-1", "", "");
		System.out.println("Step 3c complete");
		
		//3d. Go to Patient Issue screen, enter patient's MR No., add another set of items(having packet size 1 and 10 and serial items) by giving quantity as 5 and click on Save to complete issues.
		navigation.navigateTo(driver, "PatientIssueLink", "PatientIssuePage");
		PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
		patientIssue.searchPatientIssue();
		patientIssue.addPatientIssueItem("FirstIssueItem");		
		patientIssue.saveIssue();
		System.out.println("Step 3d complete");
		
		//3e. Go to Patient Issue Returns screen, add items which were issued previously by giving 3 quantity each and click on Save to complete issue returns.
		navigation.navigateTo(driver, "PatientIssueReturnLink", "PatientIssueReturnPage");
		//PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
		//patientIssue.searchPatientIssue();
		patientIssue.returnPatientIssueItem("FirstIssueItem");		
		patientIssue.saveIssueReturns();
		System.out.println("Step 3e complete");
		
		//4. Now go to New bill screen under Billing Module, enter mr_no of patient in mr_no column, click on search and create a new bill later bill say Bill2 by checking Bill later Radio button and order all types of items in newly created bill.
		navigation.navigateTo(driver, "NewBillLink", "NewBillPage");
		//PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.createBill();
		try{
			WebElement we = driver.findElement(By.xpath("//td[contains(text(),'Bill No (Type):')]/following-sibling::td[1]"));
			System.out.println(we.getText());
			int index =  we.getText().indexOf('(');
			System.out.println("index is " + index);
			if (we != null){
				patientBillNo2 = we.getText().substring(0,index).trim();
			}
		} catch (Exception e){
			System.out.println("Web Element is not found");
		}			
		EnvironmentSetup.UseLineItem = true;
		System.out.println("");
		EnvironmentSetup.LineItemIdForExec = "BillNumber-2";
		executeStep.performAction(SeleniumActions.Store, "BillNumber","BillNumberField");
		EnvironmentSetup.UseLineItem = false;
		EnvironmentSetup.LineItemIdForExec = "";
		patientBill.addBillOrder("OrderPatientBillItem");
		System.out.println("Step 4 complete");
		
		//5a. Go to Sales screen, enter the MR No, add items(having packet size 1 and 10 and serial items) by giving quantity as 5.
		//5b. Click on Add to Bill(by selecting Bill2) to complete sales.

		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		//Sales sales = new Sales(executeStep, verifications);	
		//EnvironmentSetup.selectByPartMatchInDropDown = true;
		sales.doAddToBillSales("SecondSales", patientBillNo2 , "AddToBillPaymentType");
		sales.closePrescriptionTab();
		saveBillNo(navigation, "BillNumber-3");
		System.out.println("Step 5a/b complete");
		
		//5c. Go to Sales Returns screen, enter sale bill number(for sales done in step 5b), add items which were sold previously by giving quantity as 3 and click on Add to Bill(by seelecting Bill2) to complete sales returns.
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		//SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		//billNo = "BN1025357";
		salesReturns.doSalesReturnByBillNumber("SecondSales","OP", "BillNumber-3", patientBillNo2, "AddToBill");
		System.out.println("Step 5c complete");
		
		//5d. Go to Patient Issue screen, enter patient's MR No., add another set of items(having packet size 1 and 10 and serial items) by giving quantity as 5 and click on Save(by picking Bill2) to complete issues.
		navigation.navigateTo(driver, "PatientIssueLink", "PatientIssuePage");
		//PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
	//	patientIssue = new PatientIssue(executeStep, verifications);
		patientIssue.searchPatientIssue();
		patientIssue.addPatientIssueItem("IssueSecondItem");
		EnvironmentSetup.LineItemIdForExec = "BillNumber-2";
		EnvironmentSetup.UseLineItem = true;
		executeStep.performAction(SeleniumActions.Select, "BillNumber","BillNoDropDown");
		EnvironmentSetup.LineItemIdForExec = "";
		EnvironmentSetup.UseLineItem = false;
		patientIssue.saveIssue();
		System.out.println("Step 5d complete");
		
		//5e. Go to Patient Issue Returns screen, add items which were issued previously by giving 3 quantity each and click on Save(by picking Bill2) to complete issue returns.
		navigation.navigateTo(driver, "PatientIssueReturnLink", "PatientIssueReturnPage");
		//PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
		patientIssue.returnPatientIssueItem("IssueSecondItem");
		EnvironmentSetup.LineItemIdForExec = "BillNumber-2";
		EnvironmentSetup.UseLineItem = true;
		executeStep.performAction(SeleniumActions.Select, "BillNumber","BillNoDropDown");
		EnvironmentSetup.LineItemIdForExec = "";
		EnvironmentSetup.UseLineItem = false;
		patientIssue.saveIssueReturns();
		System.out.println("Step 5e complete");		
		//6a. Go to Raise Patient Indent screen under Sales and Issues, enter MR No., add another set of items(having packet size as 1 and 10 and serial items) by giving quantity as 5 and save the patient indent in finalized status.
		
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		MRNoSearch mrNoSearch = new MRNoSearch(executeStep, verifications);
		mrNoSearch.searchMRNo("RaisePatientIndentMRNoField", "RaisePatientIndentMRNoList", "RaisePatientIndentMRNoFindButton", "RaisePatientIndentPage");		
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("FirstIndent",true);
		System.out.println("Step 6 complete");
		
		
		//6b. Go to Patient Indents List under Sales and Issues, select the above generated indent and click on Sales.
		//7. Click on Add to Bill(by selecting Bill2) in Sales screen to complete sales for the indented items.

		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.doSalesOrSalesReturnsForIndentForBillNo(true,"", "", patientBillNo2);
		saveBillNo(navigation, "BillNumber-4");

		System.out.println("Step 6b/7 complete");
		
		//8. Go to Raise Patient Return Indent screen under Sales and Issues, enter MR No., add items which were sold through patient indent by giving quantity as 3 and save the indent in finalized status.
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");		
		RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("FirstIndent", false);
		System.out.println("Step 8 complete");
		
		//9. Go to Patient Indents List, select the above raised indent and click on Sales Returns. Click on Add to Bill(by selecting Bill2) to complete sales returns.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		//patientBillNo2 = "BN1025363";
		raisePatientIndent.doSalesOrSalesReturnsForIndentForBillNo(false,"FirstIndent", "BillNumber-4", patientBillNo2);
		System.out.println("Step 9 complete");
		
		//10. Now go to New bill screen under Billing Module, enter mr_no of patient in mr_no column, click on search and create a new bill later bill say Bill2 by checking Bill later Radio button and order all types of items in newly created bill.
		navigation.navigateTo(driver, "NewBillLink", "NewBillPage");
		//PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.createBill();
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.LineItemIdForExec = "BillNumber-5";
		executeStep.performAction(SeleniumActions.Store, "BillNumber","BillNumberField");
		EnvironmentSetup.UseLineItem = false;
		EnvironmentSetup.LineItemIdForExec = "";
		patientBill.addBillOrder("SecondPatientBillOrder");
		System.out.println("Step 10 complete");
		
		//11. Go to Sales screen, enter MR No, add items(having packet size as 1 and 10 and serial items) and complete sales by clicking on Raise Bill. Let the bill generated be PBill4.

		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		//Sales sales = new Sales(executeStep, verifications);	
		sales.doSales("ThirdSales", "RaiseBillPayment");
		sales.closePrescriptionTab();		
		System.out.println("Step 11 complete");
		
		//12.Now keep Bill1&PBill4 with Open status, finalize Bill2 and Close Bill3 by clearing all Pat due.
		//TODO
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		
		//PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.viewEditBills("BL");
		patientBill.setBillStatus("NO");
		patientBill.settleBillDetails("NO", "SettlementPaymentType");

	
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");
		executeStep.performAction(SeleniumActions.Click, "","BillListSecondBillLink");
		verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
		executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		//PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.setBillStatus("YES");
		executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");	
		//patientBill.settleBillDetails("NO", "SettlementPaymentType");
		
		//12a Go to Laboratory Pending samples screen, select the test and click on collect menu option. Select the samples by ticking the check boxes and click on Save.
		//12b. Go to laboratory reports screen > select the test and click on View/Edit menu option, enter values (1,2,3,4,etc) complete,validate and Signoff.
		//12c. Go to radiology reports > Select the test and click on View/Edit menu option, choose a template then edit details and save. Mark complete,validate and signoff.
		//12d. Go to pending services list > Select the service and click on edit menu option, mark completed and save.
		performOrderedTestsAndSignOffReports(navigation);
		System.out.println("Step 12a-d complete");
		
		//12d. Go to pending services list > Select the service and click on edit menu option, mark completed and save.
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();

		System.out.println("Step 12a-d complete");

		//12e. Go to Patient & Visit EMR and check all the reports.
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		System.out.println("Step 12e complete");
		
		//13.Now navigate to Edit Patient Visit Details screen via Edit Visit details link under Registration module.
		//14.Click on Convert OP To IP link
		//15.Now observe the available bill actions for all bills and select 'Copy Charges To IP Bill Later Bill' for which all bills it is available and rest with Connect To IP
		navigation.navigateTo(driver, "EditPatientVisit", "EditPatientVisitPage");
		PatientVisit patientVisit = new PatientVisit(executeStep, verifications);
		patientVisit.convertOpToIp();
		System.out.println("Step 13-15 complete");

		//16.After converting the Op patient to IP observe the visit id linked for all bills and also bill no
		//TODO 17.Observe the rate and amounts in all the bills
		//18.Order all billable items in newly created IP Bill Later bill.
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		//PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill = new PatientBill(executeStep, verifications);
		patientBill.addBillOrder("ThirdPatientBillOrder");
		System.out.println("Step 16-18 complete");

		//18a. Observe reports in EMR
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		//MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		System.out.println("Step 18a complete");
		
		//19. Repeat step 12a to 12d.		
		performOrderedTestsAndSignOffReports(navigation);
		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
		
		System.out.println("Step 19 complete");
		
		//20. Go to Patient & Visit EMR and check all the reports.
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		//MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
		mrnoSearch.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		System.out.println("Step 20 complete");
		
		navigation.navigateTo(driver, "VisitEMRLink", "VisitEMRPage");
		mrnoSearch = new MRNoSearch(executeStep, verifications);
		mrnoSearch.searchMRNo("VisitEMRMRNoField","VisitEMRMRNoLi","VisitEMRGetDetailsBtn","VisitEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","VisitEMRPage",false);
		System.out.println("Step 20 complete");

		//21.Allocate a bed and then finalize the bed for 1 day

		navigation.navigateTo(driver, "ADTLink", "ADTPage");
		//MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
		mrnoSearch.searchMRNo("InPatientListMrNoField","InPatientListSelectionMRNoLi","InPatientListSearchButton","ADTPage");
		verifications.verify(SeleniumVerifications.Appears, "","ADTPage",false);		
		executeStep.performAction(SeleniumActions.Click, "","ADTSearchResultsTable");
		verifications.verify(SeleniumVerifications.Appears, "","AllocateBedLink",false);		

		executeStep.performAction(SeleniumActions.Click, "","AllocateBedLink");
		verifications.verify(SeleniumVerifications.Appears, "","AllocateBedPage",false);
		
		BedAllocation allocateBed = new BedAllocation(executeStep, verifications);
		allocateBed.AssignBed();
		allocateBed.setDutyDoctor();
		allocateBed.AllocateBed();
		
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
	
		navigation.navigateTo(driver, "ADTLink", "ADTPage");
		BedFinalisation FinaliseBed = new BedFinalisation(executeStep, verifications);
		FinaliseBed.finaliseBed();
		
		System.out.println("Step 21 complete");
		
		//22.Do patient amount settlement in bill and close the bill
		
		navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
		//PatientBill patientBill = new PatientBill(executeStep, verifications);			
		//MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");			
		patientBill.closeBill("OPEN_UNPAID_NONINS");

		//
		navigation.navigateTo(driver, "BillsLink", "BillListPage");			
		//PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.closeBill("FINALIZED");
		
		System.out.println("Step 22 complete");
		testCaseStatus = "Pass";
		System.out.println("TS_034 completed Successfully");

		////
	}
	
	public void performOrderedTestsAndSignOffReports(SiteNavigation navigation){
				
		//12a Go to Laboratory Pending samples screen, select the test and click on collect menu option. Select the samples by ticking the check boxes and click on Save.
		//12b. Go to laboratory reports screen > select the test and click on View/Edit menu option, enter values (1,2,3,4,etc) complete,validate and Signoff.
		
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
		
		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");

		PatientBill patientBills = new PatientBill(executeStep, verifications);
		int noOfPendingTests = patientBills.getNoOfBills();
		for (int i=0; i< noOfPendingTests; i++){
			System.out.println("Value of i is :: " + i);
			System.out.println("no of pending bills is :: " + noOfPendingTests);
			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
			verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);			
			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
			verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
			TestsConduction testConduction = new TestsConduction(executeStep, verifications);
			testConduction.conductTests();
			navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
			search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
			noOfPendingTests = patientBills.getNoOfBills();
		}
		
		//12c. Go to radiology reports > Select the test and click on View/Edit menu option, choose a template then edit details and save. Mark complete,validate and signoff.
/*		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		//Order order = new Order(executeStep, verifications);
		order.searchOrder(false, false);
		String radiologyText = "";
		try{
		radiologyText = this.executeStep.getDriver().findElement(By.xpath("//td[contains(text(), 'Radiology')]")).getText();
		System.out.println("radiology text is :: " + radiologyText);
		} catch (Exception e){
			System.out.println("Exception is :: " + e.toString());
		}
		if (radiologyText != null && radiologyText.equalsIgnoreCase("Radiology")){
			navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
			RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
			radiologyPendingTests.radiologyPending(true);
		}
*/
		//12d. Go to pending services list > Select the service and click on edit menu option, mark completed and save.
		//navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		//ServicePending servicePending = new ServicePending(executeStep, verifications);
		//servicePending.conductPendingServices();
	}
	
	//@AfterMethod
	public void saveDataForQuickEstimate(){
		DbFunctions dbFunc = new DbFunctions();
		dbFunc.saveMRIDVisitID("TS_053A");
	}
	
	@AfterTest
	public void closeBrowser(){
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OSPWorkFlowForCashPatient", null, "", "", testCaseStatus);
		driver.close();
	}
	
	public void saveBillNo(SiteNavigation navigation, String lineItemId){
		System.out.println("Saving Bill Number");
		navigation.navigateTo(driver, "DuplicateBillsLink", "DuplicateSalesBillPage");
		executeStep.performAction(SeleniumActions.Enter, "MRID","DuplicateSalesBillMRNoField");
		verifications.verify(SeleniumVerifications.Appears, "","DuplicateSalesBillMRNoList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","DuplicateSalesBillMRNoList");
		verifications.verify(SeleniumVerifications.Entered, "MRID","DuplicateSalesBillMRNoField",false);

		executeStep.performAction(SeleniumActions.Click, "","DuplicateSalesBillPageSearchBtn");
		verifications.verify(SeleniumVerifications.Appears, "","DuplicateSalesBillResultTable",false);
		
		EnvironmentSetup.UseLineItem= true;
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		executeStep.performAction(SeleniumActions.Store, "BillNumber","DuplicateSalesBillResultTableBillNoField");
		EnvironmentSetup.UseLineItem= false;
		System.out.println("Completed Saving Bill Number");
	}
	
}
