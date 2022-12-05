package com.example.lib_text.service.impl;

import com.example.lib_text.dao.IHistoryDao;
import com.example.lib_text.model.History;
import com.example.lib_text.service.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HistoryService implements IHistoryService {

    @Autowired
    IHistoryDao historyDao;

    @Override
    public History addHistory(int userId, int bookId, int count) {
        History history = new History();
        history.setUserId(userId);
        history.setBookId(bookId);
        history.setCount(count);
        history.setType("上架");
        history.setTime(new Date());
        historyDao.save(history);
        return history;
    }

    @Override
    public History borrowBook(int userId, int bookId) {
        History history = new History();
        history.setUserId(userId);
        history.setBookId(bookId);
        history.setCount(1);
        history.setType("借取");
        history.setTime(new Date());
        historyDao.save(history);
        return history;
    }

    @Override
    public History returnBook(int userId, int bookId) {
        History history = new History();
        history.setUserId(userId);
        history.setBookId(bookId);
        history.setCount(1);
        history.setType("归还");
        history.setTime(new Date());
        historyDao.save(history);
        return history;
    }

    @Override
    public Page<History> getHistoryList(Pageable pageable) {
        return historyDao.findAll(pageable);
    }

    @Override
    public Page<History> getHistoryListByUserId(int userId, Pageable pageable) {
        return historyDao.findByUserId(userId,pageable);
    }

    @Override
    public Page<History> getHistoryLIstByBookId(int bookId, Pageable pageable) {
        return historyDao.findByBookId(bookId,pageable);
    }

    @Override
    public Page<History> getHistoryListByType(String type, Pageable pageable) {
        return historyDao.findByType(type,pageable);
    }

    @Override
    public Page<History> getHistoryListByUserIdAndBookId(int userId, int bookId, Pageable pageable) {
        return historyDao.findByUserIdAndBookId(userId,bookId,pageable);
    }

    @Override
    public Page<History> getHistoryListByUserIdAndType(int userId, String type, Pageable pageable) {
        return historyDao.findByUserIdAndType(userId,type,pageable);
    }

    @Override
    public History buyBook(int userId, int bookId) {
        History history = historyDao.findByUserIdAndBookId(userId,bookId);
        history.setType("购买");
        historyDao.save(history);
        return history;
    }

    @Override
    public History receiveBook(int userId, int bookId) {
        History history = new History();
        history.setUserId(userId);
        history.setBookId(bookId);
        history.setCount(1);
        history.setType("预约");
        history.setTime(new Date());
        historyDao.save(history);
        return history;
    }

    @Override
    public History changeBookType(int userId, int bookId) {
        History history = historyDao.findByUserIdAndBookIdAndType(userId,bookId,"预约");
        history.setType("借取");
        historyDao.save(history);
        return history;
    }

    @Override
    public History getHistoryByUserIdAndBookId(int userId, int bookId) {
        return historyDao.findByUserIdAndBookId(userId,bookId);
    }

    @Override
    public History getHistoryByUserIdAndBookIdAndType(int userId, int bookId, String type) {
        return historyDao.findByUserIdAndBookIdAndType(userId,bookId,type);
    }
}
