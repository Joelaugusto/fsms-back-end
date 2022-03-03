package joel.fsms.config.notification.EMAIL.message;

import com.sun.istack.ByteArrayDataSource;
import joel.fsms.config.notification.EMAIL.NotificationsContextHolder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.springframework.core.io.InputStreamResource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MailMessage extends SimpleMessage<MailMessage> {
    @Getter
    private String template = "notifications/email.html";
    @Getter
    private String fromEmail;
    @Getter
    private String fromPersonal;

    @Getter
    private Map<String, ByteArrayDataSource> attachments = new HashMap<>();

    @Getter @Setter
    private String to;

    private List<String> replyTo = new ArrayList<>();

    public MailMessage from(String email, String name) {
        fromEmail = email;
        fromPersonal = name;
        return this;
    }

    @SneakyThrows
    public MailMessage attachment(String name, InputStreamResource fileSystemResource) {
        InputStream inputStream = fileSystemResource.getInputStream();
        byte[] target = new byte[inputStream.available()];
        inputStream.read(target);
        ByteArrayDataSource dataSource = new ByteArrayDataSource(target, "application/octet-stream");
        attachments.put(name, dataSource);
        return this;
    }

    /**
     * Context for the mail message
     * @return
     */
    public Context getContext() {
        Context context = new Context();
        context.setVariable("message", this);
        return context;
    }

    public String render() {
        TemplateEngine templateEngine = NotificationsContextHolder.getTemplateEngine();
        return templateEngine.process(template, getContext());
    }
}
