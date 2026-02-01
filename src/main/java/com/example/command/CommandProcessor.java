package com.example.command;

import com.example.service.*;
import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandProcessor(AccountService accountService,
                          BillService billService,
                          PaymentService paymentService,
                          SchedulerService schedulerService) {
        commands.put("CASH_IN", new CashInCommand(accountService));
        commands.put("LIST_BILL", new ListBillCommand(billService));
        commands.put("PAY", new PayCommand(paymentService, accountService));
        commands.put("DUE_DATE", new DueDateCommand(billService));
        commands.put("SCHEDULE", new ScheduleCommand(schedulerService));
        commands.put("LIST_PAYMENT", new ListPaymentCommand(paymentService));
        commands.put("SEARCH_BILL_BY_PROVIDER", new SearchBillByProviderCommand(billService));
        commands.put("EXIT", new ExitCommand());
    }

    public String process(String commandName, String[] args) {
        Command command = commands.get(commandName);
        if (command == null) {
            return "Unknown command: " + commandName;
        }
        return command.execute(args);
    }

    public boolean isExitCommand(String commandName) {
        return "EXIT".equals(commandName);
    }
}
