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
import org.openqa.selenium.WebElement;
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
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.Registration;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
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
public class IPWorkflowForMultiInsurancePatient {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String submissionBatchID;
	String downloadedFile;
	String workingDir;
    String filepath;
    Path source;
    String testCaseStatus="FAIL";
	
	//@BeforeSuite
	//public void BeforeSuite(){
	private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - IPWorkflowForMultiInsurancePatient";
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
		AutomationID = "IPWorkflowForMultiInsurancePatient";
		DataSet = "TS_023";
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		// Delay is introduced to handle the sync problem happening because of two 
		//consecutive close tours and no verification possible
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void IPWFForInsPatientWithoutCopay(){
		openBrowser();
		login();
		
		DataSet = "TS_023";
		AutomationID = "IPWorkflowForMultiInsurancePatient";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForInsurance - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);	
	
		navigation.navigateTo(driver, "IPRegistrationLink", "IPRegistrationPage");
		
		// 1.Go to IP Registration screen and enter all required information. Select patient 
		// category as General
		// 2.Select Network / Plan Type and Plan Name under Primary Sponsor and Secondary Sponsor 
		// section
		//3.Enter primary membership id,secondary membership id,validity start and validity end 
		// under primary and secondary sponsor section.Select bill type as Bill Later
		// 4.Select bed, ward, bed number, admitting doctor, duty doctor and then click on Register

			Registration IPReg = new Registration(executeStep, verifications);
			IPReg.RegisterPatientMultiInsuranceIP();

		
		//5.Click on Order link in bill and order all billable items. Observe the patient amt 
		// for all the orderd items in order screen before saving. Save the order
		//	6.Observe the item level parameters in bill. 	 		
			
			navigation.navigateTo(driver, "BillsLink", "BillListPage");
			SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
			searchBillList.searchBills();
				
			PatientBill patientBill = new PatientBill(executeStep, verifications);
			patientBill.addBillOrder("OrderBillItem");
				
				
		//6a. Go to Laboratory Pending samples screen, select the test and 
		//click on collect menu option. Select the samples by ticking the check boxes and click on Save.
			
			//Collect samples
			navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
			MRNoSearch Search = new MRNoSearch(executeStep, verifications);
			Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
			
			executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
			verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
			
			executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
			verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
			
			CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
			collectSamples.collectSamplesAndSave();
		

		//6b. Go to laboratory reports screen > select the test and click on View/Edit, 
		// enter values (1,2,3,4,etc) complete,validate and Signoff.
			
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
		
		//6c. Go to radiology reports > Select the test and click on View/Edit, choose a 
		//template then edit details and save. Mark complete,validate and signoff.
			
			navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
			RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
			radiologyPendingTests.radiologyPending(true);

		// 6d. Go to pending services list >Select the service and click on edit menu option 
		// ,mark completed and save.
		
			navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
			ServicePending servicePending = new ServicePending(executeStep, verifications);
			servicePending.conductPendingServices();
				
				
			try {
			Thread.sleep(500);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			}
		
		//7. Go to Patient &Visit EMR and observe documents..
			
			navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
			//MRNoSearch search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
			verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
		
		//8.Now go to sales screen and enter the MR No, wait for the patient details to auto fill, 
		//select bill type as Bill Now Insurance. Click on plus icon and then add DrugClaimable 
		//item, DrugNonClaimable item, pkg size>1 and =1 with qty as 5.
	  //9.Observe the item level values in sales screen and then complete the sales	
				navigation.navigateTo(driver, "SalesLink", "SalesPage");
				Sales sales = new Sales(executeStep, verifications);
				sales.doSales("SalesBNItem","SalesPageBillType");
				sales.closePrescriptionTab();
	
		//10.Go to search bills screen and then enter mrno. Click on bill with bill type suffixed 
		// with P as 'BN-P'. Now observe the item level parameters.
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		
		executeStep.performAction(SeleniumActions.Click, "","BillSearchScreenMoreOptions");
		verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
		executeStep.performAction(SeleniumActions.Click, "","BillListStatusAllChBox");
		verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
//		PatientBill patientBill = new PatientBill(executeStep, verifications);		
		
