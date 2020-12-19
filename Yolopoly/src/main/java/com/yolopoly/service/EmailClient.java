package com.yolopoly.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailClient {
    private static final String senderEmail = "3ayolopoly@gmail.com";
    private static final String senderPassword = "Kissa+12345";

    public static void sendMailToPlayer(String playerEmail) throws MessagingException {
        System.out.println("Sending email to " + playerEmail);

        Session session = createSession();

        //create message using session
        String title = "We have received your feedback!";
        String html = "<h2>Thank you for your feedback</h2><p>We value your comments. We will enhance the YOLOPOLY game with your special feedbacks.</p>";
        MimeMessage message = new MimeMessage(session);
        prepareEmailMessage(message, playerEmail, title, html);

        //sending message
        Transport.send(message);
        System.out.println("Done");
    }

    public static void sendMailToSelf(String playerEmail, String mailContent) throws MessagingException {
        System.out.println("Sending email to " + playerEmail);
        Session session = createSession();

        //create message using session
        String title = "You have a new feedback";
        String html = "<h4>" + playerEmail + " says:\n" + "</h4><p>" + mailContent + "</p>";
        MimeMessage message = new MimeMessage(session);
        prepareEmailMessage(message, senderEmail, title, html);

        //sending message
        Transport.send(message);
        System.out.println("Done");
    }

    private static void prepareEmailMessage(MimeMessage message, String to, String title, String html)
            throws MessagingException {
        message.setContent(html, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(title);
    }

    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");//Outgoing server requires authentication
        props.put("mail.smtp.starttls.enable", "true");//TLS must be activated
        props.put("mail.smtp.host", "smtp.gmail.com"); //Outgoing server (SMTP) - change it to your SMTP server
        props.put("mail.smtp.port", "587");//Outgoing port

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        return session;
    }

    /*public static void main(String[] args) throws MessagingException {
        EmailClient.sendMailToPlayer("benningkayleigh@gmail.com");
        EmailClient.sendMailToSelf("benningkayleigh@gmail.com", "i love you game so much");
    }*/
}
