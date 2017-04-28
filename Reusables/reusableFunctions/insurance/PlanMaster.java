package reusableFunctions.insurance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import GenericFunctions.CommonUtilities;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.SiteNavigation;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PlanMaster {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public PlanMaster(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PlanMaster(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void SearchPlanInPlanMaster(){
	
	  executeStep.performAction(SeleniumActions.Enter, "InsurancePlanName","PlanMasterPagePlanName");
 	  verifications.verify(SeleniumVerifications.Entered, "InsurancePlanName","PlanMasterPagePlanName",false);
 	  
 	  executeStep.performAction(SeleniumActions.Click, "","PlanMasterPageSearchButton");
	  verifications.verify(SeleniumVerifications.Appears, "", "PlanMasterPage", false);
	  
	  executeStep.performAction(SeleniumActions.Click, "","PlanMasterPageResultTable");
	  verifications.verify(SeleniumVerifications.Appears, "", "PlanMasterPageViewEditMenu", false);
	  CommonUtilities.delay();
	  executeStep.performAction(SeleniumActions.Click, "","PlanMasterPageViewEditMenu");
	  verifications.verify(SeleniumVerifications.Appears, "", "EditPlanDetailsPage", false);
	}
	public void editPlanMaster(){

    	executeStep.performAction(SeleniumActions.Enter, "PlanName","AddPlanDetailsPlanName");
 		verifications.verify(SeleniumVerifications.Entered, "PlanName","AddPlanDetailsPlanName",false);
 		
 		executeStep.performAction(SeleniumActions.Select, "InsuranceCompanyName","AddPlanDetailsInsuranceCompanyName");
		verifications.verify(SeleniumVerifications.Selected, "InsuranceCompanyName","AddPlanDetailsInsuranceCompanyName",false);
		
		
		executeStep.performAction(SeleniumActions.Enter, "ValidFrom","AddPlanDetailsPlanValidFrom");
 		verifications.verify(SeleniumVerifications.Entered, "ValidFrom","AddPlanDetailsPlanValidFrom",false);
 		
		
 		executeStep.performAction(SeleniumActions.Enter, "ValidUpto","AddPlanDetailsPlanValidUpto");
 		verifications.verify(SeleniumVerifications.Entered, "ValidUpto","AddPlanDetailsPlanValidUpto",false);
 		
 		executeStep.performAction(SeleniumActions.Enter, "PlanNotes","AddPlanDetailsPlanNotes");
 		verifications.verify(SeleniumVerifications.Entered, "PlanNotes","AddPlanDetailsPlanNotes",false);
 		
 		executeStep.performAction(SeleniumActions.Enter, "PlanExclusions","AddPlanDetailsPlanExclusions");
 		verifications.verify(SeleniumVerifications.Entered, "PlanExclusions","AddPlanDetailsPlanExclusions",false);
		
 		
 			
		executeStep.performAction(SeleniumActions.Select, "Sponsor","AddPlanDetailsPageSponsor");
		verifications.verify(SeleniumVerifications.Selected, "Sponsor","AddPlanDetailsPageSponsor",true);//making it true as we need for all other scenarios
		
		executeStep.performAction(SeleniumActions.Select, "PlanType","AddPlanDetailsPagePlanType");
		verifications.verify(SeleniumVerifications.Selected, "PlanType","AddPlanDetailsPagePlanType",true);
		
		executeStep.performAction(SeleniumActions.Select, "DefaultRatePlan","AddPlanDetailsPageDefaultRatePlan");
		verifications.verify(SeleniumVerifications.Selected, "DefaultRatePlan","AddPlanDetailsPageDefaultRatePlan",true);
		
				
		executeStep.performAction(SeleniumActions.Select, "DefaultDiscountPlan","AddPlanDetailsPageDefaultDiscountPlan");
		verifications.verify(SeleniumVerifications.Selected, "DefaultDiscountPlan","AddPlanDetailsPageDefaultDiscountPlan",true);
		
		executeStep.performAction(SeleniumActions.Check, "Co-pay %","AddPlanDetailsPageCopay%PostDiscountedAmt");
		verifications.verify(SeleniumVerifications.Checked, "Co-pay %","AddPlanDetailsPageCopay%PostDiscountedAmt",false);
		
		executeStep.performAction(SeleniumActions.Check, "IPApplicable","AddPlanDetailsPageIPApplicable");
		verifications.verify(SeleniumVerifications.Checked, "IPApplicable","AddPlanDetailsPageIPApplicable",false);
		
		executeStep.performAction(SeleniumActions.Check, "OPApplicable","AddPlanDetailsPageOPApplicable");
		verifications.verify(SeleniumVerifications.Checked, "OPApplicable","AddPlanDetailsPageOPApplicable",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AddPlanDetailsPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "", "EditPlanDetailsPage", false);
		
	}		
	public void editCoPayandLimitsOP(){
        CommonUtilities.delay();
		//this.executeStep.getDriver().findElement(By.xpath("//input[@id='op_planlimit']")).sendKeys(Keys.CONTROL,"a",Keys.DELETE);
		
		
    	executeStep.performAction(SeleniumActions.Enter, "OPPlanLimit","PlanMasterPageOPPlanLimit");
 		verifications.verify(SeleniumVerifications.Entered, "OPPlanLimit","PlanMasterPageOPPlanLimit",false);
 		
 			
 		executeStep.performAction(SeleniumActions.Enter, "OPVisitSponsorLimit","PlanMasterPageOPVisitSponsorLimit");
		verifications.verify(SeleniumVerifications.Entered, "OPVisitSponsorLimit","PlanMasterPageOPVisitSponsorLimit",false);
				
		executeStep.performAction(SeleniumActions.Enter, "OPVisitDeductible","PlanMasterPageOPVisitDeductible");
 		verifications.verify(SeleniumVerifications.Entered, "OPVisitDeductible","PlanMasterPageOPVisitDeductible",false);
 		
		executeStep.performAction(SeleniumActions.Enter, "OPVisitCopay","PlanMasterPageOPVisitCopayPercentage");
 		verifications.verify(SeleniumVerifications.Entered, "OPVisitCopay","PlanMasterPageOPVisitCopayPercentage",false);
 		
 		executeStep.performAction(SeleniumActions.Enter, "OPVisitMaxCopay","PlanMasterPageOPVisitMaxCopay");
 		verifications.verify(SeleniumVerifications.Entered, "OPVisitMaxCopay","PlanMasterPageOPVisitMaxCopay",false);
 		
 		executeStep.performAction(SeleniumActions.Check, "FollowupVisits","PlanMasterPageLimitsFollowUpVisitsChkBox");
 		verifications.verify(SeleniumVerifications.Checked, "FollowupVisits","PlanMasterPageLimitsFollowUpVisitsChkBox",false);
		
	}
 			
	
	public void editCoPayandLimitsIP(){

    	executeStep.performAction(SeleniumActions.Enter, "IPPlanLimit","PlanMasterPageIPPlanLimit");
 		verifications.verify(SeleniumVerifications.Entered, "IPPlanLimit","PlanMasterPageIPPlanLimit",false);
 		
 		executeStep.performAction(SeleniumActions.Enter, "IPVisitSponsorLimit","PlanMasterPageIPVisitSponsorLimit");
		verifications.verify(SeleniumVerifications.Entered, "IPVisitSponsorLimit","PlanMasterPageIPVisitSponsorLimit",false);
		
		executeStep.performAction(SeleniumActions.Enter, "IPPerDayLimit","PlanMasterPageIPPerDayLimit");
 		verifications.verify(SeleniumVerifications.Entered, "IPPerDayLimit","PlanMasterPageIPPerDayLimit",false);
				
		executeStep.performAction(SeleniumActions.Enter, "IPVisitDeductible","PlanMasterPageIPVisitDeductible");
 		verifications.verify(SeleniumVerifications.Entered, "IPVisitDeductible","PlanMasterPageIPVisitDeductible",false);
 		
		 		executeStep.performAction(SeleniumActions.Enter, "IPVisitCopay","PlanMasterPageIPVisitCopayPercentage");
 		verifications.verify(SeleniumVerifications.Entered, "IPVisitCopay","PlanMasterPageIPVisitCopayPercentage",false);
 		
 		executeStep.performAction(SeleniumActions.Enter, "IPVisitMaxCopay","PlanMasterPageIPVisitMaxCopay");
 		verifications.verify(SeleniumVerifications.Entered, "IPVisitMaxCopay","PlanMasterPageIPVisitMaxCopay",false);
 		
 		
		
	}
	

	
	
}
