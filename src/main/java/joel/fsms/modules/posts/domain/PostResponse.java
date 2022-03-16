package joel.fsms.modules.posts.domain;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostResponse {

    private Long id;
    private String title;
    private String body;
    private Long visualizations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
