package joel.fsms.config.passwordReset.domain;


import lombok.Data;

@Data
public class ResetPasswordCommand {

    private String email;
    private String password;
    private String confirmPassword;
}
