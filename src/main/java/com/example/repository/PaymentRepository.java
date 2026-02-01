package com.example.repository;

import com.example.domain.Payment;
import java.util.*;

public class PaymentRepository {
    private final Map<Integer, Payment> payments = new HashMap<>();
    private int nextId = 1;

    public Payment save(Payment payment) {
        payments.put(payment.getId(), payment);
        if (payment.getId() >= nextId) {
            nextId = payment.getId() + 1;
        }
        return payment;
    }

    public Optional<Payment> findById(int id) {
        return Optional.ofNullable(payments.get(id));
    }

    public List<Payment> findAll() {
        return new ArrayList<>(payments.values());
    }

    public int getNextId() {
        return nextId;
    }
}
