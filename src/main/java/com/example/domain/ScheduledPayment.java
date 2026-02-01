package com.example.domain;

import java.time.LocalDate;

public class ScheduledPayment {
    private final int billId;
    private final LocalDate scheduledDate;

    public ScheduledPayment(int billId, LocalDate scheduledDate) {
        this.billId = billId;
        this.scheduledDate = scheduledDate;
    }

    public int getBillId() {
        return billId;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public boolean shouldExecute(LocalDate currentDate) {
        return !currentDate.isBefore(scheduledDate);
    }
}
