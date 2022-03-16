package joel.fsms.modules.posts.comments.domain;

import joel.fsms.modules.users.domain.UserResponse;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostCommentResponse {

    private Long id;
    private String comment;
    private Long postId;
    private UserResponse user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
