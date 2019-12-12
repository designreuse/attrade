package by.attrade.service.jsoup;

import by.attrade.domain.Category;
import by.attrade.domain.Dimension;
import by.attrade.domain.Product;
import by.attrade.domain.Property;
import by.attrade.type.Unit;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Locale;

public interface IProductExtractor {
    String getVendorCode(Document doc);

    String getName(Document doc);

    String getCode(Document doc);

    Unit getUnit(Document doc);

    Integer getCount(Document doc);

    Dimension getDimension(Document doc);

    Double getWeight(Document doc);

    Unit getUnitInPack(Document doc);

    Integer getCountInPack(Document doc);

    Dimension getDimensionPack(Document doc);

    Double getWeightPack(Document doc);

    Unit getUnitCarry(Document doc);

    Integer getCountInPackCarry(Document doc);

    Dimension getDimensionCarry(Document doc);

    Double getWeightCarry(Document doc);

    String getBarcode(Document doc);

    String getDescription(Document doc);

    List<Category> getCategories(Document doc);

    List<Property> getProperties(Document doc);

    List<String> getPropertyValues(Document doc);

    List<String> getImagesUrl(Document doc);

    List<String> getDescriptionImagesUrl(Document doc);

    String getUrl();

    Locale getLocale();

    boolean isHrefExtractsByFileLoading();
}
