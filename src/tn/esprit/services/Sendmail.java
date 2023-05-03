/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author HD
 */
public class Sendmail {
    public static void sendMail(String receveursList,String object,String corps) {
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        
        String MonEmail = "ines.bessaad@esprit.tn";
        String password = "201JMT1858";
        
        Session session = Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(MonEmail, password);
            }
        
        });
        
        Message message = prepareMessage(session,MonEmail,receveursList,object,corps);
        
        try {
        Transport.send(message);
        } catch (MessagingException ex) {
            System.err.println(ex);
        }
        
    }
    
    private static Message prepareMessage(Session session,String email,String receveursList,String object,String corps)
    {
        Message message = new MimeMessage(session);
        
        try {
            message.setFrom(new InternetAddress(email));
            
            message.setSubject(object);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receveursList));
            message.setText(corps);
            
            return message;
        } catch (MessagingException ex) {
            System.err.println(ex);
        }
        
        return null;
    }
}
