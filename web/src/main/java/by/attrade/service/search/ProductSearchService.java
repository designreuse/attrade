package by.attrade.service.search;

import by.attrade.domain.Product;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static by.attrade.service.search.HibernateSearchService.ANY_CHAR;
import static by.attrade.service.search.HibernateSearchService.MIN_GRAM_SIZE;

@Service
public class ProductSearchService {
    @Autowired
    private HibernateSearchService searchService;

    public List<Product> searchProductExcessMinGramSize(String text, Pageable pageable) {
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
        Sort sort = new Sort(
                SortField.FIELD_SCORE,
                new SortField("visitors", SortField.Type.INT, true));
        jpaQuery.setSort(sort);
        jpaQuery.setFirstResult((pageable.getPageNumber()-1) * pageable.getPageSize());
        jpaQuery.setMaxResults(pageable.getPageSize());
        return jpaQuery.getResultList();
    }

    public List<Product> searchProductInsideMinGramSize(String next, Pageable pageable) {
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
        Sort sort = new Sort(
                SortField.FIELD_SCORE,
                new SortField("visitors", SortField.Type.INT, true));
        jpaQuery.setSort(sort);
        jpaQuery.setFirstResult((pageable.getPageNumber()-1) * pageable.getPageSize());
        jpaQuery.setMaxResults(pageable.getPageSize());
        return jpaQuery.getResultList();
    }

    public List<Product> searchProduct(String text, Pageable pageable) {
        text = text.trim();
        if (text.length() == 0) return Collections.emptyList();
        if (text.length() > MIN_GRAM_SIZE) {
            return searchProductExcessMinGramSize(text, pageable);
        } else {
            return searchProductInsideMinGramSize(text, pageable);
        }
    }
}
