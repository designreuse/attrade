package by.attrade.service.jsoup;

import by.attrade.config.JsoupConfig;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class JsoupDocService {
    @Autowired
    private JsoupConfig jsoupConfig;

    public Document getJsoupDoc(String url) throws IOException {
        try {
            return Jsoup.connect(url)
                    .userAgent(jsoupConfig.getUserAgent())
                    .referrer(jsoupConfig.getReferrer())
//                    .timeout(jsoupConfig.getTimeout())
                    .get();
        } catch (Exception e) {
            log.error(e.getMessage() + ": "+ url);
            throw e;
        }
    }
}
