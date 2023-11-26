package com.epam.training.ticketservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Screen {
    @Id
    @GeneratedValue
    private Long id;
    private String filmName;
    private String roomName;


    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm")
    private LocalDateTime start;


    public Screen(String filmName, String roomName, LocalDateTime start) {
        this.filmName = filmName;
        this.roomName = roomName;
        this.start = start;
    }
}
