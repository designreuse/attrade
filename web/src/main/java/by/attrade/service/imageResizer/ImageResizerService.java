package by.attrade.service.imageResizer;


import by.attrade.service.ImageService;
import by.attrade.service.exception.ImageWithoutContentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

@Service
@Slf4j
public class ImageResizerService implements IImageResizer {
    private static final String JPEG = "jpeg";
    private static final String JPG = "jpg";
    private static final String DEFAULT_FORMAT_NAME = "jpg";
    private static final double MAX_PERCENT = 100;

    @Autowired
    private ImageService imageService;

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     *
     * @param inputImagePath  Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param width           absolute width in pixels
     * @param height          absolute height in pixels
     * @throws IOException
     */
    @Override
    //TODO - switch and adjust separately GIF image
    public void resize(String inputImagePath, String outputImagePath, int width, int height)
            throws IOException, ImageWithoutContentException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = imageService.getBufferedImage(inputFile);

        if (imageService.isEmpty(inputImage)) {
            throw new ImageWithoutContentException("There is no content in image with path: " + inputImagePath);
        }

        Image tmp = inputImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // creates output image
        BufferedImage outputImage = imageService.getBufferedImage(width, height, inputImage);

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        File compressedImageFile = new File(outputImagePath);
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(DEFAULT_FORMAT_NAME);
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(getRatio(MAX_PERCENT));
        writer.write(null, new IIOImage(outputImage, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
    }

    /**
     * Resizes an image by a percentage of original size (proportional).
     *
     * @param inputImagePath     Path of the original image
     * @param outputImagePath    Path to save the resized image
     * @param compressionPercent a double number specifies percentage of the output image
     *                           over the input image.
     * @throws IOException
     */
    @Override
    public void resize(String inputImagePath, String outputImagePath, double compressionPercent)
            throws IOException, ImageWithoutContentException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = imageService.getBufferedImage(inputFile);
        if (imageService.isEmpty(inputImage)) {
            throw new ImageWithoutContentException("There is no content in image with path: " + inputImagePath);
        }
        float ratio = getRatio(compressionPercent);
        int scaledWidth = (int) (inputImage.getWidth() * ratio);
        int scaledHeight = (int) (inputImage.getHeight() * ratio);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }

    @Override
    public void compressIfJpegOrJpgExtension(String inputImagePath, double qualityPercent)
            throws IOException, ImageWithoutContentException {
        String extension = getExtension(inputImagePath);
        if (!isJpegOrJpgExtension(extension)) {
            return;
        }

        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = imageService.getBufferedImage(inputFile);

        if (imageService.isEmpty(inputImage)) {
            throw new ImageWithoutContentException("There is no content in image with path: " + inputImagePath);
        }

        File compressedImageFile = new File(inputImagePath);
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extension);
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(getRatio(qualityPercent));
        writer.write(null, new IIOImage(inputImage, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
    }

    private String getExtension(String inputImagePath) {
        return FilenameUtils.getExtension(inputImagePath);
    }

    private boolean isJpegOrJpgExtension(String extension) {
        return extension.equals(JPEG) || extension.equals(JPG);
    }

    private float getRatio(double qualityPercent) {
        return (float) (qualityPercent / 100);
    }
}