package com.example.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void shouldStartWithZeroBalance() {
        Account account = new Account();
        assertEquals(new Money(0), account.getBalance());
    }

    @Test
    void shouldDepositMoney() {
        Account account = new Account();
        account.deposit(new Money(1000));
        assertEquals(new Money(1000), account.getBalance());
    }

    @Test
    void shouldWithdrawMoneyWhenSufficientFunds() {
        Account account = new Account();
        account.deposit(new Money(1000));
        account.withdraw(new Money(500));
        assertEquals(new Money(500), account.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenInsufficientFunds() {
        Account account = new Account();
        account.deposit(new Money(500));
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(new Money(1000)));
    }

    @Test
    void shouldCheckIfHasEnoughFunds() {
        Account account = new Account();
        account.deposit(new Money(1000));
        assertTrue(account.hasEnoughFunds(new Money(500)));
        assertFalse(account.hasEnoughFunds(new Money(1500)));
    }
}
