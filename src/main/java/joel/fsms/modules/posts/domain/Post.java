package joel.fsms.modules.posts.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import joel.fsms.config.file.presentation.FileJson;
import joel.fsms.config.utils.ListToStringConverter;
import joel.fsms.modules.configuration.ListFileJson;
import joel.fsms.modules.groups.domain.Group;
import joel.fsms.modules.posts.comments.domain.PostComment;
import joel.fsms.modules.users.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "posts")
@Entity
@Getter
@Setter
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "body")
    private String body;

    @Column(name = "visualizations")
    private Long visualizations;

    @Lob
    @Column(name = "videos_link")
    @Convert(converter = ListToStringConverter.class)
    private List<String> videosLink = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonBackReference
    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<PostComment> postComment = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Lob
    @Column(name = "images")
    @Convert(converter = ListFileJson.class)
    private List<FileJson> images;

    @PrePersist
    public void prePersist() {
        this.setVisualizations(0L);
    }
}
