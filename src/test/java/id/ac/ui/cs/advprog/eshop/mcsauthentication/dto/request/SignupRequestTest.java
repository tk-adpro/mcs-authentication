package id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SignupRequestTest {

    private String dummy = "DUMMY";

    private Validator validator;

    private List<SignupRequest> requests;

    private Set<String> roles;

    @BeforeEach
    void setUp(){
        SignupRequest request1 = new SignupRequest();
        request1.setUsername("user");
        request1.setEmail("user@dummy.co");
        request1.setPassword("pass");
        request1.setFirstName(dummy);
        request1.setLastName(dummy);
        request1.setDob(new Date());

        requests = new ArrayList<>();
        requests.add(request1);

        roles = new HashSet<>();
        roles.add(dummy);

        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testNoArgsCons(){
        SignupRequest request = requests.getFirst();

        SignupRequest newRequest = new SignupRequest();
        newRequest.setUsername(request.getUsername());
        newRequest.setPassword(request.getPassword());
        newRequest.setEmail(request.getEmail());
        newRequest.setFirstName(request.getFirstName());
        newRequest.setLastName(request.getLastName());
        newRequest.setDob(request.getDob());
        newRequest.setRole(roles);

        assertArrayEquals(new String[]{request.getUsername(), request.getEmail(), request.getPassword(),
                        request.getFirstName(), request.getLastName()},
                new String[]{newRequest.getUsername(), newRequest.getEmail(), newRequest.getPassword(),
                        newRequest.getFirstName(), newRequest.getLastName()}
                );

        assertEquals(request.getDob(), newRequest.getDob());

        assertEquals(roles, newRequest.getRole());

    }

    @Test
    void testBlankUsername(){
        SignupRequest request = requests.getFirst();
        request.setUsername(null);

        var violations  = validator.validate(request);
        assertFalse(violations.isEmpty());

        request.setUsername("");

        violations  = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testBlankEmail(){
        SignupRequest request = requests.getFirst();
        request.setEmail(null);

        var violations  = validator.validate(request);
        assertFalse(violations.isEmpty());

        request.setEmail("");

        violations  = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testBlankPassword(){
        SignupRequest request = requests.getFirst();
        request.setPassword(null);

        var violations  = validator.validate(request);
        assertFalse(violations.isEmpty());

        request.setPassword("");

        violations  = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testBlankFirstName(){
        SignupRequest request = requests.getFirst();
        request.setFirstName(null);

        var violations  = validator.validate(request);
        assertFalse(violations.isEmpty());

        request.setFirstName("");

        violations  = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testBlankLastName(){
        SignupRequest request = requests.getFirst();
        request.setLastName(null);

        var violations  = validator.validate(request);
        assertFalse(violations.isEmpty());

        request.setLastName("");

        violations  = validator.validate(request);
        assertFalse(violations.isEmpty());
    }
}
