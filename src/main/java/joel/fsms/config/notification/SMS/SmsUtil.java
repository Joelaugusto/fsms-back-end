package joel.fsms.config.notification.SMS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SmsUtil {
    private String msisdn;
    private String message;
    private Object response;

    /**
     * Send an sms to a given msisdn
     *
     * @param msisdn well formatted destination number with country prefix (e.g. +258841234567)
     * @param textMessage
     */
    public abstract void sendSms(String msisdn, String textMessage) throws Exception;

    public void sendSms() throws Exception {
        if (this.getMsisdn().isEmpty() || this.getMessage().isEmpty())
            throw new RuntimeException("Por favor forneça número e mensagem válidos.");

        this.sendSms(this.getMsisdn(), this.getMessage());
    }
}
