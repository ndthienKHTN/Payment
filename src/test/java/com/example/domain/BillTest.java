package com.example.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class BillTest {

    @Test
    void shouldCreateBillWithNotPaidState() {
        Bill bill = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        assertEquals(BillState.NOT_PAID, bill.getState());
        assertTrue(bill.isNotPaid());
    }

    @Test
    void shouldMarkBillAsPaid() {
        Bill bill = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        bill.markAsPaid();
        assertEquals(BillState.PAID, bill.getState());
        assertFalse(bill.isNotPaid());
    }

    @Test
    void shouldHaveCorrectProperties() {
        LocalDate dueDate = LocalDate.of(2020, 10, 25);
        Bill bill = new Bill(1, BillType.ELECTRIC, new Money(200000), dueDate, "EVN HCMC");

        assertEquals(1, bill.getId());
        assertEquals(BillType.ELECTRIC, bill.getType());
        assertEquals(new Money(200000), bill.getAmount());
        assertEquals(dueDate, bill.getDueDate());
        assertEquals("EVN HCMC", bill.getProvider());
    }

    @Test
    void shouldBeEqualWhenIdsAreSame() {
        Bill bill1 = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        Bill bill2 = new Bill(1, BillType.WATER, new Money(100000),
                LocalDate.of(2020, 11, 25), "Other Provider");
        assertEquals(bill1, bill2);
    }
}
