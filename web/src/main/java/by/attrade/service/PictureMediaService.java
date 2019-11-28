package by.attrade.service;

import by.attrade.config.PictureMediaConfig;
import by.attrade.config.ServerPathConfig;
import by.attrade.domain.dto.PictureMediaDTO;
import by.attrade.service.exception.ImageWithoutContentException;
import by.attrade.service.pictureResizer.AwtPictureResizerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        removeEmptyPictures();
        renameWrongTypePictures();
        removeNotSynchronized();
        copyAndSqueezePictures();
    }

    private void renameWrongTypePictures() {
        try {
            Files
                    .walk(Paths.get(getMainPath()))
                    .filter(path -> Files.isRegularFile(path) && isPictureWrongType(path))
                    .forEach(this::autoRename);
        } catch (IOException e) {
            log.error("Cannot walk to autoRename wrong type pictures.", e);
        }
    }

    private void removeEmptyPictures() {
        try {
            Files
                    .walk(Paths.get(getMainPath()))
                    .filter(path -> Files.isRegularFile(path) && isEmptyFile(path))
                    .forEach(this::deletePath);
        } catch (IOException e) {
            log.error("Cannot walk to remove empty pictures.", e);
        }

    }

    private boolean isEmptyFile(Path source) {
        try {
            FileChannel imageFileChannel = FileChannel.open(source);
            long imageFileSize = imageFileChannel.size();
            if (imageFileSize <= 0) {
                return true;
            }
        } catch (IOException e) {
            log.error("Cannot open fileChannel for: " + source);
        }
        return false;
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
            String stringTarget = targetPath + File.separator + source.getFileName();
            Path target = Paths.get(stringTarget);
            boolean exists = Files.exists(target);
            if (!exists || pictureMediaConfig.isOverwriteAll()) {
                resizePicture(source, target, p.getCompressionPercent());
            }
        }
    }

    private Path autoRename(Path source) {
        String str = source.toString();
        int i = str.lastIndexOf(".");
        str = str.substring(0, i + 1) + pictureMediaConfig.getUnknownAutoImageType();
        Path target = Paths.get(str);
        try {
            Files.move(source, target);
        } catch (IOException e) {
            log.error("Cannot move file: " + source + " to: " + target);
        }
        return target;
    }

    private boolean isPictureWrongType(Path source) {
        String str = source.toString();
        String suffix = str.substring(str.lastIndexOf(".") + 1);
        String[] readerFileSuffixes = ImageIO.getReaderFileSuffixes();
        List<String> list = Arrays.asList(readerFileSuffixes);
        return !list.contains(suffix);
    }

    public boolean createResizedPictures(Path source, List<Double> compressions) {
        if (isEmptyFile(source)) {
            deletePath(source);
            return false;
        }
        if (isPictureWrongType(source)) {
            source = autoRename(source);
        }
        List<PictureMediaDTO> pictureMedias = pictureMediaConfig.getPictureMedias();
        int size = pictureMedias.size();
        if (compressions != null && compressions.size() != size) {
            throw new RuntimeException(
                    "Size of compressions must be equal size of picture medias: " + compressions.size() + " / " + +size);
        }
        for (int i = 0; i < size; i++) {
            PictureMediaDTO p = pictureMedias.get(i);
            String targetPath = getMainPath() + p.getPath();
            String stringTarget = targetPath + File.separator + source.getFileName();
            Path target = Paths.get(stringTarget);
            boolean exists = Files.exists(target);
            double compressionPercent = getCompressionPercent(compressions, i, p);
            if (!exists) {
                resizePicture(source, target, compressionPercent);
            }
        }
        return true;
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
        } catch (IOException | ImageWithoutContentException e) {
            log.error("Resize: " + target, e);
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
