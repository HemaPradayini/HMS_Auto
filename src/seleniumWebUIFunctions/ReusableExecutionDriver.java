package seleniumWebUIFunctions;

import java.sql.ResultSet;

import genericFunctions.DataParser;
import genericFunctions.DatabaseFunctions;
import genericFunctions.EnvironmentSetup;
import genericFunctions.XMLFunctions;

//@SuppressWarnings("unused")
public class ReusableExecutionDriver extends KeywordSelectionLibrary{

	DataParser dataParser = new DataParser();

	//Action Expected.....
	String strCurrentAction = "";
	String strCurrentExpected = "";
	String strInternalDataSet = "";
	String strProceed = "";
	String strParameter = "";
	String strObject = "";
	String strLineItemsCriteriaColumns = "";
	String strLineItemsCriteriaData = "";
	String [] arrLineItemsCriteria;

	boolean blnCloseResultSet = true;
	boolean blnCloseLineItems = false;

	//TODO Cover with LineItems & Internal Reusables    
	boolean blnIsLastStep = false;
	boolean blnIsFirstStep = false;
	ResultSet previousReusableResultSet;
	String strPreviousReusableName="";
	String strPreviousLineItemId = "";
	ResultSet previousLineItemDataSet;
	int previousLineItemCount;

	String currentReusableName = "";

