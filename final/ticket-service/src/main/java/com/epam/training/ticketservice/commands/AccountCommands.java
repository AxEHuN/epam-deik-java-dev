package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.services.AccountService;
import com.epam.training.ticketservice.services.AccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class AccountCommands {
    private final AccountService accountService;

    @ShellMethod(key = "sign in privileged", value = "sign in privileged <username> <password>")
    public String signInPrivileges(String username, String password){
        return accountService.signInPrivileged(username, password);
    }
    @ShellMethod(key = "sign out", value = "sign out")
    public void signOut() {
        accountService.signOut();
    }
    @ShellMethod(key = "describe account", value = "describe account")
    public void describeAccount(){
        System.out.println(accountService.describeAccount());
    }
}
