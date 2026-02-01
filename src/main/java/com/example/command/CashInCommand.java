package com.example.command;

import com.example.domain.Money;
import com.example.service.AccountService;

public class CashInCommand implements Command {
    private final AccountService accountService;

    public CashInCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 1) {
            return "Error: Amount is required";
        }

        try {
            long amount = Long.parseLong(args[0]);
            Money money = new Money(amount);
            accountService.cashIn(money);
            return "Your available balance: " + accountService.getBalance().getAmount();
        } catch (NumberFormatException e) {
            return "Error: Invalid amount format";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }
}
