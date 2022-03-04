package joel.fsms.modules.chat.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public abstract class ChatMapper {

    public static final ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    public abstract ChatResponse toResponse(Chat chat);

    @Mapping(target = "members", ignore = true)
    public abstract Chat toChat(ChatRequest chatRequest);

    @Mapping(target = "members", ignore = true)
    public abstract void toChat(ChatRequest request,@MappingTarget Chat chat);

    public List<ChatResponse> toResponse(List<Chat> chat){
        return chat.stream().map(this::toResponse).collect(Collectors.toList());
    }

}
