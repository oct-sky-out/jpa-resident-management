package com.nhnacademy.command;

public class FamilyCertCommand implements Command{
    private final String param;

    public FamilyCertCommand(String param) {
        this.param = param;
    }

    @Override
    public String getRedirectionUrl() {
        return "redirect:/certificate/family/" + this.param;
    }
}
