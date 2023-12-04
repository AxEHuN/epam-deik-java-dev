package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.repositories.AccountRepository;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.font.OpenType;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private Account loggedAccount = null;
    public String loginMessage = "";

    @Override
    public String signInPrivileged(String username, String password){
        var user = accountRepository.findByUsername(username);
        if (user.isEmpty()) {
            loginMessage = "Login failed due to incorrect credentials";
            return loginMessage;
        }else {
            if (user.get().getPassword().equals(password)) {
                loggedAccount = user.get();
                loginMessage = "Signed in with privileged account " + username;
            }else{
                loginMessage = "Login failed due to incorrect credentials";
            }
            return loginMessage;
        }
    }

    @Override
    public void signOut() {
        if (loggedAccount == null) {
            loginMessage = "You need to log in first.";
            System.out.println(loginMessage);

        }else {
            loggedAccount = null;
            loginMessage = "You logged out successfully";
            System.out.println(loginMessage);
        }
    }

    @Override
    public Optional<Account> describeAccount() {
        return Optional.ofNullable(loggedAccount);
    }

}

