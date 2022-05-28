package joel.fsms.config.file.configuration;

import joel.fsms.config.file.FileSystem;
import joel.fsms.config.file.LocalFileSystem;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class FileSystemConfiguration {
  private final Environment env;

  @Bean
  FileSystem fileSystem() {
    String storagePath = env.getProperty("app.storage.path", "storage");
    return LocalFileSystem.of(storagePath);
  }
}
