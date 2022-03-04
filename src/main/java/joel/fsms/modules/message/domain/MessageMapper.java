package joel.fsms.modules.message.domain;

import joel.fsms.modules.users.domain.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public abstract class MessageMapper {

    public static final MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(source = "message.chat.id", target = "chatId")
    @Mapping(source = "message.sentBy.id", target = "sentById")
    @Mapping(source = "message.sentBy.name", target = "sentByName")
    @Mapping(source = "message.id", target = "id")
    public abstract MessageResponse toResponse(Message message, User loggedUser);


    @AfterMapping
    public void setReceived(@MappingTarget MessageResponse message, User loggedUser){

        message.setReceived(!message.getSentById().equals(loggedUser.getId()));

    }
    public abstract Message toMessage(MessageRequest messageRequest);

    public abstract void toMessage(MessageRequest request,@MappingTarget Message message);

    public Page<MessageResponse> toResponse(Page<Message> message, User user){
        return message.map(m -> toResponse(m, user));
    }

}
