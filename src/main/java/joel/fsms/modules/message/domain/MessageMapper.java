package joel.fsms.modules.message.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public abstract class MessageMapper {

    public static final MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(source = "chat.id", target = "chatId")
    @Mapping(source = "sentBy.id", target = "sentById")
    @Mapping(source = "sentBy.name", target = "sentByName")
    public abstract MessageResponse toResponse(Message message);

    public abstract Message toMessage(MessageRequest messageRequest);

    public abstract void toMessage(MessageRequest request,@MappingTarget Message message);

    public Page<MessageResponse> toResponse(Page<Message> message){
        return message.map(this::toResponse);
    }

}
