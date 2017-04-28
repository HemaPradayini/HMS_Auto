package reusableFunctions.insurance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.MRNoSearch;
import reusableFunctions.SiteNavigation;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Doctors {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public Doctors(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Doctors(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void addNewDoctor(){
	
  executeStep.performAction(SeleniumActions.Enter, "DocPageDoctorName","AddNewDoctorsPageDoctorsName");
  verifications.verify(SeleniumVerifications.Entered, "DocPageDoctorName","AddNewDoctorsPageDoctorsName",false);
  
  executeStep.performAction(SeleniumActions.Select, "DocPageDeptName","AddNewDoctorsPageDeptName");
  verifications.verify(SeleniumVerifications.Selected, "DocPageDeptName","AddNewDoctorsPageDeptName",false);
	
  executeStep.performAction(SeleniumActions.Select, "DocPageDoctorType","AddNewDoctorsPageDoctorType");
  verifications.verify(SeleniumVerifications.Selected, "DocPageDoctorType","AddNewDoctorsPageDoctorType",false);
  
  
// executeStep.performAction(SeleniumActions.Enter, "DocPageRegistrationNumber","AddNewDoctorPageRegistrationNumber");
// verifications.verify(SeleniumVerifications.Entered, "DocPageRegistrationNumber","AddNewDoctorPageRegistrationNumber",false);
	
   
	executeStep.performAction(SeleniumActions.Enter, "DocPageLicenseNumber","AddNewDoctorsPageDocLicenseNumber");
	verifications.verify(SeleniumVerifications.Entered, "DocPageLicenseNumber","AddNewDoctorsPageDocLicenseNumber",false);
	

	  executeStep.performAction(SeleniumActions.Select, "DocPageOTDoctor","AddNewDoctorsPageOTDoctor");
	  verifications.verify(SeleniumVerifications.Selected, "DocPageOTDoctor","AddNewDoctorsPageOTDoctor",false);
	  
	  executeStep.performAction(SeleniumActions.Select, "DocPageStatus","AddNewDoctorPageStatus");
	  verifications.verify(SeleniumVerifications.Selected, "DocPageStatus","AddNewDoctorPageStatus",false);
	  
	  executeStep.performAction(SeleniumActions.Select, "DocPagePrescribeByFavourites","AddNewDoctorPagePrescribeByFavourites");
	  verifications.verify(SeleniumVerifications.Selected, "DocPagePrescribeByFavourites","AddNewDoctorPagePrescribeByFavourites",false);
	  
	  executeStep.performAction(SeleniumActions.Check, "","AddNewDoctorsPageScheduableChkBox");
	  verifications.verify(SeleniumVerifications.Checked, "","AddNewDoctorsPageScheduableChkBox",false);
	  
	  executeStep.performAction(SeleniumActions.Select, "DocPagePaymentCategory","AddNewDoctorPagePaymentCategory");
	  verifications.verify(SeleniumVerifications.Selected, "DocPagePaymentCategory","AddNewDoctorPagePaymentCategory",false);
	  
	  	  
	  executeStep.performAction(SeleniumActions.Select, "DocPagePaymentEligible","AddNewDoctorPagePaymentEligible");
	  verifications.verify(SeleniumVerifications.Selected, "DocPagePaymentEligible","AddNewDoctorPagePaymentEligible",false);
	  
	 executeStep.performAction(SeleniumActions.Enter, "DocPageOPRevisitConsultValidity","AddNewDoctorsPageOPRevisitConsultValidity");
	 verifications.verify(SeleniumVerifications.Entered, "DocPageOPRevisitConsultValidity","AddNewDoctorsPageOPRevisitConsultValidity",false);
  
	 executeStep.performAction(SeleniumActions.Enter, "DocPageOPRevisitConsultCount","AddNewDoctorsPageOPRevisitConsultCount");
	 verifications.verify(SeleniumVerifications.Entered, "DocPageOPRevisitConsultCount","AddNewDoctorsPageOPRevisitConsultCount",false);
  	  
	  executeStep.performAction(SeleniumActions.Select, "DocChargesPagePractitioner","AddNewDoctorPagePractitioner");
	  verifications.verify(SeleniumVerifications.Selected, "DocChargesPagePractitioner","AddNewDoctorPagePractitioner",false);
	  
	  executeStep.performAction(SeleniumActions.Select, "DocPageAllowSignaturesToBeUsedByOthers","AddNewDoctorPageAllowSignaturesToBeUsedByOthers");
	  verifications.verify(SeleniumVerifications.Selected, "DocPageAllowSignaturesToBeUsedByOthers","AddNewDoctorPageAllowSignaturesToBeUsedByOthers",false);
	  
	
    executeStep.performAction(SeleniumActions.Click, "","AddNewDoctorsPageSaveButton");
    verifications.verify(SeleniumVerifications.Appears, "", "EditDoctorDetailsPage", false);
  
	}
	public void searchDoctor(){

		System.out.println("Inside Available Doctors ");
		
		executeStep.performAction(SeleniumActions.Enter, "DocPageDoctorName","AvailableDoctorsPageDoctorName");
		verifications.verify(SeleniumVerifications.Appears, "","AvailableDoctorsPageDoctorNameList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AvailableDoctorsPageDoctorNameList");
		verifications.verify(SeleniumVerifications.Entered, "DocPageDoctorName","AvailableDoctorsPageDoctorName",true);	// Don't ever change this to false. Bcoz MRID doesn't get entered. Its the VisitID

		executeStep.performAction(SeleniumActions.Click, "","AvailableDoctorsPageSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","AvailableDoctorResultTable",false);
		CommonUtilities.delay();
		
		executeStep.performAction(SeleniumActions.Click, "","AvailableDoctorResultTable");
		CommonUtilities.delay();
		verifications.verify(SeleniumVerifications.Appears, "","AvailableDoctorPageEditChargesMenu",false);
		
		
		executeStep.performAction(SeleniumActions.Click, "","AvailableDoctorPageEditChargesMenu");
		CommonUtilities.delay();
		verifications.verify(SeleniumVerifications.Appears, "","DoctorChargesPage",false);
		
	}
	public void editDoctorCharges(String lineItemId){
		
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		  EnvironmentSetup.lineItemCount =0;
		  System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		
		  EnvironmentSetup.UseLineItem = true;
		  DbFunctions dbFunction = new DbFunctions();
		  int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		  System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		  for(int i=1; i<=rowCount; i++){	
		  EnvironmentSetup.UseLineItem = true;
	
		 executeStep.performAction(SeleniumActions.Select, "DocChargesPageRatesforRateSheet","DoctorChargesPageRatesforRateSheet");
		 verifications.verify(SeleniumVerifications.Selected, "DocChargesPageRatesforRateSheet","DoctorChargesPageRatesforRateSheet",false);
		  
    	executeStep.performAction(SeleniumActions.Enter, "DocChargesPageOPConsultationCharge","DoctorChargesPageOPConsultationCharge");
 		verifications.verify(SeleniumVerifications.Entered, "DocChargesPageOPConsultationCharge","DoctorChargesPageOPConsultationCharge",false);
 		
 		executeStep.performAction(SeleniumActions.Enter, "DocChargesPageOPRevisitCharge","DoctorChargesPageOPRevisitCharge");
 		verifications.verify(SeleniumVerifications.Entered, "DocChargesPageOPRevisitCharge","DoctorChargesPageOPRevisitCharge",false);
 		
 		
 		executeStep.performAction(SeleniumActions.Enter, "DocChargesPageIPConsultation","DoctorChargesPageBedTypeIpConsultation");
 		verifications.verify(SeleniumVerifications.Entered, "DocChargesPageIPConsultation","DoctorChargesPageBedTypeIpConsultation",false);
 		
 		
 		executeStep.performAction(SeleniumActions.Enter, "DocChargesPageIPWardVisitCharge","DoctorChargesPageBedTypeIpWardVisitCharge");
 		verifications.verify(SeleniumVerifications.Entered, "DocChargesPageIPWardVisitCharge","DoctorChargesPageBedTypeIpWardVisitCharge",false);
 		
 		
 		executeStep.performAction(SeleniumActions.Enter, "DocChargesPageSurgeon/AnaesthetistCharge","DoctorChargesPageBedTypeSurgeon/AnaesthetistCharge");
 		verifications.verify(SeleniumVerifications.Entered, "DocChargesPageSurgeon/AnaesthetistCharge","DoctorChargesPageBedTypeSurgeon/AnaesthetistCharge",false);
 		
 		
 		executeStep.performAction(SeleniumActions.Enter, "DocChargesPageAsstSurgeon/AnaesthetistCharge","DoctorChargesPageBedTypeAsstSurgeon/AnaesthetistCharge");
 		verifications.verify(SeleniumVerifications.Entered, "DocChargesPageAsstSurgeon/AnaesthetistCharge","DoctorChargesPageBedTypeAsstSurgeon/AnaesthetistCharge",false);
 		
		executeStep.performAction(SeleniumActions.Click, "","DoctorChargesPageBedTypeApplyChargestoAll");
		verifications.verify(SeleniumVerifications.Appears, "", "DoctorChargesPage", false);
 						
 		executeStep.performAction(SeleniumActions.Click, "","DoctorChargesPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "", "DoctorChargesPage", false);
		
		EnvironmentSetup.lineItemCount ++;
		EnvironmentSetup.UseLineItem = false;
		
		}
		
		EnvironmentSetup.UseLineItem = false;
		EnvironmentSetup.lineItemCount =0;

	}		
	

}
