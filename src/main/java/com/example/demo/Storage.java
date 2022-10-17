package com.example.demo;

import com.example.demo.controller.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    // points for spending
    private Integer incomingPoints = 0;
    // points per payer
    private Map<String, Integer> payerPoints = new HashMap<>();
    //
    private List<Transaction> transactionsArr = new ArrayList<>();

    public int getIncomingPoints() {
        return incomingPoints;
    }

    public void setIncomingPoints(int incomingPoints) {
        this.incomingPoints = incomingPoints;
    }

    public Map<String, Integer> getPayerPoints() {
        return payerPoints;
    }

    public void setPayerPoints(Map<String, Integer> payerPoints) {
        this.payerPoints = payerPoints;
    }

    public List<Transaction> getTransactionsArr() {
        return transactionsArr;
    }

    public void addTransaction(Transaction transactions) {
        this.transactionsArr.add(transactions);
    }
}
