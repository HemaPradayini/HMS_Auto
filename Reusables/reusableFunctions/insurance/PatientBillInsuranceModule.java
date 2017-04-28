package reusableFunctions.insurance;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PatientBillInsuranceModule {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;


		public PatientBillInsuranceModule(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
			this.executeStep = execStep;
			this.verifications = verifications;
		}
	
		
			  public int getNoOfClaims(){
					System.out.println("Inside PatientBillScreen getNoOfClaims");
					
					int rowCount=this.executeStep.getDriver().findElements(By.xpath("//table[@id='resultTable']/tbody/tr")).size();
					System.out.println("The no of Rows in the Bills Table is :: " + rowCount);
					return rowCount;
				} 
			    
			  
			 public void verifyClaimIdPresent(){
				String elementText;
				String xPathPre="//table[@id='resultTable']//tr[";
				String xPathPost = "]/td[8]";
				String elementXPath = "";	
				
				int rowCount = getNoOfClaims();
				for(int i=1; i<=rowCount-1; i++){
					elementXPath = xPathPre + (i+1) +xPathPost;
					EnvironmentSetup.intGlobalLineItemCount = i+1;
					
				        System.out.println("The XPath :: " + elementXPath);
					WebElement we = this.executeStep.getDriver().findElement(By.xpath(elementXPath));
					elementText= we.getText();
					
					System.out.println("The Type :: " + elementText);
					

					if(elementText.startsWith("CLD")){
												System.out.println("Claim ID generated");
								
				       	}
					
		                       else{
		                           System.out.println("Claim ID is not generated");
		                       }

						
					EnvironmentSetup.intGlobalLineItemCount = 0;
				}
				
			 }
			  
}
		
		
		    	   
		
		 

