package com.example.demo;

import java.util.*;

public class Storage {
    private Map<String, Integer> payerPoints = new HashMap<>();
    private List<Transaction> pointTransactions = new ArrayList<>();

    public Map<String, Integer> getPayerPoints() {
        return payerPoints;
    }

    public Transaction add(Transaction transaction) {
        if(payerPoints == null) payerPoints = new HashMap<>();
        String payer = transaction.getPayer();
        Integer points = transaction.getPoints();
        Integer pp = payerPoints.get(payer);
        if (pp == null) {
            payerPoints.put(payer, points);
            pointTransactions.add(transaction);
            return transaction;
        } else if (pp > Math.abs(points)) {
            pp += points;
            payerPoints.put(payer, pp);
            pointTransactions.add(transaction);
            return transaction;
        }
        return null;
    }

    public List<Transaction> addAll(List<Transaction> transactions) {
        List<Transaction> results = new ArrayList<>();
        for (Transaction transaction : transactions) {
            Transaction t = add(transaction);
            results.add(t);
        }
        return results;
    }

    public Map<String, Integer> spend(Integer points) {
        pointTransactions.sort(Comparator.comparing(Transaction::getTimestamp).reversed());
        Integer totalPoints = 0;
        for(String payer : payerPoints.keySet()) {
            totalPoints += payerPoints.get(payer);
        }
        if (points < 0) {
            throw new ArithmeticException("Points have to be greater than 0.");
        } else if (points > totalPoints) {
            throw new ArithmeticException("Unable to spend ".concat(points.toString()).concat(" points"));
        }
        totalPoints -= points;
        Map<String, Integer> spent = new HashMap<>();
        int counter = pointTransactions.size() - 1;
        while (points > 0) {
            Transaction t =  pointTransactions.get(counter);
            String transPayer = t.getPayer();
            int transPoints = t.getPoints();
            if (transPoints == points) {
                spent.merge(transPayer, points, Integer::sum);
                payerPoints.put(transPayer, payerPoints.get(transPayer) - points);
                points = 0;
            } else if (transPoints > points) {
                spent.merge(transPayer, points, Integer::sum);
                payerPoints.put(transPayer, payerPoints.get(transPayer) - points);
                points = 0;
            } else {
                spent.merge(transPayer, transPoints, Integer::sum);
                points -= transPoints;
                payerPoints.put(transPayer, payerPoints.get(transPayer) - transPoints);
            }
            counter--;
        }
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : spent.entrySet()) {
            sortedMap.put(entry.getKey(), entry.getValue() * -1);
        }
        return sortedMap;
    }
}

// ./mvnw spring-boot:run