		patientBill.viewEditBills("BN-P");
		
		//11.Now again go to sales screen and enter the mrno, wait for the patient details to auto 
		//fill and then on search. Select bill type as Add To Bill. Click on plus icon and 
		//then add DrugCliamable,DrugNonClaimable item, pkg size>1 and =1 with qty = 5.
				
				navigation.navigateTo(driver, "SalesLink", "SalesPage");
	//			Sales sales = new Sales(executeStep, verifications);
				sales.doSales("SalesABItem","SalesPageBillTypeSecond");
				sales.closePrescriptionTab();
				
				
		//12.Complete the sales and then open the hospital bill. In order to open the hospital bill 
		//go to search bill screen, enter mrno, click on serach, now click on view/edit option of 
		//the respective bill no. Now observe the pri sponsor, sec sponsor and patient amt for the 
		//pharmacy bill added to hospital bill.
				 
			navigation.navigateTo(driver, "BillsLink", "BillListPage");
		//	MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		
			executeStep.performAction(SeleniumActions.Click, "","BillSearchScreenMoreOptions");
			verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
			executeStep.performAction(SeleniumActions.Click, "","BillListStatusAllChBox");
			verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
			
		//	PatientBill patientBill = new PatientBill(executeStep, verifications);		
			patientBill.viewEditBills("BL");
			
		//13.In bill, click on Issues link, once patient details are auto filled click on plus icon 
		//and add some items. 
		//Observe the pri sponsor,sec sponsor and patient amt in issues screen.
		//Complete the issues and then again observe the pri sponsor,sec sponsor and patient 
		//amt in hospital bill for the inventory items
			
			navigation.navigateTo(driver, "BillsLink", "BillListPage");
	//			SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
				searchBillList.searchBills();
				
				executeStep.performAction(SeleniumActions.Click, "","IssueLink");
				verifications.verify(SeleniumVerifications.Appears, "","PatientIssuePage",false);
				
				PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
				patientIssue.addPatientIssueItem("IssuePatientItem");
				patientIssue.saveIssue();
	
		
		//14.Now go to sales returns screen under Sales module. Enter the mrno and then click 
		//on the auto filled record. Once the patient details are auto filled click on plus 
		//icon and then enter the items which need to be returned with the return qty as equal 
		//to sold qty or partial qty. Observe the pri Sponsor,Sec Sposnor and Patient amt in 
		//sales returns screen. 
				
				navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
				SalesReturn salesReturn=new SalesReturn(executeStep, verifications);
				salesReturn.doSalesReturn("SalesABItem","IP");

							
		//15.Now go to issue returns screen under Sales module. Enter the mrno and 
		//then click on the auto filled record. Once the patient details are auto filled 
		//click on plus icon and then enter the items which need to be returned with the 
		//return qty as equal to issued qty or partial qty. Observe the pri Sponsor,Sec 
		//Sponsor and Patient amt in issue returns screen. Do issue returns.
						
				navigation.navigateTo(driver, "PatientIssueReturnLink", "PatientIssueReturnPage");
				//PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
				patientIssue.returnPatientIssueItem("IssuePatientItem");
				patientIssue.saveIssueReturns();	
	
		
				
				
		//16. Click on Bed Details link in bill and then click on Finalize check box 
		// with date as future date. 
									
				navigation.navigateTo(driver, "BillsLink", "BillListPage");
				//	MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
				executeStep.performAction(SeleniumActions.Click, "","BillSearchScreenMoreOptions");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListStatusAllChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
			//	mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");
						
		//		PatientBill patientBill = new PatientBill(executeStep, verifications);		
				patientBill.viewEditBills("BL");
				
				executeStep.performAction(SeleniumActions.Click, "","BillPageBedDetailsLInk");
				verifications.verify(SeleniumVerifications.Appears, "","BedDetailsScreen",false);
				
				DbFunctions dbFunctions = new DbFunctions();
				dbFunctions.storeDate(this.executeStep.getDataSet(), "ShiftEndDate","N",1);
				
				BedFinalisation FinaliseBed = new BedFinalisation(executeStep, verifications);
				FinaliseBed.finaliseBedFromBill();
				
