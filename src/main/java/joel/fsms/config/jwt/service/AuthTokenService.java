package joel.fsms.config.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import joel.fsms.config.jwt.domain.BlacklistedToken;
import joel.fsms.config.jwt.persistence.BlacklistedTokenRepository;
import joel.fsms.config.jwt.presentation.AuthTokenDto;
import joel.fsms.config.utils.Converter;
import joel.fsms.exceptions.AuthenticationException;
import joel.fsms.modules.users.domain.User;
import joel.fsms.modules.users.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class AuthTokenService implements ApplicationContextAware {
    public static final RuntimeException EX_INVALID_TOKEN = new AuthenticationException("Invalid Token.");
    public static final RuntimeException EX_EXPIRED_TOKEN = new AuthenticationException("The supplied Token has expired.");
    public static final RuntimeException EX_TOKEN_BLACKLISTED = new AuthenticationException("The supplied Token has been blacklisted.");
    private final Logger logger = Logger.getLogger(AuthTokenService.class.getName());
    private ApplicationContext context;
    private final UserServiceImpl userService;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final Environment env;

    public AuthTokenDto createToken(User user) {
        try {
            LocalDateTime validUntil = LocalDateTime.now().plusHours(3);
            Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(env.getProperty("auth.jwt-secret")));
            String token = JWT.create()
                    .withIssuer("FSMS")
                    .withKeyId(UUID.randomUUID().toString())
                    .withSubject(user.getId().toString())
                    .withExpiresAt(Converter.toDate(validUntil))
                    .withClaim("refreshUntil", Converter.toDate(LocalDateTime.now().plusHours(2)))
                    .sign(algorithm);
            return new AuthTokenDto(validUntil, token);
        } catch (JWTCreationException exception){
            logger.log(Level.INFO, "Invalid Signing configuration / Couldn't convert Claims");
        }

        return null;
    }

    public AuthTokenDto createToken(String username, String password) {
        Authentication auth = getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return createToken((User) auth.getPrincipal());
    }

    private AuthenticationManager getAuthenticationManager() {
        return context.getBean(AuthenticationManager.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public AuthTokenDto refresh(String token) {
        validateToken(token);
        try {
            DecodedJWT jwt = getDecodedJWT(token);
            Claim refreshUntil = jwt.getClaim("refreshUntil");
            if (refreshUntil.asDate().before(Calendar.getInstance().getTime())) throw EX_EXPIRED_TOKEN;
            return createToken(userService.findById(Long.parseLong(jwt.getSubject())));
        } catch (JWTVerificationException exception){
            logger.log(Level.INFO, "Invalid Signing configuration / Couldn't convert Claims");
        }
        throw EX_INVALID_TOKEN;
    }

    private void validateToken(String token) {
        if (blacklistedTokenRepository.existsByToken(token)) throw EX_TOKEN_BLACKLISTED;
        DecodedJWT jwt = getDecodedJWT(token);
        Claim refreshUntil = jwt.getClaim("refreshUntil");
        if (refreshUntil.asDate().before(Calendar.getInstance().getTime())) throw EX_EXPIRED_TOKEN;
    }

    private DecodedJWT getDecodedJWT(String token){
        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(env.getProperty("auth.jwt-secret")));
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("FSMS")
                .build();
        return verifier.verify(token);
    }

    public void invalidate(String token) {
        validateToken(token);
        blacklistedTokenRepository.save(new BlacklistedToken(token));
    }

    @Scheduled(cron = "0 0 0 1 * *")
    @Transactional
    public void cleanup() {
        blacklistedTokenRepository.deleteExpiredTokens();
    }
}
