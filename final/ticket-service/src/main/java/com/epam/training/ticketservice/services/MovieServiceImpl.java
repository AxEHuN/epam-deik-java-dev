package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public void createMovie(Movie movie) {
        if (movieRepository.findById(movie.getMovieName()).isEmpty()) {
            movieRepository.save(movie);
        } else {
            throw new IllegalArgumentException("Film already exists");
        }
    }

    @Override
    public void updateMovie(String name, String type, Integer length) {
        if (movieRepository.findById(name).isPresent()) {
            Movie movie = movieRepository.findById(name).get();
            movie.setMovieType(type);
            movie.setMovieLength(length);
            movieRepository.save(movie);
        } else {
            throw new IllegalArgumentException("Movie not found");
        }
    }
    @Override
    public void deleteMovie(String name) {
        if (movieRepository.findById(name).isPresent()) {
            movieRepository.deleteById(name);
        } else {
            throw new IllegalArgumentException("Movie not found");
        }
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public String listMovies() {
        if (getMovies().isEmpty()) {
            return ("There are no movies at the moment");
        } else {
            StringBuilder curr = new StringBuilder();
            for (var film : getMovies()) {
                curr.append(film.toString()).append("\n");
            }
            return curr.toString();
        }
    }

}
