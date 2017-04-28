package reusableFunctions.insurance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import GenericFunctions.CommonUtilities;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.SiteNavigation;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PatientCategoryMaster {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public PatientCategoryMaster(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PatientCategoryMaster(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	
	public void editPatientCategoryMaster(){

    	executeStep.performAction(SeleniumActions.Enter, "CategoryMasterCategoryName","PatientCategoryMasterCategoryName");
 		verifications.verify(SeleniumVerifications.Entered, "CategoryMasterCategoryName","PatientCategoryMasterCategoryName",false);
 		
 		executeStep.performAction(SeleniumActions.Click, "","PatientCategoryMasterSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientCategoryMasterPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PatientCategoryMasterResultTable");
	    verifications.verify(SeleniumVerifications.Appears, "","PatientCategoryViewEditMenu",false);
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "","PatientCategoryViewEditMenu");
		verifications.verify(SeleniumVerifications.Appears, "","EditPatientCategoryDetailsPage",false);
		
		
	//Set OP Default Primary Sponsor and OP Default Primary Insurance Company as "Daman" 
	//and OP Default Secondary Sponsor and OP Default Secondary Insurance Company as "Axa"
		
	
		executeStep.performAction(SeleniumActions.Select, "OPDefaultPrimarySponsor","OPDefaultPrimarySponsorSelect");
		verifications.verify(SeleniumVerifications.Selected, "OPDefaultPrimarySponsor","OPDefaultPrimarySponsorSelect",false);
		
		executeStep.performAction(SeleniumActions.Select, "OPDefaultPrimaryInsuranceCompany","OPDefaultPrimaryInsuranceCompanySelect");
		verifications.verify(SeleniumVerifications.Selected, "OPDefaultPrimaryInsuranceCompany","OPDefaultPrimaryInsuranceCompanySelect",false);
		
		
		
		executeStep.performAction(SeleniumActions.Select, "OPDefaultSecondarySponsor","OPDefaultSecondarySponsorSelect");
		verifications.verify(SeleniumVerifications.Selected, "OPDefaultSecondarySponsor","OPDefaultSecondarySponsorSelect",false);
		
				
		executeStep.performAction(SeleniumActions.Select, "OPDefaultSecondaryInsuranceCompany","OPDefaultSecondaryInsuranceCompanySelect");
		verifications.verify(SeleniumVerifications.Selected, "OPDefaultSecondaryInsuranceCompany","OPDefaultSecondaryInsuranceCompanySelect",false);
		
		executeStep.performAction(SeleniumActions.Click, "","EditPatientCategoryDetailsSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditPatientCategoryDetailsPage",false);
		
		
	}		
	
	
	
	
}
