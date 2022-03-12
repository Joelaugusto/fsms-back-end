package joel.fsms.modules.posts.service;

import joel.fsms.modules.posts.domain.Post;
import joel.fsms.modules.posts.domain.PostMapper;
import joel.fsms.modules.posts.domain.PostRequest;
import joel.fsms.modules.posts.persistence.PostRepository;
import joel.fsms.modules.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostSpecification postSpecification;

    @Override
    public Post findById(Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "POST NOT FOUND!"));
    }

    @Override
    public Post findByIdAndIncrementVisualizations(Long id) {
        Post post = findById(id);
        post.setVisualizations(post.getVisualizations() + 1);
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Page<Post> findAll(String search, Pageable pageable) {
        return postRepository.findAll(postSpecification.executeQuery(search), pageable);
    }

    @Override
    public Post save(PostRequest request) {
        Post post = PostMapper.INSTANCE.mapToPost(request);
        post.setUser(loggedUser());
        return postRepository.save(post);
    }

    @Override
    public Post update(PostRequest request, Long id) {
        Post post = findById(id);
        PostMapper.INSTANCE.mapToPost(request, post);
        return postRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {
        if (postRepository.existsById(id)){
            postRepository.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "POST NOT FOUND!");
        }
    }

    private User loggedUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
