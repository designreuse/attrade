package by.attrade.service;

import by.attrade.config.PictureMediaConfig;
import by.attrade.config.ServerPathConfig;
import by.attrade.domain.dto.PictureMediaDTO;
import by.attrade.io.ImageDownloader;
import by.attrade.service.exception.ImageWithoutContentException;
import by.attrade.service.imageResizer.ImageResizerService;
import by.attrade.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PictureMediaService {

    private static final double MAX_COMPRESSION_PERCENT = 100;
    private static final double MAX_COMPRESSION_QUALITY = 100;
    private static final int MAX_DEPTH_ONLY_MAIN = 1;

    @Autowired
    private PictureMediaConfig pictureMediaConfig;

    @Autowired
    private ServerPathConfig serverPathConfig;

    @Autowired
    private ImageResizerService imageResizerService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageDownloader imageDownloader;


    @PostConstruct
    public void init() throws IOException {
        if (pictureMediaConfig.isInit()) {
            processUploadPictures();
        }
    }

    private void processUploadPictures() throws IOException {
        log.info("Create directories");
        createDirectories();
        log.info("Remove empty pictures");
        removeEmptyPictures();
        log.info("Remove unknown type of pictures");
        renameUnknownTypePictures();
        if (pictureMediaConfig.isRemoveMarkers()) {
            log.info("Remove marker pictures");
            removeMarkers();
        }
        if (pictureMediaConfig.isRemoveNotSynchronized()) {
            log.info("Remove not synchronized");
            removeNotSynchronized();
        }
        if (pictureMediaConfig.isCompressMain()) {
            log.info("Compress main pictures");
            compressMain(pictureMediaConfig.getCompressMainQuality());
        }
        if (pictureMediaConfig.isCopyMediaPictures()) {
            log.info("Copy and resize pictures");
            copyMediaPictures(pictureMediaConfig.isReplaceExistingMedia());
        }
        if (pictureMediaConfig.isCreateMarkerPictures()) {
            log.info("Create marker pictures");
            createMarkerPictures(pictureMediaConfig.isReplaceExistingMarker());
        }
    }

    private void compressMain(double compressQuality) {
        try {
            Files
                    .walk(Paths.get(getMainPath()), MAX_DEPTH_ONLY_MAIN)
                    .filter(path -> Files.isRegularFile(path) && !isMarkerPicture(path))
                    .forEach(path -> compress(path, compressQuality));
        } catch (IOException e) {
            log.error("Cannot walk to compress main pictures.", e);
        }
    }

    public void compress(Path path, double compressQuality) {
        try {
            imageResizerService.compressIfJpegOrJpgExtension(path.toString(), compressQuality);
        } catch (IOException e) {
            log.error("Cannot compress because cannot open file: " + path);
        } catch (ImageWithoutContentException e) {
            log.error("Cannot compress because picture does not have content: " + path);
        }
    }

    public boolean saveAllMediaAndMarkers(Path source, double[] compressions) {
        boolean empty = removePictureIfEmpty(source);
        if (empty) {
            return false;
        }
        compress(source, MAX_COMPRESSION_QUALITY);
        boolean resized = createAllMediaPictures(source, compressions, true);
        if (resized) {
            createAllMarkerPictures(source, true);
        } else {
            return false;
        }
        return true;
    }

    private void removeMarkers() {
        try {
            Files
                    .walk(Paths.get(getMainPath()))
                    .filter(path -> Files.isRegularFile(path) && isMarkerPicture(path))
                    .forEach(this::deletePath);
        } catch (IOException e) {
            log.error("Cannot walk to delete marker pictures.", e);
        }
    }

    private void createMarkerPictures(boolean replaceExisting) {

        try {
            Files
                    .walk(Paths.get(getMainPath()))
                    .filter(path -> Files.isRegularFile(path) && !isMarkerPicture(path))
                    .forEach(path -> createMarkerPictures(path, replaceExisting));
        } catch (IOException e) {
            log.error("Cannot walk to create marker pictures.", e);
        }
    }

    private void renameUnknownTypePictures() {
        try {
            Files
                    .walk(Paths.get(getMainPath()))
                    .filter(path -> Files.isRegularFile(path) && imageService.isPictureUnknownType(path))
                    .forEach(this::renameIfUnknownImageTypeToDefault);
        } catch (IOException e) {
            log.error("Cannot walk to renameIfUnknownImageTypeToDefault wrong type pictures.", e);
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
                    .forEach(this::deletePath);
        } catch (IOException e) {
            log.error("Files operations with: " + mainPath + " / " + childPath, e);
        }
    }

    public boolean removePictureIfEmpty(Path p) {
        if (isEmptyFile(p)) {
            deletePath(p);
            return true;
        }
        return false;
    }

    private void deletePath(Path p) {
        try {
            Files.delete(p);
        } catch (IOException e) {
            log.error("Remove path: " + p, e);
        }
    }

    private void copyMediaPictures(boolean replaceExisting) throws IOException {
        Files
                .walk(Paths.get(getMainPath()), MAX_DEPTH_ONLY_MAIN)
                .filter(Files::isRegularFile)
                .forEach(path -> createAllMediaPictures(path, replaceExisting));
    }

    private void createAllMediaPictures(Path source, boolean replaceExisting) {
        List<PictureMediaDTO> pictureMedias = pictureMediaConfig.getPictureMedias();
        for (PictureMediaDTO p : pictureMedias) {
            Path target = getMediaPath(source, p);
            boolean exists = Files.exists(target);
            if (exists && replaceExisting) {
                deletePath(target);
            }
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

    public Path renameIfUnknownImageTypeToDefault(Path source) {
        if (imageService.isPictureUnknownType(source)) {
            return imageService.renameImageTypeTo(source, pictureMediaConfig.getUnknownAutoImageType());
        }
        return source;
    }

    public String savePicture(String imageUrl, double[] compressions) {
        if (imageUrl == null) {
            return pictureMediaConfig.getDefaultPictureFileName();
        }
        Path target = getPicturePath(imageUrl);
        if (!savePicture(imageUrl, target, compressions)) {
            return null;
        }
        return target.getFileName().toString();
    }

    public String saveDefaultPicture(String imageUrl, double[] compressions) {
        String pictureFileName = getPictureFileName(pictureMediaConfig.getDefaultPictureFileName());
        Path target = Paths.get(pictureFileName);
        if (!savePicture(imageUrl, target, compressions)) {
            return null;
        }
        return target.getFileName().toString();
    }

    private boolean savePicture(String imageUrl, Path target, double[] compressions) {
        try {
            imageDownloader.downloadByUrl(imageUrl, target.toString());
        } catch (IOException e) {
            return false;
        }
        boolean noError = saveAllMediaAndMarkers(target, compressions);
        if (!noError) {
            deletePath(target);
            return false;
        }
        return true;
    }

    private Path getPicturePath(String imageUrl) {
        String fileName = getFileName(imageUrl);
        fileName = changeImageFileNameIfWrongType(fileName);
        String pictureFileName = getPictureFileName(fileName);
        return Paths.get(pictureFileName);
    }

    private String getPictureFileName(String fileName) {
        return serverPathConfig.getAbsolute() + serverPathConfig.getPicture() + File.separator + fileName;
    }

    private String changeImageFileNameIfWrongType(String name) {
        Path path = renameIfUnknownImageTypeToDefault(Paths.get(name));
        return path.toString();
    }

    private String getFileName(String imageUrl) {
        String extension = FilenameUtils.getExtension(imageUrl);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    private void createMarkerPictures(Path source, boolean replaceExisting) {
        int size = pictureMediaConfig.getMarkerNames().size();
        for (int i = 0; i < size; i++) {
            Path target = getMarkerPath(source, i);
            boolean exists = Files.exists(target);
            if (exists && replaceExisting) {
                deletePath(target);
            }
            if (!exists || replaceExisting) {
                Pair<Integer, Integer> widthHeight = getWidthHeight(source, i);
                if (widthHeight != null) {
                    resizePicture(source, target, widthHeight.getFirst(), widthHeight.getSecond());
                }
            }
        }
    }

    private boolean isMarkerPicture(Path source) {
        return pictureMediaConfig.getIndexMarker(source) != -1;
    }

    private Pair<Integer, Integer> getWidthHeight(Path source, int i) {
        Integer markerWidth = getMarkerWidth(source, i);
        Pair<Integer, Integer> widthHeight;
        try {
            widthHeight = imageService.getWidthHeight(source.toString());
        } catch (IOException e) {
            return null;
        }
        Integer width = widthHeight.getFirst();
        if (markerWidth >= width) {
            return widthHeight;
        } else {
            int markerHeight = (int) Math.round(markerWidth / ((double) width) * widthHeight.getSecond());
            return new Pair<>(markerWidth, markerHeight);
        }
    }

    private double getCompression(Path source, int i) {
        Integer markerWidth = getMarkerWidth(source, i);
        int width;
        try {
            width = imageService.getWidth(source.toString());
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

    private Integer getMarkerWidth(Path source, int i) {
        String key;
        Path parent = source.getParent();
        String markerName = pictureMediaConfig.getMarkerNames().get(i);
        if (parent.toString().equals(getMainPath())) {
            key = markerName;
        } else {
            key = markerName + parent.getFileName();
        }
        return pictureMediaConfig.getMarkerWidths().get(key);
    }

    private Path getMarkerPath(Path source, int iMarker) {
        String s = source.getFileName().toString();
        String[] split = s.split("\\.");
        String marker = pictureMediaConfig.getMarkerNames().get(iMarker);
        return source.resolveSibling(split[0] + marker + "." + split[1]);
    }

    public void createAllMarkerPictures(Path source, boolean replaceExisting) {
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

    public boolean createAllMediaPictures(Path source, double[] compressions, boolean replaceExisting) {
        if (isEmptyFile(source)) {
            deletePath(source);
            return false;
        }
        if (imageService.isPictureUnknownType(source)) {
            source = renameIfUnknownImageTypeToDefault(source);
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
            imageResizerService.resize(source.toString(), target.toString(), compressionPercent);
        } catch (IOException | ImageWithoutContentException e) {
            log.error("Resize: " + target, e);
        }
    }

    private void resizePicture(Path source, Path target, int width, int height) {
        try {
            imageResizerService.resize(source.toString(), target.toString(), width, height);
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
