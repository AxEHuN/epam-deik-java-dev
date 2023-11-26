package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.model.AccountType;
import com.epam.training.ticketservice.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private Account loggedAccount = null;

    @Override
    public String signInPrivileged(String username, String password){
        var user = accountRepository.findByUsername(username);
        if (user.isEmpty()) {
            return "Login failed due to incorrect credentials";
        }else {
            if (user.get().getPassword().equals(password)) {
                loggedAccount = user.get();
                return "Signed in with privileged account " + username;
            }else{
                return "Login failed due to incorrect credentials";
            }
        }
    }

    @Override
    public void signOut() {
        if (loggedAccount == null) {
            System.out.println("You need to log in first.");
        }else {
            loggedAccount = null;
            System.out.println("You logged out successfully");
        }
    }

    @Override
    public String describeAccount() {
        if (loggedAccount == null) {
            return "You are not signed in";
        }else{
            if (loggedAccount.getType() == AccountType.ADMIN) return "Signed in with privileged account " + loggedAccount.getUsername();
            else return "Signed in with account " + loggedAccount.getUsername();
        }
    }

}
