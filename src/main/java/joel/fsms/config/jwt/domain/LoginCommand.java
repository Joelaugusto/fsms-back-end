package joel.fsms.config.jwt.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class LoginCommand {
    @ApiModelProperty(required = true, position = -1, example = "admin")
    private String email;

    @ApiModelProperty(required = true, example = "secret")
    private String password;
}
