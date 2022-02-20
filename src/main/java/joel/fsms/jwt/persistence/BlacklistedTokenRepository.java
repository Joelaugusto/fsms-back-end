package joel.fsms.jwt.persistence;

import joel.fsms.jwt.domain.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken,String> {

    boolean existsByToken(String token);

    @Modifying
    @Query(value = "DELETE FROM blacklisted_token b WHERE b.created_at + interval '3 HOUR' < now() ", nativeQuery = true)
    void deleteExpiredTokens();


}


