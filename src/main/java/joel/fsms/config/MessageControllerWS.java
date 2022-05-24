package joel.fsms.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import joel.fsms.config.jwt.configuration.AuthTokenFilter;
import joel.fsms.config.jwt.persistence.BlacklistedTokenRepository;
import joel.fsms.exceptions.AuthenticationException;
import joel.fsms.modules.message.domain.MessageRequestWS;
import joel.fsms.modules.message.domain.MessageResponse;
import joel.fsms.modules.message.service.MessageServiceImpl;
import joel.fsms.modules.users.domain.User;
import joel.fsms.modules.users.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.Calendar;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class MessageControllerWS {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageServiceImpl messageService;
    private final UserServiceImpl userService;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final Environment env;
    private final Logger logger = Logger.getLogger(AuthTokenFilter.class.getName());


    public static final RuntimeException EX_EXPIRED_TOKEN = new AuthenticationException("The supplied Token has expired.");
    public static final RuntimeException EX_TOKEN_BLACKLISTED = new AuthenticationException("The supplied Token has been blacklisted.");


    @MessageMapping("/messages-all")
    @SendTo("/topic/messages")
    public MessageResponse receiveMessage(@Payload Map<String, String> message){

        check(message.get("apiKey"));

        MessageRequestWS messageRequestWS =
                new MessageRequestWS(message.get("message"), Long.parseLong(message.get("chatId")));

        return messageService.save(messageRequestWS);
    }

    @MessageMapping("/private-message")
    public MessageResponse recMessage(@Payload MessageResponse message){
        simpMessagingTemplate.convertAndSendToUser(message.getSentById().toString(),"/private",message);
        return message;
    }


    private void check(String apiKey){
            validateToken(apiKey);
            try {
                DecodedJWT jwt = getDecodedJWT(apiKey);
                Long id = Long.parseLong(jwt.getSubject());
                User principal = userService.findById(id);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, apiKey, principal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (JWTVerificationException exception){
                logger.log(Level.INFO, "Invalid Signing configuration / Couldn't convert Claims");
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                throw new AuthenticationException(e.getMessage());
            }
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

}
