package joel.fsms.modules.chat.domain;

import joel.fsms.modules.users.domain.UserResponse;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatResponse {

    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserResponse createdBy;
}
