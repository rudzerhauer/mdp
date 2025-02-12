package net.etfbl.api;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import net.etfbl.model.Book;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class EmailService {
	
	

	    private File createZip(List<String> filePaths) throws IOException {
	        String userHome = System.getProperty("user.home");
	        System.out.println(userHome);
	        File tempZipFile = new File(userHome, "books.zip");

	        List<String> resolvedPaths = resolvePaths(filePaths);

	        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tempZipFile))) {
	            byte[] buffer = new byte[1024];
	            for (String filePath : resolvedPaths) {
	                File file = new File(filePath);
	                try (FileInputStream fis = new FileInputStream(file)) {
	                    ZipEntry zipEntry = new ZipEntry(file.getName());
	                    zos.putNextEntry(zipEntry);
	                    int length;
	                    while ((length = fis.read(buffer)) > 0) {
	                        zos.write(buffer, 0, length);
	                    }
	                    zos.closeEntry();
	                }
	            }
	        }
	        return tempZipFile;
	    }

	    private List<String> resolvePaths(List<String> filePaths) {
	        List<String> resolvedPaths = new ArrayList<>();
	        for (String path : filePaths) {
	        	String userHome = System.getProperty("user.home");
	            File file = new File(userHome + "\\" + path); // Treat the given path as relative
	            System.out.println("Checking: " + file.getAbsolutePath());
	            if (file.exists()) {
	                resolvedPaths.add(file.getAbsolutePath());
	            } else {
	                System.out.println("File not found or not a valid file: " + path);
	            }
	        }
	        return resolvedPaths;
	    }
	


    // Updated method to accept email details and an attachment
	 public boolean sendEmailWithAttachment(String recipient, String subject, String body, List<String> filePaths) {
		    try {
		        // Set system properties for SSL connection (if necessary)
		        System.setProperty("javax.net.ssl.trustStore", "D:\\Program Files\\Java\\jdk-18.0.2.1\\lib\\security\\cacerts");
		        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		        System.setProperty("mail.smtp.starttls.enable", "true");
		        System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

		        // Create the email object
		        Email email = new HtmlEmail();
		        email.setHostName("smtp.gmail.com");
		        email.setSmtpPort(587);
		        email.setAuthentication("nikola.gataric01@gmail.com", "vqic uakv mhqr dvfw");
		        email.setStartTLSEnabled(true);
		        email.setFrom("nikola.gataric01@gmail.com");
		        email.addTo(recipient);
		        email.setSubject(subject);
		        email.setMsg(body);

		        // Create the ZIP file from the list of file paths
		        File zipFile = createZip(filePaths);

		        // Check and attach the ZIP file
		        if (zipFile != null && zipFile.exists()) {
		            System.out.println("Attaching file: " + zipFile.getAbsolutePath());

		            // Create the email attachment
		            EmailAttachment emailAttachment = new EmailAttachment();
		            emailAttachment.setPath(zipFile.getAbsolutePath());
		            emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
		            emailAttachment.setDescription("Books ZIP File");
		            emailAttachment.setName(zipFile.getName());

		            // Attach the file to the email
		            ((HtmlEmail) email).attach(emailAttachment);  // Directly call attach on HtmlEmail

		            // Optional: Print attachment details for debugging
		            System.out.println("Attachment details:");
		            System.out.println("Description: " + emailAttachment.getDescription());
		            System.out.println("Name: " + emailAttachment.getName());
		            System.out.println("Path: " + emailAttachment.getPath());
		        } else {
		            System.out.println("Attachment file is either null or doesn't exist.");
		        }

		        // Send the email
		        email.send();
		        System.out.println("Email sent successfully.");
		        return true;
		    } catch (EmailException | IOException e) {
		        // Improved error handling
		        e.printStackTrace();
		        System.out.println("Error sending email: " + e.getMessage());
		        return false;
		    }
		}

}
