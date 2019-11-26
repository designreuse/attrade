package by.attrade.service.productPathExtractor;

import by.attrade.domain.Product;

public interface IProductPathExtractor {
    String getPath(Product product) throws Exception;
}
