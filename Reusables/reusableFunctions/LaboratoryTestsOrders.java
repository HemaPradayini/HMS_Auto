package reusableFunctions;

import genericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class LaboratoryTestsOrders {
	
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
public LaboratoryTestsOrders(){
	
	
		
	}
	
	/**
	 * Added by Hema
	 * 
	 */
public LaboratoryTestsOrders(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}


public void searchLabOrderList(){
	
	MRNoSearch search = new MRNoSearch(executeStep, verifications);
	search.searchMRNo("LaboratoryTestOrderPageMRNoField","LaboratoryTestOrderPageMRNoList","LaboratoryTestOrderPageMRNoFind","LaboratoryOrderTestsPage");
	
}

public void addItemsToLabTest(){
	executeStep.performAction(SeleniumActions.Click, "", "LaboratoryTestOrderAddItemButton");
	verifications.verify(SeleniumVerifications.Appears, "", "LaboratoryTestOrderAddItemScreen", false);
	EnvironmentSetup.LineItemIdForExec = "LabOrderAddItem";
	Order LabTestOrder = new Order(executeStep, verifications);
	LabTestOrder.addOrderItem("LabOrderAddItem", "LaboratoryOrderTestsPage");
	executeStep.performAction(SeleniumActions.Click, "","LaboratoryTestOrderSaveButton");
	verifications.verify(SeleniumVerifications.Appears, "","LaboratoryOrderTestsPage",false);
	
}

}
