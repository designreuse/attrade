package by.attrade.service.search;

import by.attrade.domain.Category;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static by.attrade.service.search.HibernateSearchService.ANY_CHAR;

@Service
public class CategorySearchService {
    @Autowired
    private HibernateSearchService searchService;

    public List<Category> searchCategoryByMoreThan3Char(String text) {
        QueryBuilder queryBuilder = searchService.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Category.class)
                .get();
        Query query = queryBuilder
                .keyword()
                .onFields(
                        "name",
                        "products.name",
                        "products.code"
                )
                .matching(text)
                .createQuery();
        FullTextQuery jpaQuery
                = searchService.createFullTextQuery(query, Category.class);
        return jpaQuery.getResultList();
    }

    public List<Category> searchCategoryByLessThan3CharIncl(String text) {
        QueryBuilder queryBuilder = searchService.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Category.class)
                .get();
        Query query = queryBuilder
                .keyword()
                .wildcard()
                .onFields(
                        "name",
                        "products.name",
                        "products.code"
                )
                .matching(text.trim() + ANY_CHAR)
                .createQuery();
        FullTextQuery jpaQuery
                = searchService.createFullTextQuery(query, Category.class);
        return jpaQuery.getResultList();
    }

    public List<Category> searchProduct(String text) {
        text = text.trim();
        if (text.length() == 0) return Collections.emptyList();
        if (text.length() > 3) {
            return searchCategoryByMoreThan3Char(text);
        } else {
            return searchCategoryByLessThan3CharIncl(text);
        }
    }
}
