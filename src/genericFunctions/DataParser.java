package genericFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.By;


public class DataParser{
	
	ResultSet rsObjectDefn = null;
	ResultSet rsInputData = null;
	boolean calledWithObject = false;
	
	//DatabaseFunctions dbFunction = new DatabaseFunctions();
	
	public String [] parseActionData(String strCurrentStep){
		String strCurrentKeyword = "";
		
		String [] arrTempCurrentStep = null;
		
		String [] arrCurrentStep = {"","","",""};
		//Map<String,String> arrCurrentStep = new HashMap<String,String>();
		
		int intArraySize = 0;
		
		//If it contains 3 spaces, replace with 1
		if (strCurrentStep.contains("  ")){
			strCurrentStep = strCurrentStep.replace("  ", " "); 
		}
		//if it contains 2 spaces replace with 1
		else if (strCurrentStep.contains("   ")){
			strCurrentStep = strCurrentStep.replace("   ", " ");
		}
		
		//arrCurrentStep -> 0 - Keyword, 1 - Object, 2 - Data, 3 - VerificationType
		//Split with Space
		arrTempCurrentStep = strCurrentStep.split(" ");
		//Number of elements in the keyword structure
		intArraySize = arrTempCurrentStep.length;
		
		//Keyword is ALWAYS the first element
		strCurrentKeyword= arrTempCurrentStep[0];
		arrCurrentStep[0]= arrTempCurrentStep[0];
		
		//arrCurrentStep.put("Keyword", strCurrentKeyword);
		switch (strCurrentKeyword.toUpperCase()) {
		
		case "CLICK":
			//Click on EditOpportunity for OpportunityName in OpportunityTable
			if (intArraySize > 3 && intArraySize < 7){		//Click on <data> in Object
				
				if(arrTempCurrentStep[3].trim().equalsIgnoreCase("for")){
					arrCurrentStep[1] = arrTempCurrentStep[2];
					arrCurrentStep[2] =  arrTempCurrentStep[intArraySize-1];
				}
				else{
					arrCurrentStep[2] = arrTempCurrentStep[2];
					arrCurrentStep[1] =  arrTempCurrentStep[intArraySize-1];
				}
			}
			else{
				arrCurrentStep[1] =  arrTempCurrentStep[intArraySize-1];
			}
			//Click on Object OR Click on <data> in Object (Object/Fieldname should be the last parameter)
			//Click on Object For data
				
			break;
		
		case "STORE":
			
			//Store Data
			//Store FieldData in DataSheetFieldName
			if(intArraySize>3){
				arrCurrentStep[1] =  arrTempCurrentStep[1];
				arrCurrentStep[2] =  arrTempCurrentStep[intArraySize-1];
			}
			else
				arrCurrentStep[1] =  arrTempCurrentStep[intArraySize-1];
			
			break;
			//arrCurrentStep -> 0 - Keyword, 1 - Object, 2 - Data, 3 - VerificationType
		case "CHOOSE":	
		case "CHECK":
			if (intArraySize >3){		//Choose data in object
				//Check Data in Field
				arrCurrentStep[2] = arrTempCurrentStep[1];
				arrCurrentStep[1] =  arrTempCurrentStep[intArraySize-1];
				if(intArraySize>5){
					if(arrTempCurrentStep[4].trim().equalsIgnoreCase("for")){
						arrCurrentStep[2] =  arrTempCurrentStep[1];
						arrCurrentStep[3] =  "";
						arrCurrentStep[1] =  arrTempCurrentStep[3];
						EnvironmentSetup.strUniqueValueField = arrTempCurrentStep[intArraySize-1];
						EnvironmentSetup.logger.info("Current Step: " + strCurrentStep + "<<");
						EnvironmentSetup.logger.info("Last Value: " + arrTempCurrentStep[intArraySize-1] + "<<");
						EnvironmentSetup.logger.info("Unique Step Value: " + EnvironmentSetup.strUniqueValueField + "<<");
						
					}
				}
			}
			else{
				arrCurrentStep[2] = arrTempCurrentStep[intArraySize-1];
				arrCurrentStep[1] =  arrTempCurrentStep[intArraySize-1];
			}
			
			break;
			
		case "VERIFY":		//arrCurrentStep -> 0 - Keyword, 1 - Object, 2 - Data, 3 - VerificationType
			//Verify Field for Data appears
			if (intArraySize == 3){		//Verify Field Appears, 'Verify Field Present
				arrCurrentStep[1] = arrTempCurrentStep[1];
				arrCurrentStep[3] = arrTempCurrentStep[intArraySize-1];
			}
			else if (intArraySize == 4){		//Verify Object is Checked, 'Verify Field is ReadOnly, 'Verify Field is Editable
				if(arrTempCurrentStep[2].trim().equalsIgnoreCase("for")){
					arrCurrentStep[1] = arrTempCurrentStep[1];
					arrCurrentStep[2] = arrTempCurrentStep[3];
					arrCurrentStep[3] = arrTempCurrentStep[intArraySize-1];
				}
				else{
					arrCurrentStep[1] = arrTempCurrentStep[1];
					arrCurrentStep[3] = arrTempCurrentStep[intArraySize-1];
				}
			}
			else if (intArraySize == 5){		//Verify <data> Displayed in Field 'Verify <data> selected in field
				arrCurrentStep[2] = arrTempCurrentStep[1];
				arrCurrentStep[3] = arrTempCurrentStep[2];
				arrCurrentStep[1] =  arrTempCurrentStep[intArraySize-1];
			}

			//verify Data Displayed in Field for Unique
			else if(intArraySize == 7){
				arrCurrentStep[2] = arrTempCurrentStep[1];
				arrCurrentStep[3] = arrTempCurrentStep[2];
				arrCurrentStep[1] =  arrTempCurrentStep[4];
				EnvironmentSetup.strUniqueValueField = arrTempCurrentStep[intArraySize-1];
				EnvironmentSetup.logger.info("Current Step: " + strCurrentStep + "<<");
				EnvironmentSetup.logger.info("Last Value: " + arrTempCurrentStep[intArraySize-1] + "<<");
				
				EnvironmentSetup.logger.info("Unique Step Value: " + EnvironmentSetup.strUniqueValueField + "<<");
			}
			
			break;
			
		case "ENTER":
		case "SELECT":
		case "COMPOSE":
		case "ATTACH": //Compose Data in Field For Unique
			//Enter <data> in Field,	'Select <data> in Field
			//Enter Username,	'Select ERType	'Field and data names are the same
			//arrCurrentStep -> 0 - Keyword, 1 - Object, 2 - Data, 3 - VerificationType
			
			//Enter Data for Unique in Object
			if (intArraySize == 3){		
				//Get Object Name
				arrCurrentStep[1] = arrTempCurrentStep[1];
			}
			
			//
			if (intArraySize == 4){		
				
				arrCurrentStep[2] = arrTempCurrentStep[1];
			}
			
			try{
				arrCurrentStep[1] =  arrTempCurrentStep[intArraySize-1];
				
				if(arrCurrentStep[0].equalsIgnoreCase("Select")){
					EnvironmentSetup.logger.info("SELECT >> "+ arrCurrentStep[1]);
				}
			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Inside Select Parser: " + ex.toString());
				
			}
			
			//Enter Data in Object for unique
			//arrCurrentStep -> 0 - Keyword, 1 - Object, 2 - Data, 3 - VerificationType
			if (intArraySize == 6){
				
				arrCurrentStep[2] =  arrTempCurrentStep[2];
				
				arrCurrentStep[3] =  arrTempCurrentStep[3];
				
				arrCurrentStep[1] =  arrTempCurrentStep[intArraySize-1];
				//arrCurrentStep -> 0 - Keyword, 1 - Object, 2 - Data, 3 - VerificationType
				if(arrTempCurrentStep[4].trim().equalsIgnoreCase("for")){
					arrCurrentStep[2] =  arrTempCurrentStep[1];
					arrCurrentStep[3] =  "";
					arrCurrentStep[1] =  arrTempCurrentStep[3];
					EnvironmentSetup.strUniqueValueField = arrTempCurrentStep[intArraySize-1];
					EnvironmentSetup.logger.info("Current Step: " + strCurrentStep + "<<");
					EnvironmentSetup.logger.info("Last Value: " + arrTempCurrentStep[intArraySize-1] + "<<");
					EnvironmentSetup.logger.info("Unique Step Value: " + EnvironmentSetup.strUniqueValueField + "<<");
					
				}
				else{
					arrCurrentStep[2] =  arrTempCurrentStep[2];
					arrCurrentStep[3] =  arrTempCurrentStep[3];
					arrCurrentStep[1] =  arrTempCurrentStep[intArraySize-1];
				}
				
				
			}
			
			break;
		
		case "R:":	//arrCurrentStep -> 0 - Keyword, 1 - Object, 2 - Data, 3 - VerificationType
			arrCurrentStep[1] = arrTempCurrentStep[1];
			arrCurrentStep[2] = arrTempCurrentStep[2];
			if (intArraySize == 4){
				arrCurrentStep[3] = arrTempCurrentStep[3];
			}
			break;
			
		default:
			arrCurrentStep[1] =  arrTempCurrentStep[intArraySize-1];
			break;
		}
		EnvironmentSetup.logger.info("Keyword: " + arrCurrentStep[0] + ";\n Object: " + arrCurrentStep[1] + ";\n DataName: " + arrCurrentStep[2] + ";\n VerificationType: " 
				+ arrCurrentStep[3]);
		
		return arrCurrentStep;
	}
	
