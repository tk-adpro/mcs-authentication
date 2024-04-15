package id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTest {

    private String dummy = "DUMMY";

    private Validator validator;

    private List<LoginRequest> requests;

    @BeforeEach
    void setUp(){
        LoginRequest request1 = new LoginRequest();
        request1.setUsername("user");
        request1.setPassword("pass");

        requests = new ArrayList<>();
        requests.add(request1);

        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testNoArgsCons(){
        LoginRequest request = requests.getFirst();

        LoginRequest newRequest = new LoginRequest();
        newRequest.setUsername(request.getUsername());
        newRequest.setPassword(request.getPassword());

        assertArrayEquals(new String[]{request.getUsername(), request.getPassword()},
                new String[]{newRequest.getUsername(), newRequest.getPassword()}
        );
    }

    @Test
    void testAllArgsCons(){
        LoginRequest request = requests.getFirst();

        LoginRequest newRequest = new LoginRequest(request.getUsername(), request.getPassword());

        assertArrayEquals(new String[]{request.getUsername(), request.getPassword()},
                new String[]{newRequest.getUsername(), newRequest.getPassword()}
        );
    }

    @Test
    void testBlankUsername(){
        LoginRequest request = new LoginRequest();
        request.setPassword(dummy);

        var violations  = validator.validate(request);
        assertFalse(violations.isEmpty());

        request.setUsername("");

        violations  = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testBlankPassword(){
        LoginRequest request = new LoginRequest();
        request.setUsername(dummy);

        var violations  = validator.validate(request);
        assertFalse(violations.isEmpty());

        request.setPassword("");

        violations  = validator.validate(request);
        assertFalse(violations.isEmpty());
    }
}
