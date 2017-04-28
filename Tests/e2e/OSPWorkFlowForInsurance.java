/**
 * 
 */
package e2e;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
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
import reusableFunctions.ClaimsSubmission;
import reusableFunctions.Codification;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DiagnosisDetails;
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
import reusableFunctions.XMLConverter;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author Sai
 *
 */
public class OSPWorkFlowForInsurance {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus = "Fail";

	
	@BeforeSuite
	public void BeforeSuite(){
//		System.out.println("BeforeSuite");
//		AutomationID = "Login - OSPWorkFlowForCashPatient";
//		reporter = new ReportingFunctions();
//		System.out.println("AfterReport");
//		
//		initDriver = new KeywordExecutionLibrary();
//		System.out.println("AfterInitDriver");
//
//		try{
//			System.out.println("EnvironmentSetUp is ::" + EnvironmentSetup.URLforExec);
//			driver = initDriver.LaunchApp("Chrome",  EnvironmentSetup.URLforExec);
//			System.out.println("Am here");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		Assert.assertFalse(driver==null, "Browser Not Initialized - Check Log File for Errors");
//		initDriver = null;
//		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
//		verifications.verify(SeleniumVerifications.Appears, "", "LoginScreenHospital_Est", false);
//		verifications = null;
//		
	}
	
//	@BeforeMethod
//	public void BeforeTest(){
	private void login(){
		AutomationID = "Login - OSPWorkFlowForInsurancePatient";
		DataSet = "TS_033";
		//EnvironmentSetup.testScenarioId = "TS_001";
		
		System.out.println("BeforeSuite");
		AutomationID = "Login - OSPWorkFlowForCashPatient";
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
	public void OSPWorkFlowForCashPatient(){
		login();
		DataSet = "TS_033";
		AutomationID = "OSPWorkFlowForInsurancePatient";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OSPWorkFlowForInsurancePatient - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OSPatientRegistrationLink", "OSPatientRegistrationScreen");
	
		//From here its for Entering details on the OP Registration Screen - Move to another reusable
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientGenericDetails();
		OPReg.RegisterPatientOSPInsurance();
		OPReg.storeDetails();	
		//OPReg.visitInformationDetails();

		executeStep.performAction(SeleniumActions.Click, "","OSPRegistrationOrder");
		verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",false);

		//navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		Order ospInsOrder = new Order(executeStep, verifications);

		ospInsOrder.searchOrder(false,false);
		ospInsOrder.addOSPOrder();			
		
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.searchMRNo();// Bcoz SearchMRNO is moved out of doPharmacySales -- Tejaswini -- To accomodate changes to OSP Cash Patient Navigation to Sales Page
		sales.doPharmacySales();
		sales.closePrescriptionTab();
		
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturn=new SalesReturn(executeStep, verifications);
		salesReturn.doSalesReturn("SalesReturn", "OSP");
		
		navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");
		PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.viewEditBills("BN-P");
		patientBill.settleBillDetails("YES", "");

/*		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);

		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
		verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);*/
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
/*		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);*/
		
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

		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);

		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
		
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		
		navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
		///PatientBill patientBill = new PatientBill (executeStep, verifications);
		patientBill.settleBills("YES", "UNPAID", "OSP");
		
		navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
		//MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		//PatientBill patientBill = new PatientBill (executeStep, verifications);

		patientBill.advancedSearch(Search,"REFUND");
   	 	patientBill.viewEditBills("BN-P");
   	 	patientBill.settleBillDetails("YES", "BillRefundPaymentType");

		navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
		//PatientBill patientBill = new PatientBill (executeStep, verifications);
		patientBill.settleBills("YES", "PAID", "OSP");
		
		navigation.navigateTo(driver, "CodificationLink", "EditCodesScreen");
		Codification codification=new Codification(executeStep, verifications);
		codification.saveCodesForOSP(false);	
		
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		String submissionBatchID =claimsSubmission.submitClaims("InsuranceCenterAccGrp",true,false);
		//String submissionBatchID = "IS000025";
		generateEClaim(navigation);
		//String submissionBatchID = "IS000030";
		renameEClaimFile(DataSet, "- Account Group -Pharmacy", "Pharmacy_TS_033a.XML",false);
		//String submissionBatchID = "IS000059";
		uploadRemittanceAndResubmit(navigation, "Partial", "Pharmacy_TS_033a.xml", submissionBatchID, "InsuranceCenterAccGrp");
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		//ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		String submissionBatchID3 =claimsSubmission.submitClaims("InsuranceCenterAccGrp",true,true);
		//String submissionBatchID3 = "IS000060";
		generateEClaim(navigation);
		renameEClaimFile(DataSet, "- Account Group -Pharmacy", "Pharmacy_TS_033b.XML", true);
		uploadRemittanceAndResubmit(navigation, "Full", "Pharmacy_TS_033b.xml", submissionBatchID3, "InsuranceCenterAccGrp");
		
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		//ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		String submissionBatchIDII =claimsSubmission.submitClaims("InsuranceCenterAccGrpII",true,false);
		//String submissionBatchIDII = "IS000036";
		if (submissionBatchIDII != null || submissionBatchIDII != ""){
			//navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
			generateEClaim(navigation);
			renameEClaimFile(DataSet, "- Account Group -Hospital", "Hospital_TS_033a.XML",false);
		//29.Create a remittance file with partial amount and then go to remittance upload screen under 
	        //insurance module
	
			uploadRemittanceAndResubmit(navigation, "Partial", "Hospital_TS_033a.xml", submissionBatchIDII, "InsuranceCenterAccGrpII");
	        
	    //31.Go to Claim Reconciliation screen using submission id/claim id
	    //32.Mark the claim for resubmission with internal Complaint and enter the resubmission comments      
	
		//33.Go to Claim Submission screen and select insurance company,sponsor,plan type,plan, regsitration 
	        //date and check the checkbox of Mark Resubmission and then click on Create Submission Batch for 
	        //hospital.Click on the generated submission batche in Submission List dashboard and then on 
	        //eclaim link.Save the genearted claim xml
		
			navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		//ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
			String submissionBatchID4 =claimsSubmission.submitClaims("InsuranceCenterAccGrpII",true,true);
//		String submissionBatchID4 = "IS000023";
			generateEClaim(navigation);
		
			renameEClaimFile(DataSet, "- Account Group -Hospital", "Hospital_TS_033b.XML", true);

	  //34.Create a remittance file with full amount and then go to remittance upload screen.Select the 
	    //respective insurance company and sponsor and then account group as hospital and upload the 
	    //remittance file

		//String submissionBatchID4 = "IS000050";

			uploadRemittanceAndResubmit(navigation, "Full", "Hospital_TS_033b.xml", submissionBatchID4, "InsuranceCenterAccGrpII");
		}

	//	navigation.navigateTo(driver, "ClaimReconciliationLink" , "ClaimReconciliationPage");
		//ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);

	//	36.In Bill observe sponsor payment, claim status and bill status.
	  //	claimsSubmission.claimBill();*/
		testCaseStatus = "Pass";
		System.out.println("TS_033 completed Successfully");


	}	


