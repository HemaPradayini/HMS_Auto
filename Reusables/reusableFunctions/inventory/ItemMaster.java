package reusableFunctions.inventory;

import org.openqa.selenium.WebDriver;

import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class ItemMaster {

WebDriver driver = null;
KeywordSelectionLibrary executeStep;
VerificationFunctions verifications;
String AutomationID;
String DataSet;

public ItemMaster() {

}

/**
 * Use this
 * @param AutomationID
 */
public ItemMaster(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
	this.executeStep = execStep;
	this.verifications = verifications;
}


public void AddItem(String lineItemId){
	
	executeStep.performAction(SeleniumActions.Click, "","StoresAddNewItem");
	verifications.verify(SeleniumVerifications.Appears, "","AddStoresItemHeader",true);
	
	EnvironmentSetup.LineItemIdForExec = lineItemId;
	EnvironmentSetup.lineItemCount =0;
	System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

	EnvironmentSetup.UseLineItem = true;
	DbFunctions dbFunction = new DbFunctions();
	int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
	System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
	for(int i=1; i<=rowCount; i++){	
		EnvironmentSetup.UseLineItem = true;
		
		executeStep.performAction(SeleniumActions.Enter, "AddStoresItemName", "AddStoresItemNameField");
	    verifications.verify(SeleniumVerifications.Entered, "AddStoresItemName","AddStoresItemNameField",false);
	    
	    executeStep.performAction(SeleniumActions.Enter, "AddStoresShorterName", "AddStoresShorterNameField");
		verifications.verify(SeleniumVerifications.Entered, "AddStoresShorterName","AddStoresShorterNameField",false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddStoresManufacturer", "AddStoresManufacturerField");
		verifications.verify(SeleniumVerifications.Appears, "","AddStoresManufacturerResultsList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AddStoresManufacturerResultsList");
		verifications.verify(SeleniumVerifications.Entered, "AddStoresManufacturer","AddStoresManufacturerField",false);
		
		executeStep.performAction(SeleniumActions.Select, "AddStoresCategory", "AddStoresCategoryField");
		verifications.verify(SeleniumVerifications.Selected, "AddStoresCategory","AddStoresCategoryField",false);
		
		executeStep.performAction(SeleniumActions.Select, "UnitUOM", "AddStoreItemUnitUOM");
		verifications.verify(SeleniumVerifications.Selected, "UnitUOM","AddStoreItemUnitUOM",true);
		
		executeStep.performAction(SeleniumActions.Select, "PackageUOM", "AddStoreItemPackageUOM");
		verifications.verify(SeleniumVerifications.Selected, "PackageUOM","AddStoreItemPackageUOM",true);
		
		executeStep.performAction(SeleniumActions.Select, "AddItemServiceGroup", "AddItemServiceGroupField");
		verifications.verify(SeleniumVerifications.Selected, "AddItemServiceGroup","AddItemServiceGroupField",false);

		executeStep.performAction(SeleniumActions.Select, "AddItemServiceSubGroup", "AddItemServiceSubGroupField");
		verifications.verify(SeleniumVerifications.Selected, "AddItemServiceSubGroup","AddItemServiceSubGroupField",false);
		
		
		executeStep.performAction(SeleniumActions.Enter, "MaxCostPriceSet","MaxCostPriceTextbox");
		verifications.verify(SeleniumVerifications.Appears, "MaxCostPriceSet","MaxCostPriceTextbox",true);

		
		executeStep.performAction(SeleniumActions.Clear, "","AddItemTaxPercentageField");
		executeStep.performAction(SeleniumActions.Enter, "AddItemTaxPercentage","AddItemTaxPercentageField");
		verifications.verify(SeleniumVerifications.Appears, "AddItemTaxPercentage","AddItemTaxPercentageField",true);
		
		executeStep.performAction(SeleniumActions.Select, "AddItemTaxBasis", "AddItemTaxBasisField");
		verifications.verify(SeleniumVerifications.Selected, "AddItemTaxBasis","AddItemTaxBasisField",true);
		
		executeStep.performAction(SeleniumActions.Click, "","AddItemSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","AddStoreItem",true);
		executeStep.performAction(SeleniumActions.Click, "","AddStoreItem");
		verifications.verify(SeleniumVerifications.Appears, "","AddItemSaveButton",true);

		EnvironmentSetup.lineItemCount++;

	}
	EnvironmentSetup.lineItemCount =0;
	EnvironmentSetup.UseLineItem = false;
	

}	

}

