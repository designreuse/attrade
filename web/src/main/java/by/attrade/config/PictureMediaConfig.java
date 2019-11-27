package by.attrade.config;

import by.attrade.domain.dto.PictureMediaDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "picture.media")
public class PictureMediaConfig {
    private boolean turnOn;
    private boolean unknownAutoRecognize;
    private String unknownAutoImageType;
    private boolean removeNotSynchronized;
    private boolean overwriteAll;
    private List<String> paths = new ArrayList<>();
    private List<String> medias = new ArrayList<>();
    private List<Double> compressionWidthPercents = new ArrayList<>();

    public List<PictureMediaDTO> getPictureMedias() {
        List<PictureMediaDTO> list = new ArrayList<>();
        int size = paths.size();
        for (int i = 0; i < size; i++) {
            list.add(new PictureMediaDTO(paths.get(i), medias.get(i), compressionWidthPercents.get(i)));
        }
        return list;
    }
}
