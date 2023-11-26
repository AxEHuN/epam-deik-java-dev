package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.model.Screen;

public interface ScreeningService {
    public String createScreening(Screen screen);
    public void deleteScreening(Screen screen);
}
