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
import reusableFunctions.XMLConverter;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author C N Alamelu
 *
 */
public class IPWFForInsurancePatientWithResubmission {
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
		AutomationID = "Login - IPWFForInsurancePatientWithResubmission";
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
		AutomationID = "IPWFForInsurancePatientWithResubmission";
		DataSet = "TS_031";
		//EnvironmentSetup.testScenarioId = "TS_031";
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void IPWFForInsurancePatientWithResubmission(){
		openBrowser();
		login();
		DataSet = "TS_031";
		AutomationID = "IPWFForInsurancePatientWithResubmission";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test IPWorkflowForInsurancePatientWithResubmission - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);	
	
		navigation.navigateTo(driver, "IPRegistrationLink", "IPRegistrationPage");
		
		
		//1. Go to IP Registration screen enter all mandatory information also select primary 
		//sponsor and then select sponsor, company, plan type and plan as Plan. Select bill type 
		//as Bill Later and select bed, ward, bed number, admitting doctor and duty doctor.
		//2. Register the patient on clicking Register 

		Registration IPReg = new Registration(executeStep, verifications);
		IPReg.RegisterPatientInsuranceIP();
		
	
		// click on Proceed To Bill link in success page
		// 3. Observe the item level sponsor and patient amount for all bed charges in bill 
		//also observe the total sposnor and patient due in bill
		 		
		executeStep.performAction(SeleniumActions.Click, "","ProceedToBillingLink");
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
					
		
		//4. Click on Order link in bill and order all types of billable items in bill from order screen.
		//Save the order screen and come back to bill by clicking on bill number link
		//and observe the sponsor and patient amount in bill for all ordered items
		
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
		order.addOrderItem("OrderItem", "OrdersPage");
		order.saveOrder(false);
		
		//5. Navigate to patient issues screen via bill by clicking on Issue link in the hospital bill 
		//and add medicines whose pacakge size=1, >1, claimable and non claimable items. Give quantity as 
		//5 for all medicines.
		//6. Observe the sponsor and patient amount for all items in issues screen here non claimable 
		//items should post completely under patient and rest should split as per the defined co-pay.
		//7. Click on Save to complete patient issues.
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
			
		executeStep.performAction(SeleniumActions.Click, "","IssueLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientIssuePage",false);
		
		PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
		patientIssue.addPatientIssueItem("IssueItem");
		patientIssue.saveIssue();	
		
		// Observe the sponsor and patient amount for inventory 

		navigation.navigateTo(driver, "BillsLink", "BillListPage");
	//	SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		
		//8. Go to Sales screen under Sales and Issues, enter MR No, select Bill Type as Add To Bill, 
		//add another set of items having pacakge size=1, >1, claimable and non claimable items(by giving 
		//quantity as 5) where split should happen for non claimable items also. Complete sales by clicking
		//on Add to Bill & Print.
		
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.doSales("IssueItem","SalesPageBillType");
		sales.closePrescriptionTab();
			
		
		// 9a. Go to Sales returns screen under Sales and Issues, add the above sold items by giving 
		//quantity as 3. Complete sales returns by selecting add to Bill. 
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturn=new SalesReturn(executeStep, verifications);
		salesReturn.doSalesReturn("IssueItem","IP");
	
		//9b. Go to Patient Issue Returns screen under Sales and Issues, add items which were issued above 
		//by giving quantity as 3. 
		navigation.navigateTo(driver, "PatientIssueReturnLink", "PatientIssueReturnPage");
		
	//	PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
		patientIssue.returnPatientIssueItem("IssueItem");
		patientIssue.saveIssueReturns();	
		
		//9c. Observe that sponsor and patient amounts in bill should become half against pharmacy items 
		//and inventory items. After doing sales and sales returns, issues and issue returns observe sponsor 
		//and patient amount for sale bill and inventory items in bill.
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
//		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();

		//10. Go to Add Patient Indent screen under Sales and Issues, enter the MR Number. Raise a patient 
		//indent by adding another set of serial and batch items (having packet size 1 and 10 and serial
		//items) by giving quantity as 5. Save this indent in finalized status..
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentSales", true);
		
