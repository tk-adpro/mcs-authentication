package id.ac.ui.cs.advprog.eshop.mcsauthentication.service;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.SignupRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.enums.RoleName;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.Role;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.User;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl service;

    String dummy = "dummy";

    User user;

    Set<Role> roles;

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

        this.roles = new HashSet<>();
        roles.add(new Role());
    }

    @Test
    void testCreateRequestedUserWithRoles(){
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(this.user);

        SignupRequest request = new SignupRequest();
        request.setUsername(user.getUsername());
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        request.setFirstName(user.getFirstName());
        request.setLastName(user.getLastName());
        request.setDob(user.getDob());

        User result = service.createRequestedUserWithRoles(request, roles);

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        assertEquals(this.user.getId(), result.getId());
    }

    @Test
    void testExistByUsername(){
        Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        assertTrue(service.existsByUsername(user.getUsername()));
        Mockito.verify(userRepository, Mockito.times(1)).existsByUsername(user.getUsername());
    }

    @Test
    void testNotExistByUsername(){
        Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);

        assertFalse(service.existsByUsername(user.getUsername()));
        Mockito.verify(userRepository, Mockito.times(1)).existsByUsername(user.getUsername());
    }

    @Test
    void testExistByEmail(){
        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertTrue(service.existsByEmail(user.getEmail()));
        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(user.getEmail());
    }

    @Test
    void testNotExistByEmail(){
        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        assertFalse(service.existsByEmail(user.getEmail()));
        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(user.getEmail());
    }

    @Test
    void testFindByUsernameNotExist(){
        Mockito.when(userRepository.findByUsername(dummy)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            service.findByUsername(dummy);
        });

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(dummy);
        assertTrue(exception.getMessage().contains("User Not Found with username: " + dummy));
    }

    @Test
    void testFindByUsernameExist(){
        Mockito.when(userRepository.findByUsername(dummy)).thenReturn(Optional.of(user));

        User result = service.findByUsername(dummy);

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(dummy);
        assertEquals(user.getId(), result.getId());
    }

}
