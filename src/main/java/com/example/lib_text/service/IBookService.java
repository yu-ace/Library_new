package com.example.lib_text.service;

import com.example.lib_text.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService {
    Book newBook(String name,double price,String author,int count,int categoryId);
    Book getBookById(int id);
    Book getBookByName(String name);
    Page<Book> getBookByCategoryId(int id, Pageable pageable);
    Page<Book> getBookByAuthor(String author,Pageable pageable);
    void returnBook(int bookId);
    void borrowBook(int id);
    Page<Book> getBookList(Pageable pageable);
    Book changeBookInitialCount(int bookId);
    void reserveBook(int bookId);
}
