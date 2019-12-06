package by.attrade.component;

import by.attrade.domain.Category;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface ICategoryHierarchy {
    void build(List<Category> categories, Comparator<Category> comparator, List<Predicate<Category>> filters);
    List<Category> getAncestors(Long id);
    List<Category> getDescendants(Long id);
    List<Category> getRootCategories();
    Map<Long, List<Category>> getChildMap();
}
