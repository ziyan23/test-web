package com.ymm.response;

public class UpdateBalanceResponse extends ResultResponse {

    private int accountBalance;


    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }
}
