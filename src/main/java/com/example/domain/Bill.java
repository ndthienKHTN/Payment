package com.example.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Bill {
    private final int id;
    private final BillType type;
    private final Money amount;
    private final LocalDate dueDate;
    private final String provider;
    private BillState state;

    public Bill(int id, BillType type, Money amount, LocalDate dueDate, String provider) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.provider = provider;
        this.state = BillState.NOT_PAID;
    }

    public void markAsPaid() {
        this.state = BillState.PAID;
    }

    public boolean isNotPaid() {
        return this.state == BillState.NOT_PAID;
    }

    public int getId() {
        return id;
    }

    public BillType getType() {
        return type;
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getProvider() {
        return provider;
    }

    public BillState getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return id == bill.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
