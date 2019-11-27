package by.attrade.service.pictureResizer;

import by.attrade.service.exception.PictureAlreadySmallException;

import java.io.IOException;

public interface IPictureResizer {
    void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException, PictureAlreadySmallException;

    void resize(String inputImagePath, String outputImagePath, double ratio)
            throws IOException, PictureAlreadySmallException;
}
