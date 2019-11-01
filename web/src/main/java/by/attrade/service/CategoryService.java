package by.attrade.service;

import by.attrade.domain.Category;
import by.attrade.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    public Category save(Category category){
        return categoryRepo.save(category);
    }
    public Optional<Category> findByName(String name){return categoryRepo.findByName(name);}
    public Category saveShaneOfCategory(List<Category> categories){
        Category last = null;
        for (Category c: categories){
            Optional<Category> optional = findByName(c.getName());
            if (optional.isPresent()){
                last = optional.get();
            }else {
                if (last == null){
                    c.setParent(0L);
                }else {
                    c.setParent(last.getId());
                }
                last = save(c);
            }
        }
        return last;
    }
}
