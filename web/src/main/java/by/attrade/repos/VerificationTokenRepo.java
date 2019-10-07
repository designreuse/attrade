package by.attrade.repos;

import by.attrade.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepo extends CrudRepository<VerificationToken, String> {
}
