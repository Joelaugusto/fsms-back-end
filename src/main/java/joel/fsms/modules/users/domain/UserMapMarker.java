package joel.fsms.modules.users.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserMapMarker {

    private Long id;
    private String name;
    private UserRole role;
    private Float latitude;
    private Float longitude;
    private String profileUrl;

}
