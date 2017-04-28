package reusableFunctions.inventory;

import org.openqa.selenium.WebDriver;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Department {

WebDriver driver = null;
KeywordSelectionLibrary executeStep;
VerificationFunctions verifications;
String AutomationID;
String DataSet;

public Department() {

}
/**
 * Use this
 * @param AutomationID
 */
public Department(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
	this.executeStep = execStep;
	this.verifications = verifications;
}

public void AddDepartment(){
	
	System.out.println("Inside Department ");

	executeStep.performAction(SeleniumActions.Click, "","AddNewDepartmentLink");
	verifications.verify(SeleniumVerifications.Appears, "","AddDepartmentPage",true);
	
	executeStep.performAction(SeleniumActions.Enter, "DepartmentName","AddDeptnameField");
	verifications.verify(SeleniumVerifications.Entered, "DepartmentName","AddDeptnameField",false);
	
	executeStep.performAction(SeleniumActions.Click, "","DepartmentsSavebutton");
	verifications.verify(SeleniumVerifications.Appears, "","DeptAddedconfirLink",false);
}
}	