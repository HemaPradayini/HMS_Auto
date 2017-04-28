package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class SalesBkUp {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public SalesBkUp(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public SalesBkUp(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void payAndPrint(boolean billTypeDefaulted){
		
		System.out.println("Inside Sales payAndPrint ");

		//Vitals Reusable
		//executeStep.getDriver().findElement(By.xpath("//select[@id='sampleTypeForTests']")).click();
		searchMRNo();
		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",false);

		if (!billTypeDefaulted){
			executeStep.performAction(SeleniumActions.Select, "SalesPageBillType","SalesPageBilltype");
			verifications.verify(SeleniumVerifications.Appears, "","SalesPage",true);
		}
		
		executeStep.performAction(SeleniumActions.Click, "","SalesPagePayAndPrint");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPageHeader",false);
		verifications.verify(SeleniumVerifications.Opens, "","SalesReceipt",false);
		executeStep.performAction(SeleniumActions.CloseTab, "","SalesReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","SalesReceipt",false);
		
		System.out.println("Sales Done");
	}
	
	public void searchMRNo(){
		executeStep.performAction(SeleniumActions.Enter, "MRID","SalesPageHospitalName");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPageMRNOList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","SalesPageMRNOList");
	}
	
	public void doPharmacySales(){
		System.out.println("Inside Sales payAndPrint ");

		searchMRNo();
		verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
		try{
			EnvironmentSetup.UseLineItem = true;
			EnvironmentSetup.LineItemIdForExec = "PharmacySales";
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
			DbFunctions dbFunction = new DbFunctions();
			String dataSet = "";
			dataSet = this.executeStep.getDataSet();
			int rowCount = dbFunction.getRowCount(dataSet);
			System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
			for(int i=0; i<rowCount; i++){	
				EnvironmentSetup.UseLineItem = true;
				executeStep.performAction(SeleniumActions.Enter, "SalesItem","SalesPageAddItem");
				verifications.verify(SeleniumVerifications.Appears, "","SalesAddItemDropDown",true);
				
				executeStep.performAction(SeleniumActions.Click, "","SalesAddItemDropDown");
				verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
				//CommonUtilities.delay();
				executeStep.performAction(SeleniumActions.Enter, "SalesQuantity","SalesPageItemSalesQty");
				verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
				//CommonUtilities.delay();
				executeStep.performAction(SeleniumActions.Click, "","SalesPageItemAddButton");
				verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
				//CommonUtilities.delay();
				verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
				executeStep.performAction(SeleniumActions.Accept, "","Framework");
				
				EnvironmentSetup.lineItemCount ++;
		//		verifications.verify(SeleniumVerifications.Entered, "DiagnosisCode","ConsultMgtScreenDiagRow",true);
			}
			EnvironmentSetup.lineItemCount =0;
			EnvironmentSetup.UseLineItem = false;
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Exception is :: " + e.toString());
		}
		executeStep.performAction(SeleniumActions.Click, "","SalesPageItemClose");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");

		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",true);	
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Enter,"OSPSalesDoctor","SalesPageDoctorName");
		verifications.verify(SeleniumVerifications.Entered, "OSPSalesDoctor","SalesPageDoctorName",true);	
		
		executeStep.performAction(SeleniumActions.Select, "SalesPageBillType","SalesPageRaiseBillDropdown");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",true);	
		
		executeStep.performAction(SeleniumActions.Click, "","SalesPagePayAndPrint");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",false);
		verifications.verify(SeleniumVerifications.Opens, "","SalesReceipt",false);
		executeStep.performAction(SeleniumActions.CloseTab, "","SalesReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","SalesReceipt",false);		
	}
	
	private void addItemsToSales(){
		DbFunctions dbFunction = new DbFunctions();
		String dataSet = "";
		dataSet = this.executeStep.getDataSet();
		int rowCount = dbFunction.getRowCount(dataSet);
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=0; i<rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			executeStep.performAction(SeleniumActions.Enter, "SalesItem","SalesPageAddItem");
			verifications.verify(SeleniumVerifications.Appears, "","SalesAddItemDropDown",true);
			
			executeStep.performAction(SeleniumActions.Click, "","SalesAddItemDropDown");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
			//CommonUtilities.delay();
			executeStep.performAction(SeleniumActions.Enter, "SalesQuantity","SalesPageItemSalesQty");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
			//CommonUtilities.delay();
			executeStep.performAction(SeleniumActions.Click, "","SalesPageItemAddButton");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
			//CommonUtilities.delay();
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			
			EnvironmentSetup.lineItemCount ++;
	//		verifications.verify(SeleniumVerifications.Entered, "DiagnosisCode","ConsultMgtScreenDiagRow",true);
		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
		executeStep.performAction(SeleniumActions.Click, "","SalesPageItemClose");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",true);	
	}
	
	public void doAddToBillSales(){
		searchMRNo();
		verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
		EnvironmentSetup.LineItemIdForExec = "AddToBillSales";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		EnvironmentSetup.UseLineItem = true;
		addItemsToSales();
		executeStep.performAction(SeleniumActions.Select, "AddToBillPaymentType","SalesPageBilltype");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",true);
		executeStep.performAction(SeleniumActions.Click, "","SalesPagePayAndPrint");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",false);
		verifications.verify(SeleniumVerifications.Opens, "","SalesReceipt",false);
		executeStep.performAction(SeleniumActions.CloseTab, "","SalesReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","SalesReceipt",false);
	}
	
	public void doBillNowSales(){
		searchMRNo();
		verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
		EnvironmentSetup.LineItemIdForExec = "BillNowInSale";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		EnvironmentSetup.UseLineItem = true;
		addItemsToSales();
		executeStep.performAction(SeleniumActions.Select, "BillNowInsurance","SalesPageBilltype");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",true);
		executeStep.performAction(SeleniumActions.Click, "","SalesPagePayAndPrint");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",false);
		verifications.verify(SeleniumVerifications.Opens, "","SalesReceipt",false);
		executeStep.performAction(SeleniumActions.CloseTab, "","SalesReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","SalesReceipt",false);
	}
	
	public void IPPayAndPrint(){
		
		System.out.println("Inside Sales payAndPrint ");

		//Vitals Reusable
		//executeStep.getDriver().findElement(By.xpath("//select[@id='sampleTypeForTests']")).click();
		searchMRNo();
		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",false);

		
		executeStep.performAction(SeleniumActions.Click, "","SalesPagePayAndPrint");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPageHeader",false);
		verifications.verify(SeleniumVerifications.Opens, "","SalesReceipt",false);
		executeStep.performAction(SeleniumActions.CloseTab, "","SalesReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","SalesReceipt",false);

		System.out.println("Sales Done");
	}	
}
