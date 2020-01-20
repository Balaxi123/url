package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email 
{
	
	public void sendMail(String smtphost, String smtpportnumber, final String from, final String password, String to, String cc, String filepath, String subject)
	{
		Properties pro = System.getProperties();
		pro.put("mail.smtp.host", smtphost);
		pro.put("mail.smtp.port", smtpportnumber);
		pro.put("mail.smtp.auth", "true");
		pro.put("mail.smtp.starttls.enable", "true");
		
		Session session  = Session.getInstance(pro, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(from,password);
			}
		});
		
		try
		{
			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(from));
			
			List<String> toList = getAddress(to);
			for (String address : toList) 
			{
				message.setRecipients(Message.RecipientType.TO,  InternetAddress.parse(address));
			}
			
			List<String> ccList = getAddress(cc);
			for (String address : ccList) 
			{
				message.setRecipients(Message.RecipientType.CC,  InternetAddress.parse(address));
			}
			
			message.setSubject(subject);

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Hi, \n\n This is an auto generated mail, please find the attachment for all client URL status report. \n\n Thanks & Regards \n Venkat");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
	    
			messageBodyPart = new MimeBodyPart();
			String filename = filepath;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message);

			System.out.println("Sent message successfully....");
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Exception is "+e.getMessage());
		}
	}
	
	private static List<String> getAddress(String address) 
	{
		List<String> addressList = new ArrayList<String>();
		
		if (address.isEmpty())
			return addressList;
		
		if (address.indexOf(";") > 0) 
		{
			String[] addresses = address.split(";");
			for (String a : addresses) 
			{
				addressList.add(a);
			}
		} 
		else 
		{
			addressList.add(address);
		}
		
		return addressList;
	}
}
