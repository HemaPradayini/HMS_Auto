/**
 * 
 */
package seleniumWebUIFunctions;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import genericFunctions.EnvironmentSetup;

/**
 * @author Sai
 *
 */
public class CommonFunctions {
	
	WebDriver driver;
	String AutomationID; 
	String DataSet; 
	String LineItemID;
	
	public CommonFunctions(WebDriver driver, String AutomationID, String DataSet, String LineItemID){
		this.driver = driver;
		this.AutomationID = AutomationID;
		this.DataSet = DataSet;
		this.LineItemID = LineItemID;
	}
	
	public void captureScreenshot(){
		
		driver = new Augmenter().augment(driver);
		EnvironmentSetup.logger.info(EnvironmentSetup.strScreenshotsPath);
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			EnvironmentSetup.strCurrentScreenshotLocation = EnvironmentSetup.strScreenshotsPath +"Step_" 
					+ Integer.toString(EnvironmentSetup.intSlNo+1)+ "_" + EnvironmentSetup.strCurrentDataset +".png";
			
			FileUtils.copyFile(screenshot, new File(EnvironmentSetup.strCurrentScreenshotLocation));
			
			//return "Screenshot File: " + EnvironmentSetup.strCurrentScreenshotLocation + ":::Done";
		} catch (IOException e) {
			EnvironmentSetup.logger.info(e.toString());
			e.printStackTrace();
			//return "Failed to capture Screenshot " + e.toString() + ":::Warning";
		}
		
	}
	
	public void syncWebPage(){
		
		int timeoutInSeconds = 60;
	    
    	for (int i = 0; i< timeoutInSeconds; i++) {
    		try {
		        if (driver instanceof JavascriptExecutor) {
		            JavascriptExecutor jsDriver = (JavascriptExecutor)driver;
		                Object numberOfAjaxConnections = jsDriver.executeScript("return window.openHTTPs");
		                // return should be a number
		                if (numberOfAjaxConnections instanceof Long) {
		                    Long n = (Long)numberOfAjaxConnections;
		                    EnvironmentSetup.logger.info("Number of active calls: " + n);
		                    if (n.longValue() == 0L){
		    	            	break;
		                    }
		                    else{
		                    	
		                    }
		                    	
		                } 
		                else{
		                    // If it's not a number, the page might have been freshly loaded indicating the monkey
		                    // patch is replaced or we haven't yet done the patch.
		                	EnvironmentSetup.logger.info("Monkey Patch " + i);
		                	monkeyPatchXMLHttpRequest();
		                }
		                Thread.sleep(1000);
		            }
	//	        }
		        else {
		        	EnvironmentSetup.logger.info("Web driver: " + driver + " cannot execute javascript");
		        }
    		}
    		catch (InterruptedException e) {
    	    	EnvironmentSetup.logger.info(e.toString());
    	    }
    		catch(UnhandledAlertException ex){
        		//handleAlert();
        	}
    	    catch(Exception e){
    	    	EnvironmentSetup.logger.info(e.toString());
    	    }
	    }
		
	}
    	
	public void monkeyPatchXMLHttpRequest() {
	    try {
	        if (driver instanceof JavascriptExecutor) {
	            JavascriptExecutor jsDriver = (JavascriptExecutor)driver;
	            Object numberOfAjaxConnections = jsDriver.executeScript("return window.openHTTPs");
	            if (numberOfAjaxConnections instanceof Long) {
	            	Long n = (Long)numberOfAjaxConnections;
	            	EnvironmentSetup.logger.info("Ajax inside " + n);
	                return;
	            }
	            EnvironmentSetup.logger.info("Is It even coming here?");
	            String script = "  (function() {" +
	                "var oldOpen = XMLHttpRequest.prototype.open;" +
	                "window.openHTTPs = 0;" +
	                "XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {" +
	                "window.openHTTPs++;" +
	                "this.addEventListener('readystatechange', function() {" +
	                "if(this.readyState == 4) {" +
	                "window.openHTTPs--;" +
	                "}" +
	                "}, false);" +
	                "oldOpen.call(this, method, url, async, user, pass);" +
	                "}" +
	                "})();";
	            jsDriver.executeScript(script);
	        }
	        else {
	        	EnvironmentSetup.logger.info("Web driver: " + driver + " cannot execute javascript");
	        }
	    }
	    catch(UnhandledAlertException ex){
    		//handleAlert();
    	}
	    catch (Exception e) {
	    	EnvironmentSetup.logger.info(e.toString());
	    }
	}
	
	public void acceptAlert(){
		String alertText = "";
		
		try{
			alertText = driver.switchTo().alert().getText();
			//ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, EnvironmentSetup.strReusableName ,"Unexpected Dialog", "Unexpected Dialog", alertText,"Warning");
		}
		catch(Exception ex){
			System.out.println("Error Handler Alert");
		}
		
		try{
			driver.switchTo().alert().accept();
			//ReportingFunctionsforExcelFw.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, EnvironmentSetup.strReusableName ,"Unexpected Dialog", "Unexpected Dialog", alertText,"Warning");
		}
		catch(Exception ex){
			
		}
	}
	
	public void dismissAlert(){
		String alertText = "";
		
		try{
			alertText = driver.switchTo().alert().getText();
			//ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, EnvironmentSetup.strReusableName ,"Unexpected Dialog", "Unexpected Dialog", alertText,"Warning");
		}
		catch(Exception ex){
			System.out.println("Error Handler Alert");
		}
		
		try{
			driver.switchTo().alert().dismiss();
			//ReportingFunctionsforExcelFw.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, EnvironmentSetup.strReusableName ,"Unexpected Dialog", "Unexpected Dialog", alertText,"Warning");
		}
		catch(Exception ex){
			
		}
	}
	
}
