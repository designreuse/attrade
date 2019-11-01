package by.attrade.service;

import by.attrade.domain.ProductProperty;
import by.attrade.repos.ProductPropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPropertyService {
    @Autowired
    private ProductPropertyRepo productPropertyRepo;

    public ProductProperty save(ProductProperty productProperty) {
        return productPropertyRepo.save(productProperty);
    }
    public List<ProductProperty> saveAll(List<ProductProperty> properties){
        return productPropertyRepo.saveAll(properties);
    }
}
