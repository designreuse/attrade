package by.attrade.service;

import by.attrade.editor.SeamCarverPictureSqueezer;
import by.attrade.service.exception.PictureAlreadySmallException;
import edu.princeton.cs.algs4.Picture;
import org.springframework.stereotype.Service;

@Service
public class PictureSqueezeService {
    public void squeezeSavingProportion(String path, int newWidth) throws PictureAlreadySmallException {
        Picture picture = new Picture(path);
        int w = picture.width();
        int h = picture.height();
        int newHeight = w / newWidth * h;
        squeeze(path, newWidth, newHeight);
    }

    public void squeeze(String path, int newWidth, int newHeight) throws PictureAlreadySmallException {
        Picture picture = new Picture(path);
        int w = picture.width();
        int h = picture.height();
        if (newWidth < w) {
            throw new PictureAlreadySmallException(
                    "Picture already small. Expected width: " + newWidth + " Actual width: " + w + ".");
        }
        if (newHeight < h) {
            throw new PictureAlreadySmallException(
                    "Picture already small. Expected height: " + newWidth + " Actual height: " + w + ".");
        }
        int diffW = w - newWidth;
        int diffH = h - newHeight;
        SeamCarverPictureSqueezer squeezer = new SeamCarverPictureSqueezer(picture);
        while (diffH > 0 && diffW > 0) {
            diffH = squeezeVertical(diffH, squeezer);
            diffW = squeezeVertical(diffW, squeezer);
        }
        while (diffW > 0) {
            diffW = squeezeHorizontal(diffW, squeezer);
        }
        while (diffH > 0) {
            diffH = squeezeVertical(diffH, squeezer);
        }
        Picture squeezedPic = squeezer.picture();
        squeezedPic.save(path);
    }

    private int squeezeVertical(int diffH, SeamCarverPictureSqueezer squeezer) {
        int[] verticalSeam = squeezer.findVerticalSeam();
        squeezer.removeVerticalSeam(verticalSeam);
        diffH--;
        return diffH;
    }

    private int squeezeHorizontal(int diffW, SeamCarverPictureSqueezer squeezer) {
        int[] horizontalSeam = squeezer.findHorizontalSeam();
        squeezer.removeHorizontalSeam(horizontalSeam);
        return diffW--;
    }
}
