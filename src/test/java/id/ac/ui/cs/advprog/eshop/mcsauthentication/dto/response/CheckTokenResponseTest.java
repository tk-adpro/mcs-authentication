package id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CheckTokenResponseTest {

    private String dummy = "DUMMY";

    private List<CheckTokenResponse> responses;

    @BeforeEach
    void setUp(){
        CheckTokenResponse response1 = new CheckTokenResponse();
        response1.setMessage(dummy);
        response1.setData(dummy);

        responses = new ArrayList<>();
        responses.add(response1);
    }

    @Test
    void testNoArgsCons(){
        CheckTokenResponse response = responses.getFirst();

        CheckTokenResponse newResponse = new CheckTokenResponse();
        newResponse.setMessage(response.getMessage());
        newResponse.setData(response.getData());

        assertEquals(response.getMessage(), newResponse.getMessage());
        assertEquals(response.getData(), newResponse.getMessage());
    }
}
