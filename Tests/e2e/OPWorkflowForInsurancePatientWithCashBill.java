package e2e;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
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
import reusableFunctions.Codification;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Registration;
import reusableFunctions.Sales;
import reusableFunctions.SalesReturn;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Upload;
import reusableFunctions.XMLConverter;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;
//import Xml2Xml.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class OPWorkflowForInsurancePatientWithCashBill {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus = "Fail";
	
//	@BeforeSuite
//	public void BeforeSuite(){
	private void openBrowser(){
		AutomationID = "Login - OPWorkFlowForInsurancePatientWithCashBill";
		DataSet = "TS_009";
		//EnvironmentSetup.testScenarioId = "TS_016";
		
		System.out.println("BeforeSuite");
		AutomationID = "Login - OPWorkFlowForInsurancePatientWithCashBill";
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
	public void OPWorkFlowForInsurancePatientWithCashBill(){
		openBrowser();
		login();
		DataSet = "TS_009";
		AutomationID = "OPWorkFlowForInsurancePatientWithCashBill";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForInsurancePatientWithCashBill - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

	//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "DoctorSchedulerLink", "DoctorSchedulerPage");
		
		DoctorScheduler OPWorkFlowDocScheduler = new DoctorScheduler(executeStep,verifications);
		OPWorkFlowDocScheduler.scheduleDoctorAppointment();

		//From here its for Marking as Patient arrived. move it to another reusable
		PatientArrival OPPatientArrival = new PatientArrival(executeStep,verifications);
		OPPatientArrival.markPatientAsArrived();	
		
		//5. Enter all patient level and visit level information (all mandatory fields) and select bill type as Bill Now in Registration screen.
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.GovtIDDetailsCollapsedPanel();
		OPReg.comingFromDrScheduler();
		OPReg.setBillType();
		//6. Also select primary sponsor and then select sponsor check box and then select sponsor as TPA1, 
		//company as Company1, network plan type as Plan Type1 and plan name as Plan1.
		OPReg.RegisterPatientInsurance("OP");
		Upload upload = new Upload(executeStep, verifications);
		upload.upload("//input[@id='primary_insurance_doc_content_bytea1' and @type='file']");                                            // added on 16/3 for the changed insurance data
		//OPReg.visitInformationDetails();
		executeStep.performAction(SeleniumActions.Select, "RatePlan","OPRScreenRatePlanField");
		verifications.verify(SeleniumVerifications.Selected, "RatePlan","OPRScreenRatePlanField",false);
		//7. Click on Register&Pay or Register&Edit or Register.
		OPReg.storeDetails();
		
		//Step 8. Go to Out Patient List and try to access doctor
		//consultaion screen by clicking on Consult link before paying the bill.)
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.consultationOPListDisabledCheck("OPListScreenConsultLink");
		
		//9. Now do payment and make payment status as Paid.
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
	
		// ***************************************
		//patientBill.savePatientBillInsurancePatient("Advance");   // commented today 15/2/17
		// ***************************************
        PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.doAdvancePayment();                             // added today 15/2/17
	
		//10. Go to out patient list and click on Consult link of registered patient
		//11. Enter all required parameters in consultation Screen
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		//OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
	OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
				
		ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
		
		//12 Prescribe all types of items like medicines(serial and batch items having packet size 1 and 10) with quantity as 5,Lab,rad,services,diag packages and save the consultation screen
		//Also prescribe DrugClaimable and DrugNonClaimable User should be blocked from saving the consultation screen if diagnosis code is not entered.
		consultMgmt.saveConsultationAndMgmt("Diagnosis");
	
		//13. Go to bill order screen and observe the alert message of pending prescription
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		
		
     	//14. Click on 'OK' so that prescription with all hospital items will be loaded. Observe the patient amount in order screen 
	Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
		
//15. Save the order screen such that all prescribed Items are ordered. Verify individual item level amounts in bill also total dues
  		order.saveOrder(true);
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");  //  doadvancepayment is doing search from billlistpage
		patientBill.doAdvancePayment();                             
		
		//16.Go to Laboratory Pending samples screen, select the test and click on collect menu option.
		//Select the samples by ticking the check boxes and click on Save.
	/*	navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		order.searchOrder(false,false);
		
		
		
		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
		verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
		
	CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
	collectSamples.collectSamplesAndSave();
		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
	*/
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		  MRNoSearch SearchMr = new MRNoSearch(executeStep, verifications);
		  SearchMr.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		  
		  executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		  verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
		  
		  executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		  verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		      
		  //collect samples and save
		  CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		  collectSamples.collectSamplesAndSave();
		 // executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		//  verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
		
		//16a. Go to laboratory reports screen > 
//select the test and click on View/Edit, enter values (1,2,3,4,etc) complete,validate and Signoff.
	/*			
		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
				
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
		*/                             // commented on 14/4
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
	
	// ************************************************************************************
	// added on 21/2
		//16b. Go to Radiology reports screen, select the test and click on View/Edit menu option,
		//select a template, enter details and complete, validate and signoff.
		
		 navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
			RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
			radiologyPendingTests.radiologyPending(true);
			
			//16c.Go to Pending Services List, select the service and click on edit menu option mark completed and save.
			navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
			servicePending.conductPendingServices();
		
		
			// ***************************************************************
		
			
// step 16d) Go to Patient &Visit EMR and observe documents.
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);

	//17. Go to sales screen, enter the MR No, observe the pending prescription alert
		//and then click on OK such that all prescribed medicines get auto populated in sales screen.
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.searchMRNo();
	//18. Observe Sponsor and patient amount for each item in sales screen 
		
		//19Select Raise bill insurance in sales screen and then click on Raise Bill & Print to complete sales.(Settlement)
	sales.payAndPrint(true);
		
		//20. After doing sales observe the sponsor and patient amount in the sale bill such that patient
		//Amount should be as per the define co-pay in plan
		// this needs to be done in verification
		
		
		// check if the below step is reqd.
		// executeStep.performAction(SeleniumActions.Click, "", "SalesAddItemCloseImg"); 
		
		//executeStep.performAction(SeleniumActions.Accept, "", "Framework"); 
		
		//21. Do sales returns for partial qty(by giving quantity as 3),
		//click on Raise bill&Print to complete sales returns.
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		salesReturns.doSalesReturn("SalesReturns","OP");
		
		
		
		//take the xpath and navigation step
		
		
		
		navigation.navigateTo(driver, "NewBillLink", "NewBillPage");
		patientBill.createBill();
		
		//22. Raise a patient indent by adding serial and batch items (having packet size 1 and 10) by giving quantity as 5.
		
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentSales", true);
		
		//22a. Go to Patient Indent List under Sakes and Issues, select the above rased indent and click on Issue.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		raisePatientIndent.searchPatientIndent();
		
		//22b. Click On Save in patient Issue screen to complete issues.
		raisePatientIndent.savePatientIndentIssue(true);
		
		
		//23Raise a return indent by giving quantity as 3 for the above issued items.
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");
		
		raisePatientIndent.savePatientIndent("ReturnedSalesInden", false);	
		executeStep.performAction(SeleniumActions.Accept, "","Dialog");
	
		//24a. Go to Patient Indent List under Sakes and Issues, select the above rased indent and click on Issue Returns.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		raisePatientIndent.searchPatientIndent();
	
		//24b. Click On Save in patient Issue screen to complete issues.
		raisePatientIndent.savePatientIndentIssue(false);
		executeStep.performAction(SeleniumActions.Accept, "","Dialog");
		
		                    
		//25//26
	

		navigation.navigateTo(driver, "OpenBills", "BillListPage");
		patientBill.settleBills("YES", "PAID", "OP");
	navigation.navigateTo(driver, "OpenBills", "BillListPage");
	patientBill.settleBills("YES", "UNPAID", "OP");
		
//************************************************************21March
		navigation.navigateTo(driver, "OpenBills", "BillListPage");
		  //navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
		   	// PatientBill patientBill = new PatientBill(executeStep, verifications);
		   	MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		    patientBill.advancedSearch(Search,"REFUND");
		    patientBill.viewEditMenuClick();
		    patientBill.settleBillDetails("YES", "BillRefundPaymentType");
		
		//************************************************************21March

		
	//	navigation.navigateTo(driver, "OpenBills", "BillListPage");
	//	patientBill.settleFinalizedInsBills("YES");
		
		//till 27 do it tomorrow

		//27. Now go to Codification screen under Medical Records module, enter the mrno and then after visit id is auto filled click on Find button
		navigation.navigateTo(driver, "CodificationLink", "EditCodesScreen");
		
		//28.Enter all mandatory information in codification screen i.e diagnosis codes, encounter start, encounter end code and code type at each
		//item level in codification screen.Then change the codification status as Completed.
		Codification codification=new Codification(executeStep, verifications);
		codification.saveCodes(true);		
		
		
		//29. Go to submission batch screen and  create submission batch by providing required information and then by selecting Center/Account Group as hospital.
		//Now again repeat it by selecting Center/Account Group as pharmacy. Say generated submisison id for hospital is IS0001 and for pharmacy is IS0002.
		
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		//claimsSubmission.submitClaims("InsuranceCenterAccGrp",true);
		String submissionBatchID = claimsSubmission.submitClaims("InsuranceCenterAccGrpII",true,false);
		
		//30. Go to Claims Submission List dashboard and click on both the generated submission id's and then on
		//E-claim to generate the claim xml for hospital and pharmacy.

		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
		executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
		//verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
		
		
		//ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		String downloadedFile = claimsSubmission.getInsuranceDetails(DataSet, "- Account Group -Hospital");
		
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
       //31.Once clicking on e-claim, an xml will be generated which should be saved.Save as hospital.xml and pharmacy.xml 
        //to differentiate. Also mark the submission batch id's as sent.Open the xml's with gedit and generate the remittance 
        //file with full payment amount i.e Net=Payment amt at ecah item level.(Refer the remittance file format to generate the  xml)
        
        XMLConverter xml = new XMLConverter(executeStep, verifications);
        
        xml.xml2XML("Full","Hospital.XML","HAAD",submissionBatchID);
		//ClaimsSubmission.xml2XML("DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Center Name -Center1.XML");
		
        
        //32.Once the remittance file is created go to 'Remittance Advice Upload' screen under Insurance module and select Insurance Company as 'Company1', Sposnor as 'TPA1',Center/Account Group as Hospital,
        //Received Date as current date and then click on Browse button and upload the created remittance file for hospital and then click on Submit.
		executeStep.performAction(SeleniumActions.Click, "","ClaimSentLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
        
		//32.Once the remittance file is created go to 'Remittance Advice Upload' screen under Insurance module and select Insurance Company as 'Company1', Sposnor as 'TPA1',Center/Account Group as Hospital
		//,Received Date as current date and then click on Browse button and upload the created remittance file for hospital and then click on Submit.
        navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
        
        claimsSubmission.remittanceUpload("InsuranceCenterAccGrpII", submissionBatchID);
        
        //33.Repeat 32 with Center/Account Group as Pharmacy
        
        //34. Now navigate to Claim Reconciliation screen under Insurance module and enter the submission id's,set 'All' for all the available filters and click on Serach.
        //34.Once the respective claim is filtered, click on it and then on 'Claim Bill' option
    //    navigation.navigateTo(driver, "ClaimReconciliationLink" , "ClaimPage");
    //  claimsSubmission.claimBill();
        
        
        navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
        String submissionBatchID2 = claimsSubmission.submitClaims("InsuranceCenterAccGrp",true,false);
        
        
        navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
		executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
		
		String downloadedFile2 = claimsSubmission.getInsuranceDetails(DataSet, "- Account Group -Pharmacy");
		//String workingDir = System.getProperty("user.dir");
       // String filepath = workingDir;
        Path fileSource = Paths.get(workingDir+"\\"+downloadedFile+".XML");
        CommonUtilities.delay(3000);
        try {
        	
			Files.move(fileSource, fileSource.resolveSibling("Pharmacy.XML"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        xml.xml2XML("Full","Pharmacy.XML","HAAD",submissionBatchID2);
		
        
        //32.Once the remittance file is created go to 'Remittance Advice Upload' screen under Insurance module and select Insurance Company as 'Company1', Sposnor as 'TPA1',Center/Account Group as Hospital,
        //Received Date as current date and then click on Browse button and upload the created remittance file for hospital and then click on Submit.
		executeStep.performAction(SeleniumActions.Click, "","ClaimSentLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
        
		//32.Once the remittance file is created go to 'Remittance Advice Upload' screen under Insurance module and select Insurance Company as 'Company1', Sposnor as 'TPA1',Center/Account Group as Hospital
		//,Received Date as current date and then click on Browse button and upload the created remittance file for hospital and then click on Submit.
        navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
        claimsSubmission.remittanceUpload("InsuranceCenterAccGrpII", submissionBatchID2);
        
        //34. Now navigate to Claim Reconciliation screen under Insurance module and enter the submission id's,set 'All' for all the available filters and click on Serach.
       // 34.Once the respective claim is filtered, click on it and then on 'Claim Bill' option
       // 35.Observe the Received amount and claim status at each item level also at claim level. Observe the claim status and bill status in bill also.
		
        navigation.navigateTo(driver, "ClaimReconciliationLink" , "ClaimReconciliationPage");
        executeStep.performAction(SeleniumActions.Click, "","ClaimReconciliationStatusAllCheckBox"); 

    	verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimReconciliationPage",false);
        
    	executeStep.performAction(SeleniumActions.Enter, "ClaimsBatchSubmissionId","ClaimReconciliationSubmitBatchIdField");
    	verifications.verify(SeleniumVerifications.Entered, "ClaimsBatchSubmissionId","ClaimReconciliationSubmitBatchIdField",false);
    	
    	executeStep.performAction(SeleniumActions.Click, "","ClaimReconciliationPageSearch");                   
    	verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimPageTable",false);
    	
    	executeStep.performAction(SeleniumActions.Click, "","ClaimPageTable");                   
    	verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimBillsLink",false);
    	
    	executeStep.performAction(SeleniumActions.Click, "","ClaimBillsLink");                   
    	verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimPage",false);
        testCaseStatus = "Pass";
		
		System.out.println("TS_009 - completed");
	}
		@AfterClass
		public void closeBrowser(){
			String delimiter = " :: ";
			//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
			//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
			ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OPWorkFlowForInsurancePatientWithCashBill", null, "", "", testCaseStatus);
			driver.close();
		}
		
}
