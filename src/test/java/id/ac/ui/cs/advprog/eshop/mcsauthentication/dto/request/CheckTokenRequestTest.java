package id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CheckTokenRequestTest {

    private String dummy = "DUMMY";

    private Validator validator;

    private List<CheckTokenRequest> requests;

    @BeforeEach
    void setUp(){
        CheckTokenRequest request1 = new CheckTokenRequest();
        request1.setToken("token1");

        requests = new ArrayList<>();
        requests.add(request1);
        
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testNoArgsCons(){
        CheckTokenRequest request = requests.getFirst();

        CheckTokenRequest newRequest = new CheckTokenRequest();
        newRequest.setToken(request.getToken());

        assertEquals(request.getToken(), newRequest.getToken());
    }

    @Test
    void testAllArgsCons(){
        CheckTokenRequest request = requests.getFirst();

        CheckTokenRequest newRequest = new CheckTokenRequest(request.getToken());

        assertEquals(request.getToken(), newRequest.getToken());
    }

    @Test
    void testBlankToken(){
        CheckTokenRequest request = new CheckTokenRequest();

        var violations  = validator.validate(request);
        assertFalse(violations.isEmpty());

        request.setToken("");

        violations  = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

}
