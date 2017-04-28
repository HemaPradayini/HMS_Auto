package CustomFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import genericFunctions.DataParser;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;
@SuppressWarnings("unused")
public class PMILibrary {
	
	public static String ParentPageObject = "";
	
	public static String ChargeTypeColName = "Charge Type";
	public static String PriceMethodColName = "Price Method";
	public static String PriceUOMColName = "Price UOM";
	public static String ListPriceColName = "List Price";
	//Item Id	Charge TypeSorted Ascending	Description	Price Method	Price Uom	Active	List Price	Discount Threshold	Effective Date	Expiration Date
	
	
	public static int ChargeTypeColNo = 0;
	public static int PriceMethodColNo = 0;
	public static int PriceUOMColNo = 0;
	public static int ListPriceColNo = 0;
	
	public static String ChargeType = "";
	public static String PriceMethod = "";
	public static String PriceUOM = "";
	public static String ListPrice = "";
	
	
	public static DataParser dataParser = new DataParser();
	public static VerificationFunctions performVerification = new VerificationFunctions();
	public static WebElement priceListTable = null;
	public static KeywordSelectionLibrary executeKeyword = new KeywordSelectionLibrary();
	
	
	public static void fetchPMIData(String strDataSet){
		
		initializeColumnNos();
		FindRequiredData();
		
		//Data Sheet and Product Master sheet
		//Select Distinct
		
		
		//Login to SF
		//Login to Apttus
		//For each Product
			//Search For Product
			//Initialize Columns
			
			//Fetch & Store Data
		
		//Update Data Sheet Based on Values in the Product Master
		
	}
	
	//Initialize Column Numbers
	public static void initializeColumnNos(){
		String GoToPriceListObject = "";
		String PriceListItemsRelatedListObject = "";
		String PriceListItemsPageObject = "";
		String PriceListTableObject = "";
		String PriceListHeaderRowObject = "";
		String strCurrentColNameObject = "";
		
		String ProductDetailsPageObject = "";
		
		
		ProductDetailsPageObject = dataParser.getObjectProperty("ProductDetailsPage");
		GoToPriceListObject = dataParser.getObjectProperty("GoToPriceList");
		PriceListItemsRelatedListObject = dataParser.getObjectProperty("PriceListItemsRelatedList");
		PriceListItemsPageObject = dataParser.getObjectProperty("PriceListItemsPage");
		PriceListTableObject = dataParser.getObjectProperty("PriceListTable");
		PriceListHeaderRowObject = dataParser.getObjectProperty("PriceListHeaderRow");
		List<WebElement> priceListTableHeader = null;
		
		//Check if GoToPriceList link is present - if present click, wait for Page and then initialize Table Object else use relatedListObject.
		try{
			KeywordExecutionLibrary.driver.findElement(By.xpath(GoToPriceListObject));
			KeywordExecutionLibrary.driver.findElement(By.xpath(GoToPriceListObject)).click();
			
			//TODO add DataSet
			performVerification.verify("Appears", "PriceListItemsPage", "", "");
			
			priceListTable = KeywordExecutionLibrary.driver.findElement(By.xpath(PriceListTableObject));
			ParentPageObject = PriceListItemsPageObject;
			
		}
		catch(Exception ex){
			priceListTable = KeywordExecutionLibrary.driver.findElement(By.xpath(PriceListItemsRelatedListObject));
			ParentPageObject = ProductDetailsPageObject;
		}
		
		strCurrentColNameObject = PriceListHeaderRowObject.replace("<colName>", ChargeTypeColName);
		priceListTableHeader = priceListTable.findElements(By.xpath(strCurrentColNameObject));
		ChargeTypeColNo = priceListTableHeader.size() + 1;
		
		strCurrentColNameObject = PriceListHeaderRowObject.replace("<colName>", PriceMethodColName);
		priceListTableHeader = priceListTable.findElements(By.xpath(strCurrentColNameObject));
		PriceMethodColNo = priceListTableHeader.size() + 1;
		
		
		strCurrentColNameObject = PriceListHeaderRowObject.replace("<colName>", PriceUOMColName);
		priceListTableHeader = priceListTable.findElements(By.xpath(strCurrentColNameObject));
		PriceUOMColNo = priceListTableHeader.size() + 1;
		
		
		strCurrentColNameObject = PriceListHeaderRowObject.replace("<colName>", ListPriceColName);
		priceListTableHeader = priceListTable.findElements(By.xpath(strCurrentColNameObject));
		ListPriceColNo = priceListTableHeader.size() + 1;
		
	}
	
