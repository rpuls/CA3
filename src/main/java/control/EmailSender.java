package control;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
 
	private Properties mailServerProperties;
	private Session getMailSession;
	private MimeMessage generateMailMessage;
 
//	public static void main(String args[]) throws AddressException, MessagingException {
//		sendEmail("noreply.polygonproject@gmail.com", "Vintro");
//	}
 
	public void sendEmail(Mail inv){
            
            try{
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("MESSAGE SENT" + inv.getEmailTo());
 
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(inv.getEmailTo())); //this should be changed
		String subj = inv.getSubject();
		String emailBody = inv.getMessage();
                generateMailMessage.setSubject(subj);
		generateMailMessage.setContent(emailBody, "text/html");
		Transport transport = getMailSession.getTransport("smtp");
		transport.connect("smtp.gmail.com", "noreply.polygonproject@gmail.com", "poly123go");//this should be changed
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
	}
//
//    public String getMessage() {
//        return "To our valued shop owner,<br><br>" +
//                "Thank you for being part of Nørrebro-On-The-Ground. You can now login with the user name <b>VintroUser</b>"+
//                " and password <b>test</b>.<br><br> You can also change your password <a href=" + "--LINK HERE--"+
//                ">here.</a>" +
//                "<br><br> Regards, <br>NBROG Team"; //the generated password and new user name should be in here + link for update pw page
//    }
}