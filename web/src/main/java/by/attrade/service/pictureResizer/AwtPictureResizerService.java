package by.attrade.service.pictureResizer;

import com.sun.image.codec.jpeg.JPEGCodec;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class AwtPictureResizerService implements IPictureResizer {

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     *
     * @param inputImagePath  Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param width     absolute width in pixels
     * @param height    absolute height in pixels
     * @throws IOException
     */
    @Override
    public void resize(String inputImagePath, String outputImagePath, int width, int height)
            throws IOException {

        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = getBufferedImage(inputFile);

        Image tmp = inputImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // creates output image
        BufferedImage outputImage = new BufferedImage(width,
                height, inputImage.getType());

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
     * @param ratio         a double number specifies percentage of the output image
     *                        over the input image.
     * @throws IOException
     */
    @Override
    public void resize(String inputImagePath, String outputImagePath, double ratio)
            throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = getBufferedImage(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * ratio);
        int scaledHeight = (int) (inputImage.getHeight() * ratio);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }

    private BufferedImage getBufferedImage(File inputFile) throws IOException {
        BufferedImage inputImage;
        try {
            inputImage = ImageIO.read(inputFile);
        } catch (IOException e) {
            inputImage = JPEGCodec.createJPEGDecoder(new FileInputStream(inputFile)).decodeAsBufferedImage();
        }
        return inputImage;
    }
}