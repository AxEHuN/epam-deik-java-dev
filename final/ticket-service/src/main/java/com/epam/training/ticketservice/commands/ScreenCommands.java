package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.model.AccountType;
import com.epam.training.ticketservice.model.Screen;
import com.epam.training.ticketservice.services.AccountService;
import com.epam.training.ticketservice.services.ScreeningService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ShellComponent
@AllArgsConstructor
public class ScreenCommands {
    AccountService accountService;
    ScreeningService screeningService;

    @ShellMethod(value = "Create screening", key = "create screening")
    @ShellMethodAvailability("isAdminLoggedIn")
    public String createScreening(String filmName, String roomName, String start) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return screeningService.createScreening(
                new Screen(filmName, roomName, LocalDateTime.parse(start, formatter)));
    }

    @ShellMethod(value = "Delete screening", key = "delete screening")
    @ShellMethodAvailability(value = "isAdminLoggedIn")
    public void deleteScreening(String filmName, String roomName, String start) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        screeningService.deleteScreening(new Screen(filmName, roomName, LocalDateTime.parse(start, formatter)));
    }

    @ShellMethod(value = "List screenings", key = "list screenings")
    public String listScreenings() {
        return screeningService.listScreenings();
    }

    private Availability isAdminLoggedIn() {
        var account = accountService.describeAccount();
        if (account.isPresent()){
            if (account.get().getType() == AccountType.ADMIN){
                return Availability.available();
            }
            else{
                return Availability.unavailable("You are not logged in as admin");
            }
        }else{
            return Availability.unavailable("You are not logged in as admin");
        }
    }

}



