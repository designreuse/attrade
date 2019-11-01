package by.attrade.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "server.path")
public class ServerPathConfig {
    private String absolute;
    private String upload;
    private String product;

    @PostConstruct
    public void init() throws IOException {
        createDirectories(absolute);
        createDirectories(absolute + upload);
        createDirectories(absolute + upload + product);
    }

    private void createDirectories(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}
