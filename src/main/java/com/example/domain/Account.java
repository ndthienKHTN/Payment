package com.example.domain;

public class Account {
    private Money balance;

    public Account() {
        this.balance = new Money(0);
    }

    public void deposit(Money amount) {
        this.balance = this.balance.add(amount);
    }

    public void withdraw(Money amount) {
        if (balance.isLessThan(amount)) {
            throw new InsufficientFundsException("Not enough fund to proceed with payment.");
        }
        this.balance = this.balance.subtract(amount);
    }

    public boolean hasEnoughFunds(Money amount) {
        return this.balance.isGreaterThanOrEqual(amount);
    }

    public Money getBalance() {
        return balance;
    }
}
