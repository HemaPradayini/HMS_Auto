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
import reusableFunctions.ClaimsSubmission;
import reusableFunctions.Codification;
//import reusableFunctions.ClaimsSubmission;
//import reusableFunctions.Codification;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.Registration;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientDischarge;
import reusableFunctions.PatientEMR;
import reusableFunctions.PatientIssue;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.RaisePatientIndent;
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
 * @author C N Alamelu
 *
 */
public class IPWFForInsPatientWithoutCopay {
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
		AutomationID = "Login - IPWFForInsPatientWithoutCopay";
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
		AutomationID = "IPWFForInsPatientWithoutCopay";
		DataSet = "TS_029";
		//EnvironmentSetup.testScenarioId = "TS_029";
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		// Delay is introduced to handle the sync problem happening because of two 
		//consecutive close tours and no verification possible
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void IPWFForInsPatientWithoutCopay(){
		openBrowser();
		login();
		
		DataSet = "TS_029";
		AutomationID = "IPWFForInsPatientWithoutCopay";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForInsurance - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);	
		
		navigation.navigateTo(driver, "IPRegistrationLink", "IPRegistrationPage");
		
		//1.Go to IP Registration screen and enter all mandatory fields.Also select 
		// primary sponsor check box and then select sponsor check box and then select 
		//sponsor as TPA1, company as Company1, network plan type as Plan Type1 and 
		//plan name as Plan1 and then select bill type as Bill Later
		//2.Select bed as General, ward, bed number, admitting doctor, duty doctor and 
		//then click on Register

		Registration IPReg = new Registration(executeStep, verifications);
		IPReg.RegisterPatientInsuranceIP();
		
		
		// Observe the item level sponsor and patient amount for all 
		//bed charges in bill also observe the total sponsor and patient due in bill
		 		
		executeStep.performAction(SeleniumActions.Click, "","ProceedToBillingLink");
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
	
	
		//Order all types of items in bill from order screen, also order Lab1 which is 
		// created as specified in pre requisite and observe the sponsor and patient amount 
		// in bill for all ordered items.
			
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
	
		order.addOrderItem("OrderItem","OrdersPage");
		order.saveOrder(false);
	
		//Uncomment the below when field level validation is done and accordingly change the navigation
		//for collect sample
		
		//navigation.navigateTo(driver, "BillsLink", "BillListPage");
		//SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		//searchBillList.searchBills();
		
		// 4a.Go to Laboratory Pending samples screen, select the test and click on collect 
		// menu option. Select the samples by ticking the check boxes and click on Save.
	
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();

		
		//4b. Go to laboratory reports screen > select the test and click on View/Edit, 
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
	
		// 4c. Go to radiology reports > Select the test and click on View/Edit, choose a 
		// template then edit details and save. Mark complete,validate and signoff.

		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);

		
		//4d. Go to pending services list >Select the service and click on edit menu option ,
		//mark completed and save.
		
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
			

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}
		
		//4e. Go to Patient &Visit EMR and observe documents
			
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
	
		//5.Navigate to patient issues screen via bill and add medicines(having package size 1 
		//and 10 and serial items) and also add medicines which is created in pre requisite i.e 
		//DrugClaimable and DrugNonClaimable by giving quantity as 5 each.Click on Save and 
		//complete the issues.
		//6.Observe the sponsor and patient amount for all items in issues screen.
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
			SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
			searchBillList.searchBills();
			
			executeStep.performAction(SeleniumActions.Click, "","IssueLink");
			verifications.verify(SeleniumVerifications.Appears, "","PatientIssuePage",false);
					
		PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
		patientIssue.addPatientIssueItem("IssueItem");
		patientIssue.saveIssue();
			
		
		//7.After completing issues go to bill and observe the sponsor and patient 
		// amount for inventory items in bill

		navigation.navigateTo(driver, "BillsLink", "BillListPage");
