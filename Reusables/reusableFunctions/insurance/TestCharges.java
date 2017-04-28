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

public class TestCharges {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public TestCharges(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public TestCharges(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void editTestCharges(String LineItemID){
		
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
		
		
		
		 executeStep.performAction(SeleniumActions.Select, "TestChargesPgRatesForRatesheet","TestChargesPageRatesForRateSheet");
	    verifications.verify(SeleniumVerifications.Selected, "TestChargesPgRatesForRatesheet","TestChargesPageRatesForRateSheet",false);
		
		 executeStep.performAction(SeleniumActions.Select, "TestChargesPgTreatmentCodeType","TestChargesPageTreatmentCodeType");
		 verifications.verify(SeleniumVerifications.Selected, "TestChargesPgTreatmentCodeType","TestChargesPageTreatmentCodeType",false);
		
		 executeStep.performAction(SeleniumActions.Enter, "CodeMasterPageCodeField","TestChargesPageRatePlanCode");
		 verifications.verify(SeleniumVerifications.Entered, "CodeMasterPageCodeField","TestChargesPageRatePlanCode",false);
		  
		  executeStep.performAction(SeleniumActions.Enter, "TestChargesPgRegularCharge","TestChargesPageRegularCharges");
		  verifications.verify(SeleniumVerifications.Entered, "TestChargesPgRegularCharge","TestChargesPageRegularCharges",false);
		  
		  executeStep.performAction(SeleniumActions.Enter, "TestChargesPgDiscount","TestChargesPageDiscount");
		  verifications.verify(SeleniumVerifications.Entered, "TestChargesPgDiscount","TestChargesPageDiscount",false);
		
		  executeStep.performAction(SeleniumActions.Click, "","TestChargesPageApplyChargesToAll");
		// verifications.verify(SeleniumVerifications.Appears, "", "TestChargesPage", false);
		 
		 executeStep.performAction(SeleniumActions.Click, "","TestChargesPageUpdate");
		 verifications.verify(SeleniumVerifications.Appears, "", "TestChargesPage", false);
		 EnvironmentSetup.lineItemCount ++;
		}
			
		EnvironmentSetup.lineItemCount =0;
		}
	

		  
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	