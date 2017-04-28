package RecoveryScenarios;

import org.openqa.selenium.By;

import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import seleniumWebUIFunctions.KeywordExecutionLibrary;

public class SiebelRecovery {
	
	@SuppressWarnings("deprecation")
	public static void saveErrors(){
		//Undo Record
		//Report Step as Fail 
		//Set Continue based on Proceed
		
		try{
			KeywordExecutionLibrary.driver.findElement(By.xpath("//button[(@title='Close' or @title='close') and contains(@class,'dialog') and @aria-disabled='false']"));
/*			KeywordExecutionLibrary.Driver.highlight("xpath=//button[(@title='Close' or @title='close') and contains(@class,'dialog') and @aria-disabled='false']");
			KeywordExecutionLibrary.Driver.mouseOver("xpath=//button[(@title='Close' or @title='close') and contains(@class,'dialog') and @aria-disabled='false']");*/
			KeywordExecutionLibrary.driver.findElement(By.xpath("//button[(@title='Close' or @title='close') and contains(@class,'dialog') and @aria-disabled='false']")).click();
			Thread.sleep(500);
		}
		catch(Exception ex){
			EnvironmentSetup.logger.warning(ex.toString());
			System.out.println(ex.toString());
		}
		
		try{
			KeywordExecutionLibrary.driver.switchTo().alert().accept();
			Thread.sleep(500);
		}
		catch(Exception ex){
			EnvironmentSetup.logger.warning(ex.toString());
		}
		
		try{
			KeywordExecutionLibrary.driver.findElement(By.xpath("//button[contains(@title,'Cancel') and contains(@title,':')]")).click();
			Thread.sleep(500);
		}
		catch(Exception ex){
			EnvironmentSetup.logger.warning(ex.toString());
			System.out.println(ex.toString());
		}
		
		ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, EnvironmentSetup.strReusableName ,"", "", EnvironmentSetup.strActualResult,EnvironmentSetup.strTestStepstatus);
		
		
	}
	
	public static void navigatonErrors(){
		
	}
}