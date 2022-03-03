package joel.fsms.config.notification.EMAIL;

import joel.fsms.config.notification.EMAIL.message.MailMessage;

public interface Notification {
    default MailMessage toMailMessage(Notifiable notifiable) {
        return null;
    }
}
