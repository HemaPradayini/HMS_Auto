package e2e;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
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
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.Order;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientIssue;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.Registration;
import reusableFunctions.Sales;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.Triage;
import reusableFunctions.Upload;
import reusableFunctions.XMLConverter;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class INSSubmissionBatchCreationWhenAllCentersSameCompanyNameIsNFromSpecificCenter {
	
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
		AutomationID = "Login - INSSubmissionBatchCreationWhenAllCentersSameCompanyNameIsNFromSpecificCenter";
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
		AutomationID = "Login - INSSubmissionBatchCreationWhenAllCentersSameCompanyNameIsNFromSpecificCenter";
		DataSet = "TS_082";
				
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
		
	}
	  @Test(groups={"E2E","Regression"})
	  public void InsuranceBatchCreation(){
		    openBrowser();
			login();
			DataSet = "TS_082";
			AutomationID = "INSSubmissionBatchCreationWhenAllCentersSameCompanyNameIsNFromSpecificCenter";
			List<String> lineItemIDs = null;
			System.out.println("Inside Test INSSubmissionBatchCreationWhenAllCentersSameCompanyNameIsNFromSpecificCenter - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			//1.Go to OP Registration screen and enter all patient level and visit level information (all mandatory fields) and select bill type as Bill Now in Registration screen.
			//Also select primary sponsor check box and then select sponsor, company, network plan type and plan name
			//2(a)Click on Register&Edit button and register the patient.
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
    	    navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
            Upload upload = new Upload(executeStep, verifications);
            Registration OPReg = new Registration(executeStep, verifications);
        	OPReg.RegisterPatientGenericDetails();
        	OPReg.GovtIDDetailsCollapsedPanel();
        	OPReg.setBillType();
        	OPReg.RegisterPatientInsurance("OP");
			OPReg.visitInformationDetails();
			upload.upload("//input[@id='primary_insurance_doc_content_bytea1' and @type='file']");
			String parentHandle = driver.getWindowHandle();
			OPReg.storeAndEditBill();
			
			//patient bill pay
			PatientBill patientBill = new PatientBill(executeStep, verifications);
			patientBill.setBillStatus("YES");
			executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");
			
			verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
			executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
			verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);
			executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillScreen");
			verifications.verify(SeleniumVerifications.Closes, "","PatientBillScreen",false);

				
			//2(b)Go to Out Patient list screen and enter the mrno and then serach button. Once the record is listed click on Consult link
			navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
			OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
			OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
			
			//2(c)Enter all required parameters in consultation/ Triage/ IA/ Generic forms Screens.
			//2(d).Prescribe all types of items like medicines(serial and batch items having package size 1 and 10) ,
			//DrugClaimable item, DrugNonClaimable item by giving quantity as 5, Lab, rad, services, diag packages and save the consultation screen.
			
			
			ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
			consultMgmt.saveConsultationAndMgmt("Diagnosis");
			executeStep.performAction(SeleniumActions.Click, "","ConsulationMgtScreenTriageLink");
			verifications.verify(SeleniumVerifications.Appears, "","TriagePage",true);

			Triage triage = new Triage(executeStep, verifications);
			triage.saveTriage();

			OpenGenericForm opGenericForm = new OpenGenericForm(executeStep, verifications);
			opGenericForm.openGenericForm();
			
			navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");	
			OPSearch.searchOPList("OPListScreenIntialAssessmentLink", "InitialAssessmentPage");

			executeStep.performAction(SeleniumActions.Click, "","InitialAssessmentSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "","InitialAssessmentPage",true);

			//3.Click on Order link in bill and then user will be alerted with the Pending Prescription. Click on Ok of the prescription such that all items will be auto filled in order scree.Observe the patient amt for all the orderd items in order screen before saving. 
			//Save the order.Observe the item level parameters in bill for all the ordered items.
			
			Order order = new Order(executeStep, verifications);            // added by me now
			navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
			order = new Order(executeStep, verifications);
			order.searchOrder(false,false);
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
			executeStep.performAction(SeleniumActions.Dismiss, "","Framework");
			executeStep.performAction(SeleniumActions.Select, "OrderPageBillNoChoice", "OrdersScreenBillNumberDropDown");
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);	
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			verifications.verify(SeleniumVerifications.Selected, "OrderPageBillNoChoice", "OrdersScreenBillNumberDropDown", false);
			upload.upload("//input[@name='ad_test_file_upload[0]']");
			executeStep.performAction(SeleniumActions.Click, "","OrdersScreenSave");
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			System.out.println("Order Screen Saved");	
			
			
			//to pay bill
			navigation.navigateTo(driver, "BillsLink", "BillListPage");
			patientBill = new PatientBill(executeStep, verifications);		
			patientBill.settleBills("YES", "UNPAID", "OP");
			
			//4.Conduct all the ordered tests by providing conducting doctor
			//4a.Go to Laboratory Pending samples screen, select the test and click on collect menu option. Select the samples by ticking the check boxes and click on Save.
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
			//executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
			//verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
			
			//4b.Go to laboratory reports screen > Click on patient name  and then on Cancel test.
			navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
			MRNoSearch search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("LaboratoryReportsPageMRNo","LaboratoryReportsPageMRList","LaboratoryReportsPageSearchButton","LaboratoryReportsPage");
			verifications.verify(SeleniumVerifications.Appears, "","LaboratoryReportsPagePatientList",false);
			
			cancelRadTest();
			
			navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
			search.searchMRNo("LaboratoryReportsPageMRNo","LaboratoryReportsPageMRList","LaboratoryReportsPageSearchButton","LaboratoryReportsPage");
			verifications.verify(SeleniumVerifications.Appears, "","LaboratoryReportsPagePatientList",false);
			
			cancelRadTest();
			
			
			
			//4d.Go to radiology reports > Click on patient name and then on cancel test.
			navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
			MRNoSearch MrnoSearch=new MRNoSearch(executeStep,verifications);
			MrnoSearch.searchMRNo("RadiologyReportsPageMRNo","RadiologyReportsPageMRnoSelectList","RadiologyReportsPageSearchButton","RadiologyReportsPage");
			verifications.verify(SeleniumVerifications.Appears, "","RadiologyReportsPageResultTable",false);
			
			cancelRadiologyTest();
			
			navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
			MrnoSearch.searchMRNo("RadiologyReportsPageMRNo","RadiologyReportsPageMRnoSelectList","RadiologyReportsPageSearchButton","RadiologyReportsPage");
			verifications.verify(SeleniumVerifications.Appears, "","RadiologyReportsPageResultTable",false);
			cancelRadiologyTest();
			
			
			//4f.Go to Pending Services List, select the service and click on edit, and observe the data which are already filled and  enter the data in all the section and mark completed and save.
			navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
			ServicePending servicePending = new ServicePending(executeStep, verifications);
			servicePending.conductPendingServices();
			
			//4g. Go to Patient/Visit EMR and observe.
			navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
			MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
			mrnoSearch.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
			verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
	
			//5.Now go to sales screen such that defaulted store is 'Pharmacy-DHA' and enter the MR No, wait for the patient details to auto fill,
			//select bill type as Bill Now Insurance. Observe that prescribed medicines in step 2(d) are auto-filled.
			//6.Observe the item level values in sales screen and then complete the sales
			
			navigation.navigateTo(driver, "SalesLink", "SalesPage");
			Sales sales = new Sales(executeStep, verifications);
			sales.searchMRNo();
			executeStep.performAction(SeleniumActions.Click, "","SalesPagePayAndPrint");
			sales.closePrescriptionTab();	
			verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
			executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
			verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);
			executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
			verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);
			executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
			verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);
			
			//create new bill
			navigation.navigateTo(driver, "NewBillLink", "NewBillPage");
		//	MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
			//PatientBill patientBill = new PatientBill(executeStep, verifications);
			patientBill.createBill();
			
			//7.In bill, click on Issues link, once patient details are auto filled click on plus icon and 
			//add some medicines with qty as 4 each .Observe the sponsor and patient amt in issues screen.
			executeStep.performAction(SeleniumActions.Click, "","IssueLink");
			verifications.verify(SeleniumVerifications.Appears, "","PatientIssuePage",false);
			
			PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
			patientIssue.addPatientIssueItem("ItemIssue");		
			patientIssue.saveIssue();
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			
			//8.Do patient amount settlement in the hospital bill and change the bill status as finalized
			navigation.navigateTo(driver, "BillsLink", "BillListPage");
			 patientBill = new PatientBill(executeStep, verifications);		
			patientBill.settleBills("YES", "UNPAID", "OP");
			
			//Now go to Codification screen under Medical Records module, enter the mrno and then after visit id is auto filled click on Find button
			//Enter allpatientBill mandatory information in codification screen i.e diagnosis codes,counter start,encounter end,code and code type at each item level in codification screen.Then change the codification status as Completed.
			navigation.navigateTo(driver, "CodificationLink", "EditCodesScreen");
			Codification codification=new Codification(executeStep, verifications);
			codification.saveEncounterCodes(false);
			codification.EditCodesFinalizeAllCheckBox();
			
			executeStep.performAction(SeleniumActions.Select, "CodificationStatus","EditCodesCodificationStatus");
			verifications.verify(SeleniumVerifications.Appears, "","EditCodesScreen",false);
			
			codification.storeCodes();
			
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");   
			
		
			navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
			ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
			String submissionBatchID = claimsSubmission.submitClaims("InsuranceCenterAccGrp",true,false);
		
			//--------------------------------------------------------------------------------------
			//11.Go to submission batch screen and  create submission batch by providing required information 
			//i.e insurance company,plan type,tpa and plan also give Regsitration from and to date.
            //Select center/account group as DHA-(DHA)(center-1). Such that created submisison batch is IS0001
		
			//13.Go to Claims Submission List dashboard and click on the generated submission id's(one after the other) and then on E-claim menu option to generate the claim xml for hospital and pharmacy account groups
			//14.Once clicking on e-claim,xml's will be generated which should be saved.
			navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
			executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
			verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
			executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
			//verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
			//ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
	        String downloadedFile = claimsSubmission.getInsuranceDetails(DataSet, "- Center Name -Center1");
			
			String workingDir = System.getProperty("user.dir");
	        String filepath = workingDir;
	       // Path source = Paths.get(workingDir+"\\DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL*.XML-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Account Group -Hospital.XML");
	        Path source = Paths.get(workingDir+"\\"+downloadedFile+".XML");
	        CommonUtilities.delay(3000);
	        try {
	        	
				Files.move(source, source.resolveSibling("Hospital_TS_082.XML"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	    
	       //.Once clicking on e-claim, an xml will be generated which should be saved.Save as hospital.xml and pharmacy.xml 
	        //to differentiate. Also mark the submission batch id's as sent.Open the xml's with gedit and generate the remittance 
	        //file with full payment amount i.e Net=Payment amt at ecah item level.(Refer the remittance file format to generate the  xml)
	        
	        XMLConverter xml = new XMLConverter(executeStep, verifications);
	       // String submissionBatchID = "IS0000028";
	        xml.xml2XML("Full","Hospital_TS_082.XML","HAAD",submissionBatchID);
			//ClaimsSubmission.xml2XML("DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Center Name -Center1.XML");
			
	        
	        //.Once the remittance file is created go to 'Remittance Advice Upload' screen under Insurance module and select Insurance Company as 'Company1', Sposnor as 'TPA1',Center/Account Group as Hospital,
	        //Received Date as current date and then click on Browse button and upload the created remittance file for hospital and then click on Submit.
	        executeStep.performAction(SeleniumActions.Click, "","PayeeSearchResult");
			executeStep.performAction(SeleniumActions.Click, "","ClaimSentLinkText");
			verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
	        
			//.Once the remittance file is created go to 'Remittance Advice Upload' screen under Insurance module and select Insurance Company as 'Company1', Sposnor as 'TPA1',Center/Account Group as Hospital
			//,Received Date as current date and then click on Browse button and upload the created remittance file for hospital and then click on Submit.
	        navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
        
	        claimsSubmission.remittanceUpload("InsuranceCenterAccGrp", submissionBatchID);
			
		
		
   //-----------------------------------------------------------
	        
	        navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
	      //  ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
			String submissionBatchID2 = claimsSubmission.submitClaims("InsuranceCenterAccGrpII",true,false);
			
	        navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
			executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
			verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
			executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
			
			
			String downloadedFile2 = claimsSubmission.getInsuranceDetails(DataSet, "- Account Group -Pharmacy-Haad");
			//String workingDir = System.getProperty("user.dir");
	       // String filepath = workingDir;
	        Path fileSource = Paths.get(workingDir+"\\"+downloadedFile2+".XML");
	        CommonUtilities.delay(3000);
	        try {
	        	
				Files.move(fileSource, fileSource.resolveSibling("Pharmacy_TS_082.XML"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      //  XMLConverter xml = new XMLConverter(executeStep, verifications);
	        xml.xml2XML("Full","Pharmacy_TS_082.XML","HAAD",submissionBatchID2);
			
	        
	        //Once the remittance file is created go to 'Remittance Advice Upload' screen under Insurance module and select Insurance Company as 'Company1', Sposnor as 'TPA1',Center/Account Group as Hospital,
	        //Received Date as current date and then click on Browse button and upload the created remittance file for hospital and then click on Submit.
	        
	        executeStep.performAction(SeleniumActions.Click, "","PayeeSearchResult");
			executeStep.performAction(SeleniumActions.Click, "","ClaimSentLinkText");
			verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
	        
			//Once the remittance file is created go to 'Remittance Advice Upload' screen under Insurance module and select Insurance Company as 'Company1', Sposnor as 'TPA1',Center/Account Group as Hospital
			//,Received Date as current date and then click on Browse button and upload the created remittance file for hospital and then click on Submit.
	        navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
	        claimsSubmission.remittanceUpload("InsuranceCenterAccGrpII", submissionBatchID2);
	        
	       // 17. Now navigate to Claim Reconciliation screen under Insurance module and enter the submission id's,set 'All' for all the available filters and click on Serach.
	       // 18.Once the respective claim is filtered, click on it and then on 'Claim Bill' option
	        //19.Observe the Received amount and claim status at each item level also at claim level. Observe the claim status and bill status in bill also.
	        
	        navigation.navigateTo(driver, "ClaimReconciliationLink" , "ClaimReconciliationPage");
	        CommonUtilities.delay(2000);
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

			
		    
			System.out.println("INSSubmissionBatchCreation Test case pass");
			testCaseStatus="PASS";
			System.out.println("INSSubmissionBatchCreation");
			
	  }
	  
	  public void cancelRadiologyTest(){
		  
		  executeStep.performAction(SeleniumActions.Click, "","RadiologyReportsPageResultTable");
		    verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestCancelOption",false);
		    
		    executeStep.performAction(SeleniumActions.Click, "","LabPendingTestCancelOption");
			verifications.verify(SeleniumVerifications.Appears, "","CancelTestsPage",false);
			
			//4e.In Cancel test screen > Select cancel with/without refund and remarks.Click on Save.
			executeStep.performAction(SeleniumActions.Click, "","CancelDropDown");
			verifications.verify(SeleniumVerifications.Appears, "","CancelDropDownOptionWithoutRefund",false);
			
			executeStep.performAction(SeleniumActions.Click, "","CancelDropDownOptionWithoutRefund");
			verifications.verify(SeleniumVerifications.Selected, "CancelDropDownOption","CancelDropDownOptionWithoutRefund",false);
			
			executeStep.performAction(SeleniumActions.Click, "","ConfirmCancelButton");
			verifications.verify(SeleniumVerifications.Appears, "","CancelledMsg",false);
		  
	  }
	  
	  public void cancelRadTest(){
		  executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsPagePatientList");
			
			verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestCancelOption",false);

			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestCancelOption");
			verifications.verify(SeleniumVerifications.Appears, "","CancelTestsPage",false);

			//4c.In Cancel test screen > Select cancel with/without refund and remarks.Click on Save.
			executeStep.performAction(SeleniumActions.Click, "","CancelDropDown");
			verifications.verify(SeleniumVerifications.Appears, "","CancelDropDownOptionWithoutRefund",false);
			
			executeStep.performAction(SeleniumActions.Click, "","CancelDropDownOptionWithoutRefund");
			verifications.verify(SeleniumVerifications.Selected, "CancelDropDownOption","CancelDropDownOptionWithoutRefund",false);
			
			executeStep.performAction(SeleniumActions.Click, "","ConfirmCancelButton");
			verifications.verify(SeleniumVerifications.Appears, "","CancelledMsg",false);
	  }
	  
	  

		@AfterTest
		public void closeBrowser(){
			reporter.UpdateTestCaseReport(AutomationID, DataSet, "INSSubmissionBatchCreation", null, "", "", testCaseStatus);
			driver.close();
		}
	  
	  
}
