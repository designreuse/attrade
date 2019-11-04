package by.attrade.interceptor;

import by.attrade.domain.Product;
import org.hibernate.search.indexes.interceptor.EntityIndexingInterceptor;
import org.hibernate.search.indexes.interceptor.IndexingOverride;

public class ProductIndexingInterceptor implements
        EntityIndexingInterceptor<Product> {
    @Override
    public IndexingOverride onAdd(Product entity) {
        if (entity.isInvisible())
            return IndexingOverride.SKIP;

        return IndexingOverride.APPLY_DEFAULT;
    }

    @Override
    public IndexingOverride onUpdate(Product entity) {
        if (entity.isInvisible())
            return IndexingOverride.REMOVE;

        return IndexingOverride.APPLY_DEFAULT;
    }

    @Override
    public IndexingOverride onDelete(Product entity) {
        return IndexingOverride.APPLY_DEFAULT;
    }

    @Override
    public IndexingOverride onCollectionUpdate(Product entity) {
        if (entity.isInvisible())
            return IndexingOverride.REMOVE;

        return IndexingOverride.APPLY_DEFAULT;
    }
}
