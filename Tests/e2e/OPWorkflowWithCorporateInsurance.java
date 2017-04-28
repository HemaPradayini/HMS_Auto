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

import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.ChangeRatePlanBedType;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DiagnosisDetails;
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
import reusableFunctions.PatientIssue;
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
import reusableFunctions.Upload;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author Alamelu
 *
 */
public class OPWorkflowWithCorporateInsurance {
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
        long id = Thread.currentThread().getId();
        System.out.println("Before suite-method in OPWorkFlowForCashPatient. Thread id is: " + id);
		AutomationID = "Login - OPWorkflowWithCorporateInsurance";
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
		AutomationID = "OPWorkflowWithCorporateInsurance";
		DataSet = "TS_017";
	
        long id = Thread.currentThread().getId();
        System.out.println("Before test-method in OPWorkFlowForCashPatient. Thread id is: " + id);
		
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
	public void OPWorkflowWithCorporateInsurance(){
		openBrowser();
		login();
		
		DataSet = "TS_017";
		AutomationID = "OPWorkflowWithCorporateInsurance";
		long id = Thread.currentThread().getId();
        System.out.println("@Test -method in OPWorkFlowForCashPatient. Thread id is: " + id);
		
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForCashPatient_1 - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);

		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");	
		
		//1.Login to a schema where corporate insurance is enabled i.e as said in pre requisite (1)
		//2.Go to OP Registration screen enter all mandatory information . Select primary sponsor check box and then select sponsor check box and then select sponsor as TPA1, company as Company1, network plan type as Plan Type1 and plan name as Plan1 and then select bill type as Bill Later.
		//3.Complete Registration.
		
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientGenericDetails();
		OPReg.setBillType();
		OPReg.visitInformationDetails();
		OPReg.GovtIDDetailsCollapsedPanel();
		OPReg.unCheckPrimarySponsor();
		OPReg.RegisterPatientInsurance("OP");
		
		Upload uploadfile=new Upload(executeStep, verifications);
		uploadfile.upload("//input[@id='primary_insurance_doc_content_bytea1' and @type='file']");
		
		OPReg.storeDetails();
		


		//4.Go to OP List screen and enter the mrno. Now click on Consult link
		//5.Enter all required sections / fields in consultation/ IA / Triage/ generic forms
		//This is for navigation to the OP List and searching with the MR No on the OPList and 
		//navigating to the consultation and management screen
		//6.Prescribe all types of items like medicines(serial and batch items having packet size 
		//1 and 10) by giving quantity as 5, Lab, rad, services, diag packages and save the 
		//consultation screen. User should be blocked from saving the consultation screen if 
		//diagnosis code is not entered
		
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
		
		ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
		//consultMgmt.saveConsultationAndMgmt("Diagnosis");
		DiagnosisDetails diagDetails = new DiagnosisDetails(executeStep, verifications);
		diagDetails.addDianosisDetails( "Diagnosis","ConsultationAndManagementPage");  // Parameter added by Abhishek
		consultMgmt.doConsultationAndMangementCorpInsurance(); 

		executeStep.performAction(SeleniumActions.Click, "","ConsulationMgtScreenTriageLink");
		verifications.verify(SeleniumVerifications.Appears, "","TriagePage",false);

		Triage triage = new Triage(executeStep, verifications);
		triage.saveTriage();

		OpenGenericForm opGenericForm = new OpenGenericForm(executeStep, verifications);
		opGenericForm.openGenericForm();
		
		//7.Now under Billing module, click on Order link. Enter the mrno in order screen, and 
		//then click on the auto filled record. Click on Find
		//8.In Order screen user will be alerted that Pending Prescription exists, so click on OK. 
		//Observe the patient amount for the auto filled items in order screen.Save the order.
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		
		executeStep.performAction(SeleniumActions.Click, "","BillPageOrderLink");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
		verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",false);
	
		executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenSave");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);	
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "", "OrdersPage", false);	

		
		//9.Click on bill now link which is available in order screen at footer.After navigating 
		//to bill screen observe the sponsor and patient amounts at item level in bill for all 
		//the items and also total sponsor due and patient due.Clear the patient due in bill.
		
		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenBillLink");
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		
		PatientBill patientBill = new PatientBill(executeStep, verifications);		
		//patientBill.settleBills("YES", "PAID", "IP");
	//	patientBill.setBillStatus("YES");
		patientBill.settleBillDetails("YES","SettlementPaymentType");
	
		
		//10.a.Go to Laboratory Pending samples screen, select the test and click on collect menu 
		//option. Select the samples by ticking the check boxes and click on Save.
		
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
		
		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
		verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
		
		
		//10.b. Go to laboratory reports screen > select the test and click on View/Edit, enter 
		//values (1,2,3,4,etc).Observe the Edit link for reagent.
		
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
		

		//10c. Click on the Edit link, and edit the qty of reagent say 1,2,etc and save.
		executeStep.performAction(SeleniumActions.Click, "","TestConductionReagentEditLink");
		verifications.verify(SeleniumVerifications.Appears, "","EditDiagnosticsReagentsDetailsPage",false);
		
		executeStep.performAction(SeleniumActions.Enter, "ReagentQty","EditDiagnosticsReagentsDetailsQuantityField");
		verifications.verify(SeleniumVerifications.Entered, "ReagentQty","EditDiagnosticsReagentsDetailsQuantityField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","EditDiagnosticsReagentsDetailsSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditDiagnosticsReagentsDetailsPage",false);
	
		executeStep.performAction(SeleniumActions.Click, "","EditDiagnosticsReagentsDetailsEdittestResultLink");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
		//10d.Now mark complete,validate and Signoff.	
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.signOffReports();
		
			//10.e. Go to radiology reports > Give mr.no and select the test and click on View/Edit then select 
		//conduction status as 'Modaity Arrived' and save.
		
		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyReportDisplay();
	
		
		//10f. Observe the Edit link for reagent. Click on the Edit link and edit the qty to a different qty and save.
		//check if it is done in Radiology screen
				
		executeStep.performAction(SeleniumActions.Click, "","TestConductionReagentEditLink");
		verifications.verify(SeleniumVerifications.Appears, "","EditDiagnosticsReagentsDetailsPage",false);
		
		executeStep.performAction(SeleniumActions.Enter, "ReagentQty","EditDiagnosticsReagentsDetailsQuantityField");
		verifications.verify(SeleniumVerifications.Entered, "ReagentQty","EditDiagnosticsReagentsDetailsQuantityField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","EditDiagnosticsReagentsDetailsSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditDiagnosticsReagentsDetailsPage",false);
	
		executeStep.performAction(SeleniumActions.Click, "","EditDiagnosticsReagentsDetailsEdittestResultLink");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
