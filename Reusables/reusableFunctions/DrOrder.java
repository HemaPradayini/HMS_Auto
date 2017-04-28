package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class DrOrder {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public DrOrder() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public DrOrder(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void AddItems(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
				
		executeStep.performAction(SeleniumActions.Click, "", "DrOrderAddItemButton");
		verifications.verify(SeleniumVerifications.Appears, "","AddAdmissionOrdersScreen",false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddAdmissionDr","AddAdmissionOrdersDrField");
		verifications.verify(SeleniumVerifications.Entered, "AddAdmissionDr","AddAdmissionOrdersDrField",false);
		
		//EnvironmentSetup.replaceData= true;
		
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.LineItemIdForExec = "Investigation";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		
		DbFunctions dbFunction = new DbFunctions();
		int rowCount=0;
		rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=0; i<rowCount; i++){			
			EnvironmentSetup.UseLineItem = true;
			
			//EnvironmentSetup.dataToBeReplaced
			
			executeStep.performAction(SeleniumActions.Click, "ItemType","AddItemScreenItemType");
			verifications.verify(SeleniumVerifications.Appears,"","AddAdmissionOrdersScreen",false);
			
			WebElement othersRadioButton = this.executeStep.getDriver().findElement(By.xpath("//td[contains(text(),'Others')]//preceding-sibling::input[@name='d_itemType']"));
			if(othersRadioButton.isSelected()){
				executeStep.performAction(SeleniumActions.Enter, "Item","AddAdmissionItemNameField");
				verifications.verify(SeleniumVerifications.Entered,"Item","AddAdmissionItemNameField",false);
			}
			else{
			
				executeStep.performAction(SeleniumActions.Enter, "Item","AddAdmissionItemNameField");
				verifications.verify(SeleniumVerifications.Appears,"","AddAdmissionItemList",false);
				executeStep.performAction(SeleniumActions.Click, "","AddAdmissionItemList");
				verifications.verify(SeleniumVerifications.Appears,"","AddAdmissionOrdersScreen",false);
				verifications.verify(SeleniumVerifications.Entered,"Item","AddAdmissionItemNameField",false);
	
				executeStep.performAction(SeleniumActions.Enter, "Dosage","AddAdmissionOrdersDosageField");
				verifications.verify(SeleniumVerifications.Entered,"Dosage","AddAdmissionOrdersDosageField",false);
			}
			EnvironmentSetup.UseLineItem = false;

			executeStep.performAction(SeleniumActions.Click, "","AddAdmissionOrdersAddButton");
			verifications.verify(SeleniumVerifications.Appears, "","AddAdmissionOrdersScreen",false);
	
			EnvironmentSetup.lineItemCount ++;
			
			
		}

		executeStep.performAction(SeleniumActions.Click, "","AddAdmissionOrdersCloseButton");
		verifications.verify(SeleniumVerifications.Appears, "","DrOrderScreen",false);
	}
    
	public void SaveDrOrder(){
		executeStep.performAction(SeleniumActions.Click, "","DrOrderSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","DrOrderScreen",false);
	}
	
	}
