package reusableFunctions.inventory;

import seleniumWebUIFunctions.KeywordSelectionLibrary;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.StockEntry;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;


public class GRNModule {
	
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	String AutomationID;
	String DataSet;
	
	

	public GRNModule(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
			   this.executeStep = execStep;
			   this.verifications = verifications;
		   }


	
	public void GRNSearch()
	{
		 executeStep.performAction(SeleniumActions.Enter, "GRNNO","GRNNoField");
		 verifications.verify(SeleniumVerifications.Entered, "GRNNO","GRNNoField",true);
		 
		 executeStep.performAction(SeleniumActions.Click, "","GRNSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","GRNNoListField",true);

	}
	
	
	
	
	
	public void GRNPrint(){
		
		executeStep.performAction(SeleniumActions.Click, "","GRNNoListField");
		verifications.verify(SeleniumVerifications.Appears, "","GRNPrintButton",true);
		executeStep.performAction(SeleniumActions.Click, "","GRNPrintButton");
	}
	
	public void OpenCloseTab()
	{
		
		verifications.verify(SeleniumVerifications.Opens, "","GRNPrintPage",false);
		executeStep.performAction(SeleniumActions.CloseTab, "","GRNPrintPage");
		verifications.verify(SeleniumVerifications.Closes, "","GRNPrintPage",false);
		
		
	}
	
	public void ViewEdit()
	{
		executeStep.performAction(SeleniumActions.Click, "","GRNNoListField");
		verifications.verify(SeleniumVerifications.Appears, "","GRNViewEditButton",true);
		executeStep.performAction(SeleniumActions.Click, "","GRNViewEditButton");
		verifications.verify(SeleniumVerifications.Appears, "","GRNViewEditPage",true);
	}
	
	public void Edit()
	{
		executeStep.performAction(SeleniumActions.Click, "","GRNNoField");
		verifications.verify(SeleniumVerifications.Appears, "","GRNEditButton",true);
		executeStep.performAction(SeleniumActions.Click, "","GRNEditButton");
		verifications.verify(SeleniumVerifications.Appears, "","GRNViewEditPage",true);
	}

}
