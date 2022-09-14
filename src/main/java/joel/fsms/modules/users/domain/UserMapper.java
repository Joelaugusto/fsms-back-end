package joel.fsms.modules.users.domain;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public abstract class UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract UserResponse toResponse(User user);
    public abstract void copyProprieties(UserRequest userRequest,@MappingTarget User user);
    public abstract void copyProprieties(UserCreateRequest userRequest,@MappingTarget User user);
    public abstract User toEntity(UserRequest userRequest);
    public Page<UserResponse> toResponse(Page<User> users) {
        return users.map(UserMapper.INSTANCE::toResponse);
    }
}
