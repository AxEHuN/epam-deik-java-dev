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
        } else {
            throw new IllegalArgumentException("Room already exists");
        }
    }

    @Override
    public void updateRoom(String roomName, Integer numberOfRows, Integer numberOfColumns) {
        if (roomRepository.findById(roomName).isPresent()) {
            roomRepository.save(new Room(roomName, numberOfRows, numberOfColumns));
        } else {
            throw new IllegalArgumentException("Room does not exists");
        }
    }

    @Override
    public void deleteRoom(String roomName) {
        if (roomRepository.findById(roomName).isPresent()) {
            roomRepository.deleteById(roomName);
        } else {
            throw new IllegalStateException("Room does not exists");
        }
    }

    @Override
    public StringBuilder listRooms() {
        if (getRooms().isEmpty()) {
            return new StringBuilder("There are no rooms at the moment");
        } else {
            StringBuilder curr = new StringBuilder();
            for (var room : getRooms()) {
                curr.append(room.toString()).append("\n");
            }
            return curr;
        }
    }

    @Override
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }
}
