/**
 * 
 */
package e2e;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
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
import reusableFunctions.PatientBillOSP;
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
import reusableFunctions.Upload;
import reusableFunctions.XMLConverter;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author Reena
 *
 */
public class INSSubmissionBatchCreationWhenAllCentersSameCompanyNameIsNFromDefaultCenter {
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
		DataSet = "TS_083";
		AutomationID = "Login - INSSubmissionBatchCreationWhenAllCentersSameCompanyNameIsNFromDefaultCenter";
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
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void INSSubmissionBatchCreationWhenAllCentersSameCompanyNameIsNFromDefaultCenter(){
		openBrowser();
		login();
		DataSet = "TS_083";
		AutomationID = "INSSubmissionBatchCreationWhenAllCentersSameCompanyNameIsNFromDefaultCenter";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test INSSubmissionBatchCreationWhenAllCentersSameCompanyNameIsNFromDefaultCenter - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

	   SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);	
	  navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		
		
	//1.Go to OP Registration screen and enter all patient level and visit level information (all mandatory fields) and
		//select bill type as Bill Now in Registration screen.
		//Also select primary sponsor check box and then select sponsor, company, network plan type and plan name
		
		Registration OPReg = new Registration(executeStep, verifications);
	    Upload uploadfile=new Upload(executeStep, verifications); 
		OPReg.RegisterPatientGenericDetails();
	    OPReg.GovtIDDetailsCollapsedPanel();
		OPReg.setBillType();
		OPReg.RegisterPatientInsurance("OP");
		String registrationupload="//input[@id='primary_insurance_doc_content_bytea1' and @type='file']";
		uploadfile.upload(registrationupload);
		OPReg.visitInformationDetails();
		
		
		//2(a)Click on Register&Edit button and register the patient.
		OPReg.storeAndEditBill();
		
		
		
		//2(b)Go to Out Patient list screen and enter the mrno and then search button. 
		//Once the record is listed click on Consult link
	   //2(c)Enter all required parameters in consultation/ Triage/ IA/ Generic forms Screens.
	  //2(d).Prescribe all types of items like medicines(serial and batch items having package size 1 and 10) 
	  //DrugClaimable item, DrugNonClaimable item by giving quantity as 5, Lab, rad, services, diag packages
	   //and save the consultation screen.
		
				navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
				OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
				OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
					
				ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
				consultMgmt.saveConsultationAndMgmt("Diagnosis");

		
	//	3.Click on Order link in bill and then user will be alerted with the Pending Prescription. 
	//	Click on Ok of the prescription such that all items will be auto filled in order screen 
	//	pop up will come to upload document for a Laboratory test , upload document and 
	//	click on save save.Observe the patient amt for all the ordered items in order screen before saving.
	//	 Save the order

		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
 //	Upload uploadfile=new Upload(executeStep, verifications); 
		String filexpath ="//input[@name='ad_test_file_upload[0]']";
		uploadfile.upload(filexpath);
		order.saveOrder(false);
			
		//4.Conduct all the ordered tests by providing conducting doctor

		//	collect sample

						
			navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
	//		Order order = new Order(executeStep, verifications);
			order.searchOrder(false,false);		
			
		   executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
		   verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
		
		   CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		   collectSamples.collectSamplesAndSave();
		 
		   executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		   verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
	
		   executeStep.performAction(SeleniumActions.CloseTab, "","OrdersPage");
		   verifications.verify(SeleniumVerifications.Closes, "","OrdersPage",false);
		   
			//4b. Go to laboratory reports screen > select the test and click on View/Edit, 
			// enter values (1,2,3,4,etc) complete,validate and Signoff.
	
	 
			
	    navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
	    MRNoSearch searchmrno=new MRNoSearch(executeStep,verifications);
	    searchmrno.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.saveConductionResults();
		executeStep.performAction(SeleniumActions.Click, "","TestConductionSignOffArrow");
		testConduction.signOff();
				
			//	4c.Mark test for reconduction from Signoff screen by clicking on Test name and selecting Reconduction.
			//	4d.In reconduction screen,select with existing/new sample and enter remarks and check the checkbox.Hit on Save
		
				
		navigation.navigateTo(driver, "SignedOffReportLink", "SignedOffReportListPage");
		//TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.reconductSignedoffLabTest();
		
			
	//	4e.If with existing sample,Follow steps 4b. 
		
