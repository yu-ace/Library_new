package com.example.lib_text.dao;

import com.example.lib_text.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends JpaRepository<User,Integer> {
    User findById(int id);
    User findByName(String name);
    Page<User> findByIsDeleteAndIdentity(int delete,String identity, Pageable pageable);
    Page<User> findByIsDelete(int isDelete,Pageable pageable);
}
