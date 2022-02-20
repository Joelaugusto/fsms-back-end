package joel.fsms.jwt.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import joel.fsms.jwt.config.AuthTokenFilter;
import joel.fsms.jwt.domain.LoginCommand;
import joel.fsms.jwt.service.AuthTokenService;
import joel.fsms.modules.users.domain.User;
import joel.fsms.modules.users.domain.UserMapper;
import joel.fsms.modules.users.domain.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@Api(tags = "Authentication")
@RequestMapping("/api/users/auth")
public class AuthenticationController {
    private final AuthTokenService tokenService;

    @PostMapping
    @ApiOperation(value = "Create authentication token.")
    public AuthTokenDto createToken(@RequestBody @Validated LoginCommand request) {
        return tokenService.createToken(request.getEmail(), request.getPassword());
    }

    @PatchMapping
    @ApiOperation(value = "Create a fresh auth token and invalidates the old one.")
    public AuthTokenDto refreshToken(HttpServletRequest request) {
        String token = AuthTokenFilter.convert(request);
        return tokenService.refresh(token);
    }

    @DeleteMapping
    @ApiOperation(value = "Invalidates the access token")
    public void invalidate() {
        if (SecurityContextHolder.getContext().getAuthentication().getCredentials() instanceof String) {
            tokenService.invalidate((String) SecurityContextHolder.getContext().getAuthentication().getCredentials());
            return;
        }
        throw AuthTokenService.EX_INVALID_TOKEN;
    }

    @GetMapping("/me")
    @ApiOperation(value = "Retrieve authenticated user details", authorizations = {})
    public UserResponse getLoggedUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return UserMapper.INSTANCE.toResponse((User) authentication.getPrincipal());
    }
}
