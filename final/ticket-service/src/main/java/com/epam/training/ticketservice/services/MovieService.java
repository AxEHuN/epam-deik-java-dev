package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.model.Movie;

public interface MovieService {
    void createMovie(Movie movie);

    void updateMovie(String name, String type, Integer length);

    void deleteMovie(String name);

    String listMovies();
}
