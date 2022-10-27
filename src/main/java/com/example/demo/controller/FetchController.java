package com.example.demo.controller;
import com.example.demo.Storage;
import com.example.demo.Transaction;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/")
public class FetchController {

    private final Storage storage = new Storage();

    //route for returning all payer point balances
    @GetMapping(value = "points", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> points() {
        return ResponseEntity.ok(new TreeMap<>(storage.getPayerPoints()));
    }

    //route for adding a single transaction
    @PostMapping(value = "add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(@RequestBody Transaction transaction){
        if (transaction != null) {
            storage.add(transaction);
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.badRequest().body("Transaction failed, point totals cannot be negative.");
        }
    }

    //route for adding multiple transactions at once
    @PostMapping(value = "addAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addAll(@RequestBody List<Transaction> transactions) {
        List<Transaction> results = storage.addAll(transactions);
        results.sort(Comparator.comparing(Transaction::getTimestamp).reversed());
        return ResponseEntity.ok(results);
    }

    //route for spending points
    @PostMapping(value = "spend", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> spend(@RequestBody Map<String, Integer> body) {
        Map<String, Integer> totalMap = storage.spend(body.get("points"));
        return ResponseEntity.ok(totalMap);
    }
}
