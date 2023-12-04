package com.epam.training.ticketservice.services;
import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.model.AccountType;
import com.epam.training.ticketservice.repositories.AccountRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        }else {
            if (user.get().getPassword().equals(password)) {
                loggedAccount = user.get();
                loginMessage = "Signed in with privileged account " + username;
            }else{
                loginMessage = "Login failed due to incorrect credentials";
            }
        }
        return loginMessage;
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

    @Override
    public void createAccount(String username, String password) {
        if (accountRepository.findByUsername(username).isEmpty()) {
            accountRepository.save(new Account(username, password, AccountType.USER));
            System.out.println("Account created successfully");
        }else {
            throw new IllegalStateException("Account already exists");
        }
    }


    @Override
    public void signIn(String username, String password) {
        var user = accountRepository.findByUsername(username);
        if (user.isEmpty()) {
            loginMessage = "Login failed due to incorrect credentials";
        }else {
            if (user.get().getPassword().equals(password)) {
                loggedAccount = user.get();
                loginMessage = "Signed in with account " + username;
            }else{
                loginMessage = "Login failed due to incorrect credentials";
            }
        }
    }

}

