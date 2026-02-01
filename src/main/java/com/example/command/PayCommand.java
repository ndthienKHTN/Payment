package com.example.command;

import com.example.domain.InsufficientFundsException;
import com.example.domain.Payment;
import com.example.service.AccountService;
import com.example.service.BillNotFoundException;
import com.example.service.PaymentService;
import java.util.ArrayList;
import java.util.List;

public class PayCommand implements Command {
    private final PaymentService paymentService;
    private final AccountService accountService;

    public PayCommand(PaymentService paymentService, AccountService accountService) {
        this.paymentService = paymentService;
        this.accountService = accountService;
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 1) {
            return "Error: Bill ID is required";
        }

        try {
            if (args.length == 1) {
                int billId = Integer.parseInt(args[0]);
                Payment payment = paymentService.processSinglePayment(billId);
                return "Payment has been completed for Bill with id " + billId + ".\n" +
                        "Your current balance is: " + accountService.getBalance().getAmount();
            } else {
                List<Integer> billIds = new ArrayList<>();
                for (String arg : args) {
                    billIds.add(Integer.parseInt(arg));
                }
                paymentService.processMultiplePayments(billIds);
                return "Payment has been completed for multiple bills.\n" +
                        "Your current balance is: " + accountService.getBalance().getAmount();
            }
        } catch (NumberFormatException e) {
            return "Error: Invalid bill ID format";
        } catch (BillNotFoundException e) {
            return "Sorry! " + e.getMessage();
        } catch (InsufficientFundsException e) {
            return "Sorry! " + e.getMessage();
        } catch (IllegalStateException e) {
            return "Error: " + e.getMessage();
        }
    }
}
