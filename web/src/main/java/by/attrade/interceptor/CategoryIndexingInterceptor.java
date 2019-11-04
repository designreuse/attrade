package by.attrade.interceptor;

import by.attrade.domain.Category;
import org.hibernate.search.indexes.interceptor.EntityIndexingInterceptor;
import org.hibernate.search.indexes.interceptor.IndexingOverride;

public class CategoryIndexingInterceptor implements
        EntityIndexingInterceptor<Category> {
    @Override
    public IndexingOverride onAdd(Category entity) {
        if (entity.isInvisible())
            return IndexingOverride.SKIP;

        return IndexingOverride.APPLY_DEFAULT;
    }

    @Override
    public IndexingOverride onUpdate(Category entity) {
        if (entity.isInvisible())
            return IndexingOverride.REMOVE;

        return IndexingOverride.APPLY_DEFAULT;
    }

    @Override
    public IndexingOverride onDelete(Category entity) {
        return IndexingOverride.APPLY_DEFAULT;
    }

    @Override
    public IndexingOverride onCollectionUpdate(Category entity) {
        if (entity.isInvisible())
            return IndexingOverride.REMOVE;

        return IndexingOverride.APPLY_DEFAULT;
    }
}
