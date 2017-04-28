package genericFunctions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Set;
 





import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;

import seleniumWebUIFunctions.KeywordExecutionLibrary;

@SuppressWarnings("deprecation")
public class FileDownload {
	
	public static String strFileDownloadURL = "";
	//https://akamai--qa--c.cs9.content.force.com/servlet/servlet.FileDownload?file=00PK0000001wtb2
	//href of view link
	public static String getFileURL(String strData){
		
		String DownloadObject = "(((//div[contains(@id,'RelatedNoteList_body')]/table/tbody/tr[contains(@class,'dataRow')]/*[.='<data>'])/preceding-sibling::*)[count(//div[contains(@id,'RelatedNoteList_body')]"
				+ "/table/tbody/tr[contains(@class,'headerRow')]/*[.='Action']/preceding-sibling::*)+1])//a[.='View']";
		
		DownloadObject = DownloadObject.replace("<data>", strData);
		
		try{
			strFileDownloadURL = KeywordExecutionLibrary.driver.findElement(By.xpath(DownloadObject)).getAttribute("href");
		}
		catch(Exception ex){
			EnvironmentSetup.logger.info("Fetch Link HREF: " + ex.toString());
		}
		
		try {
			downloadFile(EnvironmentSetup.strDownloadsPath + EnvironmentSetup.strCurrentDataset + "_" + strData);
			return "Downloaded File Stored In: " + EnvironmentSetup.strDownloadsPath + EnvironmentSetup.strCurrentDataset + "_" + strData + ":::Pass";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error Downloading File: " + e.toString() + ":::Fail";
		}
	}
	
	public static void downloadFile(String outputFilePath) throws Exception {
		 
        CookieStore cookieStore = seleniumCookiesToCookieStore();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.setCookieStore(cookieStore);
 
        HttpGet httpGet = new HttpGet(strFileDownloadURL);
        EnvironmentSetup.logger.info("Downloding file form: " + strFileDownloadURL);
        HttpResponse response = httpClient.execute(httpGet);
        //https://akamai--qa--c.cs10.content.force.com/servlet/servlet.FileDownload?file=00PJ0000003gan5
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            File outputFile = new File(outputFilePath);
            InputStream inputStream = entity.getContent();
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, read);
            }
            fileOutputStream.close();
            EnvironmentSetup.logger.info("Downloded " + outputFile.length() + " bytes. " + entity.getContentType());
        }
        else {
            EnvironmentSetup.logger.info("Download failed!");
        }
        
        httpClient.close();
    }
 
    private static CookieStore seleniumCookiesToCookieStore() {
 
        Set<Cookie> seleniumCookies = KeywordExecutionLibrary.driver.manage().getCookies(); 
        CookieStore cookieStore = new BasicCookieStore();
 
        for(Cookie seleniumCookie : seleniumCookies){
            BasicClientCookie basicClientCookie =
                    new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            basicClientCookie.setDomain(seleniumCookie.getDomain());
            basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
            basicClientCookie.setPath(seleniumCookie.getPath());
            cookieStore.addCookie(basicClientCookie);
        }
 
        return cookieStore;
    }
	
}

