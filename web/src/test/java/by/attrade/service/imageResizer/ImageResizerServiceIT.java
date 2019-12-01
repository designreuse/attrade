package by.attrade.service.imageResizer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageResizerServiceIT {
    @Autowired
    private ImageResizerService imageResizerService;
    private String imagePath;
    private String imagePath1;
    private String imagePath2;
    private String imagePath3;
    private String outputImagePath;
    private String outputImagePath1;
    private String outputImagePath2;
    private String outputImagePath3;
    private double qualityPercent;
    @Before
    public void init(){
        imagePath = "d:\\java\\projects\\attrade-core\\upload\\81fc2e85-2432-4493-a44c-83cb8329167b.gif";
        imagePath1 = "d:\\java\\projects\\attrade-core\\upload\\1be1578a-b7ab-46db-9890-776d0912316f.jpeg";
        imagePath2 = "d:\\java\\projects\\attrade-core\\upload\\f196aa54-a0ee-4b3f-8c1e-cd20b1a1553c.jpg";
        imagePath3 = "d:\\java\\projects\\attrade-core\\upload\\logo.png";
        outputImagePath = "d:\\java\\projects\\attrade-core\\upload\\x-81fc2e85-2432-4493-a44c-83cb8329167b.gif";
        outputImagePath1 = "d:\\java\\projects\\attrade-core\\upload\\x-1be1578a-b7ab-46db-9890-776d0912316f.jpeg";
        outputImagePath2 = "d:\\java\\projects\\attrade-core\\upload\\x-f196aa54-a0ee-4b3f-8c1e-cd20b1a1553c.jpg";
        outputImagePath3 = "d:\\java\\projects\\attrade-core\\upload\\x-logo.png";
        qualityPercent = 100;
    }
    @Test
    public void compress() throws Exception {
        imageResizerService.compressIfJpegOrJpgExtension(imagePath, qualityPercent);
        imageResizerService.compressIfJpegOrJpgExtension(imagePath1, qualityPercent);
        imageResizerService.compressIfJpegOrJpgExtension(imagePath2, qualityPercent);
        imageResizerService.compressIfJpegOrJpgExtension(imagePath3, qualityPercent);
    }
    @Test
    public void resize()throws Exception{
        imageResizerService.resize(imagePath, outputImagePath, 50);
        imageResizerService.resize(imagePath1, outputImagePath1, 50);
        imageResizerService.resize(imagePath2, outputImagePath2, 50);
        imageResizerService.resize(imagePath3, outputImagePath3, 50);
    }
}