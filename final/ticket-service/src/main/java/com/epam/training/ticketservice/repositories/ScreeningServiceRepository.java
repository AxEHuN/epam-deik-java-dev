package com.epam.training.ticketservice.repositories;

import com.epam.training.ticketservice.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreeningServiceRepository extends JpaRepository<Screen, Long> {

    List<Screen> findByRoomName(String roomName);
}
