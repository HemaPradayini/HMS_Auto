package reusableFunctions.inventory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.MRNoSearch;
import reusableFunctions.PatientBill;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class ItemCategories {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	String AutomationID;
	String DataSet;

	public ItemCategories() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public ItemCategories(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	
    public void AddItemcategory(String lineItemId, boolean optionalFields){
		
		executeStep.performAction(SeleniumActions.Click, "","AddNewcategory");
		verifications.verify(SeleniumVerifications.Appears, "","AddItemCategoryPage",false);
		
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			
			executeStep.performAction(SeleniumActions.Enter, "AddItemCategoryName", "CategoryNameField");
		    verifications.verify(SeleniumVerifications.Entered, "AddItemCategoryName","CategoryNameField",false);
		    
		    executeStep.performAction(SeleniumActions.Select, "AddItemIdentification", "AddItemIdentificationField");
			verifications.verify(SeleniumVerifications.Selected, "AddItemIdentification","AddItemIdentificationField",false);
			
			executeStep.performAction(SeleniumActions.Select, "AddItemIssueType", "AddItemIssueTypeField");
			verifications.verify(SeleniumVerifications.Selected, "AddItemIssueType","AddItemIssueTypeField",false);
			
			if(optionalFields){
				itemCatagoryOptionalFields();
			}
			
			saveItemCatagory();
		
			EnvironmentSetup.lineItemCount++;

		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
		

    }	
    
    public void itemCatagoryOptionalFields(){
    	
    	executeStep.performAction(SeleniumActions.Select, "AddItemBillable", "AddItemBillableField");
		verifications.verify(SeleniumVerifications.Selected, "AddItemBillable","AddItemBillableField",false);
		
		executeStep.performAction(SeleniumActions.Select, "AddItemRetailable", "AddItemRetailableField");
		verifications.verify(SeleniumVerifications.Selected, "AddItemRetailable","AddItemRetailableField",false);


		executeStep.performAction(SeleniumActions.Select, "AddItemClaimable", "AddItemClaimableField");
		verifications.verify(SeleniumVerifications.Selected, "AddItemClaimable","AddItemClaimableField",false);
		
		executeStep.performAction(SeleniumActions.Select, "AddItemDiscount", "AddItemDiscountField");
		verifications.verify(SeleniumVerifications.Selected, "AddItemDiscount","AddItemDiscountField",false);
		
    	
    }
    
    public void saveItemCatagory(){
    	
    	executeStep.performAction(SeleniumActions.Click, "","AddItemCategorySaveButton");
    	verifications.verify(SeleniumVerifications.Appears, "","AddItemCategoryAddLink",true);
    	executeStep.performAction(SeleniumActions.Click, "","AddItemCategoryAddLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddItemCategoryPageHeader",true);
    	
    }
	
}
		