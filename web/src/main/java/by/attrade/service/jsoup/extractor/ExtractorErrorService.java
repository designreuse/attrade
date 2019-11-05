package by.attrade.service.jsoup.extractor;

import by.attrade.domain.ExtractorError;
import by.attrade.repos.ExtractorErrorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtractorErrorService {
    @Autowired
    private ExtractorErrorRepo errorRepo;

    public ExtractorError save(ExtractorError extractorError) {
        return errorRepo.save(extractorError);
    }
}
