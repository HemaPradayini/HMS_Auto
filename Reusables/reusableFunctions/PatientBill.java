package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PatientBill {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	/** Class Variables
	Define the Class level variables here
	*/
	
	String PaymentDue ="";
	String BillPaymentType ="";
	String BillStatus = "";
	String PaymentStatus = "";
	String BillSubmitBtn = "";
	public static final String searchOptionOpenPaidIns = "OPEN_PAID_INS";
	public static final String searchOptionOpenPaidNonIns = "OPEN_PAID_NONINS";
	public static final String searchOptionOpenUnPaidIns = "OPEN_UNPAID_INS";
	public static final String searchOptionOpenUnPaidNonIns = "OPEN_UNPAID_NONINS";
	public static final String searchOptionFinalIns = "FINALIZED_INS";
	public PatientBill(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
		public PatientBill(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
			this.executeStep = execStep;
			this.verifications = verifications;
		}
	

		public void depositOff(String PatientType){
			
			if (PatientType == "IP"){
				executeStep.performAction(SeleniumActions.Click, "","PatientBillIPDepostRadioButton");
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
			}
			/*
			if (PatientType == "OP"){
				executeStep.performAction(SeleniumActions.Click, "","PatientBillGeneralDepostRadioButton");
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
			}
			*/
			executeStep.performAction(SeleniumActions.Enter, "BillDepositSetOff", "PatientBillDepositsSetOffField");
		//	verifications.verify(SeleniumVerifications.Entered, "BillDepositSetOff","PatientBillDepositsSetOffField",false);
					
			executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");
			// Though during manual run the dialog appears after enter, during script running
			//it happens after click
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		
			//verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		
		}
		
		public void doAdvancePayment(){

			MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
			mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");		
			
			executeStep.performAction(SeleniumActions.Click, "","BillListHospitalBill");
			verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
			
			executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
			verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
			executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
			
			executeStep.performAction(SeleniumActions.Select,"PaidPaymentStatus","BillPagePaymentStatus");
  		    verifications.verify(SeleniumVerifications.Selected, "PaidPaymentStatus","BillPagePaymentStatus",false);
			
  		    //saveInsPatientBillNowDetails("AdvanceBillPaymentType");
			/*
			if (isBillLater()){
				executeStep.performAction(SeleniumActions.Click,"","BillPagePaymentSpan");
			}
			*/
			settleBillDetails("YES", "AdvanceBillPaymentType");
			/*
			executeStep.performAction(SeleniumActions.Select,"PaymentStatus","BillPagePaymentStatus");                     // added by abhishek 15/2
			verifications.verify(SeleniumVerifications.Selected, "PaymentStatus","BillPagePaymentStatus",false); 
			
			verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",false);
			executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
			verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",false);
			*/
			//executeStep.performAction(SeleniumActions.Click, "","PatientPaymentsLink");                
			//verifications.verify(SeleniumVerifications.Appears, "","SearchReceiptsPage",false);
						
		//	editReceipt();
		}		
		
		
		public void editReceipt(){
			System.out.println("Inside editReceipt");
			executeStep.performAction(SeleniumActions.Click, "","PatientPaymentsLink");
			verifications.verify(SeleniumVerifications.Appears, "","SearchReceiptsPage",false);
			
			executeStep.performAction(SeleniumActions.Click, "","SearchReceiptsResultTable");
			verifications.verify(SeleniumVerifications.Appears, "","SearchReceiptsEditReceipt",false);
			
			executeStep.performAction(SeleniumActions.Click, "","SearchReceiptsEditReceipt");
			verifications.verify(SeleniumVerifications.Appears, "","EditReceiptScreen",false);
			
			executeStep.performAction(SeleniumActions.Enter, "ReceiptPaymentAmt","EditReceiptEditAmount");
			verifications.verify(SeleniumVerifications.Appears, "ReceiptPaymentAmt","EditReceiptScreen",false);
			
			executeStep.performAction(SeleniumActions.Click, "","EditReceiptScreenSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "","EditReceiptScreen",false);
			
			System.out.println("Completed editReceipt");
		}

		public void createBill(){
			
			System.out.println("Inside createBillAndOrderItems");

			executeStep.performAction(SeleniumActions.Enter, "MRID","BillPageMRNo");
			verifications.verify(SeleniumVerifications.Appears, "","BillPageMRNoSearchResults",false);
			
			executeStep.performAction(SeleniumActions.Click, "","BillPageMRNoSearchResults");
			verifications.verify(SeleniumVerifications.Appears, "","NewBillPage",false);
			
			executeStep.performAction(SeleniumActions.Click, "","PatientIssueReturnsFindButton");
			verifications.verify(SeleniumVerifications.Appears, "","NewBillPage",false);
			
			executeStep.performAction(SeleniumActions.Click, "","BillLaterRadioBtn");
			verifications.verify(SeleniumVerifications.Appears, "","NewBillPage",false);

			executeStep.performAction(SeleniumActions.Click, "","BillPageCreateBillBtn");
			verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
			executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");

	            System.out.println("Completed Creating Bill and Adding Order");
	            
	        }
		public int getNoOfBills(){
			System.out.println("Inside PatientBillScreen getNoOfBills ");
			
			int rowCount=this.executeStep.getDriver().findElements(By.xpath("//table[@id='resultTable']/tbody/tr")).size();
			System.out.println("The no of Rows in the Bills Table is :: " + rowCount);
			return rowCount;
		}
		
	
		public void advancedSearch(MRNoSearch mrnoSearch, String searchOption){                 //   changed by abhishek
			System.out.println("Inside searchForFinalizedBills");
			
			executeStep.performAction(SeleniumActions.Click, "","BillSearchScreenMoreOptions");
			verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
			
			switch (searchOption.toUpperCase()){
			case	"OPEN_PAID_INS":
	
				executeStep.performAction(SeleniumActions.Click, "","BillListPaymentStatusAllChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListPaymentStatusPaidChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				//Select insurance check box
				executeStep.performAction(SeleniumActions.Click, "","BillListBillInsuranceAllChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListBillInsuranceInsChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				break;
			case	"OPEN_PAID_NONINS":
				executeStep.performAction(SeleniumActions.Click, "","BillListPaymentStatusAllChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListPaymentStatusPaidChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				//Select Noninsurance check box
				executeStep.performAction(SeleniumActions.Click, "","BillListBillInsuranceAllChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListBillInsuranceNonInsChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				break;
			case	"OPEN_UNPAID_INS":

				executeStep.performAction(SeleniumActions.Click, "","BillListPaymentStatusAllChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListPaymentStatusUnpaidChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				//Select insurance check box
				executeStep.performAction(SeleniumActions.Click, "","BillListBillInsuranceAllChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListBillInsuranceInsChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				break;
			case	"OPEN_UNPAID_NONINS":
				executeStep.performAction(SeleniumActions.Click, "","BillListPaymentStatusAllChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListPaymentStatusUnpaidChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				//Select Noninsurance check box
				executeStep.performAction(SeleniumActions.Click, "","BillListBillInsuranceAllChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListBillInsuranceNonInsChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				break;
			case	"FINALIZED_INS":
				//Select Finalised bills check box
				executeStep.performAction(SeleniumActions.Click, "","BillListStatusOpenChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListStatusFinalizedChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				
				//Select insurance check box
				executeStep.performAction(SeleniumActions.Click, "","BillListBillInsuranceAllChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListBillInsuranceInsChBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				break;
			case "FINALIZED":				
				executeStep.performAction(SeleniumActions.Click, "","BillSearchScreenOpenCheckBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillSearchScreenFinalizedStatusCheckBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				break;
			case "UNPAID":
				executeStep.performAction(SeleniumActions.Click, "","BillSearchScreenUnpaidStatusCheckBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				break;
			case "HOSPITAL":
				executeStep.performAction(SeleniumActions.Click, "","BillListUsageTypeAllCheckBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListUsageTypeHospitalCheckBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				break;
			case "PHARMACY":
				executeStep.performAction(SeleniumActions.Click, "","BillListUsageTypeAllCheckBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListUsageTypePharmacyCheckBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				break;
			case "REFUND":
				executeStep.performAction(SeleniumActions.Click, "","BillListCreditNotIncludedCheckBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
				executeStep.performAction(SeleniumActions.Click, "","BillListCreditAllCheckBox");
				verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
			}

			mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");		
			
		}
		
		public void settleBills(String insuranceFlag, String paymentStatus,String patientType){
	    	   System.out.println(" inside settleOpenPaidInsBills");
	    	   MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
	    	   if (insuranceFlag.equalsIgnoreCase("YES") && paymentStatus.equalsIgnoreCase("PAID")){
	    		   advancedSearch(mrnoSearch, "OPEN_PAID_INS");
	    	   }
	    	   else if(insuranceFlag.equalsIgnoreCase("NO") && paymentStatus.equalsIgnoreCase("PAID")){
	    		   advancedSearch(mrnoSearch, "OPEN_PAID_NONINS");
	    	   }
				else if(insuranceFlag.equalsIgnoreCase("YES") && paymentStatus.equalsIgnoreCase("UNPAID")){
					advancedSearch(mrnoSearch, "OPEN_UNPAID_INS");
				}
				else if(insuranceFlag.equalsIgnoreCase("NO") && paymentStatus.equalsIgnoreCase("UNPAID")){
					advancedSearch(mrnoSearch, "OPEN_UNPAID_NONINS");
				}
				
	    	   int noOfOpenOrFinalizedBills = getNoOfBills();
	    	   
	    	 
	    	   for (int i = 0; i<noOfOpenOrFinalizedBills; i++){
	    		   EnvironmentSetup.intGlobalLineItemCount= 2;
	    		   
	    		   advanceBillNavigation();
	    		   
	    		   setBillStatus(insuranceFlag);
	    		  
	    		   if (patientType.equalsIgnoreCase("IP"))
	    		   {
	    			   WebElement we = null;
	   					try{
	   						we = this.executeStep.getDriver().findElement(By.xpath("//input[@name='_okToDischarge']"));
	   					}catch (Exception e){
	   					System.out.println("Webelement for given xpath not found" + e.toString());
	   					}
	   					if (we!=null){	
	   						executeStep.performAction(SeleniumActions.Check, "","PatientBillOkToDischargeCheckBox");
		    				verifications.verify(SeleniumVerifications.Checked, "","PatientBillOkToDischargeCheckBox",false);
	   					}		
	    		   }
	    		   settleBillDetails(insuranceFlag,"SettlementPaymentType");
	    		  
	    		   SiteNavigation navigation = new SiteNavigation(this.executeStep.getAutomationID(), this.executeStep.getDataSet()); 
	    		   navigation.navigateTo(this.executeStep.getDriver(), "OpenBills", "BillListPage");
	    		   if (insuranceFlag.equalsIgnoreCase("YES") && paymentStatus.equalsIgnoreCase("PAID")){
		    		   advancedSearch(mrnoSearch, "OPEN_PAID_INS");
		    	   }
		    	   else if(insuranceFlag.equalsIgnoreCase("NO") && paymentStatus.equalsIgnoreCase("PAID")){
		    		   advancedSearch(mrnoSearch, "OPEN_PAID_NONINS");
		    	   }
					else if(insuranceFlag.equalsIgnoreCase("YES") && paymentStatus.equalsIgnoreCase("UNPAID")){
						advancedSearch(mrnoSearch, "OPEN_UNPAID_INS");
					}
					else if(insuranceFlag.equalsIgnoreCase("NO") && paymentStatus.equalsIgnoreCase("UNPAID")){
						advancedSearch(mrnoSearch, "OPEN_UNPAID_NONINS");
					}					
	    		   noOfOpenOrFinalizedBills = getNoOfBills();
	    		   }
	    	   EnvironmentSetup.intGlobalLineItemCount=0;
	       }

	       
	       public void settleFinalizedInsBills(String insuranceFlag, String patientType){
	    	   System.out.println(" inside settleFinalizedInsBills");
	    	   MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
	    	   advancedSearch(mrnoSearch, "FINALIZED_INS");
	    	   int noOfOpenOrFinalizedBills = getNoOfBills();
	    	   EnvironmentSetup.intGlobalLineItemCount=2;
	    	   for (int i = 1; i< noOfOpenOrFinalizedBills; i++){    // changed now to check if it will go through all bills
	    		   
	    		   System.out.println(" why not selected select?");
	    		
	    		   advanceBillNavigation();
	    		   
	    		   //Added to enable click OK to discharge after Finalization as per TS-31 - Alamelu
	    		   
	    		   if (patientType.equalsIgnoreCase("IP"))
	    		   {
	    			   WebElement we = null;
	   					try{
	   						we = this.executeStep.getDriver().findElement(By.xpath("//input[@name='_okToDischarge']"));
	   					}catch (Exception e){
	   					System.out.println("Webelement for given xpath not found" + e.toString());
	   					}
	   					if (we!=null){	
	   						executeStep.performAction(SeleniumActions.Check, "","PatientBillOkToDischargeCheckBox");
		    				verifications.verify(SeleniumVerifications.Checked, "","PatientBillOkToDischargeCheckBox",false);
	   					}		
	    		   }
	   		   
	    		   EnvironmentSetup.selectByPartMatchInDropDown = true; 
	    		   executeStep.performAction(SeleniumActions.Select, "PatientBillBillLabel","PatientBillBillLabelDropDown");
	    		   verifications.verify(SeleniumVerifications.Selected, "PatientBillBillLabel","PatientBillBillLabelDropDown",true);
	    		   EnvironmentSetup.selectByPartMatchInDropDown = false; 	
	    		   
	    		   
	    		   settleBillDetails(insuranceFlag,"SettlementPaymentType");
	    		   
	    		   SiteNavigation navigation = new SiteNavigation(this.executeStep.getAutomationID(), this.executeStep.getDataSet());
	    		   navigation.navigateTo(this.executeStep.getDriver(), "OpenBills", "BillListPage");
					 
	    		   advancedSearch(mrnoSearch, "FINALIZED_INS");
	    		   noOfOpenOrFinalizedBills = getNoOfBills();
	    		   EnvironmentSetup.intGlobalLineItemCount++;
				}
	    	   	EnvironmentSetup.intGlobalLineItemCount = 0;
	    	   } 
	       
	       public void settleBillDetails(String insurance, String paymentType){
	    	   System.out.println(" inside settleBillDetails");
	    	   		
	    	   if (isBillLater()){                                                                           // added by abhishek
					executeStep.performAction(SeleniumActions.Click,"","BillPagePaymentSpan");
				}
	    	   if(!(paymentType.equalsIgnoreCase("BillRefundPaymentType"))){                                     //added by Reena
	    	    executeStep.performAction(SeleniumActions.Select, paymentType,"PatientBillPaymentType");
				verifications.verify(SeleniumVerifications.Selected, paymentType,"PatientBillPaymentType",true);
				
				executeStep.performAction(SeleniumActions.Store, "PaymentDue","PatientPaymentDue");
				executeStep.performAction(SeleniumActions.Enter, "PaymentDue","PatientBillPayField");
				verifications.verify(SeleniumVerifications.Entered, "PaymentDue","PatientBillPayField",false);
	    	   }
	    	   else //added by Reena for Refund
	    	   {
	    		   executeStep.performAction(SeleniumActions.Select, "ClosedBillStatus","PatientBillStatus");
    			   verifications.verify(SeleniumVerifications.Selected, "ClosedBillStatus","PatientBillStatus",false);
	    	   }
				if (isBillLater()){
					executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");	
				}
				else {
					if (insurance.equalsIgnoreCase("YES")){
						executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");	
					}
					else {
						executeStep.performAction(SeleniumActions.Click, "","PatientBillPayCloseBtn");//Changed from PayNSave to PayClose
					}
				}
				//verifications.verify(SeleniumVerifications.Opens, "","PatientBillReceipt",true);				
				//executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillReceipt");
				//verifications.verify(SeleniumVerifications.Closes, "","PatientBillReceipt",false);
				//verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
				try{
					verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
					executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
					verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);
					
				} catch (Exception e){
					System.out.println("Receipts Not Opened");
				}
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
	       
	       }
	       public void advanceBillNavigation(){
	    	   
	    	   SiteNavigation navigation = new SiteNavigation(this.executeStep.getAutomationID(), this.executeStep.getDataSet());
    		   
	    	   executeStep.performAction(SeleniumActions.Click, "","BillListPatientBills");                 // object changed by abhishek
	    	   verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",true);
	    	   executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
	    	   verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
	    	  executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
	       
	       }
	     
		public void addBillOrder(String lineItemId){
			// Navigation to Order from Order link in Bill
			System.out.println("Inside Adding Order");
			
			executeStep.performAction(SeleniumActions.Click, "","BillPageOrderLink");
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			
			verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",false);
			Order order = new Order(executeStep, verifications);
			order.addOrderItem(lineItemId,"OrdersPage");
			executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenSave");
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);	
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			verifications.verify(SeleniumVerifications.Appears, "", "OrdersPage", false);	
			
			System.out.println("Completed Add Bill Order");
		}
		public void addItemsIntoPatientBill(){
			// To add order item through Add New Item plus sign
			System.out.println("Inside PatientBillScreen savePatientBill ");
			EnvironmentSetup.LineItemIdForExec = "OrderBillItem";
			Order patientOrder = new Order(executeStep, verifications);
			executeStep.performAction(SeleniumActions.Click, "","PatientBillAddItemButton");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",false);		
			patientOrder.addOrderItem("OrderBillItem", "PatientBillScreen");
			executeStep.performAction(SeleniumActions.Store, "PaymentDue","PatientPaymentDue");
			executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);		
		}
		public void saveRegistrationBillDetails(String paymentType, String insurance){
			System.out.println("Inside savePatientBillDetails");
			
			if (insurance == "YES"){
				setBillStatus("YES");
				settleBillDetails("YES", paymentType);	
			}
			else {
				setBillStatus("NO");
				settleBillDetails("NO", paymentType);	
			}
				
			System.out.println("Completed savePatientBillDetails");
		}
	
		public void sponsorSettlementDetails(){
	    	   System.out.println(" inside sponsorSettlement");
	    	   boolean isBillLater = false;
	    	   WebElement we = null;
				if (isBillLater()){	
					executeStep.performAction(SeleniumActions.Click,"","BillPagePaymentSpan");
				}		
				executeStep.performAction(SeleniumActions.Select, "PrimarySponsorPaymentType","PatientBillPaymentType");
				verifications.verify(SeleniumVerifications.Selected, "PrimarySponsorPaymentType","PatientBillPaymentType",true);
				
				executeStep.performAction(SeleniumActions.Store, "SponsorDue","PatientBillSponsorDue");
				executeStep.performAction(SeleniumActions.Enter, "SponsorDue","PatientBillPayField");
				verifications.verify(SeleniumVerifications.Entered, "SponsorDue","PatientBillPayField",false);
				
				 EnvironmentSetup.selectByPartMatchInDropDown = true; 
	    		 executeStep.performAction(SeleniumActions.Select, "PatientBillBillLabel","PatientBillBillLabelDropDown");
	    		 verifications.verify(SeleniumVerifications.Selected, "PatientBillBillLabel","PatientBillBillLabelDropDown",true);
	    		 EnvironmentSetup.selectByPartMatchInDropDown = false; 	
	    		   
				executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");	
				//verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		
				//verifications.verify(SeleniumVerifications.Opens, "","PatientBillReceipt",true);				
				//executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillReceipt");
				//verifications.verify(SeleniumVerifications.Closes, "","PatientBillReceipt",false);
					
				verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
				executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
				verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
	       
	       }
	       
		public void sponsorAmountSettlement(String PaymentStatus){//added by Reena
	    	   System.out.println(" inside sponsorAmountSettlement");
	    	   MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
	    	   if(PaymentStatus.equalsIgnoreCase("OPEN_UNPAID_INS"))
	    	   {
	    		   advancedSearch(mrnoSearch, "OPEN_UNPAID_INS");
	    	   }
	    	   else{
	    		   
	    	  	   advancedSearch(mrnoSearch, "FINALIZED_INS");
	    	   }
	    	   int noOfOpenOrFinalizedBills = getNoOfBills();
	    	   //EnvironmentSetup.intGlobalLineItemCount=2;
	    	   for (int i = 1; i<= noOfOpenOrFinalizedBills; i++){    // changed now to check if it will go through all bills
	    		   
	    		   System.out.println(" why not selected select?");
	    		   EnvironmentSetup.intGlobalLineItemCount=i+1;
	    		   advanceBillNavigation();
	    		   sponsorSettlementDetails();
	    		   if (i == (noOfOpenOrFinalizedBills-1)){ 
		    		   break;
					}
	    		   
	    		   SiteNavigation navigation = new SiteNavigation(this.executeStep.getAutomationID(), this.executeStep.getDataSet());
	    		  navigation.navigateTo(this.executeStep.getDriver(), "OpenBills", "BillListPage");
	    		   if(PaymentStatus.equalsIgnoreCase("OPEN_UNPAID_INS"))
		    	   {
		    		   advancedSearch(mrnoSearch, "OPEN_UNPAID_INS");
		    	   }
	    		   else
	    		   {
					advancedSearch(mrnoSearch, "FINALIZED_INS");
	    		  // noOfOpenOrFinalizedBills = getNoOfBills();
	    		   }
	    		  // EnvironmentSetup.intGlobalLineItemCount++;
					}
	    	   EnvironmentSetup.intGlobalLineItemCount = 0;
	    	   } 
		public void closeBill(String searchCriteria){
	    	   System.out.println(" inside sponsorAmountSettlement");
	    	   MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
	    	   //After discharge the patient will not be in the active list. Hence, need to uncheck Active checkbox
	   			executeStep.performAction(SeleniumActions.Click, "","PatientBillActiveCheckBox");
	   			verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
	    	   advancedSearch(mrnoSearch, searchCriteria);
	    	   
	    	   int noOfOpenOrFinalizedBills = getNoOfBills();
	    	   EnvironmentSetup.intGlobalLineItemCount=2;
	    	   for (int i = 1; i<= noOfOpenOrFinalizedBills; i++){    // changed now to check if it will go through all bills
	    		   
	    		   System.out.println(" why not selected select?");
	    		   //EnvironmentSetup.intGlobalLineItemCount=i+1;
	    		   advanceBillNavigation();
	    		  
	    		   if (isBillLater()){                                                                           // added by abhishek
						executeStep.performAction(SeleniumActions.Click,"","BillPagePaymentSpan");
					}
	    		   
					String we = null;
					Double due = 0.0;
					try{
						we = this.executeStep.getDriver().findElement(By.xpath("//label[@id='lblSponsorDue']")).getText(); 
						due = new Double(we);
					}catch (Exception e){
						System.out.println("Webelement for given xpath not found" + e.toString());
					}
					if (due > 0){	
						executeStep.performAction(SeleniumActions.Select, "PrimarySponsorPaymentType","PatientBillPaymentType");
						verifications.verify(SeleniumVerifications.Selected, "PrimarySponsorPaymentType","PatientBillPaymentType",true);
						
						executeStep.performAction(SeleniumActions.Store, "SponsorDue","PatientBillSponsorDue");
						executeStep.performAction(SeleniumActions.Enter, "SponsorDue","PatientBillPayField");
						verifications.verify(SeleniumVerifications.Entered, "SponsorDue","PatientBillPayField",false);
						executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");	
	    				verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
	    				//close the receipt	
	    				verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
	    				executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
	    				verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);
	    				
	    				we = null;
	    				if (isBillLater()){                                                                           // added by abhishek
							executeStep.performAction(SeleniumActions.Click,"","BillPagePaymentSpan");
	    				}
					}
					
					try{
						we = this.executeStep.getDriver().findElement(By.xpath("//label[@id='lblPatientDue']")).getText(); 
						due = new Double(we);
					}catch (Exception e){
						System.out.println("Webelement for given xpath not found" + e.toString());
					}
				
					if (due > 0){	
						executeStep.performAction(SeleniumActions.Select, "SettlementPaymentType","PatientBillPaymentType");
						verifications.verify(SeleniumVerifications.Selected, "SettlementPaymentType","PatientBillPaymentType",true);
						executeStep.performAction(SeleniumActions.Store, "PaymentDue","PatientPaymentDue");
						executeStep.performAction(SeleniumActions.Enter, "PaymentDue","PatientBillPayField");
						verifications.verify(SeleniumVerifications.Entered, "PaymentDue","PatientBillPayField",false);
						executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");	
	    				verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
	    				
	    				verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
	    				executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
	    				verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);
	    				executeStep.performAction(SeleniumActions.Click,"","BillPagePaymentSpan");
	    				we = null;
					}
					
					
	    		   executeStep.performAction(SeleniumActions.Select, "ClosedBillStatus","PatientBillStatus");
    			   verifications.verify(SeleniumVerifications.Selected, "ClosedBillStatus","PatientBillStatus",false);
    				executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");	
    				verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
	    		   
    				if (i == (noOfOpenOrFinalizedBills-1)){ 
		    		   break;
					}
	    		   
	    		   SiteNavigation navigation = new SiteNavigation(this.executeStep.getAutomationID(), this.executeStep.getDataSet());
	    		  navigation.navigateTo(this.executeStep.getDriver(), "OpenBills", "BillListPage");
	    		
	    		  //After discharge the patient will not be in the active list. Hence, need to uncheck Active checkbox
	    			executeStep.performAction(SeleniumActions.Click, "","PatientBillActiveCheckBox");
	    			verifications.verify(SeleniumVerifications.Appears, "","BillListPage",false);
					advancedSearch(mrnoSearch, searchCriteria);
	    		  
					noOfOpenOrFinalizedBills = getNoOfBills();
	    		  // EnvironmentSetup.intGlobalLineItemCount++;
					}
	    	   EnvironmentSetup.intGlobalLineItemCount = 0;
	    	   }
		public boolean isBillLater(){
			WebElement we = null;
			try{
				we = this.executeStep.getDriver().findElement(By.xpath("//td[contains(text(),'Bill No (Type):')]/following-sibling::td[contains(text(),'Bill Later')]"));                //xpath changed by abhishek
			}catch (Exception e){
				System.out.println("Webelement for given xpath not found" + e.toString());
			}
			if (we!=null){	
				//isBillLater = true;
				return true;
			}
			return false;
		}
		public void setBillStatus(String insuranceFlag){
			   if (insuranceFlag.equalsIgnoreCase("YES")){
    			   executeStep.performAction(SeleniumActions.Select, "FinalizedBillStatus","PatientBillStatus");
    			   verifications.verify(SeleniumVerifications.Selected, "FinalizedBillStatus","PatientBillStatus",false);
    			   
    			   executeStep.performAction(SeleniumActions.Select,"PaidPaymentStatus","BillPagePaymentStatus");
   	  		       verifications.verify(SeleniumVerifications.Selected, "PaidPaymentStatus","BillPagePaymentStatus",false); //added by abhishek  
			   }
			   else if (isBillLater()){
    			   executeStep.performAction(SeleniumActions.Select,"ClosedBillStatus","PatientBillStatus");
	    		   verifications.verify(SeleniumVerifications.Selected, "ClosedBillStatus","PatientBillStatus",false);  
			   }
		}
		
		
		
		 public void viewEditMenuClick(){
			executeStep.performAction(SeleniumActions.Click, "","BillListItemToClick");
		    verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
	        executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
	        verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
	        executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		 }
	   
		 public void viewEditBills(String BillType){
		    	   System.out.println("Inside BillList Search page");
		    	   MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
		    	   if (BillType.equalsIgnoreCase("BN-P")){
		    		   advancedSearch(mrnoSearch, "PHARMACY");
		    		   //  executeStep.performAction(SeleniumActions.Click, "","BillListPharmacyBill");
		    		   viewEditMenuClick();
		    	   }
		    	   else {
		    		   advancedSearch(mrnoSearch, "HOSPITAL");
		    		   //   executeStep.performAction(SeleniumActions.Click, "","BillListHospitalBill");
		    		   viewEditMenuClick();
		    	   }
			       
		 }
					
		
		 public void settleSecondaryBillSBillDetails(String insurance, String paymentType){
	    	   System.out.println(" inside settleBillDetails");
	    	   		
	    	   if (isBillLater()){                                                                           // added by abhishek
					executeStep.performAction(SeleniumActions.Click,"","BillPagePaymentSpan");
				}
	    	    executeStep.performAction(SeleniumActions.Select, paymentType,"PatientBillPaymentType");
				verifications.verify(SeleniumVerifications.Selected, paymentType,"PatientBillPaymentType",true);
				
				executeStep.performAction(SeleniumActions.Store, "NetAmount","PatientBillNetAmount");
				executeStep.performAction(SeleniumActions.Enter, "NetAmount","PatientBillPayField");
				verifications.verify(SeleniumVerifications.Entered, "NetAmount","PatientBillPayField",false);
				
				executeStep.performAction(SeleniumActions.Select,"PaymentMode" ,"PatientBillPaymentMode");
				verifications.verify(SeleniumVerifications.Selected,"PaymentMode" ,"PatientBillPaymentMode",false);
				
				
				if (isBillLater()){
					executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");	
				}
				else {
					if (insurance.equalsIgnoreCase("YES")){
						executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");	
					}
					else {
						executeStep.performAction(SeleniumActions.Click, "","PatientBillPayCloseBtn");//Changed from PayNSave to PayClose
					}
				}
				//verifications.verify(SeleniumVerifications.Opens, "","PatientBillReceipt",true);				
				//executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillReceipt");
				//verifications.verify(SeleniumVerifications.Closes, "","PatientBillReceipt",false);
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);	
				verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
				executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
				verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);
	       
	       }
        
		  public void addDiscountPlanAndSave(){
			  
			  executeStep.performAction(SeleniumActions.Select, "DiscountPlan","DiscountPlanDropDown");
			  verifications.verify(SeleniumVerifications.Selected, "DiscountPlan","DiscountPlanDropDown",false);
			  executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");
			  verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);	
		  }
			public void settleBillsWithoutSearch(String insuranceFlag, String paymentStatus,String patientType){
		    	   System.out.println(" inside settleOpenPaidInsBills");
		    	   
		    	   MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
		    	   /*
		    	   if (insuranceFlag.equalsIgnoreCase("YES") && paymentStatus.equalsIgnoreCase("PAID")){
		    		   advancedSearch(mrnoSearch, "OPEN_PAID_INS");
		    	   }
		    	   else if(insuranceFlag.equalsIgnoreCase("NO") && paymentStatus.equalsIgnoreCase("PAID")){
		    		   advancedSearch(mrnoSearch, "OPEN_PAID_NONINS");
		    	   }
					else if(insuranceFlag.equalsIgnoreCase("YES") && paymentStatus.equalsIgnoreCase("UNPAID")){
						advancedSearch(mrnoSearch, "OPEN_UNPAID_INS");
					}
					else if(insuranceFlag.equalsIgnoreCase("NO") && paymentStatus.equalsIgnoreCase("UNPAID")){
						advancedSearch(mrnoSearch, "OPEN_UNPAID_NONINS");
					}
				*/	
		    	   int noOfOpenOrFinalizedBills = getNoOfBills();
		    	   
		    	 
		    	   for (int i = 0; i<noOfOpenOrFinalizedBills; i++){
		    		   EnvironmentSetup.intGlobalLineItemCount= 2;
		    		   
		    		//   advanceBillNavigation();
		    		   
		    		   setBillStatus(insuranceFlag);
		    		  
		    		   if (patientType.equalsIgnoreCase("IP"))
		    		   {
		    			   WebElement we = null;
		   					try{
		   						we = this.executeStep.getDriver().findElement(By.xpath("//input[@name='_okToDischarge']"));
		   					}catch (Exception e){
		   					System.out.println("Webelement for given xpath not found" + e.toString());
		   					}
		   					if (we!=null){	
		   						executeStep.performAction(SeleniumActions.Check, "","PatientBillOkToDischargeCheckBox");
			    				verifications.verify(SeleniumVerifications.Checked, "","PatientBillOkToDischargeCheckBox",false);
		   					}		
		    		   }
		    		   settleBillDetails(insuranceFlag,"SettlementPaymentType");
		    		  
		    		   SiteNavigation navigation = new SiteNavigation(this.executeStep.getAutomationID(), this.executeStep.getDataSet()); 
		    		   navigation.navigateTo(this.executeStep.getDriver(), "OpenBills", "BillListPage");
		    		   if (insuranceFlag.equalsIgnoreCase("YES") && paymentStatus.equalsIgnoreCase("PAID")){
			    		   advancedSearch(mrnoSearch, "OPEN_PAID_INS");
			    	   }
			    	   else if(insuranceFlag.equalsIgnoreCase("NO") && paymentStatus.equalsIgnoreCase("PAID")){
			    		   advancedSearch(mrnoSearch, "OPEN_PAID_NONINS");
			    	   }
						else if(insuranceFlag.equalsIgnoreCase("YES") && paymentStatus.equalsIgnoreCase("UNPAID")){
							advancedSearch(mrnoSearch, "OPEN_UNPAID_INS");
						}
						else if(insuranceFlag.equalsIgnoreCase("NO") && paymentStatus.equalsIgnoreCase("UNPAID")){
							advancedSearch(mrnoSearch, "OPEN_UNPAID_NONINS");
						}					
		    		   noOfOpenOrFinalizedBills = getNoOfBills();
		    		   }
		    	   EnvironmentSetup.intGlobalLineItemCount=0;
		       }

		}
		    	   
		
		 

