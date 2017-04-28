package CustomFunctions;


public class CalculationLibrary {

	//Calculating Net Price(Billing Model Based Calc)
	
	
	//Get DataSet
	//Fetch Term + Term Category from Main Data Sheet
	//Fetch All Line Items from LineItem Sheet != SubTotals and !=GrandTotal
	//For each LineItem
		//Fetch PlatFormFee/BaseFee/UnitPrice
			//Verify that atleast one of the prices are listed - If Not mark step as fail and go to the next data set
		//Fetch	Discount Type and Discounted Amount
		//Calculate NetPrice based on formulae
			//Factor in Billing Models
	
	//If LineID = Ramp
		//
	
	
	//Calculating SubTotals
		//All Option Lines should be rolled up to the main Line
		
	
	//Calculating Grand Total
		//SumTotal of all SubTotal Rows
	
	//Grand Total to be in 2 places
		
}
