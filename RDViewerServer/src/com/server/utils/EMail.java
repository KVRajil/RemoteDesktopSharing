package com.server.utils;

import java.util.Properties;
import java.util.StringTokenizer;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

public class EMail {

    String authStr = "";
    private static final String SERVER = "smtp.gmail.com";
    private static final boolean DEBUG = true;
    public static String errorType = "UNKNOWN";

    public EMail() {
    }    
    public static boolean sendMail(String subject, String content,
            String attached_files) {

        Properties properties = System.getProperties();
        properties.put("mail.smtps.host", SERVER);
        properties.put("mail.smtps.auth", "true");
        Session session = Session.getInstance(properties);
        if (DEBUG) {
            session.setDebug(true);
        }
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(SettingsConstatnts.MAIL_SERVER));
            if (SettingsConstatnts.MAIL_SEND_TO != null) {
                msg.addRecipients(Message.RecipientType.TO, SettingsConstatnts.MAIL_SEND_TO);
            }
            msg.setContent(content, "text/html");
            msg.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Fill the message
            messageBodyPart.setText(content);

            // Create a Multipart
            Multipart multipart = new MimeMultipart();
            // Add part one
            multipart.addBodyPart(messageBodyPart);

            for (StringTokenizer tokenizer =
                    new StringTokenizer(attached_files, ",");
                    tokenizer.hasMoreTokens();) {
                String filepath = tokenizer.nextToken().trim();
                String filename = filepath.substring(filepath.lastIndexOf('\\'));

                // Part two is attachment //
                messageBodyPart = new MimeBodyPart();
                // Get the attachment
                DataSource source = new FileDataSource(filepath);
                // Set the data handler to the attachment
                messageBodyPart.setDataHandler(new DataHandler(source));
                // Set the filename
                messageBodyPart.setFileName(filename);
                // Add part two
                multipart.addBodyPart(messageBodyPart);
            }

            // Put parts in message
            msg.setContent(multipart);

            msg.saveChanges();

            Transport tr = session.getTransport("smtps");
            tr.connect(SERVER, SettingsConstatnts.MAIL_SERVER,
                    SettingsConstatnts.MAIL_SERVER_PSWD);
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
            System.out.println("sending success");
            

            return true;

        } catch (AuthenticationFailedException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (SendFailedException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    class MyPasswordAuthenticator extends Authenticator {

        String user;
        String password;

        public MyPasswordAuthenticator(String username, String password) {
            super();
            this.user = username;
            this.password = password;
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, password);
        }
    }

    public static void main(String[] args) {
        //invokeSendMail();
        //new EMail();
    }
}

