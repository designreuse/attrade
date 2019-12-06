package by.attrade.controller.rest;

import by.attrade.domain.Category;
import by.attrade.service.CategoryHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("category/hierarchy")
public class CategoryHierarchyRestController {
    @Autowired
    private CategoryHierarchyService categoryHierarchyService;

    @GetMapping("child-map")
    public Map<Long, List<Category>> childMap(){
        return categoryHierarchyService.getChildMap();
    }
    @GetMapping("root-categories")
    public List<Category> getRootCategories(){
        return categoryHierarchyService.getRootCategories();
    }
}
