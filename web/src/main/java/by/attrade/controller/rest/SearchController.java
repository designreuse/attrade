package by.attrade.controller.rest;

import by.attrade.domain.Category;
import by.attrade.domain.Product;
import by.attrade.service.search.CategorySearchService;
import by.attrade.service.search.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private CategorySearchService categorySearchService;
    @Autowired
    private ProductSearchService productSearchService;

    @GetMapping("category/{text}")
    public List<Category> searchCategory(
            @PathVariable(required = false) String text,
            @PageableDefault(size = 16) Pageable pageable
    ){
        return categorySearchService.searchCategory(text, pageable);
    }

    @GetMapping("product/{text}")
    public List<Product> searchProduct(
            @PathVariable(required = false) String text,
            @PageableDefault(size = 32) Pageable pageable
    ){
        return productSearchService.searchProduct(text, pageable);
    }
}
