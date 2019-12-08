package by.attrade.service;

import by.attrade.domain.Dimension;
import org.springframework.stereotype.Service;

@Service
public class DimensionParserService {
    private static final String X_SPLITER = "x";

    public Dimension parseXspliterMmToMm(String dimensionString) {
        String[] split = dimensionString.split(X_SPLITER);
        Dimension dimension = new Dimension();
        dimension.setHeight(Double.parseDouble(split[0].trim()));
        dimension.setWidth(Double.parseDouble(split[1].trim()));
        dimension.setDepth(Double.parseDouble(split[2].trim()));
        return dimension;
    }
}
