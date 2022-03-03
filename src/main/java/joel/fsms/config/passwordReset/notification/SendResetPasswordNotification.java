package joel.fsms.config.passwordReset.notification;

import joel.fsms.config.notification.EMAIL.Notifiable;
import joel.fsms.config.notification.EMAIL.Notification;
import joel.fsms.config.notification.EMAIL.message.MailMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;

@AllArgsConstructor
public class SendResetPasswordNotification implements Notification {

    private final String deadlineMinute;
    private final String token;
    private final Environment env;

    @Override
    public MailMessage toMailMessage(Notifiable notifiable) {

        String resetURL = "auth/recovery";

        return (MailMessage) new MailMessage()
                .setSubject("Email de restauraćão de senha")
                .setGreeting("Saudações")
                .line("Recebemos uma solicitação para restaurar sua senha de acesso")
                .line("Se você reconhece essa ação, clique no botão abaixo para prosseguir")
                .line("Este link é válido por "+deadlineMinute+" minutos")
                .action("Restaurar Senha", env.getProperty("baseURL")+ resetURL +"/"+token)
                .setSalutation("Melhores cumprimentos!");
    }
}
