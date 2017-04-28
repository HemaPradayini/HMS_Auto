package reusableFunctions.inventory;

import org.openqa.selenium.WebDriver;

import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PackageUOM {
	
WebDriver driver = null;
KeywordSelectionLibrary executeStep;
VerificationFunctions verifications;
String AutomationID;
String DataSet;

public PackageUOM() {

}

/**
 * Use this
 * @param AutomationID
 */
public PackageUOM(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
	this.executeStep = execStep;
	this.verifications = verifications;
}


public void AddPackageUOMItem(String lineItemId){
	
	executeStep.performAction(SeleniumActions.Click, "","AddPackageUOMLink");
	verifications.verify(SeleniumVerifications.Appears, "","AddPackageUOMHeader",true);
	
	EnvironmentSetup.LineItemIdForExec = lineItemId;
	EnvironmentSetup.lineItemCount =0;
	System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

	EnvironmentSetup.UseLineItem = true;
	DbFunctions dbFunction = new DbFunctions();
	int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
	System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
	for(int i=1; i<=rowCount; i++){	
		EnvironmentSetup.UseLineItem = true;
		
		executeStep.performAction(SeleniumActions.Enter, "PackageUOM", "PackageUOMField");
	    verifications.verify(SeleniumVerifications.Entered, "PackageUOM","PackageUOMField",false);
	    
	    executeStep.performAction(SeleniumActions.Enter, "UnitUOM", "UnitUOMField");
		verifications.verify(SeleniumVerifications.Entered, "UnitUOM","UnitUOMField",false);
		
		executeStep.performAction(SeleniumActions.Enter, "PackageSize", "PackageSizeField");
	    verifications.verify(SeleniumVerifications.Entered, "PackageSize","PackageSizeField",false);
		
		
		executeStep.performAction(SeleniumActions.Click, "","PackageUOMSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","PackageUOMAddButton",false);
		executeStep.performAction(SeleniumActions.Click, "","PackageUOMAddButton");
		

		EnvironmentSetup.lineItemCount++;

	}
	EnvironmentSetup.lineItemCount =0;
	EnvironmentSetup.UseLineItem = false;
	

}	

}
