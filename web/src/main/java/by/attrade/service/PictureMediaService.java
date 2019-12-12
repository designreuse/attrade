package by.attrade.service;

import by.attrade.config.PictureMediaConfig;
import by.attrade.config.ServerPathConfig;
import by.attrade.domain.dto.PictureMediaDTO;
import by.attrade.io.ImageDownloader;
import by.attrade.service.exception.ImageExpandNotSupportedException;
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
        createDirectories();
        removeEmptyPictures();
        renameUnknownTypePictures();
        removeNotSynchronized();
        if (pictureMediaConfig.isCompressMain()) {
            compressMain(pictureMediaConfig.getCompressMainQuality());
        }
        if (pictureMediaConfig.isCreateMediaPictures()) {
            removeMedias();
            createMediaPictures();
        }
        if (pictureMediaConfig.isCreateMarkerPictures()) {
            removeMarkers();
            createMarkerPictures();
        }
    }

    private void removeMedias() throws IOException {
        log.info("Remove media pictures");
        try {
            Files
                    .walk(Paths.get(getMainPath()), MAX_DEPTH_ONLY_MAIN)
                    .filter(Files::isRegularFile)
                    .forEach(this::removeAllMediaPictures);
        } catch (IOException e) {
            log.error("Cannot walk to delete media pictures.", e);
            throw e;
        }
    }

    private void removeAllMediaPictures(Path source) {
        List<PictureMediaDTO> pictureMedias = pictureMediaConfig.getPictureMedias();
        rollbackMediaPictures(source, pictureMedias);
    }

    private void compressMain(double compressQuality) throws IOException {
        log.info("Compress main pictures");
        try {
            Files
                    .walk(Paths.get(getMainPath()), MAX_DEPTH_ONLY_MAIN)
                    .filter(path -> Files.isRegularFile(path) && !isMarkerPicture(path))
                    .forEach(path -> compress(path, compressQuality));
        } catch (IOException e) {
            log.error("Cannot walk to compress main pictures.", e);
            throw e;
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

    public boolean saveAllMediaAndMarkers(Path source) {
        boolean empty = removePictureIfEmpty(source);
        if (empty) {
            return false;
        }
        compress(source, MAX_COMPRESSION_QUALITY);
        if(!createAllMediaPictures(source)){
            return false;
        }
        if(!createAllMarkerPictures(source)) {
            return false;
        }
        return true;
    }

    private void removeMarkers() throws IOException {
        log.info("Remove marker pictures");
        try {
            Files
                    .walk(Paths.get(getMainPath()))
                    .filter(path -> Files.isRegularFile(path) && isMarkerPicture(path))
                    .forEach(this::deletePathIfExists);
        } catch (IOException e) {
            log.error("Cannot walk to delete marker pictures.", e);
            throw e;
        }
    }

    private void createMarkerPictures() throws IOException {
        log.info("Create marker pictures");
        try {
            Files
                    .walk(Paths.get(getMainPath()))
                    .filter(path -> Files.isRegularFile(path) && !isMarkerPicture(path))
                    .forEach(path -> {
                        try {
                            createMarkerPictures(path);
                        } catch (IOException e) {
                            throw new RuntimeException("Cannot create marker picture:" + path, e);
                        }
                    });
        } catch (IOException e) {
            log.error("Cannot walk to create marker pictures.", e);
            throw e;
        }
    }

    private void renameUnknownTypePictures() {
        log.info("Remove unknown type of pictures");
        try {
            Files
                    .walk(Paths.get(getMainPath()))
                    .filter(path -> Files.isRegularFile(path) && imageService.isPictureUnknownType(path))
                    .forEach(this::renameIfUnknownImageTypeToDefaultType);
        } catch (IOException e) {
            log.error("Cannot walk to renameIfUnknownImageTypeToDefaultType wrong type pictures.", e);
        }
    }

    private void removeEmptyPictures() throws IOException {
        log.info("Remove empty pictures");
        try {
            Files
                    .walk(Paths.get(getMainPath()))
                    .filter(path -> Files.isRegularFile(path))
                    .forEach(this::removePictureIfEmpty);
        } catch (IOException e) {
            log.error("Cannot walk to remove empty pictures.", e);
            throw e;
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
        log.info("Remove not synchronized");
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
                    .forEach(this::deletePathIfExists);
        } catch (IOException e) {
            log.error("Files operations with: " + mainPath + " / " + childPath, e);
        }
    }

    public boolean removePictureIfEmpty(Path p) {
        if (isEmptyFile(p)) {
            deletePathIfExists(p);
            return true;
        }
        return false;
    }

    private void deletePathIfExists(Path p) {
        try {
            Files.deleteIfExists(p);
        } catch (IOException e) {
            log.error("Cannot delete path: " + p, e);
        }
    }

    private void createMediaPictures() throws IOException {
        log.info("Create media pictures");
        try {
            Files
                    .walk(Paths.get(getMainPath()), MAX_DEPTH_ONLY_MAIN)
                    .filter(Files::isRegularFile)
                    .forEach(this::createAllMediaPictures);
        } catch (IOException e) {
            log.error("Cannot walk to create media pictures.", e);
            throw e;
        }
    }

    private boolean createAllMediaPictures(Path source) {
        List<PictureMediaDTO> pictureMedias = pictureMediaConfig.getPictureMedias();
        try {
            for (PictureMediaDTO p : pictureMedias) {
                Path target = getMediaPath(source, p);
                int width;
                try {
                    width = imageService.getWidth(source.toString());
                } catch (IOException e) {
                    throw new RuntimeException("Cannot receive width: " + target, e);
                }

                if (p.isWidthPreferredThanPercent(width) && p.isWidthNotExpand(width)) {
                    resizePictureProportional(source, target, p.getCompressionWidth());
                } else {
                    resizePicture(source, target, p.getCompressionPercent());
                }
            }
        } catch (Exception e) {
            log.error("Cannot create all media pictures: "+ source, e);
            rollbackMediaPictures(source, pictureMedias);
            return false;
        }
        return true;
    }

    private void rollbackMediaPictures(Path source, List<PictureMediaDTO> pictureMedias) {
        for (PictureMediaDTO p : pictureMedias) {
            Path target = getMediaPath(source, p);
            deletePathIfExists(target);
        }
    }


    public Path renameIfUnknownImageTypeToDefaultType(Path source) {
        if (imageService.isPictureUnknownType(source)) {
            return imageService.changeImageTypeTo(source, pictureMediaConfig.getUnknownAutoImageType());
        }
        return source;
    }

    public String savePictureOrRollback(String imageUrl) {
            if (imageUrl == null) {
                return pictureMediaConfig.getDefaultPictureFileName();
            }
            Path target = getPicturePath(imageUrl);
        if (imageService.isPictureUnknownType(target)) {
            target = renameIfUnknownImageTypeToDefaultType(target);
        }
            if (!savePicture(imageUrl, target)) {
                deletePathIfExists(target);
                return null;
            }
            return target.getFileName().toString();
    }

    private void rollBackPicture(String imageUrl) {

    }

    public String saveDefaultPicture(String imageUrl) {
        String pictureFileName = getPictureFileName(pictureMediaConfig.getDefaultPictureFileName());
        Path target = Paths.get(pictureFileName);
        if (!savePicture(imageUrl, target)) {
            return null;
        }
        return target.getFileName().toString();
    }

    private boolean savePicture(String imageUrl, Path target) {
        try {
            imageDownloader.downloadByUrl(imageUrl, target.toString());
        } catch (IOException e) {
            return false;
        }
        return saveAllMediaAndMarkers(target);
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
        Path path = renameIfUnknownImageTypeToDefaultType(Paths.get(name));
        return path.toString();
    }

    private String getFileName(String imageUrl) {
        String extension = FilenameUtils.getExtension(imageUrl).toLowerCase();
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    private void createMarkerPictures(Path source) throws IOException {
        int size = pictureMediaConfig.getMarkerNames().size();
        for (int i = 0; i < size; i++) {
            Path target = getMarkerPath(source, i);
            boolean exists = Files.exists(target);
            if (exists) {
                deletePathIfExists(target);
            }
            Integer scaledWidth = getMarkerWidth(source, i);
            Pair<Integer, Integer> widthHeight = adjustAndGetWidthHeight(source, scaledWidth);
            if (widthHeight != null) {
                resizePicture(source, target, widthHeight.getFirst(), widthHeight.getSecond());
            }
        }
    }

    private boolean isMarkerPicture(Path source) {
        return pictureMediaConfig.getIndexMarker(source) != -1;
    }

    private Pair<Integer, Integer> adjustAndGetWidthHeight(Path source, Integer scaledWidth) throws IOException {
        Pair<Integer, Integer> widthHeight;
        widthHeight = imageService.getWidthHeight(source.toString());
        Integer width = widthHeight.getFirst();
        if (scaledWidth >= width) {
            return widthHeight;
        } else {
            int markerHeight = (int) Math.round(scaledWidth / ((double) width) * widthHeight.getSecond());
            return new Pair<>(scaledWidth, markerHeight);
        }
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

    public boolean createAllMarkerPictures(Path source) {
        try {
            createMarkerPictures(source);
            for (PictureMediaDTO p : pictureMediaConfig.getPictureMedias()) {
                Path target = getMediaPath(source, p);
                createMarkerPictures(target);
            }
        } catch (IOException e) {
            log.error("Cannot create all marker picture: "+ source, e);
            rollbackMarkerPictures(source);
            return false;
        }
        return true;
    }

    private void rollbackMarkerPictures(Path source) {
        int size = pictureMediaConfig.getMarkerNames().size();
        for (int i = 0; i < size; i++) {
            Path target = getMarkerPath(source, i);
            deletePathIfExists(target);
        }
        for (int i = 0; i < size; i++) {
            Path markerPath = getMarkerPath(source, i);
            for (PictureMediaDTO p : pictureMediaConfig.getPictureMedias()) {
                Path target = getMediaPath(markerPath, p);
                deletePathIfExists(target);
            }
        }
    }

    private Path getMediaPath(Path source, PictureMediaDTO p) {
        String targetPath = getMainPath() + p.getPath();
        String stringTarget = targetPath + File.separator + source.getFileName();
        return Paths.get(stringTarget);
    }

    private void resizePicture(Path source, Path target, double compressionPercent) {
        try {
            imageResizerService.resize(source.toString(), target.toString(), compressionPercent);
        } catch (IOException | ImageWithoutContentException e) {
            log.error("Resize: " + target, e);
        }
    }
    private void resizePictureProportional(Path source, Path target, int width) {
        try {
            imageResizerService.resizeProportional(source.toString(), target.toString(), width);
        } catch (IOException | ImageWithoutContentException | ImageExpandNotSupportedException e) {
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
        log.info("Create directories");
        List<PictureMediaDTO> pictureMedias = pictureMediaConfig.getPictureMedias();
        for (PictureMediaDTO p : pictureMedias) {
            String path = getMainPath() + p.getPath();
            serverPathConfig.createDirectories(path);
        }
    }
}
