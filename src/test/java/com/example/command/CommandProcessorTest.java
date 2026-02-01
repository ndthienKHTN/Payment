package com.example.command;

import com.example.domain.*;
import com.example.repository.*;
import com.example.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class CommandProcessorTest {
    private CommandProcessor commandProcessor;
    private AccountService accountService;
    private BillService billService;

    @BeforeEach
    void setUp() {
        Account account = new Account();
        accountService = new AccountService(account);
        BillRepository billRepository = new BillRepository();
        billService = new BillService(billRepository);
        PaymentRepository paymentRepository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(accountService, billService, paymentRepository);
        ScheduledPaymentRepository scheduledPaymentRepository = new ScheduledPaymentRepository();
        SchedulerService schedulerService = new SchedulerService(scheduledPaymentRepository, paymentService);

        commandProcessor = new CommandProcessor(accountService, billService, paymentService, schedulerService);

        Bill bill1 = new Bill(1, BillType.ELECTRIC, new Money(200000),
                LocalDate.of(2020, 10, 25), "EVN HCMC");
        billService.createBill(bill1);
    }

    @Test
    void shouldProcessCashInCommand() {
        String result = commandProcessor.process("CASH_IN", new String[]{"1000000"});
        assertTrue(result.contains("1000000"));
    }

    @Test
    void shouldProcessListBillCommand() {
        String result = commandProcessor.process("LIST_BILL", new String[]{});
        assertTrue(result.contains("ELECTRIC"));
    }

    @Test
    void shouldProcessPayCommand() {
        commandProcessor.process("CASH_IN", new String[]{"500000"});
        String result = commandProcessor.process("PAY", new String[]{"1"});
        assertTrue(result.contains("Payment has been completed"));
    }

    @Test
    void shouldHandlePaymentForNonExistentBill() {
        commandProcessor.process("CASH_IN", new String[]{"500000"});
        String result = commandProcessor.process("PAY", new String[]{"999"});
        assertTrue(result.contains("Not found a bill with such id"));
    }

    @Test
    void shouldHandleInsufficientFunds() {
        String result = commandProcessor.process("PAY", new String[]{"1"});
        assertTrue(result.contains("Not enough fund"));
    }

    @Test
    void shouldProcessExitCommand() {
        String result = commandProcessor.process("EXIT", new String[]{});
        assertEquals("Good bye!", result);
        assertTrue(commandProcessor.isExitCommand("EXIT"));
    }

    @Test
    void shouldHandleUnknownCommand() {
        String result = commandProcessor.process("UNKNOWN", new String[]{});
        assertTrue(result.contains("Unknown command"));
    }
}
