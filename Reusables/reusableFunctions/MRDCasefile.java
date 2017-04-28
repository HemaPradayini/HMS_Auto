package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class MRDCasefile {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public MRDCasefile() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public MRDCasefile(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void raiseIndent(String deptName){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
		
		EnvironmentSetup.selectByPartMatchInDropDown = true;
				
		executeStep.performAction(SeleniumActions.Select, "CasefileIndentAction", "MRDCaseFileSearchPageActionField");
		verifications.verify(SeleniumVerifications.Selected, "CasefileIndentAction","MRDCaseFileSearchPageActionField",false);
		
		EnvironmentSetup.selectByPartMatchInDropDown = false;
		
		executeStep.performAction(SeleniumActions.Check, "","MRDCaseFileSearchPageSelectCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","MRDCaseFileSearchPageSelectCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","MRDCaseFileSearchPageActionButton");
		verifications.verify(SeleniumVerifications.Appears, "","RaiseCasefileIndentPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","RaiseCasefileIndentPageEditCaseFileButton");
		verifications.verify(SeleniumVerifications.Appears, "","RaiseCasefileIndentPage",false);
		
		executeStep.performAction(SeleniumActions.Enter, deptName,"RaiseCasefileIndentPageDeptNameField");
		verifications.verify(SeleniumVerifications.Appears, deptName,"RaiseCasefileIndentPageDeptList",false);
		
		executeStep.performAction(SeleniumActions.Click,deptName ,"RaiseCasefileIndentPageDeptList");
		verifications.verify(SeleniumVerifications.Entered, deptName,"RaiseCasefileIndentPageDeptNameField",false);
	
		executeStep.performAction(SeleniumActions.Click, "","RaiseCasefileIndentPageOkButton");
		verifications.verify(SeleniumVerifications.Appears, "","RaiseCasefileIndentPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","RaiseCasefileIndentPageRaiseIndentButton");
		verifications.verify(SeleniumVerifications.Appears, "","RaiseCasefileIndentPage",false);
	}
	public void returnCasefile(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
		EnvironmentSetup.selectByPartMatchInDropDown = true;
		
		executeStep.performAction(SeleniumActions.Select, "CasefileReturnAction", "MRDCaseFileSearchPageActionField");
		verifications.verify(SeleniumVerifications.Selected, "CasefileReturnAction","MRDCaseFileSearchPageActionField",false);
		EnvironmentSetup.selectByPartMatchInDropDown = false;
		
		executeStep.performAction(SeleniumActions.Check, "","MRDCaseFileSearchPageSelectCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","MRDCaseFileSearchPageSelectCheckBox",true);
		
		executeStep.performAction(SeleniumActions.Click, "","ReturnMRDCasefileButton");
		verifications.verify(SeleniumVerifications.Appears, "","ReturnMRDCasefilePage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ReturnMRDCasefileButton");
		verifications.verify(SeleniumVerifications.Appears, "","ReturnMRDCasefilePage",false);
	}
	public void issueCasefile(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
		
				
		executeStep.performAction(SeleniumActions.Select, "CasefileIssueAction", "MRDCaseFileSearchPageActionField");
		verifications.verify(SeleniumVerifications.Selected, "CasefileIssueAction","MRDCaseFileSearchPageActionField",false);
		
		executeStep.performAction(SeleniumActions.Check, "","MRDCaseFileSearchPageSelectCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","MRDCaseFileSearchPageSelectCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","MRDCaseFileSearchPageActionButton");
		verifications.verify(SeleniumVerifications.Appears, "","IssueMRDCasefilePage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ReturnMRDCasefileButton");
		verifications.verify(SeleniumVerifications.Appears, "","IssueMRDCasefilePage",false);

	}
	public void searchAll(){
		//executeStep.performAction(SeleniumActions.Click, "","RaiseCasefileIndentPageRaiseSearchLink");
		//verifications.verify(SeleniumVerifications.Appears, "","MRDCaseFileSearchPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","MRDCaseFileSearchPageClearButton");
		verifications.verify(SeleniumVerifications.Appears, "","MRDCaseFileSearchPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","MRDCaseFileSearchPageMoreOption");
		verifications.verify(SeleniumVerifications.Appears, "","MRDCaseFileSearchPage",false);
	/*	
		executeStep.performAction(SeleniumActions.Click, "","MRDCaseFileSearchPageTypeAll");
		verifications.verify(SeleniumVerifications.Appears, "","MRDCaseFileSearchPage",false);
		executeStep.performAction(SeleniumActions.Click, "","MRDCaseFileSearchPageStatusAll");
		verifications.verify(SeleniumVerifications.Appears, "","MRDCaseFileSearchPage",false);
		executeStep.performAction(SeleniumActions.Click, "","MRDCaseFileSearchPageIndentStatusAll");
		verifications.verify(SeleniumVerifications.Appears, "","MRDCaseFileSearchPage",false);
		executeStep.performAction(SeleniumActions.Click, "","MRDCaseFileSearchPageIssuedStatusAll");
		verifications.verify(SeleniumVerifications.Appears, "","MRDCaseFileSearchPage",false);
		executeStep.performAction(SeleniumActions.Click, "","MRDCaseFileSearchPageCasefileStatusAll");
		verifications.verify(SeleniumVerifications.Appears, "","MRDCaseFileSearchPage",false);
	*/	
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("MRDCaseFileSearchPageMRNoField", "MRDCaseFileSearchPageMRNoList", "MRDCaseFileSearchPageSearchButton", "MRDCaseFileSearchPage");
		
	}
	public void closeIndent(){
		executeStep.performAction(SeleniumActions.Select, "CasefileCloseAction", "MRDCaseFileSearchPageActionField");
		verifications.verify(SeleniumVerifications.Selected, "CasefileCloseAction","MRDCaseFileSearchPageActionField",false);
		
		executeStep.performAction(SeleniumActions.Check, "","MRDCaseFileSearchPageSelectCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","MRDCaseFileSearchPageSelectCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","MRDCaseFileSearchPageActionButton");
		verifications.verify(SeleniumVerifications.Appears, "","ClosefileIndentPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ReturnMRDCasefileButton");
		verifications.verify(SeleniumVerifications.Appears, "","ClosefileIndentPage",false);
		
	}
	
	}
