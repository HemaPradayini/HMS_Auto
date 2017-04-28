package reusableFunctions;

/**
 * Author: Tejaswini Changed on 27th Feb 2017
 * 
 */
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class RaisePatientIndent {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public RaisePatientIndent(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public RaisePatientIndent(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void savePatientIndent(String lineItemForExec, boolean salesIndicator){
		
		System.out.println("Inside RaisePatientIndent savePatientIndent ");
		
		String landingPage = "";
		if (salesIndicator){
			landingPage = "RaisePatientIndentPage";
		} else{
			landingPage = "RaisePatientReturnIndentScreen";
		}
	/*		
		MRNoSearch mrNoSearch = new MRNoSearch(executeStep, verifications);
		mrNoSearch.searchMRNo("RaisePatientIndentMRNoField", "RaisePatientIndentMRNoList", "RaisePatientIndentMRNoFindButton", landingPage);
*/
		
		// If medicine added earlier to this Raise Patient Indent, as soon as MR id search button is pressed
		// a dialogue box appears. Hence, instead of using MRNOSearch the below is added.
		
		executeStep.performAction(SeleniumActions.Enter, "MRID","RaisePatientIndentMRNoField");
		verifications.verify(SeleniumVerifications.Appears, "MRID","RaisePatientIndentMRNoList",true);
		
		executeStep.performAction(SeleniumActions.Click, "MRID","RaisePatientIndentMRNoList");
		verifications.verify(SeleniumVerifications.Entered, "MRID","RaisePatientIndentMRNoField",true);	

		executeStep.performAction(SeleniumActions.Click, "","RaisePatientIndentMRNoFindButton");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		//verifications.verify(SeleniumVerifications.Appears, "","RaisePatientIndentPage",false);
		//Changed by Tejaswini
		verifications.verify(SeleniumVerifications.Appears, "",landingPage,false);
		
		executeStep.performAction(SeleniumActions.Select, "IndentStore","RaisePatientIndentStoreDropDown");
		verifications.verify(SeleniumVerifications.Selected, "IndentStore","RaisePatientIndentStoreDropDown",false);
		
		executeStep.performAction(SeleniumActions.Select, "IndentStatus","RaisePatientIndentStatusDropDown");
		verifications.verify(SeleniumVerifications.Selected, "IndentStatus","RaisePatientIndentStatusDropDown",false);
		executeStep.performAction(SeleniumActions.Click, "","RaisePatientIndentAddItemButton");
		verifications.verify(SeleniumVerifications.Appears, "","RaisePatientIndentAddItemDiv",false);

		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.lineItemCount=0;
		EnvironmentSetup.LineItemIdForExec = lineItemForExec;
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		//DbFunctions dbFunction = new DbFunctions();
		int rowCount=0;
		DbFunctions dbFunction = new DbFunctions();
		rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=0; i<rowCount; i++){			
			EnvironmentSetup.UseLineItem = true;
			executeStep.performAction(SeleniumActions.Enter, "IndentItem","AddItemPageItemField");
			verifications.verify(SeleniumVerifications.Appears,"","RaisePatientIndentAddItemDropDown",false);
			
			executeStep.performAction(SeleniumActions.Click, "","RaisePatientIndentAddItemDropDown");
			verifications.verify(SeleniumVerifications.Appears, "","RaisePatientIndentAddItemDiv",false);
			
			CommonUtilities.delay();
			
			if (salesIndicator){
				executeStep.performAction(SeleniumActions.Enter, "IndentItemQty","AddItemPageReqQtyField");
				verifications.verify(SeleniumVerifications.Entered, "IndentItemQty","AddItemPageReqQtyField",false);
			} else {
				executeStep.performAction(SeleniumActions.Enter, "IndentItemReturnedQty","AddItemPageReqQtyField");
				verifications.verify(SeleniumVerifications.Entered, "IndentItemReturnedQty","AddItemPageReqQtyField",false);
			}

			EnvironmentSetup.UseLineItem = false;

			executeStep.performAction(SeleniumActions.Click, "","AddItemPageAddButton");
			verifications.verify(SeleniumVerifications.Appears, "","RaisePatientIndentAddItemDiv",false);
			CommonUtilities.delay();

			EnvironmentSetup.lineItemCount ++;
		}
		EnvironmentSetup.lineItemCount = 0;
		executeStep.performAction(SeleniumActions.Click, "","AddItemPageCancelButton");
		verifications.verify(SeleniumVerifications.Appears, "",landingPage,true);//Synchronisation issue
				
		executeStep.performAction(SeleniumActions.Click, "","RaisePatientIndentSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditPatientIndentScreen",true);//Synchronisation issue

		//Added by Tejaswini - Bug fix for not fetching the salestypedropdown in the sales/salesreturns page
		EnvironmentSetup.UseLineItem = false;
		EnvironmentSetup.LineItemIdForExec = "";
		//Added by Tejaswini - Bug fix for not fetching the salestypedropdown in the sales/salesreturns page
		System.out.println("Patient Indent Saved");

	}
    public void doSalesOrSalesReturnsForIndent(boolean salesFlag, String lineItemId){           
    	searchPatientIndent();                                    // changed by abhishek
    	 
         executeStep.performAction(SeleniumActions.Click, "","PatientIndentListSearchResultsTable");
        if (salesFlag){
            verifications.verify(SeleniumVerifications.Appears, "","PatientIndentSalesLink",false);
            executeStep.performAction(SeleniumActions.Click, "","PatientIndentSalesLink");
            verifications.verify(SeleniumVerifications.Appears, "","SalesPage",false);
            Sales sales = new Sales(executeStep, verifications);
            sales.doSales("", "AddToBillPaymentType");
            sales.closePrescriptionTab();
        } else {
            verifications.verify(SeleniumVerifications.Appears, "","PatientIndentSalesReturnsLink",false);
            executeStep.performAction(SeleniumActions.Click, "","PatientIndentSalesReturnsLink");
            verifications.verify(SeleniumVerifications.Appears, "","SalesReturnPage",false);
            SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
            //salesReturns.doSalesReturn("", "OP");
			salesReturns.populateSalesReturnsPage(lineItemId, "OP");
			salesReturns.saveSalesReturns();

        }
		//Added by Tejaswini - Bug fix for not fetching the salestypedropdown in the sales/salesreturns page
		EnvironmentSetup.UseLineItem = false;
		EnvironmentSetup.LineItemIdForExec = "";
		//Added by Tejaswini - Bug fix for not fetching the salestypedropdown in the sales/salesreturns page
		}
    
    public void searchPatientIndent(){                                  // created by abhishek
    	executeStep.performAction(SeleniumActions.Click, "","PatientIndentsListMoreOptionsLink");
        verifications.verify(SeleniumVerifications.Appears, "","PatientIndentsListMoreOptions",false);

        executeStep.performAction(SeleniumActions.Enter, "MRID","PatientIndentListMRNo");
        verifications.verify(SeleniumVerifications.Appears, "","PatientIndentListMRNoSearchResultsList",false);
        executeStep.performAction(SeleniumActions.Click, "","PatientIndentListMRNoSearchResultsList");
        verifications.verify(SeleniumVerifications.Entered, "MRID","PatientIndentListMRNo",false);
        executeStep.performAction(SeleniumActions.Click, "","PatientIndentListMRNoSearchBtn");
        verifications.verify(SeleniumVerifications.Appears, "","PatientIndentSearchListPage",false);
    }
    
    public void savePatientIndentIssue(boolean issueFlag){                  // added by Abhishek
    	executeStep.performAction(SeleniumActions.Click, "","PatientIndentListSearchResultsTable");
    	PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
    	if(issueFlag){
			CommonUtilities.delay(); 
			verifications.verify(SeleniumVerifications.Appears, "","PatientIndentIssueLink",false);
			executeStep.performAction(SeleniumActions.Click, "","PatientIndentIssueLink");
			patientIssue.saveIssue();
			//executeStep.performAction(SeleniumActions.Click, "","PatientIssuePageSaveButton");
    	}else{
    		verifications.verify(SeleniumVerifications.Appears, "","PatientIndentIssueReturnLink",false);        		
    		executeStep.performAction(SeleniumActions.Click, "","PatientIndentIssueReturnLink");
    		patientIssue.saveIssueReturns();
    		/*verifications.verify(SeleniumVerifications.Appears, "","PatientIssueReturnPage",false);
    		executeStep.performAction(SeleniumActions.Click, "","PatientIssueReturnPageSaveButton");*/
    	}
		
	/*	verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Opens, "","StockPatientIssueReceipt",true);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","StockPatientIssueReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","StockPatientIssueReceipt",false);*/
		
		
    }
    public void doSalesOrSalesReturnsForIndentForBillNo(boolean salesFlag, String lineItemId, String salesBillNumber, String patientBillNo){           
    	searchPatientIndent();                                    // changed by abhishek
    	 
         executeStep.performAction(SeleniumActions.Click, "","PatientIndentListSearchResultsTable");
        if (salesFlag){
            verifications.verify(SeleniumVerifications.Appears, "","PatientIndentSalesLink",false);
            executeStep.performAction(SeleniumActions.Click, "","PatientIndentSalesLink");
            verifications.verify(SeleniumVerifications.Appears, "","SalesPage",false);
            Sales sales = new Sales(executeStep, verifications);
            sales.doAddToBillSales("", patientBillNo, "AddToBillPaymentType");
            sales.closePrescriptionTab();
        } else {
            verifications.verify(SeleniumVerifications.Appears, "","PatientIndentSalesReturnsLink",false);
            executeStep.performAction(SeleniumActions.Click, "","PatientIndentSalesReturnsLink");
            verifications.verify(SeleniumVerifications.Appears, "","SalesReturnPage",false);
            SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
            //salesReturns.doSalesReturn("", "OP");billNo
			salesReturns.doSalesReturnByBillNumber(lineItemId, "OP", salesBillNumber, patientBillNo, "AddToBill");
			salesReturns.saveSalesReturns();

        }
    }


}
