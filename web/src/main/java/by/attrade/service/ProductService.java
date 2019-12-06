package by.attrade.service;

import by.attrade.domain.Product;
import by.attrade.repos.ProductRepo;
import by.attrade.service.productPathExtractor.IProductPathExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public Product save(Product product) {
        return productRepo.save(product);
    }
    public List<Product> saveAll(List<Product> products) {
        return productRepo.saveAll(products);
    }
    public boolean existsByCode(Product product){
        return productRepo.existsByCode(product.getCode());
    }
    public Page<Product> getProducts(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        return productRepo.findAll(pageable);
    }
    public void updatePaths(IProductPathExtractor extractor, int sizeBunch) throws Exception {
        long count = productRepo.count();
        long remaider = count % sizeBunch;
        int countPages;
        if(remaider>0){
            countPages = (int) (count/sizeBunch)+1;
        }else {
            countPages = (int) (count/sizeBunch);
        }
        for (int i = 0; i < countPages; i++) {
            Page<Product> products = getProducts(i, sizeBunch);
            List<Product> content = products.getContent();
            for (Product product: content) {
                String path = extractor.getPath(product);
                product.setPath(path);
            }
            saveAll(content);
        }
    }
    public List<String> getUrls(){
        return productRepo.getUrls();
    }
}
