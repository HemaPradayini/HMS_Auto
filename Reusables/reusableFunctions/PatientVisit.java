package reusableFunctions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;
import supportedKeywords.SeleniumActions;
import supportedKeywords.SeleniumVerifications;

public class PatientVisit {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public PatientVisit(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PatientVisit(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void convertOpToIp(){
		
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("EditPatientVisitPageMRNo","EditPatientVisitPageMRNoLi","EditPatientVisitPageSearchBtn","EditPatientVisitPage");
		verifications.verify(SeleniumVerifications.Appears, "","EditPatientVisitPage",false);
		executeStep.performAction(SeleniumActions.Click, "","ConvertOPToIP");
		verifications.verify(SeleniumVerifications.Appears, "","OPToIPConversionPage",false);

		//Get this into the framework
		List<WebElement> weList = this.executeStep.getDriver().findElements(By.xpath("//select[@name='billAction']"));
		try {
			for (WebElement webElement : weList) {		
				System.out.println("Inside 1st For Loop");
				Select webElementSelect = new Select(webElement);
				List<WebElement> listOptions = webElementSelect.getOptions();
				boolean copyChargesExist = false;
				for (WebElement optionValue : listOptions) {
					System.out.println("Inside 2nd For Loop");
					System.out.println("Option Value is :: " + optionValue.getText());
					if (optionValue.getText().contains("Copy Charge Items to IP Bill Later bill")){
						optionValue.click();
						copyChargesExist = true;
						break;
					}
				}
				if (!copyChargesExist){
					try{
						webElementSelect.selectByVisibleText("Connect to IP Visit,leave as BillNow");
					} catch (NoSuchElementException NSEe){
						System.out.println("Insurance Flow Bill Later");
						webElementSelect.selectByVisibleText("Connect to IP Visit,leave as BillLater");
					}
				}
			}			
		} catch (Exception e){
			e.printStackTrace();
		}
		//Check with Alu for the Data Fields in the test data sheet
		executeStep.performAction(SeleniumActions.Enter, "AddAdmissionDr","OPToIPDoctor");
		verifications.verify(SeleniumVerifications.Appears, "","OPToIPDoctorLi",false);

		executeStep.performAction(SeleniumActions.Click, "","OPToIPDoctorLi");
		verifications.verify(SeleniumVerifications.Appears, "","OPToIPConversionPage",false);

		executeStep.performAction(SeleniumActions.Select, "ShiftBedType","OPToIPBedType");
		verifications.verify(SeleniumVerifications.Selected, "ShiftBedType","OPToIPBedType",false);
		
		EnvironmentSetup.selectByPartMatchInDropDown = true;
		executeStep.performAction(SeleniumActions.Select, "ShiftWard","OPToIPWard");
		verifications.verify(SeleniumVerifications.Selected, "ShiftWard","OPToIPWard",true);
		EnvironmentSetup.selectByPartMatchInDropDown = false;
		
		executeStep.performAction(SeleniumActions.Click, "","OPToIPSaveBtn");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");

		verifications.verify(SeleniumVerifications.Appears, "","EditPatientVisitPage",false);
		

	}
	
	public void changePatientCategory(){
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("EditPatientVisitPageMRNo","EditPatientVisitPageMRNoLi","EditPatientVisitPageSearchBtn","EditPatientVisitPage");
		verifications.verify(SeleniumVerifications.Appears, "","EditPatientVisitPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","EditPatientVisitPatientCategoryChangeLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientCategoryChangePage",false);

		executeStep.performAction(SeleniumActions.Select, "NewPatientCategory","PatientCategoryChngPatientCategoryId");
		verifications.verify(SeleniumVerifications.Selected, "NewPatientCategory","PatientCategoryChngPatientCategoryId",false);

		executeStep.performAction(SeleniumActions.Select, "NewRatePlan","PatientCategoryChngPatientRatePlan");
		verifications.verify(SeleniumVerifications.Selected, "NewRatePlan","PatientCategoryChngPatientRatePlan",false);
		//Added by Tejaswini
		executeStep.performAction(SeleniumActions.Click, "","OPToIPSaveBtn");
		verifications.verify(SeleniumVerifications.Appears, "","PatientCategoryChangePage",false);
		System.out.println();
		//Added by Tejaswini
		executeStep.performAction(SeleniumActions.Click, "","PatientCategoryChangeRatePlanLink");
		verifications.verify(SeleniumVerifications.Appears, "","ChangePlanHeader",false);
	}
	
	public void closeVisit(){                // added by abhishek
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("EditPatientVisitPageMRNo","EditPatientVisitPageMRNoLi","EditPatientVisitPageSearchBtn","EditPatientVisitPage");
		verifications.verify(SeleniumVerifications.Appears, "","EditPatientVisitPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","CloseVisitCheckBox");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
		verifications.verify(SeleniumVerifications.Appears, "","EditPatientVisitPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","EditPatientVisitDetailsSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditPatientVisitPage",false);
	}
	
	
}
