package by.attrade.service;

import by.attrade.domain.Category;
import by.attrade.repos.CategoryRepo;
import by.attrade.service.categoryPathAdjuster.CategoryPathByNameAdjusterService;
import by.attrade.service.categoryPathAdjuster.ICategoryPathAdjuster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryPathByNameAdjusterService adjuster;

    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    public Optional<Category> findByName(String name) {
        return categoryRepo.findByName(name);
    }

    public Category saveShaneOfCategory(List<Category> categories) {
        Category last = null;
        for (Category c : categories) {
            Optional<Category> optional = findByName(c.getName());
            if (optional.isPresent()) {
                last = optional.get();
            } else {
                if (last == null) {
                    c.setParent(0L);
                } else {
                    c.setParent(last.getId());
                }
                last = save(c);
            }
        }
        return last;
    }

    public void updatePaths(String langFrom, String langTo, int sizeBunch) throws Exception {
        long count = categoryRepo.count();
        long remainder = count % sizeBunch;
        int countPages;
        if (remainder > 0) {
            countPages = (int) (count / sizeBunch) + 1;
        } else {
            countPages = (int) (count / sizeBunch);
        }
        for (int i = 0; i < countPages; i++) {
            Page<Category> products = getCategories(i, sizeBunch);
            List<Category> content = products.getContent();
            adjuster.adjustPaths(content, langFrom, langTo);
            saveAll(content);
        }
    }

    public Page<Category> getCategories(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return categoryRepo.findAll(pageable);
    }

    public List<Category> saveAll(List<Category> categories) {
        return categoryRepo.saveAll(categories);
    }

    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

}
