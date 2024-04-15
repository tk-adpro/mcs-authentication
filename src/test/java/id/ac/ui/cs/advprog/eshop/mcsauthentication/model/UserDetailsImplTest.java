package id.ac.ui.cs.advprog.eshop.mcsauthentication.model;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.enums.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsImplTest {
    private List<User> users;

    private Set<Role> roles;

    private String dummy = "DUMMY";

    private UserDetailsImpl userDetails;

    private Collection<? extends GrantedAuthority> authorities;

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
        this.roles.add(new Role(RoleName.ROLE_USER.getValue()));
        this.roles.add(new Role(RoleName.ROLE_CUSTOMER.getValue()));

        user1.setRoles(this.roles);
        authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());


        userDetails = new UserDetailsImpl(
                user1.getId(),
                user1.getUsername(),
                user1.getEmail(),
                user1.getPassword(),
                authorities
        );
    }

    @Test
    void testBuildFromUser(){
        User user = this.users.getFirst();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertEquals(user.getId(), userDetails.getId());
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getEmail(), userDetails.getEmail());
        assertEquals(user.getPassword(), userDetails.getPassword());

        assertEquals(authorities, userDetails.getAuthorities());
    }

    @Test
    void testConstructor(){
        User user = this.users.getFirst();

        UserDetailsImpl userDetails = new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );

        assertEquals(user.getId(), userDetails.getId());
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getEmail(), userDetails.getEmail());
        assertEquals(user.getPassword(), userDetails.getPassword());

        assertEquals(authorities, userDetails.getAuthorities());

        assertTrue(this.userDetails.equals(userDetails));

        assertTrue(userDetails.equals(userDetails));

        assertFalse(userDetails.equals(null));

        assertFalse(userDetails.equals(user));
    }

    @Test
    void testIsEnabled(){
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testIsAccountNonExpired(){
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked(){
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired(){
        assertTrue(userDetails.isCredentialsNonExpired());
    }

}
