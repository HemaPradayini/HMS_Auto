
package reusableFunctions.insurance;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.Order;
import reusableFunctions.Registration;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class OrderModuleInsurance extends Order {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	
	public OrderModuleInsurance(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public OrderModuleInsurance(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		super(execStep, verifications);
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	
	public void addOrderInsurance(String lineItemId){
		executeStep.performAction(SeleniumActions.Select, "OrderPageBillNoChoice", "OrdersScreenBillNumber");
		verifications.verify(SeleniumVerifications.Selected, "OrderPageBillNoChoice", "OrdersScreenBillNumber", false);
		addOrderItemInsurance(lineItemId, "OrdersPage");
		executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenSave");
		verifications.verify(SeleniumVerifications.Appears, "", "OrdersPage", false);				
	}
	
	public void addOrderItemInsurance(String lineItemId, String calledFrom){
		executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenAddButton");
		verifications.verify(SeleniumVerifications.Appears, "", "AddItemScreen", false);
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		
		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=0; i<rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			executeStep.performAction(SeleniumActions.Enter, "Item","OrderAddItemScreenItemField");
			verifications.verify(SeleniumVerifications.Appears, "","OrderAddItemScreenItemList",true);
			
			executeStep.performAction(SeleniumActions.Click, "","OrderAddItemScreenItemList");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
//			CommonUtilities.delay();
			//Added for the scenario OSP - TS033
			
			
			WebElement	 web = this.executeStep.getDriver().findElement(By.xpath("(//a[@title='Select Tooth Numbers']//img[@class='button'])[2]"));

			 try{
			 if(web.isEnabled())
			 web.click();
			 executeStep.performAction(SeleniumActions.Click, "","ToothNumberButton");
			 					verifications.verify(SeleniumVerifications.Appears, "","ToothNumberScreen",true);
			 					
			 					executeStep.performAction(SeleniumActions.Check, "","PediatricToothA");
			 					verifications.verify(SeleniumVerifications.Checked, "","PediatricToothA",false);
			 					
			 					executeStep.performAction(SeleniumActions.Check, "","PediatricToothB");
			 					verifications.verify(SeleniumVerifications.Checked, "","PediatricToothB",false);
			 					
			 					executeStep.performAction(SeleniumActions.Check, "","AdultTooth1");
			 					verifications.verify(SeleniumVerifications.Checked, "","AdultTooth1",false);
			 					
			 					executeStep.performAction(SeleniumActions.Check, "","AdultTooth2");
			 					verifications.verify(SeleniumVerifications.Checked, "","AdultTooth2",false);
			 					
			 					executeStep.performAction(SeleniumActions.Click, "","ToothNumberScreenOKButton");
			 					verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);

			 }catch (Exception e){
			 						System.out.println("Webelement for given xpath not found" + e.toString());
			 					}

			
			
			
			
				
			if(calledFrom.equals("LaboratoryOrderTestsPage")){
			executeStep.performAction(SeleniumActions.Enter, "ItemPrescribingDoctor","OrderAddItemScreenItemPrescribingDoctorText");
			verifications.verify(SeleniumVerifications.Appears, "","OrderAddItemScreenItemPrescribingDoctorDropDown",false);	
			} else {
				executeStep.performAction(SeleniumActions.Enter, "ItemQuantity","OrderAddItemScreenItemQty");
				verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
			}
			if(calledFrom.equals("Operation"))
			{
				addOperationOrder();
			}
			//**************************Added by abhishek
			 Boolean we = false;
			 
				try{
					we = this.executeStep.getDriver().findElement(By.xpath("//input[@id='conducting_doctor']")).isEnabled();
				}catch (Exception e){
				System.out.println("Webelement for given xpath not found" + e.toString());
				}
				if (we==true){	
					executeStep.performAction(SeleniumActions.Enter, "ConductingDoctor","OrderAddItemLabConductingDoctor");
					verifications.verify(SeleniumVerifications.Appears, "","OrderAddItemLabConductingDoctorList",false);
					
					executeStep.performAction(SeleniumActions.Click, "","OrderAddItemLabConductingDoctorList");
					verifications.verify(SeleniumVerifications.Entered, "ConductingDoctor","OrderAddItemLabConductingDoctor",false);	
					
 				//verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
				}	
			//*************************
			
			
			
/*			executeStep.performAction(SeleniumActions.Enter, "ItemPrescribingDoctor","OrderAddItemScreenItemPrescribingDoctorText");
			verifications.verify(SeleniumVerifications.Appears, "","OrderAddItemScreenItemPrescribingDoctorDropDown",false);	
			
			executeStep.performAction(SeleniumActions.Click, "","OrderAddItemScreenItemPrescribingDoctorDropDown");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);	*/
			
			//Added for the scenario OSP - TS033
			if(!(calledFrom.equals("Operation")))
			{
			executeStep.performAction(SeleniumActions.Click, "","OrderAddItemScreenAddButton");

			if (calledFrom.equals("DynaPackageAddOrderPage")){
				verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
				executeStep.performAction(SeleniumActions.Accept, "","Framework");
				verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);

			}
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
			}
			EnvironmentSetup.lineItemCount ++;
			EnvironmentSetup.UseLineItem = false;

	//		verifications.verify(SeleniumVerifications.Entered, "DiagnosisCode","ConsultMgtScreenDiagRow",true);
		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
		
		executeStep.performAction(SeleniumActions.Click, "","OrderAddItemScreenCloseButton");
		if(!(calledFrom.equals("Operation")))
		{
			verifications.verify(SeleniumVerifications.Appears, "",calledFrom,false);
		}
		else{
			verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",true);
		}
		
		System.out.println("Added Order Items");
	}
	
	
}
