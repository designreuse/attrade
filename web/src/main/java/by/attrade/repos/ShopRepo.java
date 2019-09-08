package by.attrade.repos;

import by.attrade.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepo extends JpaRepository<Shop, Long> {
    Shop findByName(String name);
}
