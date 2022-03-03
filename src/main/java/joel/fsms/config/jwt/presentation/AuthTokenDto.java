package joel.fsms.config.jwt.presentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class AuthTokenDto {
    public String accessToken;

    @Setter(AccessLevel.PRIVATE)
    public String type = "Bearer";

    @JsonProperty("tokenTtl")
    private Long tokenTtl;

    public AuthTokenDto(LocalDateTime validUntil, String accessToken) {
        this.accessToken = accessToken;
        tokenTtl = ChronoUnit.MILLIS.between(LocalDateTime.now(), validUntil);
    }
}