		//Come back to bill and check the 'Ok To Discahrge' check box and save the bill. 
		//Do patient amount settlement in the bill and change the bill status as finalized.
				
				//navigation.navigateTo(driver, "BillsLink", "BillListPage");	
				executeStep.performAction(SeleniumActions.Click, "","BedDetailsBillLink");
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
				//PatientBill patientBill = new PatientBill(executeStep, verifications);		
				//patientBill.settleBills("YES", "UNPAID", "IP");

				patientBill.setBillStatus("YES");
	    		  
	    		WebElement we = null;
	   			try{
	   						we = this.executeStep.getDriver().findElement(By.xpath("//input[@name='_okToDischarge']"));
	   			}catch (Exception e){
	   					System.out.println("Webelement for given xpath not found" + e.toString());
	   			}
	   			if (we!=null){	
	   						executeStep.performAction(SeleniumActions.Check, "","PatientBillOkToDischargeCheckBox");
		    				verifications.verify(SeleniumVerifications.Checked, "","PatientBillOkToDischargeCheckBox",false);
	   			}		
	    	
	   			patientBill.settleBillDetails("YES","SettlementPaymentType");
				
				
		//17.Go to discharge screen under Registration module and enter the mrno and then 
		//click on Find button. Enter the discharge type and then click on Discharge button
				navigation.navigateTo(driver, "DischargeLink", "PatientDischargePage");
//				MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
				mrnoSearch.searchMRNo("PatientDischargeMRNoField", "PatientDischargeMRNoLi", "PatientDischargeFindButton", "PatientDischargePage");

				PatientDischarge patientDischarge = new PatientDischarge(executeStep, verifications);		
				patientDischarge.patientDischarge();	
					
			
		//18.Now go to Codification screen under Medical Records module, enter the mrno and 
		//then after visit id is auto filled click on Find button
		// 19.Enter all mandatory information in codification screen i.e diagnosis codes,
		// counter start,encounter end,code and code type at each item level in codification 
		//screen.Then change the codification status as Completed.
				
				navigation.navigateTo(driver, "CodificationLink", "EditCodesScreen");
				Codification codification=new Codification(executeStep, verifications);
			//	codification.saveCodes(true);
				
				codification.saveEncounterCodes(true);
				codification.savePrimaryDiagnosisCode();
				codification.treatmentCode();
				codification.storeCodes();
				
				
				verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
				executeStep.performAction(SeleniumActions.Accept, "","Framework");
							
				
		//17. Go to submission batch screen and  create submission batch by providing required 
		//information i.e insurance company,plan type,tpa and plan as Company1,Plan Type1,TPA1 and 
		//Plan1 by selecting Center/Account Group as hospital. Such that created submisison batches is IS0001
		
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		submissionBatchID = claimsSubmission.submitClaims("InsuranceCenterAccGrp",true,false);
		
		//18. Go to Claims Submission List dashboard and click on the generated submission 
		//id and then on E-claim menu option to generate the claim xml for hospital with Primary Sponsor
		
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",true);
		executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
	
		//19.Once clicking on e-claim, an xml will be generated which should be saved.Save as 
		//PriHospital.xml. 
		downloadedFile = claimsSubmission.getInsuranceDetails(DataSet, "- Account Group -Hospital");
		
