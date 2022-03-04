package joel.fsms.modules.message.domain;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageResponse {

    private Long id;

    private String message;

    private Long chatId;

    private String sentByName;

    private Long sentById;

    private Boolean received;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
