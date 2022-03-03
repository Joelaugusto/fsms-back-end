package joel.fsms.modules.users.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUniqueConstraints {

    private String email;
    private String phone;
}
