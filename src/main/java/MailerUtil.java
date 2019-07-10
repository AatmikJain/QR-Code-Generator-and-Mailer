import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailerUtil
{
    private static String message;
    private static String path;
    public static void setMessage(String message)
    {
        MailerUtil.message = message;
    }
    public static void setPath(String path)
    {
        MailerUtil.path = path;
    }
    public static void sendMail(String recepient)
    {
        try {
            System.out.println("Sending mail");
            Properties props = new Properties();
            
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            
            final String acc = "email address";
            final String pass = "account password";
            
            Session ses;
            ses = Session.getInstance(props, new Authenticator()
            {
                @Override
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(acc, pass);
                }
            });
            
            Message msg = prepareMessage(ses, acc, recepient);
            
            Transport.send(msg);
            System.out.println("Mail successful");
        } catch (MessagingException ex) {}
    }

    private static Message prepareMessage(Session ses, String acc, String recepient)
    {
        try {
            Message msg = new MimeMessage(ses);
            msg.setFrom(new InternetAddress(acc));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            msg.setSubject("Test Mail");
            msg.setText(message);
            
            BodyPart msgBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            String filename = "./QR_Codes/"+path+".png";
            DataSource src = new FileDataSource(filename);
            msgBodyPart.setDataHandler(new DataHandler(src));
            msgBodyPart.setFileName(filename);
            multipart.addBodyPart(msgBodyPart);
            
            msg.setContent(multipart);
            
            return msg;
        } catch (MessagingException e) {System.out.println(e);}
        return null;
    }   
}
