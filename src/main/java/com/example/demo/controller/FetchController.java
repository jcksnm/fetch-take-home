package com.example.demo.controller;

import com.example.demo.Storage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class FetchController {

    private Storage storage = new Storage();

    @GetMapping(value = "points", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> points() {
        return ResponseEntity.ok(storage.getPayerPoints());
    }

    @PostMapping(value = "add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(@RequestBody Transaction transaction){
        String payer = transaction.getPayer();
        Integer points = transaction.getPoints();
        Map<String, Integer> payerPointsMap = storage.getPayerPoints();
        Integer payerPoints = payerPointsMap.get(payer);
        if (payerPoints == null) {
            payerPointsMap.put(payer, points);
            return ResponseEntity.ok(transaction);
        } else {
            if (payerPoints > Math.abs(points)) {
                payerPoints += points;
                payerPointsMap.put(payer, payerPoints);
                storage.addTransaction(transaction);
                return ResponseEntity.ok(transaction);
            }
        }
        return ResponseEntity.badRequest().body("Transaction failed, point totals cannot be negative.");
    }
}
