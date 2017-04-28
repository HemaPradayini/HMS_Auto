package genericFunctions;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;


public class EmailFunctions {

	Properties properties = null;
	private Session session = null;
	private Store store = null;
	private Folder inbox = null;

	DataParser dataParser = new DataParser();

	public String SendEmail(String strCurrentDataSet, String subAndBody){
		String sendEmailFrom = "";
		String sendEmailTo = "";
		String sendEmailSubject = "";
		String sendEmailBody = "";
		String strActualResult = "";

		sendEmailFrom = dataParser.getData("EmailFrom", strCurrentDataSet);
		sendEmailTo = dataParser.getData("EmailTo", strCurrentDataSet);

		if(!subAndBody.isEmpty()){
			//String issueAbstract = subAndBody.split(",")[0];
			//String issueDescription = subAndBody.split(",")[1];
			sendEmailSubject = dataParser.getData(subAndBody.split(",")[0], strCurrentDataSet);
			sendEmailBody = dataParser.getData(subAndBody.split(",")[1], strCurrentDataSet);
		} else {
			sendEmailSubject = dataParser.getData("EmailSubject", strCurrentDataSet);
			sendEmailBody = dataParser.getData("EmailBody", strCurrentDataSet);                
		}

		try{
			if(sendEmailFrom.toUpperCase().contains("AKAMAI.COM")){
				sendEmailInternal(sendEmailFrom.trim(), sendEmailTo.trim(), sendEmailSubject.trim(), sendEmailBody.trim());
			}
			else{
				sendEmailExternal(sendEmailFrom.trim(), sendEmailTo.trim(), sendEmailSubject.trim(), sendEmailBody.trim());
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
			strActualResult = "Email send failed with exception: " + ex.toString() + ":::Warning";
			return strActualResult;
		}

		strActualResult = "Email sent from: " + sendEmailFrom + " To: " + sendEmailTo + ":::Done";
		return strActualResult;

	}

	public String CheckEmail(String strEmailId, String strEmailSubject, String strCurrentDataSet){
		String checkEmailMailID = "";
		String checkEmailSubject = "";
		String[] arrEmailIDs;
		String[] arrSubject;
		boolean mailStatus = false;
		String strActualResult = "";
		String strSubjectParams="";
		Boolean bIntEmailFailed=false;
		Boolean bExtEmailFailed=false;

		checkEmailMailID = dataParser.getData(strEmailId, strCurrentDataSet).trim();
		checkEmailSubject = dataParser.getData(strEmailSubject, strCurrentDataSet).trim();


		System.out.println("Subject of email is "+checkEmailSubject);

		if(checkEmailMailID.contains(",")){
			arrEmailIDs = checkEmailMailID.split(",");
			for (int iCount = 0; iCount<=arrEmailIDs.length-1; iCount++){
				EnvironmentSetup.strEmailAddress=arrEmailIDs[iCount];

				if(arrEmailIDs[iCount].toUpperCase().contains("AKAMAI.COM")){
					EnvironmentSetup.strEmailPassword=EnvironmentSetup.environmentDetailsMap.get(EnvironmentSetup.strEmailAddress);
					System.out.println("From checkEmail ==> "+ EnvironmentSetup.strEmailAddress+" : "+EnvironmentSetup.strEmailPassword);

					mailStatus = checkEmailInternal(checkEmailSubject);
					if(mailStatus==true){
						strActualResult = strActualResult + "; Email to: " + arrEmailIDs[iCount] + " Recieved";
						EnvironmentSetup.logger.info(mailStatus +" Internal Email: " + strActualResult);
					}
					else{
						bIntEmailFailed=true;
						strActualResult = strActualResult + "; Email to: " + arrEmailIDs[iCount] + " Not Recieved";
						EnvironmentSetup.logger.info(mailStatus +" Internal Email: " + strActualResult);
					}
				}
				else{
					EnvironmentSetup.strEmailPassword=EnvironmentSetup.environmentDetailsMap.get(EnvironmentSetup.strEmailAddress);
					mailStatus = checkEmailExternal(checkEmailSubject);
					if(mailStatus==true){
						strActualResult = strActualResult + "; Email to: " + arrEmailIDs[iCount] + " Recieved";
					}
					else{
						bExtEmailFailed=true;
						strActualResult = strActualResult + "; Email to: " + arrEmailIDs[iCount] + " Not Recieved";
					}
				}
			}
		}

		else if(checkEmailMailID.toUpperCase().contains("AKAMAI.COM")){
			EnvironmentSetup.strEmailAddress=checkEmailMailID;
			EnvironmentSetup.strEmailPassword=EnvironmentSetup.environmentDetailsMap.get(checkEmailMailID);
			mailStatus = checkEmailInternal(checkEmailSubject);
			if(mailStatus==true){
				strActualResult = strActualResult + "; Email to: " + checkEmailMailID + " Recieved";
			}
			else{
				bIntEmailFailed=true;
				strActualResult = strActualResult + "; Email to: " + checkEmailMailID + " Not Recieved";
			}
		}
		else{
			EnvironmentSetup.strEmailAddress=checkEmailMailID;
			EnvironmentSetup.strEmailPassword=EnvironmentSetup.environmentDetailsMap.get(checkEmailMailID);
			mailStatus = checkEmailExternal(checkEmailSubject);
			if(mailStatus==true){
				strActualResult = strActualResult + "; Email to: " + checkEmailMailID + " Recieved";
			}
			else{
				bExtEmailFailed=true;
				strActualResult = strActualResult + "; Email to: " + checkEmailMailID + " Not Recieved";
			}
		}

		if (mailStatus == true){
			strActualResult = strActualResult + ":::Pass"; 
		}
		else{
			strActualResult = strActualResult + ":::Fail";
		}

		if(bExtEmailFailed || bIntEmailFailed)
			strActualResult = strActualResult.replace("Pass", "Fail");

		return strActualResult;
	}

	private void sendEmailInternal(String from, String to, String subject, String body){

		final String username =  from;
		final String password = EnvironmentSetup.environmentDetailsMap.get(from);

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.akamai.com");
		props.put("mail.smtp.port", "25");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean checkEmailInternal(String strEmailSubject){

		boolean mailRecd = false;

		int index = EnvironmentSetup.strEmailAddress.indexOf('@');
		final String username =  EnvironmentSetup.strEmailAddress.substring(0, index);
		final String password = EnvironmentSetup.strEmailPassword;

		Properties properties = new Properties();

		//properties.setProperty("mail.imaps.host", "email.msg.corp.akamai.com");
		properties.setProperty("mail.imaps.host", "exchange.akamai.com");
		properties.setProperty("mail.imaps.port", "993");
		properties.setProperty("mail.imaps.starttls.enable", "true");
		properties.setProperty("mail.transport.protocol", "imaps");
		properties.setProperty("mail.imaps.ssl.trust", "*");

		Session session = Session.getInstance(properties,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				System.out.println(username);
				System.out.println(password);
				System.out.println("Inside authenticator");
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			System.out.println("entering try to get store");
					store = session.getStore("imaps");
					store.connect();
					System.out.println("connected to server");
					inbox = store.getFolder("INBOX");
					inbox.open(Folder.READ_WRITE);
					System.out.println("got inbox");

					Message[] unreadmessages = inbox.search(new FlagTerm(
							new Flags(Flag.SEEN), false));
					;

					System.out.println(strEmailSubject);
					SearchTerm subject = new SubjectTerm(strEmailSubject);

					Message[] messages = inbox.search(subject, unreadmessages);

					EnvironmentSetup.logger.info("Number of mails = " + messages.length);
					if(messages.length<=0){
						System.out.println("FAIL");
					}
					else{
						System.out.println("PASS");
					}


					for (int i = 0; i < messages.length; i++) {
						Message message = messages[i];
						Address[] from = message.getFrom();
						String Subject = message.getSubject();
						if(Subject.contains(strEmailSubject)) 
						{
							mailRecd = true;
							EnvironmentSetup.logger.info("-------------------------------");
							EnvironmentSetup.logger.info("Date : " + message.getSentDate());
							EnvironmentSetup.logger.info("From : " + from[0]);
							EnvironmentSetup.logger.info("Subject: " + message.getSubject());
							EnvironmentSetup.logger.info("Content :");
							processMessageBody(message);
							message.setFlag(Flag.SEEN, true);
							EnvironmentSetup.logger.info("--------------------------------");
							break;
						}
						else{
							i=i+1;
						}

					}

					inbox.close(true);
					store.close();
		} 
		catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return mailRecd;    
	}

	private void sendEmailExternal(String from, String to, String subject, String body){


		final String username =  from;
		final String password = EnvironmentSetup.environmentDetailsMap.get(from);

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}


	}

	private boolean checkEmailExternal(String strEmailSubject){

		final String userName = EnvironmentSetup.strEmailAddress;// provide user name
		final String password = EnvironmentSetup.environmentDetailsMap.get(userName);// provide password

		boolean mailRecd = false;

		Properties properties=new Properties();
		properties.setProperty("mail.host", "imap.gmail.com");
		properties.setProperty("mail.port", "995");
		properties.setProperty("mail.transport.protocol", "imaps");
		properties.setProperty("mail.imaps.ssl.trust", "*");

		session = Session.getInstance(properties,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				System.out.println("inside authenticator");
				return new PasswordAuthentication(userName, password);
			}
		});
		try {
			store = session.getStore("imaps");
			store.connect();
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);

