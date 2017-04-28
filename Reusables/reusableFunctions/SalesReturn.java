package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import java.sql.Driver;
import java.util.List;

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

public class SalesReturn {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public SalesReturn(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public SalesReturn(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}

	public void searchMRNo(){
		executeStep.performAction(SeleniumActions.Enter, "MRID","SalesPageHospitalName");
		verifications.verify(SeleniumVerifications.Appears, "","SalesPageMRNOList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","SalesPageMRNOList");
	}

	public void doSalesReturn(String lineItemIdForExec, String patientType){
	
			searchMRNo();
			verifications.verify(SeleniumVerifications.Appears, "","SalesReturnPage",false);
			/*if(patientType =="OSP"){
				setOSPDoctor(patientType);
			}*/
			if (lineItemIdForExec != ""){
				executeStep.performAction(SeleniumActions.Click, "","SalesReturnAddNewItembutton");
				verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
			}
			populateSalesReturnsPage(lineItemIdForExec, patientType);
			saveSalesReturns();
	}
	public void setOSPDoctor(String patientType){
		
			executeStep.performAction(SeleniumActions.Enter, "OSPSalesDoctor","SalesPageDoctorName");
			verifications.verify(SeleniumVerifications.Appears, "","SalesReturnDoctorNameDropDown",false);
			
			executeStep.performAction(SeleniumActions.Click, "OSPSalesDoctor","SalesReturnDoctorNameDropDown");
			verifications.verify(SeleniumVerifications.Appears, "OSPSalesDoctor","SalesPageDoctorName",false);
		
	}
	
	public void populateSalesReturnsPage(String lineItemIdForExec, String patientType){
			if (lineItemIdForExec != ""){			
				try{
					EnvironmentSetup.UseLineItem = true;
					EnvironmentSetup.lineItemCount = 0;
					EnvironmentSetup.LineItemIdForExec = lineItemIdForExec;
					System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
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
						verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
						//CommonUtilities.delay();
						executeStep.performAction(SeleniumActions.Enter, "ReturnedQuantity","SalesPageItemSalesQty");
						verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
						//CommonUtilities.delay();
						executeStep.performAction(SeleniumActions.Click, "","SalesPageItemAddButton");
						verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
						executeStep.performAction(SeleniumActions.Accept, "","Framework");
						verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
						//CommonUtilities.delay();				
	
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
			}

			verifications.verify(SeleniumVerifications.Appears, "","SalesReturnPage",false);	
			CommonUtilities.delay();
			if(patientType =="OSP"){
				executeStep.performAction(SeleniumActions.Enter,"OSPSalesDoctor","SalesPageDoctorName");
				verifications.verify(SeleniumVerifications.Entered, "OSPSalesDoctor","SalesPageDoctorName",true);	
			}
			EnvironmentSetup.selectByPartMatchInDropDown = true;
			executeStep.performAction(SeleniumActions.Select, "SalesPageBillType","SalesPageRaiseBillDropdown");
		//	verifications.verify(SeleniumVerifications.Selected, "SalesPageBillType","SalesPageRaiseBillDropdown",true);
			
			verifications.verify(SeleniumVerifications.Appears, "","SalesReturnPage",false);	
			EnvironmentSetup.selectByPartMatchInDropDown = false;
			
	}
	
	public void saveSalesReturns(){			
			System.out.println("Before Sales Return");
			executeStep.performAction(SeleniumActions.Click, "","SalesPagePayAndPrint");
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		//	executeStep.performAction(SeleniumActions.CaptureScreenshot, "","SalesPagePayAndPrint1");
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
		//	executeStep.performAction(SeleniumActions.CaptureScreenshot, "","SalesPagePayAndPrint2");
			verifications.verify(SeleniumVerifications.Appears, "","SalesPage",true);
			verifications.verify(SeleniumVerifications.Opens, "","SalesReceipt",true);
			executeStep.performAction(SeleniumActions.CloseTab, "","SalesReceipt");
			verifications.verify(SeleniumVerifications.Closes, "","SalesReceipt",true);		
		//	executeStep.performAction(SeleniumActions.CaptureScreenshot, "","SalesPagePayAndPrint3");
			System.out.println("After Sales Return");
	}
	
	public void doSalesReturnByBillNumber(String lineItemIdForExec, String patientType, String salesBillNo, String patientBillNo, String billType){
		
		searchBillNo(salesBillNo);
		verifications.verify(SeleniumVerifications.Appears, "","SalesReturnPage",false);
		//WebElement we = this.executeStep.getDriver().findElement(By.xpath("SalesReturnBillNumberField"));
		//we.sendKeys(Keys.ENTER);
		//this.executeStep.getDriver().switchTo().defaultContent();
		//this.executeStep.getDriver().switchTo().window(EnvironmentSetup.strMainWindowHandle);
		executeStep.performAction(SeleniumActions.Click, "","SalesReturnPage");
		verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);
		populateSalesReturnsPage(lineItemIdForExec, patientType);
		WebElement we = this.executeStep.getDriver().findElement(By.xpath("//td[@class='forminfo']//select[@id='billType']"));
		Select picklist = new Select(we);
		String selectOption = "";
		if (billType.contains("AddToBill")){
			selectOption = "Add to bill: " + patientBillNo;
			System.out.println("Bill Type to select is :: " + selectOption);
			picklist.selectByVisibleText(selectOption.trim());
		} else {
			EnvironmentSetup.selectByPartMatchInDropDown = true;
			executeStep.performAction(SeleniumActions.Select, "AddToBillPaymentType","SalesPageRaiseBillDropdown");
			verifications.verify(SeleniumVerifications.Appears, "","SalesReturnPage",false);	
			EnvironmentSetup.selectByPartMatchInDropDown = false;
		}
		saveSalesReturns();
	}
	
	public void searchBillNo(String billNumber){
		executeStep.performAction(SeleniumActions.Click, "","SalesReturnSearchByBillNoRadioBtn");
		verifications.verify(SeleniumVerifications.Appears, "","SalesReturnPage",false);	
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.LineItemIdForExec = billNumber;
		executeStep.performAction(SeleniumActions.Enter, "BillNumber","SalesReturnBillNumberField");
		EnvironmentSetup.UseLineItem = false;
		EnvironmentSetup.LineItemIdForExec = "";
	}

	
}
