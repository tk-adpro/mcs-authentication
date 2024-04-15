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
        request1.setUrl("/test/all");

        requests = new ArrayList<>();
        requests.add(request1);
        
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testNoArgsCons(){
        CheckTokenRequest request = requests.getFirst();

        CheckTokenRequest newRequest = new CheckTokenRequest();
        newRequest.setToken(request.getToken());
        newRequest.setUrl(request.getUrl());

        assertArrayEquals(new String[]{request.getToken(), request.getUrl()},
                new String[]{newRequest.getToken(), newRequest.getUrl()}
        );
    }

    @Test
    void testAllArgsCons(){
        CheckTokenRequest request = requests.getFirst();

        CheckTokenRequest newRequest = new CheckTokenRequest(request.getToken(), request.getUrl());

        assertArrayEquals(new String[]{request.getToken(), request.getUrl()},
                new String[]{newRequest.getToken(), newRequest.getUrl()}
        );
    }

    @Test
    void testBlankToken(){
        CheckTokenRequest request = new CheckTokenRequest();
        request.setUrl(dummy);

        var violations  = validator.validate(request);
        assertFalse(violations.isEmpty());

        request.setToken("");

        violations  = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testBlankUrl(){
        CheckTokenRequest request = new CheckTokenRequest();
        request.setToken(dummy);

        var violations  = validator.validate(request);
        assertFalse(violations.isEmpty());

        request.setUrl("");

        violations  = validator.validate(request);
        assertFalse(violations.isEmpty());
    }
}