			Message unreadmessages[] = inbox.search(new FlagTerm(
					new Flags(Flag.SEEN), false));
			;

			//Search for required subject in email
			System.out.println(strEmailSubject);
			SearchTerm subject = new SubjectTerm(strEmailSubject);

			Message[] messages = inbox.search(subject, unreadmessages);

			EnvironmentSetup.logger.info("Number of mails = " + messages.length);
			if(messages.length<=0){
				System.out.println("FAIL");
			}
			else{
				System.out.println("PASS");
			}

			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				Address[] from = message.getFrom();
				String Subject = message.getSubject();
				System.out.println("email subject "+Subject);
				if(Subject.contains(strEmailSubject)) 
				{
					mailRecd = true;
					System.out.println("mail received");
					EnvironmentSetup.logger.info("-------------------------------");
					EnvironmentSetup.logger.info("Date : " + message.getSentDate());
					EnvironmentSetup.logger.info("From : " + from[0]);
					EnvironmentSetup.logger.info("Subject: " + message.getSubject());
					EnvironmentSetup.logger.info("Content :");
					processMessageBody(message);
					message.setFlag(Flag.SEEN, true);
					EnvironmentSetup.logger.info("--------------------------------");
					break;
				}
				else{
					i=i+1;
				}

			}

			inbox.close(true);
			store.close();
		} 
		catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return mailRecd;

	}

	@SuppressWarnings("resource")
	public void processMessageBody(Message message) {
		try {
			Object content = message.getContent();
			if (content instanceof String) {
				EnvironmentSetup.logger.info(content.toString());
			} else if (content instanceof Multipart) {
				Multipart multiPart = (Multipart) content;
				procesMultiPart(multiPart);
			} else if (content instanceof InputStream) {

				InputStream inStream = (InputStream) content;
				int ch;
				while ((ch = inStream.read()) != -1) {
					System.out.write(ch);
				}
			}

		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void procesMultiPart(Multipart content) {

		try {
			int multiPartCount = content.getCount();
			for (int i = 0; i < multiPartCount; i++) {
				BodyPart bodyPart = content.getBodyPart(i);
				Object o;

				o = bodyPart.getContent();
				if (o instanceof String) {
					EnvironmentSetup.logger.info(o.toString());
				} else if (o instanceof Multipart) {
					procesMultiPart((Multipart) o);
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/*           public String ReplyEmail(String strEmailId, String strEmailSubject, String strCurrentDataSet){
                                String checkEmailMailID = "";
                                String checkEmailSubject = "";
                                String[] arrEmailIDs;
                                boolean mailStatus=false;
                                String strActualResult = "";
                                Boolean bIntEmailFailed=false;
                                Boolean bExtEmailFailed=false;

                                checkEmailMailID = dataParser.getData(strEmailId, strCurrentDataSet).trim();
                                checkEmailSubject = dataParser.getData(strEmailSubject, strCurrentDataSet).trim();


                                Properties properties = new Properties();

                                //Session session = Session.getDefaultInstance(properties);


                                System.out.println("Subject of email is "+checkEmailSubject);

                                if(checkEmailMailID.contains(",")){
                                                arrEmailIDs = checkEmailMailID.split(",");
                                                for (int iCount = 0; iCount<=arrEmailIDs.length-1; iCount++){
                                                                EnvironmentSetup.strEmailAddress=arrEmailIDs[iCount];
                                                                EnvironmentSetup.strEmailPassword=EnvironmentSetup.environmentDetailsMap.get(EnvironmentSetup.strEmailAddress);
                                                                //Get server property
                                                                properties=getServerProperty(arrEmailIDs[iCount].toUpperCase());

                                                                if(arrEmailIDs[iCount].toUpperCase().contains("AKAMAI.COM")){
                                                                                System.out.println("From checkEmail ==> "+ EnvironmentSetup.strEmailAddress+" : "+EnvironmentSetup.strEmailPassword);

                                                                                mailStatus = checkEmailInternal(checkEmailSubject);
                                                                                if(mailStatus==true){
                                                                                                EnvironmentSetup.logger.info(mailStatus +" Internal Email: " + strActualResult);
                                                                                                strActualResult = sendReply(strEmailSubject, userName, password, properties, session);
                                                                                }
                                                                                else{
                                                                                                bIntEmailFailed=true;
                                                                                                strActualResult = strActualResult + "; Email to: " + arrEmailIDs[iCount] + " Not Recieved";
                                                                                                EnvironmentSetup.logger.info(mailStatus +" Internal Email: " + strActualResult);
                                                                                }
                                                                }
                                                                else{
                                                                                properties=getExternalMailProperty(properties);
                                                                                mailStatus = checkEmailExternal(checkEmailSubject);
                                                                                if(mailStatus==true){
                                                                                                EnvironmentSetup.logger.info(mailStatus +" Internal Email: " + strActualResult);
                                                                                                strActualResult = sendReply(strEmailSubject, userName, password, properties, session);
                                                                                }
                                                                                else{
                                                                                                bIntEmailFailed=true;
                                                                                                strActualResult = strActualResult + "; Email to: " + arrEmailIDs[iCount] + " Not Recieved";
                                                                                                EnvironmentSetup.logger.info(mailStatus +" Internal Email: " + strActualResult);
                                                                                }
                                                                }
                                                }
                                }

                                //Reply to Internal single email
                                else if(checkEmailMailID.toUpperCase().contains("AKAMAI.COM")){
                                                EnvironmentSetup.strEmailAddress=checkEmailMailID;
                                                EnvironmentSetup.strEmailPassword=EnvironmentSetup.environmentDetailsMap.get(checkEmailMailID);
                                                mailStatus = checkEmailInternal(checkEmailSubject);
                                                if(mailStatus==true){
                                                                EnvironmentSetup.logger.info(mailStatus +" Internal Email: " + strActualResult);
                                                                strActualResult = sendReply(strEmailSubject, userName, password, properties, session);
                                                }
                                                else{
                                                                bIntEmailFailed=true;
                                                                strActualResult = strActualResult + "; Email to: " + checkEmailMailID + " Not Recieved";
                                                                EnvironmentSetup.logger.info(mailStatus +" Internal Email: " + strActualResult);
                                                }
                                }
                                //Reply to External single email
                                else{
                                                EnvironmentSetup.strEmailAddress=checkEmailMailID;
                                                //EnvironmentSetup.strEmailPassword=EnvironmentSetup.environmentDetailsMap.get(checkEmailMailID);
                                                mailStatus = checkEmailExternal(checkEmailSubject);
                                                if(mailStatus==true){
                                                                EnvironmentSetup.logger.info(mailStatus +" Internal Email: " + strActualResult);
                                                                strActualResult = sendReply(strEmailSubject, userName, password, properties, session);
                                                }
                                                else{
                                                                bIntEmailFailed=true;
                                                                strActualResult = strActualResult + "; Email to: " + checkEmailMailID + " Not Recieved";
                                                                EnvironmentSetup.logger.info(mailStatus +" Internal Email: " + strActualResult);
                                                }
                                }

                                if (mailStatus == true){
                                                strActualResult = strActualResult + ":::Pass"; 
                                }
                                else{
                                                strActualResult = strActualResult + ":::Fail";
                                }

                                if(bExtEmailFailed || bIntEmailFailed)
                                                strActualResult = strActualResult.replace("Pass", "Fail");

                                return strActualResult;
                }

                private String sendReply(String strEmailSubject, final String userName,
                                                final String password, Properties properties, Session session) {
                                String strActualResult="";
                                session.setDebug(true);
                                  session = Session.getInstance(properties,
                                                                                new javax.mail.Authenticator() {
                                                                protected PasswordAuthentication getPasswordAuthentication() {
                                                                                System.out.println("inside authenticator");
                                                                                return new PasswordAuthentication(userName, password);
                                                                }
                                                });

                                System.out.println(userName);
                                System.out.println(password);
                                                try {
                                                                Store store = session.getStore("imaps");
                                                                store.connect();
                                                                Folder inbox = store.getFolder("INBOX");
                                                                inbox.open(Folder.READ_WRITE);

                                                                Message unreadmessages[] = inbox.search(new FlagTerm(
                                                                                                new Flags(Flag.SEEN), false));
                                                                ;

                                                                //Search for required subject in email
                                                                System.out.println(strEmailSubject);
                                                                SearchTerm subject = new SubjectTerm(strEmailSubject);

                                                                Message[] messages = inbox.search(subject, unreadmessages);
                                                                System.out.println(messages.length);

                                                                if(messages.length<=0){
                                                                                System.out.println("FAIL");
                                                                }
                                                                else{
                                                                                System.out.println("PASS");
                                                                }

                                                                for (int i = 0; i < messages.length; i++) {
                                                                                Message message = messages[i];
                                                                                Address[] from = message.getFrom();
                                                                                String Subject = message.getSubject();
                                                                                System.out.println("email subject "+Subject);
                                                                                if(Subject.contains(strEmailSubject)) 
                                                                                {
                                                                                                System.out.println("mail received");
                                                                                                System.out.println("-------------------------------");
                                                                                                System.out.println("Date : " + message.getSentDate());
                                                                                                System.out.println("From : " + from[0]);
                                                                                                System.out.println("Subject: " + message.getSubject());
                                                                                                System.out.println("Content :");
                                                                                                processMessageBody(message);
                                                                                                message.setFlag(Flag.SEEN, true);
                                                                                                Message messagereply=message.reply(true);
                                                                                                System.out.println("messagereply "+messagereply);
                                                                                                System.out.println("recipients list"+messagereply.getAllRecipients());
                                                                                                messagereply.setText("Replying to Testing email message");

                                                                                    // Send the message by authenticating the SMTP server
                                                  // Create a Transport instance and call the sendMessage
                                                  Transport t = session.getTransport("smtp");
                                                  try {
                                                                     //connect to the smpt server using transport instance
                                                                     //change the user and password accordingly 
                                                             t.connect(userName, password);
                                                             t.sendMessage(messagereply, messagereply.getAllRecipients());
                                                             strActualResult="Pass";
                                                  } finally {
                                                     t.close();
                                                  }
                                                  System.out.println("message replied successfully ....");
                                                                                                System.out.println("--------------------------------");
                                                                                                break;
                                                                                }
                                                                                else{
                                                                                                i=i+1;
                                                                                }

                                                                }

                                                                inbox.close(true);
                                                                store.close();
                                                } 
                                                catch (NoSuchProviderException e) {
                                                                e.printStackTrace();
                                                } catch (MessagingException e) {
                                                                e.printStackTrace();
                                                }
                                return strActualResult;
                }
	 */
	private Properties getExternalMailProperty(Properties properties) {
		properties.put("mail.store.protocol", "imaps");
		properties.put("mail.host", "imap.gmail.com");
		properties.put("mail.port", "995");
		properties.setProperty("mail.transport.protocol", "imaps");
		properties.setProperty("mail.imaps.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		return properties;
	}

	/*Reply email:
	 * Step1: Get email Ids list.
	 * get properties for each email id
	 * Check for each email id if the email is received
	 * If email is received, Reply to that email.
	 */

	/**Function to set properties
	 * @param strEmailId
	 * @return
	 */
	private static Properties getServerProperty(String strEmailId){
		Properties properties=new Properties();
		if(strEmailId.toUpperCase().contains("GMAIL.COM"))
		{

			properties.setProperty("mail.host", "imap.gmail.com");
			properties.setProperty("mail.port", "995");
			properties.setProperty("mail.transport.protocol", "imaps");
			properties.setProperty("mail.imaps.ssl.trust", "*");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
		}

		else if(strEmailId.toUpperCase().contains("AKAMAI.COM"))
		{

			properties.setProperty("mail.imaps.host", "exchange.akamai.com");
			properties.setProperty("mail.imaps.port", "993");
			properties.setProperty("mail.imaps.starttls.enable", "true");
			properties.setProperty("mail.transport.protocol", "imaps");
			properties.setProperty("mail.imaps.ssl.trust", "*");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.akamai.com");
			properties.put("mail.smtp.port", "25");
		}

		return properties;

	}

	/*
	 *//**
	 * Function to reply to an email using javamail API.
	 * @param strEmailId
	 * @param strEmailSubject
	 * @param strCurrentDataSet
	 * @return
	 * Author: Triveni Aroli
	 *//*
                public String ReplyEmail(String strEmailId, String strEmailSubject, String replyType, String strAddRecipientTo,String strAddRecipientCC,String strAddRecipientBCC, String strReplyText,String strCurrentDataSet){
                                String checkEmailMailID = "";
                                String checkEmailSubject = "";
                                String[] arrEmailIDs;
                                String[] arrSubject;
                                boolean mailStatus = false;


                                String strActualResult = "";
                                String strSubjectParams="";
                                String strReplyType="";
                                String replyAddRecipientTo="";
                                String replyAddRecipientCC="";
                                String replyAddRecipientBCC="";
                                String replyText="";


                                Boolean bIntEmailFailed=false;
                                Boolean bExtEmailFailed=false;

                                checkEmailMailID = dataParser.getData(strEmailId, strCurrentDataSet).trim();
                                checkEmailSubject = dataParser.getData(strEmailSubject, strCurrentDataSet).trim();

                                strReplyType=dataParser.getData(replyType, strCurrentDataSet).trim();
                                replyAddRecipientTo=dataParser.getData(strAddRecipientTo, strCurrentDataSet).trim();
                                replyAddRecipientCC=dataParser.getData(strAddRecipientCC, strCurrentDataSet).trim();
                                replyAddRecipientBCC=dataParser.getData(strAddRecipientBCC, strCurrentDataSet).trim();
                                replyText=dataParser.getData(strReplyText, strCurrentDataSet).trim();



                                System.out.println("Subject of email is "+checkEmailSubject);

                                if(checkEmailMailID.contains(",")){
                                                arrEmailIDs = checkEmailMailID.split(",");
                                                for (int iCount = 0; iCount<=arrEmailIDs.length-1; iCount++){
                                                                EnvironmentSetup.strEmailAddress=arrEmailIDs[iCount];
                                                                EnvironmentSetup.strEmailPassword=EnvironmentSetup.environmentDetailsMap.get(EnvironmentSetup.strEmailAddress);
                                                                mailStatus = replyEmailExternal(checkEmailMailID,checkEmailSubject,strReplyType,replyAddRecipientTo,replyAddRecipientCC,replyAddRecipientBCC,replyText);
                                                }
                                }
                                else{
                                                EnvironmentSetup.strEmailAddress=checkEmailMailID;
                                                EnvironmentSetup.strEmailPassword=EnvironmentSetup.environmentDetailsMap.get(checkEmailMailID);
                                                mailStatus = replyEmailExternal(checkEmailMailID,checkEmailSubject,strReplyType,replyAddRecipientTo,replyAddRecipientCC,replyAddRecipientBCC,replyText);
                                                if(mailStatus==true){
                                                                strActualResult = strActualResult + "; Email to: " + checkEmailMailID + " Recieved";
                                                }
                                                else{
                                                                bExtEmailFailed=true;
                                                                strActualResult = strActualResult + "; Email to: " + checkEmailMailID + " Not Recieved";
                                                }
                                }

                                if (mailStatus == true){
                                                strActualResult = strActualResult + ":::Pass"; 
                                }
                                else{
                                                strActualResult = strActualResult + ":::Fail";
                                }

                                if(bExtEmailFailed || bIntEmailFailed)
                                                strActualResult = strActualResult.replace("Pass", "Fail");

                                return strActualResult;
                }


                private boolean replyEmailExternal(String checkEmailMailID,String strEmailSubject,String replyType,String replyAddRecipientTo,String replyAddRecipientCC,String replyAddRecipientBCC,String replyText){

                                int index = EnvironmentSetup.strEmailAddress.indexOf('@');
                                final String userName =  EnvironmentSetup.strEmailAddress.substring(0, index);
                                //final String userName = EnvironmentSetup.strEmailAddress;// provide user name
                                final String password = EnvironmentSetup.strEmailPassword;// provide password
                                boolean mailRecd = false;
                                boolean replyAll=false;

                                if(replyType.toUpperCase().contains("YES"))
                                                replyAll=true;

                                String address=replyAddRecipientTo;
                                String addressCC=replyAddRecipientCC;
                                String addressBCC=replyAddRecipientBCC;

                                System.out.println("ToAddress is "+address);
                                System.out.println("CCAddress is "+addressCC);
                                System.out.println("BCCAddress is "+addressBCC);

                                InternetAddress[] iAdressArray=null;
                                InternetAddress[] ccAddress=null;
                                InternetAddress[] bccAddress=null;

                                try {
                                                iAdressArray = InternetAddress.parse(address);
                                                ccAddress=InternetAddress.parse(addressCC);
                                                bccAddress=InternetAddress.parse(addressBCC);
                                } catch (AddressException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }

                                Properties properties=null;
                                properties=getServerProperty(checkEmailMailID);
                                session = Session.getInstance(properties,
                                                                new javax.mail.Authenticator() {
                                                protected PasswordAuthentication getPasswordAuthentication() {
                                                                System.out.println("inside authenticator");
                                                                return new PasswordAuthentication(userName, password);
                                                }
                                });
                                try {
                                                store = session.getStore("imaps");
                                                store.connect();
                                                inbox = store.getFolder("INBOX");
                                                inbox.open(Folder.READ_WRITE);

                                                Message unreadmessages[] = inbox.search(new FlagTerm(
                                                                                new Flags(Flag.SEEN), false));
                                                ;

                                                //Search for required subject in email
                                                System.out.println(strEmailSubject);
                                                SearchTerm subject = new SubjectTerm(strEmailSubject);

                                                Message[] messages = inbox.search(subject, unreadmessages);

                                                EnvironmentSetup.logger.info("Number of mails = " + messages.length);
                                                if(messages.length<=0){
                                                                System.out.println("FAIL");
                                                }
                                                else{
                                                                System.out.println("PASS");
                                                }

                                                for (int i = 0; i < messages.length; i++) {
                                                                Message message = messages[i];
                                                                Address[] from = message.getFrom();
                                                                String Subject = message.getSubject();
                                                                System.out.println("email subject "+Subject);
                                                                if(Subject.contains(strEmailSubject)) 
                                                                {
                                                                                mailRecd = true;
                                                                                System.out.println("mail received");
                                                                                EnvironmentSetup.logger.info("-------------------------------");
                                                                                processMessageBody(message);
                                                                                message.setFlag(Flag.SEEN, true);
                                                                                Message messagereply=message.reply(replyAll);
                                                                                //messagereply.setText("Replying to Testing email message");
                                                                                messagereply.setText(replyText);
                                                                                //messagereply.setRecipients(RecipientType.TO, iAdressArray);
                                                                                messagereply.setRecipients(RecipientType.CC, ccAddress);
                                                                                messagereply.setRecipients(RecipientType.BCC, bccAddress);

                                                                                // Send the message by authenticating the SMTP server
                                                                                // Create a Transport instance and call the sendMessage
                                                                                Transport t = session.getTransport("smtp");
                                                                                try {
                                                                                                //connect to the smpt server using transport instance
                                                                                                //change the user and password accordingly      
                                                                                                t.connect(userName, password);
                                                                                                System.out.println("connected successfully ");
                                                                                                t.sendMessage(messagereply, messagereply.getAllRecipients());
                                                                                                System.out.println("message sent successfully  ");
                                                                                                EnvironmentSetup.logger.info("Mail Delivered");
                                                                                } 
                                                                                catch(AddressException e){
                                                                                                e.printStackTrace();
                                                                                }
                                                                                catch (MessagingException e) {
                                                                                                e.printStackTrace();
                                                                                }

                                                                                finally {
                                                                                                t.close();
                                                                                }
                                                                                EnvironmentSetup.logger.info("--------------------------------");
                                                                                break;
                                                                }
                                                                else{
                                                                                i=i+1;
                                                                }

                                                }

                                                inbox.close(true);
                                                store.close();
                                } 
                                catch (NoSuchProviderException e) {
                                                e.printStackTrace();
                                } catch (MessagingException e) {
                                                e.printStackTrace();
                                }

                                return mailRecd;

                }
	  */


	/**
	 * Function to reply to an email using javamail API.
	 * @param strEmailId
	 * @param strEmailSubject
	 * @param strCurrentDataSet
	 * @return
	 * Author: Triveni Aroli
	 */
	public String ReplyEmail(String strEmailId, String strEmailSubject, String strReplyText,String strCurrentDataSet){
		String checkEmailMailID = "";
		String checkEmailSubject = "";
		String[] arrEmailIDs;
		String[] arrSubject;
		boolean mailStatus = false;


		String strActualResult = "";
		String strSubjectParams="";
		String replyText="";


		Boolean bIntEmailFailed=false;
		Boolean bExtEmailFailed=false;

		//Mandatoy fields fetched as parameters from test step
		checkEmailMailID = dataParser.getData(strEmailId, strCurrentDataSet).trim();
		checkEmailSubject = dataParser.getData(strEmailSubject, strCurrentDataSet).trim();
		replyText= dataParser.getData(strReplyText, strCurrentDataSet).trim();

		/*           strReplyType=dataParser.getData(replyType, strCurrentDataSet).trim();
                                replyAddRecipientTo=dataParser.getData(strAddRecipientTo, strCurrentDataSet).trim();
                                replyAddRecipientCC=dataParser.getData(strAddRecipientCC, strCurrentDataSet).trim();
                                replyAddRecipientBCC=dataParser.getData(strAddRecipientBCC, strCurrentDataSet).trim();
                                replyText=dataParser.getData(strReplyText, strCurrentDataSet).trim();*/

		//sendEmailFrom = dataParser.getData("EmailFrom", strCurrentDataSet);
		//ReplyEmail ApproveOrderEmailSubject,ReplyText

		//strReplyType=dataParser.getData("ReplyAll", strCurrentDataSet).trim();
		//replyAddRecipientTo=dataParser.getData(strAddRecipientTo, strCurrentDataSet).trim();
		//replyAddRecipientCC=dataParser.getData("AddRecipientCC", strCurrentDataSet).trim();
		//replyAddRecipientBCC=dataParser.getData("AddRecipientBCC", strCurrentDataSet).trim();



		System.out.println("Subject of email is "+checkEmailSubject);

		if(checkEmailMailID.contains(",")){
			arrEmailIDs = checkEmailMailID.split(",");
			for (int iCount = 0; iCount<=arrEmailIDs.length-1; iCount++){
				EnvironmentSetup.strEmailAddress=arrEmailIDs[iCount];
				EnvironmentSetup.strEmailPassword=EnvironmentSetup.environmentDetailsMap.get(EnvironmentSetup.strEmailAddress);
				//mailStatus = replyEmailExternal(checkEmailMailID,checkEmailSubject,strReplyType,replyAddRecipientTo,replyAddRecipientCC,replyAddRecipientBCC,replyText);
				mailStatus=replyEmailExternal(checkEmailMailID,checkEmailSubject,replyText,strCurrentDataSet);
			}
		}
		else{
			EnvironmentSetup.strEmailAddress=checkEmailMailID;
			EnvironmentSetup.strEmailPassword=EnvironmentSetup.environmentDetailsMap.get(checkEmailMailID);
			//mailStatus = replyEmailExternal(checkEmailMailID,checkEmailSubject,strReplyType,replyAddRecipientTo,replyAddRecipientCC,replyAddRecipientBCC,replyText);
			mailStatus=replyEmailExternal(checkEmailMailID,checkEmailSubject,replyText,strCurrentDataSet);
			if(mailStatus==true){
				strActualResult = strActualResult + "; Email to: " + checkEmailMailID + " Recieved";
			}
			else{
				bExtEmailFailed=true;
				strActualResult = strActualResult + "; Email to: " + checkEmailMailID + " Not Recieved";
			}
		}

		if (mailStatus == true){
			strActualResult = strActualResult + ":::Pass"; 
		}
		else{
			strActualResult = strActualResult + ":::Fail";
		}

		if(bExtEmailFailed || bIntEmailFailed)
			strActualResult = strActualResult.replace("Pass", "Fail");

		return strActualResult;
	}


	private boolean replyEmailExternal(String checkEmailMailID,String strEmailSubject,String replyText, String strCurrentDataSet){

		int index = EnvironmentSetup.strEmailAddress.indexOf('@');
		final String userName =  EnvironmentSetup.strEmailAddress.substring(0, index);
		//final String userName = EnvironmentSetup.strEmailAddress;// provide user name
		final String password = EnvironmentSetup.strEmailPassword;// provide password
		boolean mailRecd = false;
		boolean replyAll=false;

		String address="";
		String addressCC="";
		String addressBCC="";

		String strReplyType=dataParser.getData("ReplyAll", strCurrentDataSet).trim();
		addressCC=dataParser.getData("AddRecipientCC", strCurrentDataSet).trim();
		addressBCC=dataParser.getData("AddRecipientBCC", strCurrentDataSet).trim();

		if(strReplyType.toUpperCase().contains("YES") | strReplyType.isEmpty())
			replyAll=true;

		System.out.println("ToAddress is "+address);
		System.out.println("CCAddress is "+addressCC);
		System.out.println("BCCAddress is "+addressBCC);

		InternetAddress[] iAdressArray=null;
		InternetAddress[] ccAddress=null;
		InternetAddress[] bccAddress=null;

		try {
			iAdressArray = InternetAddress.parse(address);
			ccAddress=InternetAddress.parse(addressCC);
			bccAddress=InternetAddress.parse(addressBCC);
		} catch (AddressException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Properties properties=null;
		properties=getServerProperty(checkEmailMailID);
		session = Session.getInstance(properties,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				System.out.println("inside authenticator");
				return new PasswordAuthentication(userName, password);
			}
		});
		try {
			store = session.getStore("imaps");
			store.connect();
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);

			Message unreadmessages[] = inbox.search(new FlagTerm(
					new Flags(Flag.SEEN), false));
			;

			//Search for required subject in email
			System.out.println(strEmailSubject);
			SearchTerm subject = new SubjectTerm(strEmailSubject);

			Message[] messages = inbox.search(subject, unreadmessages);

			EnvironmentSetup.logger.info("Number of mails = " + messages.length);
			if(messages.length<=0){
				System.out.println("FAIL");
			}
			else{
				System.out.println("PASS");
			}

			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				Address[] from = message.getFrom();
				String Subject = message.getSubject();
				System.out.println("email subject "+Subject);
				if(Subject.contains(strEmailSubject)) 
				{
					mailRecd = true;
					System.out.println("mail received");
					EnvironmentSetup.logger.info("-------------------------------");
					processMessageBody(message);
					message.setFlag(Flag.SEEN, true);
					Message messagereply=message.reply(replyAll);
					//messagereply.setText("Replying to Testing email message");
					messagereply.setText(replyText);
					//messagereply.setRecipients(RecipientType.TO, iAdressArray);
					messagereply.setRecipients(RecipientType.CC, ccAddress);
					messagereply.setRecipients(RecipientType.BCC, bccAddress);

					// Send the message by authenticating the SMTP server
					// Create a Transport instance and call the sendMessage
					Transport t = session.getTransport("smtp");
					try {
						//connect to the smpt server using transport instance
						//change the user and password accordingly      
						t.connect(userName, password);
						System.out.println("connected successfully ");
						t.sendMessage(messagereply, messagereply.getAllRecipients());
						System.out.println("message sent successfully  ");
						EnvironmentSetup.logger.info("Mail Delivered");
					} 
					catch(AddressException e){
						e.printStackTrace();
					}
					catch (MessagingException e) {
						e.printStackTrace();
					}

					finally {
						t.close();
					}
					EnvironmentSetup.logger.info("--------------------------------");
					break;
				}
				else{
					i=i+1;
				}

			}

			inbox.close(true);
			store.close();
		} 
		catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return mailRecd;

	}

}


