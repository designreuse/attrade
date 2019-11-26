package by.attrade.service.productPathExtractor;

import by.attrade.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class Utf8OfNameProductPathExtractorService implements IProductPathExtractor {
    @Override
    public String getPath(Product product) throws UnsupportedEncodingException {
        String encode;
        try {
            encode = URLEncoder.encode(product.getName(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            log.error(product.getName(), e);
            throw e;
        }

        return encode;
    }
}
