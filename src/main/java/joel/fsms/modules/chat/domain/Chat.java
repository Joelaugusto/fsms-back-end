package joel.fsms.modules.chat.domain;

import joel.fsms.modules.message.domain.Message;
import joel.fsms.modules.users.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "chats")
@Getter
@Setter
@ToString
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "chat", orphanRemoval = true)
    private List<Message> message = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "chats_users",
            joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> members = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

}