	public String[] parseVerificationData(String strCurrentStep){
				
		String [] arrTempCurrentStep = {"","",""};
		String [] arrVerificationParams = {"","",""};
		//Map<String,String> arrCurrentStep = new HashMap<String,String>();
		EnvironmentSetup.logger.info(strCurrentStep);
				
		int intArraySize = 0;
		if (strCurrentStep.contains("  ")){
			strCurrentStep = strCurrentStep.replace("  ", " ");
		}
		else if (strCurrentStep.contains("   ")){
			strCurrentStep = strCurrentStep.replace("   ", " ");
		}
		//arrCurrentStep -> 0 - VerificationType, 1 - Object, 2 - Data
		arrTempCurrentStep = strCurrentStep.split(" ");
		intArraySize = arrTempCurrentStep.length;
		//Verify Data Checked in Field for Unique
		try{
			if (intArraySize == 2){
				arrVerificationParams[0] = arrTempCurrentStep[1];
				arrVerificationParams[1] = arrTempCurrentStep[0];
				arrVerificationParams[2] = "";
			}
			else if (intArraySize == 3){
				//Dialog does NotAppear
				if(arrTempCurrentStep[1].equalsIgnoreCase("does")){
					arrVerificationParams[0] = arrTempCurrentStep[2];
					arrVerificationParams[1] = arrTempCurrentStep[0];
				}
				else{
					arrVerificationParams[0] = arrTempCurrentStep[1];
					arrVerificationParams[1] = arrTempCurrentStep[2];
					arrVerificationParams[2] = arrTempCurrentStep[0];
				}
			}
			else if (intArraySize > 3){
				//Field for Data Appears
				//HideOptionsForOverage for ProductName Appears
				if(arrTempCurrentStep[1].trim().equalsIgnoreCase("for")){
					arrVerificationParams[0] = arrTempCurrentStep[intArraySize-1];
					arrVerificationParams[1] = arrTempCurrentStep[0];
					arrVerificationParams[2] = arrTempCurrentStep[2];
				}
				else{
					arrVerificationParams[0] = arrTempCurrentStep[1];
					arrVerificationParams[1] = arrTempCurrentStep[3];
					arrVerificationParams[2] = arrTempCurrentStep[0];
				}
			}
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Verification Parser: "+ ex.toString());
		}
		
		EnvironmentSetup.logger.info("Verification Type: " + arrVerificationParams[0] + " Object: " + arrVerificationParams[1] + " Data: " + arrVerificationParams[2]);
		return arrVerificationParams;
	}
	
	
	public String getData(String strDataFieldName, String strCurrentDataSet){
		String strInputData = "";
		//String str_InputData="";
		
		EnvironmentSetup.logger.info("Current Data Field Name: " + strDataFieldName);
		
		
		if (!(EnvironmentSetup.strLineItemDataSet.isEmpty()) || EnvironmentSetup.strLineItemDataSet !=""){
			if(EnvironmentSetup.fetchDataFromXML==false){
				try{	
					//EnvironmentSetup.lineItemDataResultSet.beforeFirst();
					//if (EnvironmentSetup.lineItemDataResultSet.next()){
						strInputData = EnvironmentSetup.lineItemDataResultSet.getString(strDataFieldName);	
					//}
					
				}
				catch (NullPointerException ex1){
					EnvironmentSetup.logger.info("Null pointer exception: " + ex1.toString());
					strInputData = " ";
				}
				catch (Exception ex)
				{
					EnvironmentSetup.logger.info("Get Data From Line Items: " + ex.toString());
					try{
						//EnvironmentSetup.lineItemDataResultSet.previous();
						strInputData = EnvironmentSetup.lineItemDataResultSet.getString(strDataFieldName);
					}
					catch(Exception ex1){
						System.out.println(ex1.toString());
						strInputData = "";
						try{
							//EnvironmentSetup.lineItemDataResultSet.previous();
							strInputData = EnvironmentSetup.lineItemDataResultSet.getString(strDataFieldName);
						}
						catch(Exception ex2){
							System.out.println(ex1.toString());
							strInputData = "";
						}
					}
				}
				
				if(strInputData==null){
					strInputData="";
				}
			}
			else{
				strInputData = XMLFunctions.fetchDataFromXML(strDataFieldName, EnvironmentSetup.currentChildLineItemNum);
			}
			
		}
		else{
			try{
				/** Changes to Include DB Source*/
				if(EnvironmentSetup.useExcelScripts ==false){
					try{
						strInputData = DatabaseFunctions.fetchRequiredTestData(strDataFieldName, strCurrentDataSet,"","");
					}
					catch (Exception ex){
						EnvironmentSetup.logger.info("Null Record Set Data: " + ex.toString());
						strInputData = "";
					}
				}
				//rsInputData = dbFunction.FetchData(strDataSheetPath, "*", strDataSheetName, "DataSet", strCurrentDataSet);
				else{
				String strCondColName = "";
					String strTableName = "";
					String strFieldName = "";
					if ((EnvironmentSetup.UseLineItem) && !(calledWithObject)){
						strCondColName = "LineItemID";
						//strTableName = "LineItems";
						strFieldName = strDataFieldName;
						System.out.println("Line Item Sheet Name is :: " +strTableName + "Condition Column Name is :: " + strCondColName +"Current Data Set :: "+ strCurrentDataSet);
					} else{
						strCondColName = "DataSet";
						//strTableName = EnvironmentSetup.strDataSheetName;
						strFieldName = "*";
						System.out.println("DataSheet Name is :: " +strTableName + "Condition Column Name is :: " + strCondColName);						
					}
					
					DatabaseFunctions.FetchData(EnvironmentSetup.strDataSheetPath, strFieldName, EnvironmentSetup.strDataSheetName, strCondColName, strCurrentDataSet);

					//DatabaseFunctions.FetchData(EnvironmentSetup.strDataSheetPath, "*", EnvironmentSetup.strDataSheetName, "DataSet", strCurrentDataSet);
					EnvironmentSetup.logger.info(EnvironmentSetup.strDataSheetName + " " + strCurrentDataSet);
					//if(!strInputData.equalsIgnoreCase("Null")){ // check whether string is null!!
					if(strInputData!=null){
						try{
							//Added by Tejaswini
							strInputData = getDataFromResultSet(strDataFieldName);
							//Added by Tejaswini
//							if (!(EnvironmentSetup.UseLineItem)){
//								EnvironmentSetup.testDataResultSet.beforeFirst();
//								//TODO For making multiple items from LineItems to be picker i
//								System.out.println("Called with object value is :: " + calledWithObject);
//	//							if (EnvironmentSetup.testDataResultSet.next()){
//								while(EnvironmentSetup.testDataResultSet.next()){												
//									strInputData = EnvironmentSetup.testDataResultSet.getString(strDataFieldName);	
//									System.out.println("Value from result set is :: " + strInputData); 
//									break;
//								}							
//							}else {
//								EnvironmentSetup.lineItemResultSet.beforeFirst();
//									//TODO For making multiple items from LineItems to be picker i
//								System.out.println("Called with object value is :: " + calledWithObject);
//		//							if (EnvironmentSetup.testDataResultSet.next()){
//								while(EnvironmentSetup.lineItemResultSet.next()){											
//		//									while(EnvironmentSetup.testDataResultSet.next()){
//									System.out.println("Value from Data sheet is :: " + strInputData + " for the line Item Count :: " + EnvironmentSetup.lineItemCount+"EnvironmentSetup.lineItemResultSet :"+EnvironmentSetup.lineItemResultSet );										
//									if ((EnvironmentSetup.lineItemResultSet.getRow()-1)==EnvironmentSetup.lineItemCount){
//										strInputData = EnvironmentSetup.lineItemResultSet.getString(strDataFieldName);
//										System.out.println("Value from Data sheet is :: " + strInputData + " for the line Item Count :: " + EnvironmentSetup.lineItemCount);
//										break;
//									}
//								} 							
//							}								
						}
						catch (NullPointerException ex1){
							ex1.printStackTrace();
							EnvironmentSetup.logger.info("Null pointer exception: " + ex1.toString());
							strInputData = " ";
						}
						catch (Exception ex)
						{
							strInputData = "";
							EnvironmentSetup.logger.info("Get Data From Line Items: " + ex.toString());
							//ex.printStackTrace();
							System.out.println("DataParser.java :"+ex.toString()+ " - COLUMN : " +strDataFieldName );
							//Added by Tejaswini - for catering to the requirement of catering to more than 255 columns
							if (ex.toString().contains("Column not found")){
								DatabaseFunctions.FetchData(EnvironmentSetup.strDataSheetPath, strFieldName, EnvironmentSetup.strAddendumDataSheetName, strCondColName, strCurrentDataSet);
								if(strInputData!=null){
									try{
										//Added by Tejaswini
										strInputData = getDataFromResultSet(strDataFieldName);
									} catch (Exception e){
										EnvironmentSetup.logger.info("Get Data from Addendum Sheet failed: " + ex.toString());
									}
							}
							//Added by Tejaswini
						}
/*						finally{
							if (EnvironmentSetup.testDataResultSet != null )
							{
								EnvironmentSetup.testDataResultSet.close();
							}
						}*/
					}
					}
					else{
						strInputData = "";
					}
				}
				
				try{
					 EnvironmentSetup.testDataResultSet.close();
				}
				catch (Exception ex){
					EnvironmentSetup.logger.info("Closing Test Data Result Set: " + ex.toString());
				}
				
				try{
					if(EnvironmentSetup.useExcelScripts == true){
						EnvironmentSetup.testDataConnection.close();
					}
					  
				}
				catch (Exception ex1){
					  EnvironmentSetup.logger.info("Fetch closing Connection: " + ex1.toString());
				}
			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Null Record Set Data: " + ex.toString());
			}
		}
		
		if(strInputData==null){
			strInputData = "";
		}
		
		EnvironmentSetup.logger.info("Input Data Fetched From Sheet: " + strInputData);
		return strInputData;
	}
	
