package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Registration {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	
	public Registration(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Registration(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void RegisterPatientGenericDetails(){
		System.out.println("Inside OPRegistration RegisterPatientAsOP ");
		PreRegisterOP("REGOP");

		executeStep.performAction(SeleniumActions.Select, "RatePlan","OPRScreenRatePlanField");
		verifications.verify(SeleniumVerifications.Selected, "RatePlan","OPRScreenRatePlanField",false);
	
	}
	//Below method added to take care of extra information needed for IP Registration - C N Alamelu
	
	public void RegisterAdmissionInfoIP(String DayCareFlag){
		
		System.out.println("Inside RegisterAdditionalInfo ");
		executeStep.performAction(SeleniumActions.Select, "DeptName","OPRScreenDepartmentField");
		verifications.verify(SeleniumVerifications.Selected, "DeptName","OPRScreenDepartmentField",false);
		
		executeStep.performAction(SeleniumActions.Enter, "AdmitDrName","OPRAdmittingDrField");
		verifications.verify(SeleniumVerifications.Appears, "AdmitDrName","OPRAdmittingDrField",false);
		
		executeStep.performAction(SeleniumActions.Click, "AdmitDrName","OPRScreenConsultingDoctorResult");
		verifications.verify(SeleniumVerifications.Entered, "AdmitDrName","OPRAdmittingDrField",true);	
		
		
		executeStep.performAction(SeleniumActions.Select, "BedType","OPRBedTypeField");
		verifications.verify(SeleniumVerifications.Selected, "BedType","OPRBedTypeField",false);
		
		executeStep.performAction(SeleniumActions.Select, "Ward","OPRWardField");
		verifications.verify(SeleniumVerifications.Selected, "Ward","OPRWardField",false);
		
		executeStep.performAction(SeleniumActions.Select, "BedName","OPRBedNameField");
		verifications.verify(SeleniumVerifications.Selected, "BedName","OPRBedNameField",false);
		
		
		executeStep.performAction(SeleniumActions.Select, "DutyDrName","OPRDutyDrName");
		verifications.verify(SeleniumVerifications.Selected, "DutyDrName","OPRDutyDrName",false);
		
		if(DayCareFlag == "DayCare"){
			executeStep.performAction(SeleniumActions.Check, "","OPRDayCareCheckBox");
			verifications.verify(SeleniumVerifications.Checked, "","OPRDayCareCheckBox",false);
		}
			
	}
	
	
	public void setBillType(){
		System.out.println("Inside RegisterPatient ");
		executeStep.performAction(SeleniumActions.Select, "BillType","OPRScreenBillTypeField");
		verifications.verify(SeleniumVerifications.Selected, "BillType","OPRScreenBillTypeField",false);

	}
	
	public void storeDetails(){	
		executeStep.performAction(SeleniumActions.Click, "","OPRScreenRegisterButton");
		verifications.verify(SeleniumVerifications.Appears, "","RegistrationSucccessScreen",false);

		executeStep.performAction(SeleniumActions.Store, "MRID","RegSuccessScreenHospitalIdField");
		executeStep.performAction(SeleniumActions.Store, "VisitID","RegSuccessScreenVisitIdField");		
	}
	
	
	public void RegisterPatientOSPInsurance(){
		System.out.println("Inside RegisterPatientOSPInsurance ");
		
		RegisterPatientInsurance("OSP");
		GovtIDDetailsCollapsedPanel();
		
		executeStep.performAction(SeleniumActions.Enter, "DoctorName","OPRegReferredBy");		
		verifications.verify(SeleniumVerifications.Appears, "","ReferralDoctorNameLi",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ReferralDoctorNameLi");
		verifications.verify(SeleniumVerifications.Appears, "","OSPatientRegistrationScreen",false);
		
		DiagnosisDetails diagDetails = new DiagnosisDetails(executeStep, verifications);
		diagDetails.addDianosisDetails("Diagnosis","OSPatientRegistrationScreen");
		
	}

	public void RegisterPatientInsurance(String patientType){
		System.out.println("Inside RegisterPatientInsurance ");
		if ((patientType.equalsIgnoreCase("IP"))|| (patientType.equalsIgnoreCase("OSP"))){ // Changed by Tejaswini to ensure that the check happens for OSP also
			executeStep.performAction(SeleniumActions.Check, "","OSPScreenPrimarySponsor");
			verifications.verify(SeleniumVerifications.Checked, "","OSPScreenPrimarySponsor",false);
		}
		executeStep.performAction(SeleniumActions.Enter, "PrimarySponsorName","OSPScreenPrimarySponsorName");
		verifications.verify(SeleniumVerifications.Appears, "","OSPScreenSponsorDropDown",true);		
		//CommonUtilities.delay();
		
		executeStep.performAction(SeleniumActions.Click, "","OSPScreenSponsorDropDown");
		verifications.verify(SeleniumVerifications.Entered, "PrimarySponsorName","OSPScreenPrimarySponsorName",true);	
	//	CommonUtilities.delay();
		
		//EnvironmentSetup.selectByPartMatchInDropDown = true;
		executeStep.performAction(SeleniumActions.Select, "PrimaryInsuranceCo","OSPScreenPrimaryInsuranceCo");
		verifications.verify(SeleniumVerifications.Selected, "PrimaryInsuranceCo","OSPScreenPrimaryInsuranceCo",true);	
		
		CommonUtilities.delay();
		try {
		Thread.sleep(10000);
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
	
		}
	//	verifications.verify(SeleniumVerifications.Selected, "PrimaryInsuranceCo","OSPScreenPrimaryInsuranceCo",true);	
		
	//	CommonUtilities.delay();
		
		
		//Added By Tejaswini For OP/IP Insurance Flow
		executeStep.performAction(SeleniumActions.Select, "InsurancePlanType","OSPScreenNetworkPlanType");
		verifications.verify(SeleniumVerifications.Selected, "InsurancePlanType","OSPScreenNetworkPlanType",false);	
		//CommonUtilities.delay();
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}*/
		executeStep.performAction(SeleniumActions.Select, "InsurancePlanName","OSPScreenPlanName");
		verifications.verify(SeleniumVerifications.Selected, "InsurancePlanName","OSPScreenPlanName",false);	
		//CommonUtilities.delay();
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}*/
	//	EnvironmentSetup.selectByPartMatchInDropDown = false;
		executeStep.performAction(SeleniumActions.Enter, "PrimarySponsorMemberId","OSPScreenPrimarySponsorMemberID");
		verifications.verify(SeleniumVerifications.Entered, "PrimarySponsorMemberId","OSPScreenPrimarySponsorMemberID",false);
		
		
		executeStep.performAction(SeleniumActions.Enter, "InsuranceValidityStartDate","OSPScreenPrimarySponsorValidityStart");
		verifications.verify(SeleniumVerifications.Entered, "InsuranceValidityStartDate","OSPScreenPrimarySponsorValidityStart",false);
		
		executeStep.performAction(SeleniumActions.Enter, "InsuranceValidityEndDate","OSPScreenPrimarySponsorValidityEnd");
		verifications.verify(SeleniumVerifications.Entered, "InsuranceValidityEndDate","OSPScreenPrimarySponsorValidityEnd",false);	
		
	
		
		executeStep.performAction(SeleniumActions.Enter, "DocumentUpload","OPRegDocumentUpload");//added by Reena
		verifications.verify(SeleniumVerifications.Entered, "DocumentUpload","OPRegDocumentUpload",true);//added by Reena
		
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Select, "RatePlan","OPRScreenRatePlanField");
		verifications.verify(SeleniumVerifications.Selected, "RatePlan","OPRScreenRatePlanField",true);	
		System.out.println("End of Entering Common Details For RegisterPatientInsurance ");
	}
	public void RegisterPatientSecondaryInsurance(){
		System.out.println("Inside RegisterPatientInsurance ");
		
		executeStep.performAction(SeleniumActions.Check, "","OSPScreenSecondarySponsor");
		verifications.verify(SeleniumVerifications.Checked, "","OSPScreenSecondarySponsor",false);
		
		executeStep.performAction(SeleniumActions.Enter, "SecondarySponsorName","OSPScreenSecondarySponsorName");
		verifications.verify(SeleniumVerifications.Appears, "","OSPScreenSecondarySponsorDropDown",true);		
		//CommonUtilities.delay();
		
		executeStep.performAction(SeleniumActions.Click, "","OSPScreenSecondarySponsorDropDown");
		verifications.verify(SeleniumVerifications.Entered, "SecondarySponsorName","OSPScreenSecondarySponsorName",true);	
	//	CommonUtilities.delay();
		
		//EnvironmentSetup.selectByPartMatchInDropDown = true;
		executeStep.performAction(SeleniumActions.Select, "SecondaryInsuranceCo","OSPScreenSecondaryInsuranceCo");
		verifications.verify(SeleniumVerifications.Selected, "SecondaryInsuranceCo","OSPScreenSecondaryInsuranceCo",true);	
		
	//	CommonUtilities.delay();
		
		
		//Added By Tejaswini For OP/IP Insurance Flow
		executeStep.performAction(SeleniumActions.Select, "SecondaryInsurancePlanType","OSPScreenSecondaryNetworkPlanType");
		verifications.verify(SeleniumVerifications.Selected, "SecondaryInsurancePlanType","OSPScreenSecondaryNetworkPlanType",false);	
		//CommonUtilities.delay();
		
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}*/
		executeStep.performAction(SeleniumActions.Select, "SecondaryInsurancePlanName","OSPScreenSecondaryPlanName");
		verifications.verify(SeleniumVerifications.Selected, "SecondaryInsurancePlanName","OSPScreenSecondaryPlanName",false);	
		//CommonUtilities.delay();
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}*/
	//	EnvironmentSetup.selectByPartMatchInDropDown = false;
		executeStep.performAction(SeleniumActions.Enter, "SecondarySponsorMemberId","OSPScreenSecondarySponsorMemberID");
		verifications.verify(SeleniumVerifications.Entered, "SecondarySponsorMemberId","OSPScreenSecondarySponsorMemberID",false);
		
		executeStep.performAction(SeleniumActions.Enter, "SecondaryInsuranceValidityStartDate","OSPScreenSecondarySponsorValidityStart");
		verifications.verify(SeleniumVerifications.Entered, "SecondaryInsuranceValidityStartDate","OSPScreenSecondarySponsorValidityStart",false);
		
		executeStep.performAction(SeleniumActions.Enter, "SecondaryInsuranceValidityEndDate","OSPScreenSecondarySponsorValidityEnd");
		verifications.verify(SeleniumVerifications.Entered, "SecondaryInsuranceValidityEndDate","OSPScreenSecondarySponsorValidityEnd",false);	
		
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Select, "RatePlan","OPRScreenRatePlanField");
		verifications.verify(SeleniumVerifications.Selected, "RatePlan","OPRScreenRatePlanField",true);	
		System.out.println("End of Entering Common Details For RegisterPatientInsurance ");
	}

	public void InsuranceCoPayDetails(){
		executeStep.performAction(SeleniumActions.Enter, "VisitCoPay","OSPScreenVisitCoPayPercent");
		verifications.verify(SeleniumVerifications.Entered, "VisitCoPay","OSPScreenVisitCoPayPercent",true);
	
	}
	
	public void GovtIDDetailsCollapsedPanel(){
		executeStep.performAction(SeleniumActions.Click, "", "OPRegistrationAdditionalInfoPanel");
		verifications.verify(SeleniumVerifications.Appears, "","OPRegistrationAdditionalInfoPanelExpanded",false);

		executeStep.performAction(SeleniumActions.Select, "GovtIDType","OSPScreenGovtIDType");
		verifications.verify(SeleniumVerifications.Selected, "GovtIDType","OSPScreenGovtIDType",false);
		
		executeStep.performAction(SeleniumActions.Enter, "GovtID","OSPScreenGovtID");
		verifications.verify(SeleniumVerifications.Entered, "GovtID","OSPScreenGovtID",false);
	}
	
	public void GovtIDDetailsExpandedPanel(){
		
		executeStep.performAction(SeleniumActions.Select, "GovtIDType","OSPScreenGovtIDType");
		verifications.verify(SeleniumVerifications.Selected, "GovtIDType","OSPScreenGovtIDType",false);
		
		executeStep.performAction(SeleniumActions.Enter, "GovtID","OSPScreenGovtID");
		verifications.verify(SeleniumVerifications.Entered, "GovtID","OSPScreenGovtID",false);
	}
	
	public void RegisterPatientInsuranceOP(){
		
		GovtIDDetailsCollapsedPanel();
		
		RegistrationOrderItem();
		storeDetails();
		
		//In case store of MRID Doesn't work - Use the following
		WebElement weMRID = this.executeStep.getDriver().findElement(By.xpath("//td[.='Hospital Id:' and @class='formlabel']/following-sibling::td[@class='forminfo']"));
		EnvironmentSetup.MRID = weMRID.getText();
		WebElement weVisitID = this.executeStep.getDriver().findElement(By.xpath("//td[.='Visit Id:' and @class='formlabel']/following-sibling::td[@class='forminfo']"));
		EnvironmentSetup.VisitID = weVisitID.getText();		
		DbFunctions dbFunc = new DbFunctions();
		dbFunc.saveMRIDVisitID(this.executeStep.getDataSet());		
	}
	public void RegisterPatientMultiInsuranceOP(){
		// changed the sequence
		RegisterPatientGenericDetails();
		setBillType();
		GovtIDDetailsCollapsedPanel();
		visitInformationDetails();
		RegisterPatientInsurance("OP");
		RegisterPatientSecondaryInsurance();
		RegistrationOrderItem();
		storeDetails();
			
	}
	
	public void RegisterPatientCashIP(){
		RegisterPatientGenericDetails();
		RegisterAdmissionInfoIP("IP");
		storeDetails();
	}
	
	public void RegisterPatientInsuranceIP(){
		RegisterPatientGenericDetails();
		GovtIDDetailsExpandedPanel();
		RegisterPatientInsurance("IP");
		RegisterAdmissionInfoIP("IP");
		storeDetails();
	}
	public void RegisterPatientMultiInsuranceIP(){
		RegisterPatientGenericDetails();
		GovtIDDetailsExpandedPanel();
		RegisterPatientInsurance("IP");
		RegisterPatientSecondaryInsurance();
		RegisterAdmissionInfoIP("IP");
		storeDetails();
	}
	
	public void RegisterDayCarePatient(){
		RegisterPatientGenericDetails();
		RegisterAdmissionInfoIP("DayCare");
		storeDetails();	
	}
	public void RegisterPatientOP(){
		unCheckPrimarySponsor();
		RegisterPatientGenericDetails();
		setBillType();
		visitInformationDetails();
		RegistrationOrderItem();
		storeDetails();
	}
	public void RegisterPatientOPDialysis(){ //added by Reena
		unCheckPrimarySponsor();
		RegisterPatientGenericDetails();
		setBillType();
		visitInformationDetails();
		storeDetails();
	}
	public void RegistrationOrderItem(){
		Order order = new Order(this.executeStep, this.verifications);
		executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenAddButton");
		verifications.verify(SeleniumVerifications.Appears, "", "AddItemScreen", false);
		EnvironmentSetup.LineItemIdForExec = "RegScreenOrderItem";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);		
		order.addOrderItem("RegScreenOrderItem","OPRegistrationScreen");//Modified by Tejaswini
	}
	// Added by Abhishek to fill Visit details in additional Visit details
	
	public void visitInformationDetails(){
		
//		executeStep.performAction(SeleniumActions.Select, "DeptName","OPRScreenDepartmentField");                         
//		verifications.verify(SeleniumVerifications.Selected, "DeptName","OPRScreenDepartmentField",false);               
		
//		executeStep.performAction(SeleniumActions.Select, "ConsultationType","OPRScreenConsultationType");               
//		verifications.verify(SeleniumVerifications.Selected, "ConsultationType","OPRScreenConsultationType",false);     

		executeStep.performAction(SeleniumActions.Enter, "DoctorName","OPRScreenConsultingDoctor");               
		verifications.verify(SeleniumVerifications.Appears, "","OPRScreenConsultingDoctorResult",false);   //Changed by Tejaswini    
		
		executeStep.performAction(SeleniumActions.Click, "", "OPRScreenConsultingDoctorResult");
		verifications.verify(SeleniumVerifications.Entered, "DoctorName","OPRScreenConsultingDoctor",false); //Changed by Tejaswini	

	}
	public void savePreRegister(){
		executeStep.performAction(SeleniumActions.Click, "","PreRegistrationScreenRegisterButton");
		verifications.verify(SeleniumVerifications.Appears, "","PreRegisterationScreenSuccess",false);	
		
		executeStep.performAction(SeleniumActions.Store, "MRID","RegSuccessScreenHospitalIdField");
	}
	public void PreRegisterAdditionalInfoOP(){
		executeStep.performAction(SeleniumActions.Enter, "EmailID","RegAdditionalPatientInfoEmailIDField");
		verifications.verify(SeleniumVerifications.Entered, "EmailID","RegAdditionalPatientInfoEmailIDField",false);
		
		executeStep.performAction(SeleniumActions.Select, "GovtIDType","OSPScreenGovtIDType");
		verifications.verify(SeleniumVerifications.Selected, "GovtIDType","OSPScreenGovtIDType",false);
		
		executeStep.performAction(SeleniumActions.Enter, "GovtID","OSPScreenGovtID");
		verifications.verify(SeleniumVerifications.Entered, "GovtID","OSPScreenGovtID",false);

	}
	public void PreRegisterOP(String calledFrom){
		executeStep.performAction(SeleniumActions.Select, "Title","OPRScreenTitleField");
		verifications.verify(SeleniumVerifications.Selected, "Title","OPRScreenTitleField",false);
		
		executeStep.performAction(SeleniumActions.Enter, "PatientFirstName","OPRScreenFirstNameField");
		verifications.verify(SeleniumVerifications.Entered, "PatientFirstName","OPRScreenFirstNameField",false);
		
		executeStep.performAction(SeleniumActions.Enter, "PatientMobileNumber","OPRScreenMobileNumField");
		verifications.verify(SeleniumVerifications.Entered, "PatientMobileNumber","OPRScreenMobileNumField",false);
		// Following classification is created due to change in xpath. 
		
		if (calledFrom.equalsIgnoreCase("PREREG")){
			executeStep.performAction(SeleniumActions.Enter, "Age","PreRegistrationPageAge");
			verifications.verify(SeleniumVerifications.Entered, "Age","PreRegistrationPageAge",false);
			executeStep.performAction(SeleniumActions.Select, "Gender","PreRegistrationPageGender");
			verifications.verify(SeleniumVerifications.Selected, "Gender","PreRegistrationPageGender",false);
			
		} else {

			executeStep.performAction(SeleniumActions.Enter, "Age","OPRScreenAgeField");
			verifications.verify(SeleniumVerifications.Entered, "Age","OPRScreenAgeField",false);		
			executeStep.performAction(SeleniumActions.Select, "Gender","OPRScreenGenderField");
			verifications.verify(SeleniumVerifications.Selected, "Gender","OPRScreenGenderField",false);
		}
	/*	
		executeStep.performAction(SeleniumActions.Enter, "NextOfKinName","OPRScreenRegisterNextOfKinName");
		verifications.verify(SeleniumVerifications.Entered, "NextOfKinName","OPRScreenRegisterNextOfKinName",false);
		
		executeStep.performAction(SeleniumActions.Enter, "NextOfKinContactNo","OPRScreenRegisterNextOfKinPhone");
		verifications.verify(SeleniumVerifications.Entered, "NextOfKinContactNo","OPRScreenRegisterNextOfKinPhone",false); 
	*/	
		executeStep.performAction(SeleniumActions.Select, "PatientCategory","OPRScreenPatientCategoryField");
		verifications.verify(SeleniumVerifications.Selected, "PatientCategory","OPRScreenPatientCategoryField",false);		
	}
		  
	public void unCheckPrimarySponsor(){
		executeStep.performAction(SeleniumActions.Click, "","OSPScreenPrimarySponsor");
		verifications.verify(SeleniumVerifications.Appears, "","OPRegistrationScreen",true);
	}
	
	
	public void RegisterPatientReferralDoctor(){
		
		System.out.println("RegisterPatientReferralDoctor ");

		//unCheckPrimarySponsor();
		executeStep.performAction(SeleniumActions.Select, "DeptName","OPRScreenDepartmentField");
		verifications.verify(SeleniumVerifications.Selected, "DeptName","OPRScreenDepartmentField",false);
		
		executeStep.performAction(SeleniumActions.Enter, "ReferredByDoctorName","OPRegReferredBy");
		verifications.verify(SeleniumVerifications.Entered, "ReferredByDoctorName","OPRegReferredBy",false);
		
		Order order = new Order(this.executeStep, this.verifications);
		executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenAddButton");
		verifications.verify(SeleniumVerifications.Appears, "", "AddItemScreen", false);
		order.addOrderItem("OrderItem","RegistrationScreen");		
		//storeDetails();							
	}
	
    public void patientFollowUpVisit(boolean followUpwithOrder) {     // for followup with order or revisit pass true        
		
		executeStep.performAction(SeleniumActions.Click, "", "OPRScreenMRNoRadioBtn");
		//verifications.verify(SeleniumVerifications.Enabled, "", "OPRScreenMRNoInputBox", false);
		

		executeStep.performAction(SeleniumActions.Enter, "MRID","OPRScreenMRNoInputBox");
		verifications.verify(SeleniumVerifications.Appears, "","OPRScreenMRNoResult",false);
		
		executeStep.performAction(SeleniumActions.Click, "", "OPRScreenMRNoResult");
		//verifications.verify(SeleniumVerifications.Appears, "", "OPRScreenMRNoInputBox", false);
	
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
		CommonUtilities.delay();
		if(followUpwithOrder){
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			
			executeStep.performAction(SeleniumActions.Select, "BillType","OPRScreenBillTypeField");
			verifications.verify(SeleniumVerifications.Selected, "BillType","OPRScreenBillTypeField",false);
			executeStep.performAction(SeleniumActions.Enter, "DoctorName","OPRScreenConsultingDoctor");               
			verifications.verify(SeleniumVerifications.Entered, "DoctorName","OPRScreenConsultingDoctor",false);       
			
			executeStep.performAction(SeleniumActions.Click, "", "OPRScreenConsultingDoctorResult");
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			verifications.verify(SeleniumVerifications.Appears, "","OPRegistrationScreen",false);
			
		}else{
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Dismiss, "","Framework");
		executeStep.performAction(SeleniumActions.Select, "BillType","OPRScreenBillTypeField");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Dismiss, "","Framework");
		verifications.verify(SeleniumVerifications.Selected, "BillType","OPRScreenBillTypeField",false);
		executeStep.performAction(SeleniumActions.Enter, "DoctorName","OPRScreenConsultingDoctor");
		executeStep.performAction(SeleniumActions.Click, "", "OPRScreenConsultingDoctorResult");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Dismiss, "","Framework");
		verifications.verify(SeleniumVerifications.Entered, "DoctorName","OPRScreenConsultingDoctor",false);       
		
		
		verifications.verify(SeleniumVerifications.Appears, "","OPRegistrationScreen",false);
		}
		
		
	//	executeStep.performAction(SeleniumActions.Select, "BillType","OPRScreenBillTypeField");
		//verifications.verify(SeleniumVerifications.Selected, "BillType","OPRScreenBillTypeField",false);               // added on 7 feb to set the bill type
		
		executeStep.performAction(SeleniumActions.Select, "FollowUpConsultationType","OPRScreenConsultationType");              // Added by Abhishek 
		verifications.verify(SeleniumVerifications.Selected, "FollowUpConsultationType","OPRScreenConsultationType",false);
		
		verifications.verify(SeleniumVerifications.Appears, "","OPRScreenOrderTable",false);
		
		executeStep.performAction(SeleniumActions.Store, "OPRTotalAmount","OPRScreenRegisterTotalAmount");	
		executeStep.performAction(SeleniumActions.Click, "", "OPRScreenRegisterAndEditBillBtn");
		verifications.verify(SeleniumVerifications.Appears, "","RegistrationSucccessScreen",false);	
	    
	    
		executeStep.performAction(SeleniumActions.Store, "VisitID","RegSuccessScreenVisitIdField");	// added on 2/5
	}
    
	public void storeAndEditBill(){	
		executeStep.performAction(SeleniumActions.Click, "","OPRScreenRegisterAndEditBillBtn");
		verifications.verify(SeleniumVerifications.Appears, "","RegistrationSucccessScreen",false);
		
		executeStep.performAction(SeleniumActions.Store, "MRID","RegSuccessScreenHospitalIdField");
		executeStep.performAction(SeleniumActions.Store, "VisitID","RegSuccessScreenVisitIdField");
		
		verifications.verify(SeleniumVerifications.Opens, "","PatientBillScreen",false);
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");

	}
	
	public void retrievePatientDetails(){
		
		System.out.println("Inside retrievePatientDetails");
		executeStep.performAction(SeleniumActions.Click, "","OPRScreenMRNoRadioBtn");
		verifications.verify(SeleniumVerifications.Appears, "","OPRegistrationScreen",false);

		executeStep.performAction(SeleniumActions.Enter, "MRID","OPRScreenMRNoInputBox");
		verifications.verify(SeleniumVerifications.Appears, "","OPRScreenMRNoResult",false);
		
		executeStep.performAction(SeleniumActions.Click, "MRID","OPRScreenMRNoResult");
		verifications.verify(SeleniumVerifications.Appears, "","OPRegistrationScreen",false);

		System.out.println("Patient Details retrieved");
	}
	public void mrdPatientRegistration(){
		
		unCheckPrimarySponsor();
		RegisterPatientGenericDetails();
		setBillType();
		executeStep.performAction(SeleniumActions.Enter, "AdmitDrName","OPRAdmittingDrField");
		verifications.verify(SeleniumVerifications.Appears, "AdmitDrName","OPRAdmittingDrField",false);
		
		executeStep.performAction(SeleniumActions.Click, "AdmitDrName","OPRScreenConsultingDoctorResult");
		verifications.verify(SeleniumVerifications.Entered, "AdmitDrName","OPRAdmittingDrField",true);	
		;
		executeStep.performAction(SeleniumActions.Click, "","OPRScreenRegisterButton");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","RegistrationSucccessScreen",false);

		executeStep.performAction(SeleniumActions.Store, "MRID","RegSuccessScreenHospitalIdField");
		executeStep.performAction(SeleniumActions.Store, "VisitID","RegSuccessScreenVisitIdField");		
	
	}
	
	public void registerAndPay(){
		
		executeStep.performAction(SeleniumActions.Click, "","OPRScreenRegister&PayButton");
		verifications.verify(SeleniumVerifications.Appears, "","RegistrationSucccessScreen",false);
		
		verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
		executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
		verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);		
		

		executeStep.performAction(SeleniumActions.Store, "MRID","RegSuccessScreenHospitalIdField");
		executeStep.performAction(SeleniumActions.Store, "VisitID","RegSuccessScreenVisitIdField");
		
	}
	
	public void comingFromDrScheduler(){
		executeStep.performAction(SeleniumActions.Select, "Title","OPRScreenTitleField");
		verifications.verify(SeleniumVerifications.Selected, "Title","OPRScreenTitleField",false);
		
		executeStep.performAction(SeleniumActions.Enter, "Age","OPRScreenAgeField");
		verifications.verify(SeleniumVerifications.Entered, "Age","OPRScreenAgeField",false);		
		executeStep.performAction(SeleniumActions.Select, "Gender","OPRScreenGenderField");
		verifications.verify(SeleniumVerifications.Selected, "Gender","OPRScreenGenderField",false);
		
	
		executeStep.performAction(SeleniumActions.Select, "PatientCategory","OPRScreenPatientCategoryField");
		verifications.verify(SeleniumVerifications.Selected, "PatientCategory","OPRScreenPatientCategoryField",false);		
	
		
	}
}
