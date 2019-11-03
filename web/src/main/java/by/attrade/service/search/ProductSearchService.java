package by.attrade.service.search;

import by.attrade.domain.Product;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static by.attrade.service.search.HibernateSearchService.ANY_CHAR;

@Service
public class ProductSearchService {
    @Autowired
    private HibernateSearchService searchService;

    public List<Product> searchProductByMoreThan3Char(String text) {
        QueryBuilder queryBuilder = searchService.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Product.class)
                .get();
        Query query = queryBuilder
                .keyword()
                .onFields(
                        "name",
                        "code",
                        "category.name"
                )
                .matching(text)
                .createQuery();
        FullTextQuery jpaQuery
                = searchService.createFullTextQuery(query, Product.class);
        return jpaQuery.getResultList();
    }

    public List<Product> searchProductByLessThan3CharIncl(String next) {
        QueryBuilder queryBuilder = searchService.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Product.class)
                .get();
        Query query = queryBuilder
                .keyword()
                .wildcard()
                .onFields(
                        "name",
                        "code",
                        "category.name"
                )
                .matching(next.trim() + ANY_CHAR)
                .createQuery();
        FullTextQuery jpaQuery
                = searchService.createFullTextQuery(query, Product.class);
        return jpaQuery.getResultList();
    }

    public List<Product> searchProduct(String text) {
        text = text.trim();
        if (text.length() == 0) return Collections.emptyList();
        if (text.length() > 3) {
            return searchProductByMoreThan3Char(text);
        } else {
            return searchProductByLessThan3CharIncl(text);
        }
    }
}
