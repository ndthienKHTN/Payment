package com.example.command;

import com.example.service.BillNotFoundException;
import com.example.service.SchedulerService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ScheduleCommand implements Command {
    private final SchedulerService schedulerService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ScheduleCommand(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 2) {
            return "Error: Bill ID and scheduled date are required";
        }

        try {
            int billId = Integer.parseInt(args[0]);
            LocalDate scheduledDate = LocalDate.parse(args[1], DATE_FORMATTER);

            schedulerService.schedulePayment(billId, scheduledDate);

            return "Payment for bill id " + billId + " is scheduled on " + args[1];
        } catch (NumberFormatException e) {
            return "Error: Invalid bill ID format";
        } catch (DateTimeParseException e) {
            return "Error: Invalid date format. Use dd/MM/yyyy";
        } catch (BillNotFoundException e) {
            return "Sorry! " + e.getMessage();
        }
    }
}
