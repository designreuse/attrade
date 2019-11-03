package by.attrade.service.search;

import org.apache.lucene.search.Query;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Service
public class HibernateSearchService {
    static final int THRESHOLD = 3;
    static final  String ANY_CHAR = "**";
    private final EntityManager entityManager;
    private FullTextEntityManager fullTextEntityManager;

    @Autowired
    public HibernateSearchService(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }
    public SearchFactory getSearchFactory(){
        return fullTextEntityManager.getSearchFactory();
    }

    @PostConstruct
    public void initializeHibernateSearch() {
        try {
            fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager
                    .createIndexer()
                    .purgeAllOnStart(true)
                    .optimizeOnFinish(true)
                    .batchSizeToLoadObjects(100)
                    .threadsToLoadObjects(10)
                    .startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public FullTextQuery createFullTextQuery(Query query, Class<?> productClass) {
        return fullTextEntityManager.createFullTextQuery(query,productClass);
    }
}
