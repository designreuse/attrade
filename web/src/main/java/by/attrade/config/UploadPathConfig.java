package by.attrade.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "upload")
public class UploadPathConfig {
    private Path path;
    @PostConstruct
    public void init() throws IOException {
        if (!Files.exists(path)){
            Files.createDirectories(path);
        }
    }
}
