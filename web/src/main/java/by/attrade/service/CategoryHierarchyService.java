package by.attrade.service;

import by.attrade.component.ICategoryHierarchy;
import by.attrade.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class CategoryHierarchyService implements ICategoryHierarchy {
    private static final Comparator<Category> comparator = Comparator.comparing(Category::getPriority, Comparator.nullsFirst(Comparator.naturalOrder()));
    private static final Predicate<Category> isVisible = x -> !x.isInvisible();
    private static final List<Predicate<Category>> filters = new ArrayList<>();

    static {
        filters.add(isVisible);
    }

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ICategoryHierarchy categoryHierarchy;

    private List<Category> all;

    @Override
    public void build(List<Category> categories, Comparator<Category> comparator, List<Predicate<Category>> filters) {
        categoryHierarchy.build(categories, comparator, filters);
    }

    public List<Category> getAncestors(Long id) {
        return categoryHierarchy.getAncestors(id);
    }

    public List<Category> getDescendants(Long id) {
        return categoryHierarchy.getDescendants(id);
    }

    @Override
    public List<Category> getRootCategories() {
        return categoryHierarchy.getRootCategories();
    }

    @Override
    public Map<Long, List<Category>> getChildMap() {
        return categoryHierarchy.getChildMap();
    }
    public Category getCategoryByPath(String path){
        return all.stream().filter(x->x.getPath().equals(path)).findFirst().orElse(null);
    }

    @PostConstruct
    @Scheduled(fixedDelayString = "${scheduled.CategoryHierarchyService.build.fixedDelayInMilliseconds}", initialDelayString = "${scheduled.CategoryHierarchyService.build.initialDelayInMilliseconds}")
    public void init() {
        all = categoryService.findAll();
        build(all, comparator, filters);
    }
}
