package com.example;

import com.example.command.CommandProcessor;
import com.example.domain.Account;
import com.example.repository.*;
import com.example.service.*;
import com.example.util.DataInitializer;
import java.util.Scanner;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        BillRepository billRepository = new BillRepository();
        PaymentRepository paymentRepository = new PaymentRepository();
        ScheduledPaymentRepository scheduledPaymentRepository = new ScheduledPaymentRepository();

        DataInitializer.initializeData(billRepository);

        Account account = new Account();
        AccountService accountService = new AccountService(account);
        BillService billService = new BillService(billRepository);
        PaymentService paymentService = new PaymentService(accountService, billService, paymentRepository);
        SchedulerService schedulerService = new SchedulerService(scheduledPaymentRepository, paymentService);

        CommandProcessor commandProcessor = new CommandProcessor(accountService, billService, paymentService, schedulerService);

        schedulerService.executeScheduledPayments(LocalDate.now());

        if (args.length > 0) {
            String commandName = args[0];
            String[] commandArgs = new String[args.length - 1];
            System.arraycopy(args, 1, commandArgs, 0, args.length - 1);

            String result = commandProcessor.process(commandName, commandArgs);
            System.out.println(result);
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Payment Service System - Enter commands (EXIT to quit)");

            while (true) {
                System.out.print("$ ");
                if (!scanner.hasNextLine()) {
                    break;
                }

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    continue;
                }

                String[] parts = input.split("\\s+");
                String commandName = parts[0];
                String[] commandArgs = new String[parts.length - 1];
                System.arraycopy(parts, 1, commandArgs, 0, parts.length - 1);

                String result = commandProcessor.process(commandName, commandArgs);
                System.out.println(result);

                if (commandProcessor.isExitCommand(commandName)) {
                    break;
                }
            }

            scanner.close();
        }
    }
}