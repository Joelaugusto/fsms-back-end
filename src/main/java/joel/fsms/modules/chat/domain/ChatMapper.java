package joel.fsms.modules.chat.domain;

import joel.fsms.modules.message.domain.MessageMapper;
import joel.fsms.modules.users.domain.User;
import org.mapstruct.AfterMapping;
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

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "message", ignore = true)
    public abstract ChatWithMessages toChatWithMessages(Chat chat, Long userId);

    @AfterMapping
    public void mapName(Chat chat,@MappingTarget ChatWithMessages chatWithMessages, Long userId){
        if (chat.getMembers().size() == 2){
            for (User user : chat.getMembers()){
                if(!user.getId().equals(userId)){
                    chatWithMessages.setName(user.getName());
                    chatWithMessages.setRole(user.getRole());
                    chatWithMessages.setMessage(MessageMapper.INSTANCE
                            .toResponse(chat.getMessage(), userId));
                    break;
                }
            }
        }else{
            chatWithMessages.setMessage(MessageMapper.INSTANCE
                    .toResponse(chat.getMessage(), userId));
        }
    }

    public List<ChatResponse> toResponse(List<Chat> chat){
        return chat.stream().map(this::toResponse).collect(Collectors.toList());
    }

}
