package joel.fsms.modules.message.domain;

import lombok.Data;

@Data
public class MessageRequest {

    private String message;
    private Long chatId;
}