//		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		
		
	
		// 8(a)Now go to sales screen and enter the mrno and then click on auto filled record 
		// and then on search.Select bill type as Add To Bill.
		// Click on plus icon and then add DrugCliamable,DrugNonClaimable item, pkg size>1 
		//and =1 with qty >1.Observe the sponsor and patient amount for all the added items 
		//in sales screen
		
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.doSales("IssueItem","SalesPageBillType");
		sales.closePrescriptionTab();
	
	
		//8(b)After completing the sales open the hospital bill.Inorder to open the hospital 
		// bill go to search bill screen, enter mrno, clcik on serach,now click on view/edit 
		// option of the respective bill no.Now observe the  sponsor and patient amt for the 
		// pharmacy bill added to hospital bill
		 
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
	//	SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		
			
		//9.Now go to sales returns screen under Sales module. Enter the mrno and then click 
		// on the auto filled record. Once the patient details are auto filled click on plus 
		// icon and then enter the items which need to be returned with the return qty as equal 
		//to sold qty or partial qty. Observe the Sponsor and Patient amt in sales returns screen.
		//Do sales returns.
		
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturn=new SalesReturn(executeStep, verifications);
		salesReturn.doSalesReturn("IssueItem","IP");	
		
			
		//10. Raise a patient indent by adding serial and batch items (having packet size 1 and 10).
		
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentSales", true);
		

		// 11. Do issues for this patient indent. Observe the sponsor and patient amount 
		// in Hospital bill for the issued items through indent.
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
		//12. Raise a return indent by giving partial/full quantity for the above issued items
		
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");
		RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("IndentSales", false);
	
		// 13. Do issue returns for the above return indent.Observe the sponsor and 
		//patient amount in Hospital bill for the returned inventory items through indent.
		//(serial and batch items having packet size 1 and 10)
		
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//	RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
			raisePatientIndent.searchPatientIndent();
			raisePatientIndent.savePatientIndentIssue(false);
		
		//14 Do shift bed 
		navigation.navigateTo(driver, "ADTLink", "ADTPage");
	//	MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);	
	//	mrnoSearch.searchMRNo("InPatientListMrNoField","InPatientListSelectionMRNoLi","InPatientListSearchButton","ADTPage");
	//	verifications.verify(SeleniumVerifications.Appears, "","ADTPage",false);
		
		//Navigate to Shift Bed Screen
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("ADTShiftBedLink", "ShiftBedScreen");

		BedAllocation ShiftBed = new BedAllocation(executeStep, verifications);
		ShiftBed.AssignBed();
		ShiftBed.SaveShiftBed();
		
		//and observe the sponsor and patient amount in bill for bed charges	
		//Need to open multiple bills
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
//		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		
		
		// 15 Now do bed finalization and observe the sposnor and patient amount in bill
		
		navigation.navigateTo(driver, "ADTLink", "ADTPage");

		BedFinalisation FinaliseBed = new BedFinalisation(executeStep, verifications);
		FinaliseBed.finaliseBed();

	
		//16 Click Ok to Discharge in bill and Finalize the bill
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");			
		PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.settleBills("YES", "UNPAID", "IP");
		//patientBill.settleBills("YES", "PAID", "IP");
			
		//17.Do Sponsor amount settlement
		navigation.navigateTo(driver, "BillsLink", "BillListPage");			
	//	PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.sponsorAmountSettlement("FINALIZED");
		
		//18.Go to Discharge screen and discharge thePatient
		navigation.navigateTo(driver, "DischargeLink", "PatientDischargePage");
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("PatientDischargeMRNoField", "PatientDischargeMRNoLi", "PatientDischargeFindButton", "PatientDischargePage");

		PatientDischarge patientDischarge = new PatientDischarge(executeStep, verifications);		
		patientDischarge.patientDischarge();
		
		//19 Close the bill. Observe the claim status in bill.
		navigation.navigateTo(driver, "BillsLink", "BillListPage");			
	//	PatientBill patientBill = new PatientBill(executeStep, verifications);	
		
		patientBill.closeBill("FINALIZED_INS");
		
		testCaseStatus="PASS";
		
		System.out.println("End Test IPWF For Ins patient without copay");
	
	}
	
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_029", "IPWFForInsPatientWithoutCopay", null, "", "", testCaseStatus);
		driver.close();
	}
	
		
}
