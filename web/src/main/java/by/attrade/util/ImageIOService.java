package by.attrade.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ImageIOService {
    public boolean isPictureUnknownType(Path source) {
        String str = source.toString();
        String suffix = str.substring(str.lastIndexOf(".") + 1);
        String[] readerFileSuffixes = ImageIO.getReaderFileSuffixes();
        List<String> list = Arrays.asList(readerFileSuffixes);
        return !list.contains(suffix);
    }
    public Path renameImageTypeTo(Path source, String imageType) {
        String str = source.toString();
        int i = str.lastIndexOf(".");
        str = str.substring(0, i + 1) + imageType;
        Path target = Paths.get(str);
        try {
            Files.move(source, target);
        } catch (IOException e) {
            log.error("Cannot move file: " + source + " to: " + target);
        }
        return target;
    }
}
