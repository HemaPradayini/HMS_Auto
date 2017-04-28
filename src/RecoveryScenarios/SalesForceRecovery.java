package RecoveryScenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import genericFunctions.DataParser;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.ReusableExecutionDriver;
import seleniumWebUIFunctions.VerificationFunctions;


public class SalesForceRecovery {
	
	public static void recoverServiceCloud(){
		try{
			KeywordExecutionLibrary.driver.switchTo().window(EnvironmentSetup.strMainWindowHandle);
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
		
		try{
			KeywordExecutionLibrary.driver.switchTo().defaultContent();
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
		
		//TODO Test
		KeywordExecutionLibrary key = new KeywordExecutionLibrary();
		key.closeAllServiceCloudTabs("//ul[contains(@class,'x-tab-strip x-tab-strip-top')]/li[contains(@class,'x-tab-strip-closable') and contains(@class,'x-tab-strip-active')]/a[@class='x-tab-strip-close']");
		
		ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, EnvironmentSetup.strReusableName ,"", "", EnvironmentSetup.strActualResult,EnvironmentSetup.strTestStepstatus);
		
	}

	public static void timeOutRecovery(){
		
		try{
			KeywordExecutionLibrary.driver.quit();
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
		
		ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, EnvironmentSetup.strReusableName ,"Time Out Exception", "Time Out Exception", EnvironmentSetup.strTimeOutException,EnvironmentSetup.strTestStepstatus);
		
		try{
			DataParser dataParser = new DataParser();
			String[] arrStepDetails = dataParser.parseActionData(EnvironmentSetup.strLoginReusable);
			String strKeyword = arrStepDetails[0];
			String strObjectName = arrStepDetails[1];
			String strDatatoUse = arrStepDetails[2];
			String strVerificationType = arrStepDetails[3];
			String strReusableSheet = strObjectName;
			String strReusableParam = strDatatoUse;
			ReusableExecutionDriver reusableExec = new ReusableExecutionDriver();
			System.out.println(EnvironmentSetup.strLoginReusable);
			reusableExec.ExecuteReusable(strReusableSheet, strReusableParam, EnvironmentSetup.strLoginDataSet);
			//EnvironmentSetup.strTestStepstatus = "Fail";
			
		}
		catch(Exception ex){
			System.out.println("Recovery - Login " + ex.toString());
		}
		
	}
	
	
	public static void recoverApttus(){
		
		try{
			KeywordExecutionLibrary.driver.switchTo().window(null);
		}
		catch(Exception ex){
			
		}
		
		try{
			KeywordExecutionLibrary.driver.findElement(By.xpath("//a[contains(@class,'close')]/span[contains(@class,'close') or contains(@class,'Close')]"));
			KeywordExecutionLibrary.driver.findElement(By.xpath("//a[contains(@class,'close')]/span[contains(@class,'close') or contains(@class,'Close')]")).click();
//			KeywordExecutionLibrary.driver.
			//VerificationFunctions.Driver.waitForPageToLoad("1000");
		}
		catch(Exception ex){
			
		}
		
		try{
			KeywordExecutionLibrary.driver.findElement(By.xpath("//span[.='Proposal:']//following-sibling::span[1]//a[1] | //span[@class='apt-cart-head-label']//following-sibling::span//a[1]"));
			KeywordExecutionLibrary.driver.findElement(By.xpath("//span[.='Proposal:']//following-sibling::span[1]//a[1] | //span[@class='apt-cart-head-label']//following-sibling::span//a[1]")).click();
			String pageLoad = "Nothing";
        	int counter = 0;
        	
    		while(!(pageLoad.contains("complete") || pageLoad.contains("interactive")) || counter>=100){
    			counter = counter+1;
    			try{
        			pageLoad = (String) ((JavascriptExecutor)KeywordExecutionLibrary.driver).executeScript("return document.readyState");
                	System.out.println(pageLoad);
    			}
            	catch(Exception ex){
            		System.out.println("Waiting for PageLoad" + ex.toString());
            	}
    		}
			
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
		
		
	}
	
	public static void recoverFF(){
		try{
			KeywordExecutionLibrary.driver.findElement(By.xpath("//a[@title='Home Tab']")).click();
			//KeywordExecutionLibrary.driver.findElement(By.xpath("//span[@class='apt-cart-head-label']//following-sibling::span//a[1]")).click();
			String pageLoad = "Nothing";
        	int counter = 0;
        	
    		while(!(pageLoad.contains("complete") || pageLoad.contains("interactive")) || counter>=100){
    			counter = counter+1;
    			try{
        			pageLoad = (String) ((JavascriptExecutor)KeywordExecutionLibrary.driver).executeScript("return document.readyState");
                	System.out.println(pageLoad);
    			}
            	catch(Exception ex){
            		System.out.println("Waiting for PageLoad" + ex.toString());
            	}
    		}
			
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
	}
}