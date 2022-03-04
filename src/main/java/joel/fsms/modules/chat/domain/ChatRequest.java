package joel.fsms.modules.chat.domain;

import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {

    private String name;
    private List<Long> members;

}
