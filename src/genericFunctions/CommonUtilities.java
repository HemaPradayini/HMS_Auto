package genericFunctions;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;


public class CommonUtilities {

	public static void delay(){
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("EXCEPTION IN DELAY THREAD!!");
		}
	}

	public static void delay(int delayInSecs){
		try {
			Thread.sleep(delayInSecs);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("EXCEPTION IN DELAY THREAD!!");
		}
	}
	public static String getSystemDate(String DateFormat){
		 DateFormat dateFormat = new SimpleDateFormat(DateFormat);
		 Date date = new Date();
		 String date1= dateFormat.format(date);
	     return date1;
		 
	}
	public static String getNextSystemDate(String DateFormat, int noOfDays){
		 DateFormat dateFormat = new SimpleDateFormat(DateFormat);
		 Date date = new Date();
		 date = DateUtils.addDays(date, noOfDays);
		 String date1= dateFormat.format(date);
	     return date1;
		 
	}

	public static void WriteTestCaseReport(String testCaseReportRecord){
		BufferedWriter bw = null;
		FileWriter fw = null;
	
		try {
	
			String content = "This is the content to write into file\n";
	
			fw = new FileWriter("C:\\InstaHMSBatch\\ConsolidatedBatchReport.txt", true);
			bw = new BufferedWriter(fw);
			//bw.append(testCaseReportRecord + "\n");
			PrintWriter out = new PrintWriter(bw);
		    out.println(testCaseReportRecord);
		    out.close();
			System.out.println("Done");
	
		} catch (IOException e) {
	
			e.printStackTrace();
	
		} finally {
	
			try {
	
				if (bw != null)
					bw.close();
	
				if (fw != null)
					fw.close();
	
			} catch (IOException ex) {
	
				ex.printStackTrace();
	
			}
	
		}
	}

}
