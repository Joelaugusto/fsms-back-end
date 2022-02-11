package joel.fsms.modules.users.domain;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private UserRole role;
}
