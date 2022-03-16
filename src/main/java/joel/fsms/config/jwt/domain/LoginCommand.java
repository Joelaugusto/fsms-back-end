package joel.fsms.config.jwt.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class LoginCommand {
    @ApiModelProperty(required = true, position = -1, example = "joelaugusto97@gmail.com")
    private String email;

    @ApiModelProperty(required = true, example = "string")
    private String password;
}
