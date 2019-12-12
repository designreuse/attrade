package by.attrade.service.search;

import by.attrade.config.HibernateSearchConfig;
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
    private final EntityManager entityManager;
    private FullTextEntityManager fullTextEntityManager;
    private final HibernateSearchConfig config;

    @Autowired
    public HibernateSearchService(EntityManagerFactory entityManagerFactory, HibernateSearchConfig hibernateSearchConfig) {
        this.entityManager = entityManagerFactory.createEntityManager();
        this.config = hibernateSearchConfig;
    }

    public SearchFactory getSearchFactory() {
        return fullTextEntityManager.getSearchFactory();
    }

    @PostConstruct
    public void init() {
        if (config.isInit()) {
            try {
                fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
                fullTextEntityManager
                        .createIndexer()
                        .purgeAllOnStart(config.isPurgeAllOnStart())
                        .optimizeOnFinish(config.isOptimizeOnFinish())
                        .batchSizeToLoadObjects(config.getBatchSizeToLoadObjects())
                        .threadsToLoadObjects(config.getThreadsToLoadObjects())
                        .startAndWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public FullTextQuery createFullTextQuery(Query query, Class<?> productClass) {
        return fullTextEntityManager.createFullTextQuery(query, productClass);
    }
}
