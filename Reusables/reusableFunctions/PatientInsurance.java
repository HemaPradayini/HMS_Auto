package reusableFunctions;

/**
 * Author: Tejaswini Changed on 27th Feb 2017
 * 
 */
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

public class PatientInsurance {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	
	public PatientInsurance(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PatientInsurance(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void savePatientInsuranceDetails(){
		
		System.out.println("Save Patient Insurance Details Entered");
		
		executeStep.performAction(SeleniumActions.Check, "", "AddEditPatientInsPrimarySponsorChkBox");
		verifications.verify(SeleniumVerifications.Checked, "", "AddEditPatientInsPrimarySponsorChkBox", false);
		
		executeStep.performAction(SeleniumActions.Enter, "PrimarySponsorName", "AddEditPatientInsPrimarySponsorName");
		verifications.verify(SeleniumVerifications.Appears, "PrimarySponsorName", "AddEditPatientInsPrimarySponsorNameList", false);

		executeStep.performAction(SeleniumActions.Click, "PrimarySponsorName", "AddEditPatientInsPrimarySponsorNameList");
		verifications.verify(SeleniumVerifications.Entered, "PrimarySponsorName", "AddEditPatientInsPrimarySponsorName", false);

		executeStep.performAction(SeleniumActions.Select, "PrimaryInsuranceCo", "AddEditPatientInsInsuranceCo");
		verifications.verify(SeleniumVerifications.Selected, "PrimaryInsuranceCo", "AddEditPatientInsInsuranceCo", false);
		
		executeStep.performAction(SeleniumActions.Select, "InsurancePlanType", "AddEditPatientInsNetworkPlanType");
		verifications.verify(SeleniumVerifications.Selected, "InsurancePlanType", "AddEditPatientInsNetworkPlanType", false);

		executeStep.performAction(SeleniumActions.Select, "InsurancePlanName", "AddEditPatientInsPlanName");
		verifications.verify(SeleniumVerifications.Selected, "InsurancePlanName", "AddEditPatientInsPlanName", false);

		executeStep.performAction(SeleniumActions.Enter, "PrimarySponsorMemberId", "AddEditPatientInsMemberId");
		verifications.verify(SeleniumVerifications.Entered, "PrimarySponsorMemberId", "AddEditPatientInsMemberId", false);
		
		executeStep.performAction(SeleniumActions.Click, "", "AddEditPatientInsSaveBtn");
		verifications.verify(SeleniumVerifications.Appears, "", "AddEditPatientInsurancePage", false);
		
		executeStep.performAction(SeleniumActions.Click, "", "AddEditPatientInsBackToBillLink");
		verifications.verify(SeleniumVerifications.Appears, "", "PatientBillScreen", false);
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		
	}
	
	}
