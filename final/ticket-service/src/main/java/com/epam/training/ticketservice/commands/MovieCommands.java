package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.model.AccountType;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.services.AccountService;
import com.epam.training.ticketservice.services.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@AllArgsConstructor
public class MovieCommands {
    private final MovieService movieService;
    private final AccountService accountService;

    @ShellMethod(key = "create movie", value = "create movie")
    @ShellMethodAvailability("isAdminLoggedIn")
    public void createMovie(String name, String type, int length){
        movieService.createMovie(new Movie(name, type, length));
    }

    @ShellMethod(value = "Update movie", key = "update movie")
    @ShellMethodAvailability(value = "isAdminLoggedIn")
    public void updateMovie(String name, String type, Integer length) {
        movieService.updateMovie(name, type, length);
    }

    @ShellMethod(value = "Delete movie", key = "delete movie")
    @ShellMethodAvailability(value = "isAdminLoggedIn")
    public void deleteMovie(String name) {
        movieService.deleteMovie(name);
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
