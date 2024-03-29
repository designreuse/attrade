package by.attrade.service;

import by.attrade.util.Pair;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ImageService {
    public boolean isPictureUnknownType(Path source) {
        String str = source.toString();
        String suffix = str.substring(str.lastIndexOf(".") + 1);
        String[] readerFileSuffixes = ImageIO.getReaderFileSuffixes();
        List<String> list = Arrays.asList(readerFileSuffixes);
        return !list.contains(suffix);
    }
    public Path changeImageTypeTo(Path source, String imageType) {
        String str = source.toString();
        int i = str.lastIndexOf(".");
        str = str.substring(0, i + 1) + imageType;
        Path target = Paths.get(str);
        return target;
    }
    public boolean isImage(String pathName) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(pathName));
        boolean isImage = bufferedImage != null;
        bufferedImage.flush();
        return isImage;
    }
    public boolean isEmpty(BufferedImage inputImage) {
        try {
            inputImage.getWidth();
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public BufferedImage getBufferedImage(File inputFile) throws IOException {
        BufferedImage inputImage;
        try {
            inputImage = ImageIO.read(inputFile);
        } catch (Exception e) {
            try {
                inputImage = JPEGCodec.createJPEGDecoder(new FileInputStream(inputFile)).decodeAsBufferedImage();
            } catch (Exception e1) {
                log.error(inputFile.toString(), e.getMessage());
                throw e;
            }
        }
        return inputImage;
    }

    public BufferedImage getBufferedImage(int width, int height, BufferedImage inputImage) {
        BufferedImage bufferedImage;
        int type = inputImage.getType();
        if (type != 0) {
            bufferedImage = new BufferedImage(width,
                    height, type);
        } else {
            bufferedImage = new BufferedImage(width,
                    height, BufferedImage.TYPE_INT_ARGB);
        }
        return bufferedImage;
    }

    public int getWidth(String inputImagePath) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = getBufferedImage(inputFile);
        int width = inputImage.getWidth();
        inputImage.flush();
        return width;
    }
    public Pair<Integer, Integer> getWidthHeight(String inputImagePath) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = getBufferedImage(inputFile);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        inputImage.flush();
        return new Pair<>(width, height);
    }
    public int[][] getRgbData(String inputImagePath) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = getBufferedImage(inputFile);
        int inputWidth = inputImage.getWidth();
        int inputHeight = inputImage.getHeight();
        int [][] rgbData = new int[inputWidth][inputHeight];
        for (int i = 0; i < inputWidth; i++) {
            for (int j = 0; j < inputHeight; j++) {
                rgbData[i][j] = inputImage.getRGB(i,j);
            }
        }
        return rgbData;
    }
}
