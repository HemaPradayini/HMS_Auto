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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.ChangeRatePlanBedType;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.LabPendingTests;
import reusableFunctions.LabSamplesTransferManual;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
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
public class OPWorkflowInCenterSchema {
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
	}
	
	@Test(groups={"E2E","Regression"})
	public void OPWorkflowInCenterSchemaOrderTests(){
		DataSet = "TS_008A";
		AutomationID = "OPWorkflowInCenterSchemaOrderTests";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkflowInCenterSchemaOrderTests - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		openBrowser();
		loginTest(DataSet);
		
		//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		//Step 1
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
	
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientOP();
		
		System.out.println("EnvironmentSetup.strGlobalException is :: " + EnvironmentSetup.strGlobalException.toString());
		//Step 3 to 5
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		
		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		
//		verifications.verify(SeleniumVerifications.Selected, "CollectSampleTransferTo","CollectSampleTransferTo",false);

		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();

		navigation.navigateTo(driver, "LaboratoryPendingSampleAssertionsLink", "LaboratoryPendingSampleAssertionsPage");
		collectSamples.assertPendingSamples();
		
		navigation.navigateTo(driver, "LabTransferSamplesManualLink", "LabTransferSamplesManualPage");
		LabSamplesTransferManual labTransfers = new LabSamplesTransferManual(executeStep, verifications);
		labTransfers.transferLabSamples();
		System.out.println("EnvironmentSetup.strGlobalException II is :: " + EnvironmentSetup.strGlobalException.toString());

		DbFunctions dbFunc = new DbFunctions();
		dbFunc.saveMRIDVisitID(DataSet);
		
		Login OPWFForCenterLogin = new Login(executeStep, verifications);
		OPWFForCenterLogin.logOut();

	}
	
	//@AfterTest
	
	@Test(groups={"E2E","Regression"})
	public void OPWorkflowInCenterSchemaPerformTests(){

		//openBrowser();
		
		DataSet = "TS_008B";
		AutomationID = "OPWorkflowInCenterSchemaOrderTests";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkflowInCenterSchemaPerformTests - Before Navigation");

		loginTest(DataSet);

		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
				
		//Navigate To
		SiteNavigation navigation1 = new SiteNavigation(AutomationID, DataSet);
		
		//12a Go to Laboratory Pending samples screen, select the test and click on collect menu option. Select the samples by ticking the check boxes and click on Save.
		//12b. Go to laboratory reports screen > select the test and click on View/Edit menu option, enter values (1,2,3,4,etc) complete,validate and Signoff.

		DbFunctions dbFunc = new DbFunctions();
		dbFunc.saveMRIDVisitID(DataSet);
				
		navigation1.navigateTo(driver, "LaboratoryReceiveSamplesLink", "LaboratoryReceiveSamplesPage");
		LabSamplesTransferManual labTransfers = new LabSamplesTransferManual(executeStep, verifications);
		labTransfers.receiveSamples();
		
		navigation1.navigateTo(driver, "LaboratoryPendingSampleAssertionsLink", "LaboratoryPendingSampleAssertionsPage");
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.assertPendingSamples();

		navigation1.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
		searchLabReports();
		
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.saveConductionResults();
		
		navigation1.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		
		navigation1.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
		searchLabReports();
		
		//TestsConduction testConduction 1= new TestsConduction(executeStep, verifications);
		testConduction.signOffReports();
		
		navigation1.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		//MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		
		Login OPWFForCenterLogin = new Login(executeStep, verifications);
		OPWFForCenterLogin.logOut();
		
		
	} // Tejaswini Tested
	
	@Test(groups={"E2E","Regression"})
	public void checkEMRReportsInCenter1(){
		
		DataSet = "TS_008A";
		AutomationID = "checkEMRReportsInCenter1";
		
		loginTest(DataSet);
		
		SiteNavigation navigation2 = new SiteNavigation(AutomationID, DataSet);

		navigation2.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);

		navigation2.navigateTo(driver, "SignedOffReportLink", "SignedOffReportListPage");

		search.searchMRNo("PendingSampleAssertionsSearchMRID","PendingSampleAssertionsSearchMRIDContainer","PendingSampleAssertionsSearchButton","SignedOffReportListPage");

		executeStep.performAction(SeleniumActions.Enter, "VisitID","PendingSampleAssertionsSearchMRID");
		verifications.verify(SeleniumVerifications.Entered, "VisitID","PendingSampleAssertionsSearchMRID",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSampleAssertionsSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","SignedOffReportListPage",false);
		
//		navigation2.navigateTo(driver, "SignedOffReportsLink", "SignedOffReportListPage");
//        search.searchMRNo("PendingSampleAssertionsSearchMRID","PendingSampleAssertionsSearchMRIDContainer","PendingSampleAssertionsSearchButton","PendingSampleAssertionsTable");
		
		executeStep.performAction(SeleniumActions.Click, "","SignedOffReportListAlert");
		//verifications.verify(SeleniumVerifications.Appears, "","SignedOffReportListPage",false);


		testCaseStatus = "Pass";
		System.out.println("TS_008 completed Successfully");
		
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

	private void searchLabReports(){
		MRNoSearch searchMRNo = new MRNoSearch(executeStep, verifications);
		searchMRNo.searchMRNo("LabReportsMRNoField", "LabReportsMRNoLi", "LabReportsMRNoSearch", "LabReportsMRNoSearchResultsTableFirstRow");
		executeStep.performAction(SeleniumActions.Check, "","LabReportsReportCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","LabReportsReportCheckBox",true);
		
		executeStep.performAction(SeleniumActions.Click, "","LabReportsReportRow");
		verifications.verify(SeleniumVerifications.Appears, "","LabReportsReportViewEditMenu",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabReportsReportViewEditMenu");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
	}

	@AfterClass
	public void closeBrowser(){
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OPWorkflowInCenterSchema", null, "", "", testCaseStatus);
		driver.close();
	}
}