	public String getData(String strDataFieldName, String strCurrentDataSet, boolean blnFetchFromLine){
		String strInputData = "";
		//String str_InputData="";
		
		try{
			//rsInputData = dbFunction.FetchData(strDataSheetPath, "*", strDataSheetName, "DataSet", strCurrentDataSet);
			if(EnvironmentSetup.useExcelScripts == false){
				
			}
			else{
				try{
					DatabaseFunctions.FetchData(EnvironmentSetup.strDataSheetPath, "*", EnvironmentSetup.strDataSheetName, "DataSet", strCurrentDataSet);
				}
				catch(Exception ex){
					EnvironmentSetup.logger.info("Null Record Set Data: " + ex.toString());
					strInputData = "Null";
				}
				
				if(!strInputData.equalsIgnoreCase("Null")){
					try{	
						EnvironmentSetup.testDataResultSet.beforeFirst();
						if (EnvironmentSetup.testDataResultSet.next()){
							strInputData = EnvironmentSetup.testDataResultSet.getString(strDataFieldName);	
						}
						
					}
					catch (NullPointerException ex1){
						EnvironmentSetup.logger.info("Null pointer exception: " + ex1.toString());
						strInputData = " ";
					}
					catch (Exception ex)
					{
						strInputData = "";
						EnvironmentSetup.logger.info("CLICK DATA>> " + ex.toString());
					}
				}
				else{
					strInputData = "";
				}
			}
			
			EnvironmentSetup.logger.info(EnvironmentSetup.strDataSheetName + " " + strCurrentDataSet);
		}
		catch (Exception ex){
			
		}
		
		
		try{
			 EnvironmentSetup.testDataResultSet.close();
		 }
		 catch (Exception ex){
			 EnvironmentSetup.logger.info("Closing Test Data Result Set: " + ex.toString());
		 }
		
		try{
			if(EnvironmentSetup.useExcelScripts == true){
				EnvironmentSetup.testDataConnection.close();
			}
		}
		catch (Exception ex1){
			  EnvironmentSetup.logger.info("Fetch closing Connection: " + ex1.toString());
		}
		
		return strInputData;
	}
		
