package joel.fsms.config.passwordReset.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import joel.fsms.config.jwt.domain.BlacklistedToken;
import joel.fsms.config.jwt.domain.LoginCommand;
import joel.fsms.config.jwt.persistence.BlacklistedTokenRepository;
import joel.fsms.config.notification.EMAIL.Notifiable;
import joel.fsms.config.passwordReset.notification.SendResetPasswordNotification;
import joel.fsms.config.utils.Converter;
import joel.fsms.modules.users.domain.User;
import joel.fsms.modules.users.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final Environment env;
    private final UserRepository userRepository;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final Logger logger = Logger.getLogger(PasswordResetServiceImpl.class.getName());

    @Override
    public void createResetToken(String username) {

        byte deadlineMinutes = 15;

        User user = userRepository.findByEmailOrPhone(username, username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));

        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(env.getProperty("auth.jwt-password-reset")));
        String token = JWT.create()
                .withIssuer("FSMS")
                .withKeyId(UUID.randomUUID().toString())
                .withSubject(username)
                .withExpiresAt(Converter.toDate(LocalDateTime.now().plusMinutes(deadlineMinutes)))
                .sign(algorithm);

        Notifiable.of(user.getEmail()).sendNotification(new SendResetPasswordNotification(Byte.toString(deadlineMinutes), token, env));
    }

    @Override
    public void resetPassword(String token, LoginCommand command) {

        if(blacklistedTokenRepository.existsByToken(token)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"The token is already used");
        }

        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(env.getProperty("auth.jwt-password-reset")));

        try {
            String email = JWT.require(algorithm).withIssuer("FSMS").build().verify(token).getSubject();

            if(!email.equalsIgnoreCase(command.getEmail())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "INVALID EMAIL");
            }

            if(email != null && email.equalsIgnoreCase(command.getEmail())){
                updatePassword(email, command.getPassword());
            }
            blacklistedTokenRepository.save(new BlacklistedToken(token));
        } catch (JWTVerificationException exception){
            logger.log(Level.INFO, "Invalid Signing configuration");
        }
    }

    private void updatePassword(String username, String password){
        User user = userRepository.findByEmailOrPhone(username, username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND"));
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userRepository.save(user);
    }
}
