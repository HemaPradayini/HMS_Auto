package seleniumWebUIFunctions;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import RecoveryScenarios.SiebelRecovery;
import genericFunctions.*;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import seleniumWebUIFunctions.VerificationFunctions;

/*//import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
//import com.jacob.com.LibraryLoader;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;
import com.thoughtworks.selenium.webdriven.commands.WaitForCondition;
import com.thoughtworks.selenium.webdriven.commands.WindowFocus;*/

@SuppressWarnings("unused")
public class KeywordExecutionLibrary{
	
	WebElement targetElement = null;
	
	By objectIdentifier = null;
	
	DataParser data_Parser = new DataParser();
	public static WebDriver driver;
	//public static WebDriverBackedSelenium Driver;
	VerificationFunctions performVerification;
	//static ReportingFunctions reporter = new ReportingFunctions();
	
	public  KeywordExecutionLibrary(WebDriver driver){
		this.driver = driver;
	}
	
	public KeywordExecutionLibrary(){
		
	}
	
	public WebDriver LaunchApp(String strBrowser, String URL){
		driver = null;
		try{
			if(strBrowser.toUpperCase().contains("CHROME")){
				//File driverPath = new File("./Drivers/chromedriver.exe");
				//String path = driverPath.getAbsolutePath();
				
				//added by Hema
				String workingDir = System.getProperty("user.dir");
                String filepath = workingDir + "\\Drivers\\chromedriver.exe";
                System.out.println("FILEPATH :"+filepath);
                File driverPath = new File(filepath);
                String path = driverPath.getAbsolutePath();
                
				System.out.println(path);
				System.setProperty("webdriver.chrome.driver", path);
				//Added by Hema
				ChromeOptions options = new ChromeOptions();
				 Map<String, Object> prefs = new HashMap<String, Object>();
				//Last parameter 0 changed by Abhishek
                 prefs.put("profile.default_content_settings.popups", 0);
                 prefs.put("download.default_directory", workingDir);
                 prefs.put("safebrowsing.enabled", "true"); 
                 options.setExperimentalOption("prefs", prefs);
                 //for making the 57.x version of chrome working! to remove the user / save password prompt - start
                 options.addArguments("--start-maximized");// starting chrome maximized
                 options.addArguments("--disable-web-security");
                 options.addArguments("--no-proxy-server");
                // Map<String, Object> prefs = new HashMap<String, Object>();
                 prefs.put("credentials_enable_service", false);
                 prefs.put("profile.password_manager_enabled", false);
                 options.setExperimentalOption("prefs", prefs);
                 //for making the 57.x version of chrome working! to remove the user / save password prompt - end
                 
				 
				DesiredCapabilities capability = new DesiredCapabilities();
				if (EnvironmentSetup.strModuleName!="ProjectPhoenix"){
					capability.setCapability("unexpectedAlertBehaviour", "ignore");
					driver = new ChromeDriver(options);
				}
				else{
					driver = new ChromeDriver();
				}
			}
			else if(strBrowser.toUpperCase().contains("FOX")){
				File driverPath = new File("./Drivers/geckodriver.exe");
				String path = driverPath.getAbsolutePath();
				System.out.println(path);
				System.setProperty("webdriver.gecko.driver", path);
//				DesiredCapabilities capability = DesiredCapabilities.firefox();
				//Set Certificate Behavior
				//ProfilesIni profiles = new ProfilesIni();
				//FirefoxProfile ffProfile = profiles.getProfile("Practo");
				//ffProfile.setPreference("reader.parse-on-load.enabled",false);
				//ffProfile.setAcceptUntrustedCertificates(true);
				//ffProfile.setAssumeUntrustedCertificateIssuer(false);
//				capability.setCapability(FirefoxDriver.PROFILE,ffProfile);
				
				//Added by Tejaswini
				
				FirefoxProfile ffProfile= new FirefoxProfile();
				DesiredCapabilities capability = DesiredCapabilities.firefox();
				//ffProfile.setPreference("browser.startup.homepage","http://www.google.com");
				ffProfile.setPreference("reader.parse-on-load.enabled",false);
				ffProfile.setAcceptUntrustedCertificates(true);
				ffProfile.setAssumeUntrustedCertificateIssuer(false);
				capability.setCapability(FirefoxDriver.PROFILE,ffProfile);
				
				if (EnvironmentSetup.strModuleName!="ProjectPhoenix"){
					//capability.setCapability("unexpectedAlertBehaviour", "ignore");
//					driver = new FirefoxDriver(capability);
					driver = new FirefoxDriver(ffProfile);
				}
				else{
					driver = new FirefoxDriver();
				}
			}
			else if(strBrowser.toUpperCase().contains("SAFARI")){
				DesiredCapabilities capability = DesiredCapabilities.safari();
				if (EnvironmentSetup.strModuleName!="ProjectPhoenix"){
					capability.setCapability("unexpectedAlertBehaviour", "ignore");
					driver = new SafariDriver(capability);
				}
				else{
					driver = new SafariDriver();
				}
			}
			else{
				File driverPath = new File("./Drivers/phantomjs.exe");
				String path = driverPath.getAbsolutePath();
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setJavascriptEnabled(true); // not really needed: JS enabled by default
				caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, path);
				driver = new PhantomJSDriver(caps);
			}
			
			driver.navigate().to(URL);
			/*Driver = new WebDriverBackedSelenium(driver, URL);
			Driver.open("");*/
			
		}
		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.severe("Exception in Launch " + ex.toString());
			EnvironmentSetup.strGlobalException = ex.toString();
		}
		try{
			driver.manage().window().maximize();	
			//Driver.waitForPageToLoad("10000");
			VerificationFunctions.waitForNoLoadElements();
		}
		catch (UnhandledAlertException ex){
			handleAlert();
		}
		catch(Exception ex){
			
		}
		
		return driver;
	}
	
	public void click(String strObjectProperty){
        boolean present;
        
        EnvironmentSetup.logger.info("Object to Click: " + strObjectProperty);
        System.out.println("I am here 1");
        //Extremely important - especially if a click is going to open another window (lookup)
        try{
        	Set<String> strWindowHandles = driver.getWindowHandles();
            EnvironmentSetup.windowsOpen = strWindowHandles.size();
            System.out.println("I am here 2");
        }
        catch(Exception ex){
        	System.out.println("I am here 3");
        	ex.printStackTrace();
        }
        //Tejaswini - <Data> was not getting replaced in XPath - Hence the fix
        if (EnvironmentSetup.replaceData){
        	strObjectProperty = strObjectProperty.replace("<Data>", EnvironmentSetup.dataToBeReplaced.toUpperCase());
        	System.out.println("New Object - replaced string is :: " + strObjectProperty);
        }//Tejaswini
        
        //Meant to click on the Arrow next to the Home Button in Service cloud - The object uses a ::after property which is not supported by Selenium 2.0
        if(strObjectProperty.contains("x-btn-split")){
        	try{
        		Actions action = new Actions(driver);
        		action.moveToElement(driver.findElement(By.xpath(strObjectProperty)), 245, 15).click().build().perform();
        		System.out.println("I am here 4");
        	}
        	catch(Exception ex){
        		EnvironmentSetup.logger.info("Click at Co-ordinate: " + ex.toString());
        		EnvironmentSetup.strGlobalException = ex.toString();
        		System.out.println("I am here 5");
        		ex.printStackTrace();
        	}
        	
        }
        else{
        	System.out.println("I am here 6");
	        if(EnvironmentSetup.strApplicationName.equalsIgnoreCase("Siebel") && EnvironmentSetup.strCurrentObjectName.contains("Field")){
	        	/*This part is specific to Siebel List Applet Objects - Normally a click is performed in the middle of the Object, some cells contain links that
	        	 * are above the Field and the click command will be sent to the link instead of the box. This would navigate to another screen.
	        	 * In order to avoid this, we use coordinates 1,1 to click on the edge of the box 
	        	*/
	        		try{
		        		Actions action = new Actions(driver);
		        		action.moveToElement(driver.findElement(By.xpath(strObjectProperty)), 1, 1).click().build().perform();
		        		System.out.println("I am here 7");
	        		}
	        		catch(Exception ex){
	        			EnvironmentSetup.logger.warning(EnvironmentSetup.strCurrentObjectName + " Not found: " + ex.toString());
	        			System.out.println("I am here 8");
	        		}
	        	}
        	else{
        		try{
        			/*if(strObjectProperty.contains("Tiers") && strObjectProperty.contains("close")){
        				Driver.highlight("xpath=" + strObjectProperty);
        			}*/
       				//driver.findElement(By.xpath(strObjectProperty)).click();
        			Actions actions = new Actions(driver);
        			actions.moveToElement(driver.findElement(By.xpath(strObjectProperty))).click().build().perform();
       				System.out.println("I am here 9");
    	        }
        		catch(UnhandledAlertException ex){
        			System.out.println("*******************CLICK THROWING EXCEPTION***********************");        			
        			EnvironmentSetup.logger.warning("Unexpected Alert Present after Clicking on " + EnvironmentSetup.strCurrentObjectName);
        			handleAlert();
        		}
    	        catch (NoSuchElementException ex){
    	        	System.out.println("*******************CLICK THROWING EXCEPTION***********************");
    	            EnvironmentSetup.logger.warning(EnvironmentSetup.strCurrentObjectName + " Not Found " + objectIdentifier + ex.getMessage());    	                        
    	        }
    	        catch (StaleElementReferenceException ex){
    	        	System.out.println("*******************CLICK THROWING EXCEPTION***********************");
    	        	EnvironmentSetup.logger.warning("Insufficient Sync before Click. Dom freshly loaded.  " + EnvironmentSetup.strCurrentObjectName + " " + objectIdentifier + ex.getMessage());
    	        }
    	        catch(Exception ex){
    	        	EnvironmentSetup.logger.warning("Exception Clicking on " + EnvironmentSetup.strCurrentObjectName + " " + objectIdentifier + ex.getMessage());
    	        	EnvironmentSetup.strGlobalException = ex.toString();
    	        	System.out.println("*******************CLICK THROWING EXCEPTION*********************** " + ex.toString());
    	        }       	
        		System.out.println("I am here 10");
	        }
	        
        }
	}
	
	public void doubleClick (String strObjectProperty){
		   
	       try{
	        	
	           Actions action = new Actions(driver);
	 		   action.moveToElement(driver.findElement(By.xpath(strObjectProperty)));
	 		   action.doubleClick().perform();
	 		   
	        }
	       	catch(UnhandledAlertException ex){
	       		EnvironmentSetup.logger.warning("Unexpected Alert Present after Double Clicking on " + EnvironmentSetup.strCurrentObjectName);
	       		handleAlert();
   			}
	        catch (NoSuchElementException ex){
	            EnvironmentSetup.logger.info(objectIdentifier + " " + ex.toString());
	            EnvironmentSetup.strGlobalException = ex.toString();
	                        
	        }
	        catch (StaleElementReferenceException ex){
	        	EnvironmentSetup.strGlobalException = ex.toString();
	        }
	        catch(Exception ex){
	        	EnvironmentSetup.strGlobalException = ex.toString();
	        }
		}
	
	public void enter(String strDataToEnter, String strObjectProperty){
		
		if(strDataToEnter==null || strDataToEnter.isEmpty() || strDataToEnter.trim()==""){
			strDataToEnter = "";
			EnvironmentSetup.logger.warning("Data to Enter is Null");
		}
		
		if(strObjectProperty.toLowerCase().contains("emailrecipients")){
			try{
				List<WebElement> elements = driver.findElements(By.xpath(strObjectProperty + "//span[@class='recipient']" +
						"//img[@class='deleteRecipient']"));
				for (WebElement elem : elements){
					try{
						if (elem.isEnabled()){ // Added by Tejaswini
							elem.click();
						}// Added by Tejaswini
					}
					catch(UnhandledAlertException ex){
						handleAlert();
						//SiebelRecovery.saveErrors();
		   			}
					catch(Exception ex1){
						EnvironmentSetup.strGlobalException = ex1.toString();
					}
					
				}
				driver.wait(100);
			}
			catch(UnhandledAlertException ex){
				handleAlert();
				//SiebelRecovery.saveErrors();
   			}
			catch (Exception ex){
				EnvironmentSetup.strGlobalException = ex.toString();
			}
			finally{
				strObjectProperty = strObjectProperty + "//textarea";
			}	
		}
		
		
		try{
			try{
				//if (driver.findElement(By.xpath(strObjectProperty)).isEnabled()){ //Added By tejaswini
				if ((driver.findElement(By.xpath(strObjectProperty)).getText()!= null) &&
						driver.findElement(By.xpath(strObjectProperty)).getText() != "")
				{
					System.out.println("Element's Text is :: " + driver.findElement(By.xpath(strObjectProperty)).getText());
					System.out.println("Skipping clearing of Object :: " + strObjectProperty);
					if (!(strObjectProperty.equalsIgnoreCase("//input[@id='medicine']"))){
						driver.findElement(By.xpath(strObjectProperty)).clear();
						driver.findElement(By.xpath(strObjectProperty)).sendKeys(Keys.CONTROL,"a",Keys.BACK_SPACE);

					}
				}
				//}//Added By tejaswini
			}
			catch(UnhandledAlertException ex){
				handleAlert();
   			}
			catch(Exception ex){
				EnvironmentSetup.logger.warning("Clearing field Failed");
				EnvironmentSetup.logger.warning("Exception is :: " + ex.toString());
				//ex.printStackTrace();
				/* Commented by Tejaswini//driver.findElement(By.xpath(strObjectProperty)).sendKeys(Keys.DELETE);
				Actions action = new Actions(driver);
				int lenText = driver.findElement(By.xpath(strObjectProperty)).getText().length();

				for(int i = 0; i < lenText; i++){
				  action.sendKeys(Keys.ARROW_LEFT);
				}
				action.build().perform();

				for(int i = 0; i < lenText; i++){
				  action.sendKeys(Keys.DELETE);
				}
				Thread.sleep(10000);
				driver.wait(100);
				action.build().perform();
				System.out.println(ex.toString());*///Commented by Tejaswini
				EnvironmentSetup.strGlobalException = ex.toString(); 
			}			
			try{
				if(strObjectProperty.contains("phSearchInput") && EnvironmentSetup.strCurrentModuleName.contains("FinancialForce")){
					//if (driver.findElement(By.xpath(strObjectProperty)).isEnabled()){ //Added By tejaswini
						driver.findElement(By.xpath(strObjectProperty)).sendKeys("\"" + strDataToEnter + "\"");
					//}		//Added By tejaswini
				}
				else
					//if (driver.findElement(By.xpath(strObjectProperty)).isEnabled()){ //Added By tejaswini
						driver.findElement(By.xpath(strObjectProperty)).sendKeys(strDataToEnter);
					//} //Added by Tejaswini
			}
			catch(UnhandledAlertException ex){
				handleAlert();
   			}
			catch(Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Inside enter");
				EnvironmentSetup.strGlobalException = ex.toString();
			}
			try{
				if(strObjectProperty.toLowerCase().contains("emailrecipients")){
					//if (driver.findElement(By.xpath(strObjectProperty)).isEnabled()){ //Added By tejaswini
						driver.findElement(By.xpath(strObjectProperty)).sendKeys(Keys.RETURN);
					//} //Added by Tejaswini
				}
			}
			catch(UnhandledAlertException ex){
				handleAlert();
				//SiebelRecovery.saveErrors();
   			}
			catch (Exception ex1){
				EnvironmentSetup.strGlobalException = ex1.toString();
			}
			
		}
		catch (NoSuchElementException ex){
			EnvironmentSetup.logger.info("Enter " + ex.toString());
			EnvironmentSetup.strGlobalException = ex.toString();
		}
		
		catch (StaleElementReferenceException ex){
			EnvironmentSetup.logger.info("Enter " + ex.toString());
			EnvironmentSetup.strGlobalException = ex.toString();
		}
		catch(Exception ex){
			EnvironmentSetup.logger.info(ex.toString());
			EnvironmentSetup.strGlobalException = ex.toString();
		}
	}
	
	public void select(String strDataToSelect, String strObjectProperty){

		if(strObjectProperty.contains("idSelectedDocs") || strObjectProperty.contains("idSearchableDocs") || (EnvironmentSetup.selectByPartMatchInDropDown)){ // Tejaswini
			int index = 0;
			Select picklist = new Select(driver.findElement(By.xpath(strObjectProperty))); 
			List<WebElement> listOptions = picklist.getOptions();
			
			for (WebElement ele : listOptions){  
				index = index++;
					
				System.out.println("Option fetched from the list box:       " +ele.getText());
				  
				if(ele.getText().contains(strDataToSelect)){
					//picklist.selectByIndex(index); -- Commented by Tejaswini - For the partial text selection in drop down box
					ele.click();
					System.out.println("The Data To Select is:    " + "\t" + strDataToSelect +"Data Fetched from the Application is:    " +ele.getText());   
					break; // Added by Tejaswini
				} 
			}       
		}
		else{            
			try{
				//if (!EnvironmentSetup.selectByPartMatchInDropDown){ // Added By Tejaswini For Selection by exact Match - as was existing just added the if clause
					Select picklist = new Select(driver.findElement(By.xpath(strObjectProperty)));
					picklist.selectByVisibleText(strDataToSelect.trim());
				//} else { // For Selection Using Partial Text from drop down -- Added By Tejaswini
				//	Select picklist = new Select(driver.findElement(By.xpath(strObjectProperty)));
				//	picklist.selectByValue(strDataToSelect.trim());
				//}
			}
			catch(UnhandledAlertException ex){
				handleAlert();
				//SiebelRecovery.saveErrors();
   			}
			catch (NoSuchElementException ex){
				EnvironmentSetup.strGlobalException = ex.toString();
			}
			
			catch (StaleElementReferenceException ex){
				EnvironmentSetup.strGlobalException = ex.toString();
			}
			catch(Exception ex){
				EnvironmentSetup.strGlobalException = ex.toString();
			}
		}
		//TODO This step requires sync - Use an Appears Statement in the Excel to achieve sync
		if (strDataToSelect.toUpperCase().contains("USER") || strDataToSelect.toUpperCase().contains("QUEUE")){
			try {
				//Driver.wait(2000);
			}
			catch(UnhandledAlertException ex){
				handleAlert();
   			}
			catch (Exception e) {
				System.out.println(e.toString());
			}
		}

 	}
		
	
	public void choose(String strDataToSelect, String strObjectProperty){
		String[] arrDataToSelect=null;
		Select picklist;
		int index = -1;
		EnvironmentSetup.logger.info("Choose "+strDataToSelect);
		try{
			picklist = new Select(driver.findElement(By.xpath(strObjectProperty)));
			if (strDataToSelect.contains(" >> ")){
				arrDataToSelect = strDataToSelect.split(" >> ");
				System.out.println(arrDataToSelect.length);
				((RemoteWebDriver) driver).getKeyboard().pressKey("Ctrl");
				
				
				if(strDataToSelect.equalsIgnoreCase("All")){
					List<WebElement> listOptions = picklist.getOptions();
					for (WebElement ele : listOptions){  
						index = index+ 1;
						picklist.selectByIndex(index);
					}
				}
				else{
					for(String strData:arrDataToSelect){
						index = index +1;
						picklist.selectByIndex(index);
						List<WebElement> listOptions = picklist.getOptions();
						
						for (WebElement ele : listOptions){  
							index = index+ 1;
							if(ele.getText().contains(strData.trim())){
								picklist.selectByIndex(index);
								System.out.println("The Data To Select is:    " + "\t" + strDataToSelect +"Data Fetched from the Application is:    " +ele.getText());   
							} 
						}
					}
				}
				
				((RemoteWebDriver) driver).getKeyboard().releaseKey("Ctrl");
			}
			else{
				try{
					if(strDataToSelect.equalsIgnoreCase("All")){
						((RemoteWebDriver) driver).getKeyboard().pressKey("Ctrl");
						List<WebElement> listOptions = picklist.getOptions();
						for (WebElement ele : listOptions){  
							index = index+ 1;
							picklist.selectByIndex(index);
						}
						((RemoteWebDriver) driver).getKeyboard().pressKey("Ctrl");
					}
					else{
						picklist = new Select(driver.findElement(By.xpath(strObjectProperty)));
						picklist.selectByVisibleText(strDataToSelect.trim());
					}
				}
				catch(Exception ex){
					try{
						((RemoteWebDriver) driver).getKeyboard().pressKey("Ctrl");
					}
					catch(Exception ex1){
						
					}
				}
			}
			
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info(ex.toString());
			EnvironmentSetup.strGlobalException = ex.toString();
		}
	}
	
	public String store(String strDataSet, String strDataSheetField, String strObjectProperty){
		String strValueToUpdate = "";
		try{
			try{
				//Added by Tejaswini - Temporary fix / Work around for the scenario where the property can contain an input attribute but where we should actually do a getText()
				if (strObjectProperty.contains("initiate_payment")){ //Tejaswini
					strValueToUpdate = driver.findElement(By.xpath(strObjectProperty)).getText(); //Tejaswini
				} else { 	//Tejaswini
					if(strObjectProperty.contains("//select")){
						Select picklist = new Select(driver.findElement(By.xpath(strObjectProperty)));
						strValueToUpdate = picklist.getFirstSelectedOption().getText();
					}
					else if(strObjectProperty.contains("//input")){
						strValueToUpdate = driver.findElement(By.xpath(strObjectProperty)).getAttribute("value");
					}
					else
					{
						strValueToUpdate = driver.findElement(By.xpath(strObjectProperty)).getText();
						if (strDataSheetField.contains("BillNumber") && strValueToUpdate.contains("(")){ // added by Tejaswini
							strValueToUpdate = strValueToUpdate.substring(0, strValueToUpdate.indexOf('(')).trim();// added by Tejaswini
							System.out.println("StrValue to Update is :: " + strValueToUpdate);// added by Tejaswini
						}
					}
					System.out.println("The data to update is :: " + strValueToUpdate);
					
				}
			}
			catch(UnhandledAlertException ex){
   				//
				handleAlert();
   			}
			catch (NoSuchElementException ex){
				EnvironmentSetup.strGlobalException = ex.toString();
			}
			catch (StaleElementReferenceException ex){
				EnvironmentSetup.strGlobalException = ex.toString();
			}
			
			
			if(strValueToUpdate==null){
				strValueToUpdate = "";
			}
			else if(strValueToUpdate.trim().isEmpty()){
				strValueToUpdate = "";
			}
			
			try{
				//Added by Tejaswini
				/*if (EnvironmentSetup.UseLineItem){
					/*if (strDataSheetField.contains("BillNumber") && strValueToUpdate.contains("(")){ // changed by Tejaswini
						strValueToUpdate = strValueToUpdate.substring(0, strValueToUpdate.indexOf('(')).trim();
						System.out.println("StrValue to Update is :: " + strValueToUpdate);
					}/**
					DatabaseFunctions.UpdateLineItems(strDataSheetField, strValueToUpdate.trim(), "DataSet", strDataSet);
				} else {*/			
					if(!EnvironmentSetup.strLineItemDataSet.isEmpty()){
						System.out.println("Updating Line Items");
						EnvironmentSetup.logger.info("Updating Line Items");
						if(EnvironmentSetup.useExcelScripts == false){
							DatabaseFunctions.updateDataLineItemsTable(strDataSheetField, strValueToUpdate);
						}
						else{
							DatabaseFunctions.UpdateLineItemsTable(strDataSheetField, strValueToUpdate);
						}
					}
					else{
						if(EnvironmentSetup.useExcelScripts == false){
							DatabaseFunctions.updateTestDataTable(strDataSheetField, strValueToUpdate, "DataSet", strDataSet);
						}
						else{
							DatabaseFunctions.UpdateTable(strDataSheetField, strValueToUpdate, "DataSet", strDataSet);
						}					
					}
				//}
				
				if(EnvironmentSetup.fetchDataFromXML==true){
					XMLFunctions.setDataInXML(strValueToUpdate,strDataSheetField,EnvironmentSetup.currentChildLineItemNum);
				}
				//Below added to get MRID from the datasheet for a search in a table in Dietry - Alamelu
				if (strDataSheetField.equalsIgnoreCase("MRID")){
					EnvironmentSetup.MRID=strValueToUpdate;
				}
				
			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Store:: " + ex.toString());
				EnvironmentSetup.strGlobalException = ex.toString();
			}
						
		}
		catch (Exception ex){
			EnvironmentSetup.strGlobalException = ex.toString();
		}
		
		return strValueToUpdate;
	}
	
	public void check(String strDataState, String strObjectProperty){
		//Default Value for this will be Y
		if(strDataState.isEmpty()){
			strDataState = "Y";
		}
		
		if(strDataState.equalsIgnoreCase("Y") || strDataState.equalsIgnoreCase("N")){
			try{
				String status = "";
				if(!EnvironmentSetup.strApplicationName.equalsIgnoreCase("Siebel")){
					status = driver.findElement(By.xpath(strObjectProperty)).getAttribute("checked");
					EnvironmentSetup.logger.info("Checked State App Value Before: " + status);
					if (strDataState.contains("Y") && status == null){
						driver.findElement(By.xpath(strObjectProperty)).click();
					}
					else if (strDataState.contains("N") && status.equalsIgnoreCase("true")){
						driver.findElement(By.xpath(strObjectProperty)).click();
					}
				}
				else{
					status = driver.findElement(By.xpath(strObjectProperty)).getAttribute("aria-checked");
					EnvironmentSetup.logger.info("Checked State App Value Before: " + status);
					if (strDataState.contains("Y") && status.equalsIgnoreCase("false")){
						driver.findElement(By.xpath(strObjectProperty)).click();
					}
					else if (strDataState.contains("N") && status.equalsIgnoreCase("true")){
						driver.findElement(By.xpath(strObjectProperty)).click();
					}
				}
			}
			catch(UnhandledAlertException ex){
				handleAlert();
   			}
			catch (NoSuchElementException ex){
				EnvironmentSetup.strGlobalException = ex.toString();
			}
			catch (StaleElementReferenceException ex){
				EnvironmentSetup.strGlobalException = ex.toString();
			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Checked State before: " + ex.toString());
				EnvironmentSetup.strGlobalException = ex.toString();
			}
		}
		else{
			try{
				driver.findElement(By.xpath(strObjectProperty)).click();
			}
			catch(UnhandledAlertException ex){
				handleAlert();
			}
			catch(Exception ex){
				EnvironmentSetup.strGlobalException = ex.toString();
			}
		}
	            
	}
	
	public String closeAllServiceCloudTabs(String strObjectProperty){
		driver.switchTo().defaultContent();
		int tabCount = 1;
		
		try{
			while(tabCount!=0){
				List<WebElement> tabsOpen = driver.findElements(By.xpath(strObjectProperty));
				tabCount = tabsOpen.size();
				if (tabCount!=0){
					System.out.println(tabsOpen.size());
					String prop = "//ul[contains(@class,'x-tab-strip x-tab-strip-top')]/li[contains(@class,'x-tab-strip-closable')]";
					
					List<WebElement> tabs = driver.findElements(By.xpath(prop));
					for(WebElement elem: tabs){
						elem.click();
						try{
							//Driver.waitForPageToLoad("9000");
						}
						catch (Exception ex){
							
						}
						try{
							/*Driver.highlight("xpath="+strObjectProperty);
							Driver.mouseOver("xpath="+strObjectProperty);*/
							//if(elem.isDisplayed()){
							driver.findElement(By.xpath(strObjectProperty)).click();
							
							try{
								driver.switchTo().defaultContent();
								driver.findElement(By.xpath("//div[contains(@class,'x-window') and contains(@class,'x-resizable-pinned') "
										+ "and contains(@style,'visible')]//td[contains(em,'Don') "
										+ "and contains(em,'t Save')]")).click();
								
							}
							catch(Exception ex){
								//EnvironmentSetup.logger.info("Don't Save: " + ex.toString());
							}
							
							try{
								//Driver.waitForPageToLoad("9000");
							}
							catch (Exception ex){
								
							}
							
						}
						catch(Exception ex){
							
						}
					}
				}
			}
			
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
		
		return "Tabs Closed:::Done";
	}
	
	public String closeTab(String strObjectProperty){
		int TabCountBefore = 0;
		int TabCountAfter = -1;
		
		try{
			driver.switchTo().defaultContent();
			TabCountBefore = driver.findElements(By.xpath(strObjectProperty)).size();
			/*Driver.highlight("xpath="+strObjectProperty);
			Driver.mouseOver("xpath="+strObjectProperty);*/
			driver.findElement(By.xpath(strObjectProperty)).click();
		}
		catch(UnhandledAlertException ex){
				//
			handleAlert();
		}
		catch (NoSuchElementException ex){
			EnvironmentSetup.logger.info("Tab Not Found");
			//EnvironmentSetup.strGlobalException = ex.toString();
		}
		catch (StaleElementReferenceException ex){
			//EnvironmentSetup.strGlobalException = ex.toString();
		}
		catch(Exception ex){
			EnvironmentSetup.logger.info(ex.toString());
			//EnvironmentSetup.strGlobalException = ex.toString();
		}
		
		
		try{
			driver.switchTo().defaultContent();
			driver.findElement(By.xpath("//div[contains(@class,'x-window') and contains(@class,'x-resizable-pinned') "
					+ "and contains(@style,'visible')]//td[contains(em,'Don') "
					+ "and contains(em,'t Save')]")).click();
			
		}
		catch(Exception ex){
			//EnvironmentSetup.logger.info("Don't Save: " + ex.toString());
		}
		try{
			driver.switchTo().alert().accept();
		}
		catch (NoAlertPresentException ex){
			EnvironmentSetup.logger.info("No alert");
		}
		try{
			for(int iCount=0; iCount<=20; iCount++){
				TabCountAfter = driver.findElements(By.xpath(strObjectProperty)).size();
				if(TabCountAfter<TabCountBefore){
					break;
				}
			}
			
		}
		catch(Exception ex){
			
		}
		
		return "Tab Closed:::Done";
	}
	
	public void switchtoMainWindow(){
		try{
			driver.switchTo().defaultContent();
		}
		catch(Exception ex){
			
		}
		
		try{
			driver.switchTo().defaultContent();
		}
		catch(Exception ex){
			
		}
		
	}
	
	
	public String closeBrowser(){
		try{
			driver.close();
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
		try{
			driver.quit();
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
		return "Browser Closed:::Done";
	}
	
	
	public String compose(String strObject, String data){
        String strTextEntered = "";
        String strActualResult="";
        boolean displayed = false;
        //Specific to Service Cloud Answer Customer Scenarios - This can be used as a keyword within the reusable
        try{
	        driver.findElement(By.xpath("//textarea[.='Write an email to the customer...']")).click();
	        for(int iCount=0; iCount<=10; iCount++){
	               displayed = driver.findElement(By.xpath("//textarea[.='Write an email to the customer...']")).isDisplayed();
	               if (displayed==false){
	                     break;
	               }
	               driver.findElement(By.xpath("//textarea[.='Write an email to the customer...']")).click();
	        }
        }
        catch(Exception ex){
        	
        }
        
        //Rich Text Editor Frame
        try{
               driver.switchTo().frame(driver.findElement(By.xpath(strObject)));
	      }
        catch (Exception ex){
			 EnvironmentSetup.logger.warning("Compose Frame: " + ex.toString());
			 driver.switchTo().defaultContent();
			 driver.switchTo().frame(driver.findElement(By.xpath(strObject)));
		}
	      
		try{
			WebElement compose = driver.findElement(By.tagName("body"));
			compose.click();
			compose.clear();
			compose.sendKeys(data);
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Exception setting text: " + ex.toString());
		}
	      
	      try{
	             WebElement compose = driver.findElement(By.tagName("body"));
	             strTextEntered = compose.getText();
	             strTextEntered = compose.getText();
	      }
	      catch (Exception ex){
	                   EnvironmentSetup.logger.info("Exception setting text: " + ex.toString());
	      }
	 
	       if(strTextEntered.contains(data)){
	             strActualResult = data + " Entered in Answer Customer Email Body:::Pass";
	       }
	       else{
	             strActualResult = data + " Entered in Answer Customer Email Body:::Warning";
	       }
	       driver.switchTo().defaultContent();

	       return strActualResult;
	}

	public void alertHandler(){
		boolean aletrpresent;
		aletrpresent = false;
		
		try{
			driver.wait(1000);
			
		}
		catch (Exception ex) {
			
		}
				
		try{
			Alert alert = driver.switchTo().alert(); 
	
			EnvironmentSetup.logger.info(alert.getText());
			
			alert.accept();
			
		}
		catch (NoAlertPresentException ex){
			EnvironmentSetup.logger.info("No alert");
		}
	}
	
	
	public String handleDialog(){
		boolean aletrpresent;
		aletrpresent = false;
		String alertText = "";
		

		for(int iCount=0; iCount<40; iCount++){
			try{
				//Thread.sleep(1000);
				Alert alert = driver.switchTo().alert();
				alertText = alert.getText();
				EnvironmentSetup.logger.info(alert.getText());
				alert.accept();
				aletrpresent = true;
				break;
			}
			catch(Exception ex){
				//EnvironmentSetup.logger.info();
				aletrpresent = false;
				EnvironmentSetup.strGlobalException = ex.toString();
			}
		}
		
				
		if (aletrpresent == true){
			
			return "Accepted Dialog with Text: " + alertText + ":::Pass";
		}
		else{
			return "Expected dialog Not Present:::Fail";
		}		
	}
	
	public String dismissDialog(){
		boolean aletrpresent;
		aletrpresent = false;
		String alertText = "";
		
		try{
			driver.wait(1000);
		}
		catch (Exception ex) {
			
		}
				
		try{
			Alert alert = driver.switchTo().alert(); 
			alertText = alert.getText();
			EnvironmentSetup.logger.info(alertText);
			alert.dismiss();
			
		}
		catch (NoAlertPresentException ex){
			EnvironmentSetup.logger.info("No alert");
			EnvironmentSetup.strGlobalException = ex.toString();
			return "Expected dialog Not Present:::Fail";
		}
		
		
		try{
			driver.wait(10000);
		}
		catch (Exception ex) {
			
		}
		
		return "Dismissed Dialog with Text: " + alertText + ":::Pass";
	}
	
	public String attachFile(String strFilePath){
		
		try{
			WebElement uploadElem = driver.findElement(By.xpath("//input[@type='file']"));
			uploadElem.sendKeys(strFilePath);
		}
		catch(Exception ex){
			EnvironmentSetup.logger.info(ex.toString());
		}
		
		
		try{
			driver.switchTo().defaultContent();
		}
		catch(Exception ex){
			return "File Not Attached:::Fail";
		}
		
		return "File Attached:::Pass";
	}
	
	public static String captureScreenshot(String FileName){
		driver = new Augmenter().augment(driver);
		EnvironmentSetup.logger.info(EnvironmentSetup.strScreenshotsPath);
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			EnvironmentSetup.strCurrentScreenshotLocation = EnvironmentSetup.strScreenshotsPath +"Step_" 
					+ Integer.toString(EnvironmentSetup.intSlNo+1)+ "_" + EnvironmentSetup.strCurrentDataset +".png";
			
			FileUtils.copyFile(screenshot, new File(EnvironmentSetup.strCurrentScreenshotLocation));
			
			return "Screenshot File: " + EnvironmentSetup.strCurrentScreenshotLocation + ":::Done";
		} catch (IOException e) {
			EnvironmentSetup.logger.info(e.toString());
			e.printStackTrace();
			return "Failed to capture Screenshot " + e.toString() + ":::Warning";
		}
	}
	
	//TODO Deprecated - Does not work when there is no viewport. Was specific to Apttus
	public String downloadPDF(String strFilePath){
        String strActualResult = "";
        boolean blnResult = false;
        
        try{
               //Driver.waitForFrameToLoad("//iframe[@name='theIframe']", "5000");
               driver.switchTo().frame(KeywordExecutionLibrary.driver.findElement(By.name("theIframe")));
        }
        catch(Exception ex){
               System.out.println(ex.toString());
               EnvironmentSetup.strGlobalException = ex.toString();
        }
        
        try{
               Actions oAction = new Actions(driver);
               oAction.moveToElement(driver.findElement(By.xpath("//div[contains(text(),'Akamai')]")));
               oAction.contextClick(driver.findElement(By.xpath("//div[contains(text(),'Akamai')]"))).build().perform();  /* this will perform right click */
               oAction.sendKeys(Keys.chord("h"));
               oAction.build().perform();
               oAction.sendKeys(Keys.chord("F"));
               oAction.build().perform();
               Thread.sleep(1000);
        }
        catch (Exception ex){
               blnResult = false;
               System.out.println(ex.toString());
               strActualResult = ex.toString();
               EnvironmentSetup.strGlobalException = ex.toString();
        }
        Robot robot = null;
        try {
        	robot = new Robot();
        
           StringSelection file = new StringSelection(strFilePath);
           System.out.println(strFilePath);
           Toolkit.getDefaultToolkit().getSystemClipboard().setContents(file, null);
           robot.keyPress(KeyEvent.VK_CONTROL);
           robot.keyPress(KeyEvent.VK_V);
           robot.keyRelease(KeyEvent.VK_V);
           robot.keyRelease(KeyEvent.VK_CONTROL);
           
           robot.delay(1000);
           
           robot.keyPress(KeyEvent.VK_ENTER);
           robot.keyRelease(KeyEvent.VK_ENTER);
           blnResult = true;
               
        } catch (AWTException e) {
               //
               e.printStackTrace();
               blnResult = false;
               strActualResult = e.toString();
        }
        
        if(blnResult == true){
               return "File Downloaded and Stored in: " + strFilePath + ":::Done";
        }
        else{
               try{
                     robot.keyPress(KeyEvent.VK_ESCAPE);
                     robot.keyRelease(KeyEvent.VK_ESCAPE);
               }
               catch(Exception ex){
                     System.out.println("Dialog not Closed");
               }
               return "Error Downloading File"+ strActualResult + ":::Warning";
        }
	}
	
	private void handleAlert(){
		String alertText = "";
		
		//captureScreenshot("Unexpected Alert");
		
		EnvironmentSetup.strCurrentScreenshotLocation = EnvironmentSetup.strScreenshotsPath +"Step_" 
				+ Integer.toString(EnvironmentSetup.intSlNo+1)+ "_Dialog_" + EnvironmentSetup.strCurrentDataset +".png";
		
		try {
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File(EnvironmentSetup.strCurrentScreenshotLocation));
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		try{
			alertText = driver.switchTo().alert().getText();
			//ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, EnvironmentSetup.strReusableName ,"Unexpected Dialog", "Unexpected Dialog", alertText,"Warning");
		}
		catch(Exception ex){
			System.out.println("Error Handler Alert");
		}
		
		try{
			driver.switchTo().alert().accept();
			ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, EnvironmentSetup.strReusableName ,"Unexpected Dialog", "Unexpected Dialog", alertText,"Warning");
		}
		catch(Exception ex){
			//driver.switchTo().alert().accept();
		}
		
		SiebelRecovery.saveErrors();
	}
	
	public void clearInputField(String strObjectProperty){
		try{
			driver.findElement(By.xpath(strObjectProperty)).clear();
			driver.findElement(By.xpath(strObjectProperty)).sendKeys("");
		}
		catch(UnhandledAlertException ex){
			handleAlert();
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			EnvironmentSetup.strGlobalException = ex.toString();
		}
	}
	
	public String getRequiredLink(String strObjectProperty){
		String href = "";
		try{
			href = driver.findElement(By.xpath(strObjectProperty)).getAttribute("href");
			EnvironmentSetup.logger.info(href);
		}
		catch(UnhandledAlertException ex){
			handleAlert();
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			return "";
		}
		
		return href;
	}
	
	public String buildURL(String additionalParams){
		String CurrentURL = "";
		try{
			CurrentURL = driver.getCurrentUrl();
			
			CurrentURL = CurrentURL.substring(0, CurrentURL.indexOf(".com", 0)) + ".com" + additionalParams.trim();
			
			EnvironmentSetup.logger.info(CurrentURL);
		}
		catch(UnhandledAlertException ex){
			handleAlert();
		}
		catch(Exception ex){
			EnvironmentSetup.logger.severe(ex.toString());
			return "";
		}
		
		return CurrentURL;
	}
	
	public void goToURL(String TargetURL){
		
		try{
			driver.get(TargetURL);
		}
		catch(UnhandledAlertException ex){
			handleAlert();
		}
		catch(Exception ex){
			EnvironmentSetup.logger.severe(ex.toString());
		}
		
	}
	
	//Added Action for Mouse Hover Over
	public void hoverOver (String strObjectProperty){
		   
	       try{
	        	
	           Actions action = new Actions(driver);
	 		   action.moveToElement(driver.findElement(By.xpath(strObjectProperty)));
	 		   action.build().perform();
	 		   
	        }
	       	catch(UnhandledAlertException ex){
	       		EnvironmentSetup.logger.warning("Unexpected Alert Present after Double Clicking on " + EnvironmentSetup.strCurrentObjectName);
	       		System.out.println("Exception in hoverOver");
	       		handleAlert();
			}
	        catch (NoSuchElementException ex){
	            EnvironmentSetup.logger.info(objectIdentifier + " " + ex.toString());
	            EnvironmentSetup.strGlobalException = ex.toString();
	       		System.out.println("Exception in hoverOver");	                        
	        }
	        catch (StaleElementReferenceException ex){
	        	EnvironmentSetup.strGlobalException = ex.toString();
	       		System.out.println("Exception in hoverOver");
	        }
	        catch(Exception ex){
	        	EnvironmentSetup.strGlobalException = ex.toString();
	       		System.out.println("Exception in hoverOver");
	        }
		}
	
}
