package joel.fsms.config.file.service;

import joel.fsms.config.file.domain.ImageBase64Request;
import joel.fsms.config.file.presentation.FileJson;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileService {
    /**
     * Uploads a given file e.g. my-awesome-document.pdf to a path, e.g. /var/project/uploads/{UUID}/{FILE_NAME}
     *
     * @param file
     * @return
     */
    FileJson upload(MultipartFile file);
    FileJson upload(ImageBase64Request file);
    List<FileJson> upload(List<ImageBase64Request> file);


    File getFile(String uuid, String fileName) throws FileNotFoundException;

    ResponseEntity<InputStreamResource> download(String path, boolean stream) throws IOException;

    ResponseEntity<byte[]> showImage(String path) throws IOException;
    Path move(String fromPath, String toPath) throws IOException;

    String getExtension(File file);
}
