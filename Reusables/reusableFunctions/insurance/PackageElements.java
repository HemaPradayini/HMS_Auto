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

public class PackageElements {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public PackageElements(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PackageElements(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void AddPackageElements(){
		executeStep.performAction(SeleniumActions.Enter, "PackageElementsPgPackageName","PackageElementsPagePackageName");
		verifications.verify(SeleniumVerifications.Entered, "PackageElementsPgPackageName","PackageElementsPagePackageName",false);
		  
		executeStep.performAction(SeleniumActions.Select, "PackageElementsPgPackageCategory","PackageElementsPagePackageCategory");
		verifications.verify(SeleniumVerifications.Selected, "PackageElementsPgPackageCategory","PackageElementsPagePackageCategory",false);
		
		executeStep.performAction(SeleniumActions.Select, "PackageElementsPgServiceGroup","PackageElementsPageServiceGroup");
		verifications.verify(SeleniumVerifications.Selected, "PackageElementsPgServiceGroup","PackageElementsPageServiceGroup",false);
		
		executeStep.performAction(SeleniumActions.Select, "PackageElementsPgServiceSubGroup","PackageElementsPageServiceSubGroup");
		verifications.verify(SeleniumVerifications.Selected, "PackageElementsPgServiceSubGroup","PackageElementsPageServiceSubGroup",false);
		
		executeStep.performAction(SeleniumActions.Select, "PackageElementsPgPackageCompletion","PackageElementsPagePackageCompletion");
		verifications.verify(SeleniumVerifications.Selected, "PackageElementsPgPackageCompletion","PackageElementsPagePackageCompletion",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PackageElementPageAllowRateIncreaseYes");
		executeStep.performAction(SeleniumActions.Click, "","PackageElementPageAllowRateDecreaseYes");
		
		executeStep.performAction(SeleniumActions.Select, "PackageElementPgInsuranceCategory","PackageElementPageInsuranceCategory");
		verifications.verify(SeleniumVerifications.Selected, "PackageElementPgInsuranceCategory","PackageElementPageInsuranceCategory",false);
		OrderPackageItem("PackageOrderItem");
		executeStep.performAction(SeleniumActions.Click, "","PackageElementPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "", "PackageElementsPage", false);
		
	
	}
	public void  OrderPackageItem(String lineItemId){
		executeStep.performAction(SeleniumActions.Click, "","PackageElementPageAddItemButton");
		verifications.verify(SeleniumVerifications.Appears, "","PackageElementPageItemAddItemScreen",false);
			EnvironmentSetup.LineItemIdForExec = lineItemId;
			EnvironmentSetup.lineItemCount =0;
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
			
			EnvironmentSetup.UseLineItem = true;
			DbFunctions dbFunction = new DbFunctions();
			int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
			System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
			for(int i=0; i<rowCount; i++){	
				EnvironmentSetup.UseLineItem = true;
				executeStep.performAction(SeleniumActions.Enter, "PackageElementPgItem","PackageElementPageItem");
				verifications.verify(SeleniumVerifications.Appears, "","PackageElementPageItemList",false);
				
				executeStep.performAction(SeleniumActions.Click, "","PackageElementPageItemList");
				verifications.verify(SeleniumVerifications.Appears, "","PackageElementPageItemAddItemScreen",false);
				
				executeStep.performAction(SeleniumActions.Click, "","PackageElementPageItemDialogAddButton");
				verifications.verify(SeleniumVerifications.Appears, "","PackageElementPageItemAddItemScreen",false);
				
				EnvironmentSetup.lineItemCount ++;
				EnvironmentSetup.UseLineItem = false;

		
			}
			executeStep.performAction(SeleniumActions.Click, "","PackageElementPageItemDialogCloseButton");
			verifications.verify(SeleniumVerifications.Appears, "","PackageElementsPage",false);
			
			
			EnvironmentSetup.lineItemCount =0;
			EnvironmentSetup.UseLineItem = false;
			
		
	}
	
	
public void editPackageCharges(String LineItemID){
		
	
	 executeStep.performAction(SeleniumActions.Click, "","PackageElementPageEditChargesLink");
	 verifications.verify(SeleniumVerifications.Appears, "", "PackageChargesPage", false);
	
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
		
		
		
		 executeStep.performAction(SeleniumActions.Select, "PackageChargesPgRatesForRateSheet","PackageChargesPageRatesForRateSheet");
	    verifications.verify(SeleniumVerifications.Selected, "PackageChargesPgRatesForRateSheet","PackageChargesPageRatesForRateSheet",false);
		
		 executeStep.performAction(SeleniumActions.Select, "PackageChargesPgTreatmentCodeType","PackageChargesPageTreatmentCodeType");
		 verifications.verify(SeleniumVerifications.Selected, "PackageChargesPgTreatmentCodeType","PackageChargesPageTreatmentCodeType",false);
		
		 executeStep.performAction(SeleniumActions.Enter, "CodeMasterPageCodeField","PackageChargesPageRatePlanCode");
		 verifications.verify(SeleniumVerifications.Entered, "CodeMasterPageCodeField","PackageChargesPageRatePlanCode",false);
		  
		  executeStep.performAction(SeleniumActions.Enter, "PackageChargesPgPackageCharges","PackageChargesPagePackageCharges");
		  verifications.verify(SeleniumVerifications.Entered, "PackageChargesPgPackageCharges","PackageChargesPagePackageCharges",false);
		  
		  executeStep.performAction(SeleniumActions.Enter, "PackageChargesPgDiscount","PackageChargesPageDiscount");
		  verifications.verify(SeleniumVerifications.Entered, "PackageChargesPgDiscount","PackageChargesPageDiscount",false);
		
		  executeStep.performAction(SeleniumActions.Click, "","PackageChargesPageApplyChargesToAll");
		// verifications.verify(SeleniumVerifications.Appears, "", "PackageChargesPageApplyChargesToAll", false);
		 
		 executeStep.performAction(SeleniumActions.Click, "","PackageChargesPageSaveButton");
		 verifications.verify(SeleniumVerifications.Appears, "", "PackageChargesPage", false);
		 EnvironmentSetup.lineItemCount ++;
		}
			
		EnvironmentSetup.lineItemCount =0;
		}
	
	
	
	
	}
		
	
		
		