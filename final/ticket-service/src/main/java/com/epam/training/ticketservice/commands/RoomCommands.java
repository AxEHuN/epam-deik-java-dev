package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.model.AccountType;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.services.AccountService;
import com.epam.training.ticketservice.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@AllArgsConstructor
public class RoomCommands {
    private final AccountService accountService;
    private final RoomService roomService;

    @ShellMethod(value = "Create room", key = "create room")
    @ShellMethodAvailability("isAdminLoggedIn")
    public void createRoom(String name, Integer numberOfRows, Integer numberOfColumns) {
        roomService.createRoom(new Room(name,
            numberOfRows,
            numberOfColumns));
    }

    @ShellMethod(value = "Update room", key = "update room")
    @ShellMethodAvailability("isAdminLoggedIn")
    public void updateRoom(String name, Integer numberOfRows, Integer numberOfColumns) {
        roomService.updateRoom(name,
            numberOfRows,
            numberOfColumns);
    }

    @ShellMethod(value = "Delete room", key = "delete room")
    @ShellMethodAvailability("isAdminLoggedIn")
    public void deleteRoom(String name) {
        roomService.deleteRoom(name);
    }

    @ShellMethod(value = "List rooms", key = "list rooms")
    public StringBuilder listRooms() {
        return roomService.listRooms();
    }

    private Availability isAdminLoggedIn() {
        var account = accountService.describeAccount();
        if (account.isPresent()) {
            if (account.get().getType() == AccountType.ADMIN) {
                return Availability.available();
            } else {
                return Availability.unavailable("You are not logged in as admin");
            }
        } else {
            return Availability.unavailable("You are not logged in as admin");
        }
    }
}
