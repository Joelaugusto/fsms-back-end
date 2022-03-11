package joel.fsms.modules.posts.service;

import joel.fsms.modules.posts.domain.Post;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PostSpecification {

    public Specification<Post> executeQuery(String query){
        return findByBody(query).or(findByTitle(query));
    }


    public Specification<Post> findByTitle(String title){
        return (root, query, cb) -> title != null ?
                cb.like(cb.upper(root.get("title")), "%"+title.toUpperCase()+"%"): cb.and();
    }

    public Specification<Post> findByBody(String body){
        return (root, query, cb) -> body != null ?
                cb.like(cb.upper(root.get("body")), "%"+body.toUpperCase()+"%") : cb.and();
    }
}
