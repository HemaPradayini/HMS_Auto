package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class OperationBill {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public OperationBill() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public OperationBill(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	// move as generic method to framework
	
	public void markBillable(){
		
		List <WebElement> weList = this.executeStep.getDriver().findElements(By.xpath("//input[@type='checkbox']"));
		
		for (WebElement ele : weList){ 
			if (ele.isEnabled()){
				if(!(ele.isSelected())){
					ele.click();
				}
			}
		}
	}
	public void saveOperationBill(){
		
		executeStep.performAction(SeleniumActions.Enter, "OprnAddBillRemarks","OperationAddToBillPageRemarksField");
		verifications.verify(SeleniumVerifications.Entered, "OprnAddBillRemarks","OperationAddToBillPageRemarksField",true);
		
		executeStep.performAction(SeleniumActions.Click, "","OperationAddToBillPageAddToBillButton");
		verifications.verify(SeleniumVerifications.Appears, "","OperationAddToBillPage",false);
		
		
	}
	
}
