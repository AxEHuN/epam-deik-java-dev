package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private Account loggedAccount = null;

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

}
