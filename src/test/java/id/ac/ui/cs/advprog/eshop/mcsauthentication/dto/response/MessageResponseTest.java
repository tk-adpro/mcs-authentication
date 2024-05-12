package id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageResponseTest {

    private String dummy = "DUMMY";

    private List<MessageResponse> responses;

    @BeforeEach
    void setUp(){
        MessageResponse response1 = new MessageResponse();
        response1.setMessage(dummy);

        responses = new ArrayList<>();
        responses.add(response1);
    }

    @Test
    void testNoArgsCon(){
        MessageResponse response = responses.getFirst();
        MessageResponse newResponse = new MessageResponse();
        newResponse.setMessage(response.getMessage());

        assertEquals(response.getMessage(), newResponse.getMessage());
    }

    @Test
    void testAllArgsCon(){
        MessageResponse response = new MessageResponse(dummy);

        assertEquals(dummy, response.getMessage());
    }
}
