package reusableFunctions;

import org.openqa.selenium.By;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PBMPrescription {
	
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public PBMPrescription(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PBMPrescription(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void addItemsToPBMPrescription(String lineItemId)
    {
		
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		int noOfItemsToEdit = getNoOfPBMPresc();
		for(int i=1; i<noOfItemsToEdit; i++){	
			EnvironmentSetup.UseLineItem = true;
		
			this.executeStep.getDriver().findElement(By.xpath("(//table[@id='siTable']//a[@title='Edit Item Details'])["+i+"]")).click();
			
			executeStep.performAction(SeleniumActions.Enter, "PBMPrescriptionsStength","PBMPrescriptionsStrengthField");
			verifications.verify(SeleniumVerifications.Entered, "PBMPrescriptionsStength","PBMPrescriptionsStrengthField",false);
			
			executeStep.performAction(SeleniumActions.Select, "PBMPrescriptionsItemForm", "PBMPrescriptionsItemFormField");
			verifications.verify(SeleniumVerifications.Selected, "PBMPrescriptionsItemForm","PBMPrescriptionsItemFormField",false);
			
			executeStep.performAction(SeleniumActions.Enter, "PBMPrescriptionsDosage","PBMPrescriptionsDosageField");
			verifications.verify(SeleniumVerifications.Entered, "PBMPrescriptionsDosage","PBMPrescriptionsDosageField",false);
			
			executeStep.performAction(SeleniumActions.Enter, "PBMPrescriptionsQty","PBMPrescriptionsQuantityField");
			verifications.verify(SeleniumVerifications.Entered, "PBMPrescriptionsQty","PBMPrescriptionsQuantityField",false);
			
			executeStep.performAction(SeleniumActions.Enter, "PBMPrescriptionsFrequency","PBMPrescriptionsFrequencyField");
			verifications.verify(SeleniumVerifications.Appears, "","PBMPrescriptionsFrequencyList",true);
			
			executeStep.performAction(SeleniumActions.Click, "PBMPrescriptionsFrequency","PBMPrescriptionsFrequencyList");
			verifications.verify(SeleniumVerifications.Entered, "PBMPrescriptionsFrequency","PBMPrescriptionsFrequencyField",true);	

			executeStep.performAction(SeleniumActions.Click, "","PBMPrescriptionsOkButton");
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			EnvironmentSetup.lineItemCount++;

		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
		executeStep.performAction(SeleniumActions.Click, "","SalesPageItemClose");

    }
	
	public int getNoOfPBMPresc(){
		System.out.println("Inside PBMPrescriptionScreen getNoOfPBMPresc ");
		
		int rowCount=this.executeStep.getDriver().findElements(By.xpath("//table[@id='siTable']//a[@title='Edit Item Details']")).size();
		System.out.println("The no of Rows in the Presc Table is :: " + rowCount);
		return rowCount;
	}

}
