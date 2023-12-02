import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.model.AccountType;
import com.epam.training.ticketservice.repositories.AccountRepository;
import com.epam.training.ticketservice.services.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountServiceImpl underTest;
    @Mock
    private AccountRepository accountRepository;
    @BeforeEach
    void setUp() {
        underTest = new AccountServiceImpl(accountRepository);

    }
    @Test
    public void testSignInPrivilegedWithCorrectCredentials() {
        //Given
        //When
        underTest.signInPrivileged("admin", "admin");
        //when(accountRepository.findByUsername("admin")).thenReturn(Optional.empty());
        //Then
        verify(accountRepository).findByUsername("admin");
    }
}
