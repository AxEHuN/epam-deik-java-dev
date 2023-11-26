package com.epam.training.ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    private String name;
    private Integer numberOfRows;
    private Integer numberOfColumns;

    @Override
    public String toString() {
        return "Room " + name + " with "
                + numberOfRows * numberOfColumns + " seats, "
                + numberOfRows + " rows and " + numberOfColumns + " columns";
    }
}
