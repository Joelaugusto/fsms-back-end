package joel.fsms.modules.address.domain;

import lombok.Data;

@Data
public class AddressResponse {

    private Long id;
    private String district;
    private String locality;
    private Float latitude;
    private Float longitude;
    private Province province;
}
