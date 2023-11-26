package com.epam.training.ticketservice.services;


public interface AccountService {
    String signInPrivileged(String username, String password);
    void signOut();
    String describeAccount();

}
