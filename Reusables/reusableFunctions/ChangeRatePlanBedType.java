package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class ChangeRatePlanBedType {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public ChangeRatePlanBedType(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public ChangeRatePlanBedType(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void changeAndSaveRatePlanAndBedType(){
		
		System.out.println("Change Rate Plan Bed Type");
		
		executeStep.performAction(SeleniumActions.Click, "","ChangeRateBedPlanLink");
		verifications.verify(SeleniumVerifications.Appears, "","ChangePlanHeader",false);	
				
		executeStep.performAction(SeleniumActions.Select, "UpdateCharges","UpdateChangesDropDown");
		verifications.verify(SeleniumVerifications.Selected, "UpdateCharges","UpdateChangesDropDown",false);	
		
		executeStep.performAction(SeleniumActions.Select, "ChangeVisitRatePlanTo","ChangeVisitRatePlanToDropDown");
		verifications.verify(SeleniumVerifications.Selected, "ChangeVisitRatePlanTo","ChangeVisitRatePlanToDropDown",false);
		
		executeStep.performAction(SeleniumActions.Select, "ChangeBedTypeTo","ChangeBedTypeTo");
		verifications.verify(SeleniumVerifications.Selected, "ChangeBedTypeTo","ChangeBedTypeTo",false);
		
		executeStep.performAction(SeleniumActions.Enter, "FromDate","ChangePlanFromDate");
		verifications.verify(SeleniumVerifications.Entered, "FromDate","ChangePlanFromDate",true);
		
		executeStep.performAction(SeleniumActions.Enter, "FromTime","ChangePlanToDate");
		verifications.verify(SeleniumVerifications.Entered, "FromTime","ChangePlanToDate",true);
		
		executeStep.performAction(SeleniumActions.Enter, "ToDate","ChangePlanFromTime");
		verifications.verify(SeleniumVerifications.Entered, "ToDate","ChangePlanFromTime",true);

		executeStep.performAction(SeleniumActions.Enter, "ToTime","ChangePlanToTime");
		verifications.verify(SeleniumVerifications.Entered, "ToTime","ChangePlanToTime",true);
		
		executeStep.performAction(SeleniumActions.Click, "","ChangePlanSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","ChangePlanHeader",false);

	}
	
}