	public void ExecuteReusable(String strReusableName, String strReusableParams, String strCurrentDataSet){

		String [] arrReusableParams = null;

		currentReusableName = EnvironmentSetup.strReusableName;
		//<parameter>, <object>
		if(strReusableParams.trim().contains(",")){
			arrReusableParams = strReusableParams.split(",");
			strParameter = arrReusableParams[0].trim();
			strObject = arrReusableParams[1];
			EnvironmentSetup.logger.info("param =" + strParameter);
		}
		else{
			strParameter = strReusableParams;
			EnvironmentSetup.logger.info("param =" + strParameter);
		}

		//TODO - Internal
		storeExistingReferences("Reusable");

		try{
			//Check If LineItemDataSets are present
			if(!EnvironmentSetup.strLineItemDataSet.isEmpty()){
				if(EnvironmentSetup.blnInternalReusable==true){
					//TODO Check Working
					//storeExistingReferences("Reusable");
					if(EnvironmentSetup.internalLineItemsPresent ==true){
						String criteria = null;
						if(EnvironmentSetup.strChildLineItemCriteria==null){
							EnvironmentSetup.strChildLineItemCriteria="";
						}
						//Fetch Criteria (Only one possible) before reassigning EnvironmentSetup.lineItemDataResultSet
						if(!(EnvironmentSetup.strChildLineItemCriteria.isEmpty())){
							System.out.println(EnvironmentSetup.strChildLineItemCriteria);
							try{
								criteria = EnvironmentSetup.lineItemDataResultSet.getString(EnvironmentSetup.strChildLineItemCriteria);

							}
							catch(Exception ex){
								System.out.println(ex.toString());
								try{
									criteria = EnvironmentSetup.lineItemDataResultSet.getString(EnvironmentSetup.strChildLineItemCriteria);
								}
								catch(Exception ex1){
									try{
										System.out.println(ex1.toString());
										//EnvironmentSetup.lineItemDataResultSet.previous();
										criteria = EnvironmentSetup.lineItemDataResultSet.getString(EnvironmentSetup.strChildLineItemCriteria);
									}
									catch(Exception ex2){
										System.out.println(ex2.toString());
									}
								}
							}
						}

						storeExistingReferences("LineItems");

						//EnvironmentSetup.intGlobalLineItemCount = EnvironmentSetup.intGlobalLineItemCount +1;
						if(strReusableParams.trim().contains("'")){
							String paramsDataColumn = strReusableParams.replace("'", "");
							String paramsFromData = "";
							
							if(EnvironmentSetup.useExcelScripts == false){
								DatabaseFunctions.loadRequiredLineItems("*", EnvironmentSetup.strLineItemDataSet, EnvironmentSetup.strChildLineItemCriteria, criteria);
							}
							else{
								DatabaseFunctions.FetchLineItemData("*", EnvironmentSetup.strLineItemDataSet, EnvironmentSetup.strChildLineItemCriteria, criteria);
							}
							
							
							//Select Distinct AttributeReference from LineItems where.....
							try{
								boolean lineItemsPresent = false;
								blnCloseResultSet = false;
								EnvironmentSetup.lineItemDataResultSet.beforeFirst();
								while(EnvironmentSetup.lineItemDataResultSet.next()){
									blnCloseResultSet = true;
									lineItemsPresent = true;
									if(EnvironmentSetup.lineItemDataResultSet.isFirst()){
										strReusableParams = EnvironmentSetup.lineItemDataResultSet.getString(paramsDataColumn);
									}
									else{
										paramsFromData = EnvironmentSetup.lineItemDataResultSet.getString(paramsDataColumn);
										//DoSelect,DoSelect,VerifySelected,VerifySelected,VerifyDisplayed,VerifyDisplayed,DoSelect,VerifyDisplayed,VerifyDisplayed
										//24th Nov,2015	 -	Only Unique Values need to be used to avoid repetition
										if(!strReusableParams.contains(paramsFromData)){
											strReusableParams = strReusableParams + "," + paramsFromData;
										}
									}
								}
								
								if(lineItemsPresent == true){
									//strReusableParams = DoSelect,VerifySelected,VerifyDisplayed
									String[] params = strReusableParams.split(",");

									for (String param: params){
										strReusableParams = param;
										if(EnvironmentSetup.useExcelScripts == false){
											DatabaseFunctions.fetchReusableTestSteps(strReusableName, strReusableParams + ",All");
										}
										else{
											DatabaseFunctions.FetchData(EnvironmentSetup.strReusablesPath, "*", strReusableName, "Type", strReusableParams + ",All");
										}
										
										executeReusableForLineItems(strReusableParams, EnvironmentSetup.strChildLineItemId, EnvironmentSetup.strChildLineItemCriteria +","
												+ paramsDataColumn, criteria + "," + strReusableParams);
									}
								}
								revertToPreviousReferences("LineItems");
							}
							catch(Exception ex){
								revertToPreviousReferences("LineItems");
							}
						}
						else{
							if(EnvironmentSetup.useExcelScripts == false){
								DatabaseFunctions.fetchReusableTestSteps(strReusableName, strReusableParams + ",All");
							}
							else{
								DatabaseFunctions.FetchData(EnvironmentSetup.strReusablesPath, "*", strReusableName, "Type", strReusableParams + ",All");
							}
							executeReusableForLineItems(strReusableParams, EnvironmentSetup.strChildLineItemId, EnvironmentSetup.strChildLineItemCriteria, criteria);
							EnvironmentSetup.blnInternalReusable= false;
							revertToPreviousReferences("LineItems");
						}            		
					}
					else{
						if(EnvironmentSetup.fetchDataFromXML==false){
							if(EnvironmentSetup.useExcelScripts == false){
								DatabaseFunctions.fetchReusableTestSteps(strReusableName, strReusableParams + ",All");
							}
							else{
								DatabaseFunctions.FetchData(EnvironmentSetup.strReusablesPath, "*", strReusableName, "Type", strReusableParams + ",All");
							}
							
							reusableStepIterator(EnvironmentSetup.reusablesResultSet, strReusableParams, strCurrentDataSet);
							EnvironmentSetup.blnInternalReusable= false;
						}
						else{
							if(EnvironmentSetup.useExcelScripts == false){
								DatabaseFunctions.fetchReusableTestSteps(strReusableName, strReusableParams + ",All");
							}
							else{
								DatabaseFunctions.FetchData(EnvironmentSetup.strReusablesPath, "*", strReusableName, "Type", strReusableParams + ",All");
							}
							
							XMLFunctions.getChildNodeCount(EnvironmentSetup.XMLTagName);

							for(EnvironmentSetup.currentChildLineItemNum=0;EnvironmentSetup.currentChildLineItemNum<EnvironmentSetup.childLineItemCount;EnvironmentSetup.currentChildLineItemNum++){
								EnvironmentSetup.strCurrentLineItemDataSet = XMLFunctions.fetchDataFromXML("LineItemID", EnvironmentSetup.currentChildLineItemNum);
								if(EnvironmentSetup.currentChildLineItemNum==0){
									blnIsFirstStep = true;
									blnIsLastStep = false;
								}
								else{
									blnIsFirstStep = false;
								}
								if(EnvironmentSetup.currentChildLineItemNum==EnvironmentSetup.childLineItemCount-1){
									blnIsLastStep = true;
								}

								System.out.println("Last Step? " + blnIsLastStep);
								reusableStepIterator(EnvironmentSetup.reusablesResultSet, strReusableParams, strCurrentDataSet);
							}
							EnvironmentSetup.blnInternalReusable= false;
							EnvironmentSetup.fetchDataFromXML=false;
						}
					}

					revertToPreviousReferences("Reusables");

				}
				else{
					if (!EnvironmentSetup.strLineItemsCriteria.isEmpty()){
						if(EnvironmentSetup.strLineItemsCriteria.contains(":::")){
							//Create XML for Child Line Items
							String childLineItemId = "";
							arrLineItemsCriteria = EnvironmentSetup.strLineItemsCriteria.split(":::");
							childLineItemId = arrLineItemsCriteria[1];
							if(arrLineItemsCriteria[0].equalsIgnoreCase("ForEach")){
								//FetchDataSet and Create XML	NewDP
								if(EnvironmentSetup.useExcelScripts == false){
									DatabaseFunctions.loadRequiredLineItems("*",EnvironmentSetup.strLineItemDataSet,"","");
								}
								else{
									DatabaseFunctions.FetchLineItemData("*",EnvironmentSetup.strLineItemDataSet,"","");
								}
								
								XMLFunctions.createInputDataXML(EnvironmentSetup.lineItemDataResultSet, EnvironmentSetup.strLineItemDataSet);

								EnvironmentSetup.XMLDataPresent = true;
								closeLineItemsConnection();

								EnvironmentSetup.strLineItemDataSet = childLineItemId;	//
								if(EnvironmentSetup.useExcelScripts == false){
									DatabaseFunctions.fetchReusableTestSteps(strReusableName, strReusableParams + ",All");
								}
								else{
									DatabaseFunctions.FetchData(EnvironmentSetup.strReusablesPath, "*", strReusableName, "Type", strReusableParams + ",All");
								}
								
								executeReusableForLineItems(strReusableParams, strCurrentDataSet, "", "");
								EnvironmentSetup.fetchDataFromXML = false;
								EnvironmentSetup.XMLDataPresent = false;

							}
							else if(arrLineItemsCriteria[0].equalsIgnoreCase("With")){
								//With:::ContactTypes,Accounts
								if(childLineItemId.contains(",")){
									String[] childItems =childLineItemId.split(",");
									for(String childLine : childItems){
										DatabaseFunctions.FetchLineItemData("*",childLine,"","");
										XMLFunctions.createInputDataXML(EnvironmentSetup.lineItemDataResultSet, childLine);
										EnvironmentSetup.XMLDataPresent = true;
										closeLineItemsConnection();

									}
								}
								else{
									if(EnvironmentSetup.useExcelScripts == false){
										DatabaseFunctions.loadRequiredLineItems("*",childLineItemId,"","");
									}
									else{
										DatabaseFunctions.FetchLineItemData("*",childLineItemId,"","");
									}
									

									XMLFunctions.createInputDataXML(EnvironmentSetup.lineItemDataResultSet, childLineItemId);

									EnvironmentSetup.XMLDataPresent = true;
									closeLineItemsConnection();
								}

								if(EnvironmentSetup.useExcelScripts == false){
									DatabaseFunctions.fetchReusableTestSteps(strReusableName, strReusableParams + ",All");
								}
								else{
									DatabaseFunctions.FetchData(EnvironmentSetup.strReusablesPath, "*", strReusableName, "Type", strReusableParams + ",All");
								}
								
								if(checkIfLineItemsPresent()){
									executeReusableForLineItems(strReusableParams, EnvironmentSetup.strLineItemDataSet, "", "");
									closeLineItemsConnection();
								}
								else{
									EnvironmentSetup.strLineItemDataSet = "";
									System.out.println("Current DataSet" + strCurrentDataSet);
									reusableStepIterator(EnvironmentSetup.reusablesResultSet, strReusableParams, strCurrentDataSet);
								}
								//executeReusableForLineItems(strReusableParams, strCurrentDataSet, "", "");
								EnvironmentSetup.fetchDataFromXML = false;
								EnvironmentSetup.XMLDataPresent = false;
							}

						}
						else if(EnvironmentSetup.strLineItemsCriteria.contains(",")){
							arrLineItemsCriteria = EnvironmentSetup.strLineItemsCriteria.split(",");
							strLineItemsCriteriaColumns = arrLineItemsCriteria[0];

							try{
								strLineItemsCriteriaData = dataParser.getData(strParameter, strCurrentDataSet, false);
							}
							catch(Exception ex){
								EnvironmentSetup.logger.info("Reusable Library: Fetch Data For Line Item: " + ex.toString());
							}

							for (int iCount=1; iCount<arrLineItemsCriteria.length; iCount++){
								strLineItemsCriteriaColumns = strLineItemsCriteriaColumns + "," + arrLineItemsCriteria[iCount];
								strLineItemsCriteriaData = strLineItemsCriteriaData + "," + arrReusableParams[iCount]; 
							}

							if(EnvironmentSetup.useExcelScripts == false){
								DatabaseFunctions.fetchReusableTestSteps(strReusableName, strReusableParams + ",All");
							}
							else{
								DatabaseFunctions.FetchData(EnvironmentSetup.strReusablesPath, "*", strReusableName, "Type", strReusableParams + ",All");
							}
							
							executeReusableForLineItems(strReusableParams, strCurrentDataSet, strLineItemsCriteriaColumns, strLineItemsCriteriaData);
						}

						else if(EnvironmentSetup.strLineItemsCriteria.contains(">>")){
							String[] criteria =EnvironmentSetup.strLineItemsCriteria.split(">>"); 

							System.out.println(criteria[1] + "\t" + criteria[0]);
							if(EnvironmentSetup.useExcelScripts == false){
								DatabaseFunctions.loadRequiredLineItems("*", criteria[0], "", "");
							}
							else{
								DatabaseFunctions.FetchLineItemData("*", criteria[0], "", "");
							}
							
							//DatabaseFunctions.FetchLineItemData(criteria[1], criteria[0], "", "");
							//arrLineItemCriteriaData
							System.out.println(criteria[1] + "\t" + criteria[0]);
							EnvironmentSetup.lineItemDataResultSet.beforeFirst();
							if(EnvironmentSetup.lineItemDataResultSet.next()){
								String criteriaList = "";
								strLineItemsCriteriaColumns = criteria[1];
								EnvironmentSetup.strLineItemRefCriteriaColName = criteria[0];

								int arraycount=-1;
								EnvironmentSetup.lineItemDataResultSet.beforeFirst();

								while (EnvironmentSetup.lineItemDataResultSet.next()){
									arraycount = arraycount + 1;
									try{
										System.out.println(EnvironmentSetup.lineItemDataResultSet.getString(strLineItemsCriteriaColumns));
										if (arraycount==0){
											criteriaList = criteriaList + EnvironmentSetup.lineItemDataResultSet.getString(strLineItemsCriteriaColumns);
										}
										else{
											criteriaList = criteriaList + " >> " + EnvironmentSetup.lineItemDataResultSet.getString(strLineItemsCriteriaColumns);
										}

									}
									catch(Exception ex){
										System.out.println(ex.toString());
									}
								}

								//Close Line Items Connection
								closeLineItemsConnection();

								try{
									EnvironmentSetup.arrLineItemCriteriaData = criteriaList.split(" >> ");
								}
								catch(Exception ex){

								}

								System.out.println(EnvironmentSetup.arrLineItemCriteriaData.length);

								EnvironmentSetup.strLineItemsCriteria = "";

								if(EnvironmentSetup.useExcelScripts == false){
									DatabaseFunctions.fetchReusableTestSteps(strReusableName, strReusableParams + ",All");
								}
								else{
									DatabaseFunctions.FetchData(EnvironmentSetup.strReusablesPath, "*", strReusableName, "Type", strReusableParams + ",All");
								}

								for (int i=0; i<EnvironmentSetup.arrLineItemCriteriaData.length; i++){
									executeReusableForLineItems(strReusableParams, strCurrentDataSet, strLineItemsCriteriaColumns, EnvironmentSetup.arrLineItemCriteriaData[i]);
									//TODO Check If Required
									if(EnvironmentSetup.blnProceedExec == false && EnvironmentSetup.strTestStepstatus.contains("Fail")){
										EnvironmentSetup.strTestStepstatus = "Fail - Terminated";
										EnvironmentSetup.strTestCaseStatus = "Fail - Terminated";
										break;
									}
								}
							}

							else{
								String referenceData = dataParser.getData(criteria[1], strCurrentDataSet, false);
								System.out.println(referenceData);
								System.out.println(strReusableName);
								if(EnvironmentSetup.useExcelScripts == false){
									DatabaseFunctions.fetchReusableTestSteps(strReusableName, strReusableParams + ",All");
								}
								else{
									DatabaseFunctions.FetchData(EnvironmentSetup.strReusablesPath, "*", strReusableName, "Type", strReusableParams + ",All");
								}
								executeReusableForLineItems(strReusableParams, strCurrentDataSet, criteria[1], referenceData);
							}
						}
					}
					else{
						if(EnvironmentSetup.useExcelScripts == false){
							DatabaseFunctions.fetchReusableTestSteps(strReusableName, strReusableParams + ",All");
						}
						else{
							DatabaseFunctions.FetchData(EnvironmentSetup.strReusablesPath, "*", strReusableName, "Type", strReusableParams + ",All");
						}
						//If line items are present - execute with Line Items. If Not execute with main Data Set
						if(checkIfLineItemsPresent()){
							executeReusableForLineItems(strReusableParams, EnvironmentSetup.strLineItemDataSet, "", "");
							//closeLineItemsConnection();
						}
						else{
							EnvironmentSetup.strLineItemDataSet = "";
							if(!EnvironmentSetup.strModuleName.equalsIgnoreCase("Apttus")){
								reusableStepIterator(EnvironmentSetup.reusablesResultSet, strReusableParams, strCurrentDataSet);
							}
							else{
								EnvironmentSetup.logger.info("Skipping Test Step for Apttus: Line Items not present");
							}
						}
					}
				}
			}
			else{
				if(EnvironmentSetup.internalLineItemsPresent==true){
					EnvironmentSetup.logger.warning("Skipping - No Parent Data to Filter");
					EnvironmentSetup.internalLineItemsPresent = false;
				}
				else{
					System.out.println(strReusableName);
					//Run Query
					if(EnvironmentSetup.useExcelScripts == false){
						DatabaseFunctions.fetchReusableTestSteps(strReusableName, strReusableParams + ",All");
					}
					else{
						DatabaseFunctions.FetchData(EnvironmentSetup.strReusablesPath, "*", strReusableName, "Type", strReusableParams + ",All");
					}
					
					reusableStepIterator(EnvironmentSetup.reusablesResultSet, strReusableParams, strCurrentDataSet);
				}
			}

			revertToPreviousReferences("Reusables");
			
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Reusable Driver:Execute Reusable: " + ex.toString());
		}
	}

