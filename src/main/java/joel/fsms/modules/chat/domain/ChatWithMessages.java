package joel.fsms.modules.chat.domain;

import joel.fsms.modules.message.domain.MessageResponse;
import joel.fsms.modules.users.domain.UserRole;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ChatWithMessages {

    private Long id;
    private String name;
    private UserRole userRole;
    private List<MessageResponse> message = new ArrayList<>();
}
