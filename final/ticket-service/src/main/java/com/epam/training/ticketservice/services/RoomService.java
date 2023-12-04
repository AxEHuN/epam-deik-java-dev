package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.model.Room;
import java.util.List;
public interface RoomService {
    void createRoom(Room room);
    void updateRoom(String roomName, Integer numberOfRows, Integer numberOfColumns);
    void deleteRoom(String roomName);
    StringBuilder listRooms();
    List<Room> getRooms();
}
