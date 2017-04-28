package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Sales {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public Sales(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Sales(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
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
			EnvironmentSetup.selectByPartMatchInDropDown = true;               // added by abhishek
			executeStep.performAction(SeleniumActions.Select, "SalesPageBillType","SalesPageBilltype");
			verifications.verify(SeleniumVerifications.Appears, "","SalesPage",true);
			EnvironmentSetup.selectByPartMatchInDropDown = false;              // added by abhishek
		}
		
		executeStep.performAction(SeleniumActions.Click, "","SalesPagePayAndPrint");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true); // Not appearing for bill now OP case on firefox
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPageHeader",false);
		verifications.verify(SeleniumVerifications.Opens, "","SalesReceipt",false);
		executeStep.performAction(SeleniumActions.CloseTab, "","SalesReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","SalesReceipt",false);
		
		System.out.println("Sales Done");
		closePrescriptionTab();
	}
	
	public void searchMRNo(){
		/*executeStep.performAction(SeleniumActions.Enter, "MRID","SalesPageHospitalName");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPageMRNOList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","SalesPageMRNOList");*/
		
		executeStep.performAction(SeleniumActions.Enter, "VisitID","SalesPageHospitalName");
		this.executeStep.getDriver().findElement(By.xpath("//input[@id='searchVisitId']")).sendKeys(Keys.RETURN);
		
		CommonUtilities.delay();                                                         //added by abhishek
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);            //added by abhishek
		executeStep.performAction(SeleniumActions.Accept, "","Framework");                 //added by abhishek
	}
	
	//Include entering OSPSalesDr after searchMRNO and then call  addItemsToSales to add items.
	// The part to add item in doPharmacySales can be removed after that
	public void doPharmacySales(){

		System.out.println("Inside Sales payAndPrint ");
		//searchMRNo();		
		verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.LineItemIdForExec = "PharmacySales";
		addItemsToSales();
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
			verifications.verify(SeleniumVerifications.Appears, "","SalesAddItemDropDown",false);
			
			executeStep.performAction(SeleniumActions.Click, "","SalesAddItemDropDown");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
			CommonUtilities.delay();
			//corrected the verify statement to entered from appear
			executeStep.performAction(SeleniumActions.Enter, "SalesQuantity","SalesPageItemSalesQty");
			verifications.verify(SeleniumVerifications.Entered, "SalesQuantity","SalesPageItemSalesQty",true);
			//CommonUtilities.delay();
			executeStep.performAction(SeleniumActions.Click, "","SalesPageItemAddButton");
			//verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
			//CommonUtilities.delay();
			
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
			
			EnvironmentSetup.lineItemCount ++;
	
		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
		executeStep.performAction(SeleniumActions.Click, "","SalesPageItemClose");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",true);	
	}
	public void doSales(String LineItemId, String BillType){
		populateSalesPageDetails(LineItemId, BillType); //Modified Tejaswini
		System.out.println("Before Sales");
		saveSalesPage();//Modified Tejaswini		
		System.out.println("After Sales");
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
	public void salesDepositOff(String LineItemId, String BillType, String patientType){
		
		System.out.println("Inside Sales payAndPrint ");

		searchMRNo();
		verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
		EnvironmentSetup.LineItemIdForExec = LineItemId;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		EnvironmentSetup.UseLineItem = true;
		addItemsToSales();
		executeStep.performAction(SeleniumActions.Select, BillType,"SalesPageBilltype");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",true);
		if (patientType == "IP"){
		executeStep.performAction(SeleniumActions.Enter, "SalesDepositSetOff","SalesDepositSetOffField");
		verifications.verify(SeleniumVerifications.Entered, "SalesDepositSetOff","SalesDepositSetOffField",false);
		}
		else{
			executeStep.performAction(SeleniumActions.Enter, "SalesDepositSetOff","SalesOPDepositSetOffField");
			verifications.verify(SeleniumVerifications.Entered, "SalesDepositSetOff","SalesOPDepositSetOffField",false);
		}
		executeStep.performAction(SeleniumActions.Click, "","SalesPagePayAndPrint");
		//verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		//executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",false);
		verifications.verify(SeleniumVerifications.Opens, "","SalesReceipt",false);
		executeStep.performAction(SeleniumActions.CloseTab, "","SalesReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","SalesReceipt",false);	

		System.out.println("Sales Done");
	}
	public void closePrescriptionTab(){
		verifications.verify(SeleniumVerifications.Opens, "","SalesReceipt",true);
		executeStep.performAction(SeleniumActions.CloseTab, "","SalesReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","SalesReceipt",true);
		verifications.verify(SeleniumVerifications.Opens, "","SalesReceipt",true);
		executeStep.performAction(SeleniumActions.CloseTab, "","SalesReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","SalesReceipt",true);		
	}
	
	public void saveSalesPage(){ //Modified Tejaswini
		executeStep.performAction(SeleniumActions.Click, "","SalesPagePayAndPrint");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		try{
			verifications.verify(SeleniumVerifications.Appears, "","SalesPage",false);
			verifications.verify(SeleniumVerifications.Opens, "","SalesReceipt",false);
			executeStep.performAction(SeleniumActions.CloseTab, "","SalesReceipt");
			verifications.verify(SeleniumVerifications.Closes, "","SalesReceipt",false);
		} catch (Exception e){
			System.out.println("Sales Receipt not generated.");
		}
	}
	
	public void populateSalesPageDetails(String LineItemId, String BillType){ //Modified Tejaswini
		if (LineItemId!=""){
			searchMRNo();	
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
			EnvironmentSetup.LineItemIdForExec = LineItemId;
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
			EnvironmentSetup.UseLineItem = true;
			addItemsToSales();
		}
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true); //added by Reena to accept the nonclaimable pop up
     	executeStep.performAction(SeleniumActions.Accept, "","Framework"); 
		EnvironmentSetup.selectByPartMatchInDropDown = true;
		executeStep.performAction(SeleniumActions.Select, BillType,"SalesPageBilltype");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		//verifications.verify(SeleniumVerifications.Selected,BillType ,"SalesPageBilltype",false);       
		verifications.verify(SeleniumVerifications.Appears, "","SalesPage",true);
		EnvironmentSetup.selectByPartMatchInDropDown = false;           //Added by abhishek
		executeStep.performAction(SeleniumActions.Select,"DiscountAuthName" ,"DiscountAuthNameDropDown");               //added by abhishek
		verifications.verify(SeleniumVerifications.Selected,"DiscountAuthName" ,"DiscountAuthNameDropDown",true);       //added by abhishek	
	}
	
	public void doAddToBillSales(String lineItemIdForExec, String billNo, String billType){
		
		populateSalesPageDetails(lineItemIdForExec, billType);
		WebElement we = this.executeStep.getDriver().findElement(By.xpath("//td[@class='forminfo']//select[@id='billType']"));
		Select picklist = new Select(we);
		String selectOption = "";
		if (billType.contains("AddToBill")){
			selectOption = "Add to bill: " + billNo;
			System.out.println("Bill Type to select is :: " + selectOption);
			picklist.selectByVisibleText(selectOption.trim());
		} else {
			EnvironmentSetup.selectByPartMatchInDropDown = true;
			executeStep.performAction(SeleniumActions.Select, "AddToBillPaymentType","SalesPageRaiseBillDropdown");
			verifications.verify(SeleniumVerifications.Appears, "","SalesReturnPage",false);	
			EnvironmentSetup.selectByPartMatchInDropDown = false;
		}
		saveSalesPage();
	}
}
