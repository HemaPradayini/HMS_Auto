/**
 * 
 */
package e2e;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.ChangeRatePlanBedType;
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
import reusableFunctions.PatientBillOSP;
import reusableFunctions.PatientEMR;
import reusableFunctions.PatientInsurance;
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
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

/**
 * @author Tejaswini - Changed 26/02/2017
 *
 */
public class OPWFWithInsInCenterSchWithDiffHealthAuth {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	
	@BeforeSuite
	public void BeforeSuite(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - OPWorkflowInCenterSchema";
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
	
	@BeforeMethod
	public void BeforeTest(){

		
		//executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void OPWorkflowInCenterSchemaOrderAndConductTests(){
		DataSet = "TS_018A";
		AutomationID = "OPWorkflowInCenterSchemaOrderAndConductTests";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkflowInCenterSchemaOrderAndConductTests - Before Navigation");
		
		loginTest(DataSet);
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		//Step 1
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		createOutPatient();
		
		//Step 3 to 5
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		orderTests();

		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		performTests();
	
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		settleBills();
		
		navigation.navigateTo(driver, "CodificationLink", "EditCodesScreen");
		Codification codification=new Codification(executeStep, verifications);
		codification.saveCodes(true);	
		
		Login login = new Login(executeStep, verifications);
		login.logOut();
	}
	
	//@AfterTest
	
	@Test(groups={"E2E","Regression"})
	public void OPWorkflowInCenterSchemaOrderConductAndDoSales(){
		DataSet = "TS_018B";
		AutomationID = "OPWorkflowInCenterSchemaOrderAndConductTests";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkflowInCenterSchemaOrderAndConductTests - Before Navigation");
		
		loginTest(DataSet);
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		//Step 1
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		createOutPatient();
		
		//Step 3 to 5
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		orderTests();

		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		performTests();
		
		Sales sales = new Sales(executeStep, verifications);
		sales.doSales("AddToBillSales", "AddToBillPaymentType");
		sales.closePrescriptionTab();
	
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		MRNoSearch mrNoSearch = new MRNoSearch(executeStep, verifications);
		mrNoSearch.searchMRNo("RaisePatientIndentMRNoField", "RaisePatientIndentMRNoList", "RaisePatientIndentMRNoFindButton", "RaisePatientIndentPage");		
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentForSales",true);
		System.out.println("Step 6 complete");
		
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.doSalesOrSalesReturnsForIndent(true, "");
		System.out.println("Step 6b/7 complete");
		
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		//MRNoSearch mrNoSearch = new MRNoSearch(executeStep, verifications);
		mrNoSearch.searchMRNo("RaisePatientIndentMRNoField", "RaisePatientIndentMRNoList", "RaisePatientIndentMRNoFindButton", "RaisePatientIndentPage");		
		//RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentForIssue",true);
		System.out.println("Step 6 complete");
		
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndentIssue(true);
		System.out.println("Step 6b/7 complete");
		
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturn=new SalesReturn(executeStep, verifications);
		salesReturn.doSalesReturn("SalesReturn", "OP");
		
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");		
		RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("IndentForSalesReturns", false);
		System.out.println("Step 8 complete");
		
		//9. Go to Patient Indents List, select the above raised indent and click on Sales Returns. Click on Add to Bill(by selecting Bill2) to complete sales returns.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		//raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.doSalesOrSalesReturnsForIndent(false, "");
		
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");		
		//RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("IndentForIssueReturns", false);
		System.out.println("Step 8 complete");
		
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndentIssue(false);
		System.out.println("Step 6b/7 complete");
		
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		settleBills();
		
		navigation.navigateTo(driver, "CodificationLink", "EditCodesScreen");
		Codification codification=new Codification(executeStep, verifications);
		codification.saveCodes(true);			
		
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		generateClaims();
		
		Login login = new Login(executeStep, verifications);
		login.logOut();	
	} // Tejaswini Tested

	@Test(groups={"E2E","Regression"})
	public void OPWFCreateClaimsSubmissionInCenter1(){
		
		DataSet = "TS_018B";
		AutomationID = "OPWorkflowInCenterSchemaOrderAndConductTests";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkflowInCenterSchemaOrderAndConductTests - Before Navigation");
		
		loginTest(DataSet);
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);		
		
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		generateClaims();
	}
		
	private void loginTest(String DataSet){
		AutomationID = "Login - OPWorkflowInCenterSchema";
		//DataSet = "TS_008";
		//EnvironmentSetup.testScenarioId = "TS_001";
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
	}

	private void createOutPatient(){
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientOP();
		OPReg.RegisterPatientInsurance("OP");
		OPReg.GovtIDDetailsCollapsedPanel();
		OPReg.setBillType();
		OPReg.storeDetails();
	}
	
	private void orderTests(){
		
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");
		
		executeStep.performAction(SeleniumActions.Click, "","BillListPatientBills");
		verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",true);
		
		executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.addItemsIntoPatientBill();		

	}
	
	private void performTests(){
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
		verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
		
		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		
		PatientBillOSP patientBills = new PatientBillOSP(executeStep, verifications);
		int noOfPendingTests = patientBills.getNoOfBills();
		for (int i=1; i< noOfPendingTests; i++){
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
	}
	
	private void settleBills(){
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");
		PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.settleBills("YES", "UNPAID", "OP");
	}
	
	private void generateClaims(){
		
		ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		claimsSubmission.submitClaims("InsuranceCenterAccGrp",true,false);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",true);
		executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
		verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",true);
	}
}
