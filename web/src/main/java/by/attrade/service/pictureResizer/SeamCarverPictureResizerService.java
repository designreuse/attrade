package by.attrade.service.pictureResizer;

import by.attrade.editor.SeamCarverPictureSqueezer;
import by.attrade.service.exception.PictureAlreadySmallException;
import edu.princeton.cs.algs4.Picture;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
//TODO - image does not look pretty good after compression (reason: maybe SeamCarver)
public class SeamCarverPictureResizerService implements IPictureResizer {
    @Override
    public void resize(String inputImagePath, String outputImagePath, double ratio) throws IOException, PictureAlreadySmallException {
        if (ratio <= 0) {
            return;
        }
        Picture picture = new Picture(inputImagePath);
        int w = picture.width();
        int h = picture.height();
        int newWidth = (int) (w * ratio);
        int newHeight = (int) (h * ratio);
        resize(inputImagePath, outputImagePath, newWidth, newHeight);
    }

    @Override
    public void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight) throws IOException, PictureAlreadySmallException {
        Picture picture = new Picture(inputImagePath);
        int w = picture.width();
        int h = picture.height();
        if (scaledWidth > w) {
            throw new PictureAlreadySmallException(
                    "Picture already small. Expected width: " + scaledWidth + " Actual width: " + w + ".");
        }
        if (scaledHeight > h) {
            throw new PictureAlreadySmallException(
                    "Picture already small. Expected height: " + scaledHeight + " Actual height: " + w + ".");
        }
        int diffW = w - scaledWidth;
        int diffH = h - scaledHeight;
        SeamCarverPictureSqueezer squeezer = new SeamCarverPictureSqueezer(picture);
        while (diffH > 0 && diffW > 0) {
            diffH = squeezeVertical(diffH, squeezer);
            diffW = squeezeHorizontal(diffW, squeezer);
        }
        while (diffH > 0) {
            diffH = squeezeVertical(diffH, squeezer);
        }
        while (diffW > 0) {
            diffW = squeezeHorizontal(diffW, squeezer);
        }
        Picture squeezedPic = squeezer.picture();
        squeezedPic.save(outputImagePath);
    }

    private int squeezeVertical(int diffH, SeamCarverPictureSqueezer squeezer) {
        int[] verticalSeam = squeezer.findVerticalSeam();
        squeezer.removeVerticalSeam(verticalSeam);
        return --diffH;
    }

    private int squeezeHorizontal(int diffW, SeamCarverPictureSqueezer squeezer) {
        int[] horizontalSeam = squeezer.findHorizontalSeam();
        squeezer.removeHorizontalSeam(horizontalSeam);
        return --diffW;
    }
}
