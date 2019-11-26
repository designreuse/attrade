package by.attrade.repos;

import by.attrade.domain.Product;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,Long>{
    Optional<Product> findByCode(String code);
    boolean existsByCode(String code);
//    @Modifying
//    @Query("UPDATE Product c SET c.path = :path WHERE c.id = :id")
//    int updatePath(@Param("id") int id, @Param("path") String path);
}
