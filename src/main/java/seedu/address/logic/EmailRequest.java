/*
package seedu.address.logic;

import com.nimbusds.oauth2.sdk.Message;
import org.apache.commons.codec.binary.Base64;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailRequest {

    private String emailAdd;
    private String subject;
    private String body;

    public EmailRequest(String emailAdd, String subject, String body) {
        this.emailAdd = emailAdd;
        this.subject = subject;
        this.body = body;
    }

    private String getEmailAdd() {
        return emailAdd;
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }


}
*/

/*public static MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {

    Properties props = new Properties();

    Session session = Session.getDefaultInstance(props, null);


    MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
}

public static Message createMessageWithEmail(MimeMessage emailContent)
        throws MessagingException, IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    emailContent.writeTo(buffer);
    byte[] bytes = buffer.toByteArray();
    String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
    Message message = new Message();
    message.setRaw(encodedEmail);
    return message;
}
*/

