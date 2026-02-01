package com.example.repository;

import com.example.domain.ScheduledPayment;
import java.util.*;

public class ScheduledPaymentRepository {
    private final Map<Integer, ScheduledPayment> scheduledPayments = new HashMap<>();

    public void save(ScheduledPayment scheduledPayment) {
        scheduledPayments.put(scheduledPayment.getBillId(), scheduledPayment);
    }

    public Optional<ScheduledPayment> findByBillId(int billId) {
        return Optional.ofNullable(scheduledPayments.get(billId));
    }

    public List<ScheduledPayment> findAll() {
        return new ArrayList<>(scheduledPayments.values());
    }

    public void delete(int billId) {
        scheduledPayments.remove(billId);
    }
}
