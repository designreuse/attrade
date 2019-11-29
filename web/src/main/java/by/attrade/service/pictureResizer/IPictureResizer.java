package by.attrade.service.pictureResizer;

import by.attrade.service.exception.ImageWithoutContentException;
import by.attrade.service.exception.PictureAlreadySmallException;

import java.io.IOException;

public interface IPictureResizer {
    void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException, PictureAlreadySmallException, ImageWithoutContentException;

    void resize(String inputImagePath, String outputImagePath, double ratio)
            throws IOException, PictureAlreadySmallException, ImageWithoutContentException;
}
