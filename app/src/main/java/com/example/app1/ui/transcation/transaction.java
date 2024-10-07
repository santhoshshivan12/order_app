package com.example.app1.ui.transcation;

import java.util.List;

public class transaction {
    private String amount;
    private String date;
    private String user;
    private String orderNo;
    private String transactionDetails;
    private List<transaction> transactionList;

    public transaction(String amount, String date, String user, String orderNo, String transactionDetails) {
        this.amount = amount;
        this.date = date;
        this.user = user;
        this.orderNo = orderNo;
        this.transactionDetails = transactionDetails;

    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public List<transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
