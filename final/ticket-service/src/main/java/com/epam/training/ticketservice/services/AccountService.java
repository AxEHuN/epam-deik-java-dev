package com.epam.training.ticketservice.services;


import com.epam.training.ticketservice.model.Account;

import java.util.Optional;

public interface AccountService {
    String signInPrivileged(String username, String password);
    void signOut();
    Optional<Account> describeAccount();
}
