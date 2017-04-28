package reusableFunctions.inventory;

import org.openqa.selenium.WebDriver;

import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class ItemVerification {
	
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	String AutomationID;
	String DataSet;

	public ItemVerification() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public ItemVerification(
			
		KeywordSelectionLibrary execStep,
		VerificationFunctions verifications)
	
	{
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	
    public void ItemsVerification(String lineItemId){
		
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			EnvironmentSetup.intGlobalLineItemCount= 2;
			verifications.verify(SeleniumVerifications.Appears, "PurchaseOrderAddItem","PurchaseOrderItem",false);
	
			EnvironmentSetup.lineItemCount++;
			EnvironmentSetup.intGlobalLineItemCount++;
		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.intGlobalLineItemCount = 0;
		EnvironmentSetup.UseLineItem = false;
		

    }	
    
    
}

