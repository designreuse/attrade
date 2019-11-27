package by.attrade.service.pictureResizer;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ResizeImageExample {
    public static void main(String... args) throws IOException {

        File input = new File("/tmp/duke.png");
        BufferedImage image = ImageIO.read(input);

        BufferedImage resized = resize(image, 500, 500);

        File output = new File("/tmp/duke-resized-500x500.png");
        ImageIO.write(resized, "png", output);

    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}