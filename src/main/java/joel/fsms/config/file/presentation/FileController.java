package joel.fsms.config.file.presentation;

import io.swagger.annotations.Api;
import joel.fsms.config.file.domain.ImageBase64Request;
import joel.fsms.config.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/files", name = "Files")
@Api(tags = "File Management")
@RequiredArgsConstructor
@CrossOrigin
public class FileController {
    private final FileService fileService;

    @GetMapping("/**")
    public ResponseEntity<?> streamFile(HttpServletRequest request) throws IOException {
        String path = "uploads/"+extractPath(request);
        return fileService.showImage(path);
    }

    @GetMapping(value = "/download/{uuid}/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> downloadFile(@PathVariable String uuid, @PathVariable String fileName, HttpServletResponse response) throws IOException {
        String path = "uploads/" + uuid + "/" + fileName;
        return fileService.download(path, false);
    }

    @PostMapping
    public ResponseEntity<FileJson> uploadFile(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(fileService.upload(file));
    }

    private String extractPath(HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        AntPathMatcher apm = new AntPathMatcher();
        return apm.extractPathWithinPattern(bestMatchPattern, path);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<List<FileJson>> uploadImageBase64(@RequestBody List<ImageBase64Request> images) {
        return ResponseEntity.ok(fileService.upload(images));
    }
}
