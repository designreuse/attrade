package by.attrade.service;

import by.attrade.config.PictureMediaConfig;
import by.attrade.config.ServerPathConfig;
import by.attrade.domain.dto.PictureMediaDTO;
import by.attrade.service.pictureResizer.AwtPictureResizerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Slf4j
public class PictureMediaService {
    @Autowired
    private PictureMediaConfig pictureMediaConfig;

    @Autowired
    private ServerPathConfig serverPathConfig;

    @Autowired
    private AwtPictureResizerService pictureResizerService;


    @PostConstruct
    public void init() throws IOException {
        if (pictureMediaConfig.isTurnOn()) {
            List<PictureMediaDTO> pictureMedias = pictureMediaConfig.getPictureMedias();
            String uploadPicture = serverPathConfig.getAbsolute() + serverPathConfig.getPicture();
            processUploadPictures(pictureMedias, uploadPicture);
        }
    }

    private void processUploadPictures(List<PictureMediaDTO> pictureMedias, String mainPath) throws IOException {
        for (PictureMediaDTO p : pictureMedias) {
            String childPath = mainPath + p.getPath();
            createDirectories(childPath);
            copyAndSqueezePictures(mainPath, childPath, p.getCompressionPercent());
            removeNotSynchronized(mainPath, childPath);
        }
    }

    private void removeNotSynchronized(String main, String child) {
        if (pictureMediaConfig.isRemoveNotSynchronized()) {
            Path mainPath = Paths.get(main);
            Path childPath = Paths.get(child);
            walkAndRemoveIfNotExistsInMain(mainPath, childPath);
        }
    }

    private void walkAndRemoveIfNotExistsInMain(Path mainPath, Path childPath) {
        try {
            Set<Path> fileNames = Files.list(mainPath).map(Path::getFileName).collect(Collectors.toSet());
            Files
                    .walk(childPath)
                    .filter(path -> !fileNames.contains(path.getFileName()) && Files.isRegularFile(path))
                    .forEach(this::deletePath);
        } catch (IOException e) {
            log.error("Files operations with: " + mainPath + " / " + childPath, e);
        }
    }

    private void deletePath(Path p) {
        try {
            Files.delete(p);
        } catch (IOException e) {
            log.error("Delete path: " + p, e);
        }
    }

    private void copyAndSqueezePictures(String mainPath, String targetPath, double compressionPercent) throws IOException {
        Files
                .walk(Paths.get(mainPath), 1)
                .forEach(source -> copyResizePicture(source, targetPath, compressionPercent));
    }

    private void copyResizePicture(Path source, String targetPath, double compressionPercent) {
        if (Files.isRegularFile(source)) {
            if (pictureMediaConfig.isUnknownAutoRecognize() && isPictureWrongType(source)) {
                try {
                    source = rename(source);
                } catch (IOException e) {
                    log.error("Rename source: " + source, e);
                    return;
                }
            }
            String stringTarget = targetPath + File.separator + source.getFileName();
            Path target = Paths.get(stringTarget);
            boolean exists = Files.exists(target);
            if (!exists) {
                copyPicture(source, target);
            }
            if (!exists || pictureMediaConfig.isOverwriteAll()) {
                resizePicture(source, target, compressionPercent);
            }
        }
    }

    private Path rename(Path source) throws IOException {
        String str = source.toString();
        int i = str.lastIndexOf(".");
        str = str.substring(0, i + 1) + pictureMediaConfig.getUnknownAutoImageType();
        Path target = Paths.get(str);
        Files.move(source, target);
        return target;
    }

    private boolean isPictureWrongType(Path source) {
        String str = source.toString();
        String suffix = str.substring(str.lastIndexOf(".") + 1);
        String[] readerFileSuffixes = ImageIO.getReaderFileSuffixes();
        List<String> list = Arrays.asList(readerFileSuffixes);
        return !list.contains(suffix);
    }

    private void resizePicture(Path source, Path target, double compressionPercent) {
        try {
            double ratio = compressionPercent / 100;
            pictureResizerService.resize(source.toString(), target.toString(), ratio);
        } catch (IOException e) {
            log.error("Resize: " + target, e);
        }
    }

    private void copyPicture(Path source, Path target) {
        try {
            Files.copy(source, target, NOFOLLOW_LINKS, REPLACE_EXISTING, COPY_ATTRIBUTES);
        } catch (IOException e) {
            log.error("Copy source: " + source + " - target: " + target, e);
        }
    }

    private void createDirectories(String path) throws IOException {
        serverPathConfig.createDirectories(path);
    }
}
