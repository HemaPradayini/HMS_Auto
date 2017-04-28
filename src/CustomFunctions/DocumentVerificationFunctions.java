package CustomFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.openqa.selenium.By;

import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class DocumentVerificationFunctions {

	// Variable Declaration
	public static String pricingType = "";
	public static String dataSet = "";
	public static String rampChargeType = "";
	public static Statement queryStatement = null;
	public static Connection testDataconnection = null;
	public static ResultSet testDataResultSet = null;
	public static String strModuleName = "Apttus";
	public static String strTermDuration = "";
	public static String strAgreementNumber = "";
	public static String strEffectiveDate = "";
	public static String strBillingEffectiveDate = "";
	public static String strOrderExpirationDate = "";
	public static String strProjectedMinimumValue = "";
	public static String strAccountName = "";
	public static String strPurpose = "";
	public static String strGrandTotal = "";
	public static String strTerm = "";
	public static String strTermCategory = "";
	public static String strCustomerType = "";
	public static String strTotalMonthlyFee = "";
	public static String strSubscriptionID = "";
	public static String strTerminationDate = "";
	public static String UOM = "";
	public static String strOrderType = "";
	
	public static String strBillingModel = "";
	public static String strProductName = "";

	public static String strFinalResult = "";
	public static boolean blnVerificationResult = true;
	public static String strKeyWrdDocVerify = "VERIFYDOCUMENT";

	public static String strconvertedEffDate = "";
	public static String strconvertedBillEffDate = "";
	public static String strconvertedOrdExpDate = "";

	// Method that is called from the KeywordSelectionLibrary
	public static String verifyDocumentPreview(String strCurrentPricingType,
			String strCurrentDataSet) {

		pricingType = strCurrentPricingType;
		dataSet = strCurrentDataSet;
		//strOrderType = strCurrentPricingType;
		// Switch to Verification Frame
		EnvironmentSetup.logger.info("Preview Validation : "
				+ strKeyWrdDocVerify);

		try {
			try{
				KeywordExecutionLibrary.driver.wait(5);
			}
			catch(Exception ex){
				
			}
			KeywordExecutionLibrary.driver.switchTo().frame(
					KeywordExecutionLibrary.driver.findElement(By
							.name("theIframe")));

			fetchMainParams(strCurrentPricingType);

			EnvironmentSetup.logger.info("Verifying Header Information");

			verifyHeader();

			verifyPricingInfo();

			KeywordExecutionLibrary.driver.switchTo().defaultContent();

		} catch (Exception ex) {
			// System.out.println("Preview Frame Not Found: " + ex.toString());
			EnvironmentSetup.logger.info("Preview Frame Not Found: "
					+ ex.toString());
			strFinalResult = "Preview Frame has not appeared";
			blnVerificationResult = false;
			VerificationFunctions.captureScreenshot();
		}

		if (blnVerificationResult == false) {
			strFinalResult = strFinalResult + ":::Fail";
		} else {
			strFinalResult = "Document Verified:::Pass";
		}

		return strFinalResult;
	}

	private static void verifyBoilerPlateText() {

		EnvironmentSetup.logger.info("Verifying Boiler Plate Text");
		// Initialize base objects
		String BoilerPlateTextProperty = "(((//div[.='Customer Type:']))//following-sibling::div[1])";
		try{
			// Fetch Actual Values
			String actualBoilerPlateText = KeywordExecutionLibrary.driver
					.findElement(By.xpath(BoilerPlateTextProperty)).getText()
					.trim();

			// Fetch Expected Values
			String expectedBoilerPlateText = testDataResultSet.getString("BoilerPlateText");

			if (actualBoilerPlateText.contains(expectedBoilerPlateText)) {
				EnvironmentSetup.logger.info("Boiler Plate Text Verified --> PASS");
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify BoilerPlateText"
						, expectedBoilerPlateText + " should be displayed in the Document", expectedBoilerPlateText + " is displayed", "Pass");
			} else {
				blnVerificationResult = false;
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify BoilerPlateText"
						, expectedBoilerPlateText + " should be displayed in the Document", "Actual Text: "	+ actualBoilerPlateText, "Fail");
			}
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}

	}

	public static void connectToDB() {
		try {
			closeDBConnection();
		} catch (Exception ex) {
			ex.toString();
		}

		try {

			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			testDataconnection = DriverManager
					.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
							+ "DBQ="
							+ EnvironmentSetup.strRootFolder
							+ "Test Data//Apttus_TestData.xlsx");

		} catch (Exception ex) {
			EnvironmentSetup.logger.info(ex.toString());
			EnvironmentSetup.logger.info(ex.toString());

		}
	}

	public static void closeDBConnection() {
		try {
			testDataResultSet.close();
			queryStatement.close();
		} 
		catch (Exception ex) {

		}

		try {
			testDataconnection.close();
		}
		catch (Exception ex) {
			
		}
	}

	public static void fetchMainParams(String OrderType) {
		try {
			// Connect to DataBase
			connectToDB();

			String query = "";

			// Execute Query

			/*if (EnvironmentSetup.strLineItemDataSet.contains("Modify")) {
				query = "Select * from [" + strModuleName
						+ "_LineItems$] where DataSet='" + dataSet
						+ "' and LineItemID like '%ModifyFlow%'";
				System.out.println("Query is: " + query);
			} 
			else if (EnvironmentSetup.strLineItemDataSet.contains("New")) {
				query = "Select * from [" + strModuleName
						+ "_LineItems$] where DataSet='" + dataSet
						+ "' and LineItemID like '%NewFlow%'";
				System.out.println("Query is: " + query);
			}*/
			
			query = "Select * from [Apttus_TestData$] Where DataSet = '" + dataSet + "'";
			
			
			
			queryStatement = testDataconnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			// System.out.println(query);
			EnvironmentSetup.logger.info(query);

			testDataResultSet = queryStatement.executeQuery(query);

			testDataResultSet.beforeFirst();
			testDataResultSet.next();

			// Fetch Values
			strAgreementNumber = testDataResultSet.getString(OrderType + "AgreementNumber");
			strEffectiveDate = testDataResultSet.getString(OrderType + "EffectiveDate");
			strconvertedEffDate = convertDate(strEffectiveDate);
			System.out
					.println("The Data returned from converted date function is: "
							+ strconvertedEffDate);
			strBillingEffectiveDate = testDataResultSet
					.getString(OrderType + "BillingEffectiveDate");
			strconvertedBillEffDate = convertDate(strBillingEffectiveDate);
			strOrderExpirationDate = testDataResultSet
					.getString(OrderType + "OrderExpirationDate");
			strconvertedOrdExpDate = convertDate(strOrderExpirationDate);
			strAccountName = testDataResultSet.getString("AccountName");
			strPurpose = testDataResultSet.getString(OrderType + "ProposalRecordType");
			strTerm = testDataResultSet.getString("Term");
			strTermCategory = testDataResultSet.getString("TermCategory");
			strTermDuration = strTerm + " " + strTermCategory;
			// System.out.println("Term Duration " +strTermDuration);
			EnvironmentSetup.logger
					.info("Term Duration is: " + strTermDuration);
			strCustomerType = testDataResultSet.getString("CustomerType");
			// strGrandTotal = testDataResultSet.getString("GrandTotal");
			//strTotalMonthlyFee = testDataResultSet.getString("TotalMonthlyFee");
			/*strProjectedMinimumValue = testDataResultSet
					.getString("ProjectedMinimumOrderValue");
*/
			// Close Connection
			closeDBConnection();

		} 
		catch (Exception ex) {
			EnvironmentSetup.logger.info(ex.toString());
		}
	}

	public static void verifyHeader() {

		try {
			// Initialize base objects
			EnvironmentSetup.logger
					.info("Verifying Header Information in verifyHeader ::");
			String AgreementNumberProperty = "(((//div[.='Akamai Service Order Form']))//following-sibling::div[1])";
			String EffectiveDateProperty = "(((//div[.='Effective Date:']))//following-sibling::div[1])";
			String BillingEffectiveDateProperty = "(((//div[.='Billing Effective Date:']))//following-sibling::div[1])";
			String OrderExpirationDateProperty = "(((//div[.='Order Expiration Date:']))//following-sibling::div[1])";
			String PurposeProperty = "(((//div[.='Purpose:']))//following-sibling::div[1])";
			String AccountNameProperty = "(((//div[.='SOLD TO:']))//following-sibling::div[3])";
			String TermDurationProperty = "(((//div[.='Term:']))//following-sibling::div[1])";
			String TotalMonthlyFeeProperty = "(((//div[.='Total Monthly Fee']))//following-sibling::div[1])";
			String ProjectedMinimumValueProperty = "(((//div[contains(text(),'Projected Minimum Order Value')]))//following-sibling::div[1])";
			String CustomerTypeProperty = "(((//div[.='Customer Type:']))//following-sibling::div[1])";

			// Fetch Actual Values
			EnvironmentSetup.logger.info("Fetching Actual Values ::");
			String actualAgreementNumber = KeywordExecutionLibrary.driver
					.findElement(By.xpath(AgreementNumberProperty)).getText()
					.trim();
			String actualEffectiveDate = KeywordExecutionLibrary.driver
					.findElement(By.xpath(EffectiveDateProperty)).getText()
					.trim();
			String actualBillingEffectiveDate = KeywordExecutionLibrary.driver
					.findElement(By.xpath(BillingEffectiveDateProperty))
					.getText().trim();
			String actualOrderExpirationDate = KeywordExecutionLibrary.driver
					.findElement(By.xpath(OrderExpirationDateProperty))
					.getText().trim();
			String actualPurpose = KeywordExecutionLibrary.driver
					.findElement(By.xpath(PurposeProperty)).getText().trim();
			String actualAccountName = KeywordExecutionLibrary.driver
					.findElement(By.xpath(AccountNameProperty)).getText()
					.trim();
			String actualTermDuration = KeywordExecutionLibrary.driver
					.findElement(By.xpath(TermDurationProperty)).getText()
					.trim();
			String actualTotalMonthlyFee = KeywordExecutionLibrary.driver
					.findElement(By.xpath(TotalMonthlyFeeProperty)).getText()
					.trim();
			String actualProjectedMinimumValue = KeywordExecutionLibrary.driver
					.findElement(By.xpath(ProjectedMinimumValueProperty))
					.getText().trim();
			String actualCustomerType = KeywordExecutionLibrary.driver
					.findElement(By.xpath(CustomerTypeProperty)).getText()
					.trim();

			// Fetch Expected Values
			String expectedAgreementNumber = strAgreementNumber;
			//String expectedEffectiveDate = strconvertedEffDate;
			String expectedBillingEffectiveDate = strconvertedBillEffDate;
			String expectedOrderExpirationDate = strconvertedOrdExpDate;
			String expectedPurpose = strPurpose;
			String expectedAccountName = strAccountName;
			String expectedTerm = strTerm;
			String expectedTermCategory = strTermCategory;
			String expectedTermDuration = expectedTerm + " "
					+ expectedTermCategory;
			String expectedTotalMonthlyFee = strTotalMonthlyFee;
			String expectedProjectedMinimumValue = strProjectedMinimumValue;
			String expectedCustomerType = strCustomerType;

			if (AccountNameProperty.contains("Karthik Consulting")) {

				verifyBoilerPlateText();
			}

			EnvironmentSetup.logger.info("Starting Verification of Header ::");

			if (actualAgreementNumber.contains(expectedAgreementNumber)) {
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Agreement Number"
						, expectedAgreementNumber + " should be displayed in the Document", actualAgreementNumber + " is displayed", "Pass");
				
				EnvironmentSetup.logger.info("Agreement Number Verified --> PASS");
				
			} else {
				blnVerificationResult = false;
				
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Agreement Number"
						, expectedAgreementNumber + " should be displayed in the Document", actualAgreementNumber + " is displayed", "Fail");
			}
			if (actualEffectiveDate.contains(strconvertedEffDate)) {
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Effective Date"
						, strconvertedEffDate + " should be displayed in the Document", actualEffectiveDate + " is displayed", "Pass");
				EnvironmentSetup.logger.info("Effective Date Verified --> PASS");
			} else {
				blnVerificationResult = false;
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Effective Date"
						, strconvertedEffDate + " should be displayed in the Document", actualEffectiveDate + " is displayed", "Fail");
			}
			
			if (actualBillingEffectiveDate.contains(expectedBillingEffectiveDate)) {
				
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Billing Effective Date"
						, expectedBillingEffectiveDate + " should be displayed in the Document", actualBillingEffectiveDate + " is displayed", "Pass");
				
				EnvironmentSetup.logger
						.info("Billing Effective Date Verified --> PASS");
			} else {
				blnVerificationResult = false;
				
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Billing Effective Date"
						, expectedBillingEffectiveDate + " should be displayed in the Document", actualBillingEffectiveDate + " is displayed", "Fail");
			}
			
			if (actualOrderExpirationDate.contains(expectedOrderExpirationDate)) {
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Order Expiration Date"
						, expectedOrderExpirationDate + " should be displayed in the Document", actualOrderExpirationDate + " is displayed", "Pass");
				
				EnvironmentSetup.logger.info("Order Expiration Date Verified --> PASS");
			} else {
				blnVerificationResult = false;
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Order Expiration Date"
						, expectedOrderExpirationDate + " should be displayed in the Document", actualOrderExpirationDate + " is displayed", "Fail");
			}
			
			
			/*if (actualPurpose.contains(expectedPurpose)) {
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Purpose"
						, expectedPurpose + " should be displayed in the Document", actualPurpose + " is displayed", "Pass");

				EnvironmentSetup.logger.info("Purpose Verified --> PASS");
			} else {
				blnVerificationResult = false;
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Purpose"
						, expectedPurpose + " should be displayed in the Document", actualPurpose + " is displayed", "Fail");
			}
			*/
			if (actualAccountName.contains(expectedAccountName)) {
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Account Name"
						, expectedAccountName + " should be displayed in the Document", actualAccountName + " is displayed", "Pass");
				EnvironmentSetup.logger.info("Account Name Verified --> PASS");
			} else {
				blnVerificationResult = false;
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Account Name"
						, expectedAccountName + " should be displayed in the Document", actualAccountName + " is displayed", "Fail");
			}
			
			if (actualTermDuration.contains(expectedTermDuration)) {
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Term Duration"
						, expectedTermDuration + " should be displayed in the Document", actualTermDuration + " is displayed", "Pass");
				
				EnvironmentSetup.logger.info("Term Duration Verified --> PASS");
			} else {
				blnVerificationResult = false;
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Term Duration"
						, expectedTermDuration + " should be displayed in the Document", actualTermDuration + " is displayed", "Fail");
			}
			
			/*if (actualTotalMonthlyFee.contains(expectedTotalMonthlyFee)) {
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Total Monthly Fee"
						, expectedTotalMonthlyFee + " should be displayed in the Document", actualTotalMonthlyFee + " is displayed", "Pass");
				
				EnvironmentSetup.logger
						.info("Total Monthly Fee Verified --> PASS");
			} else {
				blnVerificationResult = false;
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Total Monthly Fee"
						, expectedTotalMonthlyFee + " should be displayed in the Document", actualTotalMonthlyFee + " is displayed", "Fail");
			}
			
			EnvironmentSetup.logger.info("Proj Min Value Actual & Expected -->"
					+ actualProjectedMinimumValue + " "
					+ expectedProjectedMinimumValue);
			
			if (actualProjectedMinimumValue.contains(expectedProjectedMinimumValue)) {
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Projected Minimum Value"
						, expectedProjectedMinimumValue + " should be displayed in the Document", actualProjectedMinimumValue + " is displayed", "Pass");
				
				EnvironmentSetup.logger.info("Projected Minimum Order Value Verified --> PASS");
			} else {
				blnVerificationResult = false;
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Projected Minimum Value"
						, expectedProjectedMinimumValue + " should be displayed in the Document", actualProjectedMinimumValue + " is displayed", "Fail");
			}*/
			
			if (actualCustomerType.contains(expectedCustomerType)) {
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Customer Type"
						, expectedCustomerType + " should be displayed in the Document", actualCustomerType + " is displayed", "Pass");
				
				EnvironmentSetup.logger.info("Customer Type Verified --> PASS");
			} else {
				blnVerificationResult = false;
				ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Customer Type"
						, expectedCustomerType + " should be displayed in the Document", actualCustomerType + " is displayed", "Fail");
			}

		} catch (Exception ex) {
			EnvironmentSetup.logger.info(ex.toString());
		}

	}

	public static void verifyPricingInfo() {
		try {
			// Connect to DataBase
			connectToDB();

			// Execute Query
			queryStatement = testDataconnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			/*String query = "Select * from [" + strModuleName
					+ "_LineItems$] where DataSet='" + dataSet
					+ "' and LineItemID Like '%Product%'";
			*/
			String query= "";
			
			query = "Select * from [Apttus_LineItems$] Where DataSet='" + dataSet + "' and LineItemID Like '%ProductFor" + pricingType  + "%'";
			testDataResultSet = queryStatement.executeQuery(query);
			//FetchProductNames
			String productNames = "";
			int counter = 0;
			try{
				testDataResultSet.beforeFirst();
				while( testDataResultSet.next()){
					counter ++;
					if(counter==1){
						productNames = testDataResultSet.getString("ProductName");
					}
					else{
						productNames = productNames + "," + testDataResultSet.getString("ProductName");
					}
				}
				
			}
			catch(Exception ex){
				
			}
			
			// Close Connection
			closeDBConnection();
						
			String[] productNameArray = null;
			
			if(productNames.contains(",")){
				productNameArray =productNames.split(",");
			}
			
			//Open Connection
			connectToDB();
			
			if(productNameArray != null){
				for(String product : productNameArray){
					strProductName = product;
					query = "Select * from [Apttus_LineItems$] Where DataSet='" + dataSet + "' and LineItemID Like '%AttributeFor" + pricingType  
							+ "%' and AttributeReference='BillingModel'";
					testDataResultSet = queryStatement.executeQuery(query);
					
					try{
						testDataResultSet.beforeFirst();
						if(testDataResultSet.next()){
							strBillingModel = testDataResultSet.getString("Attribute");
						}
						else{
							strBillingModel = "";
						}
					}
					catch(Exception ex){
						strBillingModel = "";
					}
					
					//Fetch UOM
					query = "Select * from [Apttus_LineItems$] Where DataSet='" + dataSet + "' and LineItemID Like '%AttributeFor" + pricingType  
							+ "%' and AttributeReference='UOM'";
					
					testDataResultSet = queryStatement.executeQuery(query);
					
					try{
						testDataResultSet.beforeFirst();
						if(testDataResultSet.next()){
							UOM = testDataResultSet.getString("Attribute");
						}
						else{
							UOM = "";
						}
					}
					catch(Exception ex){
						UOM = "";
					}
					
					
					if(!UOM.isEmpty()){
						if(UOM.equalsIgnoreCase("Full Service + Trial")){
							//Verify Trial Lines
							//Verify Full Service Lines
							
						}
						else if(UOM.equalsIgnoreCase("Full Service")){
							//Verify Full Service Lines
						}
						else if(UOM.equalsIgnoreCase("Hours without overage")){
							
						}
					}
					
					//Verify Main Info for Product
					
					/*query = "Select * from [Apttus_LineItems$] Where DataSet='" + dataSet + "' and LineItemID Like '%For" + pricingType  
							+ "%' and AttributeReference='BillingModel'";
					testDataResultSet = queryStatement.executeQuery(query);
					
					try{
						testDataResultSet.beforeFirst();
						if(testDataResultSet.next()){
							UOM = testDataResultSet.getString("Attribute");
						}
						else{
							UOM = "";
						}
					}
					catch(Exception ex){
						UOM = "";
					}*/
					
					//Fetch ConfigureProducts
					query = "Select Dictinct OptionName from [Apttus_LineItems$] Where DataSet='" + dataSet + "' and LineItemID Like '%CommitRampsFor" + pricingType  
							+ "%' and ProductName='" + product + "'";
					testDataResultSet = queryStatement.executeQuery(query);
					ResultSet internalRS = null;
					Statement internalStatement = null;
					internalStatement = testDataconnection.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					
					try{					
						testDataResultSet.beforeFirst();
						try{
							while(testDataResultSet.next()){
								String OptionName = testDataResultSet.getString("OptionName");
								
								if(!OptionName.equalsIgnoreCase(product)){
									
									//Fetch Option Billing Model
									query = "Select * from [Apttus_LineItems$] Where DataSet='" + dataSet + "' and LineItemID Like '%For" + pricingType  
											+ "%' and AttributeReference='BillingModel'";
									
									internalRS = internalStatement.executeQuery(query);
									internalRS.beforeFirst();
									internalRS.next();
									String internalBillingModel =internalRS.getString("Attribute"); 
									
									query = "Select * from [Apttus_LineItems$] Where DataSet='" + dataSet + "' and LineItemID Like '%CommitRampsFor" + pricingType  
											+ "%' and OptionName='" + OptionName + "'";
									
									internalRS = internalStatement.executeQuery(query);
									if(internalBillingModel.equalsIgnoreCase("Period Commitment")){
										verifyPeriodCommitmentRamps(internalRS);
									}
									else if(internalBillingModel.contains("Monthly Commit") && internalBillingModel.contains("Fixed Overage")){
										verifyRampsTable(internalRS);
									}
									
								}
								else{
									if(strBillingModel.equalsIgnoreCase("Period Commitment")){
										query = "Select * from [Apttus_LineItems$] Where DataSet='" + dataSet + "' and LineItemID Like '%CommitRampsFor" + pricingType  
												+ "%' and OptionName='" + OptionName + "'";
										
										internalRS = internalStatement.executeQuery(query);
										verifyPeriodCommitmentRamps(internalRS);
										
									}
									else if(strBillingModel.contains("Monthly Commit") && strBillingModel.contains("Fixed Overage")){
										verifyRampsTable(internalRS);
									}
								}
								
							}
							internalStatement.close();
						}
						catch(Exception ex){
							
						}
						
					}
					catch(Exception ex){
						
					}
					
					
					/*try{
						testDataResultSet.beforeFirst();
						if(testDataResultSet.next()){
							strBillingModel = testDataResultSet.getString("Attribute");
						}
						else{
							UOM = "";
						}
					}
					catch(Exception ex){
						UOM = "";
					}*/
					
					
					/*if(!strBillingModel.isEmpty()){
						if(strBillingModel.trim().equalsIgnoreCase("Period Commitment")){
							
						}
					}*/

				}
			}
			
			
			// Close Connection
			closeDBConnection();

			// 

/*			if (pricingType.equalsIgnoreCase("Tier")) {
				query = "Select * from [" + strModuleName
						+ "_LineItems$] where DataSet='" + dataSet
						+ "' and LineItemID Like '%" + pricingType + "%'";
				EnvironmentSetup.logger.info(query);
			} else if (pricingType.equalsIgnoreCase("Ramp")) {
				query = "Select * from [" + strModuleName
						+ "_LineItems$] where DataSet='" + dataSet
						+ "' and LineItemID Like '%" + pricingType
						+ "%' and ChargeType ='" + rampChargeType + "'";
				EnvironmentSetup.logger.info(query);
			}

			queryStatement = testDataconnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			testDataResultSet = queryStatement.executeQuery(query);

			testDataResultSet.beforeFirst();
			testDataResultSet.next();

			if (pricingType.equalsIgnoreCase("Tier")) {
				// Verify Tiers Table
				verifyTiersTable();
				
			} else if (pricingType.equalsIgnoreCase("Ramp")) {
				if (strBillingModel.contains("Period")) {
					EnvironmentSetup.logger.info("verification started");
					// Call required Method
					verifyPeriodCommitmentRamps();
				} else if (strBillingModel.contains("Straight")) {

					// Call Required Method
					verifyStraightLineRamps();

				} else {
					EnvironmentSetup.logger.info("Verifying Ramps Table ");
					verifyRampsTable();
				}
			}

			closeDBConnection();*/
		} catch (Exception ex) {
			EnvironmentSetup.logger.info(ex.toString());
			EnvironmentSetup.logger.info(ex.toString());
		}
	}

	public static void verifyTiersTable() {
		try {
			int rowCounter = 0;
			int multiplier = 3;
			int objectIndex = 0;
			String objectIndexToReplace = "";

			int lastTierNumber = 0;

			testDataResultSet.beforeFirst();
			while (testDataResultSet.next()) {

				// increment counter
				rowCounter = rowCounter + 1;
				objectIndex = multiplier * rowCounter;
				objectIndexToReplace = Integer.toString(objectIndex);
				String PlatformFeeProperty = "";
				String expectedPlatformFee = "";
				if (strProductName.contains("Ion")) {
					PlatformFeeProperty = "(((//div[.='Platform Fee']))//following-sibling::div[1])";
					expectedPlatformFee = testDataResultSet.getString("PlatformFee");
				} else {
					PlatformFeeProperty = "(((//div[.='Base Fee']))//following-sibling::div[1])";
					expectedPlatformFee = testDataResultSet.getString("BaseFee");
				}

				String TierStartProperty = "(((//div[.='Tier Start Value']))//following-sibling::div[<count>])";
				String TierEndProperty = "(((//div[.='Tier End Value']))//following-sibling::div[<count>])";
				String TierUsageRateProperty = "(((//div[.='Usage Rate']))//following-sibling::div[<count>])";

				// Fetch Expected Values
				String expectedTierStart = testDataResultSet.getString("From");
				String expectedTierEnd = testDataResultSet.getString("To");
				String expectedTierUsageRate = testDataResultSet.getString("TiersUnitPrice");

				// Build Objects
				TierStartProperty = TierStartProperty.replace("<count>",
						objectIndexToReplace);
				TierEndProperty = TierEndProperty.replace("<count>",
						objectIndexToReplace);
				TierUsageRateProperty = TierUsageRateProperty.replace(
						"<count>", objectIndexToReplace);

				// Check product
				if (strProductName.contains("Ion")) {
					lastTierNumber = 8;
				} else {
					lastTierNumber = 9;
				}

				String actualPlatformFee = "";
				String actualTierStart = "";
				String actualTierEnd = "";
				String actualUsageRate = "";

				// Fetch Actual Values from Application
				if (rowCounter == lastTierNumber) {
					actualTierStart = KeywordExecutionLibrary.driver
							.findElement(By.xpath(TierStartProperty)).getText()
							.trim();
					actualTierEnd = "";
					expectedTierEnd = "";
					if (rowCounter == 8) {
						if (TierUsageRateProperty.contains("24")) {
							TierUsageRateProperty = TierUsageRateProperty
									.replace("24", "23");
						}

					} else {
						TierUsageRateProperty = TierUsageRateProperty.replace(
								"27", "26");
					}

					actualUsageRate = KeywordExecutionLibrary.driver
							.findElement(By.xpath(TierUsageRateProperty))
							.getText().trim();
				} else {
					actualPlatformFee = KeywordExecutionLibrary.driver
							.findElement(By.xpath(PlatformFeeProperty))
							.getText().trim();
					actualTierStart = KeywordExecutionLibrary.driver
							.findElement(By.xpath(TierStartProperty)).getText()
							.trim();
					actualTierEnd = KeywordExecutionLibrary.driver
							.findElement(By.xpath(TierEndProperty)).getText()
							.trim();
					actualUsageRate = KeywordExecutionLibrary.driver
							.findElement(By.xpath(TierUsageRateProperty))
							.getText().trim();
				}

				// Compare Values for current Row
				if (actualTierStart.trim().contains(expectedTierStart.trim())) {
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Tier Start"
							, expectedTierStart + " should be displayed in the Document", actualTierStart + " is displayed", "Pass");
					EnvironmentSetup.logger.info("Tier Start Value Verifed --> PASS");
				} else {
					blnVerificationResult = false;
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Tier Start"
							, expectedTierStart + " should be displayed in the Document", actualTierStart + " is displayed", "Fail");
				}
				
				if (actualTierEnd.trim().contains(expectedTierEnd.trim())) {
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Tier End"
							, expectedTierEnd + " should be displayed in the Document", actualTierEnd + " is displayed", "Pass");
					
					EnvironmentSetup.logger.info("Tier End Value Verified --> PASS");
				} else {
					blnVerificationResult = false;
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Tier End"
							, expectedTierEnd + " should be displayed in the Document", actualTierEnd + " is displayed", "Fail");
				}
				
				if (actualUsageRate.trim().contains(expectedTierUsageRate.trim())) {
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Tier Usage Rate"
							, expectedTierUsageRate + " should be displayed in the Document", actualUsageRate + " is displayed", "Pass");
					
					EnvironmentSetup.logger.info("Tiered Usage Rate Verified --> PASS");
				} else {
					blnVerificationResult = false;
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Tier Usage Rate"
							, expectedTierUsageRate + " should be displayed in the Document", actualUsageRate + " is displayed", "Fail");
				}

				if (actualPlatformFee.trim().contains(expectedPlatformFee.trim())) {
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Platform Fee"
							, expectedPlatformFee + " should be displayed in the Document", actualPlatformFee + " is displayed", "Pass");
					EnvironmentSetup.logger.info("Platform Fee Verified --> PASS");
				} else {
					blnVerificationResult = false;
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Platform Fee"
							, expectedPlatformFee + " should be displayed in the Document", actualPlatformFee + " is displayed", "Fail");
				}

			}

		} catch (Exception ex) {
			EnvironmentSetup.logger.info(ex.toString());
		}
	}

	public static void verifyStraightLineRamps() {
		try {
			int rowCounter = 0;
			int multiplier = 6;
			int objectIndex = 0;
			String objectIndexToReplace = "";

			testDataResultSet.beforeFirst();
			while (testDataResultSet.next()) {
				// increment counter
				rowCounter = rowCounter + 1;
				objectIndex = multiplier * rowCounter;
				objectIndexToReplace = Integer.toString(objectIndex);

				// Initialize Objects
				String timePeriodStartObjectProp = "(((//div[.='Period Start Date']))//following-sibling::div[<count>])";
				String timePeriodEndObjectProp = "(((//div[.='Period End Date']))//following-sibling::div[<count>])";

				// Replace <Count>
				timePeriodStartObjectProp = timePeriodStartObjectProp.replace(
						"<count>", objectIndexToReplace);
				timePeriodEndObjectProp = timePeriodEndObjectProp.replace(
						"<count>", objectIndexToReplace);
				

				// Fetch expected Values
				String expectedStartDate = testDataResultSet
						.getString("StartDate");
				String expectedEndDate = testDataResultSet.getString("EndDate");

				// Fetch Actual Values
				String actualStartDate = KeywordExecutionLibrary.driver
						.findElement(By.xpath(timePeriodStartObjectProp))
						.getText().trim();
				String actualEndDate = KeywordExecutionLibrary.driver
						.findElement(By.xpath(timePeriodEndObjectProp))
						.getText().trim();

				// Compare actual vs Expected
				if (actualStartDate.trim().contains(expectedStartDate.trim())) {
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Ramp Start Date"
							, expectedStartDate + " should be displayed in the Document", actualStartDate + " is displayed", "Pass");
					EnvironmentSetup.logger.info("Start Date Verifed --> PASS");
				} else {
					blnVerificationResult = false;
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Ramp Start Date"
							, expectedStartDate + " should be displayed in the Document", actualStartDate + " is displayed", "Fail");
				}

				if (actualEndDate.trim().contains(expectedEndDate.trim())) {
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Ramp Start Date"
							, expectedEndDate + " should be displayed in the Document", actualEndDate + " is displayed", "Pass");
					EnvironmentSetup.logger.info("End Date Verified --> PASS");
				} else {
					blnVerificationResult = false;
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Ramp Start Date"
							, expectedEndDate + " should be displayed in the Document", actualEndDate + " is displayed", "Fail");
				}

			}
		} 
		catch (Exception ex) {
			EnvironmentSetup.logger.info(ex.toString());
		}
	}

	public static void verifyRampsTable(ResultSet testDataResultSet) {
		try {
			int rowCounter = 0;
			int multiplier = 5;
			int objectIndex = 0;
			String objectIndexToReplace = "";

			testDataResultSet.beforeFirst();
			while (testDataResultSet.next()) {

				// increment counter
				rowCounter = rowCounter + 1;
				objectIndex = multiplier * rowCounter;
				objectIndexToReplace = Integer.toString(objectIndex);

				// Initialize Objects
				String timePeriodStartObjectProp = "(((//div[.='Period Start Date']))//following-sibling::div[<count>])";
				String timePeriodEndObjectProp = "(((//div[.='Period End Date']))//following-sibling::div[<count>])";
				String commitmentObjectProp = "(((//div[.='Period Commitment']))//following-sibling::div[<count>])";
				String usageRateObjectProp = "(((//div[.='Usage Rate']))//following-sibling::div[<count>])";
				String monthlyFeeObjectProp = "(((//div[.='Monthly Fee']))//following-sibling::div[<count>])";
				String quantityObjectProp = "(((//div[.='Quantity']))//following-sibling::div[<count>])";
				String usageRateforOverageObjectProp = "(((//div[.='Usage Rate for Overage']))//following-sibling::div[<count>])";

				// Replace <Count>
				timePeriodStartObjectProp = timePeriodStartObjectProp.replace(
						"<count>", objectIndexToReplace);
				timePeriodEndObjectProp = timePeriodEndObjectProp.replace(
						"<count>", objectIndexToReplace);
				commitmentObjectProp = commitmentObjectProp.replace("<count>",
						objectIndexToReplace);
				usageRateObjectProp = usageRateObjectProp.replace("<count>",
						objectIndexToReplace);
				monthlyFeeObjectProp = monthlyFeeObjectProp.replace("<count>",
						objectIndexToReplace);
				quantityObjectProp = quantityObjectProp.replace("<count>",
						objectIndexToReplace);
				usageRateforOverageObjectProp = usageRateforOverageObjectProp
						.replace("<count>", objectIndexToReplace);

				// Fetch expected Values

				String expectedStartDate = testDataResultSet.getString("RepricedStartDate");
				String expectedEndDate = testDataResultSet.getString("RepricedEndDate");
				String expectedQuantity = testDataResultSet.getString("RepricedQuantity");
				// String expectedUsageRateforOverage =

				// Fetch Actual Values
				String actualStartDate = KeywordExecutionLibrary.driver
						.findElement(By.xpath(timePeriodStartObjectProp))
						.getText().trim();
				String actualEndDate = KeywordExecutionLibrary.driver
						.findElement(By.xpath(timePeriodEndObjectProp))
						.getText().trim();
				String actualQuantity = KeywordExecutionLibrary.driver
						.findElement(By.xpath(quantityObjectProp)).getText()
						.trim();

				// Compare actual vs Expected

				if (actualStartDate.trim().contains(expectedStartDate.trim())) {
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Ramp Start Date"
							, expectedStartDate + " should be displayed in the Document", actualStartDate + " is displayed", "Pass");
					EnvironmentSetup.logger.info("Start Date Verifed --> PASS");
				} else {
					blnVerificationResult = false;
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Ramp Start Date"
							, expectedStartDate + " should be displayed in the Document", actualStartDate + " is displayed", "Fail");
				}

				if (actualEndDate.trim().contains(expectedEndDate.trim())) {
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Ramp End Date"
							, expectedEndDate + " should be displayed in the Document", actualEndDate + " is displayed", "Pass");
					EnvironmentSetup.logger.info("End Date Verified --> PASS");
				} else {
					blnVerificationResult = false;
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Ramp End Date"
							, expectedEndDate + " should be displayed in the Document", actualEndDate + " is displayed", "Fail");
				}

				if (actualQuantity.trim().contains(expectedQuantity.trim())) {
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Ramp Quantity"
							, expectedEndDate + " should be displayed in the Document", actualEndDate + " is displayed", "Pass");
					EnvironmentSetup.logger.info("Quantity Verified --> PASS");
				} else {
					blnVerificationResult = false;
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Ramp Quantity"
							, expectedEndDate + " should be displayed in the Document", actualEndDate + " is displayed", "Fail");
				}

			}
		} catch (Exception ex) {
			EnvironmentSetup.logger.info(ex.toString());
		}
	}

	public static void verifyPeriodCommitmentRamps(ResultSet testDataResultSet) {
		try {
			int rowCounter = 0;
			int multiplier = 5;
			int objectIndex = 0;
			String objectIndexToReplace = "";

			testDataResultSet.beforeFirst();
			while (testDataResultSet.next()) {

				// increment counter
				rowCounter = rowCounter + 1;
				objectIndex = multiplier * rowCounter;
				objectIndexToReplace = Integer.toString(objectIndex);

				// Initialize Objects
				String timePeriodObjectProp = "(((//div[.='Time Period']))//following-sibling::div[<count>])";

				// Replace Count
				timePeriodObjectProp = timePeriodObjectProp.replace("<count>",
						objectIndexToReplace);

				// Fetch expected values
				String expectedStartDate = testDataResultSet
						.getString("RepricedStartDate");
				String expectedEndDate = testDataResultSet.getString("RepricedEndDate");
				
				EnvironmentSetup.logger.info(expectedStartDate + " "
						+ expectedEndDate + "concatenating");
				String expectedTimePeriod = convertRampsDateFormat(
						expectedStartDate, expectedEndDate);

				// Fetch actual values
				String actualTimePeriod = KeywordExecutionLibrary.driver
						.findElement(By.xpath(timePeriodObjectProp)).getText()
						.trim();

				// Compare actual vs Expected
				if (actualTimePeriod.trim().contains(expectedTimePeriod.trim())) {
					// System.out.println("Verifed --> PASS");
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Ramp Time Period"
							, expectedTimePeriod + " should be displayed in the Document", actualTimePeriod + " is displayed", "Pass");
					EnvironmentSetup.logger.info("Time Period Verifed --> PASS");
					
				} else {
					blnVerificationResult = false;
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, strKeyWrdDocVerify, "Verify Ramp Time Period"
							, expectedTimePeriod + " should be displayed in the Document", actualTimePeriod + " is displayed", "Fail");
				}

			}
		} catch (Exception ex) {
			EnvironmentSetup.logger.info(ex.toString());
		}
	}

	public static String convertRampsDateFormat(String Date1, String Date2)
			throws ParseException {

		String strDuration = "";
		String strRampStartDate = "";
		String strRampEndDate = "";

		// conversion of date from 'mm/dd/yyyy' to 'mmm dd, yyyy' format
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");

		// parse the string into Date object
		Date startDate = format1.parse(Date1);
		Date endDate = format2.parse(Date2);

		// changing date format to desired type
		SimpleDateFormat strStartDate = new SimpleDateFormat("MMM dd, yyyy");
		SimpleDateFormat strEndDate = new SimpleDateFormat("MMM dd, yyyy");

		// parse the date to desired format
		strRampStartDate = strStartDate.format(startDate);
		strRampEndDate = strEndDate.format(endDate);

		// System.out.println("Date is converted from MM/dd/yyyy format to MMM dd, yyyy");
		EnvironmentSetup.logger
				.info("Date is converted from MM/dd/yyyy format to MMM dd, yyyy");
		// System.out.println("Ramp Start date is : " + strRampStartDate);
		EnvironmentSetup.logger
				.info("Ramp Start date is : " + strRampStartDate);
		// System.out.println("Ramp End date is : " + strRampEndDate);
		EnvironmentSetup.logger.info("Ramp End date is : " + strRampEndDate);

		// combining start date and end date
		strDuration = strRampStartDate + "-" + strRampEndDate;
		// System.out.println("Ramp Period is:"+strDuration);
		EnvironmentSetup.logger.info("Ramp Period is:" + strDuration);

		// verifying Ramp Duration
		return strDuration;

	}

	private static String convertDate(String Date) throws ParseException {

		SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
		Date thedate = parser.parse(Date);
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		String dateStrReformatted = formatter.format(thedate);

		System.out.println("Converted Date is : " + dateStrReformatted);

		return dateStrReformatted;
	}

}

/*

Fetch Oppty Currency

Fetch All Products

Trial Charges:

((//div[.='Ion Premier'])/following-sibling::div[.='Ion Premier']/following-sibling::div[.='Trial Charges (Oct 01,2015 - Oct 31,2015)'])[1]


dipage 1v
	content
div page 2
	content
	


*/