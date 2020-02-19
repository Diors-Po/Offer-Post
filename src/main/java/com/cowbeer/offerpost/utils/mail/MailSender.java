package com.cowbeer.offerpost.utils.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailSender {

    private static final String SMTP_QQ = "smtp.exmail.qq.com";
    private static final String SMTP_EDM = "smtp.trigger.edmcn.cn";

    private final transient Properties props = System.getProperties();
    private transient MailAuthenticator authenticator;
    private transient Session session;


    public MailSender(final String smtpHostName, final String username,
                      final String password) {
        init(username, password, smtpHostName);
    }

    public MailSender(final String username, final String password) {
        String smtpHost;
        // QQ企业邮箱
        if(username.endsWith("@mooctest.net")){
            smtpHost = SMTP_QQ;
        }
        // EDM帐号
        else if(isEDM(username)){
            smtpHost = SMTP_EDM;
        }
        else{
            smtpHost = "smtp." + username.split("@")[1];
        }

        init(username, password, smtpHost);
    }

    private void init(String username, String password, String smtpHostName) {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", smtpHostName);
        // patch for QQ企业邮箱
        if (smtpHostName.equals(SMTP_QQ)){
            props.put("mail.smtp.socketFactory.port", "465");
        }
        authenticator = new MailAuthenticator(username, password);
        session = Session.getInstance(props, authenticator);
    }


    private boolean isEDM(String account){
        if(account.startsWith("edmc") && !account.contains("@")){
            return true;
        }
        return false;
    }

    private InternetAddress getSenderAddress() throws AddressException, UnsupportedEncodingException {
        if(isEDM(authenticator.getUsername())){
            return new InternetAddress(MailConstants.EDM_SENDER_ADDRESS, MailConstants.EDM_SENDER_NAME);
        }
        return new InternetAddress(authenticator.getUsername(), MailConstants.DEFAULT_SENDER_NAME);
    }


    public void send(String recipient, String subject, Object content)
            throws AddressException, MessagingException, UnsupportedEncodingException {
        final MimeMessage message = new MimeMessage(session);

        message.setHeader("X-Priority", "3");
        message.setFrom(getSenderAddress());
        message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);
        message.setContent(content.toString(), "text/html;charset=utf-8");
        Transport.send(message);
    }

    public void send(String recipient, String subject, String content, String attachmentPath)
            throws MessagingException, UnsupportedEncodingException {
        final MimeMessage message = new MimeMessage(session);

        // set priority to normal - 1 for high, 3 for normal, 5 for low
        message.setHeader("X-Priority", "3");
        message.setFrom(getSenderAddress());
        message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);

        // Create and combine email parts
        MimeBodyPart attachment = createAttachment(attachmentPath);
        MimeBodyPart contentPart = createContent(content);

        MimeMultipart allPart = new MimeMultipart("mixed");
        allPart.addBodyPart(attachment);
        allPart.addBodyPart(contentPart);

        message.setContent(allPart, "text/html;charset=utf-8");
        Transport.send(message);
    }

    public void send(List<String> recipients, String subject, Object content)
            throws AddressException, MessagingException, UnsupportedEncodingException {
        final MimeMessage message = new MimeMessage(session);

        message.setFrom(getSenderAddress());

        final int num = recipients.size();
        InternetAddress[] addresses = new InternetAddress[num];
        for (int i = 0; i < num; i++) {
            addresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setHeader("X-Priority", "3");
        message.setRecipients(RecipientType.TO, addresses);

        message.setSubject(subject);
        message.setContent(content.toString(), "text/html;charset=utf-8");
        Transport.send(message);
    }

    private MimeBodyPart createAttachment(String fileName) throws MessagingException {
        MimeBodyPart attachmentPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(fileName);
        attachmentPart.setDataHandler(new DataHandler(fds));
        attachmentPart.setFileName(fds.getName());
        return attachmentPart;
    }
    private MimeBodyPart createContent(String body)
            throws MessagingException {
        MimeBodyPart contentBody = new MimeBodyPart();
        contentBody.setContent(body, "text/html;charset=utf-8");

        return contentBody;
    }

}
