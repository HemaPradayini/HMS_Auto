package insurance;

	import java.util.List;

	import keywords.SeleniumActions;
	import keywords.SeleniumVerifications;
	import GenericFunctions.CommonUtilities;
	import GenericFunctions.EnvironmentSetup;
	import GenericFunctions.ReportingFunctions;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
	import org.testng.annotations.AfterMethod;
	import org.testng.annotations.AfterTest;
	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.BeforeSuite;
	import org.testng.annotations.Test;

	import reusableFunctions.ClaimsSubmission;
	import reusableFunctions.Codification;
	import reusableFunctions.CollectSamples;
	import reusableFunctions.ConsultationAndMgmt;
	import reusableFunctions.DoctorScheduler;
	import reusableFunctions.LabPendingTests;
	import reusableFunctions.Login;
	import reusableFunctions.MRNoSearch;
	import reusableFunctions.OPListSearch;
	import reusableFunctions.OpenGenericForm;
	import reusableFunctions.Order;
	import reusableFunctions.PatientArrival;
	import reusableFunctions.PatientBill;
	import reusableFunctions.PatientEMR;
	import reusableFunctions.PatientIssue;
	import reusableFunctions.RadiologyPendingTests;
	import reusableFunctions.RaisePatientIndent;
	import reusableFunctions.Registration;
	import reusableFunctions.Sales;
	import reusableFunctions.SalesReturn;
	import reusableFunctions.ServicePending;
	import reusableFunctions.SiteNavigation;
	import reusableFunctions.TestsConduction;
	import reusableFunctions.Triage;
	import reusableFunctions.Upload;
