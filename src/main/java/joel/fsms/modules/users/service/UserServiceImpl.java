package joel.fsms.modules.users.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import joel.fsms.config.jwt.presentation.AuthTokenDto;
import joel.fsms.config.jwt.service.AuthTokenService;
import joel.fsms.config.notification.EMAIL.Notifiable;
import joel.fsms.config.utils.Converter;
import joel.fsms.modules.address.service.AddressServiceImpl;
import joel.fsms.modules.users.domain.*;
import joel.fsms.modules.users.notification.EmailVerification;
import joel.fsms.modules.users.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static joel.fsms.config.jwt.service.AuthTokenService.EX_INVALID_TOKEN;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressServiceImpl addressService;
    private final AuthTokenService authTokenService;
    private final Environment env;
    private final Logger logger = Logger.getLogger(AuthTokenService.class.getName());


    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"USER_NOT_FOUND"));
    }

    @Override
    public User update(Long id, UserRequest userRequest) {
        User user = findById(id);
        UserMapper.INSTANCE.copyProprieties(userRequest, user);
        return userRepository.save(user);
    }

    @Override
    public User create(UserRequest userRequest) {

        if(userRepository.existsByEmail(userRequest.getEmail())){
            throw new ResponseStatusException(HttpStatus.FOUND, "O Email já é usado!");
        }

        if(userRepository.existsByPhone(userRequest.getPhone())){
            throw new ResponseStatusException(HttpStatus.FOUND, "O numero de telefone já é usado!");
        }

        User user = UserMapper.INSTANCE.toEntity(userRequest);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setAddress(addressService.save(userRequest.getAddress()));
        return userRepository.save(user);
    }

    @Override
    public AuthTokenDto create(UserCreateRequest userRequest, Long id) {
        User user = findById(id);
        UserMapper.INSTANCE.copyProprieties(userRequest,user);
        user.setAddress(addressService.save(userRequest.getAddress()));
        return authTokenService.createToken(userRepository.save(user));

    }

    @Override
    public void delete(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"USER_NOT_FOUND");
        }

    }

    @Override
    public Page<User> findAll(Pageable pageable, UserQuery userQuery) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Map<String, Boolean> verifyIfExists(UserUniqueConstraints uniqueConstraints) {
        return Map.of("email", userRepository.existsByEmail(uniqueConstraints.getEmail()),
                "phone", userRepository.existsByPhone(uniqueConstraints.getPhone()));
    }

    @Override
    public void sendEmailVerification(UserUniqueConstraints uniqueConstraints) {
        byte deadlineMinutes = 15;

        String code =  (100000 + (int)(Math.random() * 999999)+"").substring(0,6);

        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(env.getProperty("auth.jwt-secret")));
        String token = JWT.create()
                .withIssuer("FSMS")
                .withKeyId(UUID.randomUUID().toString())
                .withSubject(uniqueConstraints.getEmail())
                .withExpiresAt(Converter.toDate(LocalDateTime.now().plusMinutes(deadlineMinutes)))
                .withClaim("code", code+"")
                .withClaim("password",new BCryptPasswordEncoder().encode(uniqueConstraints.getPassword()))
                .withClaim("email", uniqueConstraints.getEmail())
                .withClaim("phone", uniqueConstraints.getPhone())
                .sign(algorithm);

        Notifiable.of(uniqueConstraints.getEmail())
                .sendNotification(new EmailVerification(code, deadlineMinutes+"", token, env));
    }

    @Override
    public List<UserMapMarker> findAllMapMarkers() {
        return userRepository.findAllMapMarker();
    }

    public User verifyAccount(String token){
        try {
            DecodedJWT jwt = getDecodedJWT(token);

            Map<String, Boolean> map = verifyIfExists(new UserUniqueConstraints(jwt.getClaim("email").asString(),
                    jwt.getClaim("phone").asString(),null));

            if(map.get("phone") || map.get("email")){
                return userRepository.findByEmailOrPhone(jwt.getClaim("email").asString(),jwt.getClaim("phone").asString())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            }
            User user = new User();
            user.setPassword(jwt.getClaim("password").asString());
            user.setEmail(jwt.getClaim("email").asString());
            user.setPhone(jwt.getClaim("phone").asString());
            return userRepository.save(user);

        } catch (JWTVerificationException exception){
            logger.log(Level.INFO, "Invalid Signing configuration / Couldn't convert Claims");
        }
        throw EX_INVALID_TOKEN;
    }

    private DecodedJWT getDecodedJWT(String token){
        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(env.getProperty("auth.jwt-secret")));
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("FSMS")
                .build();
        return verifier.verify(token);
    }
}
