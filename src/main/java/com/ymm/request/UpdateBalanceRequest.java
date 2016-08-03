package com.ymm.request;

/**
 * Created by gujie on 2016/8/2.
 */
public class UpdateBalanceRequest {

    private static final long serialVersionUID = -1L;


    private String telephoneNum;

    private int accoutBalance;


    public String getTelephoneNum() {
        return telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }

    public int getAccoutBalance() {
        return accoutBalance;
    }

    public void setAccoutBalance(int accoutBalance) {
        this.accoutBalance = accoutBalance;
    }
}
