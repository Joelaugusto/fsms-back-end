package joel.fsms.modules.users.notification;

import joel.fsms.config.notification.EMAIL.Notifiable;
import joel.fsms.config.notification.EMAIL.Notification;
import joel.fsms.config.notification.EMAIL.message.MailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
public class EmailVerification implements Notification {

    private final String code;
    private final String deadlineMinute;
    private final String token;
    private final Environment env;

    @Override
    public MailMessage toMailMessage(Notifiable notifiable) {

        String emailVerificationURL = "auth/register";

        return (MailMessage) new MailMessage()
                .setSubject("Email de verificaćão da conta")
                .setGreeting("Saudações")
                .line("Para completar o registro da conta, por favor clica no botão a seguir")
                .line("ou use o código "+code)
                .line("Este link é válido por "+deadlineMinute+" minutos")
                .action("Completar o registro", env.getProperty("baseURL")+ emailVerificationURL +"/"+token)
                .setSalutation("Melhores cumprimentos!");
    }
}
