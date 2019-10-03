package by.attrade.core.repos;

import by.attrade.core.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepo extends JpaRepository<Shop, Long> {
    Shop findByName(String name);
}
