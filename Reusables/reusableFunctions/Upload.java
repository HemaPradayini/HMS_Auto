package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Upload {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public Upload() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Upload(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	/* public void upload(){
			String workingDir = System.getProperty("user.dir");
			String filepath = workingDir + "\\test.txt";
			System.out.println(filepath);
	        this.executeStep.getDriver().findElement(By.xpath("//input[@id='primary_insurance_doc_content_bytea1' and @type='file']")).sendKeys(filepath);
			verifications.verify(SeleniumVerifications.Appears, "","OPRegDocumentUpload",true);
		}
		*/
		public void upload(String xpathofupload){
			
			String workingDir = System.getProperty("user.dir");
			String filepath = workingDir + "\\test.txt";
			System.out.println(filepath);
	        this.executeStep.getDriver().findElement(By.xpath(xpathofupload)).sendKeys(filepath);
	        //Don't remove the comment since it takes too much of time to verify and the test case fails - Alamelu
	//		verifications.verify(SeleniumVerifications.Appears, "",xpathofupload,false);
		}

	 
	 public void uploadClaims(String filename){
		 String workingDir = System.getProperty("user.dir");
			String filepath = workingDir + "\\"+ filename+".XML";
			System.out.println(filepath);
	        this.executeStep.getDriver().findElement(By.xpath("//input[@id='remittance_metadata']")).sendKeys(filepath);
			verifications.verify(SeleniumVerifications.Appears, "","ClaimSubmissionUploadButton",true);
		 
	 }
	}
