package reusableFunctions.insurance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;
import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;

public class RateSheets {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public RateSheets(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public RateSheets(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	
	public void createRateSheet(){
		
		 /* EnvironmentSetup.LineItemIdForExec = lineItemId;
		  EnvironmentSetup.lineItemCount =0;
		  System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		
		  EnvironmentSetup.UseLineItem = true;
		  DbFunctions dbFunction = new DbFunctions();
		  int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		  System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		  for(int i=1; i<=rowCount; i++){	
		  EnvironmentSetup.UseLineItem = true;
		  
			*/
		executeStep.performAction(SeleniumActions.Enter, "RateSheetName", "AddEditRateSheetPageRateSheetName");
		verifications.verify(SeleniumVerifications.Entered, "RateSheetName", "AddEditRateSheetPageRateSheetName",false);
				
		executeStep.performAction(SeleniumActions.Select, "RateSheetStatus","AddEditRateSheetPageStatus");
		verifications.verify(SeleniumVerifications.Selected, "RateSheetStatus","AddEditRateSheetPageStatus",false);
		executeStep.performAction(SeleniumActions.Select, "RateSheetEligibletoEarnPoints","AddEditRateSheetPageEligibleToEarnPoints");
		verifications.verify(SeleniumVerifications.Selected, "RateSheetEligibletoEarnPoints","AddEditRateSheetPageEligibleToEarnPoints",false);
		executeStep.performAction(SeleniumActions.Select, "RateSheetStoreTariff","AddEditRateSheetPageStoreTariff");
		verifications.verify(SeleniumVerifications.Selected, "RateSheetStoreTariff","AddEditRateSheetPageStoreTariff",false);
		executeStep.performAction(SeleniumActions.Enter, "RateSheetDiscountPercentage", "AddEditRateSheetPageDiscountPercentage");
		verifications.verify(SeleniumVerifications.Entered, "RateSheetDiscountPercentage", "AddEditRateSheetPageDiscountPercentage",false);
		
		executeStep.performAction(SeleniumActions.Click, "", "AddEditRateSheetPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "", "AddEditRateSheetPage", false);
		//executeStep.performAction(SeleniumActions.Click, "", "AddEditRateSheetPageAddButton");
	//	verifications.verify(SeleniumVerifications.Appears, "", "AddEditRateSheetPage", false);
		/*EnvironmentSetup.lineItemCount ++;
		EnvironmentSetup.UseLineItem = false;
		
		}
		
			
		EnvironmentSetup.UseLineItem = false;
		EnvironmentSetup.lineItemCount =0;*/

		}
		
	public void searchExisitingRateSheet(String lineItemId){

		  EnvironmentSetup.LineItemIdForExec = lineItemId;
		  EnvironmentSetup.lineItemCount =0;
		  System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		
		  EnvironmentSetup.UseLineItem = true;
		  DbFunctions dbFunction = new DbFunctions();
		  int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		  System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		  for(int i=1; i<=rowCount; i++){	
		  EnvironmentSetup.UseLineItem = true;
		executeStep.performAction(SeleniumActions.Enter, "RateSheetName","RateSheetSearchRateSheetName");
		verifications.verify(SeleniumVerifications.Entered, "RateSheetName", "RateSheetSearchRateSheetName",false);
				
		executeStep.performAction(SeleniumActions.Click, "","RateSheetStatusAllChkBox");
		verifications.verify(SeleniumVerifications.Appears, "","RateSheetsPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","RateSheetSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","RateSheetSearchResultTable",false);
		
		executeStep.performAction(SeleniumActions.Click, "","RateSheetSearchResultTable");
		verifications.verify(SeleniumVerifications.Appears, "","RateSheetResultTableViewEditMenu",false);
		
		executeStep.performAction(SeleniumActions.Click, "","RateSheetResultTableViewEditMenu");
		verifications.verify(SeleniumVerifications.Appears, "","AddEditRateSheetPage",false);
		createRateSheet();
		executeStep.performAction(SeleniumActions.Click, "", "RateSheetsList");
		verifications.verify(SeleniumVerifications.Appears, "", "RateSheetsPage", false);
		EnvironmentSetup.lineItemCount ++;
		EnvironmentSetup.UseLineItem = false;
		
		}
		
			
		EnvironmentSetup.UseLineItem = false;
		EnvironmentSetup.lineItemCount =0;

		
	}
	
	public void searchNewRateSheet(String lineItemId){
		  EnvironmentSetup.LineItemIdForExec = lineItemId;
		  EnvironmentSetup.lineItemCount =0;
		  System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		
		  EnvironmentSetup.UseLineItem = true;
		  DbFunctions dbFunction = new DbFunctions();
		  int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		  System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		  for(int i=1; i<=rowCount; i++){	
		  EnvironmentSetup.UseLineItem = true;
		executeStep.performAction(SeleniumActions.Enter, "RateSheetName","RateSheetSearchRateSheetName");
		verifications.verify(SeleniumVerifications.Entered, "RateSheetName", "RateSheetSearchRateSheetName",false);
				
		executeStep.performAction(SeleniumActions.Click, "","RateSheetStatusAllChkBox");
		verifications.verify(SeleniumVerifications.Appears, "","RateSheetsPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","RateSheetSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","RateSheetNoResultFoundMessage",false);
		
		executeStep.performAction(SeleniumActions.Click, "", "AddNewRateSheetLink");
		verifications.verify(SeleniumVerifications.Appears, "", "AddEditRateSheetPage", false);
	    createRateSheet();
		executeStep.performAction(SeleniumActions.Click, "", "RateSheetsList");
		verifications.verify(SeleniumVerifications.Appears, "", "RateSheetsPage", false);
	    
		EnvironmentSetup.lineItemCount ++;
		EnvironmentSetup.UseLineItem = false;
		
		}
		
			
		EnvironmentSetup.UseLineItem = false;
		EnvironmentSetup.lineItemCount =0;

		}
		

	
	
}
