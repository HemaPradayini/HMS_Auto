package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Order {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	
	public Order(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Order(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void searchOrder(boolean followUPVisit, boolean Operation){                      // operation parameter passed
		
		System.out.println("Inside Order saveOrder ");
		
		executeStep.performAction(SeleniumActions.Enter, "MRID","OrdersScreenPatientIDField");
		verifications.verify(SeleniumVerifications.Appears, "","OrdersPageMRIDListBox",false);
		//CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "","OrdersPageMRIDListBox");	
		verifications.verify(SeleniumVerifications.Appears, "","OrdersScreenPatientIDField",true);

		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenFind");
		
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		if(followUPVisit){                                                                                 //added by Abhishek
			executeStep.performAction(SeleniumActions.Dismiss, "","Framework");
			
		} else {
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
		}
		
		if(Operation){                                               // added by abhishek if operation is also present in consultation screen
			executeStep.performAction(SeleniumActions.Accept, "","Framework");  // to accept becoz operation is also present
		}
		
		verifications.verify(SeleniumVerifications.Appears, "","OrdersScreenBillNumber",true);
	}

	public void saveOrder(boolean billNow){

		if (billNow){
			executeStep.performAction(SeleniumActions.Select, "OrderPageBillNoChoice", "OrdersScreenBillNumberDropDown");
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);	
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			verifications.verify(SeleniumVerifications.Selected, "OrderPageBillNoChoice", "OrdersScreenBillNumberDropDown", false);					
		}
		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenSave");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		if (billNow){
			executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenBillLink");
			verifications.verify(SeleniumVerifications.Appears, "", "PatientBillScreen", false);
		}
		
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		System.out.println("Order Screen Saved");		
	}
	
	public void cancelOrder(){
		DbFunctions dbFunc = new DbFunctions();
		Map<String, String> ordersToCancel = dbFunc.getOrdersToCancel(executeStep.getDataSet());
		String cancelFlag = "";
		String itemToCancel = "";
		String orderCancelXPathPrefix = "//tr/td/label[contains(text(), '";
		String orderCancelXPathSuffix = "')]/parent::td/following-sibling::td/a[contains(@title, 'Cancel Order')]";
		//EnvironmentSetup.UseLineItem = true;
		//EnvironmentSetup.LineItemIdForExec = "Investigation";
		EnvironmentSetup.replaceData = true;

		try{
		if ((ordersToCancel != null) && !(ordersToCancel.isEmpty())){						
			//Iterator itr = ordersToCancel.k
			for(Entry<?, ?> e: ordersToCancel.entrySet()){
				if((e.getValue()!= null) && (e.getKey()!= null)){
					cancelFlag = e.getValue().toString();
					itemToCancel =e.getKey().toString();
				}
				if ((cancelFlag != null)&&(cancelFlag.equalsIgnoreCase("Y") ||cancelFlag.equalsIgnoreCase("R"))){
					EnvironmentSetup.dataToBeReplaced = itemToCancel;
					executeStep.performAction(SeleniumActions.Click,"", "OrderScreenCancelOrder");
					verifications.verify(SeleniumVerifications.Appears, "", "OrderScreenCancelDialog", false);					
					if (cancelFlag.equalsIgnoreCase("Y")){						
						executeStep.performAction(SeleniumActions.Click, "", "OrderScreenCancelWithOutRefund");
						verifications.verify(SeleniumVerifications.Selected, "", "OrderScreenCancelWithOutRefund", false);
					} else{
						if (cancelFlag.equalsIgnoreCase("R")){
							executeStep.performAction(SeleniumActions.Click, "", "OrderScreenCancelWithRefund");
							verifications.verify(SeleniumVerifications.Selected, "", "OrderScreenCancelWithRefund", false);
						}
					}
				}
			}
		EnvironmentSetup.replaceData = false;
		executeStep.performAction(SeleniumActions.Click, "", "OrderScreenCancelOrderOKBtn");
		verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",true);		
		}
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Excception while getting Cancel Orders :: " + e.toString());
		}
	}
	
	public void addOSPOrder(){
		EnvironmentSetup.selectByPartMatchInDropDown = true;                       // Added by Abhishek
		executeStep.performAction(SeleniumActions.Select, "OrderPageBillNoChoice", "OrdersScreenBillNumber");
		verifications.verify(SeleniumVerifications.Selected, "OrderPageBillNoChoice", "OrdersScreenBillNumber", true);     //changed by Abhishek
		EnvironmentSetup.selectByPartMatchInDropDown = false;                       // Added by Abhishek
		addOrderItem("OrderItem","OrdersPage");
		executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenSave");
		verifications.verify(SeleniumVerifications.Appears, "", "OrdersPage", false);				
	}
	public void addOrderItem(String lineItemId, String calledFrom){
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
	
	public void addOrderPrescribingDoc()
	{
		//executeStep.performAction(SeleniumActions.Select, "OrderPageBillNoChoice", "OrdersScreenBillNumber");
//		verifications.verify(SeleniumVerifications.Selected, "OrderPageBillNoChoice", "OrdersScreenBillNumber", false);
		EnvironmentSetup.LineItemIdForExec = "OrderItem";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		addOrderItem("OrderItem","LaboratoryOrderTestsPage");
		executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenSave");
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "", "LaboratoryOrderTestsPage", false);				
	}
	
	public void addOrder(String lineItemId){
		executeStep.performAction(SeleniumActions.Select, "OrderPageBillNoChoice", "OrdersScreenBillNumber");
		verifications.verify(SeleniumVerifications.Selected, "OrderPageBillNoChoice", "OrdersScreenBillNumber", false);
		addOrderItem(lineItemId, "OrdersPage");
		executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenSave");
		verifications.verify(SeleniumVerifications.Appears, "", "OrdersPage", false);				
	}
	
	public void addOperationOrder()
	{
		EnvironmentSetup.selectByPartMatchInDropDown = true;                                           //Changed by Abhishek
		executeStep.performAction(SeleniumActions.Select, "ItemOTChargeType", "OTChargeType");
		verifications.verify(SeleniumVerifications.Selected, "ItemOTChargeType", "OTChargeType", false);
		executeStep.performAction(SeleniumActions.Enter, "ItemOrderEndTime","AddItemOrderEndTime");
		verifications.verify(SeleniumVerifications.Entered, "ItemOrderEndTime","AddItemOrderEndTime",false);
		executeStep.performAction(SeleniumActions.Click, "", "AddItemNextButton");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "", "AddItemOperationDetailsScreen", false);		
		executeStep.performAction(SeleniumActions.Enter, "OperationalDetailsPrimarySurgeon","AddItemOperationalDetailsPrimarySurgeon");
		verifications.verify(SeleniumVerifications.Entered, "OperationalDetailsPrimarySurgeon","AddItemOperationalDetailsPrimarySurgeon",false);
		executeStep.performAction(SeleniumActions.Select, "OperationalDetailsTheatre", "AddItemOperationalDetailsTheatre");
		verifications.verify(SeleniumVerifications.Selected, "OperationalDetailsTheatre", "AddItemOperationalDetailsTheatre", false);
		
		EnvironmentSetup.selectByPartMatchInDropDown = false;                                          //Changed by Abhishek
		executeStep.performAction(SeleniumActions.Click, "", "AddItemOperationDetailsAddButton");
		verifications.verify(SeleniumVerifications.Appears, "", "AddItemScreen", false);
		
	}
	
	public void addMultiVisitPakageWithnewBill(String lineItemId)
    {
		executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenAddButton");
		verifications.verify(SeleniumVerifications.Appears, "", "AddItemScreen", false);
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			executeStep.performAction(SeleniumActions.Enter, "Item","OrderAddItemScreenItemField");
			verifications.verify(SeleniumVerifications.Appears, "","OrderAddItemScreenItemList",false);
			executeStep.performAction(SeleniumActions.Click, "","OrderAddItemScreenItemList");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
			executeStep.performAction(SeleniumActions.Click, "","AddItemNextButton");
			verifications.verify(SeleniumVerifications.Appears, "","MultiVisitPkgScreen",false);

			// get the number of items in the second screen and loop through it to click the check box

			int rowCount1 = this.executeStep.getDriver().findElements(By.xpath("//tr/td/input[@name='select_item']")).size();
			System.out.println(rowCount1+ "........................................");
			for (int j=2; j<rowCount1+2; j++){
				EnvironmentSetup.intGlobalLineItemCount = j;
				executeStep.performAction(SeleniumActions.Click, "","MultiVisitPkgCheckBox");
				verifications.verify(SeleniumVerifications.Appears, "","MultiVisitPkgCheckBox",true);

			}
			executeStep.performAction(SeleniumActions.Enter, "ItemQuantity","MultiVisitPkgOrderQuantity");
			verifications.verify(SeleniumVerifications.Appears, "","MultiVisitPkgOrderQuantity",false);
			//EnvironmentSetup.intGlobalLineItemCount = 0;
			executeStep.performAction(SeleniumActions.Click, "","OrderAddItemScreenAddButton");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
			executeStep.performAction(SeleniumActions.Click, "","OrderAddItemScreenCloseButton");
			verifications.verify(SeleniumVerifications.Appears, "", "OrdersPage", false);
			executeStep.performAction(SeleniumActions.Click, "","OrdersScreenSave");
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			

		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;

    }
	
	public void addMultiVisitPakageWithOldBill(String lineItemId)
    {
		executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenAddButton");
		verifications.verify(SeleniumVerifications.Appears, "", "AddItemScreen", false);
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			executeStep.performAction(SeleniumActions.Enter, "Item","OrderAddItemScreenItemField");
			verifications.verify(SeleniumVerifications.Appears, "","OrderAddItemScreenItemList",false);
			executeStep.performAction(SeleniumActions.Click, "","OrderAddItemScreenItemList");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
			executeStep.performAction(SeleniumActions.Click, "","AddItemNextButton");
			
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			
			executeStep.performAction(SeleniumActions.Click, "","OrderAddItemScreenCloseButton");
			verifications.verify(SeleniumVerifications.Appears, "", "OrdersPage", false);

		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;

    }
	
	
	public void addItemsWithMVP1(String lineItemId)
    {
		executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenAddButton");
		verifications.verify(SeleniumVerifications.Appears, "", "AddItemScreen", false);
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		EnvironmentSetup.UseLineItem = true;
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=1; i<=rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			executeStep.performAction(SeleniumActions.Enter, "Item","OrderAddItemScreenItemField");
			verifications.verify(SeleniumVerifications.Appears, "","OrderAddItemScreenItemList",false);
			executeStep.performAction(SeleniumActions.Click, "","OrderAddItemScreenItemList");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
			executeStep.performAction(SeleniumActions.Click, "","OrderAddItemScreenAddButton");
			
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			
			executeStep.performAction(SeleniumActions.Click, "","OrderAddItemScreenCloseButton");
			verifications.verify(SeleniumVerifications.Appears, "", "OrdersPage", false);

		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;

    }
	
	
	
}
