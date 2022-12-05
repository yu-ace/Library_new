package com.example.lib_text.service;

import com.example.lib_text.model.DepositHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDepositHistoryService {
    DepositHistory newDepositHistory(int userId,String type,double price);
    Page<DepositHistory> getDepositHistoryByUserId(int userId, Pageable pageable);
    Page<DepositHistory> getDepositHistory(Pageable pageable);
}
