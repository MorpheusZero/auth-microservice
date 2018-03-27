package net.snowlynxsoftware.authservice.services;

import com.sendgrid.*;
import net.snowlynxsoftware.authservice.config.AuthConfigProperties;

import java.io.IOException;

/**
 * Handles sending of emails when registering a new user or attempting to recover a lost password.
 */
public class EmailService {

    /**
     * Sends an email.
     * @param fromEmail
     * @param subject
     * @param toEmail
     * @param emailContent
     * @throws IOException
     */
    public static void sendEmail(String fromEmail, String subject, String toEmail, String emailContent, AuthConfigProperties config) throws IOException {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Content content = new Content(config.getSendgridContentType(), emailContent);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(config.getSendgridApiKey());
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint(config.getSendgridMailSendEndpoint());
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }

}