		workingDir = System.getProperty("user.dir");
        filepath = workingDir;
        source = Paths.get(workingDir+"\\"+downloadedFile+".XML");
        CommonUtilities.delay(3000);
        try {
        	
			Files.move(source, source.resolveSibling("PriHospital_TS_014.XML"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		//20. Also mark the submission batch id's as sent.Open the xml's with gedit 
		//and generate the remittance file with full payment amount i.e Net=Payment amt at ecah 
		//item level.(Refer the remittance file format to generate the remittance xml). Create 
		//remittance file 
        XMLConverter xml = new XMLConverter(executeStep, verifications);
        
        xml.xml2XML("Full","PriHospital_TS_014.XML","HAAD",submissionBatchID);
       
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimSentLink",false);
		executeStep.performAction(SeleniumActions.Click, "","ClaimSentLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
			        
		//21.Select the respective insurance company and sponsor and then account group hospital and upload the 
		//remittance file
    	
	    navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
	//    ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
	//   String submissionBatchID="IS000016";
        claimsSubmission.remittanceUpload("InsuranceCenterAccGrp", submissionBatchID);

        
		
		//22. Now navigate to Claim Reconciliation screen under Insurance module and enter the 
		//submission id's,set 'All' for all the available filters and click on Serach.	
		//23.Once the respective claim is filtered, click on it and then on 'Claim Bill' 
		//option(Repeat for all the 4 submission id's)	
		//24.Observe the Received amount and claim status at each item level also at claim level. 
		//Observe the claim status and bill status in bill also
        
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
		
		//17a. Go to submission batch screen and  create submission batch by providing required 
		//information i.e insurance Conpany2,Plan Type2,TPA2 and Plan2 with center/account group as hospital.
		// Such that created submisison batches is IS0002
				
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
//			ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		submissionBatchID =claimsSubmission.submitClaims("InsuranceCenterAccGrp",false,false);
				
		//18a. Go to Claims Submission List dashboard and click on the generated submission 
		//id and then on E-claim menu option to generate the claim xml for hospital with Secondary Sponsor.
				
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",true);
		executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
	
		//19a.Once clicking on e-claim, an xml will be generated which should be saved.Save as 
		//SecHospital.xml. 
		downloadedFile = claimsSubmission.getSecondaryInsuranceDetails(DataSet, "- Account Group -Hospital");
		
		workingDir = System.getProperty("user.dir");
        filepath = workingDir;
        source = Paths.get(workingDir+"\\"+downloadedFile+".XML");
        CommonUtilities.delay(3000);
        try {
        	
			Files.move(source, source.resolveSibling("SecHospital_TS_014.XML"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  //20a. Also mark the submission batch id's as sent.Open the xml's with gedit 
	  //and generate the remittance file with full payment amount i.e Net=Payment amt at ecah 
	  //item level.(Refer the remittance file format to generate the remittance xml). Create 
	  //remittance file 
//		XMLConverter xml = new XMLConverter(executeStep, verifications);
		        
		xml.xml2XML("Full","SecHospital_TS_014.XML","HAAD",submissionBatchID);
		       
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimSentLink",false);
		executeStep.performAction(SeleniumActions.Click, "","ClaimSentLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
					        
		//21a.Select the respective insurance company and sponsor and then account group hospital and upload the 
		//remittance file
		    	
		navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
  //    ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
   //   String submissionBatchID="IS000016";
		claimsSubmission.remittanceUpload("InsuranceCenterAccGrp", submissionBatchID);
        
	  //22a. Now navigate to Claim Reconciliation screen under Insurance module and enter the 
	  //submission id's,set 'All' for all the available filters and click on Serach.	
	  //23a.Once the respective claim is filtered, click on it and then on 'Claim Bill' 
	  //option(Repeat for all the 4 submission id's)	
	  //24a.Observe the Received amount and claim status at each item level also at claim level. 
	  //Observe the claim status and bill status in bill also
		        
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
		
		
											
		//17b. Go to submission batch screen and  create submission batch by providing required 
		//information i.e insurance Conpany1,Plan Type1,TPA1 and Plan1 with center/account group as pharmacy.
		// Such that created submisison batches is IS0003
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
	//ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		submissionBatchID = claimsSubmission.submitClaims("InsuranceCenterAccGrpII",true,false);
						
		//18b. Go to Claims Submission List dashboard and click on the generated submission 
		//id and then on E-claim menu option to generate the claim xml for pharmacy with Secondary Sponsor
						
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",true);
		executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
		
		//19b.Once clicking on e-claim, an xml will be generated which should be saved.Save as 
		//PriPharmacy.xml. 
		downloadedFile = claimsSubmission.getInsuranceDetails(DataSet, "- Account Group -Pharmacy");
		
		workingDir = System.getProperty("user.dir");
        filepath = workingDir;
        source = Paths.get(workingDir+"\\"+downloadedFile+".XML");
        CommonUtilities.delay(3000);
        try {
        	
			Files.move(source, source.resolveSibling("PriPharmacy_TS_014.XML"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
        
	   //20b. Also mark the submission batch id's as sent.Open the xml's with gedit 
	   //and generate the remittance file with full payment amount i.e Net=Payment amt at ecah 
	   //item level.(Refer the remittance file format to generate the remittance xml). Create 
	   //remittance file 
//			XMLConverter xml = new XMLConverter(executeStep, verifications);
				        
		xml.xml2XML("Full","PriPharmacy_TS_014.XML","HAAD",submissionBatchID);
				       
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimSentLink",false);
		executeStep.performAction(SeleniumActions.Click, "","ClaimSentLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
							        
		//21b.Select the respective insurance company and sponsor and then account group pharmacy and upload the 
		//remittance file
				    	
		navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
  //    ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
   //   String submissionBatchID="IS000016";
		claimsSubmission.remittanceUpload("InsuranceCenterAccGrpII", submissionBatchID);  
		
		//22b. Now navigate to Claim Reconciliation screen under Insurance module and enter the 
		//submission id's,set 'All' for all the available filters and click on Serach.	
		//23b.Once the respective claim is filtered, click on it and then on 'Claim Bill' 
		//option(Repeat for all the 4 submission id's)	
		//24b.Observe the Received amount and claim status at each item level also at claim level. 
		//Observe the claim status and bill status in bill also
				        
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
		
		
		//17c. Go to submission batch screen and  create submission batch by providing required 
		//information i.e insurance Conpany2,Plan Type2,TPA2 and Plan2 with center/account group as pharmacy.
		// Such that created submisison batches is IS0004
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
	//ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
		submissionBatchID = claimsSubmission.submitClaims("InsuranceCenterAccGrpII",false,false);
								
		//18c. Go to Claims Submission List dashboard and click on the generated submission 
		//id and then on E-claim menu option to generate the claim xml for pharmacy with Secondary Sponsor
								
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",true);
		executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");
	
		//19c.Once clicking on e-claim, an xml will be generated which should be saved.Save as 
		//SecPharmacy.xml. 
		downloadedFile = claimsSubmission.getSecondaryInsuranceDetails(DataSet, "- Account Group -Pharmacy");
		
		workingDir = System.getProperty("user.dir");
        filepath = workingDir;
        source = Paths.get(workingDir+"\\"+downloadedFile+".XML");
        CommonUtilities.delay(3000);
        try {
        	
			Files.move(source, source.resolveSibling("SecPharmacy_TS_014.XML"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 //20c. Also mark the submission batch id's as sent.Open the xml's with gedit 
		 //and generate the remittance file with full payment amount i.e Net=Payment amt at ecah 
		 //item level.(Refer the remittance file format to generate the remittance xml). Create 
		 //remittance file 
//			XMLConverter xml = new XMLConverter(executeStep, verifications);
						        
		xml.xml2XML("Full","SecPharmacy_TS_014.XML","HAAD",submissionBatchID);
						       
		navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimSentLink",false);
		executeStep.performAction(SeleniumActions.Click, "","ClaimSentLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddEditSubmissionBatchReferencePage",false);
									        
		 //21c.Select the respective insurance company and sponsor and then account group pharmacy and upload the 
		 //remittance file
						    	
		navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
  //    ClaimsSubmission claimsSubmission = new ClaimsSubmission(executeStep, verifications);
   //   String submissionBatchID="IS000016";
		claimsSubmission.remittanceUpload("InsuranceCenterAccGrpII", submissionBatchID); 
				
		 //22c. Now navigate to Claim Reconciliation screen under Insurance module and enter the 
		//submission id's,set 'All' for all the available filters and click on Serach.	
		//23c.Once the respective claim is filtered, click on it and then on 'Claim Bill' 
		//option(Repeat for all the 4 submission id's)	
		//24c.Observe the Received amount and claim status at each item level also at claim level. 
		//Observe the claim status and bill status in bill also
				        
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
				System.out.println("End Test IPWF For Multi Ins patient");
	}
	
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_023", "IPWorkflowForMultiInsurancePatient", null, "", "", testCaseStatus);
		
		driver.close();
	}
		
}