	public String getObjectProperty(String strCurrentObjectName){
		String strObjectProperty = "";
		if(EnvironmentSetup.useExcelScripts == false){
			strCurrentObjectName = DatabaseFunctions.fetchObjectProperty(strCurrentObjectName);
		}
		else{
			 //rsObjectDefn = dbFunction.FetchData(strObjectSheetPath, "*", strObjectSheetName, "FieldName", strCurrentObjectName);
			DatabaseFunctions.FetchData(EnvironmentSetup.strObjectSheetPath, "*", EnvironmentSetup.strObjectSheetName, "FieldName", strCurrentObjectName);
			
			try{
				 EnvironmentSetup.testObjectsResultSet.beforeFirst();
				 while (EnvironmentSetup.testObjectsResultSet.next()){
					strObjectProperty = EnvironmentSetup.testObjectsResultSet.getString("Property");
				}
			}
			catch (Exception ex)
			{
				EnvironmentSetup.logger.info("Object 2 >> " + ex.toString());
				strObjectProperty = "";
			}
			 try{
				 EnvironmentSetup.testObjectsResultSet.close();
			 }
			 catch (Exception ex){
				 EnvironmentSetup.logger.info("Closing Test Objects Result Set: " + ex.toString());
			 }
			 
			 try{
				 EnvironmentSetup.testObjectsConnection.close();
			 }
			 catch (Exception ex1){
				 EnvironmentSetup.logger.info("Objects closing Connection: " + ex1.toString());
			 }
		}
		
		 EnvironmentSetup.logger.info("Current Object Property: " + strObjectProperty);

		 return strObjectProperty;
	}
		
