package joel.fsms.modules.posts.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mapper
public abstract class PostMapper {

    public static final PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    public abstract Post mapToPost(PostRequest request);
    public abstract void mapToPost(PostRequest request,@MappingTarget Post post);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "username")
    @Mapping(source = "user.profilePhotoUrl", target = "userProfilePhotoUrl")
    public abstract PostResponse mapToResponse(Post post);
    public Page<PostResponse> mapToResponse(Page<Post> posts){
        return posts.map(this::mapToResponse);
    }
}
