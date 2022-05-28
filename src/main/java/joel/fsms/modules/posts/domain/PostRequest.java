package joel.fsms.modules.posts.domain;

import joel.fsms.config.file.domain.ImageBase64Request;
import lombok.Data;

import java.util.List;

@Data
public class PostRequest {

    private String title;
    private String body;
    private List<ImageBase64Request> images;

}
