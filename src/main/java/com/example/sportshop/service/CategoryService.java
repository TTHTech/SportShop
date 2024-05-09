package com.example.sportshop.service;


import com.example.sportshop.model.Category;
import com.example.sportshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public Category findByName(String name){
        return this.categoryRepository.findCategoryByName(name);
    }
    public Category createCategory(Category category)
    {
        return this.categoryRepository.save(category);
    }
    public List<Category> getAllCategory()
    {
        return this.categoryRepository.findAll();
    }
}
