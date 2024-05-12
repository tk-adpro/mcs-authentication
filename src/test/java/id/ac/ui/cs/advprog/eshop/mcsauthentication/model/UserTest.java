package id.ac.ui.cs.advprog.eshop.mcsauthentication.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private List<User> users;

    private Set<Role> roles;

    private String dummy = "DUMMY";

    private Validator validator;

    @BeforeEach
    void setUp(){
        this.users = new ArrayList<>();
        User user1 = new User(
                "user1",
                "user1@dummy",
                dummy,
                dummy,
                dummy,
                new Date()
        );

        User user2 = new User(
                "user2",
                "user2@dummy",
                dummy,
                dummy,
                dummy,
                new Date()
        );

        this.users.add(user1);
        this.users.add(user2);

        this.roles = new HashSet<>();
        this.roles.add(new Role());
        this.roles.add(new Role());

        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testCreateUserNotValid(){
        this.users.clear();

        User user = new User();

        var violations  = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testCreateUserValid(){
        var violations  = validator.validate(users.getFirst());
        assertTrue(violations.isEmpty());
    }

    @Test
    void testGetterSetter(){
        User user = users.getFirst();
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setDob(user.getDob());

        assertArrayEquals(new String[]{user.getUsername(), user.getEmail(), user.getPassword(),
                                       user.getFirstName(), user.getLastName()},
                new String[]{newUser.getUsername(), newUser.getEmail(), newUser.getPassword(),
                             newUser.getFirstName(), newUser.getLastName()}
                );

        assertEquals(user.getId(), newUser.getId());
        assertEquals(user.getDob(), newUser.getDob());

        newUser.setRoles(roles);

        assertNotEquals(user.getRoles(), newUser.getRoles());
        assertEquals(roles, newUser.getRoles());
    }

    @Test
    void testAllArgsConstructor(){
        User user = users.getFirst();
        User newUser = new User(
                0L,
                "user1",
                "user1@dummy",
                dummy,
                dummy,
                dummy,
                new Date(),
                roles
        );


        assertArrayEquals(new String[]{user.getUsername(), user.getEmail(), user.getPassword(),
                        user.getFirstName(), user.getLastName()},
                new String[]{newUser.getUsername(), newUser.getEmail(), newUser.getPassword(),
                        newUser.getFirstName(), newUser.getLastName()}
        );

        assertNotEquals(user.getId(), newUser.getId());
        assertNotEquals(user.getDob(), newUser.getDob());

        assertEquals(roles, newUser.getRoles());
    }
}
