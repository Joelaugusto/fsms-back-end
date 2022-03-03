package joel.fsms.config.jwt.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import joel.fsms.config.jwt.persistence.BlacklistedTokenRepository;
import joel.fsms.exceptions.AuthenticationException;
import joel.fsms.modules.users.domain.User;
import joel.fsms.modules.users.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final UserServiceImpl userService;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final Environment env;
    private final Logger logger = Logger.getLogger(AuthTokenFilter.class.getName());


    public static final RuntimeException EX_EXPIRED_TOKEN = new AuthenticationException("The supplied Token has expired.");
    public static final RuntimeException EX_TOKEN_BLACKLISTED = new AuthenticationException("The supplied Token has been blacklisted.");


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException, AuthenticationException {
        String apiKey = convert(httpServletRequest);
        if (apiKey == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        } else {
            validateToken(apiKey);
                try {
                    DecodedJWT jwt = getDecodedJWT(apiKey);
                    Long id = Long.parseLong(jwt.getSubject());
                    User principal = userService.findById(id);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, apiKey, principal.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (JWTVerificationException exception){
                    logger.log(Level.INFO, "Invalid Signing configuration / Couldn't convert Claims");
                } catch (Exception e) {
                SecurityContextHolder.clearContext();
                throw new AuthenticationException(e.getMessage());
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
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

    public static String convert(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.length()>7) {
            if (authorization.startsWith("Bearer")) return authorization.substring(7);
            return authorization;
        }
        return null;
    }
}
