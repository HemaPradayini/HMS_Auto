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

public class Equipment {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public Equipment(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Equipment(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void addNewEquipment(){
		executeStep.performAction(SeleniumActions.Click, "","EquipmentMasterPageAddNewEquipment");
		verifications.verify(SeleniumVerifications.Appears, "", "EquipmentDetailsPage", false);
		
		executeStep.performAction(SeleniumActions.Enter, "EquipmentPageNewEquipName","EquipmentDetailsPageEquipmentName");
		verifications.verify(SeleniumVerifications.Entered, "EquipmentPageNewEquipName","EquipmentDetailsPageEquipmentName",false);
		  
			
		executeStep.performAction(SeleniumActions.Select, "EquipmentPageDeptName","EquipmentDetailsPageDepartmentName");
		verifications.verify(SeleniumVerifications.Selected, "EquipmentPageDeptName","EquipmentDetailsPageDepartmentName",false);
		
		executeStep.performAction(SeleniumActions.Select, "EquipmentPageStatus","EquipmentDetailsPageStatus");
		verifications.verify(SeleniumVerifications.Selected, "EquipmentPageStatus","EquipmentDetailsPageStatus",false);
		
		executeStep.performAction(SeleniumActions.Select, "EquipmentPageServiceGrp","EquipmentDetailsPageServiceGroup");
		verifications.verify(SeleniumVerifications.Selected, "EquipmentPageServiceGrp","EquipmentDetailsPageServiceGroup",false);
		
		executeStep.performAction(SeleniumActions.Select, "EquipmentPageServiceSubGrp","EquipmentDetailsPageServiceSubGroup");
		verifications.verify(SeleniumVerifications.Selected, "EquipmentPageServiceSubGrp","EquipmentDetailsPageServiceSubGroup",false);
		
		executeStep.performAction(SeleniumActions.Select, "EquipmentPageInsuranceCategory","EquipmentDetailsPageInsuranceCategory");
		verifications.verify(SeleniumVerifications.Selected, "EquipmentPageInsuranceCategory","EquipmentDetailsPageInsuranceCategory",false);
		
		executeStep.performAction(SeleniumActions.Select, "EquipmentPageChargeBasis","EquipmentDetailsPageChargeBasis");
		verifications.verify(SeleniumVerifications.Selected, "EquipmentPageChargeBasis","EquipmentDetailsPageChargeBasis",false);
		
		executeStep.performAction(SeleniumActions.Enter, "EquipmentPageUnitSize(minutes)","EquipmentDetailsPageUnitSize(Minutes)");
		verifications.verify(SeleniumVerifications.Entered, "EquipmentPageUnitSize(minutes)","EquipmentDetailsPageUnitSize(Minutes)",false);
		  
		executeStep.performAction(SeleniumActions.Enter, "EquipmentPageMiniDuration(units)","EquipmentDetailsPageMinimumDuration(units)");
		verifications.verify(SeleniumVerifications.Entered, "EquipmentPageMiniDuration(units)","EquipmentDetailsPageMinimumDuration(units)",false);
		  
		executeStep.performAction(SeleniumActions.Enter, "EquipmentpageSlab1Threshold(units)","EquipmentDetailsPageSlab1Threshold(units)");
		verifications.verify(SeleniumVerifications.Entered, "EquipmentpageSlab1Threshold(units)","EquipmentDetailsPageSlab1Threshold(units)",false);
		  
		executeStep.performAction(SeleniumActions.Enter, "EquipmentpageIncrDuration(units)","EquipmentDetailsPageIncrDuration(units)");
		verifications.verify(SeleniumVerifications.Entered, "EquipmentpageIncrDuration(units)","EquipmentDetailsPageIncrDuration(units)",false);
		  
		executeStep.performAction(SeleniumActions.Click, "","EquipmentDetailsPageAllowRateIncreaseYes");
		
		executeStep.performAction(SeleniumActions.Click, "","EquipmentDetailsPageAllowRateDecreaseYes");
		
		executeStep.performAction(SeleniumActions.Click, "","EquipmentDetailsPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "", "EquipmentDetailsPage", false);
		
	}
	
	
	public void editEquipment(String LineItemID){
		
		
		executeStep.performAction(SeleniumActions.Click, "","EquipmentDetailsPageEquipmentChargesLink");
		verifications.verify(SeleniumVerifications.Appears, "", "EquipmentChargesPage", false);
		
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
			
			
		executeStep.performAction(SeleniumActions.Select, "EquipChargePageRateSheet","EquipmentChargesPageRateSheet");
		verifications.verify(SeleniumVerifications.Selected, "EquipChargePageRateSheet","EquipmentChargesPageRateSheet",false);
		
		
		executeStep.performAction(SeleniumActions.Enter, "EquipChargePageDailyCharge","EquipmentChargesPageDailyCharge");
		verifications.verify(SeleniumVerifications.Entered, "EquipChargePageDailyCharge","EquipmentChargesPageDailyCharge",false);
		  
		executeStep.performAction(SeleniumActions.Enter, "EquipChargePageMinCharge","EquipmentChargesPageMinCharge");
		verifications.verify(SeleniumVerifications.Entered, "EquipChargePageMinCharge","EquipmentChargesPageMinCharge",false);
		  
		executeStep.performAction(SeleniumActions.Enter, "EquipChargePageIncrCharge","EquipmentChargesPageIncrCharge");
		verifications.verify(SeleniumVerifications.Entered, "EquipChargePageIncrCharge","EquipmentChargesPageIncrCharge",false);
		  
		
		executeStep.performAction(SeleniumActions.Click, "","EquipmentChargesPageUpdateButton");
		verifications.verify(SeleniumVerifications.Appears, "", "EquipmentChargesPage", false);
		 EnvironmentSetup.lineItemCount ++;
		}
			
		EnvironmentSetup.lineItemCount =0;
		}
	
	}
		
	
		
		