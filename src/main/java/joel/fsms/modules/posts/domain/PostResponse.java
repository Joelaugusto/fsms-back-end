package joel.fsms.modules.posts.domain;
import joel.fsms.config.file.presentation.FileJson;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {

    private Long id;
    private String title;
    private String body;
    private Long visualizations;
    private String username;
    private Long userId;
    private Integer comments;
    private List<FileJson> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
