package com.example.lib_text.controller;

import com.example.lib_text.model.Category;
import com.example.lib_text.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    @ResponseBody
    @RequestMapping(path = "/category",method = RequestMethod.GET)
    public Category getCategoryName(int categoryId){
        Category category = categoryService.getCategoryName(categoryId);
        return category;
    }
}
