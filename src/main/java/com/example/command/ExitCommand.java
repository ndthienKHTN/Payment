package com.example.command;

public class ExitCommand implements Command {
    @Override
    public String execute(String[] args) {
        return "Good bye!";
    }
}
