import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repositories.MovieRepository;
import com.epam.training.ticketservice.services.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    MovieServiceImpl underTest;

    private final Movie exampleMovie = new Movie("The movie", "comedy", 100);
    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        underTest = new MovieServiceImpl(movieRepository);
    }

    @Test
    public void testCreateMovie() {
        //Given
        when(movieRepository.findById("The movie")).thenReturn(Optional.empty());
        //When
        underTest.createMovie(exampleMovie);
        //Then
        verify(movieRepository).save(exampleMovie);
    }
    @Test
    public void testCreateMovieWhenAlreadyExists() {
        // Given
        when(movieRepository.findById("The movie")).thenReturn(Optional.of(exampleMovie));

        // When and Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> underTest.createMovie(exampleMovie));

        assertEquals("Film already exists", exception.getMessage());
        verify(movieRepository, never()).save(exampleMovie);
    }

    @Test
    public void testUpdateMovie() {
        //Given
        when(movieRepository.findById("The movie")).thenReturn(Optional.of(exampleMovie));
        //When
        underTest.updateMovie("The movie", "fantasy", 100);
        //Then
        assertEquals(movieRepository.findById("The movie").get(), exampleMovie);
    }
    @Test
    public void testUpdateMovieWhenNotExists() {
        //Given
        when(movieRepository.findById("The movie")).thenReturn(Optional.empty());
        // When and Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> underTest.updateMovie("The movie", "fantasy", 100));

        assertEquals("Movie not found", exception.getMessage());
    }
    @Test
    public void testDeleteMovie() {
        //Given
        when(movieRepository.findById("The movie")).thenReturn(Optional.of(exampleMovie));
        //When
        underTest.deleteMovie("The movie");
        //Then
        verify(movieRepository).deleteById("The movie");
    }
    @Test
    public void testDeleteMovieWhenNotExists() {
        //Given
        when(movieRepository.findById("The movie")).thenReturn(Optional.empty());
        // When and Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> underTest.deleteMovie("The movie"));

        assertEquals("Movie not found", exception.getMessage());
    }
    @Test
    public void testGetMovies() {
        //Given
        when(movieRepository.findAll()).thenReturn(List.of(exampleMovie));
        //When
        List<Movie> result = underTest.getMovies();
        //Then
        assertEquals(List.of(exampleMovie), result);
    }
    @Test
    public void testListMoviesWhenEmpty() {
        //Given
        when(movieRepository.findAll()).thenReturn(List.of());
        //When
        String result = underTest.listMovies();
        //Then
        assertEquals("There are no movies at the moment", result);
    }
   /* @Test
    public void testListMoviesWhenNotEmpty() {
        //Given
        when(movieRepository.findAll()).thenReturn(List.of(exampleMovie));
        //When
        String result = underTest.listMovies();
        //Then
        assertEquals("The movie (comedy, 100 minutes)", result);
    }*/
}
