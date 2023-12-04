import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repositories.RoomRepository;
import com.epam.training.ticketservice.services.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
    RoomServiceImpl underTest;
    @Mock
    private RoomRepository roomRepository;
    private final Room example = new Room("The room", 10, 10);

    @BeforeEach
    public void setUp() {
        underTest = new RoomServiceImpl(roomRepository);
    }

    @Test
    public void testCreateRoom() {
        //Given
        given(roomRepository.findById("The room")).willReturn(Optional.empty());
        //When
        underTest.createRoom(example);
        //Then
        verify(roomRepository).save(example);
    }
    @Test
    public void testCreateRoomShouldThrowExceptionWhenRoomAlreadyExists() {
        //Given
        given(roomRepository.findById("The room")).willReturn(Optional.of(example));
        //When
        //Then
        try {
            underTest.createRoom(example);
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("Room already exists");
        }
    }
    @Test
    public void testUpdateRoom() {
        //Given
        given(roomRepository.findById("The room")).willReturn(Optional.of(example));
        //When
        underTest.updateRoom("The room", 15, 10);
        //Then
        assertEquals(roomRepository.findById("The room").get(), example);
    }
    @Test
    public void testUpdateRoomShouldThrowExceptionWhenRoomDoesNotExists() {
        //Given
        given(roomRepository.findById("The room")).willReturn(Optional.empty());
        //When
        //Then
        try {
            underTest.updateRoom("The room", 15, 10);
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("Room does not exists");
        }
    }
    @Test
    public void testDeleteRoom() {
        //Given
        given(roomRepository.findById("The room")).willReturn(Optional.of(example));
        //When
        underTest.deleteRoom("The room");
        //Then
        verify(roomRepository).deleteById("The room");
    }
    @Test
    public void testDeleteRoomShouldThrowExceptionWhenRoomDoesNotExists() {
        //Given
        given(roomRepository.findById("The room")).willReturn(Optional.empty());
        //When
        //Then
        try {
            underTest.deleteRoom("The room");
        } catch (IllegalStateException e) {
            assert e.getMessage().equals("Room does not exists");
        }
    }
    @Test
    public void testListRoomsWhenThereAreNoRooms() {
        //Given
        when(roomRepository.findAll()).thenReturn(List.of());
        //When
        String result = underTest.listRooms().toString();
        //Then
        assertEquals("There are no rooms at the moment",result);
    }
    /*@Test
    public void testListRoomsWhenThereAreRooms() {
        //Given
        when(roomRepository.findAll()).thenReturn(List.of(example));
        //When
        String result = underTest.listRooms().toString();
        //Then
        assertEquals("Room{name='The room', numberOfRows=10, numberOfColumns=10}\n",result);
    }*/

}
