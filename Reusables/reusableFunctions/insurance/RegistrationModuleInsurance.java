
package reusableFunctions.insurance;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.Registration;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class RegistrationModuleInsurance extends Registration {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	
	public RegistrationModuleInsurance(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public RegistrationModuleInsurance(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		super(execStep, verifications);
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	
	public void planDetailVerification() 
	{
	//OPPlanLimit,PlanUtlization,VisitSponsorLimit,VisitDeductible fields are COMMON for primary &secondary(datasheet) as it is only for verification (avoiding extra columns)
		executeStep.performAction(SeleniumActions.Enter, "PrimarySponsorMemberId","PrimaryPriorAuthId");
		verifications.verify(SeleniumVerifications.Entered, "PrimarySponsorMemberId","PrimaryPriorAuthId",false);
		
		
		executeStep.performAction(SeleniumActions.Enter, "OPPlanLimit","PrimaryPlanLimit");
		verifications.verify(SeleniumVerifications.Entered, "OPPlanLimit","PrimaryPlanLimit",false);
		executeStep.performAction(SeleniumActions.Enter, "OPPlanUtlization","PrimaryPlanUtilization");
		verifications.verify(SeleniumVerifications.Entered, "OPPlanUtlization","PrimaryPlanUtilization",false);
		executeStep.performAction(SeleniumActions.Enter, "OPVisitSponsorLimit","PrimaryVisitSponsorLimit");
		verifications.verify(SeleniumVerifications.Entered, "OPVisitSponsorLimit","PrimaryVisitSponsorLimit",false);
		executeStep.performAction(SeleniumActions.Enter, "OPVisitDeductible","PrimaryVisitDedcutible");
		verifications.verify(SeleniumVerifications.Entered, "OPVisitDeductible","PrimaryVisitDedcutible",false);
		executeStep.performAction(SeleniumActions.Enter, "OPVisitMaxCopay","PrimaryVisitMaxCopay");
		verifications.verify(SeleniumVerifications.Entered, "OPVisitMaxCopay","PrimaryVisitMaxCopay",false);
		
	    
		
		
		executeStep.performAction(SeleniumActions.Enter, "PrimarySponsorMemberId","SecondaryPriorAuthId");
		verifications.verify(SeleniumVerifications.Entered, "PrimarySponsorMemberId","SecondaryPriorAuthId",false);
		
		
		executeStep.performAction(SeleniumActions.Enter, "OPPlanLimit","SecondaryPlanLimit");
		verifications.verify(SeleniumVerifications.Entered, "OPPlanLimit","SecondaryPlanLimit",false);
		executeStep.performAction(SeleniumActions.Enter, "OPPlanUtlization","SecondaryPlanUtilization");
		verifications.verify(SeleniumVerifications.Entered, "OPPlanUtlization","SecondaryPlanUtilization",false);
		executeStep.performAction(SeleniumActions.Enter, "OPVisitSponsorLimit","SecondaryVisitSponsorLimit");
		verifications.verify(SeleniumVerifications.Entered, "OPVisitSponsorLimit","SecondaryVisitSponsorLimit",false);
		executeStep.performAction(SeleniumActions.Enter, "OPVisitDeductible","SecondaryVisitDedcutible");
		verifications.verify(SeleniumVerifications.Entered, "OPVisitDeductible","SecondaryVisitDedcutible",false);
		executeStep.performAction(SeleniumActions.Enter, "OPVisitMaxCopay","SecondaryVisitMaxCopay");
		verifications.verify(SeleniumVerifications.Entered, "OPVisitMaxCopay","SecondaryVisitMaxCopay",false);
		
					
	}
	
	public void asterickSignVerification(){
		
		verifications.verify(SeleniumVerifications.Appears, "Asterick","PrimaryMemberIdStar",false);
		verifications.verify(SeleniumVerifications.Appears, "Asterick","PrimaryPolicyValidityStartStar",false);
		verifications.verify(SeleniumVerifications.Appears, "Asterick","PrimaryPolicyValidityEndStar",false);
		verifications.verify(SeleniumVerifications.Appears, "Asterick","PrimaryPolicyNumberStar",false);
		verifications.verify(SeleniumVerifications.Appears, "Asterick","PrimaryPolicyHolderNameStar",false);
		verifications.verify(SeleniumVerifications.Appears, "Asterick","PrimaryPatientRelationshipStar",false);
		
		verifications.verify(SeleniumVerifications.Appears, "Asterick","SecondaryMemberIdStar",false);
		verifications.verify(SeleniumVerifications.Appears, "Asterick","SecondaryPolicyValidityStartStar",false);
		verifications.verify(SeleniumVerifications.Appears, "Asterick","SecondaryPolicyValidityEndStar",false);
		verifications.verify(SeleniumVerifications.Appears, "Asterick","SecondaryPolicyNumberStar",false);
		verifications.verify(SeleniumVerifications.Appears, "Asterick","SecondaryPolicyHolderNameStar",false);
		verifications.verify(SeleniumVerifications.Appears, "Asterick","SecondaryPatientRelationshipStar",false);
		
	}
	
	
	
	public void RegisterPatientMultiInsuranceOSP(){
		RegisterPatientGenericDetails();
		GovtIDDetailsExpandedPanel();
		RegisterPatientInsurance("OSP");
		RegisterPatientSecondaryInsurance();
		RegisterAdmissionInfoIP("OSP");
		additionalSponsorDetails();
		storeDetails();
		
	}
	

	public void additionalSponsorDetails(){
		
		additionalPrimarySponsorDetails();
		executeStep.performAction(SeleniumActions.Enter, "SecondaryPolicyNumber","RegistrationSecondaryPolicyNumber");
		verifications.verify(SeleniumVerifications.Entered, "SecondaryPolicyNumber","RegistrationSecondaryPolicyNumber",false);
		
		
		executeStep.performAction(SeleniumActions.Enter, "SecondaryPolicyHolder","RegistrationSecondaryPolicyHolder");
		verifications.verify(SeleniumVerifications.Entered, "SecondaryPolicyHolder","RegistrationSecondaryPolicyHolder",false);
		
		executeStep.performAction(SeleniumActions.Enter, "SecondaryPatientRelationship","RegistrationSecondaryPatientRelationship");
		verifications.verify(SeleniumVerifications.Entered, "SecondaryPatientRelationship","RegistrationSecondaryPatientRelationship",false);
		
		
		
	}
	public void additionalPrimarySponsorDetails()
	{
		executeStep.performAction(SeleniumActions.Enter, "PrimaryPolicyNumber","RegistrationPrimaryPolicyNumber");
		verifications.verify(SeleniumVerifications.Entered, "PrimaryPolicyNumber","RegistrationPrimaryPolicyNumber",false);
		
		executeStep.performAction(SeleniumActions.Enter, "PrimaryPolicyHolder","RegistrationPrimaryPolicyHolder");
		verifications.verify(SeleniumVerifications.Entered, "PrimaryPolicyHolder","RegistrationPrimaryPolicyHolder",false);
		
		
		executeStep.performAction(SeleniumActions.Enter, "PrimaryPatientRelationship","RegistrationPrimaryPatientRelationship");
		verifications.verify(SeleniumVerifications.Entered, "PrimaryPatientRelationship","RegistrationPrimaryPatientRelationship",false);
		
	}
	
	public void uncheckSecondarySponsor()
	{
		   String SecSponsor= this.executeStep.getDriver().findElement(By.xpath("//input[@id='secondary_sponsor']")).getAttribute("value");
			if(SecSponsor.equals("I")){
				
				executeStep.performAction(SeleniumActions.Click, "","OSPScreenSecondarySponsor");
				verifications.verify(SeleniumVerifications.Appears, "","OPRegistrationScreen",false);
	}
	
	}
	
	
public void RegistrationFollowUpChecked() {    
		
		executeStep.performAction(SeleniumActions.Click, "", "OPRScreenMRNoRadioBtn");
		
		executeStep.performAction(SeleniumActions.Enter, "MRID","OPRScreenMRNoInputBox");
		verifications.verify(SeleniumVerifications.Appears, "","OPRScreenMRNoResult",false);
		
		executeStep.performAction(SeleniumActions.Click, "", "OPRScreenMRNoResult");
		//verifications.verify(SeleniumVerifications.Appears, "", "OPRScreenMRNoInputBox", false);
	
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
		executeStep.performAction(SeleniumActions.Select, "FollowUpConsultationType","OPRScreenConsultationType");              
		verifications.verify(SeleniumVerifications.Selected, "FollowUpConsultationType","OPRScreenConsultationType",false);
		
		executeStep.performAction(SeleniumActions.Click, "", "OPRScreenRegisterAndEditBillBtn");
		verifications.verify(SeleniumVerifications.Appears, "","RegistrationSucccessScreen",false);		
		
		
		
}
		
}
