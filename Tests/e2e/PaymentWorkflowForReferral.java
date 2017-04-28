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
public class PaymentWorkflowForReferral {
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
		AutomationID = "Login - PaymentWorkflowForReferral";
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
		AutomationID = "Login - PaymentWorkflowForReferral";
		DataSet = "TS_043";
				
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
	public void PaymentWorkflowForReferral(){
		DataSet = "TS_043";
		AutomationID = "PaymentWorkflowForReferral";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test PaymentWorkflowForReferral - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		//From here its for Entering details on the OP Registration Screen - Move to another reusable
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientGenericDetails();
		OPReg.setBillType();
		OPReg.RegisterPatientReferralDoctor();
		OPReg.storeDetails();
		
		executeStep.performAction(SeleniumActions.Click, "","OPRegistrationSuccessBillingLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
		
	
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");

			
	PatientBill patientBill=new PatientBill(executeStep, verifications);
//	patientBill.savePatientBill();
	

	navigation.navigateTo(driver, "ReferralDoctorPaymentsLink", "ReferralDoctorPaymentsPage");

	executeStep.performAction(SeleniumActions.Enter, "MRID","ReferralDoctorPaymentsPageMRNo");
	verifications.verify(SeleniumVerifications.Appears, "","ReferralDoctorPaymentsPageDropdownMRNO",false);
	executeStep.performAction(SeleniumActions.Click, "","ReferralDoctorPaymentsPageDropdownMRNO");
	verifications.verify(SeleniumVerifications.Entered, "MRID","ReferralDoctorPaymentsPageMRNo",true);
	
	

	executeStep.performAction(SeleniumActions.Enter, "ReferralDoctor","ReferralDoctorPaymentsPageReferralDoctor");
	verifications.verify(SeleniumVerifications.Appears, "","ReferralDoctorPaymentsPageReferralDoctorDropdown",false);
	executeStep.performAction(SeleniumActions.Click, "","ReferralDoctorPaymentsPageReferralDoctorDropdown");
    verifications.verify(SeleniumVerifications.Entered, "ReferralDoctor","ReferralDoctorPaymentsPageReferralDoctor",true);
	

	
	executeStep.performAction(SeleniumActions.Click, "","ReferralDoctorPaymentsPageSearch");
//	verifications.verify(SeleniumVerifications.Appears, "","ReferralDoctorPaymentsPage",false);
	
	DoctorReferral DocReferral=new DoctorReferral(executeStep, verifications);
	DocReferral.ReferralDocPaymentsSave();
	
	navigation.navigateTo(driver, "PaymentsDueLink", "PaymentVoucherDashboardScreen");
	
	DocReferral.PaymentsDue();
	
	
	}
}

	