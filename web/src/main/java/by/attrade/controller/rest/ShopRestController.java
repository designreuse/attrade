package by.attrade.controller.rest;

import by.attrade.domain.Book;
import by.attrade.domain.Shop;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/shops")
public class ShopRestController {
    @GetMapping
    public Set<Book> test(
            @RequestParam Shop shop
    ) {
        return shop.getBooks();
    }
}
