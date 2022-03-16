package joel.fsms.modules.posts.comments.persistence;

import joel.fsms.modules.posts.comments.domain.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    Page<PostComment> findByPost_Id(Long id, Pageable pageable);
    Optional<PostComment> findByIdAndPost_Id(Long id, Long id1);
    boolean existsByIdAndPost_Id(Long id, Long id1);
    
    
}
