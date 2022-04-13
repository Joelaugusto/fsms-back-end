package joel.fsms.modules.groups.domain;

import joel.fsms.modules.chat.domain.Chat;
import joel.fsms.modules.chat.domain.ChatResponse;
import joel.fsms.modules.posts.domain.Post;
import joel.fsms.modules.posts.domain.PostResponse;
import joel.fsms.modules.users.domain.User;
import joel.fsms.modules.users.domain.UserResponse;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
public class GroupResponse {

    private Long id;
    private String name;
    private ChatResponse chat;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
