package com.example.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Payment {
    private final int id;
    private final Money amount;
    private final LocalDate paymentDate;
    private final PaymentState state;
    private final int billId;

    public Payment(int id, Money amount, LocalDate paymentDate, PaymentState state, int billId) {
        this.id = id;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.state = state;
        this.billId = billId;
    }

    public int getId() {
        return id;
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public PaymentState getState() {
        return state;
    }

    public int getBillId() {
        return billId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
