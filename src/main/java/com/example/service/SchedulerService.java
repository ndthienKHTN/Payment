package com.example.service;

import com.example.domain.ScheduledPayment;
import com.example.repository.ScheduledPaymentRepository;
import java.time.LocalDate;

public class SchedulerService {
    private final ScheduledPaymentRepository scheduledPaymentRepository;
    private final PaymentService paymentService;

    public SchedulerService(ScheduledPaymentRepository scheduledPaymentRepository, PaymentService paymentService) {
        this.scheduledPaymentRepository = scheduledPaymentRepository;
        this.paymentService = paymentService;
    }

    public void schedulePayment(int billId, LocalDate scheduledDate) {
        ScheduledPayment scheduledPayment = new ScheduledPayment(billId, scheduledDate);
        scheduledPaymentRepository.save(scheduledPayment);
        paymentService.createScheduledPayment(billId, scheduledDate);
    }

    public void executeScheduledPayments(LocalDate currentDate) {
        scheduledPaymentRepository.findAll().stream()
                .filter(sp -> sp.shouldExecute(currentDate))
                .forEach(sp -> {
                    try {
                        paymentService.processSinglePayment(sp.getBillId());
                        scheduledPaymentRepository.delete(sp.getBillId());
                    } catch (Exception e) {
                    }
                });
    }
}
