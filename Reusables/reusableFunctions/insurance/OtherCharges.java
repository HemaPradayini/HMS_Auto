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

public class OtherCharges {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public OtherCharges(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public OtherCharges(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void addOtherCharges(){
		
				
		  executeStep.performAction(SeleniumActions.Enter, "AddChargePgChargeName","AddChargePageChargeName");
		  verifications.verify(SeleniumVerifications.Entered, "AddChargePgChargeName","AddChargePageChargeName",false);
		
		
		  executeStep.performAction(SeleniumActions.Select, "AddChargePgeChargeGroup","AddChargePageChargeGroup");
		  verifications.verify(SeleniumVerifications.Selected, "AddChargePgeChargeGroup","AddChargePageChargeGroup",false);
			
		  executeStep.performAction(SeleniumActions.Select, "AddChargePgChargeHead","AddChargePageChargeHead");
		  verifications.verify(SeleniumVerifications.Selected, "AddChargePgChargeHead","AddChargePageChargeHead",false);
			
		  executeStep.performAction(SeleniumActions.Select, "AddChargePgServiceGroup","AddChargePageServiceGroup");
		  verifications.verify(SeleniumVerifications.Selected, "AddChargePgServiceGroup","AddChargePageServiceGroup",false);
		
		
		  executeStep.performAction(SeleniumActions.Select, "AddChargePgServiceSubGroup","AddChargePageServiceSubGroup");
		  verifications.verify(SeleniumVerifications.Selected, "AddChargePgServiceSubGroup","AddChargePageServiceSubGroup",false);
		  
		  

		  executeStep.performAction(SeleniumActions.Enter, "AddChargePgOrderCode/Alias","AddChargePageOrderCode/Alias");
		  verifications.verify(SeleniumVerifications.Entered, "AddChargePgOrderCode/Alias","AddChargePageOrderCode/Alias",false);
		  
		  

		  executeStep.performAction(SeleniumActions.Enter, "AddChargePgCharge","AddChargePageCharge");
		  verifications.verify(SeleniumVerifications.Entered, "AddChargePgCharge","AddChargePageCharge",false);
		  
		  		  
		  executeStep.performAction(SeleniumActions.Select, "AddChargePgStatus","AddChargePageStatus");
		  verifications.verify(SeleniumVerifications.Selected, "AddChargePgStatus","AddChargePageStatus",false);
		  
		  executeStep.performAction(SeleniumActions.Select, "AddChargePgInsuranceCategory","AddChargePageInsuranceCategory");
		  verifications.verify(SeleniumVerifications.Selected, "AddChargePgInsuranceCategory","AddChargePageInsuranceCategory",false);
		  
	     
		 executeStep.performAction(SeleniumActions.Click, "","AddChargePageAllowRateIncreaseYes");
		 executeStep.performAction(SeleniumActions.Click, "","AddChargePageAllowRateDecreaseYes");
		
		 
		 executeStep.performAction(SeleniumActions.Click, "","AddChargePageSaveButton");
		 verifications.verify(SeleniumVerifications.Appears, "", "EditChargePage", false);
		
	}


}