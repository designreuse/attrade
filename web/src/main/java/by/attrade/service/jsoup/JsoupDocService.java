package by.attrade.service.jsoup;

import by.attrade.config.JsoupConfig;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
                    .userAgent(jsoupConfig.getUserAgent())
                    .referrer(jsoupConfig.getReferrer())
                    .timeout(jsoupConfig.getTimeout())
                    .get();
        } catch (Exception e) {
            log.error(e.getMessage() + ": " + url);
            throw e;
        }
    }

    public Stream<String> getAllHrefNotPdfNotImage(Document doc) {
        Elements links = doc.select(HREF_NOT_PDF_NOT_IMAGE_NOT_EXCEL_CSS_QUERY);
        return links.stream().map(e -> e.absUrl(HREF_CSS_ATTRIBUTE));
    }
}
