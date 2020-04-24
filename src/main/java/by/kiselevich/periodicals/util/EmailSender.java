package by.kiselevich.periodicals.util;

import by.kiselevich.periodicals.exception.UtilRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailSender {

    private static final Logger LOG = LogManager.getLogger(EmailSender.class);

    private static final String NO_EMAIL_PROPERTIES = "No email properties file";
    private static final String EMAIL_PROPERTIES = "credentials.properties";
    private static final String PROTOCOL_PROPERTIES_KEY = "mail.protocol";
    private static final String HOST_PROPERTIES_KEY = "mail.host";
    private static final String USER_PROPERTIES_KEY = "mail.user";
    private static final String PASSWORD_PROPERTIES_KEY = "mail.password";
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private static final String SUBJECT_CHARSET = "UTF-8";

    private final Properties mailServerProperties = new Properties();

    private EmailSender() {
        try {
            InputStream stream = EmailSender.class.getClassLoader().getResourceAsStream(EMAIL_PROPERTIES);
            if (stream == null) {
                throw new UtilRuntimeException(NO_EMAIL_PROPERTIES);
            }
            mailServerProperties.load(stream);
        } catch (IOException e) {
            LOG.warn(e);
        }
    }

    private static final class EmailSenderHolder {
        private static final EmailSender EMAIL_SENDER = new EmailSender();
    }

    public static EmailSender getInstance() {
        return EmailSenderHolder.EMAIL_SENDER;
    }

    public void sendLetter(String destinationEmail, String subject, String body) {
        new Thread(() -> sendLetterInThread(destinationEmail, subject, body)).start();
    }

    private void sendLetterInThread(String destinationEmail, String subject, String body) {
        try {
            LOG.info("Letter sending to {} started", destinationEmail);
            Session getMailSession;
            MimeMessage generateMailMessage;

            getMailSession = Session.getDefaultInstance(mailServerProperties);
            generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(destinationEmail));
            generateMailMessage.setSubject(subject, SUBJECT_CHARSET);
            generateMailMessage.setContent(body, CONTENT_TYPE);

            Transport transport = getMailSession.getTransport(mailServerProperties.getProperty(PROTOCOL_PROPERTIES_KEY));
            transport.connect(mailServerProperties.getProperty(HOST_PROPERTIES_KEY),
                    mailServerProperties.getProperty(USER_PROPERTIES_KEY),
                    mailServerProperties.getProperty(PASSWORD_PROPERTIES_KEY));
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();
            LOG.info("Letter sent to {}", destinationEmail);
        } catch (MessagingException e) {
            LOG.warn(e);
        }
    }
}
