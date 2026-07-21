package com.example.p1150512.dto.request;


public class DepositReq {

    private String account;

    private String password;

    private Integer amount;


    public String getAccount() {
        return account;
    }


    public void setAccount(String account) {
        this.account = account;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getAmount() {
        return amount;
    }


    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}