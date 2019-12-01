package by.attrade.controller.rest;

import by.attrade.domain.Category;
import by.attrade.domain.Product;
import by.attrade.domain.Views;
import by.attrade.service.search.CategorySearchService;
import by.attrade.service.search.ProductSearchService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private CategorySearchService categorySearchService;
    @Autowired
    private ProductSearchService productSearchService;

    @GetMapping("category")
    @JsonView(Views.IdNamePathIcon.class)
    public List<Category> searchCategory(
            @RequestParam String text,
            @PageableDefault(size = 10) Pageable pageable
    ){
        return categorySearchService.searchCategory(text, pageable);
    }

    @GetMapping("product")
    @JsonView(Views.IdNameCodePathPicturePriceCategoryQuantityInStockQuantitySupplierUnit.class)
    public List<Product> searchProduct(
            @RequestParam String text,
            @PageableDefault(size = 50) Pageable pageable
    ){
        return productSearchService.searchProduct(text, pageable);
    }
}
