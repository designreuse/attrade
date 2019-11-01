package by.attrade.repos;

import by.attrade.domain.Category;
import by.attrade.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepo extends JpaRepository<Property,Long>{
    List<Property> findByCategoryAndName(Category category, String name);
}
