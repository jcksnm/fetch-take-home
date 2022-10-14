package com.example.demo;

import com.example.demo.controller.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    // points for spending
    private Integer totalPoints = 0;
    // points per payer
    private Map<String, Integer> payerPoints = new HashMap<>();
    //
    private List<Transaction> transactions = new ArrayList<>();

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Map<String, Integer> getPayerPoints() {
        return payerPoints;
    }

    public void setPayerPoints(Map<String, Integer> payerPoints) {
        this.payerPoints = payerPoints;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transactions) {
        this.transactions.add(transactions);
    }
}
