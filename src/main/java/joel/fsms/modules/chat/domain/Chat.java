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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @Column(name = "user_id")
    private User createdBy;

    @ManyToMany
    @JoinTable(name = "chats_chats",
            joinColumns = @JoinColumn(name = "chat_1_id"),
            inverseJoinColumns = @JoinColumn(name = "chats_2_id"))
    private Set<Chat> chats = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "chats_users",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<User> users = new LinkedHashSet<>();

    @OneToMany(mappedBy = "chat", orphanRemoval = true)
    private List<Message> message = new ArrayList<>();

}
