package com.example.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateMoneyWithPositiveAmount() {
        Money money = new Money(1000);
        assertEquals(1000, money.getAmount());
    }

    @Test
    void shouldThrowExceptionForNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Money(-100));
    }

    @Test
    void shouldAddTwoMoneyObjects() {
        Money money1 = new Money(1000);
        Money money2 = new Money(500);
        Money result = money1.add(money2);
        assertEquals(1500, result.getAmount());
    }

    @Test
    void shouldSubtractTwoMoneyObjects() {
        Money money1 = new Money(1000);
        Money money2 = new Money(500);
        Money result = money1.subtract(money2);
        assertEquals(500, result.getAmount());
    }

    @Test
    void shouldCompareMoneyCorrectly() {
        Money money1 = new Money(1000);
        Money money2 = new Money(500);
        assertTrue(money1.isGreaterThanOrEqual(money2));
        assertTrue(money2.isLessThan(money1));
    }

    @Test
    void shouldBeEqualWhenAmountsAreSame() {
        Money money1 = new Money(1000);
        Money money2 = new Money(1000);
        assertEquals(money1, money2);
    }
}
