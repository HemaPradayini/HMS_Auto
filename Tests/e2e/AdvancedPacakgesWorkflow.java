package e2e;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.openqa.selenium.By;
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
import reusableFunctions.ClaimsSubmission;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConductionPackages;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.Order;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientPackages;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.Registration;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Upload;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author Hema
 *
 */

public class AdvancedPacakgesWorkflow {
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
		AutomationID = "Login - AdvancedPacakgesWorkflow";
		reporter = new ReportingFunctions();
		System.out.println("AfterReport");
		
		initDriver = new KeywordExecutionLibrary();
		System.out.println("AfterInitDriver");

		try{
			System.out.println("EnvironmentSetUp is ::" + EnvironmentSetup.URLforExec);
			driver = initDriver.LaunchApp("chrome",  EnvironmentSetup.URLforExec);
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
		AutomationID = "Login - AdvancedPacakgesWorkflow";
		DataSet = "TS_073";
				
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	  String workingDir = System.getProperty("user.dir");
	@Test(groups={"E2E","Regression"})
	public void AdvancedPacakgesWorkflow(){
		openBrowser();
		login();
		DataSet = "TS_073";
		AutomationID = "AdvancedPacakgesWorkflow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test AdvancedPacakgesWorkflow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		//Register user with basic, patient info, sponsor info, pay info, visit info 
		//and order items(lab, radiology and service) and register
		Registration OPReg = new Registration(executeStep, verifications);
        Upload upload = new Upload(executeStep, verifications);
    	OPReg.RegisterPatientGenericDetails();
    	OPReg.GovtIDDetailsCollapsedPanel();
    	OPReg.setBillType();
    	OPReg.RegisterPatientInsurance("OP");
		OPReg.visitInformationDetails();
		upload.upload("//input[@id='primary_insurance_doc_content_bytea1' and @type='file']");
		OPReg.storeDetails();
		
		//click on Order link in bill and order OP/Diag package and save the order screen
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");		
		PatientBill patientBill = new PatientBill(executeStep, verifications);	
		patientBill.viewEditMenuClick();
		
		executeStep.performAction(SeleniumActions.Click, "","BillPageOrderLink");
	    verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",false);
		
		Order order = new Order(executeStep, verifications);
		order.addOrderItem("OrderItem1","OrdersPage");
		order.saveOrder(false);

		
		//Go to Advance packages module>Patient Packages screen>Search patient with mr_no/date.
		//3.Observe the Conduction Status of package before conducting the tests in Patient Packages screen
	    //Verify conduction status-- progress
		navigation.navigateTo(driver, "PatientPackageLink", "PatientPackagePage");
		PatientPackages patientPakgs = new PatientPackages(executeStep, verifications);
		patientPakgs.searchPatientPackages();
		patientPakgs.packageConductionStatus();
	
		
		//4.Click on Patient Package Details menu option of the package and observe
		//the details in 'Patient Package Details' screen
        //Verify test on pkg
		patientPakgs.navigateToPatientPackageDetailsScreen();
		
		
		// 5.Go to Laboratory Pending samples screen, select the test and click on collect menu option. Select the samples by ticking the check boxes and click on Save.
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
		
		//6.Go to laboratory reports screen > select the test and click on View/Edit, enter values (1,2,3,4,etc) complete,validate and Signoff.
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
		
		
		//7.Go to radiology reports > Select the test and click on View/Edit, 
		//choose a template then edit details and save. Mark complete,validate and signoff.
		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
	
		//8.Go to Pending Services List, select the service and click on edit menu option, mark completed and save.
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
						
        
		//9.Go to Advance packages module>Patient Packages screen>Observe the Conduction Status of package after conducting the test
		//Status --- done
		navigation.navigateTo(driver, "PatientPackageLink", "PatientPackagePage");
		patientPakgs = new PatientPackages(executeStep, verifications);
		patientPakgs.searchPatientPackages();
			
		
		//10.Observe the menu bar, click on HandoverTo Patient in menu bar & click on save in handover screen.
        //left click on ppatieny pakg list -- HandoverTo Patien -- test data --- Han over To: Test
		navigation.navigateTo(driver, "PatientPackageLink", "PatientPackagePage");
		patientPakgs = new PatientPackages(executeStep, verifications);
		patientPakgs.searchPatientPackages();
		patientPakgs.packageConductionStatus();
		patientPakgs.navigateToHandOverToPatient();
		
		///11.Now navigate to conducted Packages screen and observe the records.
		navigation.navigateTo(driver, "ConductedPackagesLink", "ConductedPackagesPage");
		ConductionPackages conductionPakgs = new ConductionPackages(executeStep, verifications);
		conductionPakgs.searchConductionPackages();
		conductionPakgs.verifyRecords();

        //12.Go to submission batch screen and  create submission batch by providing required 
		//information like Insurance company,tpa,plan type and plan and then by selecting 
		//Center/Account Group as Hospital.Also provide registeration from and to dates.Say created submission id is IS0001.
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubNewClaimsSubmissionLink");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimsSubNewBatchSubmissionPage",false);
		
		executeStep.performAction(SeleniumActions.Select, "PrimaryInsuranceCo","ClaimsSubInsuranceCoName");
		verifications.verify(SeleniumVerifications.Selected, "PrimaryInsuranceCo","ClaimsSubInsuranceCoName",true);
		
		executeStep.performAction(SeleniumActions.Select, "PrimarySponsorName","ClaimsSubTPAName");
		verifications.verify(SeleniumVerifications.Selected, "PrimarySponsorName","ClaimsSubTPAName",true);
		
		executeStep.performAction(SeleniumActions.Select, "InsurancePlanType","ClaimsSubNetworkPlanType");
		verifications.verify(SeleniumVerifications.Selected, "InsurancePlanType","ClaimsSubNetworkPlanType",true);
		
		executeStep.performAction(SeleniumActions.Select, "InsurancePlanName","ClaimsSubPlanName");
		verifications.verify(SeleniumVerifications.Selected, "InsurancePlanName","ClaimsSubPlanName",true);
		
		executeStep.performAction(SeleniumActions.Enter, "InsuranceValidityStartDate","ClaimsSubRegistrationDateFrom");
		verifications.verify(SeleniumVerifications.Entered, "InsuranceValidityStartDate","ClaimsSubRegistrationDateFrom",true);

		executeStep.performAction(SeleniumActions.Enter, "InsuranceValidityEndDate","ClaimsSubRegistrationDateTo");
		verifications.verify(SeleniumVerifications.Entered, "InsuranceValidityEndDate","ClaimsSubRegistrationDateTo",true);
		
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubSubmitBtn");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimsSubNewBatchSubmissionPage",false);
	
		
		
		//13.Now go to Claims Submission List dashboard and click on the generated submission id IS0001 and then on 'Download Dcouments' link
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","GenerateDownloadDocuments",true);
		executeStep.performAction(SeleniumActions.Click, "","GenerateDownloadDocuments");
		 File file = new File(workingDir + "\\Submission_IS000002_Documents.zip");
		 
		  if(file.exists()){
			  System.out.println("File downloaded");
			  file.delete();
		  }else{
			  System.out.println("File not found!");
			  
		  }
	   
		 
		  testCaseStatus="PASS";
		  System.out.println("Advanced package workflow");
	  

}
	
	
	@AfterTest
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, DataSet, "Advanced package workflow", null, "", "", testCaseStatus);
		driver.close();
	}
	
	
}