	private void reusableStepIterator(ResultSet rsCurrentReusablesteps, String strReusableParams, String strCurrentDataSet){
		try{

			if(strReusableParams.trim().contains(",")){
				String [] arrReusableParams = null;
				arrReusableParams = strReusableParams.split(",");
				strParameter = arrReusableParams[0].trim();
				strObject = arrReusableParams[1];
			}
			else{
				strParameter = strReusableParams;
			}

		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Reusable part 1: "+ ex.toString());
		}

		try{

			rsCurrentReusablesteps.beforeFirst();
			while (rsCurrentReusablesteps.next()){
				strCurrentAction = rsCurrentReusablesteps.getString("Action");
				strCurrentExpected = rsCurrentReusablesteps.getString("ExpectedResult");
				EnvironmentSetup.strProceedOnError = rsCurrentReusablesteps.getString("ProceedOnError");
				strInternalDataSet = rsCurrentReusablesteps.getString("DataSet");
				//TODO Test Reset TestStep Status
				EnvironmentSetup.strTestStepstatus = "";
				//EnvironmentSetup.strTestCaseStatus = "";

				String strType = rsCurrentReusablesteps.getString("Type");

				try{
					if(EnvironmentSetup.strProceedOnError.equalsIgnoreCase("Yes")){
						EnvironmentSetup.blnProceedExec = true;
					}

					if(EnvironmentSetup.strProceedOnError.equalsIgnoreCase("No")){
						EnvironmentSetup.blnProceedExec = false;
						System.out.println(EnvironmentSetup.strProceedOnError);
					}
				}
				catch(NullPointerException ex){
					EnvironmentSetup.blnProceedExec = false;
				}
				
				if (strType.contains("NotLast") && blnIsLastStep ==true){
					EnvironmentSetup.logger.info("Not Required - Skipping Last Step: Criteria NotLast");
				}
				else if (strType.contains("OnlyFirst") && blnIsFirstStep ==false){
					EnvironmentSetup.logger.info("Not Required - Skipping Step: Criteria OnlyFirst");
				}
				else if (strType.contains("OnlyLast") && blnIsLastStep ==false){
					EnvironmentSetup.logger.info("Not Required - Skipping Step: Criteria OnlyLast");
				}
				else if (strType.contains("NotFirst") && blnIsFirstStep == true){
					EnvironmentSetup.logger.info("Not Required - Skipping First Step: Criteria NotFirst");
				}

				else{
					if(strCurrentAction.contains("R:")){
						EnvironmentSetup.blnInternalReusable = true;

						try{
							if(strInternalDataSet!=null){
								EnvironmentSetup.strChildLineItemId = strInternalDataSet;
								if(EnvironmentSetup.strChildLineItemId!=null || !EnvironmentSetup.strChildLineItemId.isEmpty()){
									EnvironmentSetup.internalLineItemsPresent = true;
								}
								EnvironmentSetup.strChildLineItemCriteria = rsCurrentReusablesteps.getString("Criteria");
							}
						}
						catch(Exception ex){

						}


						if(strInternalDataSet!=null && EnvironmentSetup.XMLDataPresent ==true){
							if(strInternalDataSet!=""){
								EnvironmentSetup.fetchDataFromXML = true;
								EnvironmentSetup.internalLineItemsPresent = false;
								EnvironmentSetup.XMLTagName = strInternalDataSet;
								EnvironmentSetup.strLineItemDataSet = strInternalDataSet;
							}
							else{
								EnvironmentSetup.fetchDataFromXML = false;
								EnvironmentSetup.XMLTagName = "";
							}
						}
						else{
							EnvironmentSetup.fetchDataFromXML = false;
							EnvironmentSetup.XMLTagName = "";
						}
					}

					//Replace Parameters with Actuals
					if (strCurrentAction.trim().contains("<parameter>")){
						strCurrentAction = strCurrentAction.replace("<parameter>", strParameter);
					}

					//Replace Objects with Actuals
					if (strCurrentAction.trim().contains("<object>")){
						strCurrentAction = strCurrentAction.replace("<object>", strObject);
					}

					//Replace Parameters with Actuals
					if (strCurrentExpected.trim().contains("<parameter>")){
						strCurrentExpected = strCurrentExpected.replace("<parameter>", strParameter);
					}

					//Replace Objects with Actuals
					if (strCurrentExpected.trim().contains("<object>")){
						strCurrentExpected = strCurrentExpected.replace("<object>", strObject);
					}

					try{

						EnvironmentSetup.logger.info("Action within Reusable: " +strCurrentAction);
						//Call Keyword Selection Library
						selectandExecuteKeyword(strCurrentAction, strCurrentExpected, strCurrentDataSet);
					}
					catch (Exception ex){
						EnvironmentSetup.logger.info("Inside Reusable: " + ex.toString());
					}
				}

				if(EnvironmentSetup.blnProceedExec == false && EnvironmentSetup.strTestStepstatus.contains("Fail")){
					EnvironmentSetup.strTestStepstatus = "Fail - Terminated";
					EnvironmentSetup.strTestCaseStatus = "Fail - Terminated";
					break;
				}
			}
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Reusable: " + ex.toString());
		}

	}

