package com.example.lib_text.service.impl;

import com.example.lib_text.dao.IBookDao;
import com.example.lib_text.model.Book;
import com.example.lib_text.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService implements IBookService {

    @Autowired
    IBookDao bookDao;

    @Override
    public Book newBook(String name, double price, String author, int count, int categoryId) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setAuthor(author);
        book.setCount(count);
        book.setCategoryId(categoryId);
        book.setInitialCount(count);
        bookDao.save(book);
        return book;
    }

    @Override
    public Book getBookById(int id) {
        return bookDao.findById(id);
    }

    @Override
    public Book getBookByName(String name) {
        return bookDao.findByName(name);
    }

    @Override
    public Page<Book> getBookByCategoryId(int id, Pageable pageable) {
        return bookDao.findByCategoryId(id,pageable);
    }

    @Override
    public Page<Book> getBookByAuthor(String author, Pageable pageable) {
        return bookDao.findByAuthor(author,pageable);
    }

    @Override
    public void returnBook(int id) {
        Book book = bookDao.findById(id);
        book.setCount(book.getCount() + 1);
        bookDao.save(book);
    }

    @Override
    public void borrowBook(int id) {
        Book book = bookDao.findById(id);
        book.setCount(book.getCount() - 1);
        bookDao.save(book);
    }

    @Override
    public Page<Book> getBookList(Pageable pageable) {
        return bookDao.findAll(pageable);
    }

    @Override
    public Book changeBookInitialCount(int bookId) {
        Book book = bookDao.findById(bookId);
        book.setInitialCount(book.getInitialCount() - 1);
        bookDao.save(book);
        return book;
    }

    @Override
    public void reserveBook(int bookId) {
        Book book = bookDao.findById(bookId);
        book.setCount(book.getCount() - 1);
        bookDao.save(book);
    }
}
