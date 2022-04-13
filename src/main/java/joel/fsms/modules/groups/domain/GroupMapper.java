package joel.fsms.modules.groups.domain;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public abstract class GroupMapper {


    public static final GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    public abstract GroupResponse toResponse(Group group);
    public abstract Group toEntity(CreateGroupRequest request);
    public abstract void copyProprieties(UpdateGroupRequest request,@MappingTarget Group group);
    public List<GroupResponse> toResponse(List<Group> groups){
        return groups.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
