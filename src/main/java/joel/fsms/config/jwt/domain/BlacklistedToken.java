package joel.fsms.config.jwt.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "blacklisted_tokens")
public class BlacklistedToken {
    @Id
    private String token;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    public BlacklistedToken(String token) {
        this.token = token;
    }
}
