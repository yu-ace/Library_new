package com.example.lib_text.service;

import com.example.lib_text.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IHistoryService {
    History addHistory(int userId,int bookId,int count);
    History borrowBook(int userId,int bookId);
    History returnBook(int userId,int bookId);
    Page<History> getHistoryList(Pageable pageable);
    Page<History> getHistoryListByUserId(int userId,Pageable pageable);
    Page<History> getHistoryLIstByBookId(int bookId,Pageable pageable);
    Page<History> getHistoryListByType(String type,Pageable pageable);
    Page<History> getHistoryListByUserIdAndBookId(int userId,int bookId,Pageable pageable);
    Page<History> getHistoryListByUserIdAndType(int userId,String type,Pageable pageable);
    History buyBook(int userId,int bookId);
    History receiveBook(int userId,int bookId);
    History changeBookType(int userId,int bookId);
    History getHistoryByUserIdAndBookId(int userId,int bookId);
    History getHistoryByUserIdAndBookIdAndType(int userId,int bookId,String type);
}
