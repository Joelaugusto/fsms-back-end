package joel.fsms.config.notification.EMAIL.channel;


import joel.fsms.config.notification.EMAIL.Notifiable;
import joel.fsms.config.notification.EMAIL.Notification;
import joel.fsms.config.notification.EMAIL.message.MailMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@AllArgsConstructor
@Component
@Slf4j
public class MailChannel {
    private final JavaMailSender mailer;
    private final Environment environment;

    public void send(Notifiable notifiable, Notification notification) {
        MailMessage mailMessage = notification.toMailMessage(notifiable);

        if (mailMessage == null) return;

        String defaultFromAddress = environment.getProperty("spring.mail.from_address");
        String defaultFromName = environment.getProperty("spring.mail.from_name");
        String fromAddress = getOrFallback(mailMessage.getFromEmail(), defaultFromAddress);
        String fromName = getOrFallback(mailMessage.getFromPersonal(), defaultFromName);

        try {
            MimeMessage message = mailer.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddress, fromName);
            helper.setTo(notifiable.getEmail());
            helper.setSubject(mailMessage.getSubject());
            helper.setText(mailMessage.render(), true);
            for (String fileName : mailMessage.getAttachments().keySet()) {
                helper.addAttachment(fileName, mailMessage.getAttachments().get(fileName));
            }
            mailer.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getOrFallback(String value, String defaultValue) {
        return Optional.ofNullable(value).map(v -> v.trim().isEmpty() ? defaultValue : v.trim()).orElse(defaultValue);
    }
}
