package by.attrade.service;

import by.attrade.domain.Category;
import by.attrade.domain.Property;
import by.attrade.repos.PropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepo propertyRepo;

    public Property save(Property property) {
        return propertyRepo.save(property);
    }

    public List<Property> findByCategoryAndName(Property property) {
        return propertyRepo.findByCategoryAndName(property.getCategory(), property.getName());
    }

    public List<Property> saveAll(List<Property> properties) {
        return propertyRepo.saveAll(properties);
    }

    public List<Property> saveAll(List<Property> properties, Category category) {
        for (int i = 0; i < properties.size(); i++) {
            Property p = properties.get(i);
            p.setCategory(category);
            Property save;
            List<Property> list = findByCategoryAndName(p);
            if (list.isEmpty()) {
                save = save(p);
            } else {
                save = list.get(0);
            }
            properties.set(i, save);
        }
        return properties;
    }
}
