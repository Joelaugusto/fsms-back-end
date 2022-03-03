package joel.fsms.config.passwordReset.domain;

import lombok.Data;

@Data
public class CreateTokenCommand {

    private String email;
}
