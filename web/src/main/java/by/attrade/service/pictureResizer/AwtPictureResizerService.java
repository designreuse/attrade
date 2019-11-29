package by.attrade.service.pictureResizer;


import by.attrade.service.exception.ImageWithoutContentException;
import com.sun.image.codec.jpeg.JPEGCodec;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class AwtPictureResizerService implements IPictureResizer {

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
    public void resize(String inputImagePath, String outputImagePath, int width, int height)
            throws IOException, ImageWithoutContentException {

        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = getBufferedImage(inputFile);

        if (isEmpty(inputImage)){
            throw new ImageWithoutContentException("There is no content in image with path: "+ inputImagePath);
        }

        Image tmp = inputImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // creates output image
        BufferedImage outputImage = getBufferedImage(width, height, inputImage);

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    /**
     * Resizes an image by a percentage of original size (proportional).
     *
     * @param inputImagePath  Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param ratio           a double number specifies percentage of the output image
     *                        over the input image.
     * @throws IOException
     */
    @Override
    public void resize(String inputImagePath, String outputImagePath, double ratio)
            throws IOException, ImageWithoutContentException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = getBufferedImage(inputFile);
        if(isEmpty(inputImage)){
            throw new ImageWithoutContentException("There is no content in image with path: "+ inputImagePath);
        }
            int scaledWidth = (int) (inputImage.getWidth() * ratio);
            int scaledHeight = (int) (inputImage.getHeight() * ratio);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }

    private boolean isEmpty(BufferedImage inputImage) {
        try {
            inputImage.getWidth();
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    private BufferedImage getBufferedImage(File inputFile) throws IOException {
        BufferedImage inputImage;
        try {
            inputImage = ImageIO.read(inputFile);
        } catch (Exception e) {
            inputImage = JPEGCodec.createJPEGDecoder(new FileInputStream(inputFile)).decodeAsBufferedImage();
        }
        return inputImage;
    }

    private BufferedImage getBufferedImage(int width, int height, BufferedImage inputImage) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = new BufferedImage(width,
                    height, inputImage.getType());
        } catch (Exception e) {
            bufferedImage = new BufferedImage(width,
                    height, BufferedImage.TYPE_INT_RGB);
        }
        return bufferedImage;
    }

    public int getWidth(Path source) throws IOException {
        File inputFile = source.toFile();
        BufferedImage inputImage = getBufferedImage(inputFile);
        return inputImage.getWidth();
    }
}