import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.model.AccountType;
import com.epam.training.ticketservice.repositories.AccountRepository;
import com.epam.training.ticketservice.services.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountServiceImpl underTest;
    @BeforeEach
    public void setUp() {
        underTest = new AccountServiceImpl(accountRepository);
    }

    @Test
    void createAccount() {
        // Given
        // When
        underTest.createAccount("user", "user");
        // Then
        verify(accountRepository).save(new Account("user", "user", AccountType.USER));
    }
    @Test
    void createAccountWhenAccountAlreadyExists() {
        // Given
        when(accountRepository.findByUsername("user")).thenReturn(Optional.of(new Account("user", "user", AccountType.USER)));
        // When
        // Then
        assertThrows(IllegalStateException.class, () -> underTest.createAccount("user", "user"));
    }
    @Test
    void testSignInWithNoPrivileges() {
        // Given
        when(accountRepository.findByUsername("user")).thenReturn(Optional.of(new Account("user", "user", AccountType.USER)));
        // When
        underTest.signIn("user", "user");
        // Then
        verify(accountRepository).findByUsername("user");
    }

    @Test
    public void testSignInPrivilegedWithCorrectCredentials() {
        //Given
        when(accountRepository.findByUsername("admin")).thenReturn(Optional.empty());
        //When
        String result = underTest.signInPrivileged("admin", "admin");
        //Then
        verify(accountRepository).findByUsername("admin");
        //assertEquals("Signed in with privileged account admin", result);
    }
    @Test
    public void testSignInPrivilegedWithIncorrectPassword() {
        //Given
        when(accountRepository.findByUsername("admin")).thenReturn(Optional.empty());
        //When
        String result = underTest.signInPrivileged("admin", "sajt");
        //Then
        assertEquals("Login failed due to incorrect credentials", result);
    }
    @Test
    public void testSignInPrivilegedWithIncorrectUsername() {
        //Given
        //When
        String result = underTest.signInPrivileged("sajt", "admin");
        //Then
        assertEquals("Login failed due to incorrect credentials", result);
    }
    @Test
    public void testSignOut() {
        when(accountRepository.findByUsername("admin")).thenReturn(Optional.empty());
        underTest.signInPrivileged("admin", "admin");
        underTest.signOut();

        assertNull(underTest.getLoggedAccount());
    }
    @Test
    public void testSignOutWhenNotSignedIn() {
        underTest.signOut();
        assertNull(underTest.getLoggedAccount());
        assertEquals("You need to log in first.", underTest.loginMessage);
    }
    @Test
    public void testDescribeAccount() {
        when(accountRepository.findByUsername("admin")).thenReturn(Optional.empty());
        underTest.signInPrivileged("admin", "admin");
        assertEquals(Optional.ofNullable(underTest.getLoggedAccount()), underTest.describeAccount());
    }

}

