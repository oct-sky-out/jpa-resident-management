package com.nhnacademy.command;

public class RegistrationCertCommand implements Command{
    private final String param;

    public RegistrationCertCommand(String param) {
        this.param = param;
    }

    @Override
    public String getRedirectionUrl() {
        return "redirect:/certificate/household/" + this.param;
    }
}
