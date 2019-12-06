package by.attrade.component;

import by.attrade.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CategoryHierarchy implements ICategoryHierarchy {
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Map<Long, Node> hierarchy;
    private List<Category> root;
    private Map<Long, List<Category>> childMap;

    @Override
    public void build(List<Category> categories, Comparator<Category> comparator, List<Predicate<Category>> filters) {
        Lock lock = readWriteLock.writeLock();
        try {
            lock.lock();
            init();
            fillHierarchy(categories, filters);
            adjustHierarchy();
            sortChild(comparator);
            sortRoot(comparator);
            adjustChildMap();
        } finally {
            lock.unlock();
        }
    }

    private void init() {
        hierarchy = new HashMap<>();
        root = new ArrayList<>();
        childMap = new HashMap<>();
    }

    private void adjustChildMap() {
        childMap = hierarchy.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v-> v.getValue().getChild()));
    }

    private void sortRoot(Comparator<Category> comparator) {
        root.sort(comparator);
    }

    private void sortChild(Comparator<Category> comparator) {
        hierarchy.values().forEach(node -> node.sortChild(comparator));
    }

    @Override
    public List<Category> getAncestors(Long id) {
        Lock lock = readWriteLock.readLock();
        try {
            lock.lock();
            Node node = hierarchy.get(id);
            return node.getChild();
        } finally {
            lock.unlock();
        }

    }

    @Override
    public List<Category> getDescendants(Long id) {
        Lock lock = readWriteLock.readLock();
        try {
            lock.lock();
            Queue<Category> queue = new LinkedList<>();
            Category category = hierarchy.get(id).getCategory();
            queue.add(category);
            Long currId = category.getParent();
            while (currId != null) {
                category = hierarchy.get(currId).getCategory();
                queue.add(category);
                currId = category.getParent();
            }
            return (List<Category>) queue;
        } finally {
            lock.unlock();
        }
    }
    @Override
    public List<Category> getRootCategories(){
        return root;
    }

    @Override
    public Map<Long, List<Category>> getChildMap(){
        return childMap;
    }

    private void adjustHierarchy() {
        hierarchy.values().forEach(node -> {
            Long parentId = node.getCategory().getParent();
            if (parentId == 0) {
                root.add(node.getCategory());
                return;
            }
            Node parentNode = hierarchy.get(parentId);
            parentNode.addChild(node.getCategory());
            node.setParent(parentNode.getCategory());
        });
    }

    private void fillHierarchy(List<Category> categories, List<Predicate<Category>> filters) {
        for (Category c : categories) {
            if(isPassFilters(c,filters)){
                putAsNode(c);
            }
        }
    }

    private boolean isPassFilters(Category c, List<Predicate<Category>> filters) {
        if (filters == null) {
            return true;
        }
        for (Predicate<Category> p: filters) {
            if (!p.test(c)){
                return false;
            }
        }
        return true;
    }

    private void putAsNode(Category c) {
        hierarchy.put(c.getId(), new Node(c, null, new ArrayList<>()));
    }


    @Getter
    @Setter
    @AllArgsConstructor
    private static class Node {
        private Category category;
        private Category parent;
        private List<Category> child;

        private void addChild(Category c) {
            child.add(c);
        }

        private void sortChild(Comparator<Category> comparator) {
            child.sort(comparator);
        }
    }
}
