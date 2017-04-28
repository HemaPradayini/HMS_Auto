/**
 * 
 */
package e2e;

import java.util.List;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DoctorReferral;
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
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Registration;
import reusableFunctions.Sales;
import reusableFunctions.SalesReturn;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Triage;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author Sai
 *
 */
public class PaymentWorkflowForPrescribingDoctor {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	
	@BeforeSuite
	public void BeforeSuite(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - PaymentWorkflowForPrescribingDoctor";
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
	
	@BeforeMethod
	public void BeforeTest(){
		AutomationID = "Login - PaymentWorkflowForPrescribingDoctor";
		DataSet = "TS_042";
		//EnvironmentSetup.testScenarioId = "TS_001";
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void PaymentWorkflowForPrescribingDoctor(){
		DataSet = "TS_042";
		AutomationID = "PaymentWorkflowForPrescribingDoctor";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test PaymentWorkflowForPrescribingDoctor - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		//From here its for Entering details on the OP Registration Screen - Move to another reusable
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientGenericDetails();
		OPReg.setBillType();
		executeStep.performAction(SeleniumActions.Click, "", "OPRegisterationPrimarySponsorCheckBox");
		executeStep.performAction(SeleniumActions.Select, "DeptName","OPRScreenDepartmentField");
		verifications.verify(SeleniumVerifications.Selected, "DeptName","OPRScreenDepartmentField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","OPRScreenRegisterButton");
		executeStep.performAction(SeleniumActions.Accept, "","Framework");/*
		
		verifications.verify(SeleniumVerifications.Appears, "","RegistrationSucccessScreen",true);
		
		executeStep.performAction(SeleniumActions.Store, "MRID","RegSuccessScreenHospitalIdField");
		executeStep.performAction(SeleniumActions.Store, "VisitID","RegSuccessScreenVisitIdField");	
		*/
		OPReg.storeDetails();
		navigation.navigateTo(driver, "LaboratoryOrderTestsLink", "LaboratoryOrderTestsPage");

		
		executeStep.performAction(SeleniumActions.Enter, "MRID","LaboratoryOrderTestsMRNoField");
		verifications.verify(SeleniumVerifications.Appears, "","LaboratoryOrderTestsMRNoList",false);
		executeStep.performAction(SeleniumActions.Click, "","LaboratoryOrderTestsMRNoList");
		verifications.verify(SeleniumVerifications.Entered, "MRID","LaboratoryOrderTestsMRNoField",true);
		executeStep.performAction(SeleniumActions.Click, "","LaboratoryOrderTestsMRNoFindButton");
		verifications.verify(SeleniumVerifications.Appears, "","LaboratoryOrderTestsPage",false);
		
		Order order = new Order(executeStep, verifications);
		order.addOrderPrescribingDoc();
		
		
	executeStep.performAction(SeleniumActions.Click, "","LaboratoryOrderTestsBillLink");
	verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
	executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");

			
	PatientBill patientBill=new PatientBill(executeStep, verifications);
//	patientBill.savePatientBill();
	
	executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
	executeStep.performAction(SeleniumActions.Click, "","PrescribingDoctorPaymentsLink");
	verifications.verify(SeleniumVerifications.Appears, "","PrescribingDoctorPaymentsPage",false);
	

	executeStep.performAction(SeleniumActions.Enter, "MRID","ReferralDoctorPaymentsPageMRNo");
	//verifications.verify(SeleniumVerifications.Appears, "","ReferralDoctorPaymentsPageDropdownMRNO",True);
	//executeStep.performAction(SeleniumActions.Click, "","ReferralDoctorPaymentsPageDropdownMRNO");
	verifications.verify(SeleniumVerifications.Entered, "MRID","ReferralDoctorPaymentsPageMRNo",false);
	

	executeStep.performAction(SeleniumActions.Enter, "PrescribingDoctor","PrescribingDoctorPaymentsPrescribingDoctor");
	verifications.verify(SeleniumVerifications.Appears, "","PrescribingDoctorPaymentsPrescribingDoctorList",false);
	
	executeStep.performAction(SeleniumActions.Click, "","PrescribingDoctorPaymentsPrescribingDoctorList");
    verifications.verify(SeleniumVerifications.Entered, "PrescribingDoctor","PrescribingDoctorPaymentsPrescribingDoctor",false);
	
	
	
	executeStep.performAction(SeleniumActions.Click, "","ReferralDoctorPaymentsPageSearch");
	DoctorReferral DocReferral=new DoctorReferral(executeStep, verifications);
	DocReferral.PrescribingDocPaymentsSave();

	navigation.navigateTo(driver, "PaymentsDueLink", "PaymentVoucherDashboardScreen");
	DocReferral.PaymentsDue();
	
	}
}

	