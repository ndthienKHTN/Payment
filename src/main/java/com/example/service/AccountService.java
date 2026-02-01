package com.example.service;

import com.example.domain.Account;
import com.example.domain.Money;

public class AccountService {
    private final Account account;

    public AccountService(Account account) {
        this.account = account;
    }

    public void cashIn(Money amount) {
        account.deposit(amount);
    }

    public Money getBalance() {
        return account.getBalance();
    }

    public Account getAccount() {
        return account;
    }
}
