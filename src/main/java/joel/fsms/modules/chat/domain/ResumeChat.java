package joel.fsms.modules.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeChat {

    private Long id;
    private String name;
    private Long notViewed;
}
