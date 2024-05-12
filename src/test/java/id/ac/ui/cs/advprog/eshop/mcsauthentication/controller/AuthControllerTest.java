package id.ac.ui.cs.advprog.eshop.mcsauthentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.CheckTokenRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.LoginRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.SignupRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response.CheckTokenResponse;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.service.AuthService;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.service.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    private MockMvc mockMvc;

    @MockBean
    AuthService service;

    Map<String, Object> responseMap;

    private ObjectMapper mapper;

    String dummy = "dummy";

    String passDummy = "Dummy123";

    SignupRequest signupRequest;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        responseMap = new HashMap<>();
        responseMap.put("status", HttpStatus.OK);

        this.mapper = new ObjectMapper();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        signupRequest = new SignupRequest();
        signupRequest.setUsername(dummy);
        signupRequest.setEmail("dummy@dummy.co");
        signupRequest.setPassword(passDummy);
        signupRequest.setFirstName(dummy);
        signupRequest.setLastName(dummy);

        try {
            signupRequest.setDob(sf.parse(sf.format(new Date())));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testLoginSuccess() throws Exception{
        lenient().when(service.login(any())).thenReturn(responseMap);
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.mapper.writeValueAsString(new LoginRequest(dummy, passDummy)))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("login"));
    }

    @Test
    void testLoginBlank() throws Exception{
        lenient().when(service.login(any())).thenReturn(responseMap);
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.mapper.writeValueAsString(new LoginRequest()))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(handler().methodName("login"));
    }

    @Test
    void testLoginFail() throws Exception{
        responseMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        lenient().when(service.login(any())).thenReturn(responseMap);
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.mapper.writeValueAsString(new LoginRequest(dummy, passDummy)))
                        .with(csrf()))
                .andExpect(status().is5xxServerError())
                .andExpect(handler().methodName("login"));
    }

    @Test
    void testSignup() throws Exception{
        lenient().when(service.signup(any())).thenReturn(responseMap);
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.mapper.writeValueAsString(signupRequest))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("signup"));
    }

    @Test
    void testSignupInvalidRequestBody() throws Exception{
        signupRequest.setPassword(dummy);
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.mapper.writeValueAsString(signupRequest))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(handler().methodName("signup"));
    }

    @Test
    void testSignupFailed() throws Exception{
        responseMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        lenient().when(service.signup(any())).thenReturn(responseMap);
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.mapper.writeValueAsString(signupRequest))
                        .with(csrf()))
                .andExpect(status().is5xxServerError())
                .andExpect(handler().methodName("signup"));
    }

    @Test
    void testCheckToken() throws Exception{
        lenient().when(service.checkToken(any())).thenReturn(new CheckTokenResponse());
        mockMvc.perform(post("/api/auth/check-token")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.mapper.writeValueAsString(new CheckTokenRequest(dummy)))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("checkToken"));
    }

    @Test
    void testCheckTokenBlank() throws Exception{
        lenient().when(service.checkToken(any())).thenReturn(new CheckTokenResponse());
        mockMvc.perform(post("/api/auth/check-token")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.mapper.writeValueAsString(new CheckTokenRequest()))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(handler().methodName("checkToken"));
    }
}
