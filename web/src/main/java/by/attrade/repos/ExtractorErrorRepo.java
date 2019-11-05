package by.attrade.repos;

import by.attrade.domain.ExtractorError;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtractorErrorRepo extends JpaRepository<ExtractorError, Long> {
}