	public By getObjectIdentifierType(String strTempObjectProp){
		By identifier = null;
		String strIdentifier = "";
		String strProperty = "";
		String [] arrObjectProp = {"",""};
		
		arrObjectProp = strTempObjectProp.trim().split("=", 2);
		strIdentifier = arrObjectProp[0].trim();
		strProperty = arrObjectProp[1].trim();
		
		switch (strIdentifier.toUpperCase()) {
		case "XPATH":
			identifier = By.xpath(strProperty);
			break;
		
		case "CSS":
			identifier = By.cssSelector(strProperty);
			break;
		
		case "ID":
			identifier = By.id(strProperty);
			EnvironmentSetup.logger.info(strProperty);
			break;
			
		case "NAME":
			identifier = By.name(strProperty);
			break;
			
		case "LINK":
			identifier = By.linkText(strProperty);
			break;
			
		case "CLASS":
			identifier = By.className(strProperty);
			break;
		
		case "TAG":
			identifier = By.tagName(strProperty);
			break;
			
		default:
			identifier = By.xpath(strProperty);
			break;
		}  
		return identifier;
	}
	
	/*
	 * 
	 * 
	 * 
	*/
	public String BuildObjectWithData(String strObjectProperty,String strCurrentDataSet){
		String[] valuesToReplace = null;
		calledWithObject = true;
		String replacementValue = "";
		EnvironmentSetup.uniqueValues = "";
		if(!EnvironmentSetup.strUniqueValueField.isEmpty()){
			if(EnvironmentSetup.strUniqueValueField.contains(",")){
				valuesToReplace = EnvironmentSetup.strUniqueValueField.split(",");
				try{
					for(int i=0;i<valuesToReplace.length;i++){
						if(strObjectProperty.contains("<" + valuesToReplace[i] +">")){
							replacementValue = getData(valuesToReplace[i], strCurrentDataSet);
							strObjectProperty = strObjectProperty.replace("<" + valuesToReplace[i] +">", replacementValue.trim());
							EnvironmentSetup.uniqueValues =  EnvironmentSetup.uniqueValues + replacementValue + " ";
						}
					}
				}
				catch(Exception ex){
					
				}
			}
			else{ 
				if(strObjectProperty.contains("<"+EnvironmentSetup.strUniqueValueField+">")){
					replacementValue = getData(EnvironmentSetup.strUniqueValueField, strCurrentDataSet);
					strObjectProperty = strObjectProperty.replace("<"+EnvironmentSetup.strUniqueValueField+">", replacementValue.trim());
				}
				
				if(strObjectProperty.contains("<unique>")){
					replacementValue = getData(EnvironmentSetup.strUniqueValueField, strCurrentDataSet);
					strObjectProperty = strObjectProperty.replace("<unique>", replacementValue.trim());
					EnvironmentSetup.uniqueValues =replacementValue; 
				}
			}
			
			if(strObjectProperty.contains("<count>")){
				strObjectProperty = strObjectProperty.replace("<count>", Integer.toString(EnvironmentSetup.intGlobalLineItemCount));
			}
		}
		
		System.out.println("Final Object: " + strObjectProperty);
		EnvironmentSetup.logger.info("Final Object: " + strObjectProperty);
		calledWithObject = false;
		return strObjectProperty;
	}
	