//		10g. Mark complete,validate and signoff.
		executeStep.performAction(SeleniumActions.Click, "","Test(s)ConductionDefaultReport");
		verifications.verify(SeleniumVerifications.Opens, "","Test(s)ConductionDefaultReportFile",false);
		
		executeStep.performAction(SeleniumActions.Click, "","Test(s)ConductionDefaultReportFileSave");
		verifications.verify(SeleniumVerifications.Closes, "","Test(s)ConductionDefaultReportFile",false);
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
		TestsConduction testsConduction = new TestsConduction(executeStep, verifications);
		testsConduction.signOffReports();
		
	
		//10.h.Go to pending services list >Select the service and click on edit menu option ,
		//mark completed and save.
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}
		
		//10.i.Go to Patient &Visit EMR and observe documents
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
//		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
	
	//	11. Now under Sales and Issues module click on 'Sales' link. Wait for the medicines and 
		//patient details to get auto-filled. Observe the sponsor and patient amount in Sales screen 
		//Select bill type as Add To Bill and complete the sales. 
		
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.payAndPrint(false);
		
		//Observe the sponsor and patient amount in hospiatl bill against the sale bill.
		
		navigation.navigateTo(driver, "SearchBillLink", "BillListPage");		
		
	//	PatientBill patientBill = new PatientBill(executeStep, verifications);	
		patientBill.viewEditBills("BL");
			
		//12. Go to sales returns screen under sales and issues module and enter the mrno. Once the 
		//record is auto filled with visit id click on it. 
		//13.After all the patient details are auto filled,click on plus icon and enter the item names 
		//with return qty as partial qty i.e if sale is done with 5 qty enter 2 qty as return qty.
		//Observe the sponsor and patient amount in sales returns screen.Complete sales returns and 
		
		
		navigation.navigateTo(driver, "SalesReturnLink", "SalesPage");
		SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		salesReturns.doSalesReturn("SalesReturns","OP");
		
		//observe the sponsor and patient amount in hospital bill wrt sale bill.
		
		navigation.navigateTo(driver, "SearchBillLink", "BillListPage");		
		
		//	PatientBill patientBill = new PatientBill(executeStep, verifications);	
		patientBill.viewEditBills("BL");
			
	//	14.Now click on Issues link in bill. User will be navigated to issues screen, once the patient
		//details are auto filled, click on plus icon and enter the medicines with the issue qty as 5. 
		//Complete the issues by clicking on Save.
		navigation.navigateTo(driver, "SearchBillLink", "BillListPage");		
		
	//	PatientBill patientBill = new PatientBill(executeStep, verifications);	
		patientBill.viewEditBills("BL");
			
		executeStep.performAction(SeleniumActions.Click, "","IssueLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientIssuePage",false);
			
		PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
		patientIssue.addPatientIssueItem("IssueItem");		
		patientIssue.saveIssue();
		
		
	//	15.Now go to Patient Issue Returns screen under sales/issues module and enter the return qty. 
		//Say issue is done with 5 qty then return 2 qty by clicking on Save. Observe the patient 
		//amount in issue returns screen. 
		
		navigation.navigateTo(driver, "PatientIssueReturnLink", "PatientIssueReturnPage");
		//	PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
			patientIssue = new PatientIssue(executeStep, verifications);
			//patientIssue.searchPatientIssue();
			patientIssue.returnPatientIssueItem("IssueItem");		
			patientIssue.saveIssueReturns();
			System.out.println("Step 5 complete");
	
		//	16.Observe sponsor and patient amount in hospiatl bill for inventory items after doing returns.
		//	17.Do sponsor and patient settlement in bill and then close the bill. Observe the claim status
		//in bill
		
			navigation.navigateTo(driver, "BillsLink", "BillListPage");				
	//	PatientBill patientBill = new PatientBill(executeStep, verifications);	
	//	patientBill.closeBill("BL");
		patientBill.closeBill("OPEN_UNPAID_INS");
	
		testCaseStatus="PASS";
		System.out.println("End of OP Workflow with Corporate Insurance");
			
	}
	
	@AfterTest
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_017", "OPWorkflowWithCorporateInsurance", null, "", "", testCaseStatus);
		
		driver.close();
	}
	
}
