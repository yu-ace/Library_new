package com.example.lib_text.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "user")
@DynamicUpdate
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "name")
    String name;
    @Column(name = "password")
    String password;
    @Column(name = "identity")
    String identity;
    @Column(name = "is_delete")
    int isDelete;
    @Column(name = "borrow_book_price")
    double borrowBookPrice;
    @Column(name = "deposit")
    double deposit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public double getBorrowBookPrice() {
        return borrowBookPrice;
    }

    public void setBorrowBookPrice(double borrowBookPrice) {
        this.borrowBookPrice = borrowBookPrice;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }
}