	public String BuildObjectWithData(String dataFields, String strObjectProperty,String strCurrentDataSet){
		calledWithObject = true;
		String[] valuesToReplace = null;
		String replacementValue = "";
		//EnvironmentSetup.uniqueValues = "";
		if(dataFields.contains(",")){
			valuesToReplace = dataFields.split(",");
			try{
				for(int i=0;i<valuesToReplace.length;i++){
					if(strObjectProperty.contains("<" + valuesToReplace[i] +">")){
						replacementValue = getData(valuesToReplace[i], strCurrentDataSet);
						strObjectProperty = strObjectProperty.replace("<" + valuesToReplace[i] +">", replacementValue.trim());
						EnvironmentSetup.uniqueValues =  EnvironmentSetup.uniqueValues + replacementValue + " ";
					}
				}
			}
			catch(Exception ex){
				
			}
		}
		else{ 
			if(strObjectProperty.contains("<"+dataFields+">")){
				replacementValue = getData(dataFields, strCurrentDataSet);
				strObjectProperty = strObjectProperty.replace("<"+dataFields+">", replacementValue.trim());
			}
			
			if(strObjectProperty.contains("<data>")){
				replacementValue = getData(dataFields, strCurrentDataSet);
				strObjectProperty = strObjectProperty.replace("<data>", replacementValue.trim());
			}
			
			if (replacementValue.contains(" >> ")){
				String strTableObject = "";
				strTableObject = "//td[.='<data2>']//preceding-sibling::th/a[.='<data1>']";
				String[] arrDataParser = replacementValue.split(" >> ");
				String strDataToClick = arrDataParser[0].trim();
				String strIdentifier = arrDataParser[1].trim();
				strTableObject =  strTableObject.replace("<data1>", strDataToClick);
				strObjectProperty = "//*[contains(@class,'lookup') or contains(@id,'body') or contains(@id,'Body')]//table/tbody";
				strTableObject =  strObjectProperty + strTableObject.replace("<data2>", strIdentifier);
				strObjectProperty= strTableObject;
				EnvironmentSetup.logger.info(strTableObject);
			}
			
			if(strObjectProperty.contains("<data>")){
				replacementValue = getData(dataFields, strCurrentDataSet);
				strObjectProperty = strObjectProperty.replace("<data>", replacementValue.trim());
				EnvironmentSetup.uniqueValues =replacementValue;
			}
			else if (strObjectProperty.contains("<unique>")){
				replacementValue = getData(dataFields, strCurrentDataSet);
				strObjectProperty = strObjectProperty.replace("<unique>", replacementValue.trim());
				EnvironmentSetup.uniqueValues =replacementValue;
			}
		}
		
		/*if(EnvironmentSetup.intGlobalLineItemCount ==0){
			EnvironmentSetup.intGlobalLineItemCount = 1;
		}*/
		
		if(strObjectProperty.contains("<count>")){
			strObjectProperty = strObjectProperty.replace("<count>", Integer.toString(EnvironmentSetup.intGlobalLineItemCount));
		}
		
		System.out.println("Final Object: " + strObjectProperty);
		EnvironmentSetup.logger.info("Final Object: " + strObjectProperty);
		calledWithObject = false;
		return strObjectProperty;
	}
	
