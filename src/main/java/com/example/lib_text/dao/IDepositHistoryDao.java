package com.example.lib_text.dao;

import com.example.lib_text.model.DepositHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepositHistoryDao extends JpaRepository<DepositHistory,Integer> {
    Page<DepositHistory> getDepositHistoryByUserId(int userId, Pageable pageable);
}
