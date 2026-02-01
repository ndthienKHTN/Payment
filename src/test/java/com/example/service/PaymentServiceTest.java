package com.example.service;

import com.example.domain.*;
import com.example.repository.BillRepository;
import com.example.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {
    private PaymentService paymentService;
    private AccountService accountService;
    private BillService billService;
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        Account account = new Account();
        accountService = new AccountService(account);
        BillRepository billRepository = new BillRepository();
        billService = new BillService(billRepository);
        paymentRepository = new PaymentRepository();
        paymentService = new PaymentService(accountService, billService, paymentRepository);
    }

    @Test
    void shouldProcessSinglePaymentSuccessfully() {
        accountService.cashIn(new Money(500000));
        Bill bill = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        billService.createBill(bill);

        Payment payment = paymentService.processSinglePayment(1);

        assertNotNull(payment);
        assertEquals(new Money(300000), accountService.getBalance());
        assertTrue(bill.getState() == BillState.PAID);
    }

    @Test
    void shouldThrowExceptionWhenBillNotFound() {
        accountService.cashIn(new Money(500000));
        assertThrows(BillNotFoundException.class, () -> paymentService.processSinglePayment(999));
    }

    @Test
    void shouldThrowExceptionWhenInsufficientFunds() {
        accountService.cashIn(new Money(100000));
        Bill bill = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        billService.createBill(bill);

        assertThrows(InsufficientFundsException.class, () -> paymentService.processSinglePayment(1));
    }

    @Test
    void shouldProcessMultiplePaymentsSuccessfully() {
        accountService.cashIn(new Money(1000000));
        Bill bill1 = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        Bill bill2 = new Bill(2, BillType.WATER, new Money(175000),
                LocalDate.of(2020, 10, 30), "SAVACO HCMC");
        billService.createBill(bill1);
        billService.createBill(bill2);

        List<Payment> payments = paymentService.processMultiplePayments(Arrays.asList(1, 2));

        assertEquals(2, payments.size());
        assertEquals(new Money(625000), accountService.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenMultiplePaymentsInsufficientFunds() {
        accountService.cashIn(new Money(300000));
        Bill bill1 = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        Bill bill2 = new Bill(2, BillType.WATER, new Money(175000),
                LocalDate.of(2020, 10, 30), "SAVACO HCMC");
        billService.createBill(bill1);
        billService.createBill(bill2);

        assertThrows(InsufficientFundsException.class,
                () -> paymentService.processMultiplePayments(Arrays.asList(1, 2)));
    }

    @Test
    void shouldCreateScheduledPayment() {
        Bill bill = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        billService.createBill(bill);

        Payment payment = paymentService.createScheduledPayment(1, LocalDate.of(2020, 10, 28));

        assertNotNull(payment);
        assertEquals(PaymentState.PENDING, payment.getState());
    }

    @Test
    void shouldGetAllPayments() {
        accountService.cashIn(new Money(500000));
        Bill bill = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        billService.createBill(bill);
        paymentService.processSinglePayment(1);

        List<Payment> payments = paymentService.getAllPayments();
        assertEquals(1, payments.size());
    }
}
