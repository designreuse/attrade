package by.attrade.service.jsoup.extractor;

import by.attrade.domain.ExtractorErrorUrl;
import by.attrade.repos.ExtractorErrorUrlRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtractorErrorUrlService {
    @Autowired
    private ExtractorErrorUrlRepo urlRepo;

    public List<ExtractorErrorUrl> saveAll(List<ExtractorErrorUrl> extractorErrors) {
        return urlRepo.saveAll(extractorErrors);
    }

}
