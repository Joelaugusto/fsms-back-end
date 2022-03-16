package joel.fsms.modules.posts.comments.service;

import joel.fsms.modules.posts.comments.domain.PostComment;
import joel.fsms.modules.posts.comments.domain.PostCommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCommentService {

    Page<PostComment> findAll(Pageable pageable, Long postId);
    PostComment save(PostCommentRequest request, Long postId);
    PostComment findById(Long id, Long postId);
    PostComment update(PostCommentRequest request,Long id, Long postId);
    void deleteById(Long id, Long postId);
}
