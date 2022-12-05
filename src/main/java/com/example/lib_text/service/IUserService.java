package com.example.lib_text.service;

import com.example.lib_text.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    User newReader(String name,String password,double deposit);
    User newUser(String name,String password);
    User getUserById(int id);
    User getUserByName(String name);
    Page<User> getUserList(int isDelete, Pageable pageable);
    Page<User> getUserListByIdentity(int isDelete,String identity,Pageable pageable);
    User changeUserIsDelete(String name);
    User deleteUserById(int id);
    User changePassword(String name,String password);
    User borrowBookPrice(int id,double price);
    User returnBookPrice(int id,double price);
    User addDeposit(int id,double price);
    User cutDeposit(int id,double price);
    User buyBook(int id,double price);
    User reserveBook(int id,double price);
}
