package com.nhnacademy.command;

public class DeathIssueCommand implements Command{
    private final String param;

    public DeathIssueCommand(String param) {
        this.param = param;
    }

    @Override
    public String getRedirectionUrl() {
        return "redirect:/issue/death?serialNumber=" + param;
    }
}
