package reusableFunctions.insurance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;

public class RolesAndUsers {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public RolesAndUsers(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public RolesAndUsers(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	
	public void createRole(){
		
		executeStep.performAction(SeleniumActions.Click, "", "NewRoleLink");
		verifications.verify(SeleniumVerifications.Appears, "", "RolePageRole", false);
		
			
		executeStep.performAction(SeleniumActions.Enter, "ApplicationRoleName", "ApplicationRoleName");
		verifications.verify(SeleniumVerifications.Entered, "ApplicationRoleName", "ApplicationRoleName",false);
		
		
		//executeStep.performAction(SeleniumActions.Click, "", "RolePagePlusButton");
		//verifications.verify(SeleniumVerifications.Appears, "", "RolePage", false);
	//	selectScreen("AccessToScreen");

       List <WebElement> weList = this.executeStep.getDriver().findElements(By.xpath("//a[@class='ygtvspacer']"));
		for (WebElement ele : weList){ 
			if (ele.isEnabled()){
			ele.click();
			}
		}
		
	}		
	
	
	

	public void selectScreen(String lineItemId){
	 
	  EnvironmentSetup.LineItemIdForExec = lineItemId;
	  EnvironmentSetup.lineItemCount =0;
	  System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
	
	  EnvironmentSetup.UseLineItem = true;
	  DbFunctions dbFunction = new DbFunctions();
	  int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
	  System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
	  for(int i=1; i<=rowCount; i++){	
	  EnvironmentSetup.UseLineItem = true;
	  executeStep.performAction(SeleniumActions.Click, "RolePageScreenName", "RolePageScreenName");
	  EnvironmentSetup.lineItemCount ++;
	  EnvironmentSetup.UseLineItem = false;
	
	}
	
		
	EnvironmentSetup.UseLineItem = false;
	EnvironmentSetup.lineItemCount =0;
		
		
	}
	
	public void selectActions(String lineItemId){
		 
		  EnvironmentSetup.LineItemIdForExec = lineItemId;
		  EnvironmentSetup.lineItemCount =0;
		  System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		
		  EnvironmentSetup.UseLineItem = true;
		  DbFunctions dbFunction = new DbFunctions();
		  int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		  System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		  for(int i=1; i<=rowCount; i++){	
		  EnvironmentSetup.UseLineItem = true;
		  executeStep.performAction(SeleniumActions.Click, "RolePageAction", "RolePageActionName");
		  EnvironmentSetup.lineItemCount ++;
		  EnvironmentSetup.UseLineItem = false;
		
		}
					
		EnvironmentSetup.UseLineItem = false;
		EnvironmentSetup.lineItemCount =0;
			
			
		}
	
	
		
	public void createUser(){
		
		System.out.println("Inside Roles and Users ");
		
		
		executeStep.performAction(SeleniumActions.Click, "", "NewUserLink");
		verifications.verify(SeleniumVerifications.Appears, "", "AddUserPage", false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddUserName","AddUserPageUserName");
		verifications.verify(SeleniumVerifications.Entered, "AddUserName","AddUserPageUserName",false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddPassword","UserPassword");
		verifications.verify(SeleniumVerifications.Entered, "AddPassword","UserPassword",false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddPassword","AddUserPageConfirmPassword");
		verifications.verify(SeleniumVerifications.Entered, "AddPassword","AddUserPageConfirmPassword",false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddUserName","NameToDisplay");
		verifications.verify(SeleniumVerifications.Entered, "AddUserName","NameToDisplay",false);
		
		executeStep.performAction(SeleniumActions.Select, "ApplicationRole","ApplicationRole");
		verifications.verify(SeleniumVerifications.Selected, "ApplicationRole","ApplicationRole",false);
		
		executeStep.performAction(SeleniumActions.Select, "BillingCounter","BillingCounterDropDown");
		verifications.verify(SeleniumVerifications.Selected, "BillingCounter","BillingCounterDropDown",false);
		
						
		executeStep.performAction(SeleniumActions.Select, "Store","AddUserPageStoreAccess");
		verifications.verify(SeleniumVerifications.Selected, "Store","AddUserPageStoreAccess",false);
		
				
		executeStep.performAction(SeleniumActions.Select, "DefaultStore","AddUserPageDefaultStore");
		verifications.verify(SeleniumVerifications.Selected, "DefaultStore","AddUserPageDefaultStore",false);
		
		
		executeStep.performAction(SeleniumActions.Select, "PharmacyCounter","AddUserPagePharmacyCounter");
		verifications.verify(SeleniumVerifications.Selected, "PharmacyCounter","AddUserPagePharmacyCounter",false);
		
		
		executeStep.performAction(SeleniumActions.Select, "ServiceDept1","AddUserPageServiceDeptDental");
		verifications.verify(SeleniumVerifications.Selected, "ServiceDept1","AddUserPageServiceDeptDental",false);
		
		
		executeStep.performAction(SeleniumActions.Select, "ServiceDept2","AddUserPageServiceDeptGENERAL");
		verifications.verify(SeleniumVerifications.Selected, "ServiceDept2","AddUserPageServiceDeptGENERAL",false);
		
		
		executeStep.performAction(SeleniumActions.Select, "ServiceDept3","AddUserPageServiceDeptEmergencyRoom");
		verifications.verify(SeleniumVerifications.Selected, "ServiceDept3","AddUserPageServiceDeptEmergencyRoom",false);
		
		executeStep.performAction(SeleniumActions.Select, "ServiceDept3","AddUserPageServiceDeptProcedures");
		verifications.verify(SeleniumVerifications.Selected, "ServiceDept3","AddUserPageServiceDeptProcedures",false);
		
		
		executeStep.performAction(SeleniumActions.Click, "","AddUserPageSubmitButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditUserPage",false);
		
		
		
		
		
	}
	
	
	
	
	
	
}
