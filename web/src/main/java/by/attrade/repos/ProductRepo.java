package by.attrade.repos;

import by.attrade.domain.Category;
import by.attrade.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);

    boolean existsByCode(String code);

    @Query("select d.url from Product d")
    List<String> getUrls();

    @Query("select d.url from Product d where d.url like concat(:text,'%')")
    List<String> getUrlsStartWith(@Param("text") String text);

    @Query("select d from Product d where d.category = :category")
    Page<Product> getProductsByCategory(@Param("category") Category category, Pageable pageable);
//    @Modifying
//    @Query("UPDATE Product c SET c.path = :path WHERE c.id = :id")
//    int updatePath(@Param("id") int id, @Param("path") String path);
}