	private void renameEClaimFile(String DataSet, String fileNameSuffix, String outputFileName, boolean reSubmission){
		ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		//String downloadedFile = claimsSubmission.getInsuranceDetails(DataSet, "- Account Group -Pharmacy");
		String downloadedFile = claimsSubmission.getInsuranceDetails(DataSet, fileNameSuffix);
		if (reSubmission){
			downloadedFile = "Resubmission-"+downloadedFile;
		}
		String workingDir = System.getProperty("user.dir");
        String filepath = workingDir;
      // boolean done=filepath.makedir();
        Path source = Paths.get(workingDir+"\\"+downloadedFile+".XML");
        CommonUtilities.delay(3000);
        try {
        	
			//Files.move(source, source.resolveSibling("Pharmacy_TS_016a.XML"));
        	Files.move(source, source.resolveSibling(outputFileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("");
		}

	}
	
	private void uploadRemittanceAndResubmit(SiteNavigation navigation,String partialOrFull, String inputXMLName, String submissionBatchID, String AccGroup){
       XMLConverter xml = new XMLConverter(executeStep, verifications);
        
        xml.xml2XML(partialOrFull,inputXMLName,"HAAD",submissionBatchID);
        //xml.xml2XML("Partial","Hospital_TS_16a.XML","HAAD",submissionBatchID);
       
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		
		/*executeStep.performAction(SeleniumActions.Click, "","ClaimReconciliationClearButton");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimsSubmissionPage",false);
		
		executeStep.performAction(SeleniumActions.Enter, "ClaimsBatchSubmissionId","ClaimsSubmissionListBatchIDField");
		verifications.verify(SeleniumVerifications.Entered, "ClaimsBatchSubmissionId","ClaimsSubmissionListBatchIDField",false);
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionListSearchBtn");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimsSubmissionTableRowToClick",false);*/
		
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimSentLink",false);
		executeStep.performAction(SeleniumActions.Click, "","ClaimSentLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
			        
	//30.Select the respective insurance company and sponsor and then account group hospital and upload the 
        //remittance file
    	
	    navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
	    ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
	//   String submissionBatchID="IS000016";
        claimsSubmission.remittanceUpload(AccGroup, submissionBatchID);
		if (partialOrFull.equalsIgnoreCase("Partial")){
			navigation.navigateTo(driver, "ClaimReconciliationLink" , "ClaimReconciliationPage");
			//ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
			claimsSubmission.claimsResubmission();
		} else {
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

		}
		
	}

	public void generateEClaim(SiteNavigation navigation){
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		/*executeStep.performAction(SeleniumActions.Click, "","ClaimSubmissionsListClearLink");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimsSubmissionPage",true);
		executeStep.performAction(SeleniumActions.Enter, "ClaimsBatchSubmissionId","ClaimsSubmissionListBatchIDField");
		verifications.verify(SeleniumVerifications.Entered, "ClaimsBatchSubmissionId","ClaimsSubmissionListBatchIDField",false);
		//executeStep.performAction(SeleniumActions.Click, "","ClaimSubmissionsListClearLink");
		//verifications.verify(SeleniumVerifications.Appears, "","ClaimsSubmissionPage",true);

		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionListSearchBtn");*/

		verifications.verify(SeleniumVerifications.Appears, "","ClaimsSubmissionTableRowToClick",false);

		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		//MRNoSearch search = new MRNoSearch(executeStep, verifications);
		verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
		System.out.println("");
		executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
		executeStep.performAction(SeleniumActions.HoverOver, "","ClaimsSubmissionPage");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimsSubmissionPage",false);
      //  closeDownloadBarInChrome(navigation);

	}
	
	public void closeDownloadBarInChrome(SiteNavigation navigation){
/*		driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, "j"));
		Actions action = new Actions(driver);		
		action.sendKeys(Keys.chord(Keys.CONTROL, "j")).build().perform();
//		action.keyUp(Keys.CONTROL).build().perform();
//		CommonUtilities.delay();        
//		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
//		driver.switchTo().window(tabs2.get(1));
//		CommonUtilities.delay();
//		driver.close();
//		driver.switchTo().window(tabs2.get(0));*/
		driver.close();
		login();
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		CommonUtilities.delay();
	}

	@AfterClass
	public void closeBrowser(){
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OSPWorkFlowForInsurance", null, "", "", testCaseStatus);
		driver.close();
	}
}
