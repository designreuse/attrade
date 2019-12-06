package by.attrade.service.categoryPathExtractor;

import by.attrade.domain.Category;

public interface ICategoryPathExtractor {
    String getPath(Category category) throws Exception;
}
