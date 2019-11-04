package by.attrade.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "product.search")
//TODO
public class ProductSearchConfig {
    private static final SortField SEARCH_ANALYSER = SortField.FIELD_SCORE;
    private List<String> sortFieldsNames = new ArrayList<>();
    private List<String> sortFieldsTypes = new ArrayList<>();
    private List<Boolean> sortReverse = new ArrayList<>();
    private Sort sort = new Sort(getSortFields());
    private Integer minGramSize;
    private String[] onFields;

    private SortField[] getSortFields() {
        int count = sortFieldsNames.size() + 1;
        SortField[] sortFields = new SortField[count];
        sortFields[0] = SEARCH_ANALYSER;
        for (int i = 1; i < count; i++) {
            sortFields[i] =
                    new SortField(
                            sortFieldsNames.get(i),
                            SortField.Type.valueOf(sortFieldsTypes.get(i)),
                            sortReverse.get(i)
                    );

        }
        return sortFields;
    }
}
