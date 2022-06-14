package joel.fsms.modules.message.domain;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public abstract class MessageMapper {

    public static final MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(source = "message.chat.id", target = "chatId")
    @Mapping(source = "message.sentBy.id", target = "sentById")
    @Mapping(source = "message.sentBy.name", target = "sentByName")
    @Mapping(source = "message.sentBy.profilePhotoUrl", target = "sendByProfilePhotoUrl")
    @Mapping(source = "message.id", target = "id")
    public abstract MessageResponse toResponse(Message message, Long loggedUser);


    @AfterMapping
    public void setReceived(@MappingTarget MessageResponse message, Long loggedUser){

        message.setReceived(!message.getSentById().equals(loggedUser));

    }
    public abstract Message toMessage(MessageRequest messageRequest);

    public abstract void toMessage(MessageRequest request,@MappingTarget Message message);

    public Page<MessageResponse> toResponse(Page<Message> message, Long user){
        return message.map(m -> toResponse(m, user));
    }

    public List<MessageResponse> toResponse(List<Message> message, Long user){
        return message.stream().map(m -> toResponse(m, user)).collect(Collectors.toList());
    }

}
