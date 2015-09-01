package myclass.function;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import myclass.config.MyConfig;

public class MyMail{
	private static Properties property;
	private static final String TO_MAIL_ADDRESS = MyConfig.getConfig("MyMail.toMailAddress");
	private static final String TO_PERSONAL_NAME = MyConfig.getConfig("MyMail.toPersonalName");
	private static final String FROM_MAIL_ADDRESS = MyConfig.getConfig("MyMail.fromMailAddress");
	private static final String FROM_PERSONAL_NAME = MyConfig.getConfig("MyMail.fromPersonalName");
	
    @SuppressWarnings("unused")
	private static MyMail instance = new MyMail();
	
	private MyMail(){
		property = new Properties();
        property.put("mail.smtp.auth", "true");
        property.put("mail.smtp.starttls.enable", "true");
        property.put("mail.smtp.host", "smtp.gmail.com");
        property.put("mail.smtp.port", "587");
        property.put("mail.smtp.debug", "true");
    }
	
    public static void sendMail(String title, String message) throws Exception{
        Session session = Session.getInstance(property);

        InternetAddress toAddress = new InternetAddress(TO_MAIL_ADDRESS, TO_PERSONAL_NAME);
        InternetAddress fromAddress = new InternetAddress(FROM_MAIL_ADDRESS, FROM_PERSONAL_NAME);

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setRecipient(Message.RecipientType.TO, toAddress);
        mimeMessage.setFrom(fromAddress);
        mimeMessage.setSubject(title, "ISO-2022-JP");
        mimeMessage.setText(message, "ISO-2022-JP");

        Transport.send(mimeMessage);
    }
}
