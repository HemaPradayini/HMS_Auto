package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class DentalConsultations {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public DentalConsultations() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public DentalConsultations(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

public void addTreatmentDetails(String lineItemId){
  String elementXPath = ""; 	
  String elementText;

  EnvironmentSetup.LineItemIdForExec = lineItemId;
  EnvironmentSetup.lineItemCount =0;
  System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

  EnvironmentSetup.UseLineItem = true;
  DbFunctions dbFunction = new DbFunctions();
  int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
  System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
  for(int i=1; i<=rowCount; i++){	
  EnvironmentSetup.UseLineItem = true;
  elementXPath ="//legend[@class='fieldSetLabel' and contains(text(),'Supplies List')]";

 executeStep.performAction(SeleniumActions.Enter, "DentalServiceItem","DentalConsultationAddServiceName");
 verifications.verify(SeleniumVerifications.Entered, "DentalServiceItem","DentalConsultationAddServiceName",false);

 executeStep.performAction(SeleniumActions.Enter, "DentalTreatmentPlannedDoctor","DentalConsultationTreatmentPlannedByDoctor");
 verifications.verify(SeleniumVerifications.Entered, "DentalTreatmentPlannedDoctor","DentalConsultationTreatmentPlannedByDoctor",false);

 executeStep.performAction(SeleniumActions.Enter, "DentalTreatmentPlannedQuantity","DentalConsultationTreatmentPlannedQuantity");
 verifications.verify(SeleniumVerifications.Entered, "DentalTreatmentPlannedQuantity","DentalConsultationTreatmentPlannedQuantity",false);
      
   WebElement we = this.executeStep.getDriver().findElement(By.xpath(elementXPath));
   elementText= we.getText();                        

    System.out.println("The Type :: " + elementText);
	

	if(elementText.equalsIgnoreCase("Supplies List")){
		
		System.out.println("Inside Supplies List"); 
	             
	                 	
		executeStep.performAction(SeleniumActions.Select, "SupplierListSupplier","DentalConsultationTreatmentSupplier");
		verifications.verify(SeleniumVerifications.Selected,"SupplierListSupplier","DentalConsultationTreatmentSupplier",true);
	             	
		executeStep.performAction(SeleniumActions.Select, "SupplierListShade","DentalConsultationTreatmentSupplierShade");
		verifications.verify(SeleniumVerifications.Selected,"SupplierListShade","DentalConsultationTreatmentSupplierShade",true);
	    executeStep.performAction(SeleniumActions.Enter, "SupplierListItemQuantity","DentalConsultationTreatmentSupplierItemQuantity");
	     verifications.verify(SeleniumVerifications.Entered, "SupplierListItemQuantity","DentalConsultationTreatmentSupplierItemQuantity",false);
	    
	  }


   executeStep.performAction(SeleniumActions.Click, "","DentalConsultationTreatmentAddButton");


  EnvironmentSetup.lineItemCount ++;
  EnvironmentSetup.UseLineItem = false;

  }
	executeStep.performAction(SeleniumActions.Click, "","DentalConsultationTreatmentCloseButton");
	verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationPage",false);
	
	executeStep.performAction(SeleniumActions.Click, "","DentalConsultationSaveButton");
	verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationPage",false);
	
	EnvironmentSetup.lineItemCount =0;
	EnvironmentSetup.UseLineItem = false;
		
		
		
}
		
		public void editDentalSupplies(){
			executeStep.performAction(SeleniumActions.Click, "","DentalSuppliesLink");
			verifications.verify(SeleniumVerifications.Appears, "","PatientDentalSuppliesPage",false);
			
			executeStep.performAction(SeleniumActions.Click, "","PatientDentalSuppliesEditButton");
			verifications.verify(SeleniumVerifications.Appears, "","PatientDentalSuppliesAddItemScreen",false);
			
			 executeStep.performAction(SeleniumActions.Enter, "SuppliesItemRemarks","PatientDentalSuppliesItemRemarks");
		     verifications.verify(SeleniumVerifications.Entered, "SuppliesItemRemarks","PatientDentalSuppliesItemRemarks",false);
			
			executeStep.performAction(SeleniumActions.Select, "SuppliesOrderStatus","PatientDentalSuppliesOrderStatusSelection");
			verifications.verify(SeleniumVerifications.Selected,"SuppliesOrderStatus","PatientDentalSuppliesOrderStatusSelection",false);
			
			executeStep.performAction(SeleniumActions.Click, "","PatientDentalSuppliesUpdateButton");
			verifications.verify(SeleniumVerifications.Appears, "","PatientDentalSuppliesPage",false);
			executeStep.performAction(SeleniumActions.Click, "","PatientDentalSuppliesSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "","PatientDentalSuppliesPage",false);
			
		}
		
		
	
	
		
		public int getNoOfBills(){
			System.out.println("Inside Dental Consultation getNoOfBills ");
			
			int rowCount=this.executeStep.getDriver().findElements(By.xpath("//table[@id='resultTable']//tr[@id='billTr']")).size();
			System.out.println("The no of Rows in the Bills Table is :: " + rowCount);
			return rowCount;
		}


       public void payDentalBills(){
            int noOfOpenOrFinalizedBills = getNoOfBills();
	    	   
	        for (int i = 0; i<noOfOpenOrFinalizedBills; i++){
	        EnvironmentSetup.intGlobalLineItemCount= 2;
            executeStep.performAction(SeleniumActions.Click, "","DentalConsultationPaymentCollapsiblePanel");
			verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationInitiatePaymentButton",false);
			

            executeStep.performAction(SeleniumActions.Click, "","DentalConsultationInitiatePaymentButton");
			verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationPaymentSpan",false);
			
			executeStep.performAction(SeleniumActions.Store, "PaymentDue","DentalPaymentTotAmountDue");
			
			executeStep.performAction(SeleniumActions.Select, "BillPaymentType","PatientBillPaymentType");
			verifications.verify(SeleniumVerifications.Selected,"BillPaymentType","PatientBillPaymentType",true);
			
			executeStep.performAction(SeleniumActions.Enter, "PaymentDue","PatientBillPayField");
			verifications.verify(SeleniumVerifications.Entered, "PaymentDue","PatientBillPayField",false);
			
			executeStep.performAction(SeleniumActions.Click, "","DentalPaymentPayAndSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationPage",false); 
                           //noOfOpenOrFinalizedBills = getNoOfBills();
	    		   EnvironmentSetup.intGlobalLineItemCount++;
	        verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
			executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
			verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);
	    		   
				}
	    	   	EnvironmentSetup.intGlobalLineItemCount = 0;
	    	   }    

         public void editTreatment(){
     		executeStep.performAction(SeleniumActions.Click, "","DentalConsultationEditTreatmentButton");
    		verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationEditTreatmentScreen",false);
    		
    		executeStep.performAction(SeleniumActions.Select, "TreatmentStatus","DentalConsultationEditTreatmentStatus");
    		verifications.verify(SeleniumVerifications.Selected, "TreatmentStatus","DentalConsultationEditTreatmentStatus",false);
    		
    		executeStep.performAction(SeleniumActions.Enter, "TreatmentComments","DentalConsultationEditTreatmentComments");
    		verifications.verify(SeleniumVerifications.Entered, "TreatmentComments","DentalConsultationEditTreatmentComments",false);
    		
    		executeStep.performAction(SeleniumActions.Click, "","DentalConsultationOKButton");
    		verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationPage",false);
    		executeStep.performAction(SeleniumActions.Click, "","DentalConsultationSaveButton");
    		verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationPage",false);
         }
		
		
public void editSubTasks(){
	executeStep.performAction(SeleniumActions.Click, "","DentalConsultationEditSubTaskTreatmentButton");
	verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationEditSubTaskScreen",false);
	
	
	
	EnvironmentSetup.selectByPartMatchInDropDown = true;
	executeStep.performAction(SeleniumActions.Select, "EditSecondSubTask","DentalConsultationEditSecondSubTaskStatus");
	verifications.verify(SeleniumVerifications.Selected,"EditSecondSubTask","DentalConsultationEditSecondSubTaskStatus",true);
	EnvironmentSetup.selectByPartMatchInDropDown = false;
	
	
	executeStep.performAction(SeleniumActions.Select, "EditFirstSubTask","DentalConsultationEditFirstSubTaskStatus");
	verifications.verify(SeleniumVerifications.Selected,"EditFirstSubTask","DentalConsultationEditFirstSubTaskStatus",true);
	
	executeStep.performAction(SeleniumActions.Enter, "SubTaskBy","DentalConsultationtSubTaskByDoctor");
	verifications.verify(SeleniumVerifications.Entered, "SubTaskBy","DentalConsultationtSubTaskByDoctor",false);
	
	executeStep.performAction(SeleniumActions.Click, "","DentalConsultationSubTaskStatusOKButton");
	verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationPage",false);
	
	
	executeStep.performAction(SeleniumActions.Click, "","DentalConsultationSaveButton");
	verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationPage",false);	
}
}
