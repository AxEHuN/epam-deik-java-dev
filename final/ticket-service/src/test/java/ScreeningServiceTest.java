import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screen;
import com.epam.training.ticketservice.repositories.MovieRepository;
import com.epam.training.ticketservice.repositories.RoomRepository;
import com.epam.training.ticketservice.repositories.ScreeningServiceRepository;
import com.epam.training.ticketservice.services.ScreeningServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ScreeningServiceTest {
    private ScreeningServiceImpl underTest;
    @Mock
    private ScreeningServiceRepository screeningRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private RoomRepository roomRepository;
    private final Movie exampleMovie = new Movie("The movie", "comedy", 100);
    private final Room exampleRoom = new Room("The room", 10, 10);
    private final LocalDateTime exampleDate = LocalDateTime.of(2021, 4, 1, 10, 0);
    private final Screen exampleScreen = new Screen(exampleMovie.getMovieName(), exampleRoom.getName(), exampleDate);

    @BeforeEach
    public void setUp() {
        underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
    }

    @Test
    void testListScreeningsShouldReturnEmptyListWhenNoScreeningsAdded() {
        // Given
        // When
        List<Screen> actualResult = underTest.getScreenings();
        // Then
        Assertions.assertEquals(Collections.emptyList(), actualResult);
    }
    @Test
    void testListScreeningsShouldReturnTheListOfScreeningsWhenNotEmpty() {
        // Given
        List<Screen> screenings = Collections.singletonList(exampleScreen);
        given(underTest.getScreenings()).willReturn(screenings);
        // When
        final List<Screen> actual = underTest.getScreenings();
        // Then
        Assertions.assertEquals(screenings, actual);
    }
    @Test
    void testAddNewScreening() {
        //Given
        given(movieRepository.existsById(exampleScreen.getFilmName())).willReturn(true);
        given(roomRepository.existsById(exampleScreen.getRoomName())).willReturn(true);
        given(movieRepository.findById(exampleScreen.getFilmName())).willReturn(Optional.of(exampleMovie));
        given(screeningRepository.findByRoomName(exampleScreen.getRoomName())).willReturn(List.of());
        String expected = "Screening succesfully added";
        //When
        String actual = underTest.createScreening(exampleScreen);
        //Then
        Assertions.assertEquals(actual, expected);
    }
    @Test
    void testDeleteScreeningWhenScreenIsFound() {

        //Given
        given(screeningRepository.findByFilmNameAndRoomNameAndStart
                (exampleScreen.getFilmName(), exampleScreen.getRoomName(), exampleScreen.getStart())).willReturn(Optional.of(exampleScreen));
        //When
        underTest.deleteScreening(exampleScreen);
        //Then
        verify(screeningRepository).deleteById(exampleScreen.getId());
    }
    @Test
    void testListScreeningsWhenListIsEmpty() {
        //Given
        given(screeningRepository.findAll()).willReturn(List.of());
        String expected = "There are no screenings";
        //When
        String actual = underTest.listScreenings();
        //Then
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void testListScreeningsWhenListIsNotEmpty() {
        //Given
        given(screeningRepository.findAll()).willReturn(List.of(exampleScreen));
        given(movieRepository.findById(exampleScreen.getFilmName())).willReturn(Optional.of(exampleMovie));
        given(roomRepository.findById(exampleScreen.getRoomName())).willReturn(Optional.of(exampleRoom));
        String expected = "The movie (comedy, 100 minutes), screened in room The room, at 2021-04-01 10:00\n";
        //When
        String actual = underTest.listScreenings();
        //Then
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void testOverLapping() {
        //Given
        given(movieRepository.findById(exampleScreen.getFilmName())).willReturn(Optional.of(exampleMovie));
        List<Screen> screeningList = List.of(exampleScreen);
        //When
        boolean actual = underTest.isOverLapping(screeningList, exampleScreen);
        //Then
        Assertions.assertTrue(actual);
    }
    @Test
    void testOverLappingWhenItIsFalse() {
        //Given
        given(movieRepository.findById(exampleScreen.getFilmName())).willReturn(Optional.of(exampleMovie));
        List<Screen> screeningList = List.of();
        //When
        boolean actual = underTest.isOverLapping(screeningList, exampleScreen);
        //Then
        Assertions.assertFalse(actual);
    }
    @Test
    void testIsInBreakWhenItIsNot() {
        //Given
        given(movieRepository.findById(exampleScreen.getFilmName())).willReturn(Optional.of(exampleMovie));
        List<Screen> screeningList = List.of(exampleScreen);
        //When
        boolean actual = underTest.isInBreak(screeningList, exampleScreen);
        //Then
        Assertions.assertFalse(actual);
    }
   /* @Test
    void testDeleteScreeningWhenScreenNotFound() {

        //Given
        given(screeningRepository.findByFilmNameAndRoomNameAndStart
                (exampleScreen.getFilmName(), exampleScreen.getRoomName(), exampleScreen.getStart())).willReturn(Optional.empty());
        //When
        underTest.deleteScreening(exampleScreen);
        //Then
        verify(screeningRepository).deleteById(exampleScreen.getId());

    }*/

}
