package e2e;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import genericFunctions.CommonUtilities;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.ClaimsSubmission;
import reusableFunctions.Codification;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DiagnosisDetails;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.Order;
import reusableFunctions.PatientBill;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.Registration;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Upload;
import reusableFunctions.XMLConverter;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class INSObservationPresentInHAADClaims {

	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus = "Fail";
	
//	@BeforeSuite
	//public void BeforeSuite(){
	private void openBrowser(){
		AutomationID = "Login - INSObservationPresentInHAADClaims";
		DataSet = "TS_085";
		//EnvironmentSetup.testScenarioId = "TS_016";
		
		System.out.println("BeforeSuite");
		AutomationID = "Login - INSObservationPresentInHAADClaims";
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
	public void INSObservationPresentInHAADClaims(){
		openBrowser();
		login();
		DataSet = "TS_085";
		AutomationID = "INSObservationPresentInHAADClaims";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test INSObservationPresentInHAADClaims - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
	
		//1.Login to center schema with user as 'User-HAAD' [ center 1 ] .Go to OP Registration screen and enter all patient level and visit level
		//information (all mandatory fields) and select bill type as Bill Now in 
		//Registration screen.Also select primary sponsor check box and then select sponsor, company,network plan type and plan name
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
 		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.GovtIDDetailsCollapsedPanel();
		OPReg.RegisterPatientGenericDetails();
		OPReg.setBillType();
		OPReg.RegisterPatientInsurance("OP");
		Upload upload = new Upload(executeStep, verifications);
		upload.upload("//input[@id='primary_insurance_doc_content_bytea1' and @type='file']");
		OPReg.visitInformationDetails();
		//2. Click on Register&Edit or Register.
		OPReg.storeDetails();
		
		//3.Go to patient bill and clear the patient due and bill status as paid.
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		PatientBill patientBill = new PatientBill(executeStep, verifications);
		
		//to open the bill view edit
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");		
		
		executeStep.performAction(SeleniumActions.Click, "","BillListHospitalBill");
		verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
		
		executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		
		executeStep.performAction(SeleniumActions.Select,"PaidPaymentStatus","BillPagePaymentStatus");
		verifications.verify(SeleniumVerifications.Selected, "PaidPaymentStatus","BillPagePaymentStatus",false);
		patientBill.setBillStatus("YES");
		patientBill.settleBillDetails("YES", "AdvanceBillPaymentType");
		
		
		
		// ************************************8
		//patientBill.doAdvancePayment(); 
		//*************************************
		
		
		
		//4.Go to Out Patient List and enter the mrno. Once the record is filtered click on Consult link.
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
		
		//5.Enter all required information in consultation screen also enter tobacco form information and 
		//then order a test which has result entry type as results(Lab1),order a 
		//service which requires tooth number(Service 86).
		
		ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
		consultMgmt.TobaccoUsage();
		consultMgmt.saveConsultationAndMgmt("Diagnosis");

		
		//6.Now under Billing module click on Order link and enter the mrno. Once the visit record is filerted select the record and then click on Find button.
		//Now pending prescription alert will be shown, click on Ok of the alert such that all the ordered items are auto filled in order screen.
		//Provide the tooth number for the ordered service.Save the order screen.
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
		order.saveOrder(true);
		
		//7.Go to bill and settle the patient amount.       
	
		patientBill.setBillStatus("YES");
		patientBill.settleBillDetails("YES","SettlementPaymentType"); 
		
		//8a.Go to Laboratory Pending samples screen, select the test and click on collect menu option. Select the samples by ticking the check boxes and click on Save.
/*		
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		order = new Order(executeStep, verifications);
		order.searchOrder(true,false);                                           // added by Abhishek
		
		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
		verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
	*/
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
		  executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		  verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
		  
		//8b.Go to laboratory reports screen > select the test and click on View/Edit, enter values (1,2,3,4,etc) complete,validate and Signoff.
	
		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();
	
	
		//8c.Check the Report checkbox and click on Revert Signoff option in Signoff screen.
		navigation.navigateTo(driver, "SignedOffReportLink", "SignedOffReportListPage");
        search.searchMRNo("PendingSampleAssertionsSearchMRID","PendingSampleAssertionsSearchMRIDContainer","PendingSampleAssertionsSearchButton","PendingSampleAssertionsTable");
		
		executeStep.performAction(SeleniumActions.Click, "","SignedOffReportListAlert");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSampleAssertionsTable",false);
		testConduction.revertSignOff();
		
		
		//8d.Go to Lab reports screen and mark the above test as Signoff.
		navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
		
		//MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryReportsPage");//LabPendingTests
		CommonUtilities.delay(2000);
		executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsPageResultTable");
		verifications.verify(SeleniumVerifications.Appears, "","LaboratoryReportsPageReconductMenu",false);
		//executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
		//verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		testConduction.signOff();
		
		//8e.Go to radiology reports > Select the test and click on View/Edit, choose a template then edit details and save. 
		//Mark complete,validate and signoff.
		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
		
		//8f.Check the Report checkbox and click on Revert Signoff option in Signoff screen.
		navigation.navigateTo(driver, "SignedOffReportsListLink", "SignedOffReportListPage");
		search.searchMRNo("PendingSampleAssertionsSearchMRID","PendingSampleAssertionsSearchMRIDContainer","PendingSampleAssertionsSearchButton","PendingSampleAssertionsTable");
		
		executeStep.performAction(SeleniumActions.Click, "","SignedOffReportListAlert");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSampleAssertionsTable",false);
		testConduction.revertSignOff();
		
		//8g.Go to Rad reports screen and mark the above test as Signoff.
		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		search.searchMRNo("RadiologyReportsPageMRNo","RadiologyReportsPageMRnoSelectList","RadiologyReportsPageSearchButton","RadiologyReportsPage");
        //executeStep.performAction(SeleniumActions.Click, "","RadiologyReportsPageTestCheckBox");
		
		
		executeStep.performAction(SeleniumActions.Click, "","RadiologyReportResultTable");
		verifications.verify(SeleniumVerifications.Appears, "","RadiologyReportsPageTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","RadiologyReportsPageTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		testConduction.signOff();
		
		//8h.Go to Pending Services List, select the service and click on edit, and observe the data which are already filled and
		//enter the data in all the section and mark completed and save.
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
		
		//9.Now go to Codification screen under Medical Records module, enter the mrno and then after visit id is auto filled click on Find button
		navigation.navigateTo(driver, "CodificationLink", "EditCodesScreen");
		
		
		//10.Enter all mandatory information in codification screen i.e diagnosis codes, encounter start, encounter end, code and code type at each item level in codification screen.
		//11.Observe the 'View/Edit Observations' section for the service and lab test in dialogue box by clicking on Edit Treatment Code icon
		//12.Now mark Finalize all bill check box and change the codification status as Completed.
		executeStep.performAction(SeleniumActions.Check, "","FinalizeAllBillsCheckBox");
		Codification codification=new Codification(executeStep, verifications);
		codification.saveCodes(true);
		//13.Go to submission batch screen and  create submission batch by providing required information i.e insurance company,plan type,tpa and plan also give Regsitration from and to date
		//. Select center/account group as HAAD-(HAAD). Such that created submisison batch is IS0001 
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		
		String submissionBatchID = claimsSubmission.submitClaims("InsuranceCenterAccGrp",true,false);
		
		//14.Go to Claims Submission List dashboard and click on the generated submission id and then on E-claim menu option
		//to generate the claim xml for hospital account group
		
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
		executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
		//verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
String downloadedFile = claimsSubmission.getInsuranceDetails(DataSet, "- Center Name -Center1");
		
		String workingDir = System.getProperty("user.dir");
        String filepath = workingDir;
       // Path source = Paths.get(workingDir+"\\DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL*.XML-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Account Group -Hospital.XML");
        Path source = Paths.get(workingDir+"\\"+downloadedFile+".XML");
        CommonUtilities.delay(3000);
        try {
        	
			Files.move(source, source.resolveSibling("Hospital.XML"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       //.Once clicking on e-claim, an xml will be generated which should be saved.Save as hospital.xml and pharmacy.xml 
        //to differentiate. Also mark the submission batch id's as sent.Open the xml's with gedit and generate the remittance 
        //file with full payment amount i.e Net=Payment amt at ecah item level.(Refer the remittance file format to generate the  xml)
        
        XMLConverter xml = new XMLConverter(executeStep, verifications);
        
        xml.xml2XML("Full","Hospital.XML","HAAD",submissionBatchID);
		//ClaimsSubmission.xml2XML("DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Center Name -Center1.XML");
		
        
        //.Once the remittance file is created go to 'Remittance Advice Upload' screen under Insurance module and select Insurance Company as 'Company1', Sposnor as 'TPA1',Center/Account Group as Hospital,
        //Received Date as current date and then click on Browse button and upload the created remittance file for hospital and then click on Submit.
		executeStep.performAction(SeleniumActions.Click, "","ClaimSentLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
        
		//.Once the remittance file is created go to 'Remittance Advice Upload' screen under Insurance module and select Insurance Company as 'Company1', Sposnor as 'TPA1',Center/Account Group as Hospital
		//,Received Date as current date and then click on Browse button and upload the created remittance file for hospital and then click on Submit.
        navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");

        claimsSubmission.remittanceUpload("InsuranceCenterAccGrp", submissionBatchID);
       
        //19.Observe the Received amount and claim status at each item level also at claim level. Observe the claim status and bill status in bill also.
        //added today
        navigation.navigateTo(driver, "ClaimReconciliationLink" , "ClaimReconciliationPage");
        CommonUtilities.delay(2000);
        executeStep.performAction(SeleniumActions.Click, "","ClaimReconciliationStatusAllCheckBox"); 

    	verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimReconciliationPage",false);
        
    	executeStep.performAction(SeleniumActions.Enter, "ClaimsBatchSubmissionId","ClaimReconciliationSubmitBatchIdField");
    	verifications.verify(SeleniumVerifications.Entered, "ClaimsBatchSubmissionId","ClaimReconciliationSubmitBatchIdField",false);
    	//
    	 
    	//   
    	executeStep.performAction(SeleniumActions.Click, "","ClaimReconciliationPageSearch");                   
    	verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimPageTable",false);
    	
    	executeStep.performAction(SeleniumActions.Click, "","ClaimPageTable");                   
    	verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimBillsLink",false);
    	
    	executeStep.performAction(SeleniumActions.Click, "","ClaimBillsLink");                   
    	verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimPage",false);
        testCaseStatus = "Pass";
        System.out.println("TS_085 (HAAD CLAIMS) completed Successfully");

		
	}
	
	@AfterClass
	public void closeBrowser(){
		String delimiter = " :: ";
		//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
		//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "INSObservationPresentInHAADClaims", null, "", "", testCaseStatus);
		driver.close();
	}
	

}
