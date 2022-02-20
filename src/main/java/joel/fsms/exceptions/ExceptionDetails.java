package joel.fsms.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionDetails {
    private Integer status;
    private String message;
}