import reusableFunctions.insurance.PatientBillInsuranceModule;
import reusableFunctions.insurance.PatientCategoryMaster;
import reusableFunctions.insurance.RegistrationModuleInsurance;
import reusableFunctions.insurance.SponsorTypeDetails;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
	import seleniumWebUIFunctions.KeywordSelectionLibrary;
	import seleniumWebUIFunctions.VerificationFunctions;

	/**
	 * @author Reena
	 *
	 */
	 public class INSClaimId{ 
		WebDriver driver = null;
		KeywordSelectionLibrary executeStep;
		KeywordExecutionLibrary initDriver;
		VerificationFunctions verifications;
		ReportingFunctions reporter;
		String AutomationID;
		String DataSet;
		
		//@BeforeSuite
		//public void BeforeSuite(){
		private void openBrowser(){
			System.out.println("BeforeSuite");
			AutomationID = "Login - INSClaimId";
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
		
		
		private void login(){
			AutomationID = "Login - INSClaimId";
			DataSet = "TS_005";
			
			
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
		
		@Test(priority=1)
		public void InsClaimIdIns(){
			openBrowser();
			login();
			DataSet = "TS_005";
			AutomationID = "INSClaimId";
			List<String> lineItemIDs = null;
			System.out.println("INSClaimId - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
            //Application level pre-requisites
			
			 SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	    	 navigation.navigateToSettings(driver, "TPA/SponsorMasterLink", "TPA/SponsorMasterPage");
			
	    	executeStep.performAction(SeleniumActions.Click, "","AddNewTPA/SponsorLink");
	 		verifications.verify(SeleniumVerifications.Appears, "","AddTPA/SponsorPage",false);
	    	 
	    	 SponsorTypeDetails addTPASponsor = new SponsorTypeDetails(executeStep, verifications);
	    	 addTPASponsor.addNewTPASponsor();
	    	 
	    	
	    	 //1.Go to OP Registration,enter all mandatory information,select Primary Sponsor check box 
	    	// and select tpa as Insurance.Select respective company,plan type and plan. Register the patient.
	    	 
	    	
	 		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
	 		RegistrationModuleInsurance OPReg = new RegistrationModuleInsurance(executeStep, verifications);
	 	    Upload uploadfile=new Upload(executeStep, verifications);
	 		
	 	       OPReg.RegisterPatientGenericDetails();
	 		   OPReg.GovtIDDetailsCollapsedPanel();
	 		   OPReg.setBillType();
	 	       OPReg.RegisterPatientInsurance("OP");
	 	       OPReg.uncheckSecondarySponsor();
	 		   String registrationupload="//input[@id='primary_insurance_doc_content_bytea1' and @type='file']";
	 	       uploadfile.upload(registrationupload);
	 	       OPReg.additionalPrimarySponsorDetails();
	 		   OPReg.visitInformationDetails();
	 		   	
	 		   OPReg.storeDetails();
	    	 
	 			navigation.navigateTo(driver, "OpenBills", "BillListPage");
				MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
				mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");
					
				executeStep.performAction(SeleniumActions.Click, "","BillListItemToClick");
			    verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
			    CommonUtilities.delay(15);
			    executeStep.performAction(SeleniumActions.Click, "","BillListPageEditClaimLink");
			    verifications.verify(SeleniumVerifications.Appears, "","EditClaimPage",false);
			    
			    
			    PatientBillInsuranceModule claimIDVerification = new PatientBillInsuranceModule(executeStep, verifications);
			    claimIDVerification.verifyClaimIdPresent();
	 		   
		}
		
		
	 			@Test(priority=2)
	 			public void INSClaimIdNat(){
	 				
	 				  DataSet = "TS_005b";
	 		 		  AutomationID = "INSClaimIdNat";
	 				List<String> lineItemIDs = null;
	 				System.out.println("INSClaimId - Before Navigation");
	 				
	 				executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
	 				verifications = new VerificationFunctions(driver, AutomationID, DataSet);
	 				
	 		   				
	            //Application level pre-requisites
				
				 SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		     navigation.navigateToSettings(driver, "TPA/SponsorMasterLink", "TPA/SponsorMasterPage");
				
		    	executeStep.performAction(SeleniumActions.Click, "","AddNewTPA/SponsorLink");
		 		verifications.verify(SeleniumVerifications.Appears, "","AddTPA/SponsorPage",false);
		    	 
		   	     SponsorTypeDetails addTPASponsor = new SponsorTypeDetails(executeStep, verifications);
		    	 addTPASponsor.addNewTPASponsor();
		    	
		    	
		    	 //1.Go to OP Registration,enter all mandatory information,select Primary Sponsor check box 
		    	// and select tpa as Insurance.Select respective company,plan type and plan. Register the patient.
		    	 
			//	SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		 		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		 		RegistrationModuleInsurance OPReg = new RegistrationModuleInsurance(executeStep, verifications);
		 	    Upload uploadfile=new Upload(executeStep, verifications);
		 		
		 	   OPReg.RegisterPatientGenericDetails();
	 		   OPReg.GovtIDDetailsCollapsedPanel();
	 		   OPReg.setBillType();
	 	       OPReg.RegisterPatientInsurance("OP");
	 	       OPReg.uncheckSecondarySponsor();
	 	       String registrationupload="//input[@id='primary_insurance_doc_content_bytea1' and @type='file']";
	 	       uploadfile.upload(registrationupload);
	 	       OPReg.additionalPrimarySponsorDetails();
	 		   OPReg.visitInformationDetails();
	 		   	
	 		   OPReg.storeDetails();
	    	 
		 		   
		 		   
		 			navigation.navigateTo(driver, "OpenBills", "BillListPage");
					MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
					mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");
						
					executeStep.performAction(SeleniumActions.Click, "","BillListItemToClick");
				    verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
				    
				    executeStep.performAction(SeleniumActions.Click, "","BillListPageEditClaimLink");
				    verifications.verify(SeleniumVerifications.Appears, "","EditClaimPage",false);
				    PatientBillInsuranceModule claimIDVerification = new PatientBillInsuranceModule(executeStep, verifications);
				    claimIDVerification.verifyClaimIdPresent();
		    	 
	 			}
		 			@Test(priority=3)
		 			public void INSClaimIdCorp(){
		 			openBrowser();
	 				login();
		 			DataSet = "TS_005c";
		 		 	AutomationID = "INSClaimIdCorp";
		 				List<String> lineItemIDs = null;
		 				System.out.println("INSClaimId - Before Navigation");
		 				
		 				executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		 				verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		 				
					
					
					executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
					verifications = new VerificationFunctions(driver, AutomationID, DataSet);
					
		            //Application level pre-requisites
					
					 SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			    	 navigation.navigateToSettings(driver, "TPA/SponsorMasterLink", "TPA/SponsorMasterPage");
					
			    	 executeStep.performAction(SeleniumActions.Click, "","AddNewTPA/SponsorLink");
			 		 verifications.verify(SeleniumVerifications.Appears, "","AddTPA/SponsorPage",false);
			    	 
		    	     SponsorTypeDetails addTPASponsor = new SponsorTypeDetails(executeStep, verifications);
			    	 addTPASponsor.addNewTPASponsor();
			    	 
			    	
			    	 //1.Go to OP Registration,enter all mandatory information,select Primary Sponsor check box 
			    	// and select tpa as Insurance.Select respective company,plan type and plan. Register the patient.
			    	 
			    	
			 		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
			 		RegistrationModuleInsurance OPReg = new RegistrationModuleInsurance(executeStep, verifications);
			 	    Upload uploadfile=new Upload(executeStep, verifications);
			 		
			 	   OPReg.RegisterPatientGenericDetails();
		 		   OPReg.GovtIDDetailsCollapsedPanel();
		 		   OPReg.setBillType();
		 	       OPReg.RegisterPatientInsurance("OP");
		 	       OPReg.uncheckSecondarySponsor();
		 	       String registrationupload="//input[@id='primary_insurance_doc_content_bytea1' and @type='file']";
		 	       uploadfile.upload(registrationupload);
		 	       OPReg.additionalPrimarySponsorDetails();
		 		   OPReg.visitInformationDetails();
		 		   	
		 		   OPReg.storeDetails();
		    	 
			    	 
			 		navigation.navigateTo(driver, "OpenBills", "BillListPage");
					MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
					mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");
						
					executeStep.performAction(SeleniumActions.Click, "","BillListItemToClick");
					CommonUtilities.delay();
				    verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
				    
				    executeStep.performAction(SeleniumActions.Click, "","BillListPageEditClaimLink");
				    verifications.verify(SeleniumVerifications.Appears, "","EditClaimPage",false);
				    PatientBillInsuranceModule claimIDVerification = new PatientBillInsuranceModule(executeStep, verifications);
				    claimIDVerification.verifyClaimIdPresent();
				    
				  	  	 
	}
		 		
		 			  
		 			
}
	 