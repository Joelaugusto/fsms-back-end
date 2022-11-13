package joel.fsms.modules.users.domain;

import joel.fsms.modules.address.domain.AddressResponse;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private AddressResponse address;
    private String profilePhotoUrl;
}
