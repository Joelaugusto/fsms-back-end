package joel.fsms.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException exception) {
        ExceptionDetails data = new ExceptionDetails();
        data.setStatus(BAD_REQUEST.value());
        data.setMessage(exception.getMessage());
        return new ResponseEntity<>(data, BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException exception) {
        ExceptionDetails data = new ExceptionDetails();
        data.setStatus(UNAUTHORIZED.value());
        data.setMessage(exception.getMessage());
        return new ResponseEntity<>(data, UNAUTHORIZED);
    }
}
