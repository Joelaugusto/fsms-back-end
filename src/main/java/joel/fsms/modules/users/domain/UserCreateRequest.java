package joel.fsms.modules.users.domain;

import joel.fsms.modules.address.domain.AddressRequest;
import lombok.Data;

@Data
public class UserCreateRequest {

    private String name;
    private Long role;
    private AddressRequest address;
}
