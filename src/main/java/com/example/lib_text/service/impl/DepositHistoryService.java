package com.example.lib_text.service.impl;

import com.example.lib_text.dao.IDepositHistoryDao;
import com.example.lib_text.model.DepositHistory;
import com.example.lib_text.service.IDepositHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DepositHistoryService implements IDepositHistoryService {

    @Autowired
    IDepositHistoryDao depositHistoryDao;

    @Override
    public DepositHistory newDepositHistory(int userId, String type, double price) {
        DepositHistory depositHistory = new DepositHistory();
        depositHistory.setUserId(userId);
        depositHistory.setType(type);
        depositHistory.setPrice(price);
        depositHistory.setTime(new Date());
        depositHistoryDao.save(depositHistory);
        return depositHistory;
    }

    @Override
    public Page<DepositHistory> getDepositHistoryByUserId(int userId, Pageable pageable) {
        return depositHistoryDao.getDepositHistoryByUserId(userId,pageable);
    }

    @Override
    public Page<DepositHistory> getDepositHistory(Pageable pageable) {
        return depositHistoryDao.findAll(pageable);
    }
}
