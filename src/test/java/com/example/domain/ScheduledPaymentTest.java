package com.example.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ScheduledPaymentTest {

    @Test
    void shouldExecuteWhenCurrentDateIsAfterScheduledDate() {
        ScheduledPayment scheduledPayment = new ScheduledPayment(1, LocalDate.of(2020, 10, 25));
        assertTrue(scheduledPayment.shouldExecute(LocalDate.of(2020, 10, 26)));
        assertTrue(scheduledPayment.shouldExecute(LocalDate.of(2020, 10, 25)));
    }

    @Test
    void shouldNotExecuteWhenCurrentDateIsBeforeScheduledDate() {
        ScheduledPayment scheduledPayment = new ScheduledPayment(1, LocalDate.of(2020, 10, 25));
        assertFalse(scheduledPayment.shouldExecute(LocalDate.of(2020, 10, 24)));
    }

    @Test
    void shouldHaveCorrectProperties() {
        LocalDate scheduledDate = LocalDate.of(2020, 10, 25);
        ScheduledPayment scheduledPayment = new ScheduledPayment(1, scheduledDate);

        assertEquals(1, scheduledPayment.getBillId());
        assertEquals(scheduledDate, scheduledPayment.getScheduledDate());
    }
}
