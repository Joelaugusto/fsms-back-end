package joel.fsms.config.file.service;

import joel.fsms.config.file.FileSystem;
import joel.fsms.config.file.domain.ImageBase64Request;
import joel.fsms.config.file.presentation.FileJson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final Environment env;
    private final FileSystem fileSystem;

    @SneakyThrows
    @Override
    public FileJson upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IOException("Ficheiro inválido");
        }
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
        Path uploadPath = Paths.get(Optional.ofNullable(env.getProperty("app.upload_path")).orElse("storage/uploads"));

        // Make sure upload folder exists
        uploadPath.toFile().mkdirs();

        byte[] bytes = file.getBytes();

        // Generate a UUID and factory a folder with it to prevent file collision
        String uuid = UUID.randomUUID().toString();
        Path filePath = Paths.get(uploadPath.toString() + "/" + uuid);
        filePath.toFile().mkdirs();

        Path pathWithName = Paths.get(String.join("/", filePath.toString(), fileName));
        Files.write(pathWithName, bytes);

        String path = String.join("/", uuid, fileName);
        return FileJson.toJson(fileName, path);
    }

    @Override
    public FileJson upload(ImageBase64Request file) {

        String uploadPath = Optional.ofNullable(env.getProperty("app.upload_path")).orElse("storage/uploads");

        String[] strings = file.getDataURL().split(";",2);

        byte[] decodedBytes = Base64.getDecoder().decode(strings[1].split(",",2)[1]);

        String filePath = UUID.randomUUID()+"/"+file.getName();

        try {
            FileUtils.writeByteArrayToFile(new File(uploadPath+"/"+ filePath), decodedBytes);
        }catch (IOException exception){
            throw new RuntimeException("Failed when upload file");
        }

        FileJson fileJson = new FileJson();
        fileJson.setName(file.getName());
        fileJson.setPath(filePath);

        return fileJson;
    }

    @Override
    public List<FileJson> upload(List<ImageBase64Request> file) {
        return file.stream().map(this::upload).collect(Collectors.toList());
    }


    @Override
    public java.io.File getFile(String uuid, String fileName) throws FileNotFoundException {
        String uploadPath = Optional.ofNullable(env.getProperty("app.upload_path")).orElse("");
        String filePath = String.join("/",uploadPath, uuid, fileName);
        return new java.io.File(filePath);
    }

    public ResponseEntity<InputStreamResource> download(String path, boolean stream) throws IOException {
        return fileSystem.download(path, stream);
    }

    @Override
    public ResponseEntity<byte[]> showImage(String path) throws IOException {
        InputStreamResource resource = fileSystem.download(path,false).getBody();
        byte[] imageBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @Override
    public Path move(String fromPath, String toPath) throws IOException {
        return fileSystem.move(fromPath, toPath);
    }

    @Override
    public String getExtension(java.io.File file) {
        String[] ext = file.getName().split("\\.");
        return ext.length > 0 ? ext[ext.length - 1] : "";
    }
}
