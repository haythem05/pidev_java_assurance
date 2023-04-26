/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author HD
 */
public class Emailsender {
    private static final String USERNAME = "aymen.rahali@esprit.tn";
    private static final String PASSWORD = "tumlrjgqhmlmbpwt";
    
    public static void sendEmail_add(String toEmail, String message) {
        //String toEmail = "manouch2001.ra@gmail.com";
        String subject = "Bienvenue chez SecurAssur ";
        //String message = "confirmation email";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session;
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(USERNAME));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            emailMessage.setSubject(subject);
            emailMessage.setText(message);

            Transport.send(emailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
   
}
}