	private String getDataFromResultSet(String strDataFieldName) throws Exception{
		String strInputData = "";
		if (!(EnvironmentSetup.UseLineItem)){
		EnvironmentSetup.testDataResultSet.beforeFirst();
		//TODO For making multiple items from LineItems to be picker i
		System.out.println("Called with object value is :: " + calledWithObject);
//							if (EnvironmentSetup.testDataResultSet.next()){
		while(EnvironmentSetup.testDataResultSet.next()){												
			strInputData = EnvironmentSetup.testDataResultSet.getString(strDataFieldName);	
			System.out.println("Value from result set is :: " + strInputData); 
			break;
		}							
	}else {
		EnvironmentSetup.lineItemResultSet.beforeFirst();
			//TODO For making multiple items from LineItems to be picker i
		System.out.println("Called with object value is :: " + calledWithObject);
//							if (EnvironmentSetup.testDataResultSet.next()){
		while(EnvironmentSetup.lineItemResultSet.next()){											
//									while(EnvironmentSetup.testDataResultSet.next()){
			System.out.println("Value from Data sheet is :: " + strInputData + " for the line Item Count :: " + EnvironmentSetup.lineItemCount+"EnvironmentSetup.lineItemResultSet :"+EnvironmentSetup.lineItemResultSet );										
			if ((EnvironmentSetup.lineItemResultSet.getRow()-1)==EnvironmentSetup.lineItemCount){
				strInputData = EnvironmentSetup.lineItemResultSet.getString(strDataFieldName);
				System.out.println("Value from Data sheet is :: " + strInputData + " for the line Item Count :: " + EnvironmentSetup.lineItemCount);
				break;
			}
		} 		
	}
	return strInputData;
	}
}