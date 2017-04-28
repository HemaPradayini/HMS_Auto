package e2e;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import reusableFunctions.Codification;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.PBMApprovalList;
import reusableFunctions.PBMPrescription;
import reusableFunctions.PBMPrescriptionsList;
import reusableFunctions.Registration;
import reusableFunctions.Sales;
import reusableFunctions.SiteNavigation;
import reusableFunctions.Upload;
import reusableFunctions.XMLConverter;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PBMWorkFlow {

	WebDriver driver;
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
		AutomationID = "Login - PBMWorkFlow";
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
		AutomationID = "Login - PBMWorkFlow";
		DataSet = "TS_068";
		
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
	public void PBMWorkFlow(){
		openBrowser();
		login();
		DataSet = "TS_068";
		AutomationID = "PBMWorkFlow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test PBMWorkFlow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		//1 Go to OP Registration screen and enter all patient level and visit level information 
		//(all mandatory fields) select doctor as Aameer .Also select primary sponsor check box,
		//select primary tpa/sponsor,company,netwok plan type and  insurance plan name 
		//as Plan1 and then select bill type as Bill Now. Enter mrmbership id as 1116528.
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	    /*navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		Registration OPReg = new Registration(executeStep, verifications);
		Upload upload = new Upload(executeStep, verifications);
		OPReg.RegisterPatientGenericDetails();
		OPReg.GovtIDDetailsCollapsedPanel();
		OPReg.setBillType();
		OPReg.RegisterPatientInsurance("OP");
		OPReg.visitInformationDetails();
		upload.upload("//input[@id='primary_insurance_doc_content_bytea1' and @type='file']");
	
		//2.Click on Register&Pay button and register the patient
		executeStep.performAction(SeleniumActions.Click, "","OPRScreenRegister&PayButton");
		verifications.verify(SeleniumVerifications.Appears, "","RegistrationSucccessScreen",false);
			*/
		//3.Go to Out Patient list and enter the mrno and then on serach button. 
		//Click on the listed record and then on Consult link.
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
		
		ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
		
		//4.In consultation screen enter all required information like diagnosis details and form inputs. Enter diagnosis codes which are valid say search the codes with description as headache and other as fever
		//5.Prescribe few medicines which are matching the  diagnosis codes say Adol 500 mg (1000 package size) and other as Brufen 200mg (package size 25)
		//6.Save the consultation screen after entering all required information
		consultMgmt.saveConsultationAndMgmt("Diagnosis");
		
		//7.Under PBM Authorization module click on PBM Prescription List link. 
		//In dashboard click on the latest prescription id (say the generated PBM Presc id is 30 i.e the patien which is registered in step 2
		navigation.navigateTo(driver, "PBMPrescriptionsListLink", "PBMPrescriptionsListPage");
		PBMPrescriptionsList pbmprescriptionList = new PBMPrescriptionsList(executeStep, verifications);
		pbmprescriptionList.searchMRNo();
		
		executeStep.performAction(SeleniumActions.Click, "","PBMPrescriptionsListResultList");
		verifications.verify(SeleniumVerifications.Appears, "","EditPBMPresc",true);
		CommonUtilities.delay();
		
		executeStep.performAction(SeleniumActions.Click, "","EditPBMPresc");
		
		//Close parent tab
		Set<String> winSet = driver.getWindowHandles();
		List<String> winList = new ArrayList<String>(winSet);
		String newTab = winList.get(winList.size() - 1);
		driver.close(); // close the original tab
		driver.switchTo().window(newTab);
		
		// click on 'Edit Item Details' dialogue box and enter Item Form,Strength,Route,Duration and Frequency. Perform this step for both the medicines in PBM Prescription screen. click in Save PBM button.
		//In PBM Prescription screen,select the store as Administration Consumables 
		executeStep.performAction(SeleniumActions.Select, "PBMPrescriptionsStore","PrescriptionsListField");
		verifications.verify(SeleniumVerifications.Selected, "PBMPrescriptionsStore","PrescriptionsListField",false);
		
		PBMPrescription pbmprescription = new PBMPrescription(executeStep, verifications);
		pbmprescription.addItemsToPBMPrescription("pbmprescriptionitem");
		
		//9.Now check the Finalize check box and then Save the PBM Prescription.
		executeStep.performAction(SeleniumActions.Check, "","PBMPrescriptionsFinalize");             
		verifications.verify(SeleniumVerifications.Appears,"" ,"PBMPrescriptionsPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PBMPrescriptionsSaveButton");
	
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
		//10.Click on 'PBM Request' link and then click on menu option of 30 PBM Presc id and then click on 'PBM Request' link.
		executeStep.performAction(SeleniumActions.Click, "","PBMPrescriptionsRequestsLink");
		verifications.verify(SeleniumVerifications.Appears, "","PBMRequestsList",false);
		
		//search with mrno
		
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("PBMApprovalListMRNoField", "PBMApprovalListMRNoList", "PBMApprovalListSearchButton", "PBMApprovalListResultList");
		
		executeStep.performAction(SeleniumActions.Click, "","PBMRequestsListResult");
		verifications.verify(SeleniumVerifications.Appears, "","PBMRequestOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PBMRequestOption");
		verifications.verify(SeleniumVerifications.Appears, "","PBMStatusSent",false);

		
		//11.Now click on 'PBM Approval' link under PBM Authorization module. Observe the Approval Status for PBM Presc. ID 30 and click on refresh button
		navigation.navigateTo(driver, "PBMApprovalListLink", "PBMApprovalListPage");
		PBMApprovalList pbmapprovallist = new PBMApprovalList(executeStep, verifications);
		
		executeStep.performAction(SeleniumActions.Click, "","PBMApprovalRefreshButton");
		verifications.verify(SeleniumVerifications.Appears, "","PBMApprovalListPage",false);
		
		mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("PBMApprovalListMRNoField", "PBMApprovalListMRNoList", "PBMApprovalListSearchButton", "PBMApprovalListResultList");
	
		
		CommonUtilities.delay();
		//12.Once the Approval Status is changed to Fully Approved/Partially Approved, clikc on the approval and then on Sales link
		verifications.verify(SeleniumVerifications.Appears, "","PBMApprovalListResultList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PBMApprovalListResultList");
		verifications.verify(SeleniumVerifications.Appears, "","PBMApprovalListSalesOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PBMApprovalListSalesOption");
		//verifications.verify(SeleniumVerifications.Appears, "","PBMSalesPageHeader",true);
		
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","PBMSalesPageHeader",true);
		CommonUtilities.delay(2000);
		
		//13.Once the user is navigated to PBM Sales screen, approved medicines should get auto filled in sales screen. Click on Pay&Print and complete the sales
		
		executeStep.performAction(SeleniumActions.Click, "","SalesPayAndPrint");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","PBMSalesPageHeader",false);
		
		//14. Now go to Codification screen under Medical Records module, enter the mrno and then after visit id is auto filled click on Find button
		 navigation.navigateTo(driver, "CodificationLink", "EditCodesScreen");
				
		//15.Enter all mandatory information in codification screen i.e diagnosis codes, encounter start, encounter end code and code type at each
		//item level in codification screen.Then change the codification status as Completed.
		Codification codification=new Codification(executeStep, verifications);
		codification.saveCodes(true);		
				
	    //16. Go to submission batch screen and  create submission batch by providing required information and then by selecting Center/Account Group as hospital.
		//Now again repeat it by selecting Center/Account Group as pharmacy. Say generated submisison id for hospital is IS0001 and for pharmacy is IS0002.
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");   
		
	
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		String submissionBatchID = claimsSubmission.submitClaims("InsuranceCenterAccGrp",true,false);
		
		//17.Go to Claims Submission List dashboard and click on both the generated submission id IS0001 and then on E-claim to generate the claim xml for pharmacy.
		
		/*navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
		executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");*/
		
		//navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
		executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
		//verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",false);
       String downloadedFile = claimsSubmission.getInsuranceDetails(DataSet, "- Account Group -Pharmacy");
       
       String workingDir = System.getProperty("user.dir");
       String filepath = workingDir;
       Path source = Paths.get(workingDir+"\\"+downloadedFile+".XML");
       CommonUtilities.delay(3000);
       try {
       	
			Files.move(source, source.resolveSibling("PBM.XML"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      //.Once clicking on e-claim, an xml will be generated which should be saved.Save as hospital.xml and pharmacy.xml 
       //to differentiate. Also mark the submission batch id's as sent.Open the xml's with gedit and generate the remittance 
       //file with full payment amount i.e Net=Payment amt at ecah item level.(Refer the remittance file format to generate the  xml)
       
       XMLConverter xml = new XMLConverter(executeStep, verifications);
       
       xml.xml2XML("Full","PBM_TS_068.XML","HAAD",submissionBatchID);
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
       
       //20.Now navigate to Claim Reconciliation screen under Insurance module and enter the submission id,set 'All' for all the available filters and click on Search.
       //21.Once the respective claim is filtered, click on it and then on 'Claim Bill' option
       //22.Observe the Received amount and claim status at each item level also at claim level. Observe the claim status and bill status in bill also.

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
		System.out.println("PBM work flow");
		
		
	}
	
	@AfterTest
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, DataSet, "PBM work flow", null, "", "", testCaseStatus);
		driver.close();
	}
}
