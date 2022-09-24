package joel.fsms.modules.posts.service;

import joel.fsms.modules.groups.persistence.GroupRepository;
import joel.fsms.modules.posts.domain.Post;
import joel.fsms.modules.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSpecification {


    private final GroupRepository groupRepository;

    public Specification<Post> executeQuery(String query){

        Specification<Post> specification = findWithoutGroup().or(findPostOnMyGroup());

        if (query == null || query.isEmpty()) {
            return specification;
        }

        return (findByTitle(query).or(findByBody(query))).and(specification);
    }


    public Specification<Post> findByTitle(String title){

        return (root, query, cb) -> cb.like(cb.upper(root.get("title")), "%"+title.toUpperCase()+"%");
    }

    public Specification<Post> findByBody(String body){
        return (root, query, cb) -> cb.like(cb.upper(root.get("body")), "%"+body.toUpperCase()+"%");
    }

    public Specification<Post> findByGroupId(Long groupId){
        return (root, query, cb) -> cb.equal(root.get("group").get("id"), groupId);
    }

    private Specification<Post> findWithoutGroup() {
        return (root, query, cb) -> cb.isNull(root.get("group"));
    }

    private Specification<Post> findPostOnMyGroup() {
        return (root, query, cb) -> root.get("group").get("id").in(groupRepository.findIdByUserId(loggedUser().getId()));
    }

    private User loggedUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