		// 11. Go to Patient Indents List under Sales and Issues. Select the above generated indent and click on Issue.
		//12. Complete issues by clicking on Save.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//	RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
			raisePatientIndent.searchPatientIndent();
			raisePatientIndent.savePatientIndentIssue(true);
				
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			
			}
		
		
		//13a. Go to Add Patient Return Indent screen under Sales and Issues, enter the MR number and add 
		//above issued items(through indent) by giving 3 quantity each. Save this indent in finalized 
		//status.
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");
		RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("IndentSales", false);
	
		// 13b. Go to Patient Indents List under Sales and Issues. Select the above generated return indent 
		//and click on Issue Returns.
		// 13c. Complete issue returns by clicking on Save. Observe sponsor and patient amount for inventory items
		
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//	RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
			raisePatientIndent.searchPatientIndent();
			raisePatientIndent.savePatientIndentIssue(false);
		
		//14. Now click on Bed Details link in bill and then on Shift Bed link. Do shift bed and observe 
		//the sponsor and patient amount in bill for the new bed charges
		navigation.navigateTo(driver, "ADTLink", "ADTPage");
		//MR No search in ADT page
	    MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);	
		mrnoSearch.searchMRNo("InPatientListMrNoField","InPatientListSelectionMRNoLi","InPatientListSearchButton","ADTPage");
		verifications.verify(SeleniumVerifications.Appears, "","ADTPage",false);
		
		//Navigate to Shift Bed Screen
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("ADTShiftBedLink", "ShiftBedScreen");

		BedAllocation ShiftBed = new BedAllocation(executeStep, verifications);
		ShiftBed.AssignBed();
		ShiftBed.SaveShiftBed();
		
		//and observe the sponsor and patient amount in bill for bed charges	
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
//		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		
	
		// 15.Now do bed finalization and observe the sposnor and patient amount in bill for the bed charges.
		
		navigation.navigateTo(driver, "ADTLink", "ADTPage");

		BedFinalisation FinaliseBed = new BedFinalisation(executeStep, verifications);
		FinaliseBed.finaliseBed();

		//16.Settle patient amount in bill and save the bill
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");			
		PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.settleBills("YES", "UNPAID", "OP");
		
		//17.a.Go to Laboratory Pending samples screen, select the test and click on collect menu 
		//option. Select the samples by ticking the check boxes and click on Save.
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		//below is introduced to beat the tool tip menu
				try {
							Thread.sleep(10000);
				} catch (InterruptedException e) {
							// TODO Auto-generated catch block
						
				}
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
		
		//17.b.Go to laboratory reports screen > select the test and click on View/Edit, enter values 
		//1,2,3,4,etc) complete,validate and Signoff.
		
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
		
		//	17.c.Go to radiology reports > Select the test and click on View/Edit, choose a template then 
		//edit details and save. Mark complete,validate and signoff.
		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
		
		//17.d.Go to Pending Services List, select the service and click on edit menu option mark 
		//completed and save.
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
		
			try {
					Thread.sleep(500);
			} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				
			}
		
		//17e. Go to EMR and observe.
			navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
	//		MRNoSearch search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
			verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		
		//18.Go back to bill, click on 'Ok To Discaharge' check box and save the bill.
			// This involves selecting the BL bill and clicking on OK to discharge. Hence, 
			//done directly here.
			navigation.navigateTo(driver, "SearchBillLink", "BillListPage");		
			
	//		PatientBill patientBill = new PatientBill(executeStep, verifications);	
			patientBill.viewEditBills("BL");
			executeStep.performAction(SeleniumActions.Check, "","PatientBillOkToDischargeCheckBox");
			verifications.verify(SeleniumVerifications.Checked, "","PatientBillOkToDischargeCheckBox",false);
			executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");	
			verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
				
		//19.Go to Discharge screen in Registration module and discharge the patient.
			navigation.navigateTo(driver, "DischargeLink", "PatientDischargePage");
	//		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
			mrnoSearch.searchMRNo("PatientDischargeMRNoField", "PatientDischargeMRNoLi", "PatientDischargeFindButton", "PatientDischargePage");

			PatientDischarge patientDischarge = new PatientDischarge(executeStep, verifications);		
			patientDischarge.patientDischarge();	

		//20.Go to codification screen under Mediacl Records and enter mrno and then enter diagnosis 
		//codes,encounter details and all required codes for all activities and change the codification 
		//status as Completed.
		//21.Save the codification screen.
		
			navigation.navigateTo(driver, "CodificationLink", "EditCodesScreen");
			Codification codification=new Codification(executeStep, verifications);
			// codification.saveCodes(true);
			codification.saveEncounterCodes(true);
			codification.savePrimaryDiagnosisCode();
			codification.treatmentCode();
			codification.storeCodes();
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
		//22.Under Insurance module go to Claim Submission screen and click on 'New Claim Batch Submission'
		//Link
		//23.Select the respective Insurance company, sponsor,plan type and plan with which the patient
		//got registered
		//24.Select center/account group as Hopital and enter the registration date
		//25.Click on Create Submission Batch
		//26.Verify that Claim Submissions List dashboard contains the created submission batches.
			navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
			ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
			String submissionBatchID =claimsSubmission.submitClaims("InsuranceCenterAccGrp",true,false);
		//	claimsSubmission.submitClaims("InsuranceCenterAccGrp",false);

		////27.Click on the generated submission batch in Submission List dashboard and then on eclaim link 
		//28.Save the generated claim xml
		
			navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
			executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
			verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
			executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
		//	verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
		
			String downloadedFile = claimsSubmission.getInsuranceDetails(DataSet, "- Account Group -Hospital");
			
			String workingDir = System.getProperty("user.dir");
	        String filepath = workingDir;
	      // boolean done=filepath.makedir();
	        Path source = Paths.get(workingDir+"\\"+downloadedFile+".XML");
	        CommonUtilities.delay(3000);
	        try {
	        	
				Files.move(source, source.resolveSibling("Hospital_TS_031a.XML"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

      
		//29.Create a remittance file with partial amount and then go to remittance upload screen under 
	        //insurance module
	        XMLConverter xml = new XMLConverter(executeStep, verifications);
	        
	        xml.xml2XML("Partial","Hospital_TS_031a.XML","HAAD",submissionBatchID);
	       
			navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
			executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
			verifications.verify(SeleniumVerifications.Appears, "","ClaimSentLink",false);
			executeStep.performAction(SeleniumActions.Click, "","ClaimSentLink");
			verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
				        
		//30.Select the respective insurance company and sponsor and then account group hospital and upload the 
	        //remittance file
	    	
		    navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
		//    ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		//   String submissionBatchID="IS000016";
	        claimsSubmission.remittanceUpload("InsuranceCenterAccGrp", submissionBatchID);
	       
	    //31.Go to Claim Reconciliation screen using submission id/claim id
	    //32.Mark the claim for resubmission with internal Complaint and enter the resubmission comments
	     
	      navigation.navigateTo(driver, "ClaimReconciliationLink" , "ClaimReconciliationPage");
	 //     ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
	      claimsSubmission.claimsResubmission();
	        
	
		//33.Go to Claim Submission screen and select insurance company,sponsor,plan type,plan, regsitration 
	        //date and check the checkbox of Mark Resubmission and then click on Create Submission Batch for 
	        //hospital.Click on the generated submission batche in Submission List dashboard and then on 
	        //eclaim link.Save the genearted claim xml
		
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
	//	ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		submissionBatchID =claimsSubmission.submitClaims("InsuranceCenterAccGrp",true,true);
		
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
		executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
		
		 downloadedFile = claimsSubmission.getInsuranceDetailsForResubmission(DataSet, "- Account Group -Hospital");
		
		 workingDir = System.getProperty("user.dir");
		 filepath = workingDir;
		 source = Paths.get(workingDir+"\\"+downloadedFile+".XML");
        CommonUtilities.delay(3000);
        try {
        	
			Files.move(source, source.resolveSibling("Hospital_TS_031b.XML"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

      //34.Create a remittance file with full amount and then go to remittance upload screen.Select the 
        //respective insurance company and sponsor and then account group as hospital and upload the 
        //remittance file
    //   XMLConverter xml = new XMLConverter(executeStep, verifications);
        
        xml.xml2XML("Full","Hospital_TS_031b.XML","HAAD",submissionBatchID);
        navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimSentLink",false);
		executeStep.performAction(SeleniumActions.Click, "","ClaimSentLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
				           	
	    navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
        claimsSubmission.remittanceUpload("InsuranceCenterAccGrp", submissionBatchID);
       
//    	35.Go to reconciliation screen and observe the claim status of each item and claim it should be closed.
        //Observe claim status at claim level
//  	36.In Bill observe sponsor payment, claim status and bill status.
    	
        navigation.navigateTo(driver, "ClaimReconciliationLink" , "ClaimReconciliationPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimReconciliationClearButton");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimReconciliationPage",false);
		executeStep.performAction(SeleniumActions.Enter, "ClaimsBatchSubmissionId","ClaimReconciliationSubmitBatchIdField");
		verifications.verify(SeleniumVerifications.Entered, "ClaimsBatchSubmissionId","ClaimReconciliationSubmitBatchIdField",false);
						
		executeStep.performAction(SeleniumActions.Click, "","ClaimReconciliationPageSearch");                   
		verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimPageTable",false);
		executeStep.performAction(SeleniumActions.Click, "","ClaimPageTable");                   
		verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimBillsLink",false);

		executeStep.performAction(SeleniumActions.Click, "","ClaimBillsLink");                   
		verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimPage",false);
		testCaseStatus="PASS";
         	
			System.out.println("End Test IPWF For Ins patient with resubmission");
			
	}
	
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_031", "IPWFForInsurancePatientWithResubmission", null, "", "", testCaseStatus);
		driver.close();
	}
	
		
}
