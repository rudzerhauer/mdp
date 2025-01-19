package net.etfbl.api;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import jakarta.mail.*;

public class EmailService {
    public boolean sendEmail(String recipient, String subject, String body) {
        // Create an instance of HtmlEmail (a subclass of Email)
        
        try {
        	System.setProperty("javax.net.ssl.trustStore", "D:\\Program Files\\Java\\jdk-18.0.2.1\\lib\\security\\cacerts");
        	System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

        	Email email = new HtmlEmail();
            // Set up the email parameters
            email.setHostName("smtp.gmail.com"); // SMTP server
            email.setSmtpPort(587);
           // email.setSSLOnConnect(true);  // Use this if you're using port 465
 // SMTP port for Gmail
            email.setAuthentication("nikola.gataric01@gmail.com", "vqic uakv mhqr dvfw"); // Email and password for authentication
            email.setStartTLSEnabled(true); // Enable TLS
            email.setFrom("nikola.gataric01@gmail.com"); // Sender email address
            email.addTo(recipient); // Recipient's email address
            email.setSubject(subject); // Subject of the email
            email.setMsg(body); // Body content of the email

            // Send the email
            email.send();
            System.out.println("Email sent successfully.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
