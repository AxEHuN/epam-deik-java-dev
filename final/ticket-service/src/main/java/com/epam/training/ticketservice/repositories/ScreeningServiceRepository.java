package com.epam.training.ticketservice.repositories;

import com.epam.training.ticketservice.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningServiceRepository extends JpaRepository<Screen, Long> {

    Optional<Screen> findByFilmNameAndRoomNameAndStart(String filmName, String roomName, LocalDateTime start);

    List<Screen> findByRoomName(String roomName);

    // void deleteByFilmNameAndRoomNameAndStart(String filmName, String roomName, LocalDateTime start);
}
