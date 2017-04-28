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
import reusableFunctions.insurance.AddNewTest;
import reusableFunctions.insurance.BillVerification;
import reusableFunctions.insurance.CodeMaster;
import reusableFunctions.insurance.ConsultationTypes;
import reusableFunctions.insurance.Doctors;
import reusableFunctions.insurance.Equipment;
import reusableFunctions.insurance.OrderModuleInsurance;
import reusableFunctions.insurance.OtherCharges;
import reusableFunctions.insurance.PackageElements;
import reusableFunctions.insurance.PatientCategoryMaster;
import reusableFunctions.insurance.PlanMaster;
import reusableFunctions.insurance.RateSheets;
import reusableFunctions.insurance.RegistrationModuleInsurance;
import reusableFunctions.insurance.ServiceDefinition;
import reusableFunctions.insurance.SponsorTypeDetails;
import reusableFunctions.insurance.TestCharges;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
	import seleniumWebUIFunctions.KeywordSelectionLibrary;
	import seleniumWebUIFunctions.VerificationFunctions;

	/**
	 * @author Reena
	 *
	 */
	 public class INSVisitCopayPercentIncludeFollowUpChecked{ 
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
			AutomationID = "Login - INSVisitCopayPercentIncludeFollowUpChecked";
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
			AutomationID = "Login - INSVisitCopayPercentIncludeFollowUpChecked";
			DataSet = "TS_009";
			
			
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
		
		
		/*@Test(priority=1)
		public void INSVisitCopayPercentIncludeFollowUpCheckedWithNewRateSheet(){
			openBrowser();
			login();
			DataSet = "TS_009a";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpCheckedWithNewRateSheet";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpChecked - Before Navigation");
		
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			
			//CREATING RATE SHEETS
			navigation.navigateToSettings(driver, "RateSheetsLink", "RateSheetsPage");
			
			RateSheets addNewRateSheet = new RateSheets(executeStep, verifications);
			addNewRateSheet.searchNewRateSheet("RateSheet");
			System.out.println("RateSheet Settings Completed");
		}
		
		@Test(priority=2)
		public void INSVisitCopayPercentIncludeFollowUpCheckedWithExisitngRateSheet(){
		//	openBrowser();
		//	login();
			DataSet = "TS_009b";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpCheckedWithExisitngRateSheet";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpCheckedWithExisitngRateSheet - Before Navigation");
		
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			
			//CREATING RATE SHEETS
			navigation.navigateToSettings(driver, "RateSheetsLink", "RateSheetsPage");
			RateSheets editRateSheet = new RateSheets(executeStep, verifications);
			editRateSheet.searchExisitingRateSheet("ExistingRateSheet");
			System.out.println("ExistingRateSheet Settings Completed");
		}
		
		
		@Test(priority=3)
		public void INSVisitCopayPercentIncludeFollowUpCheckedNewDoctor(){
		//	openBrowser();
		//	login();
			DataSet = "TS_009c";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpCheckedNewDoctor";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpCheckedDoctorCharges - Before Navigation");
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
			//STEPS TO CREATE DOCTOR
			navigation.navigateToSettings(driver, "DoctorsLink", "AvailableDoctorsPage");
			executeStep.performAction(SeleniumActions.Click, "", "AddNewDoctorLink");
			verifications.verify(SeleniumVerifications.Appears, "", "AddNewDoctorPage", false);
			     
			Doctors addnewdoctor = new Doctors(executeStep, verifications);
			addnewdoctor.addNewDoctor();
			System.out.println("DoctorCreation Settings Completed");
			
		}
			//Defining Charges for Doctor
		@Test(priority=4)
		public void INSVisitCopayPercentIncludeFollowUpCheckedDoctorCharges(){
		//	openBrowser();
		//	login();
			DataSet = "TS_009c";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpCheckedNewDoctor";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpCheckedDoctorCharges - Before Navigation");
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateToSettings(driver, "DoctorsLink", "AvailableDoctorsPage");
			Doctors addnewdoctor = new Doctors(executeStep, verifications);
			addnewdoctor.searchDoctor();
			addnewdoctor.editDoctorCharges("DoctorCharges");
			System.out.println("DoctorCharges Settings Completed");
		}	*/
			
		
		//STEPS TO CREATE CONSULTATION TYPE
		
		
		@Test(priority=5)
		public void INSVisitCopayPercentIncludeFollowUpCheckedConsultType(){
			openBrowser();
			login();
			DataSet = "TS_009d";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpCheckedConsultType";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpCheckedConsultType - Before Navigation");
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			
			navigation.navigateToSettings(driver, "CodeMasterLink", "CodeMasterPage");
			CodeMaster storeCodes = new CodeMaster(executeStep, verifications);
			storeCodes.storecodesCodeMaster("CodeField");
	    
			
			
			
		     navigation.navigateToSettings(driver, "ConsultationTypeLink", "ConsultationTypePage");
			ConsultationTypes addConsultationType = new ConsultationTypes(executeStep, verifications);
			addConsultationType.addConsultationTypes();
			
			executeStep.performAction(SeleniumActions.Click, "","EditConsultationTypePageEditChargesLink");
			verifications.verify(SeleniumVerifications.Appears, "", "ConsultationChargesPage", false);
			
			addConsultationType.editConsultationCharges("CodeField");
			System.out.println("ConsultType Settings Completed");			
		}	
	
	@Test(priority=6)
		public void INSVisitCopayPercentIncludeFollowUpCheckedRevisitConsultType(){
		//	openBrowser();
		//	login();
			DataSet = "TS_009e";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpCheckedRevisitConsultType";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpCheckedRevisitConsultType - Before Navigation");
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			
			navigation.navigateToSettings(driver, "CodeMasterLink", "CodeMasterPage");
			CodeMaster storeCodes = new CodeMaster(executeStep, verifications);
			storeCodes.storecodesRevisit("RevisitCode");
	    
			
		   navigation.navigateToSettings(driver, "ConsultationTypeLink", "ConsultationTypePage");
			ConsultationTypes addConsultationType = new ConsultationTypes(executeStep, verifications);
			addConsultationType.addConsultationTypes();
			
			executeStep.performAction(SeleniumActions.Click, "","EditConsultationTypePageEditChargesLink");
			verifications.verify(SeleniumVerifications.Appears, "", "ConsultationChargesPage", false);
			
			addConsultationType.editConsultationCharges("RevisitCode");
			System.out.println("Revisit ConsultType Settings Completed");				
		}	
		
		
		
		
		@Test(priority=7)
		public void INSVisitCopayPercentIncludeFollowUpCheckedEquipment(){
		//	openBrowser();
		//	login();
			DataSet = "TS_009f";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpCheckedEquipment";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpCheckedEquipment - Before Navigation");
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			
			navigation.navigateToSettings(driver, "EquipmentsLink", "EquipmentMasterPage");
			Equipment addEquipment = new Equipment(executeStep, verifications);
			addEquipment.addNewEquipment();
			addEquipment.editEquipment("EquipmentCharges");
			System.out.println("Equipment Settings Completed");	
				   
		}	
		
		@Test(priority=8)
		public void INSVisitCopayPercentIncludeFollowUpCheckedLaboratoryTest(){
		//	openBrowser();
		//	login();
			DataSet = "TS_009g";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpCheckedLaboratoryTest";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpCheckedLaboratoryTest - Before Navigation");
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateToSettings(driver, "CodeMasterLink", "CodeMasterPage");
			CodeMaster storeCodes = new CodeMaster(executeStep, verifications);
			storeCodes.storecodesLaboratory("CodesForLab");
			navigation.navigateToSettings(driver, "DiagnosticTestsLink", "DiagnosticTestListPage");
			
			AddNewTest addTest = new AddNewTest(executeStep, verifications);
			addTest.AddNewTestForLab();
			executeStep.performAction(SeleniumActions.Click, "", "AddNewTestPageEditCharges");
			verifications.verify(SeleniumVerifications.Appears, "", "TestChargesPage", false);
			TestCharges testcharges = new TestCharges(executeStep, verifications);
			testcharges.editTestCharges("CodesForLab");
			System.out.println("Laboratory Settings Completed");	
			   
		}	
		
		
		@Test(priority=9)
		public void INSVisitCopayPercentIncludeFollowUpCheckedRadiologyTest(){
			//openBrowser();
			//login();
			DataSet = "TS_009h";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpCheckedDoctorCharges";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpCheckedRadiologyTest - Before Navigation");
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateToSettings(driver, "CodeMasterLink", "CodeMasterPage");
			CodeMaster storeCodes = new CodeMaster(executeStep, verifications);
			storeCodes.storecodesRadiology("CodesForRadio");
			navigation.navigateToSettings(driver, "DiagnosticTestsLink", "DiagnosticTestListPage");
			
			AddNewTest addTest = new AddNewTest(executeStep, verifications);
			addTest.AddNewTestForRadiology();
			executeStep.performAction(SeleniumActions.Click, "", "AddNewTestPageEditCharges");
			verifications.verify(SeleniumVerifications.Appears, "", "TestChargesPage", false);
			TestCharges testcharges = new TestCharges(executeStep, verifications);
			testcharges.editTestCharges("CodesForRadio");
			System.out.println("Radiology Settings Completed");		   
		}	
	
		
		@Test(priority=10)
		public void INSVisitCopayPercentIncludeFollowUpCheckedPackage(){
			//openBrowser();
			//login();
			DataSet = "TS_009i";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpCheckedPackage";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpCheckedPackage - Before Navigation");
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
			navigation.navigateToSettings(driver, "CodeMasterLink", "CodeMasterPage");
			CodeMaster storeCodes = new CodeMaster(executeStep, verifications);
			storeCodes.storecodesPackages("CodesForPackages");
			
			navigation.navigateToSettings(driver, "PackagesLink", "PackageListPage");
			
			executeStep.performAction(SeleniumActions.Click, "", "PackageListPageAddOPPackage");
			verifications.verify(SeleniumVerifications.Appears, "", "PackageElementsPage", false);
			
			PackageElements AddPackageElements = new PackageElements(executeStep, verifications);
			AddPackageElements.AddPackageElements();
			
			PackageElements editPackageElements = new PackageElements(executeStep, verifications);
			editPackageElements.editPackageCharges("CodesForPackages");
			System.out.println("Package Settings Completed");	
		}
			
		@Test(priority=11)
		public void INSVisitCopayPercentIncludeFollowUpCheckedService(){
			//openBrowser();
			//login();
			DataSet = "TS_009j";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpCheckedService";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpCheckedService - Before Navigation");
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
			navigation.navigateToSettings(driver, "CodeMasterLink", "CodeMasterPage");
			CodeMaster storeCodes = new CodeMaster(executeStep, verifications);
			storeCodes.storecodesService("CodesForService");
			
			navigation.navigateToSettings(driver, "ServicesLink", "ServicesPage");
			
			executeStep.performAction(SeleniumActions.Click, "", "ServicesPageAddNewServices");
			verifications.verify(SeleniumVerifications.Appears, "", "ServiceDefinitionPage", false);
			
			ServiceDefinition addservice = new ServiceDefinition(executeStep, verifications);
			addservice.addServiceDefinition();
			
			
			
			ServiceDefinition editServiceCharges = new ServiceDefinition(executeStep, verifications);
			editServiceCharges.editServiceCharges("CodesForService");
			System.out.println("Service Settings Completed");	
		}
		@Test(priority=12)
		public void INSVisitCopayPercentIncludeFollowUpCheckedOtherCharge(){
		//	openBrowser();
		//	login();
			DataSet = "TS_009k";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpCheckedOtherCharge";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpCheckedOtherCharge - Before Navigation");
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
			
			navigation.navigateToSettings(driver, "OtherChargesLink", "OtherChargesPage");
			
			executeStep.performAction(SeleniumActions.Click, "", "OtherChargesPageAddNewCharge");
			verifications.verify(SeleniumVerifications.Appears, "", "AddChargePage", false);
			
			OtherCharges addOtherCharge = new OtherCharges(executeStep, verifications);
			addOtherCharge.addOtherCharges();
			System.out.println("OtherCharge Settings Completed");	
			
		}
		
		@Test(priority=13)
		public void INSVisitCopayPercentIncludeFollowUpCheckedInsPlan(){
			//openBrowser();
			//login();
			DataSet = "TS_009l";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpCheckedInsPlan";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpCheckedInsPlan - Before Navigation");
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
			
			navigation.navigateToSettings(driver, "InsurancePlanLink", "PlanMasterPage");
			
			 executeStep.performAction(SeleniumActions.Click, "", "PlanMasterAddNewPlanLink");
			 verifications.verify(SeleniumVerifications.Appears, "", "AddPlanDetailsPage", false);
			 PlanMaster planMaster = new PlanMaster(executeStep, verifications);
			 planMaster.editPlanMaster();		    	 
		     planMaster.editCoPayandLimitsOP();
		     executeStep.performAction(SeleniumActions.Click, "","AddPlanDetailsPageSaveButton");
		     verifications.verify(SeleniumVerifications.Appears, "", "EditPlanDetailsPage", false);
		 	System.out.println("InsurancePlan Settings Completed");	
		}
		
		
		
		
		
	
		
		@Test(priority=14)
		public void INSVisitCopayPercentIncludeFollowUpChecked(){
			//openBrowser();
			//login();
			DataSet = "TS_009";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpChecked";
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
			  	 
		//Go to OP Registration screen,select primary sponsor check box,enter Daman tpa,daman insurance company,daman plan type and then select insurance plan as 'Daman Plan'. 
			  	
	    	navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
	    	RegistrationModuleInsurance OPReg = new RegistrationModuleInsurance(executeStep, verifications);
	    	OPReg.RegisterPatientGenericDetails();
			OPReg.setBillType();
			OPReg.GovtIDDetailsCollapsedPanel();
			OPReg.visitInformationDetails();
			OPReg.RegisterPatientInsurance("OP");
			OPReg.additionalPrimarySponsorDetails(); //need to check if policy number/holder name needs to be taken
			OPReg.storeDetails();
			
		//Go to bill and click on plus icon. Add few billable items and Save .
			navigation.navigateTo(driver, "OpenBills", "BillListPage");
			MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
			mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");
			
			PatientBill patientBill = new PatientBill(executeStep, verifications);
			executeStep.performAction(SeleniumActions.Click, "","BillListItemToClick");
			CommonUtilities.delay();
		    verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
		    CommonUtilities.delay();
		    executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
		    CommonUtilities.delay(10);
	        verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
	        executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
			patientBill.addItemsIntoPatientBill();
			/*verifications.verify(SeleniumVerifications.Opens, "","PatientBillErrorPage",true);
			executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillErrorPage");
			verifications.verify(SeleniumVerifications.Closes, "","PatientBillErrorPage",true);
		*/
			 
			
			
			
		//	2(b)Observe Billed Amount,Discounts,Net Amount,Patient Amount,Patient due,Sponsor Amount and Sponsor Due under Totals section of Bill1
			
			
			    navigation.navigateTo(driver, "OpenBills", "BillListPage");
	//		   	MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
		//		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");
					
				//   PatientBill patientBill = new PatientBill(executeStep, verifications);
					executeStep.performAction(SeleniumActions.Click, "","BillListItemToClick");
					CommonUtilities.delay();
				    verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
				    CommonUtilities.delay();
				    executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
				    CommonUtilities.delay(10);
			        verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
			        executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
			        BillVerification billdetails = new BillVerification(executeStep, verifications);
			        billdetails.toVerifyBillDetails("BillVerify");
					
			 //3)Now click on Order link in bill.
			//4)Select bill type as New Bill Insurance. Order following items and save the order screen :
			
			
			        executeStep.performAction(SeleniumActions.Click, "","BillPageOrderLink");
					verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
					executeStep.performAction(SeleniumActions.Accept, "","Framework");
					verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",false);
					
					
					OrderModuleInsurance order = new OrderModuleInsurance(executeStep, verifications);
					order.addOrderInsurance("OrderItem");
				

//Click on the bill number link(new bill which is created say Bill2) in order screen 
//4(b)Observe Billed Amount,Discounts,Net Amount,Patient Amount,Patient due,Sponsor Amount and Sponsor Due under Totals section in Bill2
			
			
			
					executeStep.performAction(SeleniumActions.Click, "","OrderPageNewBillLink");
					verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
					executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
			
					billdetails.toVerifyBillDetails("BillSecondVerify");
			
			
		
			
			
			// Go back to OP Registration screen, select MR No/Name/Phone radio button and enter the 
		//mrno. Once the patient details are auto filled,select consultation type and  click on register&edit button
				navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
			//	RegistrationModuleInsurance OPReg = new RegistrationModuleInsurance(executeStep, verifications);
				OPReg.RegistrationFollowUpChecked();
				
				//Go to bill and click on plus icon. Add  few billable items and Save the bill.
				navigation.navigateTo(driver, "OpenBills", "BillListPage");
		//		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
				mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");
		//	    PatientBill patientBill = new PatientBill(executeStep, verifications);
				executeStep.performAction(SeleniumActions.Click, "","BillListItemToClick");
				CommonUtilities.delay();
			    verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
			    CommonUtilities.delay();
			    executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
			    CommonUtilities.delay(10);
		        verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
		        executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
				patientBill.addItemsIntoPatientBill();
				
			//	7)Observe Billed Amount,Discounts,Net Amount,Patient Amount,Patient due,Sponsor Amount and Sponsor Due under Totals section		
				navigation.navigateTo(driver, "OpenBills", "BillListPage");
				//	MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
					mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");
					
			//		PatientBill patientBill = new PatientBill(executeStep, verifications);
					executeStep.performAction(SeleniumActions.Click, "","BillListItemToClick");
					CommonUtilities.delay();
				    verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
				    CommonUtilities.delay();
				    executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
				    CommonUtilities.delay(10);
			        verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
			        executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
			 //       BillVerification billdetails = new BillVerification(executeStep, verifications);
			        billdetails.toVerifyBillDetails("BillThirdVerify");
			     	System.out.println("INSVisitCopayPercentIncludeFollowUpChecked Completed");	
				
				}

}