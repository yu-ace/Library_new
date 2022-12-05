package com.example.lib_text.service;

import com.example.lib_text.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {
    Category newCategory(String name);
    Page<Category> getCategoryList(Pageable pageable);
    Category getCategoryName(int id);
}
