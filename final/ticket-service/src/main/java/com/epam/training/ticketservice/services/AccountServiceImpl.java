package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.font.OpenType;
import java.util.Optional;

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
    public Optional<Account> describeAccount() {
        return Optional.ofNullable(loggedAccount);
    }


}
