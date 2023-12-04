package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.model.Screen;

public interface ScreeningService {
    String createScreening(Screen screen);

    void deleteScreening(Screen screen);

    String listScreenings();
}
