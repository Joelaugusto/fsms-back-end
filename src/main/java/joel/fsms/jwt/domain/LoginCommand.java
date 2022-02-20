package joel.fsms.jwt.domain;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginCommand {
    @NotNull
    @ApiModelProperty(required = true, position = -1, example = "joelaugusto97@gmail.com")
    private String email;

    @NotNull
    @ApiModelProperty(required = true, example = "12345678")
    private String password;
}
