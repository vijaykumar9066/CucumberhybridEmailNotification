package utiles;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailUtils {

    public static void sendEmailWithAttachment(String reportPath) {

        Properties emailProp = ConfigReader.loadEmailNotificationProperties();
        Properties configProp = ConfigReader.loadConfigProperties();

        String from     = emailProp.getProperty("email.from");
        String password = emailProp.getProperty("email.password");

        String to       = emailProp.getProperty("email.to");
        String cc       = emailProp.getProperty("email.cc");
        String bcc      = emailProp.getProperty("email.bcc");

        String smtpHost = emailProp.getProperty("email.smtp");
        String smtpPort = emailProp.getProperty("email.port");

        String timestamp = commonutils.getCurrentTimestamp();
        String browser   = configProp.getProperty("browser");
        String url       = configProp.getProperty("url");
        String project   = configProp.getProperty("projectName");

        String subject = emailProp.getProperty("email.subject", "Automation Report");

        String bodyTemplate = "";
        try {
            String templatePath = System.getProperty("user.dir")
                    + "/src/test/resources/templates/emailTemplate.html";

            bodyTemplate = new String(Files.readAllBytes(Paths.get(templatePath)));

        } catch (Exception e) {
            System.err.println("❌ Failed to load email template!");
            e.printStackTrace();
        }

        // ✅ Replace placeholders in HTML
        bodyTemplate = bodyTemplate
                .replace("{{timestamp}}", timestamp)
                .replace("{{browser}}", browser)
                .replace("{{url}}", url)
                .replace("{{projectName}}", project);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            if (cc != null && !cc.isEmpty())
                message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));

            if (bcc != null && !bcc.isEmpty())
                message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));

            message.setSubject(subject);

            // ✅ Email Body
            MimeBodyPart htmlBody = new MimeBodyPart();
            htmlBody.setContent(bodyTemplate, "text/html; charset=UTF-8");

            // ✅ Cucumber Report Attachment
            MimeBodyPart attachment = new MimeBodyPart();
            attachment.attachFile(new File(reportPath));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlBody);
            multipart.addBodyPart(attachment);

            message.setContent(multipart);
            Transport.send(message);

            System.out.println("✅ Email Sent Successfully!");

        } catch (Exception e) {
            System.err.println("❌ Email sending failed!");
            e.printStackTrace();
        }
    }
}
