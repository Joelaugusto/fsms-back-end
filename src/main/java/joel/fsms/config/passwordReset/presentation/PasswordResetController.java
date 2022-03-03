package joel.fsms.config.passwordReset.presentation;

import joel.fsms.config.jwt.domain.LoginCommand;
import joel.fsms.config.passwordReset.domain.CreateTokenCommand;
import joel.fsms.config.passwordReset.service.PasswordResetServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/password-reset")
@CrossOrigin
public class PasswordResetController {

    private final PasswordResetServiceImpl passwordResetService;

    @PostMapping
    public ResponseEntity<Void> createToken(@Validated @RequestBody CreateTokenCommand command){
        passwordResetService.createResetToken(command.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{token}")
    public ResponseEntity<Void> resetPassword(@Validated @RequestBody LoginCommand command, @PathVariable String token){
        passwordResetService.resetPassword(token, command);
        return ResponseEntity.ok().build();
    }


}
