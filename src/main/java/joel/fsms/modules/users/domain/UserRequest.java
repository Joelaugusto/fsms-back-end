package joel.fsms.modules.users.domain;

import joel.fsms.modules.address.domain.AddressRequest;
import lombok.Data;

@Data
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private String phone;
    private Long role;
    private AddressRequest address;






}
