package joel.fsms.modules.posts.domain;

import lombok.Data;

@Data
public class PostQuery {

    private String query;
    private Boolean onlyCreatedByMe;
}
