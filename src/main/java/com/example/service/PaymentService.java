package com.example.service;

import com.example.domain.*;
import com.example.repository.PaymentRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private final AccountService accountService;
    private final BillService billService;
    private final PaymentRepository paymentRepository;

    public PaymentService(AccountService accountService, BillService billService, PaymentRepository paymentRepository) {
        this.accountService = accountService;
        this.billService = billService;
        this.paymentRepository = paymentRepository;
    }

    public Payment processSinglePayment(int billId) {
        Bill bill = billService.findBillById(billId)
                .orElseThrow(() -> new BillNotFoundException("Not found a bill with such id"));

        if (!bill.isNotPaid()) {
            throw new IllegalStateException("Bill has already been paid");
        }

        Account account = accountService.getAccount();
        account.withdraw(bill.getAmount());
        bill.markAsPaid();

        Payment payment = new Payment(
                paymentRepository.getNextId(),
                bill.getAmount(),
                LocalDate.now(),
                PaymentState.PROCESSED,
                billId
        );

        return paymentRepository.save(payment);
    }

    public List<Payment> processMultiplePayments(List<Integer> billIds) {
        List<Bill> billsToPay = new ArrayList<>();
        Money totalAmount = new Money(0);

        for (int billId : billIds) {
            Bill bill = billService.findBillById(billId)
                    .orElseThrow(() -> new BillNotFoundException("Not found a bill with such id"));

            if (!bill.isNotPaid()) {
                throw new IllegalStateException("Bill " + billId + " has already been paid");
            }

            billsToPay.add(bill);
            totalAmount = totalAmount.add(bill.getAmount());
        }

        if (!accountService.getAccount().hasEnoughFunds(totalAmount)) {
            throw new InsufficientFundsException("Not enough fund to proceed with payment.");
        }

        List<Payment> payments = new ArrayList<>();
        for (Bill bill : billsToPay) {
            accountService.getAccount().withdraw(bill.getAmount());
            bill.markAsPaid();

            Payment payment = new Payment(
                    paymentRepository.getNextId(),
                    bill.getAmount(),
                    LocalDate.now(),
                    PaymentState.PROCESSED,
                    bill.getId()
            );
            payments.add(paymentRepository.save(payment));
        }

        return payments;
    }

    public Payment createScheduledPayment(int billId, LocalDate scheduledDate) {
        Bill bill = billService.findBillById(billId)
                .orElseThrow(() -> new BillNotFoundException("Not found a bill with such id"));

        Payment payment = new Payment(
                paymentRepository.getNextId(),
                bill.getAmount(),
                scheduledDate,
                PaymentState.PENDING,
                billId
        );

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
