package joel.fsms.config.notification.EMAIL;

public interface Notifiable <N extends Notifiable<N>> {
    @SuppressWarnings("unchecked")
    default  <P extends Notification> N sendNotification(P notification) {
        NotificationsContextHolder.getChannel().send(this, notification);
        return (N) this;
    }

    String getEmail();

    static Notifiable of(String email) {
        return new GenericNotifiable(email);
    }
}
