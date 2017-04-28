package reusableFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

    public class ConductionPackages {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public ConductionPackages(){

	}

	/**
	 * Use this
	 * @param AutomationID
	 */
	public ConductionPackages(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}

	public void searchConductionPackages(){

		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("ConductingDoctorPaymentMRNoField", "ConductingDoctorPaymentMRNoList", "ConductingDoctorPaymentSearchButton", "ConductingDoctorPaymenResultList");

	}

	public void verifyRecords(){

		verifications.verify(SeleniumVerifications.Appears, "","ConductedPackageName",true);

	}


}
