package com.nhnacademy.command;

public class BirthIssueCommand implements Command{
    private final String param;

    public BirthIssueCommand(String param) {
        this.param = param;
    }

    @Override
    public String getRedirectionUrl() {
        return "redirect:/issue/birth?serialNumber=" + param;
    }
}
