package by.attrade.service;

import by.attrade.config.PictureMediaConfig;
import by.attrade.config.ServerPathConfig;
import by.attrade.domain.dto.PictureMediaDTO;
import by.attrade.service.pictureResizer.AwtPictureResizerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        if (pictureMediaConfig.isInit()) {
            processUploadPictures();
        }
    }

    private void processUploadPictures() throws IOException {
        createDirectories();
        removeNotSynchronized();
        copyAndSqueezePictures();
    }

    private void removeNotSynchronized() {
        if (pictureMediaConfig.isRemoveNotSynchronized()) {
            List<PictureMediaDTO> pictureMedias = pictureMediaConfig.getPictureMedias();
            for (PictureMediaDTO p : pictureMedias) {
                String child = getMainPath() + p.getPath();

                Path mainPath = Paths.get(getMainPath());
                Path childPath = Paths.get(child);
                walkAndRemoveIfNotExistsInMain(mainPath, childPath);
            }
        }
    }

    private String getMainPath() {
        return serverPathConfig.getAbsolute() + serverPathConfig.getPicture();
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

    private void copyAndSqueezePictures() throws IOException {
        Files
                .walk(Paths.get(getMainPath()), 1)
                .filter(Files::isRegularFile)
                .forEach(this::createResizedPictures);
    }

    private void createResizedPictures(Path source) {
        List<PictureMediaDTO> pictureMedias = pictureMediaConfig.getPictureMedias();
        for (PictureMediaDTO p : pictureMedias) {
            String targetPath = getMainPath() + p.getPath();
//            if (pictureMediaConfig.isUnknownAutoRecognize() && pictureMediaConfig.isPictureWrongType(source)) {
//                try {
//                    source = pictureMediaConfig.rename(source);
//                } catch (IOException e) {
//                    log.error("Rename source: " + source, e);
//                    return;
//                }
//            }
            String stringTarget = targetPath + File.separator + source.getFileName();
            Path target = Paths.get(stringTarget);
            boolean exists = Files.exists(target);
            if (!exists || pictureMediaConfig.isOverwriteAll()) {
                resizePicture(source, target, p.getCompressionPercent());
            }

        }
    }

    public void createResizedPictures(Path source, List<Double> compressions) {
        List<PictureMediaDTO> pictureMedias = pictureMediaConfig.getPictureMedias();
        int size = pictureMedias.size();
        if (compressions!=null && compressions.size() != size) {
            throw new RuntimeException(
                    "Size of compressions must be equal size of picture medias: " + compressions.size() + " / " + +size);
        }
        for (int i = 0; i < size; i++) {
            PictureMediaDTO p = pictureMedias.get(i);
            String targetPath = getMainPath() + p.getPath();
//            if (pictureMediaConfig.isUnknownAutoRecognize() && pictureMediaConfig.isPictureWrongType(source)) {
//                try {
//                    source = pictureMediaConfig.rename(source);
//                } catch (IOException e) {
//                    log.error("Rename source: " + source, e);
//                    return;
//                }
//            }
            String stringTarget = targetPath + File.separator + source.getFileName();
            Path target = Paths.get(stringTarget);
            boolean exists = Files.exists(target);
            double compressionPercent = getCompressionPercent(compressions, i, p);
            if (!exists) {
                resizePicture(source, target, compressionPercent);
            }
        }
    }

    private double getCompressionPercent(List<Double> compressions, int i, PictureMediaDTO p) {
        double compressionPercent = p.getCompressionPercent();
        if (compressions != null) {
            Double customCompression = compressions.get(i);
            if (customCompression > 0) {
                compressionPercent = customCompression;
            }
        }
        return compressionPercent;
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

    private void createDirectories() throws IOException {
        List<PictureMediaDTO> pictureMedias = pictureMediaConfig.getPictureMedias();
        for (PictureMediaDTO p : pictureMedias) {
            String path = getMainPath() + p.getPath();
            serverPathConfig.createDirectories(path);
        }
    }
}
