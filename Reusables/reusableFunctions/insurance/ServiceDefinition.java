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

public class ServiceDefinition {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public ServiceDefinition(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public ServiceDefinition(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void addServiceDefinition(){
		executeStep.performAction(SeleniumActions.Enter, "ServiceDefinitionPgServiceName","ServiceDefinitionPageServiceName");
		verifications.verify(SeleniumVerifications.Entered, "ServiceDefinitionPgServiceName","ServiceDefinitionPageServiceName",false);
		  
		executeStep.performAction(SeleniumActions.Select, "ServiceDefinitionPgServiceDept","ServiceDefinitionPageServiceDept");
		verifications.verify(SeleniumVerifications.Selected, "ServiceDefinitionPgServiceDept","ServiceDefinitionPageServiceDept",false);
		
		executeStep.performAction(SeleniumActions.Select, "ServiceDefinitionPgConductingDocRequired","ServiceDefinitionPageConductingDocRequired");
		verifications.verify(SeleniumVerifications.Selected, "ServiceDefinitionPgConductingDocRequired","ServiceDefinitionPageConductingDocRequired",false);
		
		executeStep.performAction(SeleniumActions.Select, "ServiceDefinitionPgServiceGrp","ServiceDefinitionPageServiceGrp");
		verifications.verify(SeleniumVerifications.Selected, "ServiceDefinitionPgServiceGrp","ServiceDefinitionPageServiceGrp",false);
		
		executeStep.performAction(SeleniumActions.Select, "ServiceDefinitionPgServiceSubGrp","ServiceDefinitionPageServiceSubGrp");
		verifications.verify(SeleniumVerifications.Selected, "ServiceDefinitionPgServiceSubGrp","ServiceDefinitionPageServiceSubGrp",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ServiceDefinitionPageConductionRequiredYes");
		
		executeStep.performAction(SeleniumActions.Select, "ServiceDefinitionPgInsuranceCategory","ServiceDefinitionPageInsuranceCategory");
		verifications.verify(SeleniumVerifications.Selected, "ServiceDefinitionPgInsuranceCategory","ServiceDefinitionPageInsuranceCategory",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ServiceDefinitionPageAllowRateIncreaseYes");
		executeStep.performAction(SeleniumActions.Click, "","PackageElementPageAllowRateDecreaseYes");
		executeStep.performAction(SeleniumActions.Click, "","ServiceDefinitionPageToothNumReqYes");
		
		executeStep.performAction(SeleniumActions.Click, "","ServiceDefinitionPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "", "ServiceDefinitionPage", false);
		
	
	}
	
	
public void editServiceCharges(String LineItemID){
		
	
	 executeStep.performAction(SeleniumActions.Click, "","ServiceDefinitionPageServiceChargelink");
	 verifications.verify(SeleniumVerifications.Appears, "", "ServiceChargesPage", false);
	
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
		
		
		
		 executeStep.performAction(SeleniumActions.Select, "ServiceChargesPgRateSheet","ServiceChargesPageRateSheet");
	    verifications.verify(SeleniumVerifications.Selected, "ServiceChargesPgRateSheet","ServiceChargesPageRateSheet",false);
		
		 executeStep.performAction(SeleniumActions.Select, "ServiceChargesPgTreatmentCodeType","ServiceChargesPageTreatmentCodeType");
		 verifications.verify(SeleniumVerifications.Selected, "ServiceChargesPgTreatmentCodeType","ServiceChargesPageTreatmentCodeType",false);
		
		 executeStep.performAction(SeleniumActions.Enter, "CodeMasterPageCodeField","ServiceChargesPageRatePlanCode");
		 verifications.verify(SeleniumVerifications.Entered, "CodeMasterPageCodeField","ServiceChargesPageRatePlanCode",false);
		  
		  executeStep.performAction(SeleniumActions.Enter, "ServiceChargesPgCharge","ServiceChargesPageCharge");
		  verifications.verify(SeleniumVerifications.Entered, "ServiceChargesPgCharge","ServiceChargesPageCharge",false);
		  
		  executeStep.performAction(SeleniumActions.Enter, "ServiceChargesPgDiscount","ServiceChargesPageDiscount");
		  verifications.verify(SeleniumVerifications.Entered, "ServiceChargesPgDiscount","ServiceChargesPageDiscount",false);
		
		  executeStep.performAction(SeleniumActions.Click, "","ServiceChargesPageApplyChargesToAll");
		// verifications.verify(SeleniumVerifications.Appears, "", "PackageChargesPageApplyChargesToAll", false);
		 
		 executeStep.performAction(SeleniumActions.Click, "","ServiceChargesPageUpdateButton");
		 verifications.verify(SeleniumVerifications.Appears, "", "ServiceChargesPage", false);
		 EnvironmentSetup.lineItemCount ++;
		}
			
		EnvironmentSetup.lineItemCount =0;
		}
	
	
	
	
	}
		
	
		
		