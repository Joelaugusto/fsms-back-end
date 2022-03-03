package joel.fsms.config.notification.SMS;


import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.twilio.Twilio.init;

@Component
@RequiredArgsConstructor
@Setter
@Getter
public class TwilioSms extends SmsUtil{

    private final Environment env;
    private String phoneNumber;
    private String accountSid;
    private String authToken;

    @Override
    public void sendSms(String msisdn, String textMessage) throws Exception {
        phoneNumber = Objects.nonNull(phoneNumber) ? phoneNumber : env.getProperty("sms.twilio.phone_number");
        accountSid = Objects.nonNull(accountSid) ? accountSid : env.getProperty("sms.twilio.account_sid");
        authToken = Objects.nonNull(authToken) ? authToken : env.getProperty("sms.twilio.auth_token");

        if (phoneNumber == null || accountSid == null || authToken == null) {
            throw new Exception("Credenciais invalidas.");
        }

        init(accountSid, authToken);

        this.setResponse(
                Message
                        .creator(new PhoneNumber(msisdn), new PhoneNumber(phoneNumber), textMessage)
                        .create()
        );
    }
}
