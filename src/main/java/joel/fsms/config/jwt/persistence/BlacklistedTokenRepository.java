package joel.fsms.config.jwt.persistence;

import joel.fsms.config.jwt.domain.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken,String> {

    boolean existsByToken(String token);

    @Modifying
    @Query(value = "DELETE FROM blacklisted_tokens b WHERE b.created_at + interval '3 HOUR' < now() ", nativeQuery = true)
    void deleteExpiredTokens();


}


