package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;
import org.openqa.selenium.WebElement;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;

public class Dietary {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public Dietary() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Dietary(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void prescribeMeal(){
		/*
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}
		*/
		executeStep.performAction(SeleniumActions.Enter, "Dietician","PrescribeMealDieticianField");
		verifications.verify(SeleniumVerifications.Appears, "Dietician","PrescribeMealDieticianList",false);
		
		executeStep.performAction(SeleniumActions.Click, "Dietician","PrescribeMealDieticianList");
		verifications.verify(SeleniumVerifications.Entered, "Dietician","PrescribeMealDieticianField",false);
		
		
		DbFunctions dbFunctions = new DbFunctions();
		dbFunctions.storeDate(this.executeStep.getDataSet(), "MealFromDate","C",0);
		
		executeStep.performAction(SeleniumActions.Enter, "MealFromDate", "PrescribeMealFromDate");
		verifications.verify(SeleniumVerifications.Entered, "MealFromDate","PrescribeMealFromDate",false);
		
		dbFunctions.storeDate(this.executeStep.getDataSet(), "MealToDate","N",1);
		executeStep.performAction(SeleniumActions.Enter, "MealToDate", "PrescribeMealToDate");
		verifications.verify(SeleniumVerifications.Entered, "MealToDate","PrescribeMealToDate",false);
		
		
		executeStep.performAction(SeleniumActions.Enter, "MealName","PrescribeMealMealName");
		verifications.verify(SeleniumVerifications.Appears, "MealName","PrescribeMealMealNameList",false);
		
		executeStep.performAction(SeleniumActions.Click, "MealName","PrescribeMealMealNameList");
		verifications.verify(SeleniumVerifications.Entered, "MealName","PrescribeMealMealName",false);
		
		
		executeStep.performAction(SeleniumActions.Select, "MealTime", "PrescribeMealMealTime");
		verifications.verify(SeleniumVerifications.Selected, "MealTime","PrescribeMealMealTime",false);
		
		
		executeStep.performAction(SeleniumActions.Click, "","PrescribeMealAddButton");
		verifications.verify(SeleniumVerifications.Appears, "","PrescribeMealPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PrescribeMealSaveAndPrintButton");
		verifications.verify(SeleniumVerifications.Appears, "","PrescribeMealPage",false);
				
	}
	public void updateDeliveryTime(){
				
		/* get dynamic row size if no filter is applicable */
		EnvironmentSetup.dataToBeReplaced = "MRID";
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.LineItemIdForExec = "";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		
	//	DbFunctions dbFunction = new DbFunctions();
		int rowCount=0;
		//rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		rowCount = this.executeStep.getDriver().findElements(By.xpath("//div[@class='resultList']/table[@class='resultList dialog_displayColumns']//tr")).size();
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
	
		for(int i=0; i<=rowCount; i++){			
			EnvironmentSetup.UseLineItem = true;
		//	EnvironmentSetup.MRID="LLM290405";
			String xpath = "//tr["+i+"]/td[contains(text(),'"+EnvironmentSetup.MRID+"')]";
			System.out.println(xpath);
			try {
				WebElement we = this.executeStep.getDriver().findElement(By.xpath("//tr["+i+"]/td[contains(text(),'"+EnvironmentSetup.MRID+"')]"));
				EnvironmentSetup.intGlobalLineItemCount = i;
				executeStep.performAction(SeleniumActions.Check, "","MealsScheduleForCanteenStatusCheckBox");
				verifications.verify(SeleniumVerifications.Checked, "","MealsScheduleForCanteenStatusCheckBox",false);
			}
			catch (Exception e){
				System.out.println(i);
				System.out.println(e.toString());
			}
			
			EnvironmentSetup.UseLineItem = false;
			EnvironmentSetup.lineItemCount ++;
			
		}
	
		executeStep.performAction(SeleniumActions.Click, "","MealsScheduleForCanteenUpdateButton");
		verifications.verify(SeleniumVerifications.Appears, "","MealsScheduleForCanteenPage",false);
		
		
	}
	
	}
