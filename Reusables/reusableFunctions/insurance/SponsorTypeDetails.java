package reusableFunctions.insurance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.SiteNavigation;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class SponsorTypeDetails {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public SponsorTypeDetails(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public SponsorTypeDetails(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	
	public void editSponsorTypeDetails(){
					
	
   	 
   	 //2) Click on Insurance Company sponsor type and then on View/Edit option
		
		
    executeStep.performAction(SeleniumActions.Enter, "TPA/SponsorType","SponsorTypeName");
    verifications.verify(SeleniumVerifications.Appears, "","SponsorTypeNameList",false);

    executeStep.performAction(SeleniumActions.Click, "","SponsorTypeNameList");
    verifications.verify(SeleniumVerifications.Entered, "TPA/SponsorType","SponsorTypeName",false);

    executeStep.performAction(SeleniumActions.Click, "","SponsorTypeSearchButton");
    verifications.verify(SeleniumVerifications.Appears, "","SponsorTypeResultTable",false);

    executeStep.performAction(SeleniumActions.Click, "","SponsorTypeResultTable");
 	verifications.verify(SeleniumVerifications.Appears, "", "SponsorTypeViewEditMenu", false);
 	CommonUtilities.delay(10);
   	executeStep.performAction(SeleniumActions.Click, "", "SponsorTypeViewEditMenu");
   	verifications.verify(SeleniumVerifications.Appears, "", "EditSponsorTypeDetailsPage", false);
   	 
   	executeStep.performAction(SeleniumActions.Enter, "PlanTypeLabel", "EditSponsorPlanTypeLabel");
	verifications.verify(SeleniumVerifications.Entered, "PlanTypeLabel", "EditSponsorPlanTypeLabel",false);
	executeStep.performAction(SeleniumActions.Enter, "MemberIDLabel", "EditSponsorMemeberIDLabel");
	verifications.verify(SeleniumVerifications.Entered, "MemberIDLabel", "EditSponsorMemeberIDLabel",false);
		
   	// Set Membership ID,Validity Period and Policy No as display and mandatory 
   		executeStep.performAction(SeleniumActions.Check, "","EditSponsorMemberIdMandatoryCheckBox");
   		verifications.verify(SeleniumVerifications.Checked, "","EditSponsorMemberIdMandatoryCheckBox",false);
   		executeStep.performAction(SeleniumActions.Check, "","EditSponsorPolicyIdMandatoryCheckBox");
   		verifications.verify(SeleniumVerifications.Checked, "","EditSponsorPolicyIdMandatoryCheckBox",false);
   		executeStep.performAction(SeleniumActions.Check, "","EditSponsorValidityPeriodMandatoryCheckBox");
   		verifications.verify(SeleniumVerifications.Checked, "","EditSponsorValidityPeriodMandatoryCheckBox",false);
   		
   	
   	//Set Prior Auth Details and Visit Details display as yes by checking the check box
   	 
   		executeStep.performAction(SeleniumActions.Check, "","EditSponsorPriorAuthDetails");
   		verifications.verify(SeleniumVerifications.Checked, "","EditSponsorPriorAuthDetails",false);
   		executeStep.performAction(SeleniumActions.Check, "","EditSponsorVisitLimits");
   		verifications.verify(SeleniumVerifications.Checked, "","EditSponsorVisitLimits",false);
   	 
   	//Set Validity as Editable by checking the check box
   		executeStep.performAction(SeleniumActions.Check, "","EditSponsorValidityPeriodEditableCheckBox");
   		verifications.verify(SeleniumVerifications.Checked, "","EditSponsorValidityPeriodEditableCheckBox",false);
   	 
   	 
   	//Save the sponsor type. Now go to TPA/Sponsor master under Admin Masters and pick any TPA say Axa. 
   	//Map sposnor type as Insurance Company and save the TPA
   		executeStep.performAction(SeleniumActions.Click, "", "EditSponsorTypeSaveButton");
   		verifications.verify(SeleniumVerifications.Appears, "", "EditSponsorTypeDetailsPage", false);
		
		
		
	}		
	
	public void editTPASponsorMasters()
	{
		executeStep.performAction(SeleniumActions.Enter, "TPA/SponsorName","TPA/SponsorName");
		verifications.verify(SeleniumVerifications.Appears, "","TPA/SponsorNameList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","TPA/SponsorNameList");
		verifications.verify(SeleniumVerifications.Entered, "TPA/SponsorName","TPA/SponsorName",false);
		
		executeStep.performAction(SeleniumActions.Click, "","TPA/SponsorMasterPageSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","TPA/SponsorMasterResultTable",false);
		
		executeStep.performAction(SeleniumActions.Click, "","TPA/SponsorMasterResultTable");
		verifications.verify(SeleniumVerifications.Appears, "","TPA/SponsorMasterViewEditMenu",false);
		
		CommonUtilities.delay(10);
		
		executeStep.performAction(SeleniumActions.Click, "","TPA/SponsorMasterViewEditMenu");
		verifications.verify(SeleniumVerifications.Appears, "","EditTPA/SponsorPage",false);
		
		executeStep.performAction(SeleniumActions.Select, "TPA/SponsorType","EditTPA/SponsorTypeSelection");
		verifications.verify(SeleniumVerifications.Selected, "TPA/SponsorType","EditTPA/SponsorTypeSelection",false);
		
		executeStep.performAction(SeleniumActions.Click, "","EditTPA/SponsorPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditTPA/SponsorPage",false);
		
	}
	
	
	public void addNewTPASponsor(){
		
		System.out.println("Inside AddTPA/SponsorPage ");
	
		executeStep.performAction(SeleniumActions.Enter, "TPA/SponsorName", "AddTPA/SponsorName");
		verifications.verify(SeleniumVerifications.Entered, "TPA/SponsorName", "AddTPA/SponsorName",false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddTPA/SponsorCountry", "AddTPA/SponsorPageCountry");
		verifications.verify(SeleniumVerifications.Entered, "AddTPA/SponsorCountry", "AddTPA/SponsorPageCountry",false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddTPA/SponsorState", "AddTPA/SponsorPageState");
		verifications.verify(SeleniumVerifications.Entered, "AddTPA/SponsorState", "AddTPA/SponsorPageState",false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddTPA/SponsorCity", "AddTPA/SponsorPageCity");
		verifications.verify(SeleniumVerifications.Entered, "AddTPA/SponsorCity", "AddTPA/SponsorPageCity",false);
		
		executeStep.performAction(SeleniumActions.Select, "TPA/SponsorType", "AddTPA/SponsorPageSponsorType");
		verifications.verify(SeleniumVerifications.Selected, "TPA/SponsorType", "AddTPA/SponsorPageSponsorType",false);
		
		executeStep.performAction(SeleniumActions.Click, "","EditTPA/SponsorPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditTPA/SponsorPage",false);

		
		System.out.println("New TPA/Sponsor's Added");

	}
	
	public void mapCompanyTPASponsor(){
		
		executeStep.performAction(SeleniumActions.Click, "","AddInsuranceCompanyTPA/SponsorLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddInsuranceCompanyTPA/SponsorPage",false);
		
		executeStep.performAction(SeleniumActions.Select, "InsuranceCompanyName", "TPA/SponsorInsuranceCompany");
		verifications.verify(SeleniumVerifications.Selected, "InsuranceCompanyName", "TPA/SponsorInsuranceCompany",false);
		
		executeStep.performAction(SeleniumActions.Select, "Sponsor", "TPA/Sponsor");
		verifications.verify(SeleniumVerifications.Selected, "Sponsor", "TPA/Sponsor",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AddInsuranceCompanyTPA/SponsorSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","InsuranceCompanyTPA/SponsorMasterPage",false);
		
		
	}
	
}
