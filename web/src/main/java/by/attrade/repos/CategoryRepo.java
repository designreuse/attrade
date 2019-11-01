package by.attrade.repos;

import by.attrade.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category,Long>{
    Optional<Category> findByName(String name);
}
