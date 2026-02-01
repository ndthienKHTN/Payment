package com.example.repository;

import com.example.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BillRepositoryTest {
    private BillRepository repository;

    @BeforeEach
    void setUp() {
        repository = new BillRepository();
    }

    @Test
    void shouldSaveBill() {
        Bill bill = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        Bill saved = repository.save(bill);
        assertEquals(bill, saved);
    }

    @Test
    void shouldFindBillById() {
        Bill bill = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        repository.save(bill);
        assertTrue(repository.findById(1).isPresent());
        assertEquals(bill, repository.findById(1).get());
    }

    @Test
    void shouldReturnEmptyWhenBillNotFound() {
        assertFalse(repository.findById(999).isPresent());
    }

    @Test
    void shouldFindAllBills() {
        Bill bill1 = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        Bill bill2 = new Bill(2, BillType.WATER, new Money(175000),
                LocalDate.of(2020, 10, 30), "SAVACO HCMC");

        repository.save(bill1);
        repository.save(bill2);

        List<Bill> bills = repository.findAll();
        assertEquals(2, bills.size());
    }

    @Test
    void shouldFindBillsByProvider() {
        Bill bill1 = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        Bill bill2 = new Bill(2, BillType.WATER, new Money(175000),
                LocalDate.of(2020, 10, 30), "SAVACO HCMC");
        Bill bill3 = new Bill(3, BillType.INTERNET, new Money(800000),
                LocalDate.of(2020, 11, 30), "EVN HCMC");

        repository.save(bill1);
        repository.save(bill2);
        repository.save(bill3);

        List<Bill> evnBills = repository.findByProvider("EVN HCMC");
        assertEquals(2, evnBills.size());
    }

    @Test
    void shouldFindUnpaidBillsSortedByDueDate() {
        Bill bill1 = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 11, 25), "EVN HCMC");
        Bill bill2 = new Bill(2, BillType.WATER, new Money(175000),
                LocalDate.of(2020, 10, 30), "SAVACO HCMC");
        Bill bill3 = new Bill(3, BillType.INTERNET, new Money(800000),
                LocalDate.of(2020, 10, 15), "VNPT");

        repository.save(bill1);
        repository.save(bill2);
        repository.save(bill3);

        bill3.markAsPaid();

        List<Bill> unpaidBills = repository.findUnpaidBillsSortedByDueDate();
        assertEquals(2, unpaidBills.size());
        assertEquals(2, unpaidBills.get(0).getId());
        assertEquals(1, unpaidBills.get(1).getId());
    }

    @Test
    void shouldDeleteBill() {
        Bill bill = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        repository.save(bill);
        repository.delete(1);
        assertFalse(repository.findById(1).isPresent());
    }
}
