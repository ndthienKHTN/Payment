package com.example.repository;

import com.example.domain.Bill;
import java.util.*;
import java.util.stream.Collectors;

public class BillRepository {
    private final Map<Integer, Bill> bills = new HashMap<>();
    private int nextId = 1;

    public Bill save(Bill bill) {
        bills.put(bill.getId(), bill);
        if (bill.getId() >= nextId) {
            nextId = bill.getId() + 1;
        }
        return bill;
    }

    public Optional<Bill> findById(int id) {
        return Optional.ofNullable(bills.get(id));
    }

    public List<Bill> findAll() {
        return new ArrayList<>(bills.values());
    }

    public List<Bill> findByProvider(String provider) {
        return bills.values().stream()
                .filter(bill -> bill.getProvider().equalsIgnoreCase(provider))
                .collect(Collectors.toList());
    }

    public List<Bill> findUnpaidBillsSortedByDueDate() {
        return bills.values().stream()
                .filter(Bill::isNotPaid)
                .sorted(Comparator.comparing(Bill::getDueDate))
                .collect(Collectors.toList());
    }

    public void delete(int id) {
        bills.remove(id);
    }

    public int getNextId() {
        return nextId;
    }
}
