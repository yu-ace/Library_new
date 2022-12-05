package com.example.lib_text.dao;

import com.example.lib_text.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookDao extends JpaRepository<Book,Integer> {
    Page<Book> findByCategoryId(int categoryId, Pageable pageable);
    Book findById(int id);
    Book findByName(String name);
    Page<Book> findByAuthor(String author,Pageable pageable);
}
