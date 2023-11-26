package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screen;
import com.epam.training.ticketservice.repositories.MovieRepository;
import com.epam.training.ticketservice.repositories.RoomRepository;
import com.epam.training.ticketservice.repositories.ScreeningServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScreeningServiceImpl implements ScreeningService{

    private final ScreeningServiceRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    @Override
    public String createScreening(Screen screen) {
        if (movieRepository.existsById(screen.getFilmName()) && roomRepository.existsById(screen.getRoomName())) {

            List<Screen> screeningList = screeningRepository.findByRoomName(screen.getRoomName());
            if (isOverLapping(screeningList, screen)) {
                return ("There is an overlapping screening");
            } else if (isInBreak(screeningList, screen)) {
                return ("This would start in the break period after another screening in this room");
            } else {
                screeningRepository.save(screen);
                return "User succesfully added";
            }

        } else {
            throw new IllegalArgumentException();
        }

    }

    private boolean isOverLapping(List<Screen> screeningList, Screen screen) {
        boolean isOverLapping = false;
        int screeningLength = movieRepository.findById(screen.getFilmName()).get().getMovieLength();
        LocalDateTime screenStart = screen.getStart();
        LocalDateTime screeningEnd = screen.getStart().plusMinutes(screeningLength);
        for (var actual : screeningList) {
            int actualLength = movieRepository.findById(actual.getFilmName()).get().getMovieLength();
            LocalDateTime actualStart = actual.getStart();
            LocalDateTime actualEnd = actual.getStart().plusMinutes(actualLength);

            if (screeningEnd.isAfter(actualStart) && screeningEnd.isBefore(actualEnd)
                    ||
                    screenStart.isAfter(actualStart) && screeningEnd.isBefore(actualEnd)
                    ||
                    screenStart.isAfter(actualStart) && screenStart.isBefore(actualEnd)
                    ||
                    screenStart.isBefore(actualStart) && screeningEnd.isAfter(actualEnd)
                    ||
                    screenStart.equals(actualStart) && screeningEnd.equals(actualEnd)
            ) {
                isOverLapping = true;
            }
        }
        return isOverLapping;
    }
    public boolean isInBreak(List<Screen> screeningList, Screen screen) {
        boolean isInBreak = false;
        LocalDateTime screeningStart = screen.getStart();

        for (var actual : screeningList) {
            int actualLength = movieRepository.findById(actual.getFilmName()).get().getMovieLength();
            LocalDateTime actualEnd = actual.getStart().plusMinutes(actualLength);
            if (screeningStart.isAfter(actualEnd) && screeningStart.isBefore(actualEnd.plusMinutes(10))) {
                isInBreak = true;
            }
        }
        return isInBreak;

    }

    @Override
    public void deleteScreening(Screen screen) {
        if (screeningRepository.findByAll(screen.getFilmName(),
                screen.getRoomName(),
                screen.getStart()).isPresent()) {
            Long id = screeningRepository.findByAll(screening.getFilmName(),
                    screen.getRoomName(),
                    screen.getStart()).get().getId();

            screeningRepository.deleteById(id);
        } else {
            System.out.print("Screening not found");
        }
    }
}
