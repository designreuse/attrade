package by.attrade.service;

import by.attrade.config.PictureMediaConfig;
import by.attrade.config.ServerPathConfig;
import by.attrade.domain.dto.PictureMediaDTO;
import by.attrade.service.exception.ImageWithoutContentException;
import by.attrade.service.pictureResizer.AwtPictureResizerService;
import by.attrade.util.ImageIOService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PictureMediaService {
    public static final double MAX_COMPRESSION_PERCENT = 100;
    private static final int MAX_DEPTH_ONLY_MAIN = 1;

    @Autowired
    private PictureMediaConfig pictureMediaConfig;

    @Autowired
    private ServerPathConfig serverPathConfig;

    @Autowired
    private AwtPictureResizerService pictureResizerService;

    @Autowired
    private ImageIOService imageIOService;


    @PostConstruct
    public void init() throws IOException {
        if (pictureMediaConfig.isInit()) {
            processUploadPictures();
        }
    }

    private void processUploadPictures() throws IOException {
        createDirectories();
        removeEmptyPictures();
        renameUnknownTypePictures();
        if (pictureMediaConfig.isRemoveMarkersFromMain()){
            removeMarkersFromMain();
        }
        if(pictureMediaConfig.isRemoveNotSynchronized()){
            removeNotSynchronized();
        }
        copyResizedPictures(pictureMediaConfig.isReplaceExistingResized());
        createMarkerPictures();
    }

    private void removeMarkersFromMain() {
        try {
            Files
                    .walk(Paths.get(getMainPath()), MAX_DEPTH_ONLY_MAIN)
                    .filter(path -> Files.isRegularFile(path) && isMarkerPicture(path))
                    .forEach(this::removePath);
        } catch (IOException e) {
            log.error("Cannot walk to delete marker pictures.", e);
        }
    }

    private void createMarkerPictures() {
        try {
            Files
                    .walk(Paths.get(getMainPath()))
                    .filter(path -> Files.isRegularFile(path) && !isMarkerPicture(path))
                    .forEach(path -> createMarkerPictures(path, pictureMediaConfig.isReplaceExistingMarker()));
        } catch (IOException e) {
            log.error("Cannot walk to create marker pictures.", e);
        }
    }

    private void renameUnknownTypePictures() {
        try {
            Files
                    .walk(Paths.get(getMainPath()))
                    .filter(path -> Files.isRegularFile(path) && imageIOService.isPictureUnknownType(path))
                    .forEach(this::renameUnknownImageTypeToDefault);
        } catch (IOException e) {
            log.error("Cannot walk to renameUnknownImageTypeToDefault wrong type pictures.", e);
        }
    }

    private void removeEmptyPictures() {
        try {
            Files
                    .walk(Paths.get(getMainPath()))
                    .filter(path -> Files.isRegularFile(path))
                    .forEach(this::removePictureIfEmpty);
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
        List<PictureMediaDTO> pictureMedias = pictureMediaConfig.getPictureMedias();
        for (PictureMediaDTO p : pictureMedias) {
            String child = getMainPath() + p.getPath();
            Path mainPath = Paths.get(getMainPath());
            Path childPath = Paths.get(child);
            walkAndRemoveIfNotExistsInMain(mainPath, childPath);
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
                    .forEach(this::removePath);
        } catch (IOException e) {
            log.error("Files operations with: " + mainPath + " / " + childPath, e);
        }
    }

    public boolean removePictureIfEmpty(Path p){
        if (isEmptyFile(p)){
            removePath(p);
            return true;
        }
        return false;
    }

    private void removePath(Path p) {
        try {
            Files.delete(p);
        } catch (IOException e) {
            log.error("Remove path: " + p, e);
        }
    }

    private void copyResizedPictures(boolean replaceExisting) throws IOException {
        Files
                .walk(Paths.get(getMainPath()), MAX_DEPTH_ONLY_MAIN)
                .filter(Files::isRegularFile)
                .forEach(path -> createAllResizedPictures(path, replaceExisting));
    }

    private void createAllResizedPictures(Path source, boolean replaceExisting) {
        List<PictureMediaDTO> pictureMedias = pictureMediaConfig.getPictureMedias();
        for (PictureMediaDTO p : pictureMedias) {
            Path target = getMediaPath(source, p);
            boolean exists = Files.exists(target);
            if (!exists || replaceExisting) {
                Double compressionPercent = getMarkerCompression(p, target);
                resizePicture(source, target, compressionPercent);
            }
        }
    }

    private Double getMarkerCompression(PictureMediaDTO p, Path target) {
        Double compressionPercent = p.getCompressionPercent();
        int indexMarker = pictureMediaConfig.getIndexMarker(target);
        if (indexMarker != -1) {
            compressionPercent = getCompression(target, indexMarker);
        }
        return compressionPercent;
    }

    public Path renameUnknownImageTypeToDefault(Path source) {
        return imageIOService.renameImageTypeTo(source, pictureMediaConfig.getUnknownAutoImageType());
    }

    private void createMarkerPictures(Path source, boolean replaceExisting) {
        int size = pictureMediaConfig.getMarkerNames().size();
        for (int i = 0; i < size; i++) {
            Path target = getMarkerPath(source, i);
            if (!Files.exists(target) || replaceExisting) {
                createMarkerPicture(source, target, i);
            }
        }
    }

    private boolean isMarkerPicture(Path source) {
        return pictureMediaConfig.getIndexMarker(source) != -1;
    }

    private void createMarkerPicture(Path source, Path target, int i) {
        double compression = getCompression(source, i);
        resizePicture(source, target, compression);
    }

    private double getCompression(Path source, int i) {
        Integer markerWidth = getMarkerWidth(i);
        int width;
        try {
            width = pictureResizerService.getWidth(source);
        } catch (IOException e) {
            return MAX_COMPRESSION_PERCENT;
        }
        double compression;
        if (markerWidth >= width) {
            compression = MAX_COMPRESSION_PERCENT;
        } else {
            compression = markerWidth / ((double) width) * 100;
        }
        return compression;
    }

    private Integer getMarkerWidth(int i) {
        return pictureMediaConfig.getMarkerWidths().get(i);
    }

    private Path getMarkerPath(Path source, int i) {
        String s = source.getFileName().toString();
        String[] split = s.split("\\.");
        String marker = pictureMediaConfig.getMarkerNames().get(i);
        return source.resolveSibling(split[0] + marker + "." + split[1]);
    }

    public void createAllMediaMarkerPictures(Path source, boolean replaceExisting) {
        createMarkerPictures(source, replaceExisting);
        for (PictureMediaDTO p : pictureMediaConfig.getPictureMedias()) {
            Path target = getMediaPath(source, p);
            createMarkerPictures(target, replaceExisting);
        }
    }

    private Path getMediaPath(Path source, PictureMediaDTO p) {
        String targetPath = getMainPath() + p.getPath();
        String stringTarget = targetPath + File.separator + source.getFileName();
        return Paths.get(stringTarget);
    }

    public boolean createAllResizedPictures(Path source, double[] compressions, boolean replaceExisting) {
        if (isEmptyFile(source)) {
            removePath(source);
            return false;
        }
        if (imageIOService.isPictureUnknownType(source)) {
            source = renameUnknownImageTypeToDefault(source);
        }
        List<PictureMediaDTO> pictureMedias = pictureMediaConfig.getPictureMedias();
        int size = pictureMedias.size();
        if (compressions != null && compressions.length != size) {
            throw new RuntimeException(
                    "Size of compressions must be equal size of picture medias: " + compressions.length + " / " + +size);
        }
        for (int i = 0; i < size; i++) {
            PictureMediaDTO p = pictureMedias.get(i);
            Path target = getMediaPath(source, p);
            double compressionPercent = getCompressionPercent(compressions, i, p);
            boolean exists = Files.exists(target);
            if (!exists || replaceExisting) {
                resizePicture(source, target, compressionPercent);
            }
        }
        return true;
    }

    private double getCompressionPercent(double[] compressions, int i, PictureMediaDTO p) {
        double compressionPercent = p.getCompressionPercent();
        if (compressions != null) {
            double customCompression = compressions[i];
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

    private void resizePicture(Path source, Path target, int width, int height) {
        try {
            pictureResizerService.resize(source.toString(), target.toString(), width, height);
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
