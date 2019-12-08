package by.attrade.service.categoryPathAdjuster;

import by.attrade.domain.Category;

import java.util.List;

public interface ICategoryPathAdjuster {
    void adjustPaths(List<Category> categories) throws Exception;

    static void chainAdjustPaths(List<Category> categories, ICategoryPathAdjuster... adjusters) throws Exception {
        for (ICategoryPathAdjuster adjuster : adjusters) {
            adjuster.adjustPaths(categories);
        }
    }
}
