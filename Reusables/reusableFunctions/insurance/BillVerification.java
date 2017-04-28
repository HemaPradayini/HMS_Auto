package reusableFunctions.insurance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.MRNoSearch;
import reusableFunctions.SiteNavigation;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class BillVerification {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public BillVerification(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public BillVerification(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void toVerifyBillDetails(String lineItemId){
		
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.lineItemCount = 0;           
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		int rowCount=0;
		DbFunctions dbFunction = new DbFunctions();

		rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		
		for(int i=0; i<=rowCount; i++){			
			EnvironmentSetup.UseLineItem = true;
			EnvironmentSetup.LineItemIdForExec = lineItemId;
			
		
			verifications.verify(SeleniumVerifications.Appears, "PatientBillPgBilledAmount", "PatientBillBilledAmount", false);
			
			
			verifications.verify(SeleniumVerifications.Appears, "PatientBillPgDiscounts", "PatientBillDiscounts", false);
		
			verifications.verify(SeleniumVerifications.Appears, "PatientBillPgNetAmount", "PatientBillNetAmountVerify", false);
			
			verifications.verify(SeleniumVerifications.Appears, "PatientBillPgPatientAmount", "PatientBillPatientAmount", false);
			
			verifications.verify(SeleniumVerifications.Appears, "PatientBillPgPatientDue", "PatientBillPatientDue", false);
			
			verifications.verify(SeleniumVerifications.Appears, "PatientBillPgSponsorAmount", "PatientBillSponsorAmount", false);
			
			verifications.verify(SeleniumVerifications.Appears, "PatientBillPgSponsorDue", "PatientBillSponsorDueVerify", false);
			EnvironmentSetup.lineItemCount ++;
		}
		
       	EnvironmentSetup.UseLineItem = false; 
	}	
		
}
	
		
		