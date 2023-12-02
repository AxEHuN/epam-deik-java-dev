import com.epam.training.ticketservice.repositories.AccountRepository;
import com.epam.training.ticketservice.services.AccountServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountServiceTest;
    @Mock
    private AccountRepository accountRepository;


}
