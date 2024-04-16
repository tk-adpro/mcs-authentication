package id.ac.ui.cs.advprog.eshop.mcsauthentication.service;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.Role;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.User;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserDetailsServiceImpl service;

    User user;

    String dummy = "dummy";

    @BeforeEach
    void setUp(){
        this.user = new User(
                "user",
                "user@dummy",
                dummy,
                dummy,
                dummy,
                new Date()
        );
    }

    @Test
    void testLoadUserByUsername(){
        Mockito.when(userService.findByUsername(dummy)).thenReturn(user);

        UserDetailsImpl userDetails = (UserDetailsImpl) service.loadUserByUsername(dummy);

        assertEquals(user.getUsername(), userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsernameThrowsException(){
        Mockito.when(userService.findByUsername(dummy)).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername(dummy);
        });

        Mockito.verify(userService, Mockito.times(1)).findByUsername(dummy);
    }
}
