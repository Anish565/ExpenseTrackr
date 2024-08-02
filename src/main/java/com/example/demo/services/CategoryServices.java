package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repositories.CategoryRepository;

import com.example.demo.entities.*;

public class CategoryServices {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        List<Category> categories = (List<Category>) categoryRepository.findAll();

        return categories;
    }

    public Optional<Category> findCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

}