	public static void FindRequiredData(){
		//QuoteBillingEffectiveDate
		String PriceListRequiredRowsObject = "";
		String strCurrentObject = "";
		String PriceListItemDetailsObject = "";
		String ChargeType = "";
		String PriceMethod = "";
		String PriceUom = "";
		String ChargeTypeCriteriaObject = "";

		
		//Get Objects
		PriceListRequiredRowsObject = dataParser.getObjectProperty("PriceListRequiredRows");
		PriceListItemDetailsObject = dataParser.getObjectProperty("PriceListRequiredRows");
		ChargeTypeCriteriaObject = dataParser.getObjectProperty("ChargeTypeCriteria");
		
		//Get Data from Data Sheet
		ChargeType = "";
		PriceMethod = "";
		PriceUom = "";
		
		String strCurrentObjectProp = "";
		String strChargeTypeCriteriaActual = "";
		String strChargeTypeCriteria = "";
		
		//Fetch Data From the Product Master Sheet
		
		
		//Replace Column Number params
		strCurrentObject = PriceListRequiredRowsObject.replace("<ChargeTypeCol>", Integer.toString(ChargeTypeColNo));
		strCurrentObject = strCurrentObject.replace("<PriceMethodCol>", Integer.toString(PriceMethodColNo));
		strCurrentObject = strCurrentObject.replace("<PriceUomCol>", Integer.toString(PriceUOMColNo));
		
		//Fetch Data
		strChargeTypeCriteria = dataParser.getData("AddHTTPS", EnvironmentSetup.strCurrentDataset);
		ChargeType = dataParser.getData("ChargeType", EnvironmentSetup.strCurrentDataset);
		PriceMethod = dataParser.getData("PriceMethod", EnvironmentSetup.strCurrentDataset);
		PriceUom = dataParser.getData("PriceUOM", EnvironmentSetup.strCurrentDataset);
		
		strChargeTypeCriteria = "Add HTTPS? = " + strChargeTypeCriteria;

		//Replace Column Data
		strCurrentObject = strCurrentObject.replace("<ChargeType>", ChargeType);
		strCurrentObject = strCurrentObject.replace("<PriceMethod>", PriceMethod);
		strCurrentObject = strCurrentObject.replace("<PriceUom>", PriceUom);
		
		
		//Fetch Data From Price List Item - click, verify, if correct capture data else go back and click on next object
		List<WebElement> RequiredPriceListRows = KeywordExecutionLibrary.driver.findElements(By.xpath(strCurrentObject));
		
		int noOfRequiredRows = RequiredPriceListRows.size();
		for(int currentRow = 1; currentRow<=noOfRequiredRows; currentRow++){
			try{
				strCurrentObjectProp = "(" + strCurrentObject + "/*[count(//table/tbody/tr[contains(@class,'headerRow')]/*[*[.='Item Id']]/preceding-sibling::*)+1]//a)"
						+ "[" + Integer.toString(currentRow) + "]";
				KeywordExecutionLibrary.driver.findElement(By.xpath(strCurrentObjectProp)).click();
				performVerification.verify("Appears", PriceListItemDetailsObject, "", "");
				
				//Fetch Charge type Criteria Text
				strChargeTypeCriteriaActual = KeywordExecutionLibrary.driver.findElement(By.xpath(ChargeTypeCriteriaObject)).getText();
				
				//If Charge Type Criteria Matches required criteria, capture & Store List Price
				if (strChargeTypeCriteriaActual.contains(strChargeTypeCriteria)){
					//Get List Price & Discount Threshold
					executeKeyword.selectandExecuteKeyword("Store ProductDiscountThreshold", "Done", EnvironmentSetup.strCurrentDataset);
					
				}
				else{
					KeywordExecutionLibrary.driver.navigate().back();
					performVerification.verify("Appears", ParentPageObject, "", "");
				}
				
				
				
			}
			catch(Exception ex){
				
			}
		}
		
	}
	
	private static void fetchProductMasterData(String ProductName, String BillingModel){
		
		Connection testDataConn = null;
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		catch (Exception ex){
			
		}
		
		//Test Data Connection
		try{
			testDataConn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver "
            		+ "(*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
			
		}
		catch (Exception ex){
			System.out.println("Test Suite Connection Exception: " + ex.toString());
		}
		
		String strQuery;
  	   	Statement QueryStatement = null;
		  	   	
  	   	strQuery = "Select * From [ProductMaster$] Where ProductName='" + ProductName + "' and BillingModel = '" + BillingModel +"'";
	}
}
