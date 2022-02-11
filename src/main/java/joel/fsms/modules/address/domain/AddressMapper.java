package joel.fsms.modules.address.domain;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public abstract class AddressMapper {

    public static final AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
    public abstract AddressResponse toResponse(Address address);
    public abstract Address toEntity(AddressRequest addressRequest);
    public abstract void copyProperties(AddressRequest addressRequest,@MappingTarget Address address);
    public Page<AddressResponse> toResponse(Page<Address> addresses){
        return addresses.map(this::toResponse);
    }


}
