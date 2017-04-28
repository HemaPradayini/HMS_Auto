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

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
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
import reusableFunctions.LabSamplesTransferManual;
import reusableFunctions.LaboratoryTestsOrders;
//import reusableFunctions.LaboratoryTestsOrders;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.Registration;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
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
import reusableFunctions.Upload;
import reusableFunctions.XMLConverter;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author Reena
 *
 */
public class INSObservationPresentInDHAClaims {
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
		DataSet = "TS_086";
		AutomationID = "INSObservationPresentInDHAClaims";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test INSObservationPresentInDHAClaims - Before Navigation");
		
		System.out.println("BeforeSuite");
		AutomationID = "Login - INSObservationPresentInDHAClaims";
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
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
	}
	
	@Test(groups={"E2E","Regression"})
	public void INSObservationPresentInDHAClaims(){
		DataSet = "TS_086";
		AutomationID = "INSObservationPresentInDHAClaims";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test INSObservationPresentInDHAClaims - Before Navigation");
		
		openBrowser();
		login();

		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);

        navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		Registration OPReg = new Registration(executeStep, verifications);
	    Upload uploadfile=new Upload(executeStep, verifications); 
		
		   
	       OPReg.RegisterPatientGenericDetails();
	       OPReg.GovtIDDetailsCollapsedPanel();
	       OPReg.setBillType();
	       OPReg.unCheckPrimarySponsor();
	       OPReg.RegisterPatientInsurance("OP");
	       String registrationupload="//input[@id='primary_insurance_doc_content_bytea1' and @type='file']";
		   uploadfile.upload(registrationupload);
	       OPReg.visitInformationDetails();
	       OPReg.storeAndEditBill();
	       
	      
	       DbFunctions db = new DbFunctions();
			db.saveMRIDVisitID("TS_086b");

	       // 3.Go to Out Patient List and enter the mrno. Once the record is filtered click on Consult link.

	        navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
			OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
			
			OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
			
            //4.Enter all required parameters in consultation/ Triage/ IA/ Generic forms Screens .
			//Enter all required information in consultation screen also enter tobacco form information and
			//	then order a service which requires tooth number ( Service tooth 86 )order a radiology test ( Radio test 86 ) .(Asked to skip)
				
			ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
			
			consultMgmt.EditCheifComplaint();
			consultMgmt.TobaccoUsage();
			consultMgmt.saveConsultationAndMgmt("Diagnosis");
			
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
	
			
			navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
   // OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
			OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
			
			executeStep.performAction(SeleniumActions.Click, "","ConsulationMgtScreenTriageLink");
			verifications.verify(SeleniumVerifications.Appears, "","TriagePage",true);

			
			Triage triage = new Triage(executeStep, verifications);
			triage.saveTriage();

			OpenGenericForm opGenericForm = new OpenGenericForm(executeStep, verifications);
			opGenericForm.openGenericForm();
			
	
			navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
	//		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
			OPSearch = new OPListSearch(executeStep, verifications);		
			OPSearch.searchOPList("OPListScreenIntialAssessmentLink", "InitialAssessmentPage");
			
			executeStep.performAction(SeleniumActions.Click, "","InitialAssessmentSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "","InitialAssessmentPage",true);

			
			
			//5.Now under Billing module click on Order link and enter the mrno.
			//Once the visit record is filtered select the record and then click on Find button.
			//Now pending prescription alert will be shown, click on Ok of the alert such that all the ordered items are auto filled in order screen.
			//Save the order screen
						
			navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
			
			Order order = new Order(executeStep, verifications);
			order.searchOrder(false,false);
			order.saveOrder(false);
		
		//	6a.Go lab orders screen and search with mr_no > order a test mapped to internal lab [ Lab test out 86 ]
			
			navigation.navigateTo(driver, "LaboratoryOrderTestsLink", "LaboratoryOrderTestsPage");
			LaboratoryTestsOrders search = new LaboratoryTestsOrders(executeStep, verifications);
    		search.searchLabOrderList();
     //		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
	//		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
			verifications.verify(SeleniumVerifications.Appears, "","LaboratoryOrderTestsPage",false);
  		    search.addItemsToLabTest();
			
				
	     navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
			
		//	Order order = new Order(executeStep, verifications);
			order.searchOrder(false,false);		
			executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
		    verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
			
			  CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
			  collectSamples.collectSamplesAndSave();
			  
			  executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
			  verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
			  executeStep.performAction(SeleniumActions.CloseTab, "","OrdersPage");
			  verifications.verify(SeleniumVerifications.Closes, "","OrdersPage",false);
		
		//6c.Go to Lab Transfer Sample Manual > Search with mr_no > Check the checkbox and click on Mark Transfer
			navigation.navigateTo(driver, "LabTransferSamplesManualLink", "LabTransferSamplesManualPage");
			
			LabSamplesTransferManual labTransfers = new LabSamplesTransferManual(executeStep, verifications);
			labTransfers.transferLabSamples();
			executeStep.performAction(SeleniumActions.CloseTab, "","LabSampleWorkSheet");
		
			System.out.println("EnvironmentSetup.strGlobalException II is :: " + EnvironmentSetup.strGlobalException.toString());
		
		//6d.Login to center 1(HAAD) where sample is transfered to (C1)
					
		
		  Login OPWorkFlowLogin = new Login(executeStep,verifications);
		  OPWorkFlowLogin.logOut();
		  	
		    AutomationID = "Login - INSObservationPresentInDHAClaims";
		    DataSet = "TS_086b";
		    
		    login();

        
    
		//6.e Go to Laboratory Receive / Split Sample screen >Search with mr_no >  Mark the sample as received.
		 
	   navigation.navigateTo(driver, "LaboratoryReceiveSamplesLink", "LaboratoryReceiveSamplesPage");
   //  LabSamplesTransferManual labTransfers = new LabSamplesTransferManual(executeStep, verifications);
	   labTransfers.receiveSamples();
							
	//6f. Go to laboratory reports screen > select the test and click on View/Edit, 
	// enter values (1,2,3,4,etc) complete,validate and Signoff.
					
		    navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		    MRNoSearch searchmrno=new MRNoSearch(executeStep,verifications);
		    searchmrno.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
			verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
			
			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
			verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
			TestsConduction testConduction = new TestsConduction(executeStep, verifications);
			testConduction.conductTests();
		
				 
				 
     //6g.Login to C2 and go to radiology reports > Select the test and click on View/Edit, 
     //choose a template then edit details and save. Mark complete,validate and signoff.
					
			// Login OPWorkFlowLogin = new Login(executeStep,verifications);
	           OPWorkFlowLogin.logOut();
	      
		       AutomationID = "Login - INSObservationPresentInDHAClaims";
		       DataSet = "TS_086";
		      
		       login();
		
				
				//6g. Go to radiology reports > Select the test and click on View/Edit, choose a 
				//template then edit details and save. Mark complete,validate and signoff.
					
								       
				   	//6.g.Go to radiology reports > Select the test and click on View/Edit, choose a template then edit details and save. Mark complete,validate and signoff.
					navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
					RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
					radiologyPendingTests.radiologyPending(true);
			       
			       
				       
					//6.h.Go to Pending Services List, select the service and click on edit menu option mark 
					// completed and save.
						
					navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
					ServicePending servicePending = new ServicePending(executeStep, verifications);
					servicePending.conductPendingServices();
					
			
		
					//7.Now go to Codification screen under Medical Records module, enter the mrno and 
					//then after visit id is auto filled click on Find button
					// 19.Enter all mandatory information in codification screen i.e diagnosis codes,
					// counter start,encounter end,code and code type at each item level in codification 
					//screen.Then change the codification status as Completed.
						
				navigation.navigateTo(driver, "CodificationLink", "EditCodesScreen");
				Codification codification=new Codification(executeStep, verifications);
			
				
				codification.saveEncounterCodes(true);
				codification.savePrimaryDiagnosisCode();
				codification.EditCodesFinalizeAllCheckBox();
		//		codification.storeCodes(); cannot use this as we need to accept a temporary pop up on 'treatment observation values not entered ,proceed anyway' for now
				executeStep.performAction(SeleniumActions.Click, "","EditCodesSave");
				verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
				executeStep.performAction(SeleniumActions.Accept, "","Framework");
				
			
	
			//	11.Go to submission batch screen and  create submission batch by providing required information
		//		i.e insurance company,plan type,tpa and plan also give Regsitration from and to date. 
		//		Select center/account group as DHA-(DHA. Such that created submisison batch is IS0001 

				navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
				ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
			
				String submissionBatchID = claimsSubmission.submitClaims("InsuranceCenterAccGrp",true,false);

	
		//	12.Go to Claims Submission List dashboard and click on the generated submission id and 
		//	then on E-claim menu option to generate the claim xml for hospital account group

			
				navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
				executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
				verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",true);
				executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
			

//	13.Once clicking on e-claim,xml will be generated which should be saved.
	//Save as hospital.xml. Also mark the submission batch id as sent.
				
				String downloadedFile = claimsSubmission.getInsuranceDetails(DataSet, "- Center Name -Center-2");
			
		      
				String workingDir = System.getProperty("user.dir");
		        String filepath = workingDir;
		       // Path source = Paths.get(workingDir+"\\DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL*.XML-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Account Group -Hospital.XML");
		        Path source = Paths.get(workingDir+"\\"+downloadedFile+".XML");
		        CommonUtilities.delay(3000);
		        
		        try {
		        	
					Files.move(source, source.resolveSibling("Hospital_TS_086.XML"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       //.Once clicking on e-claim, an xml will be generated which should be saved.Save as hospital.xml and pharmacy.xml 
		        //to differentiate. Also mark the submission batch id's as sent.Open the xml's with gedit and generate the remittance 
		        //file with full payment amount i.e Net=Payment amt at each item level.(Refer the remittance file format to generate the  xml)
		        
		        XMLConverter xml = new XMLConverter(executeStep, verifications);
		        
		        xml.xml2XML("Full","Hospital_TS_086.XML","DHA",submissionBatchID);
				//ClaimsSubmission.xml2XML("DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Center Name -Center1.XML");
				
		        
		        //.Once the remittance file is created go to 'Remittance Advice Upload' screen under Insurance module and select Insurance Company as 'Company1', Sposnor as 'TPA1',Center/Account Group as Hospital,
		        //Received Date as current date and then click on Browse button and upload the created remittance file for hospital and then click on Submit.
		     
		        navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		    	executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		        verifications.verify(SeleniumVerifications.Appears, "","ClaimSentLink",false);
		        executeStep.performAction(SeleniumActions.Click, "","ClaimSentLink");
		    	verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
		        
				//.Once the remittance file is created go to 'Remittance Advice Upload' screen under Insurance module and select Insurance Company as 'Company1', Sposnor as 'TPA1',Center/Account Group as Hospital
				//,Received Date as current date and then click on Browse button and upload the created remittance file for hospital and then click on Submit.
				
				
		       
		        navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
		//     	ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);//for partial run
		        claimsSubmission.remittanceUpload("InsuranceCenterAccGrp", submissionBatchID);

		       //Observe the Received amount and claim status at each item level also at claim level. 
		      //Observe the claim status and bill status in bill also.
		        
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
		        
		        
		        
		        testCaseStatus="PASS";
				System.out.println("INSObservationPresentInDHAClaims-Completed");		
		}
		
		
		@AfterClass
		public void closeBrowser(){
			reporter.UpdateTestCaseReport(AutomationID, "TS_086", "INSObservationPresentInDHAClaims", null, "", "", testCaseStatus);
			driver.close();
		}
				
				
			
	
	
	}	
	


