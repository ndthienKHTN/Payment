package com.example.command;

import com.example.domain.Bill;
import com.example.service.BillService;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListBillCommand implements Command {
    private final BillService billService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ListBillCommand(BillService billService) {
        this.billService = billService;
    }

    @Override
    public String execute(String[] args) {
        List<Bill> bills = billService.getAllBills();

        if (bills.isEmpty()) {
            return "No bills found";
        }

        StringBuilder output = new StringBuilder();
        output.append(String.format("%-10s %-15s %-15s %-15s %-15s %-20s\n",
                "Bill No.", "Type", "Amount", "Due Date", "State", "PROVIDER"));

        for (Bill bill : bills) {
            output.append(String.format("%-10s %-15s %-15s %-15s %-15s %-20s\n",
                    bill.getId() + ".",
                    bill.getType(),
                    bill.getAmount().getAmount(),
                    bill.getDueDate().format(DATE_FORMATTER),
                    bill.getState(),
                    bill.getProvider()));
        }

        return output.toString().trim();
    }
}
