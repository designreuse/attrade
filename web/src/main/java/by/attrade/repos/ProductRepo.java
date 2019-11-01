package by.attrade.repos;

import by.attrade.domain.Product;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,Long>{
    Optional<Product> findByCode(String code);
    boolean existsByCode(String code);
}
