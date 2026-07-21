package com.example.p1150512.dto.response;

public class AccountBalanceRes {

    private String account;

    private int balance;



    public AccountBalanceRes() {

    }



    public AccountBalanceRes(
            String account,
            int balance) {

        this.account = account;
        this.balance = balance;
    }



    public String getAccount() {
        return account;
    }



    public void setAccount(String account) {
        this.account = account;
    }



    public int getBalance() {
        return balance;
    }



    public void setBalance(int balance) {
        this.balance = balance;
    }
}
