package by.attrade.service.search;

import by.attrade.domain.Category;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static by.attrade.service.search.HibernateSearchService.ANY_CHAR;
import static by.attrade.service.search.HibernateSearchService.MIN_GRAM_SIZE;

@Service
public class CategorySearchService {
    @Autowired
    private HibernateSearchService searchService;

    public List<Category> searchCategoryExcessMinGramSize(String text) {
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

    public List<Category> searchCategoryInsideMinGramSize(String text) {
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

    public List<Category> searchCategory(String text) {
        text = text.trim();
        if (text.length() == 0) return Collections.emptyList();
        if (text.length() > MIN_GRAM_SIZE) {
            return searchCategoryExcessMinGramSize(text);
        } else {
            return searchCategoryInsideMinGramSize(text);
        }
    }
}
