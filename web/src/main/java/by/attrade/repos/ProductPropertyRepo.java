package by.attrade.repos;

import by.attrade.domain.ProductProperty;
import by.attrade.domain.ProductPropertyId;
import by.attrade.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductPropertyRepo extends JpaRepository<ProductProperty,ProductPropertyId>{
    @Query("select d.property, d.propertyData from ProductProperty d where d.property in :properties GROUP BY d.property.id, d.propertyData order by d.property.id ASC, d.propertyData ASC")
    List<?> getPropertyPropertyData(@Param("properties")List<Property> properties);
}
