package reusableFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilderFactory;
 

public class ClaimsSubmission {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public ClaimsSubmission(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public ClaimsSubmission(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public String submitClaims(String centreAccountGroup, boolean primary, boolean resubmission){          //Changed by Abhishek String return type,parameter added
		
		System.out.println("Inside ClaimsSubmission submitClaims ");
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubNewClaimsSubmissionLink");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimsSubNewBatchSubmissionPage",false);	
		
		
		if (primary){
			executeStep.performAction(SeleniumActions.Select, "PrimaryInsuranceCo","ClaimsSubInsuranceCoName");
			verifications.verify(SeleniumVerifications.Selected, "PrimaryInsuranceCo","ClaimsSubInsuranceCoName",true);
			
			executeStep.performAction(SeleniumActions.Select, "PrimarySponsorName","ClaimsSubTPAName");
			verifications.verify(SeleniumVerifications.Selected, "PrimarySponsorName","ClaimsSubTPAName",true);
			
			executeStep.performAction(SeleniumActions.Select, "InsurancePlanType","ClaimsSubNetworkPlanType");
			verifications.verify(SeleniumVerifications.Selected, "InsurancePlanType","ClaimsSubNetworkPlanType",true);
			
			executeStep.performAction(SeleniumActions.Select, "InsurancePlanName","ClaimsSubPlanName");
			verifications.verify(SeleniumVerifications.Selected, "InsurancePlanName","ClaimsSubPlanName",true);
			
			EnvironmentSetup.selectByPartMatchInDropDown = true;
			executeStep.performAction(SeleniumActions.Select, centreAccountGroup,"ClaimsSubCenter");                   //Changed by Abhishek
			verifications.verify(SeleniumVerifications.Selected, centreAccountGroup,"ClaimsSubCenter",true);            //Changed by Abhishek
			EnvironmentSetup.selectByPartMatchInDropDown = false;
			
			//Added by Tejaswini
			executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionAllStatusChkBox");                   
			verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimsSubNewBatchSubmissionPage",false);           
			
			executeStep.performAction(SeleniumActions.Check, "","ClaimsSubmissionFinalizedStatusChkBox");             
			verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimsSubNewBatchSubmissionPage",false);           
			
			//Added by Tejaswini
			
			executeStep.performAction(SeleniumActions.Enter, "InsuranceValidityStartDate","ClaimsSubRegistrationDateFrom");
			verifications.verify(SeleniumVerifications.Entered, "InsuranceValidityStartDate","ClaimsSubRegistrationDateFrom",true);
	
			executeStep.performAction(SeleniumActions.Enter, "InsuranceValidityEndDate","ClaimsSubRegistrationDateTo");
			verifications.verify(SeleniumVerifications.Entered, "InsuranceValidityEndDate","ClaimsSubRegistrationDateTo",true);
			
			//Added by Tejaswini	
			DbFunctions dbFunctions = new DbFunctions();
			dbFunctions.storeDate(this.executeStep.getDataSet(), "InsuranceValidityStartDate","C",0);
			
			executeStep.performAction(SeleniumActions.Enter, "InsuranceValidityStartDate","ClaimsSubmissionLastBillFinalizedDateStart");
			verifications.verify(SeleniumVerifications.Entered, "InsuranceValidityStartDate","ClaimsSubmissionLastBillFinalizedDateStart",true);
			
			executeStep.performAction(SeleniumActions.Enter, "InsuranceValidityStartDate","ClaimsSubmissionLastBillFinalizedDateEnd");
			verifications.verify(SeleniumVerifications.Entered, "InsuranceValidityStartDate","ClaimsSubmissionLastBillFinalizedDateEnd",true);

			//Added by Tejaswini
			
			// Added by Alamelu to enable resubmission
			if (resubmission){
				executeStep.performAction(SeleniumActions.Check, "","ClaimsSubmissionResubmissionCheckbox");             
				verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimsSubNewBatchSubmissionPage",false); 
			}
			
		}
		else {
			executeStep.performAction(SeleniumActions.Select, "SecondaryInsuranceCo","ClaimsSubInsuranceCoName");
			verifications.verify(SeleniumVerifications.Selected, "SecondaryInsuranceCo","ClaimsSubInsuranceCoName",true);
			
			executeStep.performAction(SeleniumActions.Select, "SecondarySponsorName","ClaimsSubTPAName");
			verifications.verify(SeleniumVerifications.Selected, "SecondarySponsorName","ClaimsSubTPAName",true);
			
			executeStep.performAction(SeleniumActions.Select, "SecondaryInsurancePlanType","ClaimsSubNetworkPlanType");
			verifications.verify(SeleniumVerifications.Selected, "SecondaryInsurancePlanType","ClaimsSubNetworkPlanType",true);
			
			executeStep.performAction(SeleniumActions.Select, "SecondaryInsurancePlanName","ClaimsSubPlanName");
			verifications.verify(SeleniumVerifications.Selected, "SecondaryInsurancePlanName","ClaimsSubPlanName",true);
			
			executeStep.performAction(SeleniumActions.Select, centreAccountGroup,"ClaimsSubCenter");                   //Changed by Abhishek
			verifications.verify(SeleniumVerifications.Selected, centreAccountGroup,"ClaimsSubCenter",true);            //Changed by Abhishek
	
			//Added by Tejaswini
			executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionAllStatusChkBox");                   
			verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimsSubNewBatchSubmissionPage",false);           
			
			executeStep.performAction(SeleniumActions.Check, "","ClaimsSubmissionFinalizedStatusChkBox");             
			verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimsSubNewBatchSubmissionPage",false);           
			
			//Added by Tejaswini
			executeStep.performAction(SeleniumActions.Enter, "SecondaryInsuranceValidityStartDate","ClaimsSubRegistrationDateFrom");
			verifications.verify(SeleniumVerifications.Entered, "SecondaryInsuranceValidityStartDate","ClaimsSubRegistrationDateFrom",true);
	
			executeStep.performAction(SeleniumActions.Enter, "SecondaryInsuranceValidityEndDate","ClaimsSubRegistrationDateTo");
			verifications.verify(SeleniumVerifications.Entered, "SecondaryInsuranceValidityEndDate","ClaimsSubRegistrationDateTo",true);
			
			//Added by Tejaswini	
			DbFunctions dbFunctions = new DbFunctions();
			dbFunctions.storeDate(this.executeStep.getDataSet(), "InsuranceValidityStartDate","C",0);
			
			executeStep.performAction(SeleniumActions.Enter, "InsuranceValidityStartDate","ClaimsSubmissionLastBillFinalizedDateStart");
			verifications.verify(SeleniumVerifications.Entered, "InsuranceValidityStartDate","ClaimsSubmissionLastBillFinalizedDateStart",true);
			
			executeStep.performAction(SeleniumActions.Enter, "InsuranceValidityStartDate","ClaimsSubmissionLastBillFinalizedDateEnd");
			verifications.verify(SeleniumVerifications.Entered, "InsuranceValidityStartDate","ClaimsSubmissionLastBillFinalizedDateEnd",true);

			//Added by Tejaswini
			// Added by Alamelu to enable resubmission
			if (resubmission){
					executeStep.performAction(SeleniumActions.Check, "","ClaimsSubmissionResubmissionCheckbox");             
					verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimsSubNewBatchSubmissionPage",false); 
			}
		
		}
		executeStep.performAction(SeleniumActions.Click, "","ClaimsSubSubmitBtn");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimsSubNewBatchSubmissionPage",false);		
		
		String claimsSubmissionSuccessMsg = this.executeStep.getDriver().findElement(By.xpath("//div[@id='msgDiv']/div[2]/div[2]")).getText();
		
		System.out.println("Claims Submission Success Msg is :: " + claimsSubmissionSuccessMsg);
		int indexOfISO = claimsSubmissionSuccessMsg.indexOf("IS");
		System.out.println("Index of IS is :: " + indexOfISO);
		String submissionBatchId ="";
		try{
			if (indexOfISO != -1){
				submissionBatchId = claimsSubmissionSuccessMsg.substring(indexOfISO, indexOfISO+8);//the end of the index can be 8 as well. Check and change
			}
			System.out.println("Submission Batch ID is :: " + submissionBatchId);
			if (submissionBatchId =="" || submissionBatchId == null){
				submissionBatchId = "CLAIMS SUBMISSION BATCH ID IS NULL";
			}
			
			DbFunctions dbFunctions = new DbFunctions();
			
			dbFunctions.storeClaimsBatchSubmissionId(submissionBatchId, this.executeStep.getDataSet());
		} catch (Exception e){
			e.printStackTrace();
		}

		System.out.println("Claims Submission Done");
		return submissionBatchId;
	}
	
	public void remittanceUpload(String centreAccountGroup, String filename ){
		
		executeStep.performAction(SeleniumActions.Select, "PrimaryInsuranceCo","InsuranceCompanyNameDropDown");
		verifications.verify(SeleniumVerifications.Selected, "PrimaryInsuranceCo","InsuranceCompanyNameDropDown",true);
		
		executeStep.performAction(SeleniumActions.Select, "PrimarySponsorName","SponsorDropDown");
		verifications.verify(SeleniumVerifications.Selected, "PrimarySponsorName","SponsorDropDown",true);
		
		executeStep.performAction(SeleniumActions.Select, centreAccountGroup,"CentreAccGroupDropDown");
		verifications.verify(SeleniumVerifications.Selected, centreAccountGroup,"CentreAccGroupDropDown",true);
		
		executeStep.performAction(SeleniumActions.Enter, "InsuranceValidityStartDate","ReceivedDate");
		verifications.verify(SeleniumVerifications.Entered, "InsuranceValidityStartDate","ReceivedDate",true);
		
		Upload upload = new Upload(executeStep, verifications);
		upload.uploadClaims(filename);
		
		executeStep.performAction(SeleniumActions.Enter, "MRID","ReferenceNo");
		verifications.verify(SeleniumVerifications.Entered, "MRID","ReferenceNo",false);
		
		executeStep.performAction(SeleniumActions.Click, "","SubmitButton");                   
		verifications.verify(SeleniumVerifications.Appears,"" ,"RemittanceUploadPage",false);           
		
		}
	
	public void claimBill(){
		executeStep.performAction(SeleniumActions.Enter, "MRID","ClaimReconciliationPageMRID");
		verifications.verify(SeleniumVerifications.Entered, "MRID","ClaimReconciliationPageMRID",false);

		//Added by Tejaswini
		executeStep.performAction(SeleniumActions.Click, "","ClaimReconcilliationMoreOptions");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimReconcilliationSearchAllStatusChkBox",false);		

		
		executeStep.performAction(SeleniumActions.Check, "","ClaimReconcilliationSearchAllStatusChkBox");
		verifications.verify(SeleniumVerifications.Checked, "","ClaimReconcilliationSearchAllStatusChkBox",false);		
		//Added by Tejaswini
		
		executeStep.performAction(SeleniumActions.Click, "","ClaimReconciliationPageSearch");                   
		verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimPageTable",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ClaimPageTable");                   
		verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimBillsLink",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ClaimBillsLink");                   
		verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimPage",false);
		
		
	}
	public void claimsResubmission(){
		
		executeStep.performAction(SeleniumActions.Click, "","ClaimReconciliationClearButton");
		verifications.verify(SeleniumVerifications.Appears, "","ClaimReconciliationPage",false);
		
		executeStep.performAction(SeleniumActions.Enter, "ClaimsBatchSubmissionId","ClaimReconciliationSubmitBatchIdField");
		verifications.verify(SeleniumVerifications.Entered, "ClaimsBatchSubmissionId","ClaimReconciliationSubmitBatchIdField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ClaimReconciliationPageSearch");                   
		verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimReconciliationPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ClaimReconciliationClaimIDCheckbox");                   
		verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimReconciliationPage",false);
		
		executeStep.performAction(SeleniumActions.Select, "ClaimsResubmissionType","ResubmissionTypeDropDown");
		verifications.verify(SeleniumVerifications.Selected, "ClaimsResubmissionType","ResubmissionTypeDropDown",false);
		
		executeStep.performAction(SeleniumActions.Enter, "ClaimsResubmissionComment","ClaimsComment");
		verifications.verify(SeleniumVerifications.Entered, "ClaimsResubmissionComment","ClaimsComment",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ClaimReconciliationResubmissionButton");                   
		verifications.verify(SeleniumVerifications.Appears,"" ,"ClaimReconciliationPage",false);
		
		
	}
	
	public String getInsuranceDetails(String dataSet,String centreAcc){		
		//Connection testConnection = null;
		/*ResultSet testResultSet = null;
		int rowCount = 0;
		String strQuery;
		String downloadedFile="";
		Statement QueryStatement = null;*/		
		// changed 23Mar
		DbFunctions dbfunc = new DbFunctions();
		return (dbfunc.getInsuranceDetails(dataSet, centreAcc));
		/*String month= dbfunc.monthYear();
		
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
					+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
			QueryStatement = EnvironmentSetup.dBConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			strQuery = "SELECT PrimaryInsuranceCo,PrimarySponsorName,InsurancePlanType,InsurancePlanName from TestData where DataSet = '" + dataSet + "'";
			
			testResultSet = QueryStatement.executeQuery(strQuery);
			testResultSet.beforeFirst();
			String PrimaryInsuranceCo ="";
            String PrimarySponsorName="";
            String InsurancePlanType="";
            String InsurancePlanName="";
            
			while (testResultSet.next()){
				
                      PrimaryInsuranceCo =testResultSet.getString("PrimaryInsuranceCo");
                      PrimarySponsorName =testResultSet.getString("PrimarySponsorName");
                      InsurancePlanType= testResultSet.getString("InsurancePlanType");
                      InsurancePlanName=testResultSet.getString("InsurancePlanName");
                    //  getInsuranceDetails.put(PrimaryInsuranceCo,PrimarySponsorName,InsurancePlanType,InsurancePlanName);
                      //downloadedFile = PrimaryInsuranceCo+ "-" +PrimarySponsorName+"-All-"+month+"-"+InsurancePlanType+"-"+InsurancePlanName+"- Account Group -"+centreAcc;
                      downloadedFile = PrimaryInsuranceCo+ "-" +PrimarySponsorName+"-All-"+month+"-"+InsurancePlanType+"-"+InsurancePlanName+centreAcc;
                		
                      break;
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Executing Query on Objects: " + ex.toString());
		} finally{
			try {
				testResultSet.close();		
				testResultSet = null;
			} catch (SQLException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}							
		}
		//DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Account Group -Pharmacy
		System.out.println(downloadedFile);
		return downloadedFile;*/
		
		}
	
	public String getSecondaryInsuranceDetails(String dataSet,String centreAcc){
		//Connection testConnection = null;
		/*ResultSet testResultSet = null;
		int rowCount = 0;
		String strQuery;
		String downloadedFile="";
		Statement QueryStatement = null;*/
		// changed 23Mar
		DbFunctions dbfunc = new DbFunctions();
		return (dbfunc.getSecondaryInsuranceDetails(dataSet, centreAcc));
		/*String month= dbfunc.monthYear();
		
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
					+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
			QueryStatement = EnvironmentSetup.dBConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			strQuery = "SELECT SecondaryInsuranceCo,SecondarySponsorName,SecondaryInsurancePlanType,SecondaryInsurancePlanName from TestData where DataSet = '" + dataSet + "'";
			
			testResultSet = QueryStatement.executeQuery(strQuery);
			testResultSet.beforeFirst();
			String SecondaryInsuranceCo ="";
            String SecondarySponsorName="";
            String SecondaryInsurancePlanType="";
            String SecondaryInsurancePlanName="";
            
			while (testResultSet.next()){
				
				SecondaryInsuranceCo =testResultSet.getString("SecondaryInsuranceCo");
				SecondarySponsorName =testResultSet.getString("SecondarySponsorName");
				SecondaryInsurancePlanType= testResultSet.getString("SecondaryInsurancePlanType");
				SecondaryInsurancePlanName=testResultSet.getString("SecondaryInsurancePlanName");
                downloadedFile = SecondaryInsuranceCo+ "-" +SecondarySponsorName+"-All-"+month+"-"+SecondaryInsurancePlanType+"-"+SecondaryInsurancePlanName+centreAcc;
                		
                      break;
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Executing Query on Objects: " + ex.toString());
		} finally{
			try {
				testResultSet.close();
				testResultSet = null;
			} catch (SQLException e) {
					//TODO Auto-generated catch block
				e.printStackTrace();
			}							
		}
		//DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Account Group -Pharmacy
		System.out.println(downloadedFile);
		return downloadedFile;*/
		
		}
	
	public String getInsuranceDetailsForResubmission(String dataSet,String centreAcc){
		//Connection testConnection = null;
		/*ResultSet testResultSet = null;
		int rowCount = 0;
		String strQuery;
		String downloadedFile="";
		Statement QueryStatement = null;*/
		// changed 23Mar
		DbFunctions dbfunc = new DbFunctions();
		return (dbfunc.getInsuranceDetailsForResubmission(dataSet, centreAcc));
		/*String month= dbfunc.monthYear();
		
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
					+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
			QueryStatement = EnvironmentSetup.dBConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			strQuery = "SELECT PrimaryInsuranceCo,PrimarySponsorName,InsurancePlanType,InsurancePlanName from TestData where DataSet = '" + dataSet + "'";
			
			testResultSet = QueryStatement.executeQuery(strQuery);
			testResultSet.beforeFirst();
			String PrimaryInsuranceCo ="";
            String PrimarySponsorName="";
            String InsurancePlanType="";
            String InsurancePlanName="";
            
			while (testResultSet.next()){
				
                      PrimaryInsuranceCo =testResultSet.getString("PrimaryInsuranceCo");
                      PrimarySponsorName =testResultSet.getString("PrimarySponsorName");
                      InsurancePlanType= testResultSet.getString("InsurancePlanType");
                      InsurancePlanName=testResultSet.getString("InsurancePlanName");
                    //  getInsuranceDetails.put(PrimaryInsuranceCo,PrimarySponsorName,InsurancePlanType,InsurancePlanName);
                      //downloadedFile = PrimaryInsuranceCo+ "-" +PrimarySponsorName+"-All-"+month+"-"+InsurancePlanType+"-"+InsurancePlanName+"- Account Group -"+centreAcc;
                      downloadedFile = "Resubmission-"+PrimaryInsuranceCo+ "-" +PrimarySponsorName+"-All-"+month+"-"+InsurancePlanType+"-"+InsurancePlanName+centreAcc;
                		
                      break;
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Executing Query on Objects: " + ex.toString());
		} finally{
			try {
				testResultSet.close();
				testResultSet=null;
			} catch (SQLException e) {
					//TODO Auto-generated catch block
					e.printStackTrace();
			}							
		}
		//DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Account Group -Pharmacy
		System.out.println(downloadedFile);
		return downloadedFile;
		*/
		}
		
		public void generateEClaim(SiteNavigation navigation){
			navigation.navigateTo(this.executeStep.getDriver(), "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
			executeStep.performAction(SeleniumActions.Click, "","ClaimSubmissionsListClearLink");
			verifications.verify(SeleniumVerifications.Appears, "","ClaimsSubmissionPage",false);
			
			executeStep.performAction(SeleniumActions.Enter, "ClaimsBatchSubmissionId","ClaimsSubmissionListBatchIDField");
			verifications.verify(SeleniumVerifications.Entered, "ClaimsBatchSubmissionId","ClaimsSubmissionListBatchIDField",true);
			//executeStep.performAction(SeleniumActions.Click, "","ClaimSubmissionsListClearLink");

			executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionListSearchBtn");
			verifications.verify(SeleniumVerifications.Appears, "","ClaimsSubmissionTableRowToClick",true);

			executeStep.performAction(SeleniumActions.Click, "","ClaimsSubmissionTableRowToClick");
			//MRNoSearch search = new MRNoSearch(executeStep, verifications);
			verifications.verify(SeleniumVerifications.Appears, "","GenerateEClaimLink",true);
			executeStep.performAction(SeleniumActions.Click, "","GenerateEClaimLink");

		}	
	

}
