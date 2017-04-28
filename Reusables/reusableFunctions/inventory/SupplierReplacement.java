package reusableFunctions.inventory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import GenericFunctions.CommonUtilities;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class SupplierReplacement {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	
	public SupplierReplacement(){

	   }

	   /**
	    * Use this
	    * @param AutomationID
	    */
	public SupplierReplacement(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		   this.executeStep = execStep;
		   this.verifications = verifications;
	   }
	
	
	public void clickReplaceMenuOption(){
		
		executeStep.performAction(SeleniumActions.Click, "","TxnNoRow");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnListReplaceMenu",true);
			
		executeStep.performAction(SeleniumActions.Click, "","SupplierReturnListReplaceMenu");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReplacementPage",true);
	}
	
	public void supplierOriginalDetails(){
		executeStep.performAction(SeleniumActions.Select, "SupplierReturnType", "ReturnTypeDropDown");
		verifications.verify(SeleniumVerifications.Selected, "SupplierReturnType","ReturnTypeDropDown",false);
		
		executeStep.performAction(SeleniumActions.Enter, "SupplierReturnRemarks", "ReturnRemarksField");
		verifications.verify(SeleniumVerifications.Entered, "SupplierReturnRemarks","ReturnRemarksField",false);
		
		
	}
	
	public void replaceSupplier(){
		

		executeStep.performAction(SeleniumActions.Enter, "StockEntryMRP","SupplierReplacementMRP");
		
		verifications.verify(SeleniumVerifications.Entered, "StockEntryMRP","SupplierReplacementMRP",false);

		this.executeStep.getDriver().findElement(By.xpath("//input[@id='mon1']")).sendKeys("12");
		this.executeStep.getDriver().findElement(By.xpath("//input[@name='h1year']")).sendKeys("50");
		
		executeStep.performAction(SeleniumActions.Enter, "StockEntryQuantity","SupplierReplacementReplaceQty");
		verifications.verify(SeleniumVerifications.Entered, "StockEntryQuantity","SupplierReplacementReplaceQty",true);

		
		

		executeStep.performAction(SeleniumActions.Enter, "StockEntryBatchNo","SupplierReplacementReplaceBatch");
		verifications.verify(SeleniumVerifications.Entered, "StockEntryBatchNo","SupplierReplacementReplaceBatch",false);
		
	}
	
public void replaceSupplier1(String lineItemId, boolean partial){
		
		EnvironmentSetup.LineItemIdForExec = lineItemId;
		EnvironmentSetup.lineItemCount =0;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		EnvironmentSetup.UseLineItem = true;
		executeStep.performAction(SeleniumActions.Enter, "StockEntryMRP","SupplierReplacementMRP");
		
		verifications.verify(SeleniumVerifications.Entered, "StockEntryMRP","SupplierReplacementMRP",false);

		this.executeStep.getDriver().findElement(By.xpath("//input[@id='mon1']")).sendKeys("12");
		this.executeStep.getDriver().findElement(By.xpath("//input[@id='h1year1']")).sendKeys("50");
		
		 
			WebElement itemRetQty1 = this.executeStep.getDriver().findElement(By.xpath("//tr[2]/td[7]"));
			String  itemQty1 = itemRetQty1.getText().trim();
			
			executeStep.performAction(SeleniumActions.Store, "SupplierReplacementQuantity","SupplierReturnsReturnedQty3");
		
			WebElement itemReplaceQty1 = this.executeStep.getDriver().findElement(By.xpath("//input[@id='hreplacingqty1']"));
			boolean itemRepQty1 = itemReplaceQty1.isEnabled();
			
			if(itemRepQty1){
			      if(partial){
		executeStep.performAction(SeleniumActions.Enter, "SupplierPartialReplacementQuantity","SupplierReplacementReplaceQty");
		verifications.verify(SeleniumVerifications.Entered, "SupplierPartialReplacementQuantity","SupplierReplacementReplaceQty",true);
			                    
			            }
			else{
				executeStep.performAction(SeleniumActions.Enter, "SupplierReplacementQuantity","SupplierReplacementReplaceQty");
				verifications.verify(SeleniumVerifications.Entered, "SupplierReplacementQuantity","SupplierReplacementReplaceQty",true);
				
			}
			}

		executeStep.performAction(SeleniumActions.Enter, "StockEntryBatchNo","SupplierReplacementReplaceBatch1");
		verifications.verify(SeleniumVerifications.Entered, "StockEntryBatchNo","SupplierReplacementReplaceBatch1",false);
		
		
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
	}
public void replaceSupplier2(String lineItemId, boolean partial){
		
	EnvironmentSetup.LineItemIdForExec = lineItemId;
	EnvironmentSetup.lineItemCount =0;
	System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

	EnvironmentSetup.UseLineItem = true;
		executeStep.performAction(SeleniumActions.Enter, "StockEntryMRP","SupplierReplacementMRP2");
		
		verifications.verify(SeleniumVerifications.Entered, "StockEntryMRP","SupplierReplacementMRP2",false);

		this.executeStep.getDriver().findElement(By.xpath("//input[@id='mon2']")).sendKeys("12");
		this.executeStep.getDriver().findElement(By.xpath("//input[@id='h1year2']")).sendKeys("50");
		
		WebElement itemRetQty1 = this.executeStep.getDriver().findElement(By.xpath("//tr[3]/td[7]"));
		String  itemQty1 = itemRetQty1.getText().trim();
		
		executeStep.performAction(SeleniumActions.Store, "SupplierReplacementQuantity","SupplierReturnsReturnedQty3");
	
		WebElement itemReplaceQty1 = this.executeStep.getDriver().findElement(By.xpath("//input[@id='hreplacingqty2']"));
		boolean itemRepQty1 = itemReplaceQty1.isEnabled();
		
		
		if(itemRepQty1){
		      if(partial){
	executeStep.performAction(SeleniumActions.Enter, "SupplierPartialReplacementQuantity","SupplierReplacementReplaceQty2");
	verifications.verify(SeleniumVerifications.Entered, "SupplierPartialReplacementQuantity","SupplierReplacementReplaceQty2",true);
		                    
		            }
		else{
			executeStep.performAction(SeleniumActions.Enter, "SupplierReplacementQuantity","SupplierReplacementReplaceQty2");
			verifications.verify(SeleniumVerifications.Entered, "SupplierReplacementQuantity","SupplierReplacementReplaceQty2",true);
			
		}
		}
		

		executeStep.performAction(SeleniumActions.Enter, "StockEntryBatchNo","SupplierReplacementReplaceBatch2");
		verifications.verify(SeleniumVerifications.Entered, "StockEntryBatchNo","SupplierReplacementReplaceBatch2",false);
		
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
	}
public void replaceSupplier3(String lineItemId, boolean partial){
	
	EnvironmentSetup.LineItemIdForExec = lineItemId;
	EnvironmentSetup.lineItemCount =0;
	System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

	EnvironmentSetup.UseLineItem = true;
	
	executeStep.performAction(SeleniumActions.Enter, "StockEntryMRP","SupplierReplacementMRP3");
	
	verifications.verify(SeleniumVerifications.Entered, "StockEntryMRP","SupplierReplacementMRP3",false);

	this.executeStep.getDriver().findElement(By.xpath("//input[@id='mon3']")).sendKeys("12");
	this.executeStep.getDriver().findElement(By.xpath("//input[@id='h1year3']")).sendKeys("50");
	
	WebElement itemRetQty1 = this.executeStep.getDriver().findElement(By.xpath("//tr[4]/td[7]"));
	String  itemQty1 = itemRetQty1.getText().trim();
	
	executeStep.performAction(SeleniumActions.Store, "SupplierReplacementQuantity","SupplierReturnsReturnedQty3");

	WebElement itemReplaceQty1 = this.executeStep.getDriver().findElement(By.xpath("//input[@id='hreplacingqty3']"));
	boolean itemRepQty1 = itemReplaceQty1.isEnabled();
	
	if(itemRepQty1){
	      if(partial){
executeStep.performAction(SeleniumActions.Enter, "SupplierPartialReplacementQuantity","SupplierReplacementReplaceQty3");
verifications.verify(SeleniumVerifications.Entered, "SupplierPartialReplacementQuantity","SupplierReplacementReplaceQty3",true);
	                    
	            }
	else{
		executeStep.performAction(SeleniumActions.Enter, "SupplierReplacementQuantity","SupplierReplacementReplaceQty3");
		verifications.verify(SeleniumVerifications.Entered, "SupplierReplacementQuantity","SupplierReplacementReplaceQty3",true);
		
	}
	}
	executeStep.performAction(SeleniumActions.Enter, "StockEntryBatchNo","SupplierReplacementReplaceBatch3");
	verifications.verify(SeleniumVerifications.Entered, "StockEntryBatchNo","SupplierReplacementReplaceBatch3",false);
	
	EnvironmentSetup.lineItemCount =0;
	EnvironmentSetup.UseLineItem = false;
}
	public void clickReplace(){
		executeStep.performAction(SeleniumActions.Click, "","SupplierReplacementReturnButton");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierReturnsReplacementPage",true);
		
	}
}
