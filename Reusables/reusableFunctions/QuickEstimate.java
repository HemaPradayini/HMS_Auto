package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class QuickEstimate {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public QuickEstimate() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public QuickEstimate(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void quickEstimateIPVisit(){

		quickEstimateForNewPatient();

		executeStep.performAction(SeleniumActions.Click, "","QuickEstimatesDirectEstimateIPVisitRadioBtn");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimatesRatePlanOPDropDown",false);
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimateBedTypeOP",false);
		
		executeStep.performAction(SeleniumActions.Select, "RatePlan","QuickEstimatesRatePlanOPDropDown");
		verifications.verify(SeleniumVerifications.Selected, "RatePlan","QuickEstimatesRatePlanOPDropDown",false);

		executeStep.performAction(SeleniumActions.Select, "BedType","QuickEstimateBedTypeOP");
		verifications.verify(SeleniumVerifications.Selected, "BedType","QuickEstimateBedTypeOP",false);
		
		/*addOrderItems();
		executeStep.performAction(SeleniumActions.Click, "","QuickEstimatesSaveBtn");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimatePage",false);
		
		saveQuickEstimateForExistingPatient();
		
		viewSavedEstimates();*/
			
	}
	
	public void quickEstimateForOPVisit(){
		
		quickEstimateForNewPatient();
		
		executeStep.performAction(SeleniumActions.Click, "","QuickEstimatesDirectEstimateOPVisitRadioBtn");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimatesRatePlanOPDropDown",false);

		executeStep.performAction(SeleniumActions.Click, "","QuickEstimatesRegisteredPatientRadioBtn");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimatePage",false);
		
		executeStep.performAction(SeleniumActions.Select, "RatePlan","QuickEstimatesRatePlanOPDropDown");
		verifications.verify(SeleniumVerifications.Selected, "RatePlan","QuickEstimatesRatePlanOPDropDown",false);

		addOrderItems();
		
		executeStep.performAction(SeleniumActions.Click, "","QuickEstimatesSaveBtn");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimatePage",false);
		
		saveQuickEstimateForExistingPatient();
		
		viewSavedEstimates();

	}

	private void quickEstimateForNewPatient(){
		executeStep.performAction(SeleniumActions.Click, "","QuickEstimateNewPatientRadioBtn");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimatePage",false);
		
		executeStep.performAction(SeleniumActions.Select, "Title","QuickEstimateSalutation");
		verifications.verify(SeleniumVerifications.Selected, "Title","QuickEstimateSalutation",false);
		
		executeStep.performAction(SeleniumActions.Enter, "PatientFirstName","QuickEstimateFirstName");
		verifications.verify(SeleniumVerifications.Entered, "PatientFirstName","QuickEstimateFirstName",false);
		
		executeStep.performAction(SeleniumActions.Enter, "Age","QuickEstimatePatientAge");
		verifications.verify(SeleniumVerifications.Entered, "Age","QuickEstimatePatientAge",false);
		
		executeStep.performAction(SeleniumActions.Select, "Gender","QuickEstimatePatientGender");
		verifications.verify(SeleniumVerifications.Selected, "Gender","QuickEstimatePatientGender",false);
		
		executeStep.performAction(SeleniumActions.Enter, "PatientMobileNumber","QuickEstimateMobileNo");
		verifications.verify(SeleniumVerifications.Entered, "PatientMobileNumber","QuickEstimateMobileNo",false);

		executeStep.performAction(SeleniumActions.Click, "","QuickEstimateDirectEstimateRadioBtn");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimatePage",false);
	}
	
	private void saveQuickEstimateForExistingPatient(){
		executeStep.performAction(SeleniumActions.Click, "","QuickEstimatesRegisteredPatientRadioBtn");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimatePage",false);

		executeStep.performAction(SeleniumActions.Enter, "MRID","QuickEstimatesMRNoField");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimateMRNoListBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","QuickEstimateMRNoListBox");
		verifications.verify(SeleniumVerifications.Entered, "MRID","QuickEstimatesMRNoField",false);
		
		//executeStep.performAction(SeleniumActions.Click, "","QuickEstimatesBillingConsiderationInsRadioBtn");
		//verifications.verify(SeleniumVerifications.Appears, "","QuickEstimatePage",false);
		
		addOrderItems();
				
		executeStep.performAction(SeleniumActions.Click, "","QuickEstimatesSaveBtn");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimatePage",false);		
	}
	
	private void viewSavedEstimates(){
		executeStep.performAction(SeleniumActions.Click, "","QuickEstimateSavedEstimatesLink");
		verifications.verify(SeleniumVerifications.Appears, "","SavedEsimatesResultsList",false);

		executeStep.performAction(SeleniumActions.Click, "","SavedEsimatesResultsList");
		verifications.verify(SeleniumVerifications.Appears, "","SavedEstimatesViewOrEditEstimationDetails",false);

		executeStep.performAction(SeleniumActions.Click, "","SavedEstimatesViewOrEditEstimationDetails");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimatePage",false);

	}
	private void addOrderItems(){
		executeStep.performAction(SeleniumActions.Click, "","QuickEstimatesAddItemBtn");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimateOrerDialogAddDialog",false);
		
		Order order = new Order(executeStep, verifications);
		order.addOrderItem("QuickEstimateOrderItems", "QuickEstimatePage");
		
		executeStep.performAction(SeleniumActions.Click, "","QuickEstimateEditChargeEditItemLink");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimateEditChargeDialog",false);

		executeStep.performAction(SeleniumActions.Enter, "eDiscount","QuickEstimateEditChargeDialogeDiscount");
		verifications.verify(SeleniumVerifications.Entered, "eDiscount","QuickEstimateEditChargeDialogeDiscount",false);
		
		executeStep.performAction(SeleniumActions.Click, "","QuickEstimateEditChargeDialogOKBtn");
		verifications.verify(SeleniumVerifications.Appears, "","QuickEstimatePage",false);		
		
	}
	
}
