package com.example.command;

import com.example.domain.Payment;
import com.example.service.PaymentService;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListPaymentCommand implements Command {
    private final PaymentService paymentService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ListPaymentCommand(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public String execute(String[] args) {
        List<Payment> payments = paymentService.getAllPayments();

        if (payments.isEmpty()) {
            return "No payments found";
        }

        StringBuilder output = new StringBuilder();
        output.append(String.format("%-10s %-15s %-20s %-15s %-15s\n",
                "No.", "Amount", "Payment Date", "State", "Bill Id"));

        for (Payment payment : payments) {
            output.append(String.format("%-10s %-15s %-20s %-15s %-15s\n",
                    payment.getId() + ".",
                    payment.getAmount().getAmount(),
                    payment.getPaymentDate().format(DATE_FORMATTER),
                    payment.getState(),
                    payment.getBillId()));
        }

        return output.toString().trim();
    }
}
