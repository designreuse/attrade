package by.attrade.service.imageResizer;

import by.attrade.service.exception.ImageWithoutContentException;
import by.attrade.service.exception.PictureAlreadySmallException;

import java.io.IOException;

public interface IImageResizer {
    void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException, PictureAlreadySmallException, ImageWithoutContentException;

    void resize(String inputImagePath, String outputImagePath, double ratio)
            throws IOException, PictureAlreadySmallException, ImageWithoutContentException;

    void compressIfJpegOrJpgExtension(String inputImagePath, double qualityPercent)
            throws IOException, ImageWithoutContentException;
}
