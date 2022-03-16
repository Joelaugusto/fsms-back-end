package joel.fsms.modules.posts.comments.service;

import joel.fsms.modules.posts.comments.domain.PostComment;
import joel.fsms.modules.posts.comments.domain.PostCommentRequest;
import joel.fsms.modules.posts.comments.persistence.PostCommentRepository;
import joel.fsms.modules.posts.service.PostServiceImpl;
import joel.fsms.modules.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository repository;
    private final PostServiceImpl postService;

    @Override
    public Page<PostComment> findAll(Pageable pageable, Long postId) {
        return repository.findByPost_Id(postId, pageable);
    }

    @Override
    public PostComment save(PostCommentRequest request, Long postId) {
        PostComment comment = new PostComment();
        comment.setComment(request.getComment());
        comment.setPost(postService.findById(postId));
        comment.setUser(loggedUser());
        return repository.save(comment);
    }

    @Override
    public PostComment findById(Long id, Long postId) {
        return repository.findByIdAndPost_Id(id, postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "COMMENT NOT FOUND"));
    }

    @Override
    public PostComment update(PostCommentRequest request, Long id, Long postId) {
        PostComment comment = findById(id, postId);
        comment.setComment(request.getComment());
        return repository.save(comment);
    }

    @Override
    public void deleteById(Long id, Long postId) {
        if(repository.existsByIdAndPost_Id(id, postId)){
            repository.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "COMMENT NOT FOUND");
        }
    }

    private User loggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
