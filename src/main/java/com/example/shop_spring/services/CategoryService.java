package com.example.shop_spring.services;

import com.example.shop_spring.models.Category;
import com.example.shop_spring.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
@Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    //Данный метод позволяет вернуть все категории
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    //Данный метод позволяет сохранить категории
    @Transactional
    public void saveCategory(Category category){
    categoryRepository.save(category);
    }

    // Данный метод позволяет вернуть категории по ID
    public Category getCategoryID(int id){
    Optional<Category> optionalCategory = categoryRepository.findById(id);
    return optionalCategory.orElse(null);
    }

    // Данный метод позволяет обновить данные о категории
    @Transactional
    public void updateCategory(int id, Category category){
    category.setId(id);
    categoryRepository.save(category);
    }

    // Данный метод позволяет удалить категории по ID
    @Transactional
    public void deleteCategory(int id){
    categoryRepository.deleteById(id);
    }


}
