package com.ymm.request;

/**
 * Created by gujie on 2016/8/2.
 */
public class UpdateBalanceRequest {

    private static final long serialVersionUID = -1L;


    private String telephoneNum;

    private int accountBalance;


    public String getTelephoneNum() {
        return telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }


    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }
}
