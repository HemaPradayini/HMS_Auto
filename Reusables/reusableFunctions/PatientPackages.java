package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PatientPackages {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	public PatientPackages(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PatientPackages(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
    public void searchPatientPackages(){
		
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("PatientPackageMRNoField", "PatientPackageMRNoList", "PatientPackageSearchButton", "PatientPackagelist");

		

	}
    
     public void packageConductionStatus(){
    	 verifications.verify(SeleniumVerifications.Appears, "","PatientPackageConductionStatus",true);
		

	}
     
     public void navigateToPatientPackageDetailsScreen(){
    	 
    	executeStep.performAction(SeleniumActions.Click, "","PatientPackagelist");
 		verifications.verify(SeleniumVerifications.Appears, "","PatientPackageDetails",true);
 		
 		executeStep.performAction(SeleniumActions.Click, "","PatientPackageDetails");
 		verifications.verify(SeleniumVerifications.Appears, "","PatientPackageDetailsScreen",true);
 		verifications.verify(SeleniumVerifications.Appears, "","PatientPackageDetailsResults",true);
 		
 		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");

 		System.out.println("Patient package details screen Opened");
     }
     
     public void navigateToHandOverToPatient(){
    	 
     	executeStep.performAction(SeleniumActions.Click, "","PatientPackagelist");
  		verifications.verify(SeleniumVerifications.Appears, "","PatientPackageDetails",true);
  		
  		executeStep.performAction(SeleniumActions.Click, "","PatientPackageHandoverToPatient");
  		verifications.verify(SeleniumVerifications.Appears, "","PatientPackageHandoverToPatientScreen",true);
  		
  		executeStep.performAction(SeleniumActions.Enter, "HandOverTo","HandoverToFeild");
  		verifications.verify(SeleniumVerifications.Entered, "HandOverTo","HandoverToFeild",true);	
  		
  		executeStep.performAction(SeleniumActions.Click, "","HandoverPackageSaveButton");
  		verifications.verify(SeleniumVerifications.Appears, "","PatientPackageHandoverToPatientScreen",true);
		
		

      }
     
    
   
 
}
