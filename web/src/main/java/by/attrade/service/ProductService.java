package by.attrade.service;

import by.attrade.domain.Product;
import by.attrade.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public Product save(Product product) {
        return productRepo.save(product);
    }
    public boolean existsByCode(Product product){
        return productRepo.existsByCode(product.getCode());
    }
}
