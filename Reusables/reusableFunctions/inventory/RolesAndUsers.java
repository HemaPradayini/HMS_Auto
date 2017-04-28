package reusableFunctions.inventory;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

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
	
	public void createUser(){
		
		System.out.println("Inside Roles and Usres ");

		
		executeStep.performAction(SeleniumActions.Enter, "AddUserName","UserName");
		verifications.verify(SeleniumVerifications.Entered, "AddUserName","UserName",false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddPassword","UserPassword");
		verifications.verify(SeleniumVerifications.Entered, "AddPassword","UserPassword",false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddPassword","ConfirmPassword");
		verifications.verify(SeleniumVerifications.Entered, "AddPassword","ConfirmPassword",false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddUserName","NameToDisplay");
		verifications.verify(SeleniumVerifications.Entered, "AddUserName","NameToDisplay",false);
		
		executeStep.performAction(SeleniumActions.Select, "ApplicationRole","ApplicationRole");
		verifications.verify(SeleniumVerifications.Selected, "ApplicationRole","ApplicationRole",false);
		
		executeStep.performAction(SeleniumActions.Select, "UserCenter","UserCenterdd");
		verifications.verify(SeleniumVerifications.Selected, "UserCenter","UserCenterdd",true);
		
		executeStep.performAction(SeleniumActions.Select, "BillingCounter","BillingCounterDropDown");
		verifications.verify(SeleniumVerifications.Selected, "BillingCounter","BillingCounterDropDown",false);
		
		executeStep.performAction(SeleniumActions.Select, "Store","StoreAccess");
		verifications.verify(SeleniumVerifications.Selected, "Store","StoreAccess",false);
		
		executeStep.performAction(SeleniumActions.Select, "Store","DefaultStore");
		verifications.verify(SeleniumVerifications.Selected, "Store","DefaultStore",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AddUserPageSubmitButton");
		//verifications.verify(SeleniumVerifications.Appears, "","DefaultStore",false);
		
		
		
		
		
	}
	
	public void SearchUser()
	{
		
		 
		 executeStep.performAction(SeleniumActions.Enter, "UserName", "UsernameField");
		 verifications.verify(SeleniumVerifications.Entered, "UserName","UsernameField",false);
		 
		 executeStep.performAction(SeleniumActions.Click, "","SearchButton");
		 verifications.verify(SeleniumVerifications.Appears, "","RolesAndUsersPage",false);
		 
		
	}
	
	public void EditUser()
	{
		executeStep.performAction(SeleniumActions.Click, "","ApplicationRoleNameField");
		verifications.verify(SeleniumVerifications.Appears, "","EditRole",false);
		
		executeStep.performAction(SeleniumActions.Click, "","EditRole");
		verifications.verify(SeleniumVerifications.Appears, "","RolePage",false);
		
		
		
		
	}
	
	public void allowDirectStockEntry(){
		executeStep.performAction(SeleniumActions.Click, "","AllowDirectStockEntryRadioButton");
		verifications.verify(SeleniumVerifications.Appears, "","RoleSubmitButton",false);
		
		executeStep.performAction(SeleniumActions.Click, "","RoleSubmitButton");
		verifications.verify(SeleniumVerifications.Appears, "","RolesAndUsersPage",false);
	}
	
}
