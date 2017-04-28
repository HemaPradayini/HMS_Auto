package reusableFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

 

       public class ConductingDoctorPayment {
	   KeywordSelectionLibrary executeStep;
	   VerificationFunctions verifications;

	   public ConductingDoctorPayment(){

	   }

	   /**
	    * Use this
	    * @param AutomationID
	    */
	   public ConductingDoctorPayment(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		   this.executeStep = execStep;
		   this.verifications = verifications;
	   }
	   
	   public void searchRecords(){
		    executeStep.performAction(SeleniumActions.Enter, "ConductingDoctor","ConductingDoctorFeild");
			verifications.verify(SeleniumVerifications.Appears, "","ConductingDoctorList",false);
			
			executeStep.performAction(SeleniumActions.Click, "","ConductingDoctorList");
			verifications.verify(SeleniumVerifications.Appears, "","ConductingDoctorPaymentPage",false);
			
			MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
			mrnoSearch.searchMRNo("ConductingDoctorPaymentMRNoField", "ConductingDoctorPaymentMRNoList", "ConductingDoctorPaymentSearchButton", "ConductingDoctorPatientRecordList");		

		}
	   
	   public void checkPatientRecord(){
		   executeStep.performAction(SeleniumActions.Click, "","ConductingDoctorPatientRecordCheckBox");
		   verifications.verify(SeleniumVerifications.Appears, "","ConductingDoctorPatientRecordCheckBox",false);
		   
		   executeStep.performAction(SeleniumActions.Click, "","ConductingDoctorSaveButton");
		   verifications.verify(SeleniumVerifications.Appears, "","ConductingDoctorPaymentPage",false);
		   
		   verifications.verify(SeleniumVerifications.Appears, "","ConductingDoctorViewPostedPaymentsTab",false);
		   
		   
	   }

   }   