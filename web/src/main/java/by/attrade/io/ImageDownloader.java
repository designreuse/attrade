package by.attrade.io;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Component
public class ImageDownloader {

    public void downloadByUrl(String urlName, String pathName) throws IOException {
        FileUtils.copyURLToFile(
                new URL(urlName),
                new File(pathName));
    }
}