	private void executeReusableForLineItems(String strReusableParams, String strCurrentDataSet, String criteriaColumns, String criteriaData){

		System.out.println(EnvironmentSetup.strLineItemDataSet + "\t" + criteriaColumns + "\t" + criteriaData);
		if(EnvironmentSetup.useExcelScripts == false){
			DatabaseFunctions.loadRequiredLineItems("*", EnvironmentSetup.strLineItemDataSet, criteriaColumns, criteriaData);
		}
		else{
			DatabaseFunctions.FetchLineItemData("*", EnvironmentSetup.strLineItemDataSet, criteriaColumns, criteriaData);
		}
		
		//Select * From LineItems where Dataset='DS-1' and LineItemID like '%Oppty%'
		try{
			EnvironmentSetup.lineItemDataResultSet.beforeFirst();
			//Execute for each Line Item

			while (EnvironmentSetup.lineItemDataResultSet.next()){
				EnvironmentSetup.strCurrentLineItemDataSet = EnvironmentSetup.lineItemDataResultSet.getString("LineItemID");
				EnvironmentSetup.intGlobalLineItemCount ++;	//<count> 

				System.out.println(EnvironmentSetup.intGlobalLineItemCount);
				//fetchDataFromLineItemRS
				EnvironmentSetup.logger.info("Line Item Id: " + EnvironmentSetup.lineItemDataResultSet.getString("LineItemID"));
				if(EnvironmentSetup.XMLDataPresent==false){
					blnIsLastStep = EnvironmentSetup.lineItemDataResultSet.isLast();
					blnIsFirstStep = EnvironmentSetup.lineItemDataResultSet.isFirst();
					EnvironmentSetup.logger.info("Is Last Step: " + blnIsLastStep);
					EnvironmentSetup.logger.info("Is First Step: " + blnIsFirstStep);
				}
				else{
					blnIsFirstStep = true;
					EnvironmentSetup.fetchDataFromXML=false;
				}

				System.out.println("Is First Step: " + blnIsFirstStep);
				reusableStepIterator(EnvironmentSetup.reusablesResultSet, strReusableParams, strCurrentDataSet);

				if(EnvironmentSetup.blnProceedExec == false && EnvironmentSetup.strTestStepstatus.contains("Fail")){
					EnvironmentSetup.strTestStepstatus = "Fail - Terminated";
					EnvironmentSetup.strTestCaseStatus = "Fail - Terminated";
					break;
				}
				EnvironmentSetup.reusablesResultSet.beforeFirst();
			}
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
	}


	//Checks if the LineItemID specified in the DataSetColumn is present or not. Used for Further Processing in the calling method.
	private boolean checkIfLineItemsPresent(){
		if(EnvironmentSetup.useExcelScripts == false){
			DatabaseFunctions.loadRequiredLineItems("*", EnvironmentSetup.strLineItemDataSet, "", "");
		}
		else{
			DatabaseFunctions.FetchLineItemData("*", EnvironmentSetup.strLineItemDataSet, "", "");
		}
		
		try{
			//EnvironmentSetup.lineItemDataResultSet.beforeFirst();
			if(EnvironmentSetup.lineItemDataResultSet.next()){
				closeLineItemsConnection();
				return true;
			}
			else{
				closeLineItemsConnection();
				return false;
			}
		}
		catch(Exception ex){
			closeLineItemsConnection();
			return false;
		}
	}


	private void storeExistingReferences(String Type){
		//String currentReusableName = "";
		try{
			if(EnvironmentSetup.blnInternalReusable==true){
				strPreviousReusableName = EnvironmentSetup.strParentReusableName;
				previousReusableResultSet = EnvironmentSetup.reusablesResultSet;
				//currentReusableName = EnvironmentSetup.strReusableName;
				EnvironmentSetup.strReusableName = strPreviousReusableName + " - " + currentReusableName;
				if(Type.equalsIgnoreCase("LineItems")){
					previousLineItemDataSet = EnvironmentSetup.lineItemDataResultSet;
					strPreviousLineItemId = EnvironmentSetup.strLineItemDataSet; //EditContractLine
					EnvironmentSetup.strLineItemDataSet = EnvironmentSetup.strChildLineItemId;
					previousLineItemCount = EnvironmentSetup.intGlobalLineItemCount;
					EnvironmentSetup.strPreviousLineItemDataSet = EnvironmentSetup.strCurrentLineItemDataSet;
					EnvironmentSetup.intGlobalLineItemCount = 0;
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

	}

	private void revertToPreviousReferences(String Type){
		try{
			EnvironmentSetup.strReusableName = EnvironmentSetup.strParentReusableName;
			EnvironmentSetup.reusablesResultSet = previousReusableResultSet;
			EnvironmentSetup.strParentReusableName = strPreviousReusableName;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		if(Type.equalsIgnoreCase("LineItems")){
			EnvironmentSetup.blnInternalReusable= false;
			EnvironmentSetup.lineItemDataResultSet =previousLineItemDataSet; 
			EnvironmentSetup.strLineItemDataSet = strPreviousLineItemId;
			EnvironmentSetup.blnInternalReusable= false;
			EnvironmentSetup.internalLineItemsPresent= false;
			System.out.println("Previous Line Item: " + strPreviousLineItemId);
			EnvironmentSetup.strChildLineItemCriteria = "";
			EnvironmentSetup.strChildLineItemId = "";
			EnvironmentSetup.strCurrentLineItemDataSet = EnvironmentSetup.strPreviousLineItemDataSet;
			EnvironmentSetup.intGlobalLineItemCount = previousLineItemCount;
		}

	}

	private void closeLineItemsConnection(){
		
		if(EnvironmentSetup.useExcelScripts == false){
			try{
				EnvironmentSetup.dBConnection.commit();
			}
			catch(Exception ex){
				
			}
			try{
				EnvironmentSetup.lineItemDataResultSet.close();
			}
			catch(Exception ex){
				
			}
		}
		else{
			try{
				EnvironmentSetup.lineItemDataConnection.commit();
			}
			catch(Exception ex){

			}
			try{
				EnvironmentSetup.lineItemDataResultSet.close();
			}
			catch(Exception ex){

			}
			try{
				EnvironmentSetup.lineItemDataConnection.close();
			}
			catch(Exception ex){

			}
		}
		
	}
}               

