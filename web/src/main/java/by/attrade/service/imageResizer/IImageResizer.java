package by.attrade.service.imageResizer;

import by.attrade.service.exception.ImageWithoutContentException;
import by.attrade.service.exception.ImageExpandNotSupportedException;

import java.io.IOException;

public interface IImageResizer {
    void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException, ImageExpandNotSupportedException, ImageWithoutContentException;

    void resize(String inputImagePath, String outputImagePath, double ratio)
            throws IOException, ImageExpandNotSupportedException, ImageWithoutContentException;

    void resizeProportional(String inputImagePath, String outputImagePath, int scaledWidth)
            throws IOException, ImageWithoutContentException, ImageExpandNotSupportedException;

    void compressIfJpegOrJpgExtension(String inputImagePath, double qualityPercent)
            throws IOException, ImageWithoutContentException;
}
