package joel.fsms.modules.posts.comments.domain;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public abstract class PostCommentMapper {

    public static final PostCommentMapper MAPPER = Mappers.getMapper(PostCommentMapper.class);

    @Mapping(source = "post.id", target = "postId")
    @Mapping(target = "user.role", source = "user.role.name")
    public abstract PostCommentResponse toResponse(PostComment postComment);
    public Page<PostCommentResponse> toResponse(Page<PostComment> comments){
        return comments.map(this::toResponse);
    }
}
