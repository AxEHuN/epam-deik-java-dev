package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repositories.AccountRepository;
import com.epam.training.ticketservice.repositories.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    @Override
    public void createMovie(Movie movie) {
        if (movieRepository.findById(movie.getMovieName()).isEmpty()) {
            movieRepository.save(movie);
        }
        else{
            throw new IllegalArgumentException("Film already exists");
        }
    }
}
