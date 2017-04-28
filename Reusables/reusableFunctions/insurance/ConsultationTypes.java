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

public class ConsultationTypes {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public ConsultationTypes(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public ConsultationTypes(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void addConsultationTypes(){
		
		 executeStep.performAction(SeleniumActions.Click, "","AddConsultationTypeLink");
		 verifications.verify(SeleniumVerifications.Appears, "", "AddConsultationTypePage", false);
		
		
		  executeStep.performAction(SeleniumActions.Enter, "ConsultTypePageConsultName","AddConsultationTypePageConsultName");
		  verifications.verify(SeleniumVerifications.Entered, "ConsultTypePageConsultName","AddConsultationTypePageConsultName",false);
		
		
		  executeStep.performAction(SeleniumActions.Select, "ConsultTypePageDoctorChargeType","AddConsultationTypePageDoctorChargeType");
		  verifications.verify(SeleniumVerifications.Selected, "ConsultTypePageDoctorChargeType","AddConsultationTypePageDoctorChargeType",false);
			
		  executeStep.performAction(SeleniumActions.Select, "ConsultTypePageChargeHeadType","AddConsultationTypePageChargeHeadType");
		  verifications.verify(SeleniumVerifications.Selected, "ConsultTypePageChargeHeadType","AddConsultationTypePageChargeHeadType",false);
			
		  executeStep.performAction(SeleniumActions.Select, "ConsultTypePagePatientType","AddConsultationTypePagePatientType");
		  verifications.verify(SeleniumVerifications.Selected, "ConsultTypePagePatientType","AddConsultationTypePagePatientType",false);
		
		
		  executeStep.performAction(SeleniumActions.Select, "ConsultTypePageStatus","AddConsultationTypePageStatus");
		  verifications.verify(SeleniumVerifications.Selected, "ConsultTypePageStatus","AddConsultationTypePageStatus",false);
		  
		  executeStep.performAction(SeleniumActions.Select, "ConsultTypePageInsuranceCategory","AddConsultationTypePageInsuranceCategory");
		  verifications.verify(SeleniumVerifications.Selected, "ConsultTypePageInsuranceCategory","AddConsultationTypePageInsuranceCategory",false);
		  
		  executeStep.performAction(SeleniumActions.Select, "ConsultTypePageVisitConsultationType","AddConsultationTypePageVisitConsultationType");
		  verifications.verify(SeleniumVerifications.Selected, "ConsultTypePageVisitConsultationType","AddConsultationTypePageVisitConsultationType",false);
		  
	     
		 executeStep.performAction(SeleniumActions.Click, "","AddConsultationTypePageAllowRateIncrease");
		// verifications.verify(SeleniumVerifications.Appears, "", "ConsultationTypePage", false);
		
		 executeStep.performAction(SeleniumActions.Click, "","AddConsultationTypePageAllowRateDecrease");
		// verifications.verify(SeleniumVerifications.Appears, "", "ConsultationTypePage", false); // (radio button already in 'yes' by default hence commenting
		 
		 executeStep.performAction(SeleniumActions.Click, "","AddConsultationTypePageSave");
		 verifications.verify(SeleniumVerifications.Appears, "", "EditConsultationTypePage", false);
		
	}
	public void editConsultationCharges(String LineItemID){
	EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.lineItemCount = 0;           
		EnvironmentSetup.LineItemIdForExec = LineItemID;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		int rowCount=0;
		DbFunctions dbFunction = new DbFunctions();

		rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){			
			EnvironmentSetup.UseLineItem = true;
			
		  executeStep.performAction(SeleniumActions.Select, "ConsultationChargesRatesForRateSheet","ConsultationChargesPageRatesForRateSheet");
		  verifications.verify(SeleniumVerifications.Selected, "ConsultationChargesRatesForRateSheet","ConsultationChargesPageRatesForRateSheet",false);
		  executeStep.performAction(SeleniumActions.Select, "ConsultationChargesTreatmentCodeType","ConsultationChargesPageTreatmentCodeType");
		  verifications.verify(SeleniumVerifications.Selected, "ConsultationChargesTreatmentCodeType","ConsultationChargesPageTreatmentCodeType",false);
		  executeStep.performAction(SeleniumActions.Enter, "CodeMasterPageCodeField","ConsultationChargesPageItemCode");
		  verifications.verify(SeleniumVerifications.Entered, "CodeMasterPageCodeField","ConsultationChargesPageItemCode",false);
		  executeStep.performAction(SeleniumActions.Enter, "ConsultationChargesPageCharges","ConsultationChargesPageChargeField");
		  verifications.verify(SeleniumVerifications.Entered, "ConsultationChargesPageCharges","ConsultationChargesPageChargeField",false);
		  		  
		  executeStep.performAction(SeleniumActions.Click, "","ConsultationChargesPageApplyChargesToAll");
		  verifications.verify(SeleniumVerifications.Appears, "", "ConsultationChargesPage", false);
		  EnvironmentSetup.lineItemCount ++;
}
		EnvironmentSetup.lineItemCount =0;
}
}