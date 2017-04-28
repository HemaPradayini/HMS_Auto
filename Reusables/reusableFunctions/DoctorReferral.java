package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class DoctorReferral {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public DoctorReferral(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public DoctorReferral(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void ReferralDocPaymentsSave(){
		
		System.out.println("Inside Referral Doctor Payments Page SelectAndSave ");

		int rowCount = this.executeStep.getDriver().findElements(By.xpath("//div[@id='payment_content']//table[@id='payResultTable']//tr")).size();
		System.out.println("Row Count is :: " + rowCount);
		for(int i=1; i<=rowCount-1; i++){
			EnvironmentSetup.intGlobalLineItemCount = i+1;
			executeStep.performAction(SeleniumActions.Check, "","ReferralDoctorPayementRows");
			verifications.verify(SeleniumVerifications.Checked, "","ReferralDoctorPayementRows",false);
		}
		
		EnvironmentSetup.intGlobalLineItemCount = 0;
		executeStep.performAction(SeleniumActions.Click, "","ReferralDoctorPaymentsPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","ReferralDoctorPaymentsPage",true);
	    System.out.println("ReferralDoctorPayment Saved");

	}
	
	public void PrescribingDocPaymentsSave()
	{
		System.out.println("Inside Prescribing Doctor Payments Page SelectAndSave ");

		int rowCount = this.executeStep.getDriver().findElements(By.xpath("//div[@id='payment_content']//table[@id='payResultTable']//tr")).size();
		System.out.println("Row Count is :: " + rowCount);
		for(int i=1; i<=rowCount-1; i++){
			EnvironmentSetup.intGlobalLineItemCount = i+1;
			executeStep.performAction(SeleniumActions.Check, "","PrescribingDoctorPayementRows");
			verifications.verify(SeleniumVerifications.Checked, "","PrescribingDoctorPayementRows",false);
		}
		
		EnvironmentSetup.intGlobalLineItemCount = 0;
		executeStep.performAction(SeleniumActions.Click, "","ReferralDoctorPaymentsPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","PrescribingDoctorPaymentsPage",true);
	    System.out.println("Prescribing DoctorPayment Saved");
		
	}
	
	public void PaymentsDue(){
		executeStep.performAction(SeleniumActions.Enter, "PayeeName","PaymentVoucherDashboardPayeeName");
		verifications.verify(SeleniumVerifications.Appears, "","PaymentVoucherDashboardPayeeNameList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PaymentVoucherDashboardPayeeNameList");
		verifications.verify(SeleniumVerifications.Entered, "PayeeName","PaymentVoucherDashboardPayeeName",true);
		
		executeStep.performAction(SeleniumActions.Click, "","PaymentVoucherDashboardSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","PrescribingDoctorPaymentsPage",true);
		
		executeStep.performAction(SeleniumActions.Click, "","PaymentVoucherDashBoardPayeeSelection");
		executeStep.performAction(SeleniumActions.Click, "","PaymentVoucherDashboardPaymentVouchCreate");
		verifications.verify(SeleniumVerifications.Appears, "","PaymentVoucherPage",true);
		
		executeStep.performAction(SeleniumActions.Click, "","PaymentVoucherPageSaveAndPrintButton");
		
	}
	}
