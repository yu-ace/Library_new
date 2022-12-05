package com.example.lib_text.dao;

import com.example.lib_text.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHistoryDao extends JpaRepository<History,Integer> {
    Page<History> findByUserId(int userId, Pageable pageable);
    Page<History> findByBookId(int bookId,Pageable pageable);
    Page<History> findByType(String type,Pageable pageable);
    Page<History> findByUserIdAndType(int userId,String type,Pageable pageable);
    Page<History> findByUserIdAndBookId(int userId,int bookId,Pageable pageable);
    History findByUserIdAndBookId(int userId,int bookId);
    History findByUserIdAndBookIdAndType(int userId,int bookId,String type);
}