		    navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		// MRNoSearch searchmrno=new MRNoSearch(executeStep,verifications);
		    searchmrno.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
			verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
			
			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
			verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		//	TestsConduction testConduction = new TestsConduction(executeStep, verifications);
			testConduction.saveConductionResults();
			executeStep.performAction(SeleniumActions.Click, "","TestConductionSignOffArrow");
			testConduction.signOff();
	
								
			//4g. Go to radiology reports > Select the test and click on View/Edit, choose a 
			//template then edit details and save. Mark complete,validate and signoff.
				
			navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
			RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
			radiologyPendingTests.radiologyPending(true);
				
								
    //  4h.Mark test for reconduction from Signoff screen by clicking on Test name and selecting Reconduction.
  //   4i.In reconduction screen, enter remarks and check the checkbox.Hit on Save

				
				navigation.navigateTo(driver, "RadiologySignedoffReportLink", "SignedOffReportListPage");
				RadiologyPendingTests testReConduction = new RadiologyPendingTests(executeStep, verifications);
				testReConduction.reconductSignedoffRadiologyTest();
				
				
			//	4j.Follow step 4g.
				navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
			//	RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
				radiologyPendingTests.radiologyPending(true);
				
				
				
		
				//4k.Go to Pending Services List, select the service and click on edit 
				//and observe the data which are already filled 
				//and  enter the data in all the section and mark completed and save.
				
				navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
				ServicePending servicePending = new ServicePending(executeStep, verifications);
				servicePending.conductPendingServices();
			
				
			//4.i. Go to Patient &Visit EMR and observe documents..
					
			navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
			MRNoSearch search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
			verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		
			//5.Now go to sales screen such that defaulted store is 'Pharmacy-DHA' and
			//enter the MR No, wait for the patient details to auto fill, select bill type as Bill Now Insurance. 
			//Observe that prescribed medicines in step 2(d) are auto-filled.
			
			navigation.navigateTo(driver, "SalesLink", "SalesPage");
			Sales sales = new Sales(executeStep, verifications);
			sales.searchMRNo();
			sales.payAndPrint(true);
							
				
				//7.In bill, click on Issues link, once patient details are auto filled click on plus icon 
				//and add some items. 
				//Observe the pri sponsor,sec sponsor and patient amt in issues screen.
				//Complete the issues and then again observe the pri sponsor,sec sponsor and patient 
				//amt in hospital bill for the inventory items
				
				
				navigation.navigateTo(driver, "BillsLink", "BillListPage");
     			PatientBill patientBill = new PatientBill(executeStep, verifications);
     			MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
				patientBill.advancedSearch(mrnoSearch, "HOSPITAL");
				
				
			//	mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");

			//below is introduced to beat the tool tip menu
						
