package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.model.AccountType;
import com.epam.training.ticketservice.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class AccountCommands {
    private final AccountService accountService;

    @ShellMethod(key = "sign up", value = "sign up <username> <password>")
    public void signUp(String username, String password) {
        accountService.createAccount(username, password);
    }

    @ShellMethod(key = "sign in", value = "sign in <username> <password>")
    public void signIn(String username, String password) {
        accountService.signIn(username, password);
    }

    @ShellMethod(key = "sign in privileged", value = "sign in privileged <username> <password>")
    public String signInPrivileges(String username, String password) {
        return accountService.signInPrivileged(username, password);
    }

    @ShellMethod(key = "sign out", value = "sign out")
    public void signOut() {
        accountService.signOut();
    }

    @ShellMethod(key = "describe account", value = "describe account")
    public void describeAccount() {
        var user = accountService.describeAccount();
        if (user.isEmpty()) {
            System.out.println("You are not signed in");
        } else {
            if (user.get().getType() == AccountType.ADMIN) {
                System.out.println("Signed in with privileged account " + "'" + user.get().getUsername() + "'");
            } else {
                System.out.println("Signed in with account " + "'" + user.get().getUsername() + "'");
            }
        }
    }
}
