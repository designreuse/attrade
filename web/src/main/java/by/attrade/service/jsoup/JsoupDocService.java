package by.attrade.service.jsoup;

import by.attrade.config.JsoupConfig;
import by.attrade.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Slf4j
public class JsoupDocService {
    private static final String HREF_NOT_PDF_NOT_IMAGE_NOT_EXCEL_CSS_QUERY = "a[href]:not(a[href~=(?i)\\.(pdf|png|jpe?g|svg|gif|bmp|xlsx)])";
    private static final String HREF_CSS_ATTRIBUTE = "href";
    @Autowired
    private JsoupConfig jsoupConfig;


    public Document getJsoupDoc(String url) throws IOException {
        try {
            return Jsoup.connect(url)
//                    .userAgent(jsoupConfig.getUserAgent())
                    .referrer(jsoupConfig.getReferrer())
                    .timeout(jsoupConfig.getTimeout())
                    .get();
        } catch (Exception e) {
            log.error(e.getMessage() + ": " + url);
            throw e;
        }
    }

    public Stream<String> getAllHrefNotPdfNotImage(Document doc, boolean extractByFileLoading) {
        Pair<Document, File> pair = null;
        if (extractByFileLoading) {
            pair = loadJsoupDoc(doc.location());
            doc = pair.getFirst();
        }
        Elements links = null;
        if (doc != null) {
            links = doc.select(HREF_NOT_PDF_NOT_IMAGE_NOT_EXCEL_CSS_QUERY);
        }
        if (pair != null) {
            deleteTempFile(pair.getSecond());
        }
        if (links != null && !links.isEmpty()) {
            return links.stream().filter(Objects::nonNull).map(e -> e.absUrl(HREF_CSS_ATTRIBUTE).trim()).distinct().filter(s -> !s.isEmpty());
        } else {
            return Stream.empty();
        }

    }

    private Pair<Document, File> loadJsoupDoc(String url) {
        File tmp = null;
        Document doc = null;
        try {
            tmp = File.createTempFile("temp-file-jsoup", ".tmp");
            FileUtils.copyURLToFile(
                    new URL(url),
                    tmp);
            doc = Jsoup.parse(tmp, "UTF-8", url);
        } catch (Exception e) {
            deleteTempFile(tmp);
            log.error(e.getMessage() + ": " + url);
        }
        return new Pair<>(doc, tmp);
    }

    private void deleteTempFile(File tmp) {
        try {
            Files.deleteIfExists(tmp.toPath());
        } catch (IOException e) {
            log.error("Cannot delete temp file: " + tmp, e);
        }
    }
}
