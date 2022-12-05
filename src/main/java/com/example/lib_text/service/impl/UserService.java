package com.example.lib_text.service.impl;

import com.example.lib_text.dao.IUserDao;
import com.example.lib_text.model.User;
import com.example.lib_text.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserDao userDao;

    @Override
    public User newReader(String name, String password,double deposit) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setIdentity("读者");
        user.setIsDelete(0);
        user.setDeposit(deposit);
        userDao.save(user);
        return user;
    }

    @Override
    public User newUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setIdentity("管理员");
        user.setIsDelete(0);
        userDao.save(user);
        return user;
    }

    @Override
    public User getUserById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public Page<User> getUserList(int isDelete, Pageable pageable) {
        return userDao.findByIsDelete(0,pageable);
    }

    @Override
    public Page<User> getUserListByIdentity(int isDelete,String identity, Pageable pageable) {
        return userDao.findByIsDeleteAndIdentity(isDelete,identity,pageable);
    }

    @Override
    public User changeUserIsDelete(String name) {
        User user = userDao.findByName(name);
        user.setIsDelete(0);
        userDao.save(user);
        return user;
    }

    @Override
    public User deleteUserById(int id) {
        User user = userDao.findById(id);
        user.setIsDelete(1);
        userDao.save(user);
        return user;
    }

    @Override
    public User changePassword(String name, String password) {
        User user = userDao.findByName(name);
        user.setPassword(password);
        userDao.save(user);
        return user;
    }

    @Override
    public User borrowBookPrice(int id, double price) {
        User user = userDao.findById(id);
        user.setBorrowBookPrice(user.getBorrowBookPrice() + price);
        userDao.save(user);
        return user;
    }

    @Override
    public User returnBookPrice(int id, double price) {
        User user = userDao.findById(id);
        user.setBorrowBookPrice(user.getBorrowBookPrice() - price);
        userDao.save(user);
        return user;
    }

    @Override
    public User addDeposit(int id, double price) {
        User user = userDao.findById(id);
        user.setDeposit(user.getDeposit() + price);
        userDao.save(user);
        return user;
    }

    @Override
    public User cutDeposit(int id, double price) {
        User user = userDao.findById(id);
        user.setDeposit(user.getDeposit() - price);
        userDao.save(user);
        return user;
    }

    @Override
    public User buyBook(int id,double price){
        User user = userDao.findById(id);
        user.setBorrowBookPrice(user.getBorrowBookPrice() - price);
        user.setDeposit(user.getDeposit() - price);
        userDao.save(user);
        return user;
    }

    @Override
    public User reserveBook(int id, double price) {
        User user = userDao.findById(id);
        user.setDeposit(user.getDeposit() - price);
        userDao.save(user);
        return user;
    }

}
