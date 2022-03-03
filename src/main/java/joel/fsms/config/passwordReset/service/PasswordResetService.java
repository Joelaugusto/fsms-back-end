package joel.fsms.config.passwordReset.service;


import joel.fsms.config.jwt.domain.LoginCommand;

public interface PasswordResetService {

    void createResetToken(String email);
    void resetPassword(String token, LoginCommand command);
}
