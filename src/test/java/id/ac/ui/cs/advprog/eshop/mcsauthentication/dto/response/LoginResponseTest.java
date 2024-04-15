package id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginResponseTest {

    private String dummy = "DUMMY";

    private List<LoginResponse> responses;
    private List<String> roles;

    @BeforeEach
    void setUp(){
        LoginResponse response1 = new LoginResponse();
        response1.setToken(dummy);
        response1.setEmail("dummy@dummy.co");
        response1.setUsername(dummy);
        response1.setId(1L);

        responses = new ArrayList<>();
        responses.add(response1);

        roles = new ArrayList<>();
        roles.add(dummy);
    }

    @Test
    void testTokenType(){
        LoginResponse response = new LoginResponse();
        assertEquals("Bearer", response.getType());
    }

    @Test
    void testNoArgsCons(){
        LoginResponse response = responses.getFirst();

        LoginResponse newResponse = new LoginResponse();
        newResponse.setToken(response.getToken());
        newResponse.setUsername(response.getUsername());
        newResponse.setEmail(response.getEmail());
        newResponse.setId(response.getId());
        newResponse.setRoles(roles);

        assertArrayEquals(new String[]{response.getUsername(), response.getEmail(), response.getToken()},
                new String[]{newResponse.getUsername(), newResponse.getEmail(), newResponse.getToken()}
        );

        newResponse.setType(dummy);

        assertEquals(dummy, newResponse.getType());
        assertEquals(response.getId(), newResponse.getId());
        assertEquals(roles, newResponse.getRoles());
    }
}
