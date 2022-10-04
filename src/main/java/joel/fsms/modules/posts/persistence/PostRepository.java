package joel.fsms.modules.posts.persistence;

import joel.fsms.modules.posts.domain.Post;
import joel.fsms.modules.users.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Page<Post> findByOrderByCreatedAtDesc(Pageable pageable);

    List<Post> findByGroup_IdOrderByCreatedAtDesc(Long id);

    List<Post> findByUser(User user);







}
