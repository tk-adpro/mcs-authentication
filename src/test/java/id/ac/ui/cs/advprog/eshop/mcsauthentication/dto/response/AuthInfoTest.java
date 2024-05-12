package id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthInfoTest {

    private String dummy = "DUMMY";

    private AuthInfo authInfo;
    private List<String> authorities;

    @BeforeEach
    void setUp(){

        authorities = new ArrayList<>();
        authorities.add(dummy);

        authInfo = new AuthInfo();
        authInfo.setId(1L);
        authInfo.setEmail(dummy);
        authInfo.setUsername(dummy);
        authInfo.setFirstName(dummy);
        authInfo.setLastName(dummy);
        authInfo.setAuthorities(authorities);
    }

    @Test
    void testAuthInfo(){
        AuthInfo newInfo = new AuthInfo();
        newInfo.setId(authInfo.getId());
        newInfo.setEmail(authInfo.getEmail());
        newInfo.setUsername(authInfo.getUsername());
        newInfo.setFirstName(authInfo.getFirstName());
        newInfo.setLastName(authInfo.getLastName());
        newInfo.setAuthorities(authorities);

        assertArrayEquals(new String[]{authInfo.getEmail(), authInfo.getUsername(), authInfo.getFirstName(), authInfo.getLastName()},
                new String[]{newInfo.getEmail(), newInfo.getUsername(), newInfo.getFirstName(), newInfo.getLastName()});
        assertEquals(authorities, newInfo.getAuthorities());
        assertEquals(authInfo.getId(), newInfo.getId());
    }
}
