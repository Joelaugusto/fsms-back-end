package joel.fsms.modules.posts.service;

import joel.fsms.modules.posts.domain.Post;
import joel.fsms.modules.posts.domain.PostQuery;
import joel.fsms.modules.posts.domain.PostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Post findByIdAndIncrementVisualizations(Long id);

    Post findById(Long id);

    Page<Post> findAll(PostQuery search, Pageable pageable);
    Post save(PostRequest request);
    Post save(PostRequest request, Long groupId);
    Post update(PostRequest request, Long id);
    void deleteById(Long id);

    Page<Post> findByGroupId(PostQuery search, Long groupId, Pageable pageable);
}
