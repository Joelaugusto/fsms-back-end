package joel.fsms.modules.groups.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public abstract class GroupMapper {


    public static final GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    @Mapping(target = "chat.createdBy.role", ignore = true)
    public abstract GroupResponse toResponse(Group group);
    public abstract Group toEntity(CreateGroupRequest request);
    public abstract void copyProprieties(UpdateGroupRequest request,@MappingTarget Group group);
    public List<GroupResponse> toResponse(List<Group> groups){
        return groups.stream().map(this::toResponse).collect(Collectors.toList());
    }
    public Page<GroupResponse> toResponse(Page<Group> groups){
        return groups.map(this::toResponse);
    }
}
