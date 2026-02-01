package com.example.service;

import com.example.domain.*;
import com.example.repository.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BillServiceTest {
    private BillService billService;
    private BillRepository billRepository;

    @BeforeEach
    void setUp() {
        billRepository = new BillRepository();
        billService = new BillService(billRepository);
    }

    @Test
    void shouldCreateBill() {
        Bill bill = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        Bill created = billService.createBill(bill);
        assertEquals(bill, created);
    }

    @Test
    void shouldFindBillById() {
        Bill bill = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        billService.createBill(bill);
        assertTrue(billService.findBillById(1).isPresent());
    }

    @Test
    void shouldGetAllBills() {
        Bill bill1 = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        Bill bill2 = new Bill(2, BillType.WATER, new Money(175000),
                LocalDate.of(2020, 10, 30), "SAVACO HCMC");

        billService.createBill(bill1);
        billService.createBill(bill2);

        List<Bill> bills = billService.getAllBills();
        assertEquals(2, bills.size());
    }

    @Test
    void shouldSearchBillsByProvider() {
        Bill bill1 = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        Bill bill2 = new Bill(2, BillType.WATER, new Money(175000),
                LocalDate.of(2020, 10, 30), "SAVACO HCMC");

        billService.createBill(bill1);
        billService.createBill(bill2);

        List<Bill> evnBills = billService.searchBillsByProvider("EVN HCMC");
        assertEquals(1, evnBills.size());
        assertEquals("EVN HCMC", evnBills.get(0).getProvider());
    }

    @Test
    void shouldGetUnpaidBillsSortedByDueDate() {
        Bill bill1 = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 11, 25), "EVN HCMC");
        Bill bill2 = new Bill(2, BillType.WATER, new Money(175000),
                LocalDate.of(2020, 10, 30), "SAVACO HCMC");

        billService.createBill(bill1);
        billService.createBill(bill2);

        List<Bill> unpaidBills = billService.getUnpaidBillsSortedByDueDate();
        assertEquals(2, unpaidBills.size());
        assertTrue(unpaidBills.get(0).getDueDate().isBefore(unpaidBills.get(1).getDueDate()));
    }

    @Test
    void shouldDeleteBill() {
        Bill bill = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        billService.createBill(bill);
        billService.deleteBill(1);
        assertFalse(billService.findBillById(1).isPresent());
    }
}
