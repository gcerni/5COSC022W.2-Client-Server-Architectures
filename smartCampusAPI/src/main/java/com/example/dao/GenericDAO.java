package com.example.dao;

import com.example.model.BaseModel;
import java.util.*;

public class GenericDAO<T extends BaseModel> {

    private final List<T> items;

    public GenericDAO(List<T> items) {
        this.items = items;
    }

    public List<T> getAll() {
        return items;
    }

    public T getById(String id) {
        for (T item : items) {
            // Use .equals() for String comparison
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public void add(T item) {
        if (item.getId() == null || item.getId().isEmpty()) {
            // Throw an error or handle the fact that a semantic ID is missing
            throw new IllegalArgumentException("Item ID must be provided for " + item.getClass().getSimpleName());
        }
        items.add(item);
    }

    public void update(T updatedItem) {
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            // Use .equals() and handle potential nulls
            if (item.getId() != null && item.getId().equals(updatedItem.getId())) {
                items.set(i, updatedItem);
                return;
            }
        }
    }

    public void delete(String id) {
        items.removeIf(item -> item.getId() != null && item.getId().equals(id));
    }
}
