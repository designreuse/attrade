package by.attrade.repos;

import by.attrade.domain.ProductProperty;
import by.attrade.domain.ProductPropertyId;
import by.attrade.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPropertyRepo extends JpaRepository<ProductProperty,ProductPropertyId>{
}
