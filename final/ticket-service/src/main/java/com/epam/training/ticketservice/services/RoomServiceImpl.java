package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repositories.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    @Override
    public void createRoom(Room room) {
        if (roomRepository.findById(room.getName()).isEmpty()) {
            roomRepository.save(room);
        }
    }

    @Override
    public void updateRoom(String roomName, Integer numberOfRows, Integer numberOfColumns) {

    }

    @Override
    public void deleteRoom(String roomName) {

    }

    @Override
    public String listRooms() {
        return null;
    }

    @Override
    public List<Room> getRooms() {
        return null;
    }
}
