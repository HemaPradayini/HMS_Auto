package reusableFunctions.inventory;

import seleniumWebUIFunctions.KeywordSelectionLibrary;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.SiteNavigation;
import reusableFunctions.StockEntry;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class StockReorder {
	
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	
	public StockReorder(){

	   }

	   /**
	    * Use this
	    * @param AutomationID
	    */
	public StockReorder(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		   this.executeStep = execStep;
		   this.verifications = verifications;
	   }
	
	
	
	/*public void belowReorder()
	{
		
		executeStep.performAction(SeleniumActions.Click, "","ReorderLevelRadioButton");
		verifications.verify(SeleniumVerifications.Appears, "","BelowReorderLevelRadioButton",true);
		
		executeStep.performAction(SeleniumActions.Click, "","BelowReorderLevelRadioButton");
		verifications.verify(SeleniumVerifications.Appears, "","ExcludeItemsCheckbox",true);
		
		executeStep.performAction(SeleniumActions.Click, "","ExcludeItemsCheckbox");
		verifications.verify(SeleniumVerifications.Appears, "","StockReorderPageSearchbutton",true);
		
		executeStep.performAction(SeleniumActions.Click, "","StockReorderPageSearchbutton");
		verifications.verify(SeleniumVerifications.Appears, "","StockReorderPage",true);
		
		
		
		
		
	}*/
	

}
