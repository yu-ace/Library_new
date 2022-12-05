package com.example.lib_text.service.impl;

import com.example.lib_text.dao.ICategoryDao;
import com.example.lib_text.model.Category;
import com.example.lib_text.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    ICategoryDao categoryDao;

    @Override
    public Category newCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryDao.save(category);
        return category;
    }

    @Override
    public Page<Category> getCategoryList(Pageable pageable) {
        return categoryDao.findAll(pageable);
    }

    @Override
    public Category getCategoryName(int id) {
        return categoryDao.findById(id);
    }
}
