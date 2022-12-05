package com.example.lib_text.dao;

import com.example.lib_text.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryDao extends JpaRepository<Category,Integer> {
    Category findById(int id);
}
