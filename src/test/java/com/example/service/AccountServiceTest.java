package com.example.service;

import com.example.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        Account account = new Account();
        accountService = new AccountService(account);
    }

    @Test
    void shouldCashInMoney() {
        accountService.cashIn(new Money(1000000));
        assertEquals(new Money(1000000), accountService.getBalance());
    }

    @Test
    void shouldGetCorrectBalance() {
        accountService.cashIn(new Money(500000));
        accountService.cashIn(new Money(300000));
        assertEquals(new Money(800000), accountService.getBalance());
    }

    @Test
    void shouldReturnAccountInstance() {
        assertNotNull(accountService.getAccount());
    }
}