						executeStep.performAction(SeleniumActions.Click, "","BillListPatientBills");
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
						
						}
					
						verifications.verify(SeleniumVerifications.Appears, "","BillListIssueMenu",false);
						//below is introduced to beat the tool tip menu
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
						
						}
						executeStep.performAction(SeleniumActions.Click, "","BillListIssueMenu");
						verifications.verify(SeleniumVerifications.Appears, "","PatientIssuePage",false);
						
						PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
						patientIssue.addPatientIssueItem("IssuePatientItem");
						patientIssue.saveIssue();
				
						
						//8.Do patient amount settlement in the hospital bill and change the bill status as finalized
						
						  navigation.navigateTo(driver, "BillsLink", "BillListPage");			
					//		PatientBill patientBill = new PatientBill(executeStep, verifications);		
							patientBill.settleBills("YES", "UNPAID", "OP");
					
						
									
			//9.Now go to Codification screen under Medical Records module, enter the mrno and 
			//then after visit id is auto filled click on Find button
			// 10.Enter all mandatory information in codification screen i.e diagnosis codes,
			// counter start,encounter end,code and code type at each item level in codification 
			//screen.Then change the codification status as Completed.
												
				navigation.navigateTo(driver, "CodificationLink", "EditCodesScreen");
				Codification codification=new Codification(executeStep, verifications);
				codification.saveCodes(true);
				verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
				executeStep.performAction(SeleniumActions.Accept, "","Framework");
			//11(a)Logout and re-login as InstaAdmin
				
				 Login OPWorkFlowLogin = new Login(executeStep,verifications);
		         OPWorkFlowLogin.logOut();
		      
			       AutomationID = "Login - INSSubmissionBatchCreationWhenAllCentersSameCompanyNameIsNFromDefaultCenter";
			       DataSet = "TS_083b";
			       login();
		       
	  // 11(b)Go to submission batch screen and  create submission batch by providing required information 
	   //i.e insurance company,plan type,tpa and plan also give Regsitration from and to date. 
	  //Select center/account group as haad-(haad). Such that created submisison batch is IS0001 
	  //12.Repeat step11(b) with center/account group as Pharmacy-DHA such that create submission batch is IS0002
   
	        navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
			ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
			String submissionBatchID=claimsSubmission.submitClaims("InsuranceCenterAccGrp",true,false);
		//	claimsSubmission.submitClaims("InsuranceCenterAccGrpII",true);
			
					
		//13.Go to Claims Submission List dashboard and click on the generated submission id's(one after the other)
		// and then on E-claim menu option to generate the claim xml for hospital and pharmacy account groups
				
	
				
	navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
	executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
	verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
	executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
	
	
	//14.Once clicking on e-claim,xml's will be generated which should be saved.Save as hospital.xml and pharmacy.xml to differentiate.
	// Also mark the submission batch id's as sent.Open the xml's with gedit and 
	//generate the remittance file with full payment amount i.e Net=Payment amt at ecah item level.
	//(Refer the remittance file format to generate the remittance xml)
	
	

	
	String downloadedFile = claimsSubmission.getInsuranceDetails(DataSet, "- Center Name -Center1");



	String workingDir = System.getProperty("user.dir");
    String filepath = workingDir;
    
   
   // Path source = Paths.get(workingDir+"\\DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL*.XML-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Account Group -Hospital.XML");
    Path source = Paths.get(workingDir+"\\"+downloadedFile+".XML");
    CommonUtilities.delay(3000);
    
    
    try {
    	
		Files.move(source, source.resolveSibling("Hospital_TS_083.XML"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   //.Once clicking on e-claim, an xml will be generated which should be saved.Save as hospital.xml and pharmacy.xml 
    //to differentiate. Also mark the submission batch id's as sent.Open the xml's with gedit and generate the remittance 
    //file with full payment amount i.e Net=Payment amt at ecah item level.(Refer the remittance file format to generate the  xml)
    
    XMLConverter xml = new XMLConverter(executeStep, verifications);
    
    xml.xml2XML("Full","Hospital_TS_083.XML","HAAD",submissionBatchID);

	//ClaimsSubmission.xml2XML("DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Center Name -Center1.XML");
	
    
    //.Once the remittance file is created go to 'Remittance Advice Upload' screen under Insurance module and select Insurance Company as 'Company1', Sposnor as 'TPA1',Center/Account Group as Hospital,
    //Received Date as current date and then click on Browse button and upload the created remittance file for hospital and then click on Submit.
	
    
    
  navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
	executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
    verifications.verify(SeleniumVerifications.Appears, "","ClaimSentLink",false);
    executeStep.performAction(SeleniumActions.Click, "","ClaimSentLink");
	verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
    

	
		
    navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
//	ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
    claimsSubmission.remittanceUpload("InsuranceCenterAccGrp", submissionBatchID);
	
	
  //Now navigate to Claim Reconciliation screen under Insurance module and enter the submission id's,set 
    //'All' for all the available filters and click on Search.

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
	

   //18.Once the respective claim is filtered, click on it and then on 'Claim Bill' option
    
	

	testCaseStatus="PASS";
	System.out.println("INSSubmissionBatchCreationWhenAllCentersSameCompanyNameIsNFromDefaultCenter-Completed");		
}


@AfterClass
public void closeBrowser(){
reporter.UpdateTestCaseReport(AutomationID, "TS_083", "INSSubmissionBatchCreationWhenAllCentersSameCompanyNameIsNFromDefaultCenter", null, "", "", testCaseStatus);
driver.close();
}
    

		
}
